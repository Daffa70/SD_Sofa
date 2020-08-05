package com.ta.sdsofa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ta.sdsofa.activity.LoginActivityActivity;
import com.ta.sdsofa.helper.SessionManager;

public class CekSession extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_session);

        sessionManager = new SessionManager(this);

        if(!sessionManager.isLoggedIn()){
            Intent intentLogin = new Intent(this, LoginActivityActivity.class);
            startActivity(intentLogin);
            finish();
        }
        else{
            Intent intentAdmin = new Intent(this, MainActivityAdmin.class);
            startActivity(intentAdmin);
            finish();
        }
    }
}