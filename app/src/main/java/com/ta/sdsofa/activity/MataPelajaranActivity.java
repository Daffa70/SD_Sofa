package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ta.sdsofa.R;
import com.ta.sdsofa.model.KelasRowModel;

public class MataPelajaranActivity extends AppCompatActivity {
    private CardView senin,selasa,rabu,kamis,jumaat;
    private KelasRowModel kelasRowModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mata_pelajaran);

        senin = findViewById(R.id.senin);
        selasa = findViewById(R.id.selasa);
        rabu = findViewById(R.id.rabu);
        kamis = findViewById(R.id.kamis);
        jumaat = findViewById(R.id.jumaat);
        kelasRowModel = (KelasRowModel) getIntent().getExtras().get("data");


        senin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MataPelajaranActivity.this, DetailJadwalActivity.class);
                intent.putExtra("hari", "senin");
                intent.putExtra("data", kelasRowModel);

                startActivity(intent);
            }
        });

        selasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MataPelajaranActivity.this, DetailJadwalActivity.class);
                intent.putExtra("hari", "selasa");
                intent.putExtra("data", kelasRowModel);

                startActivity(intent);
            }
        });

        rabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MataPelajaranActivity.this, DetailJadwalActivity.class);
                intent.putExtra("hari", "rabu");
                intent.putExtra("data", kelasRowModel);

                startActivity(intent);
            }
        });

        kamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MataPelajaranActivity.this, DetailJadwalActivity.class);
                intent.putExtra("hari", "kamis");
                intent.putExtra("data", kelasRowModel);

                startActivity(intent);
            }
        });

        jumaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MataPelajaranActivity.this, DetailJadwalActivity.class);
                intent.putExtra("hari", "jumaat");
                intent.putExtra("data", kelasRowModel);

                startActivity(intent);
            }
        });
    }
}