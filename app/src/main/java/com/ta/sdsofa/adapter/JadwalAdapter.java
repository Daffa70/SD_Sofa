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
import com.ta.sdsofa.model.JadwalModel;
import com.ta.sdsofa.model.KelasRowModel;

import java.util.ArrayList;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder> {
    private Context context;
    private ArrayList<JadwalModel> data;
    private JadwalAdapterListener jadwalAdapterListener;

    public interface JadwalAdapterListener{
        void onItemClickListener(JadwalModel jadwalModel);
    }

    public JadwalAdapter(Context context, ArrayList<JadwalModel> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<JadwalModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setAdapterListener(JadwalAdapter.JadwalAdapterListener adapterListener) {
        this.jadwalAdapterListener = adapterListener;
    }

    @NonNull
    @Override
    public JadwalAdapter.JadwalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_jadwal, parent, false);

        return new JadwalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalAdapter.JadwalViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class JadwalViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvMataPelajaran, tvGuru, tvJamKe, tvJam;

        public JadwalViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMataPelajaran = itemView.findViewById(R.id.textViewNama);
            tvGuru = itemView.findViewById(R.id.textViewGuru);
            tvJamKe = itemView.findViewById(R.id.tvJamke);
            tvJam = itemView.findViewById(R.id.tvjam);

        }

        public void bind(final JadwalModel jadwalModel) {
            tvMataPelajaran.setText(jadwalModel.getMata_pelajaran());
            tvGuru.setText(jadwalModel.getGuru());
            tvJam.setText(jadwalModel.getJam_Waktu());
            tvJamKe.setText(jadwalModel.getJam());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jadwalAdapterListener.onItemClickListener(jadwalModel);
                }
            });
        }
    }
}
