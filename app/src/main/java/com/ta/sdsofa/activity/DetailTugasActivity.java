package com.ta.sdsofa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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
import com.ta.sdsofa.model.TugasModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class DetailTugasActivity extends AppCompatActivity {
    private TextView namaTugas, tugas, mata_pelajaran, guru, kelas, date, deadline;
    private TugasModel tugasModel;
    private SessionManager sessionManager;
    private UtilMessage utilMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tugas);

        mata_pelajaran = findViewById(R.id.textJudul);
        sessionManager = new SessionManager(this);
        utilMessage = new UtilMessage(this);
        tugas = findViewById(R.id.TextIsi);
        namaTugas = findViewById(R.id.textSubjek);
        guru = findViewById(R.id.tv_guru);
        kelas = findViewById(R.id.kelas);
        date = findViewById(R.id.tv_tanggal);
        deadline = findViewById(R.id.text_due);
        tugasModel = (TugasModel) getIntent().getExtras().get("data");

        setData(tugasModel);

    }

    private void setData(TugasModel tugasModel) {
        if(tugasModel != null){
            mata_pelajaran.setText(tugasModel.getMata_pelajaran());
            tugas.setText(tugasModel.getTugas());
            namaTugas.setText(tugasModel.getNamatugas());
            guru.setText(tugasModel.getGuru());
            kelas.setText(tugasModel.getKelas());
            date.setText(tugasModel.getDate());
            deadline.setText(tugasModel.getDeadline());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (sessionManager.getRole().equals("admin")){
            getMenuInflater().inflate(R.menu.menu_edit_delete_info, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                Intent intent = new Intent(this, EditTugasActivity.class);
                intent.putExtra("data", tugasModel);

                startActivity(intent);
                break;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Anda yakin menghapus \"" + tugasModel.getNamatugas() +"\"?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest request = new StringRequest(Request.Method.POST,
                                BASE_URL + "hapus_tugas.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        utilMessage.dismissProgressBar();

                                        try{
                                            JSONObject jsonResponse = new JSONObject(response);

                                            int status = jsonResponse.getInt("status");
                                            String message = jsonResponse.getString("message");

                                            Toast.makeText(DetailTugasActivity.this, message,Toast.LENGTH_SHORT).show();

                                            if(status == 0){
                                                finish();
                                            }
                                        } catch (JSONException e){
                                            Toast.makeText(DetailTugasActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        utilMessage.dismissProgressBar();
                                        Toast.makeText(DetailTugasActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("id", tugasModel.getId());
                                return params;

                            }
                        };
                        utilMessage.showProgressBar("");
                        Volley.newRequestQueue(DetailTugasActivity.this).add(request);

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