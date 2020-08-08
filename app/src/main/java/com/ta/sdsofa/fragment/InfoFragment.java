package com.ta.sdsofa.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ta.sdsofa.R;
import com.ta.sdsofa.activity.InfoDetailActivity;
import com.ta.sdsofa.activity.TambahInfoActivity;
import com.ta.sdsofa.adapter.InfoAdapter;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.InfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;


public class InfoFragment extends Fragment {
    private RecyclerView rvList;
    private InfoAdapter infoAdapter;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;

    public InfoFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        rvList = view.findViewById(R.id.rv_list);
        sessionManager = new SessionManager(getContext());
        utilMessage = new UtilMessage(getActivity());
        infoAdapter = new InfoAdapter(getContext(), new ArrayList<InfoModel>());
        infoAdapter.setAdapterListener(new InfoAdapter.InfoAdapterListener() {
            @Override
            public void onItemClickListener(InfoModel infoModel) {
                Intent intent = new Intent(getContext(), InfoDetailActivity.class);
                intent.putExtra("data", infoModel);

                startActivity(intent);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(infoAdapter);

        getData();
    }


    private void getData() {
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

                                data.add(infoModel);
                            }

                            infoAdapter.setData(data);

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if(sessionManager.getRole().equals("admin")) {
            inflater.inflate(R.menu.menu_tambah_info, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_tambah){
            Intent intent = new Intent(getContext(), TambahInfoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}