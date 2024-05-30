package com.thuydev.app_ban_an.frm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thuydev.app_ban_an.Adapter.QuanLyGiayItemAdapter_Nhanvien;
import com.thuydev.app_ban_an.DTO.CategoryDTO;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;
import com.thuydev.app_ban_an.databinding.FragmentQuanlygiayNhanvienBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fragment_quanlygiay_nhanvien extends Fragment {
    RecyclerView recyclerView;
    List<ProductDTO> listsp;
    String TAG = "vvvvvvvvvvv";
    QuanLyGiayItemAdapter_Nhanvien giayAdapter_nhanvien;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlygiay_nhanvien, container, false);
        recyclerView = view.findViewById(R.id.rcv_qlgiay);

        listsp = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        giayAdapter_nhanvien = new QuanLyGiayItemAdapter_Nhanvien(getContext(), listsp);
        recyclerView.setAdapter(giayAdapter_nhanvien);

        GetAllProducs();

        return view;
    }
    void GetAllProducs(){
        Call<List<ProductDTO>> call = ProductInterface.GETAPI().GetListProducts();

        call.enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: lay du lieu thanh cong " + response.body().toString());
                    // cập nhật vào list và hiển th lên danh sach
                    listsp.clear();
                    listsp.addAll(response.body() ); //

                    giayAdapter_nhanvien.notifyDataSetChanged();
                }else{
                    Log.d(TAG, "onResponse: khong lay duoc du lieu");
                }
            }


            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage() );
                throwable.printStackTrace();// in ra danh sách các file liên quan tới lõi
            }
        });
    }
}