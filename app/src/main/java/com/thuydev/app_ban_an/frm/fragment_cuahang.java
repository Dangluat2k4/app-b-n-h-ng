package com.thuydev.app_ban_an.frm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thuydev.app_ban_an.Adapter.ProductAdapter;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fragment_cuahang extends Fragment {

    RecyclerView recyclerView;
    List<ProductDTO>list;
    String TAG = "vvvvvvvvvvv";
    ProductAdapter adapter;
    ;
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cuahang, container, false);

        recyclerView = view.findViewById(R.id.rcv_cuaHang);

        list = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProductAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        getProduct();
        return view;
    }

    public void getProduct() {

        // Tạo đối tượng Call
        Call<List<ProductDTO>> call = ProductInterface.GETAPI().lay_danh_sach();

        // Thực hiện gọi hàm enqueue để lấy dữ liệu
        call.enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Lấy dữ liệu thành công " + response.body());
                    list.clear();
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "onResponse: Không lấy được dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }


}
