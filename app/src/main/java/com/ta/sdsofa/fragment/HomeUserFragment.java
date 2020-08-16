package com.ta.sdsofa.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ta.sdsofa.R;
import com.ta.sdsofa.helper.SessionManager;
import com.ta.sdsofa.helper.UtilMessage;
import com.ta.sdsofa.model.AdminModel;
import com.ta.sdsofa.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ta.sdsofa.helper.GlobalVariable.BASE_URL;
import static com.ta.sdsofa.helper.GlobalVariable.IMAGE_URL;


public class HomeUserFragment extends Fragment {
    private ImageView imgProfile;
    private TextView nama;
    private SessionManager sessionManager;
    private UtilMessage utilMessage;
    private AdminModel adminModel;
    private UserModel userModel;
    private Context context;


    public HomeUserFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgProfile = view.findViewById(R.id.imageViewProfile);
        nama = view.findViewById(R.id.tv_nama);

        sessionManager = new SessionManager(getContext());
        utilMessage = new UtilMessage(getActivity());

        userModel = getActivity().getIntent().getParcelableExtra("data");




        getData();


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

                            ArrayList<UserModel> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                UserModel userModel = new UserModel();
                                userModel.setId(item.getString("id"));
                                userModel.setNisn(item.getString("nisn"));
                                userModel.setNama(item.getString("nama"));
                                userModel.setAlamat(item.getString("alamat"));
                                userModel.setNohp(item.getString("nohp"));
                                userModel.setNohporangtua(item.getString("nohporangtua"));
                                userModel.setTanggal_lahir(item.getString("tanggal_lahir"));
                                userModel.setFoto(item.getString("foto"));
                                sessionManager.setKelas(item.getString("kelas"));
                                nama.setText(userModel.getNama());

                                Glide.with(getContext()).asBitmap().load(IMAGE_URL+userModel.getFoto()).into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        imgProfile.setImageBitmap(resource);
                                    }
                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });



                                data.add(userModel);
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