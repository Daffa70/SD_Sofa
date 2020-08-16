package com.ta.sdsofa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.ta.sdsofa.R;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.SppModel;

import org.w3c.dom.Text;

import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_URL;

public class DetailSppActivity extends AppCompatActivity {
    private TextView nama, nisn, untuk_bulan,tanggal, status, kelas;
    private ImageView ivBukti;
    private Button btnKonfirmasi;
    private UtilMessage utilMessage;
    private SppModel sppModel;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spp);

        sppModel = (SppModel) getIntent().getExtras().get("data");
        sessionManager = new SessionManager(this);
        utilMessage = new UtilMessage(this);

        nama = findViewById(R.id.tv_namaSiswa);
        nisn = findViewById(R.id.tv_nisn);
        untuk_bulan = findViewById(R.id.tv_bulan);
        kelas = findViewById(R.id.tv_kelas);
        tanggal = findViewById(R.id.tv_tanggal);
        status = findViewById(R.id.tv_status);

        ivBukti = findViewById(R.id.image_bukti);

        setData(sppModel);

    }

    private void setData(SppModel sppModel) {
        nama.setText(sppModel.getNama());
        nisn.setText(sppModel.getNisn());
        untuk_bulan.setText(sppModel.getUntuk_bulan());
        kelas.setText(sppModel.getKelas());
        tanggal.setText(sppModel.getTgl_pembayaran());
        status.setText(sppModel.getStatus_pembayaran());

        Glide.with(this)
                .load(IMAGE_URL+sppModel.getBukti())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(ivBukti);
    }
}