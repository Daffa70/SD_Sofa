package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ta.sdsofa.MainActivityAdmin;
import com.ta.sdsofa.R;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class LoginActivityActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private SessionManager sessionManager;
    private UtilMessage utilMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);

        sessionManager = new SessionManager(this);
        utilMessage = new UtilMessage(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }
    private void doLogin(){
        final String username = edtUsername.getText().toString();
        final String password = edtPassword.getText().toString();
        if (username.trim().isEmpty()) {
            Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (password.trim().isEmpty()) {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            utilMessage.showProgressBar("Logging in...");

            StringRequest request = new StringRequest(Request.Method.POST,
                    BASE_URL + "login.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            utilMessage.dismissProgressBar();
                            //convert string to json object
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                int status = jsonResponse.getInt("status");
                                String message = jsonResponse.getString("message");

                                Toast.makeText(LoginActivityActivity.this, message, Toast.LENGTH_SHORT).show();
                                if (status == 0) {
                                    String userId = jsonResponse.getString("data");
                                    String role = jsonResponse.getString("role");
                                    // simpan ke session
                                    sessionManager.setUserID(userId);
                                    sessionManager.setUserRole(role);

                                    if(role.equals("siswa")){
                                        Intent intentSiswa = new Intent();
                                        startActivity(intentSiswa);
                                    }
                                    else if(role.equals("admin")){
                                        Intent intentPegawai = new Intent(LoginActivityActivity.this, MainActivityAdmin.class);
                                        startActivity(intentPegawai);
                                    }
                                    //Intent intentMain = new Intent(LoginActivityActivity.this, MainActivityAdmin.class);
                                    //startActivity(intentMain);
                                    finish();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(LoginActivityActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            utilMessage.dismissProgressBar();
                            Toast.makeText(LoginActivityActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();

                    params.put("username", username);
                    params.put("password", password);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }

    }
}