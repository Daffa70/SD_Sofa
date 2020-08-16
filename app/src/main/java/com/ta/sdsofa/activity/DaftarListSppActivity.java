package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ta.sdsofa.R;
import com.ta.sdsofa.model.KelasRowModel;

public class DaftarListSppActivity extends AppCompatActivity {
    private CardView rvSiswaLunas, rvKonfirmasi;
    private KelasRowModel kelasRowModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_list_spp);

        rvKonfirmasi = findViewById(R.id.rl_MenungguKonfirmasi);
        rvSiswaLunas = findViewById(R.id.rl_SiswaLunas);
        kelasRowModel = (KelasRowModel) getIntent().getExtras().get("data");

        rvKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RowSppKonfirmasi.class);
                intent.putExtra("data", kelasRowModel);
                intent.putExtra("bulan", "now");
                intent.putExtra("status", "&status=menunggu_konfirmasi");

                startActivity(intent);
            }
        });

        rvSiswaLunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DaftarBulanActivity.class);
                intent.putExtra("data", kelasRowModel);

                startActivity(intent);
            }
        });
    }
}