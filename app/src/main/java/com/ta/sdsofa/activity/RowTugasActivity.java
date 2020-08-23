package com.ta.sdsofa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ta.sdsofa.R;
import com.ta.sdsofa.adapter.SiswaAdapter;
import com.ta.sdsofa.adapter.TugasAdapter;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.KelasRowModel;
import com.ta.sdsofa.model.SiswaModel;
import com.ta.sdsofa.model.TugasModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class RowTugasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private KelasRowModel kelasRowModel;
    private TugasAdapter tugasAdapter;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_tugas);

        recyclerView = findViewById(R.id.rv_rowtugas);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        utilMessage = new UtilMessage(this);
        sessionManager = new SessionManager(this);
        kelasRowModel = (KelasRowModel) getIntent().getExtras().get("data");

        tugasAdapter = new TugasAdapter(this, new ArrayList<TugasModel>());
        tugasAdapter.setAdapterListener(new TugasAdapter.TugasAdapterListener() {
            @Override
            public void onItemClickListener(TugasModel tugasModel) {
                Intent intentt = new Intent(RowTugasActivity.this, DetailTugasActivity.class);
                intentt.putExtra("data", tugasModel);


                startActivity(intentt);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tugasAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        
        getData();

        floatingActionButton = findViewById(R.id.fab);
        if(sessionManager.getRole().equals("admin")){
            floatingActionButton.show();
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RowTugasActivity.this, TambahTugasActivity.class);
                intent.putExtra("kelas", kelasRowModel);
                startActivity(intent);
            }
        });

    }

    private void getData() {
        utilMessage.showProgressBar("Getting Info...");
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "get_tugas.php?kelas="+kelasRowModel.getKelas(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<TugasModel> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                TugasModel tugasModel = new TugasModel();
                                tugasModel.setId(item.getString("id"));
                                tugasModel.setNamatugas(item.getString("nama_tugas"));
                                tugasModel.setTugas(item.getString("tugas"));
                                tugasModel.setMata_pelajaran(item.getString("mata_pelajaran"));
                                tugasModel.setGuru(item.getString("guru"));
                                tugasModel.setKelas(item.getString("kelas"));
                                tugasModel.setDate(item.getString("date"));
                                tugasModel.setDeadline(item.getString("deadline"));
                                tugasModel.setFoto(item.getString("foto"));
                                data.add(tugasModel);
                            }

                            tugasAdapter.setData(data);
                        } catch (JSONException e) {

                            Toast.makeText(RowTugasActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utilMessage.dismissProgressBar();
                        Toast.makeText(RowTugasActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(RowTugasActivity.this).add(request);
    }
}