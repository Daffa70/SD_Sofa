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

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder> {
    private Context context;
    private ArrayList<InfoModel> data;
    private InfoAdapterListener infoAdapterListener;


    public interface InfoAdapterListener{
        void onItemClickListener(InfoModel infoModel);
    }

    public InfoAdapter(Context context, ArrayList<InfoModel> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<InfoModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setAdapterListener(InfoAdapterListener adapterListener) {
        this.infoAdapterListener = adapterListener;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.info_adapter, parent, false);

        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvTitle, tvAuthor, tvCreatedAt;

        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.textMaintitle);
            tvAuthor = itemView.findViewById(R.id.textIsi);
            tvCreatedAt = itemView.findViewById(R.id.texttanggal);
        }

        public void bind(final InfoModel infoModel) {
            tvTitle.setText(infoModel.getJudul());
            tvAuthor.setText(infoModel.getIsi());
            tvCreatedAt.setText(infoModel.getTanggal());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    infoAdapterListener.onItemClickListener(infoModel);
                }
            });
        }
    }
}
