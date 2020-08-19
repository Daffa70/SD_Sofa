package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.ta.sdsofa.R;
import com.ta.sdsofa.adapter.AbsenAdapter;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.AbsenModel;
import com.ta.sdsofa.model.TugasModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class AbsensiSiswaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;
    private AbsenAdapter absenAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi_siswa);

        recyclerView = findViewById(R.id.rv_rowabsen);

        utilMessage = new UtilMessage(this);
        sessionManager = new SessionManager(this);

        absenAdapter = new AbsenAdapter(this, new ArrayList<AbsenModel>());
        absenAdapter.setAdapterListener(new AbsenAdapter.AbsenAdapterListener() {
            @Override
            public void onItemClickListener(AbsenModel absenModel) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(absenAdapter);

        getData();

    }

    private void getData() {
        utilMessage.showProgressBar("Getting Info...");
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "get_absen.php?nisn="+sessionManager.getUserId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<AbsenModel> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                AbsenModel absenModel = new AbsenModel();
                                absenModel.setId(item.getString("id"));
                                absenModel.setStatus_kehadiran(item.getString("status_kehadiran"));
                                absenModel.setTanggal(item.getString("tanggal"));
                                absenModel.setNisn(item.getString("nisn"));

                                data.add(absenModel);
                            }

                            absenAdapter.setData(data);
                        } catch (JSONException e) {

                            Toast.makeText(AbsensiSiswaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utilMessage.dismissProgressBar();
                        Toast.makeText(AbsensiSiswaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(AbsensiSiswaActivity.this).add(request);
    }
}