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
import com.ta.sdsofa.model.InfoModel;
import com.ta.sdsofa.model.KelasRowModel;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.ICON_KELAS;
import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_URL;

public class RowKelasAdapter extends RecyclerView.Adapter<RowKelasAdapter.RowKelasViewHolder>{
    private Context context;
    private ArrayList<KelasRowModel> data;
    private RowKelasAdapterListener rowKelasAdapterListener;


    public interface RowKelasAdapterListener{
        void onItemClickListener(KelasRowModel kelasRowModel);
    }

    public RowKelasAdapter(Context context, ArrayList<KelasRowModel> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<KelasRowModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setAdapterListener(RowKelasAdapter.RowKelasAdapterListener adapterListener) {
        this.rowKelasAdapterListener = adapterListener;
    }

    @NonNull
    @Override
    public RowKelasAdapter.RowKelasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_kelas, parent, false);

        return new RowKelasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowKelasAdapter.RowKelasViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RowKelasViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvWaliKelas, tvKelas;

        public RowKelasViewHolder(@NonNull View itemView) {
            super(itemView);

            tvKelas = itemView.findViewById(R.id.textView1);
            tvWaliKelas = itemView.findViewById(R.id.textView2);
            ivImage = itemView.findViewById(R.id.image);

        }

        public void bind(final KelasRowModel kelasRowModel) {
            tvKelas.setText(kelasRowModel.getKelas());
            tvWaliKelas.setText(kelasRowModel.getWali_kelas());

            Glide.with(context)
                    .load(ICON_KELAS+kelasRowModel.getFoto())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(ivImage);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rowKelasAdapterListener.onItemClickListener(kelasRowModel);
                }
            });
        }
    }
}
