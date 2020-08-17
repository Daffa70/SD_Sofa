package com.ta.sdsofa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.print.PageRange;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ta.sdsofa.R;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.InfoModel;
import com.ta.sdsofa.model.JadwalModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class DetailJadwalDuoActivity extends AppCompatActivity {
    private EditText edtMatapelajaran, edtGuru, edtJamke, edtWaktu, edtHari, edtKelas;
    private Button btnSubmit;
    private String kelas;
    private UtilMessage utilMessage;
    private JadwalModel jadwalModel;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal_duo);

        edtMatapelajaran = findViewById(R.id.edt_matapelajaran);
        edtGuru = findViewById(R.id.edt_guru);
        edtJamke = findViewById(R.id.edt_jamke);
        edtWaktu = findViewById(R.id.edt_waktu);
        edtHari = findViewById(R.id.edt_hari);
        edtKelas = findViewById(R.id.edt_kelas);
        btnSubmit = findViewById(R.id.btn_submit);

        kelas = getIntent().getExtras().getString("kelas");
        jadwalModel = (JadwalModel) getIntent().getExtras().get("data");

        utilMessage = new UtilMessage(this);
        sessionManager = new SessionManager(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });

        setData();
    }

    private void setData() {
        edtMatapelajaran.setText(jadwalModel.getMata_pelajaran());
        edtGuru.setText(jadwalModel.getGuru());
        edtJamke.setText(jadwalModel.getJam());
        edtWaktu.setText(jadwalModel.getJam_Waktu());
        edtHari.setText(jadwalModel.getHari());
        edtKelas.setText(jadwalModel.getKelas());
    }

    private void submitData() {
        final String matapelajaran = edtMatapelajaran.getText().toString();
        final String guru = edtGuru.getText().toString();
        final String jamke = edtJamke.getText().toString();
        final String waktu = edtWaktu.getText().toString();
        final String hari = edtHari.getText().toString();
        final String kelas = edtKelas.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST,
                BASE_URL + "edt_jadwal.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();
                        try {
                            JSONObject jsonRespone = new JSONObject(response);

                            int status = jsonRespone.getInt("status");
                            String message = jsonRespone.getString("message");

                            if (status == 0 ){
                                Toast.makeText(DetailJadwalDuoActivity.this, message, Toast.LENGTH_SHORT).show();


                            }
                            else{
                                Toast.makeText(DetailJadwalDuoActivity.this, "Tambar buku gagal " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e){
                            Toast.makeText(DetailJadwalDuoActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utilMessage.dismissProgressBar();
                Toast.makeText(DetailJadwalDuoActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                params.put("id", jadwalModel.getId());
                params.put("mata_pelajaran", matapelajaran);
                params.put("guru", guru);
                params.put("jamke", jamke);
                params.put("waktu", waktu);
                params.put("hari", hari);
                params.put("kelas", kelas);

                return params;
            }
        };

        utilMessage.showProgressBar("Submiting Data");
        Volley.newRequestQueue(this).add(request);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(sessionManager.getRole().equals("admin")){
            getMenuInflater().inflate(R.menu.menu_delete_jadwal, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            if (item.getItemId() == R.id.action_delete){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Anda yakin menghapus \"" + jadwalModel.getMata_pelajaran() +"\"?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest request = new StringRequest(Request.Method.POST,
                                BASE_URL + "hapus_jadwal.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        utilMessage.dismissProgressBar();

                                        try{
                                            JSONObject jsonResponse = new JSONObject(response);

                                            int status = jsonResponse.getInt("status");
                                            String message = jsonResponse.getString("message");

                                            Toast.makeText(DetailJadwalDuoActivity.this, message,Toast.LENGTH_SHORT).show();

                                            if(status == 0){
                                                finish();

                                            }
                                        } catch (JSONException e){
                                            Toast.makeText(DetailJadwalDuoActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        utilMessage.dismissProgressBar();
                                        Toast.makeText(DetailJadwalDuoActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("id", jadwalModel.getId());
                                return params;

                            }
                        };
                        utilMessage.showProgressBar("");
                        Volley.newRequestQueue(DetailJadwalDuoActivity.this).add(request);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}