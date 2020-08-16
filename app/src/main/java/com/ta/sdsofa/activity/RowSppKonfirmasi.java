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
import com.ta.sdsofa.adapter.SppAdapter;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.KelasRowModel;
import com.ta.sdsofa.model.SppModel;
import com.ta.sdsofa.model.TugasModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class RowSppKonfirmasi extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SppAdapter sppAdapter;
    private KelasRowModel kelasRowModel;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;
    private String bulan, status_pembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_spp_konfirmasi);

        recyclerView = findViewById(R.id.rv_rowSpp);
        utilMessage = new UtilMessage(this);
        sessionManager = new SessionManager(this);
        kelasRowModel = (KelasRowModel) getIntent().getExtras().get("data");
        bulan = getIntent().getExtras().getString("bulan");
        status_pembayaran = getIntent().getExtras().getString("status_pembayaran");

        sppAdapter = new SppAdapter(this, new ArrayList<SppModel>());
        sppAdapter.setAdapterListener(new SppAdapter.SppAdapterListener() {
            @Override
            public void onItemClickListener(SppModel sppModel) {
                Intent intent = new Intent(RowSppKonfirmasi.this, DetailSppActivity.class);
                intent.putExtra("data", sppModel);

                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sppAdapter);

        getData();
    }

    private void getData() {
        utilMessage.showProgressBar("Getting Info...");
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "get_spp.php?bulan="+bulan+"&kelas="+kelasRowModel.getKelas()+"&status=menunggu_konfirmasi",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<SppModel> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);
                                SppModel sppModel = new SppModel();

                                sppModel.setId(item.getString("id"));
                                sppModel.setNisn(item.getString("nisn"));
                                sppModel.setNama(item.getString("nama_siswa"));
                                sppModel.setKelas(item.getString("kelas"));
                                sppModel.setUntuk_bulan(item.getString("untuk_bulan"));
                                sppModel.setTgl_pembayaran(item.getString("tgl_pembayaran"));
                                sppModel.setBukti(item.getString("bukti"));
                                sppModel.setStatus_pembayaran(item.getString("status_pembayaran"));
                                data.add(sppModel);
                            }

                            sppAdapter.setData(data);
                        } catch (JSONException e) {

                            Toast.makeText(RowSppKonfirmasi.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utilMessage.dismissProgressBar();
                        Toast.makeText(RowSppKonfirmasi.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(RowSppKonfirmasi.this).add(request);
    }
}