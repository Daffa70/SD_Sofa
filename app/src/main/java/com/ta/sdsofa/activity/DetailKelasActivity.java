package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.ta.sdsofa.R;
import com.ta.sdsofa.model.KelasRowModel;

public class DetailKelasActivity extends AppCompatActivity {
    private CardView rvDaftarSiswa, rvMataPelajaran, rvDaftarTugas, rvSpp, rvEdit;
    private KelasRowModel kelasRowModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas);

        rvDaftarSiswa = findViewById(R.id.rl_daftarSiswa);
        rvMataPelajaran = findViewById(R.id.rl_mataPelajaran);
        rvDaftarTugas = findViewById(R.id.rl_daftarTugas);
        rvEdit = findViewById(R.id.rl_setting);
        rvSpp = findViewById(R.id.rl_daftarSpp);
        kelasRowModel = (KelasRowModel) getIntent().getExtras().get("data");

        rvDaftarSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSiswa = new Intent(view.getContext(), DetailSiswaActivity.class);
                intentSiswa.putExtra("data", kelasRowModel);

                startActivity(intentSiswa);
            }
        });
        rvDaftarTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTugas = new Intent(view.getContext(), RowTugasActivity.class);
                intentTugas.putExtra("data", kelasRowModel);

                startActivity(intentTugas);
            }
        });
        rvMataPelajaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMataPelajaran = new Intent(view.getContext(), MataPelajaranActivity.class);
                intentMataPelajaran.putExtra("data", kelasRowModel);

                startActivity(intentMataPelajaran);
            }
        });
        rvSpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSpp = new Intent(view.getContext(), DaftarListSppActivity.class);
                intentSpp.putExtra("data", kelasRowModel);

                startActivity(intentSpp);
            }
        });
        rvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditKelasActivity.class);
                intent.putExtra("data", kelasRowModel);

                startActivity(intent);
            }
        });
    }
}