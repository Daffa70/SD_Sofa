package com.ta.sdsofa.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ta.sdsofa.R;
import com.ta.sdsofa.helper.UtilMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class TambahAdminActivity extends AppCompatActivity {
    private Spinner spinjabatan;
    private String jabatan;
    private EditText edtid, edtnama, edtalamat,  edtmatapelajaran, edtnohp, edtemail;
    private ImageView imageViewFoto;
    private UtilMessage utilMessage;
    private Button btnSubmit, btnChooseFile;
    int CODE_GALLERY_REQUEST = 999;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_admin);

        spinjabatan = findViewById(R.id.spinner1);

        edtid = findViewById(R.id.edt_id);
        edtnama = findViewById(R.id.edt_nama);
        edtalamat = findViewById(R.id.edt_alamat);
        edtnohp = findViewById(R.id.edt_nohp);
        edtemail = findViewById(R.id.edt_email);
        edtmatapelajaran = findViewById(R.id.edt_pengurus);

        btnSubmit = findViewById(R.id.btn_submit);
        btnChooseFile = findViewById(R.id.btn_choose_file);

        imageViewFoto = findViewById(R.id.no_image);

        utilMessage = new UtilMessage(this);

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        TambahAdminActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });



        //get the spinner from the xml.
        spinjabatan = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"admin", "guru", "pegawai"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        spinjabatan.setAdapter(adapter);

        spinjabatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jabatan = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void submitData() {
        final String nama = edtnama.getText().toString();
        final String alamat = edtalamat.getText().toString();
        final String nohp = edtnohp.getText().toString();
        final String email = edtemail.getText().toString();
        final String id = edtid.getText().toString();
        final String matapelajaran = edtmatapelajaran.getText().toString();
        final String jabatan1 = jabatan;






        if (id.trim().isEmpty()){
            Toast.makeText(this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }
        else if(bitmap == null){
            Toast.makeText(this, "Mohon pilih foto", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST,
                    BASE_URL + "tambah_admin.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            utilMessage.dismissProgressBar();
                            try {
                                JSONObject jsonRespone = new JSONObject(response);

                                int status = jsonRespone.getInt("status");
                                String message = jsonRespone.getString("message");

                                if (status == 0 ){
                                    Toast.makeText(TambahAdminActivity.this, message, Toast.LENGTH_SHORT).show();

                                    finish();
                                }
                                else{
                                    Toast.makeText(TambahAdminActivity.this, "Tambar buku gagal " + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e){
                                Toast.makeText(TambahAdminActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    utilMessage.dismissProgressBar();
                    Toast.makeText(TambahAdminActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    String imageData = imageToString(bitmap);
                    params.put("nama", nama);
                    params.put("alamat", alamat);
                    params.put("nohp", nohp);
                    params.put("email", email);
                    params.put("mata_pelajaran", matapelajaran);
                    params.put("id", id);
                    params.put("role", jabatan1);
                    params.put("foto", imageData);

                    return params;
                }
            };

            utilMessage.showProgressBar("Submiting Data");
            Volley.newRequestQueue(this).add(request);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }
            else {
                Toast.makeText(this, "Anda tidak punya akses", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imageViewFoto.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}