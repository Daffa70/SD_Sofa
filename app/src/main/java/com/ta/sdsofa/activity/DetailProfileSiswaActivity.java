package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ta.sdsofa.R;
import com.ta.sdsofa.model.SiswaModel;

public class DetailProfileSiswaActivity extends AppCompatActivity {
    private TextView nama, nisn, kelas, nohp, tanggal_lahir, tahunmasuk, nama_wali, nohpwali;
    private SiswaModel siswaModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile_siswa);
        nama = findViewById(R.id.tv_namaSiswa);
        nisn = findViewById(R.id.tv_nisn);
        kelas = findViewById(R.id.tv_kelas);
        nohp = findViewById(R.id.tv_nohp);
        tanggal_lahir = findViewById(R.id.tv_tanggalLahir);
        tahunmasuk = findViewById(R.id.tv_tahunmasuk);
        nama_wali = findViewById(R.id.tv_namawali);
        nohpwali = findViewById(R.id.tv_hpwali);

        siswaModel = (SiswaModel) getIntent().getExtras().get("data");

        setData(siswaModel);
    }

    private void setData(SiswaModel siswaModel) {
    if(siswaModel != null){
        nama.setText(siswaModel.getNama());
        nisn.setText(siswaModel.getNisn());
        kelas.setText(siswaModel.getKelas());
        nohp.setText(siswaModel.getNohp());
        tahunmasuk.setText(siswaModel.getTahun_masuk());
        nama_wali.setText(siswaModel.getWali_murid());
        nohpwali.setText(siswaModel.getNohporangtua());
        tanggal_lahir.setText(siswaModel.getTanggal_lahir());
    }
    }
}