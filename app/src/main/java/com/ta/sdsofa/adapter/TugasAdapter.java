package com.ta.sdsofa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ta.sdsofa.R;
import com.ta.sdsofa.model.SiswaModel;
import com.ta.sdsofa.model.TugasModel;

import java.util.ArrayList;

public class TugasAdapter extends RecyclerView.Adapter<TugasAdapter.TugasViewHolder> {
    private Context context;
    private ArrayList<TugasModel> data;
    private TugasAdapterListener tugasAdapterListener;


    public interface TugasAdapterListener{
        void onItemClickListener(TugasModel tugasModel);
    }

    public TugasAdapter(Context context, ArrayList<TugasModel> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<TugasModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setAdapterListener(TugasAdapterListener adapterListener) {
        this.tugasAdapterListener = adapterListener;
    }

    @NonNull
    @Override
    public TugasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_tugas, parent, false);

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
            tvTanggal = itemView.findViewById(R.id.texttanggal);
            tvSubjek = itemView.findViewById(R.id.textIsi);
            tvDeadline = itemView.findViewById(R.id.tv_deadline);

        }

        public void bind(final TugasModel tugasModel) {
            tvNamaTugas.setText(tugasModel.getMata_pelajaran());
            tvSubjek.setText(tugasModel.getNamatugas());
            tvTanggal.setText(tugasModel.getDate());
            tvDeadline.setText(tugasModel.getDeadline());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tugasAdapterListener.onItemClickListener(tugasModel);
                }
            });
        }
    }
}
