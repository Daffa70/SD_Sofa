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
import com.ta.sdsofa.model.SiswaModel;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_URL;

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.SiswaViewHolder>{
    private Context context;
    private ArrayList<SiswaModel> data;
    private SiswaAdapterListener siswaAdapterListener;


    public interface SiswaAdapterListener{
        void onItemClickListener(SiswaModel siswaModel);
    }

    public SiswaAdapter(Context context, ArrayList<SiswaModel> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<SiswaModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setAdapterListener(SiswaAdapterListener adapterListener) {
        this.siswaAdapterListener = adapterListener;
    }

    @NonNull
    @Override
    public SiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_siswa, parent, false);

        return new SiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiswaViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SiswaViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvNama, tvNisn;

        public SiswaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.textViewNama);
            tvNisn = itemView.findViewById(R.id.textViewNISN);
            ivImage = itemView.findViewById(R.id.image);
        }

        public void bind(final SiswaModel siswaModel) {
            tvNama.setText(siswaModel.getNama());
            tvNisn.setText(siswaModel.getNisn());

            Glide.with(context)
                    .load(IMAGE_URL+siswaModel.getFoto())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(ivImage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    siswaAdapterListener.onItemClickListener(siswaModel);
                }
            });
        }
    }
}
