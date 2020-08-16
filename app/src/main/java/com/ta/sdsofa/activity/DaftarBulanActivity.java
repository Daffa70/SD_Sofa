package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ta.sdsofa.R;
import com.ta.sdsofa.model.KelasRowModel;

public class DaftarBulanActivity extends AppCompatActivity {
    private CardView januari, febuari, maret, april, mei, juni, juli, agustus, september, oktober, novomber, desember;
    private KelasRowModel kelasRowModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_bulan);

        januari = findViewById(R.id.januari);
        febuari = findViewById(R.id.febuari);
        maret = findViewById(R.id.maret);
        april = findViewById(R.id.april);
        mei = findViewById(R.id.mei);
        juni = findViewById(R.id.juni);
        juli = findViewById(R.id.juli);
        agustus = findViewById(R.id.agustus);
        september = findViewById(R.id.september);
        oktober = findViewById(R.id.oktober);
        novomber = findViewById(R.id.november);
        desember = findViewById(R.id.desember);

        kelasRowModel = (KelasRowModel) getIntent().getExtras().get("data");

        januari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "1");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        febuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "2");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        maret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "3");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        april.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "4");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        mei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "5");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        juni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "6");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        juli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "7");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        agustus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "8");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        september.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "9");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        oktober.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "10");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        novomber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "11");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        desember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarBulanActivity.this, RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "12");
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

    }
}