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
import com.ta.sdsofa.model.AbsenModel;
import com.ta.sdsofa.model.InfoModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AbsenAdapter extends RecyclerView.Adapter<AbsenAdapter.AbsenViewHolder> {
    private Context context;
    private ArrayList<AbsenModel> data;
    private AbsenAdapterListener absenAdapterListener;

    public interface AbsenAdapterListener{
        void onItemClickListener(AbsenModel absenModel);
    }

    public AbsenAdapter(Context context, ArrayList<AbsenModel> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<AbsenModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setAdapterListener(AbsenAdapterListener adapterListener) {
        this.absenAdapterListener = adapterListener;
    }

    @NonNull
    @Override
    public AbsenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_absen, parent, false);

        return new AbsenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsenViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AbsenViewHolder extends RecyclerView.ViewHolder {
        private TextView status, tanggal;

        public AbsenViewHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            tanggal = itemView.findViewById(R.id.tanggal);
        }

        public void bind(final AbsenModel absenModel) {
            tanggal.setText(absenModel.getStatus_kehadiran());
            status.setText(absenModel.getTanggal());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    absenAdapterListener.onItemClickListener(absenModel);
                }
            });
        }
    }
}
