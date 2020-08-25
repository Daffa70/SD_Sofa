package com.ta.sdsofa.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ta.sdsofa.R;
import com.ta.sdsofa.activity.InfoDetailActivity;
import com.ta.sdsofa.adapter.InfoAdapterHome;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.AdminModel;
import com.ta.sdsofa.model.InfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;
import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_ADMIN;


public class HomeFragment extends Fragment {
    private ImageView imgProfile;
    private TextView nama;
    private SessionManager sessionManager;
    private UtilMessage utilMessage;
    private AdminModel adminModel;
    private InfoAdapterHome infoAdapterHome;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvInfo;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgProfile = view.findViewById(R.id.imageViewProfile);
        nama = view.findViewById(R.id.tv_nama);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        rvInfo = view.findViewById(R.id.rv_info);

        sessionManager = new SessionManager(getContext());
        utilMessage = new UtilMessage(getActivity());

        infoAdapterHome = new InfoAdapterHome(getContext(), new ArrayList<InfoModel>());
        infoAdapterHome.setAdapterListener(new InfoAdapterHome.InfoAdapterHomeListener() {
            @Override
            public void onItemClickListener(InfoModel infoModel) {
                Intent intent = new Intent(getContext(), InfoDetailActivity.class);
                intent.putExtra("data", infoModel);

                startActivity(intent);
            }
        });
        rvInfo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvInfo.setAdapter(infoAdapterHome);

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                getDataInfo();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        getData();
        getDataInfo();

    }

    private void getDataInfo() {
        utilMessage.showProgressBar("Getting Info...");
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "get_info.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<InfoModel> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                InfoModel infoModel = new InfoModel();
                                infoModel.setId(item.getString("id"));
                                infoModel.setJudul(item.getString("judul"));
                                infoModel.setIsi(item.getString("isi"));
                                infoModel.setTanggal(item.getString("tanggal"));
                                infoModel.setSubjek(item.getString("subjek"));
                                infoModel.setFoto(item.getString("foto"));

                                data.add(infoModel);
                            }

                            infoAdapterHome.setData(data);

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

    private void getData(){
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "get_profile.php?id="+sessionManager.getUserId()+"&role="+sessionManager.getRole(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<AdminModel> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                AdminModel adminModel = new AdminModel();
                                adminModel.setId(item.getString("id"));
                                adminModel.setAlamat(item.getString("alamat"));
                                adminModel.setJabatan(item.getString("jabatan"));
                                adminModel.setMatapelajaran(item.getString("mata_pelajaran"));
                                adminModel.setNama(item.getString("nama"));
                                adminModel.setNohp(item.getString("nohp"));
                                adminModel.setFoto(item.getString("foto"));
                                nama.setText(adminModel.getNama());

                                Glide.with(getContext()).asBitmap().load(IMAGE_ADMIN+adminModel.getFoto()).into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        imgProfile.setImageBitmap(resource);
                                    }
                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });

                                data.add(adminModel);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utilMessage.dismissProgressBar();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getContext()).add(request);
    }
}