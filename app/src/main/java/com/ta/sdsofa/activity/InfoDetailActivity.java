package com.ta.sdsofa.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.ta.sdsofa.R;
import com.ta.sdsofa.fragment.InfoFragment;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.InfoModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class InfoDetailActivity extends AppCompatActivity {
    private TextView tvJudul,tvSubjek,tvIsi;
    private InfoModel infoModel;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);
        
        imageView = findViewById(R.id.imageViewInfo);
        tvJudul = findViewById(R.id.textJudul);
        tvSubjek = findViewById(R.id.textSubjek);
        tvIsi = findViewById(R.id.TextIsi);
        sessionManager = new SessionManager(this);
        utilMessage = new UtilMessage(this);
        infoModel = (InfoModel) getIntent().getExtras().get("data");
        
        setData(infoModel);
    }

    private void setData(InfoModel infoModel) {
        if(infoModel != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(infoModel.getJudul());
            }
            tvJudul.setText(infoModel.getJudul());
            tvSubjek.setText(infoModel.getSubjek());
            tvIsi.setText(infoModel.getIsi());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(sessionManager.getRole().equals("admin")){
            getMenuInflater().inflate(R.menu.menu_edit_delete_info, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                Intent intent = new Intent(this,  EditInfoActivity.class);
                intent.putExtra("data", infoModel);

                startActivity(intent);
                break;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Anda yakin menghapus \"" + infoModel.getJudul() +"\"?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest request = new StringRequest(Request.Method.POST,
                                BASE_URL + "hapus_berita.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        utilMessage.dismissProgressBar();

                                        try{
                                            JSONObject jsonResponse = new JSONObject(response);

                                            int status = jsonResponse.getInt("status");
                                            String message = jsonResponse.getString("message");

                                            Toast.makeText(InfoDetailActivity.this, message,Toast.LENGTH_SHORT).show();

                                            if(status == 0){
                                                finish();
                                            }
                                        } catch (JSONException e){
                                            Toast.makeText(InfoDetailActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        utilMessage.dismissProgressBar();
                                        Toast.makeText(InfoDetailActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("id", infoModel.getId());
                                return params;

                            }
                        };
                        utilMessage.showProgressBar("");
                        Volley.newRequestQueue(InfoDetailActivity.this).add(request);
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