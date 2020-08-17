package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.JadwalModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class TambahJadwalActivity extends AppCompatActivity {
    private EditText edtMatapelajaran, edtGuru, edtJamke, edtWaktu, edtHari, edtKelas;
    private Button btnSubmit;
    private String kelas;
    private UtilMessage utilMessage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal);


        edtMatapelajaran = findViewById(R.id.edt_matapelajaran);
        edtGuru = findViewById(R.id.edt_guru);
        edtJamke = findViewById(R.id.edt_jamke);
        edtWaktu = findViewById(R.id.edt_waktu);
        edtHari = findViewById(R.id.edt_hari);
        edtKelas = findViewById(R.id.edt_kelas);
        btnSubmit = findViewById(R.id.btn_submit);

        kelas = getIntent().getExtras().getString("kelas");


        utilMessage = new UtilMessage(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });

        setData();
    }
    private void setData() {
        edtKelas.setText(kelas);
    }

    private void submitData() {
        final String matapelajaran = edtMatapelajaran.getText().toString();
        final String guru = edtGuru.getText().toString();
        final String jamke = edtJamke.getText().toString();
        final String waktu = edtWaktu.getText().toString();
        final String hari = edtHari.getText().toString();
        final String kelas = edtKelas.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST,
                BASE_URL + "tambah_jadwal.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();
                        try {
                            JSONObject jsonRespone = new JSONObject(response);

                            int status = jsonRespone.getInt("status");
                            String message = jsonRespone.getString("message");

                            if (status == 0 ){
                                Toast.makeText(TambahJadwalActivity.this, message, Toast.LENGTH_SHORT).show();

                                resetInput();
                            }
                            else{
                                Toast.makeText(TambahJadwalActivity.this, "Tambar buku gagal " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e){
                            Toast.makeText(TambahJadwalActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utilMessage.dismissProgressBar();
                Toast.makeText(TambahJadwalActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

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
    private void resetInput(){
        edtMatapelajaran.setText("");
        edtGuru.setText("");
        edtJamke.setText("");
        edtWaktu.setText("");
        edtHari.setText("");
        edtKelas.setText("");
    }
}