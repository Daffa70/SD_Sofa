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
    private CardView rvDaftarSiswa, rvMataPelajaran, rvDaftarTugas;
    private KelasRowModel kelasRowModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas);

        rvDaftarSiswa = findViewById(R.id.rl_daftarSiswa);
        rvMataPelajaran = findViewById(R.id.rl_mataPelajaran);
        rvDaftarTugas = findViewById(R.id.rl_daftarTugas);
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
    }
}