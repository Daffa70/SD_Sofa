package com.ta.sdsofa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ta.sdsofa.activity.LoginActivityActivity;
import com.ta.sdsofa.fragment.HomeFragment;
import com.ta.sdsofa.fragment.HomeUserFragment;
import com.ta.sdsofa.fragment.InfoFragment;
import com.ta.sdsofa.fragment.KelasFragment;
import com.ta.sdsofa.fragment.PembayaranFragment;
import com.ta.sdsofa.fragment.ProfilAdminFragment;
import com.ta.sdsofa.fragment.UserKelasFragment;
import com.ta.sdsofa.helper.SessionManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                setPage(menuItem.getItemId());
                return true;
            }
        });

        if(savedInstanceState == null){
            setPage(R.id.navigation_home);
        }
    }

    private void setPage(int itemId) {
        String title = "";
        Fragment fragment = null;

        switch (itemId){
            case R.id.navigation_home:
                title = "Home";
                fragment = new HomeUserFragment();
                break;
            case R.id.navigation_kelas:
                title = "Kelas";
                fragment = new UserKelasFragment();
                break;
            case R.id.navigation_info:
                title = "Info";
                fragment = new InfoFragment();
                break;
            case R.id.navigation_pembayaran:
                title = "Pembayaran";
                fragment = new PembayaranFragment();
                break;
            case R.id.navigation_user:
                title = "Profile";
                fragment = new ProfilAdminFragment();
                break;
        }
        MainActivity.this.getSupportActionBar().setTitle(title);

        //set contenct fragment
        if(fragment != null){
            MainActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_content, fragment)
                    .commit();
        }
    }

}