package com.ta.sdsofa.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ta.sdsofa.R;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.KelasRowModel;
import com.ta.sdsofa.model.TugasModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;
import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_URL;

public class EditTugasActivity extends AppCompatActivity {
    private EditText edtMatapelajaran, edtnamaTugas, edtTugas, edtGuru, edtKelas;
    private DatePicker deadline;
    private ImageView imageView;
    private String kelas;
    private Button btnSubmit, btnChoosefile;
    private UtilMessage utilMessage;
    private KelasRowModel kelasRowModel;
    private TugasModel tugasModel;

    int CODE_GALLERY_REQUEST = 999;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tugas);

        edtMatapelajaran = findViewById(R.id.edt_matapelajaran);
        edtnamaTugas = findViewById(R.id.edt_namatugas);
        edtTugas = findViewById(R.id.edt_tugas);
        edtGuru = findViewById(R.id.edt_guru);
        edtKelas = findViewById(R.id.edt_kelas);
        kelasRowModel = (KelasRowModel) getIntent().getExtras().get("kelas");
        tugasModel = (TugasModel) getIntent().getExtras().get("data");
        imageView = findViewById(R.id.no_imageTugas);
        deadline = findViewById(R.id.edt_tanggal);

        btnSubmit = findViewById(R.id.btn_submit);
        btnChoosefile = findViewById(R.id.btn_choose_file);
        utilMessage = new UtilMessage(this);

        btnChoosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        EditTugasActivity.this,
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

        setData(tugasModel);
    }
    private void setData(TugasModel tugasModel){
        edtMatapelajaran.setText(tugasModel.getMata_pelajaran());
        edtnamaTugas.setText(tugasModel.getNamatugas());
        edtTugas.setText(tugasModel.getTugas());
        edtGuru.setText(tugasModel.getGuru());
        edtKelas.setText(tugasModel.getKelas());

        Glide.with(this).asBitmap().load(IMAGE_URL+tugasModel.getFoto()).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(resource);
            }
            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }
    private void submitData() {
        final String matapelajaran = edtMatapelajaran.getText().toString();
        final String namatugas = edtnamaTugas.getText().toString();
        final String tugas = edtTugas.getText().toString();
        final String guru = edtGuru.getText().toString();
        final String kelas1 = edtKelas.getText().toString();





        int   day  = deadline.getDayOfMonth();
        int   month= deadline.getMonth();
        int   year = deadline.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String formatedDate = sdf.format(calendar.getTime());



        if (matapelajaran.trim().isEmpty()){
            Toast.makeText(this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }

        else{
            StringRequest request = new StringRequest(Request.Method.POST,
                    BASE_URL + "tambah_tugas.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            utilMessage.dismissProgressBar();
                            try {
                                JSONObject jsonRespone = new JSONObject(response);

                                int status = jsonRespone.getInt("status");
                                String message = jsonRespone.getString("message");

                                if (status == 0 ){
                                    Toast.makeText(EditTugasActivity.this, message, Toast.LENGTH_SHORT).show();


                                }
                                else{
                                    Toast.makeText(EditTugasActivity.this, "Tambar buku gagal " + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e){
                                Toast.makeText(EditTugasActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    utilMessage.dismissProgressBar();
                    Toast.makeText(EditTugasActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    String imageData = imageToString(bitmap);
                    params.put("mata_pelajaran", matapelajaran);
                    params.put("nama_tugas", namatugas);
                    params.put("tugas", tugas);
                    params.put("guru", guru);
                    params.put("kelas", kelas1);
                    params.put("tanggal", formatedDate);
                    params.put("foto", imageData);


                    return params;
                }
            };

            utilMessage.showProgressBar("Submiting Data");
            Volley.newRequestQueue(this).add(request);
        }

    }
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
                imageView.setImageBitmap(bitmap);
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