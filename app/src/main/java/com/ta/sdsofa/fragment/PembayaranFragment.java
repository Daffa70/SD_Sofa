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
import com.ta.sdsofa.activity.RowSppKonfirmasi;
import com.ta.sdsofa.helper.SessionManager;


public class PembayaranFragment extends Fragment {
    private CardView rvHistory, rvTagihan;
    private SessionManager sessionManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pembayaran, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvHistory = view.findViewById(R.id.rl_history);
        rvTagihan = view.findViewById(R.id.rl_spp_bulan_ini);

        sessionManager = new SessionManager(getContext());

        rvTagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RowSppKonfirmasi.class);
                intent.putExtra("kelas", sessionManager.getKelas());
                intent.putExtra("web", "get_spp_user.php?bulan=");
                intent.putExtra("bulan", "a");
                intent.putExtra("id", "&nisn="+sessionManager.getUserId());
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });

        rvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RowSppKonfirmasi.class);
                intent.putExtra("kelas", sessionManager.getKelas());
                intent.putExtra("web", "get_spp_user.php?");
                intent.putExtra("bulan", "");
                intent.putExtra("id", "&nisn="+sessionManager.getUserId());
                intent.putExtra("status", "");

                startActivity(intent);
            }
        });
    }
}