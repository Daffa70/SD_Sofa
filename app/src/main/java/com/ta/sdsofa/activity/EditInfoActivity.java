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
import com.ta.sdsofa.model.InfoModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;
import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_URL;

public class EditInfoActivity extends AppCompatActivity {
    private EditText edtJudul, edtSubjek, edtIsi;
    private ImageView foto;
    private UtilMessage utilMessage;
    private Button btnSubmit, btnChooseFile;
    private InfoModel infoModel;
    int CODE_GALLERY_REQUEST = 999;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        edtJudul = findViewById(R.id.edt_judul);
        edtSubjek = findViewById(R.id.edt_subjek);
        edtIsi = findViewById(R.id.edt_isi);
        foto = findViewById(R.id.no_imageTugas);
        btnSubmit = findViewById(R.id.btn_submit);
        btnChooseFile = findViewById(R.id.btn_choose_file);

        infoModel = (InfoModel) getIntent().getExtras().get("data");
        utilMessage = new UtilMessage(this);

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        EditInfoActivity.this,
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

        setData(infoModel);
    }

    private void setData(InfoModel infoModel) {
        edtJudul.setText(infoModel.getJudul());
        edtSubjek.setText(infoModel.getSubjek());
        edtIsi.setText(infoModel.getIsi());

        Glide.with(this).asBitmap().load(IMAGE_URL+infoModel.getFoto()).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                foto.setImageBitmap(resource);
            }
            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }

    private void submitData() {
        final String judul = edtJudul.getText().toString();
        final String subjek = edtSubjek.getText().toString();
        final String isi = edtIsi.getText().toString();


        if (judul.trim().isEmpty()){
            Toast.makeText(this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }

        else{
            StringRequest request = new StringRequest(Request.Method.POST,
                    BASE_URL + "edit_info.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            utilMessage.dismissProgressBar();
                            try {
                                JSONObject jsonRespone = new JSONObject(response);

                                int status = jsonRespone.getInt("status");
                                String message = jsonRespone.getString("message");

                                if (status == 0 ){
                                    Toast.makeText(EditInfoActivity.this, message, Toast.LENGTH_SHORT).show();


                                }
                                else{
                                    Toast.makeText(EditInfoActivity.this, "Tambar buku gagal " + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e){
                                Toast.makeText(EditInfoActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    utilMessage.dismissProgressBar();
                    Toast.makeText(EditInfoActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    String imageData = imageToString(bitmap);

                    params.put("id", infoModel.getId());
                    params.put("judul", judul);
                    params.put("isi", isi);
                    params.put("subjek", subjek);
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
                foto.setImageBitmap(bitmap);
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