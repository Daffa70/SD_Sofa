package com.ta.sdsofa.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ta.sdsofa.R;
import com.ta.sdsofa.activity.DetailSiswaActivity;
import com.ta.sdsofa.activity.MataPelajaranActivity;
import com.ta.sdsofa.activity.RowTugasActivity;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.model.KelasRowModel;

public class UserKelasFragment extends Fragment {
    private CardView rvDaftarSiswa, rvMataPelajaran, rvDaftarTugas;
    private KelasRowModel kelasRowModel;
    private SessionManager sessionManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_kelas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvMataPelajaran = view.findViewById(R.id.rl_mataPelajaran);
        rvDaftarTugas = view.findViewById(R.id.rl_daftarTugas);
        kelasRowModel = new KelasRowModel();
        sessionManager = new SessionManager(getContext());

        kelasRowModel.setKelas(sessionManager.getKelas());



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
    }
}