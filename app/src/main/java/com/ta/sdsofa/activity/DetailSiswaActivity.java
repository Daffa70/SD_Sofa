package com.ta.sdsofa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.ta.sdsofa.R;
import com.ta.sdsofa.adapter.InfoAdapter;
import com.ta.sdsofa.adapter.SiswaAdapter;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.KelasRowModel;
import com.ta.sdsofa.model.SiswaModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class DetailSiswaActivity extends AppCompatActivity {
    private RecyclerView recyclerViewSiswa;
    private SiswaAdapter siswaAdapter;
    private UtilMessage utilMessage;
    private KelasRowModel kelasRowModel;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_siswa);

        recyclerViewSiswa = findViewById(R.id.rv_list_siswa);
        utilMessage = new UtilMessage(this);
        sessionManager = new SessionManager(this);
        kelasRowModel = (KelasRowModel) getIntent().getExtras().get("data");
        siswaAdapter = new SiswaAdapter(this, new ArrayList<SiswaModel>());
        siswaAdapter.setAdapterListener(new SiswaAdapter.SiswaAdapterListener() {
            @Override
            public void onItemClickListener(SiswaModel siswaModel) {
                Intent intent = new Intent(DetailSiswaActivity.this, DetailProfileSiswaActivity.class);
                intent.putExtra("data", siswaModel);

                startActivity(intent);

            }
        });
        recyclerViewSiswa.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSiswa.setAdapter(siswaAdapter);

        getData();
    }


    private void getData() {
        utilMessage.showProgressBar("Getting Info...");
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "get_siswa.php?kelas="+kelasRowModel.getKelas(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<SiswaModel> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                SiswaModel siswaModel = new SiswaModel();
                                siswaModel.setId(item.getString("id"));
                                siswaModel.setNisn(item.getString("nisn"));
                                siswaModel.setNama(item.getString("nama"));
                                siswaModel.setAlamat(item.getString("alamat"));
                                siswaModel.setNohp(item.getString("nohp"));
                                siswaModel.setNohporangtua(item.getString("nohporangtua"));
                                siswaModel.setTanggal_lahir(item.getString("tanggal_lahir"));
                                siswaModel.setTahun_masuk(item.getString("tahun_masuk"));
                                siswaModel.setWali_murid(item.getString("wali_murid"));
                                siswaModel.setKelas(item.getString("kelas"));
                                siswaModel.setFoto(item.getString("foto"));
                                data.add(siswaModel);
                            }

                            siswaAdapter.setData(data);
                        } catch (JSONException e) {

                            Toast.makeText(DetailSiswaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utilMessage.dismissProgressBar();
                        Toast.makeText(DetailSiswaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(DetailSiswaActivity.this).add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(sessionManager.getRole().equals("admin")){
            getMenuInflater().inflate(R.menu.menu_tambah_siswa, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_tambah){
            Intent intent = new Intent(this, TambahSiswaActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.action_refresh){
            getData();
        }
        return super.onOptionsItemSelected(item);
    }
}