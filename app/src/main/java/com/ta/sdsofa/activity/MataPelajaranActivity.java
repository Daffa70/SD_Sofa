package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ta.sdsofa.R;

public class MataPelajaranActivity extends AppCompatActivity {
    private CardView senin,selasa,rabu,kamis,jumaat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mata_pelajaran);

        senin = findViewById(R.id.senin);
        selasa = findViewById(R.id.selasa);
        rabu = findViewById(R.id.rabu);
        kamis = findViewById(R.id.kamis);
        jumaat = findViewById(R.id.jumaat);

        senin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MataPelajaranActivity.this, DetailJadwalActivity.class);
                intent.putExtra("hari", "senin");

                startActivity(intent);
            }
        });

        selasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MataPelajaranActivity.this, DetailJadwalActivity.class);
                intent.putExtra("hari", "selasa");

                startActivity(intent);
            }
        });

        rabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MataPelajaranActivity.this, DetailJadwalActivity.class);
                intent.putExtra("hari", "rabu");

                startActivity(intent);
            }
        });

        kamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MataPelajaranActivity.this, DetailJadwalActivity.class);
                intent.putExtra("hari", "kamis");

                startActivity(intent);
            }
        });

        jumaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MataPelajaranActivity.this, DetailJadwalActivity.class);
                intent.putExtra("hari", "jumaat");

                startActivity(intent);
            }
        });
    }
}