package com.ta.sdsofa.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.ta.sdsofa.R;
import com.ta.sdsofa.activity.DetailKelasActivity;
import com.ta.sdsofa.adapter.RowKelasAdapter;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.InfoModel;
import com.ta.sdsofa.model.KelasRowModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;

public class KelasFragment extends Fragment {
    private RecyclerView recyclerViewKelas;
    private RowKelasAdapter rowKelasAdapter;
    private UtilMessage utilMessage;

    public KelasFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kelas, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewKelas = view.findViewById(R.id.rv_list_kelas);
        utilMessage = new UtilMessage(getActivity());
        rowKelasAdapter = new RowKelasAdapter(getContext(), new ArrayList<KelasRowModel>());
        rowKelasAdapter.setAdapterListener(new RowKelasAdapter.RowKelasAdapterListener() {
            @Override
            public void onItemClickListener(KelasRowModel kelasRowModel) {
                Intent intentKelas = new Intent(getContext(), DetailKelasActivity.class);
                intentKelas.putExtra("data", kelasRowModel);

                startActivity(intentKelas);
            }
        });

        recyclerViewKelas.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewKelas.setAdapter(rowKelasAdapter);

        getData();
    }

    private void getData() {
        utilMessage.showProgressBar("Getting Info...");
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "get_kelas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<KelasRowModel> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                KelasRowModel kelasRowModel = new KelasRowModel();
                                kelasRowModel.setId(item.getString("id"));
                                kelasRowModel.setKelas(item.getString("kelas"));
                                kelasRowModel.setWali_kelas(item.getString("wali_kelas"));

                                data.add(kelasRowModel);
                            }

                            rowKelasAdapter.setData(data);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utilMessage.dismissProgressBar();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getContext()).add(request);

    }


}