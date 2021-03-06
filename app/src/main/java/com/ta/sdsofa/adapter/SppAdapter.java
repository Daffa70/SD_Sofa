package com.ta.sdsofa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ta.sdsofa.R;
import com.ta.sdsofa.model.SiswaModel;
import com.ta.sdsofa.model.SppModel;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_URL;

public class SppAdapter extends RecyclerView.Adapter<SppAdapter.SppViewHolder> {
    private Context context;
    private ArrayList<SppModel> data;
    private SppAdapterListener sppAdapterListener;

    public interface SppAdapterListener{
        void onItemClickListener(SppModel sppModel);
    }

    public SppAdapter(Context context, ArrayList<SppModel> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<SppModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setAdapterListener(SppAdapterListener adapterListener) {
        this.sppAdapterListener = adapterListener;
    }

    @NonNull
    @Override
    public SppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_spp, parent, false);

        return new SppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SppViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SppViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvNama, tvStatus, tvBulan;

        public SppViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.textViewNama);
            tvStatus = itemView.findViewById(R.id.textViewStatus);
            ivImage = itemView.findViewById(R.id.image);
            tvBulan = itemView.findViewById(R.id.bulan);
        }

        public void bind(final SppModel sppModel) {
            //tvNama.setText(siswaModel.getNama());
            //tvNisn.setText(siswaModel.getNisn());

            //Glide.with(context)
                    //.load(IMAGE_URL+siswaModel.getFoto())
                    //.placeholder(R.mipmap.ic_launcher_round)
                    //.into(ivImage);
            tvNama.setText(sppModel.getNama());
            tvStatus.setText(sppModel.getStatus_pembayaran());

            switch (sppModel.getUntuk_bulan()){
                case("1"):
                    tvBulan.setText("Januari");
                    break;
                case("2"):
                    tvBulan.setText("Febuari");
                    break;
                case("3"):
                    tvBulan.setText("Maret");
                    break;
                case("4"):
                    tvBulan.setText("April");
                    break;
                case("5"):
                    tvBulan.setText("Mei");
                    break;
                case("6"):
                    tvBulan.setText("Juni");
                    break;
                case("7"):
                    tvBulan.setText("Juli");
                    break;
                case("8"):
                    tvBulan.setText("Agustus");
                    break;
                case("9"):
                    tvBulan.setText("September");
                    break;
                case("10"):
                    tvBulan.setText("Oktober");
                    break;
                case("11"):
                    tvBulan.setText("November");
                    break;
                case("12"):
                    tvBulan.setText("Desember");
                    break;

            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sppAdapterListener.onItemClickListener(sppModel);
                }
            });
        }
    }
}
