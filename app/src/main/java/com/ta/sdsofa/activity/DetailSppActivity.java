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
import android.print.PageRange;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.bumptech.glide.util.Util;
import com.ta.sdsofa.R;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.SppModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;
import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_SPP;
import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_URL;

public class DetailSppActivity extends AppCompatActivity {
    private TextView nama, nisn, untuk_bulan,tanggal, status, kelas;
    private ImageView ivBukti;
    private Button btnKonfirmasi, btnChooseFile, btnUpload;
    private UtilMessage utilMessage;
    private SppModel sppModel;
    private SessionManager sessionManager;
    int CODE_GALLERY_REQUEST = 999;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spp);

        sppModel = (SppModel) getIntent().getExtras().get("data");
        sessionManager = new SessionManager(this);
        utilMessage = new UtilMessage(this);

        nama = findViewById(R.id.tv_namaSiswa);
        nisn = findViewById(R.id.tv_nisn);
        untuk_bulan = findViewById(R.id.tv_bulan);
        kelas = findViewById(R.id.tv_kelas);
        tanggal = findViewById(R.id.tv_tanggal);
        status = findViewById(R.id.tv_status);
        btnChooseFile = findViewById(R.id.btn_choose_file);
        btnUpload = findViewById(R.id.btn_upload);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi);

        ivBukti = findViewById(R.id.image_bukti);

        if (sessionManager.getRole().equals("admin")){
            btnChooseFile.setVisibility(View.GONE);
            btnUpload.setVisibility(View.GONE);
        }
        else{
            btnKonfirmasi.setVisibility(View.GONE);
        }

        if(sppModel.getStatus_pembayaran().equals("lunas")){
            btnUpload.setVisibility(View.GONE);
            btnChooseFile.setVisibility(View.GONE);
            btnKonfirmasi.setVisibility(View.GONE);
        }

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        DetailSppActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                konfirmasiData();
            }
        });

        setData(sppModel);

    }

    private void konfirmasiData() {
            StringRequest request = new StringRequest(Request.Method.POST,
                    BASE_URL + "konfirmasi_spp.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            utilMessage.dismissProgressBar();
                            try {
                                JSONObject jsonRespone = new JSONObject(response);

                                int status = jsonRespone.getInt("status");
                                String message = jsonRespone.getString("message");

                                if (status == 0) {
                                    Toast.makeText(DetailSppActivity.this, message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(DetailSppActivity.this, "Gagal " + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(DetailSppActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    utilMessage.dismissProgressBar();
                    Toast.makeText(DetailSppActivity.this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("id", sppModel.getId());


                    return params;
                }
            };

            utilMessage.showProgressBar("Submiting Data");
            Volley.newRequestQueue(this).add(request);
    }


    private void setData(SppModel sppModel) {
        nama.setText(sppModel.getNama());
        nisn.setText(sppModel.getNisn());
        untuk_bulan.setText(sppModel.getUntuk_bulan());
        kelas.setText(sppModel.getKelas());
        tanggal.setText(sppModel.getTgl_pembayaran());
        status.setText(sppModel.getStatus_pembayaran());

        Glide.with(this).asBitmap().load(IMAGE_SPP + sppModel.getBukti()).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                ivBukti.setImageBitmap(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }

    private void submitData() {
        if(bitmap == null){
            Toast.makeText(this, "Mohon pilih foto bukti", Toast.LENGTH_SHORT).show();
        }
        else {
            StringRequest request = new StringRequest(Request.Method.POST,
                    BASE_URL + "edit_spp.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            utilMessage.dismissProgressBar();
                            try {
                                JSONObject jsonRespone = new JSONObject(response);

                                int status = jsonRespone.getInt("status");
                                String message = jsonRespone.getString("message");

                                if (status == 0) {
                                    Toast.makeText(DetailSppActivity.this, message, Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(DetailSppActivity.this, "Tambah gagal " + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(DetailSppActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    utilMessage.dismissProgressBar();
                    Toast.makeText(DetailSppActivity.this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    String imageData;

                    if (bitmap == null) {
                        imageData = sppModel.getBukti();
                    } else {
                        imageData = imageToString(bitmap);
                    }
                    params.put("id", sppModel.getId());
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
                ivBukti.setImageBitmap(bitmap);
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