package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.ta.sdsofa.R;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.InfoModel;

public class InfoDetailActivity extends AppCompatActivity {
    private TextView tvJudul,tvSubjek,tvIsi;
    private InfoModel infoModel;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);
        
        imageView = findViewById(R.id.imageViewInfo);
        tvJudul = findViewById(R.id.textJudul);
        tvSubjek = findViewById(R.id.textSubjek);
        tvIsi = findViewById(R.id.TextIsi);
        utilMessage = new UtilMessage(this);
        infoModel = (InfoModel) getIntent().getExtras().get("data");
        
        setData(infoModel);
    }

    private void setData(InfoModel infoModel) {
        if(infoModel != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(infoModel.getJudul());
            }
            tvJudul.setText(infoModel.getJudul());
            tvSubjek.setText(infoModel.getSubjek());
            tvIsi.setText(infoModel.getIsi());
        }
    }
}