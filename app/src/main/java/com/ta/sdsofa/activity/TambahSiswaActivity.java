package com.ta.sdsofa.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.ta.sdsofa.MainActivity;
import com.ta.sdsofa.R;
import com.ta.sdsofa.helper.UtilMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class TambahSiswaActivity extends AppCompatActivity {
    private Button btnSubmit, btnChooseFile;
    private EditText edtnisn, edtkelas, edtnama, edtalamat,  edtkotalhir, edtnohp, edttahunmasuk, edtnamawali, edtnohpwali;
    private DatePicker tanggal;
    private ImageView imageViewFoto;
    private UtilMessage utilMessage;
    int CODE_GALLERY_REQUEST = 999;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_siswa);

        edtnisn = findViewById(R.id.edt_nisn);
        edtkelas = findViewById(R.id.edt_kelas);
        edtnama = findViewById(R.id.edt_nama);
        edtalamat = findViewById(R.id.edt_alamat);
        edtkotalhir = findViewById(R.id.edt_kotalahir);
        edtnohp = findViewById(R.id.edt_nohp);
        edttahunmasuk = findViewById(R.id.edt_tahun_masuk);
        edtnamawali = findViewById(R.id.edt_nama_wali);
        edtnohpwali = findViewById(R.id.edt_nama_wali);

        tanggal = findViewById(R.id.edt_tanggal);

        btnSubmit = findViewById(R.id.btn_submit);
        btnChooseFile = findViewById(R.id.btn_choose_file);

        imageViewFoto = findViewById(R.id.no_image);

        utilMessage = new UtilMessage(this);

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        TambahSiswaActivity.this,
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

    }

    private void submitData() {
        final String nisn = edtnisn.getText().toString();
        final String kelas = edtkelas.getText().toString();
        final String nama = edtnama.getText().toString();
        final String alamat = edtalamat.getText().toString();
        final String kotaklhir = edtkotalhir.getText().toString();
        final String nohp = edtnohp.getText().toString();
        final String tahunmasuk = edttahunmasuk.getText().toString();
        final String namawali = edtnamawali.getText().toString();
        final String nohpwali = edtnohpwali.getText().toString();

        int   day  = tanggal.getDayOfMonth();
        int   month= tanggal.getMonth();
        int   year = tanggal.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String formatedDate = sdf.format(calendar.getTime());




        if (nisn.trim().isEmpty()){
            Toast.makeText(this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }
        else if (kelas.trim().isEmpty()){
            Toast.makeText(this , "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }
        else if (nama.trim().isEmpty()){
            Toast.makeText(this , "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }
        else if (alamat.trim().isEmpty()){
            Toast.makeText(this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }
        else if (kotaklhir.trim().isEmpty()){
            Toast.makeText(this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }
        else if (nohp.trim().isEmpty()){
            Toast.makeText(this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }
        else if (tahunmasuk.trim().isEmpty()){
            Toast.makeText(this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }
        else if (namawali.trim().isEmpty()){
            Toast.makeText(this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }
        else if (nohpwali.trim().isEmpty()){
            Toast.makeText(this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST,
                    BASE_URL + "tambah_buku.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            utilMessage.dismissProgressBar();
                            try {
                                JSONObject jsonRespone = new JSONObject(response);

                                int status = jsonRespone.getInt("status");
                                String message = jsonRespone.getString("message");

                                if (status == 0 ){
                                    Toast.makeText(TambahSiswaActivity.this, message, Toast.LENGTH_SHORT).show();

                                    resetInput();
                                }
                                else{
                                    Toast.makeText(TambahSiswaActivity.this, "Tambar buku gagal " + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e){
                                Toast.makeText(TambahSiswaActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    utilMessage.dismissProgressBar();
                    Toast.makeText(TambahSiswaActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    String imageData = imageToString(bitmap);
                    params.put("nisn", nisn);
                    params.put("kelas", kelas);
                    params.put("nama", nama);
                    params.put("alamat", alamat);
                    params.put("kotalhir", kotaklhir);
                    params.put("nohp", nohp);
                    params.put("tahunmasuk", tahunmasuk);
                    params.put("namawali", namawali);
                    params.put("nohpwali", nohpwali);
                    params.put("tanggal", formatedDate);
                    params.put("foto", imageData);


                    return params;
                }
            };

            utilMessage.showProgressBar("Submiting Data");
            Volley.newRequestQueue(this).add(request);
        }
    }

    private void resetInput() {
        edtnisn.setText("");
        edtkelas.setText("");
        edtnama.setText("");
        edtalamat.setText("");
        edtkotalhir.setText("");
        edtnohp.setText("");
        edttahunmasuk.setText("");
        edtnamawali.setText("");
        edtnohpwali.setText("");
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