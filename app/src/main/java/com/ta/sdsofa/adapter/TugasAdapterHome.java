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
import com.ta.sdsofa.model.TugasModel;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_INFO;
import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_TUGAS;

public class TugasAdapterHome extends RecyclerView.Adapter<TugasAdapterHome.TugasViewHolder> {
    private Context context;
    private ArrayList<TugasModel> data;
    private TugasAdapterHomeListener tugasAdapterHomeListener;


    public interface TugasAdapterHomeListener{
        void onItemClickListener(TugasModel tugasModel);
    }

    public TugasAdapterHome(Context context, ArrayList<TugasModel> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<TugasModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setAdapterListener(TugasAdapterHomeListener adapterListener) {
        this.tugasAdapterHomeListener = adapterListener;
    }

    @NonNull
    @Override
    public TugasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_tugas_home, parent, false);

        return new TugasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TugasViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TugasViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvNamaTugas, tvSubjek, tvTanggal, tvDeadline;

        public TugasViewHolder(@NonNull View itemView) {
            super(itemView);


            tvNamaTugas = itemView.findViewById(R.id.textMaintitle);
            tvSubjek = itemView.findViewById(R.id.textIsi);
            tvDeadline = itemView.findViewById(R.id.tv_deadline);

            ivImage = itemView.findViewById(R.id.imageViewInfo);

        }

        public void bind(final TugasModel tugasModel) {
            tvNamaTugas.setText(tugasModel.getMata_pelajaran());
            tvSubjek.setText(tugasModel.getNamatugas());
            tvDeadline.setText(tugasModel.getDeadline());

            Glide.with(context)
                    .load(IMAGE_TUGAS+tugasModel.getFoto())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(ivImage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tugasAdapterHomeListener.onItemClickListener(tugasModel);
                }
            });
        }
    }

}
