package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ta.sdsofa.R;
import com.ta.sdsofa.model.TugasModel;

public class DetailTugasActivity extends AppCompatActivity {
    private TextView namaTugas, tugas, mata_pelajaran, guru, kelas, date, deadline;
    private TugasModel tugasModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tugas);

        mata_pelajaran = findViewById(R.id.textJudul);
        tugas = findViewById(R.id.TextIsi);
        namaTugas = findViewById(R.id.textSubjek);
        guru = findViewById(R.id.tv_guru);
        kelas = findViewById(R.id.kelas);
        date = findViewById(R.id.tv_tanggal);
        deadline = findViewById(R.id.text_due);
        tugasModel = (TugasModel) getIntent().getExtras().get("data");

        setData(tugasModel);

    }

    private void setData(TugasModel tugasModel) {
        if(tugasModel != null){
            mata_pelajaran.setText(tugasModel.getMata_pelajaran());
            tugas.setText(tugasModel.getTugas());
            namaTugas.setText(tugasModel.getNamatugas());
            guru.setText(tugasModel.getGuru());
            kelas.setText(tugasModel.getKelas());
            date.setText(tugasModel.getDate());
            deadline.setText(tugasModel.getDeadline());
        }
    }
}