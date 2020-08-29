package com.ta.sdsofa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.ta.sdsofa.R;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.SiswaModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;
import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_URL;

public class DetailProfileSiswaActivity extends AppCompatActivity {
    private TextView nama, nisn, kelas, nohp, tanggal_lahir, tahunmasuk, nama_wali, nohpwali, email;
    private SiswaModel siswaModel;
    private SessionManager sessionManager;
    private UtilMessage utilMessage;
    private ImageView fotoSiswa;
    private Spinner dropdown;
    private String absen;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile_siswa);

        sessionManager = new SessionManager(this);
        utilMessage = new UtilMessage(this);
        nama = findViewById(R.id.tv_namaSiswa);
        nisn = findViewById(R.id.tv_nisn);
        kelas = findViewById(R.id.tv_kelas);
        nohp = findViewById(R.id.tv_nohp);
        tanggal_lahir = findViewById(R.id.tv_tanggalLahir);
        tahunmasuk = findViewById(R.id.tv_tahunmasuk);
        nama_wali = findViewById(R.id.tv_namawali);
        nohpwali = findViewById(R.id.tv_hpwali);
        fotoSiswa = findViewById(R.id.foto_siswa);
        btnSubmit = findViewById(R.id.btn_absen);
        email = findViewById(R.id.tv_email);

        //get the spinner from the xml.
        dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"hadir", "sakit", "ijin", "absen"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                absen = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });

        siswaModel = (SiswaModel) getIntent().getExtras().get("data");

        setData(siswaModel);
    }

    private void submitData() {
        StringRequest request = new StringRequest(Request.Method.POST,
                BASE_URL + "tambah_absen.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();
                        try {
                            JSONObject jsonRespone = new JSONObject(response);

                            int status = jsonRespone.getInt("status");
                            String message = jsonRespone.getString("message");

                            if (status == 1 ){
                                Toast.makeText(DetailProfileSiswaActivity.this, message, Toast.LENGTH_SHORT).show();

                                finish();
                            }
                            else{
                                Toast.makeText(DetailProfileSiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e){
                            Toast.makeText(DetailProfileSiswaActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utilMessage.dismissProgressBar();
                Toast.makeText(DetailProfileSiswaActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("nisn", siswaModel.getNisn());
                params.put("status", absen);


                return params;
            }
        };

        utilMessage.showProgressBar("Submiting Data");
        Volley.newRequestQueue(this).add(request);
    }

    private void setData(SiswaModel siswaModel) {
        if(siswaModel != null){
            nama.setText(siswaModel.getNama());
            nisn.setText(siswaModel.getNisn());
            kelas.setText(siswaModel.getKelas());
            nohp.setText(siswaModel.getNohp());
            tahunmasuk.setText(siswaModel.getTahun_masuk());
            nama_wali.setText(siswaModel.getWali_murid());
            nohpwali.setText(siswaModel.getNohporangtua());
            tanggal_lahir.setText(siswaModel.getTanggal_lahir());
            email.setText(siswaModel.getEmail());

            Glide.with(this)
                    .load(IMAGE_URL+siswaModel.getFoto())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(fotoSiswa);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(sessionManager.getRole().equals("admin")){
            getMenuInflater().inflate(R.menu.menu_edit_delete_siswa, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                Intent intent = new Intent(this, EditSiswaActivity.class);
                intent.putExtra("data", siswaModel);

                startActivity(intent);
                break;
            case R.id.action_absensi:
                Intent intent2 = new Intent(this, AbsensiSiswaActivity.class);
                intent2.putExtra("nisn", siswaModel.getNisn());

                startActivity(intent2);
                break;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Anda yakin menghapus \"" + siswaModel.getNama() +"\"?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest request = new StringRequest(Request.Method.POST,
                                BASE_URL + "hapus_siswa.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        utilMessage.dismissProgressBar();

                                        try{
                                            JSONObject jsonResponse = new JSONObject(response);

                                            int status = jsonResponse.getInt("status");
                                            String message = jsonResponse.getString("message");

                                            Toast.makeText(DetailProfileSiswaActivity.this, message,Toast.LENGTH_SHORT).show();

                                            if(status == 0){
                                                finish();
                                            }
                                        } catch (JSONException e){
                                            Toast.makeText(DetailProfileSiswaActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        utilMessage.dismissProgressBar();
                                        Toast.makeText(DetailProfileSiswaActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("id", siswaModel.getId());
                                return params;

                            }
                        };
                        utilMessage.showProgressBar("");
                        Volley.newRequestQueue(DetailProfileSiswaActivity.this).add(request);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}