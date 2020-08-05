package com.ta.sdsofa.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ta.sdsofa.R;
import com.ta.sdsofa.activity.LoginActivityActivity;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.model.AdminModel;


public class ProfilAdminFragment extends Fragment {
    private Button button;
    private SessionManager sessionManager;
    private TextView nama;
    private AdminModel adminModel;

    public ProfilAdminFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.button);
        nama = view.findViewById(R.id.nama_admin);


        sessionManager = new SessionManager(getContext());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin logout? ");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sessionManager.clearEditor();
                        Intent intentLogin = new Intent(getContext(), LoginActivityActivity.class);
                        startActivity(intentLogin);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                builder.show();
            }
        });
    }
}