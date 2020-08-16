package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.ta.sdsofa.R;
import com.ta.sdsofa.adapter.JadwalAdapter;
import com.ta.sdsofa.adapter.TugasAdapter;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.JadwalModel;
import com.ta.sdsofa.model.KelasRowModel;
import com.ta.sdsofa.model.SiswaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class DetailJadwalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UtilMessage utilMessage;
    private JadwalAdapter jadwalAdapter;
    private KelasRowModel kelasRowModel;
    private String  mataPelajaranActivity;
    private SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal);

        recyclerView = findViewById(R.id.rowjadwal);
        utilMessage = new UtilMessage(this);
        kelasRowModel = (KelasRowModel) getIntent().getExtras().get("data");
        mataPelajaranActivity = getIntent().getExtras().getString("hari");
        sessionManager = new SessionManager(this);
        jadwalAdapter = new JadwalAdapter(this, new ArrayList<JadwalModel>());
        if(sessionManager.getRole().equals("admin")) {
            jadwalAdapter.setAdapterListener(new JadwalAdapter.JadwalAdapterListener() {
                @Override
                public void onItemClickListener(JadwalModel jadwalModel) {
                Intent intent = new Intent(DetailJadwalActivity.this, DetailJadwalDuoActivity.class);
                intent.putExtra("data", jadwalModel);
                }
            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(jadwalAdapter);

        getData();
    }

    private void getData() {
        utilMessage.showProgressBar("Getting Info...");
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "get_matapelajaran.php?kelas="+kelasRowModel.getKelas()+"&hari="+mataPelajaranActivity,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<JadwalModel> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                JadwalModel jadwalModel = new JadwalModel();

                                jadwalModel.setId(item.getString("id"));
                                jadwalModel.setMata_pelajaran(item.getString("mata_pelajaran"));
                                jadwalModel.setGuru(item.getString("guru"));
                                jadwalModel.setJam(item.getString("jam"));
                                jadwalModel.setJam_Waktu(item.getString("jam_waktu"));


                                data.add(jadwalModel);
                            }

                            jadwalAdapter.setData(data);
                        } catch (JSONException e) {

                            Toast.makeText(DetailJadwalActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utilMessage.dismissProgressBar();
                        Toast.makeText(DetailJadwalActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(DetailJadwalActivity.this).add(request);
    }
}