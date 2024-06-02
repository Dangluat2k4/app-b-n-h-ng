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

import com.thuydev.app_ban_an.Adapter.XacNhanDonHangAdapter;
import com.thuydev.app_ban_an.DTO.BillDetail;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_quanlyhoadon_nhanvien extends Fragment {
    RecyclerView recyclerView;
    List<BillDetail> detailList;
    String TAG = "vvvvvvvvvvv";
    XacNhanDonHangAdapter xacNhanDonHangAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_quan_ly_hoa_don, container, false);
        recyclerView = view.findViewById(R.id.rcv_quanLyHoaDon);


        detailList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        xacNhanDonHangAdapter = new XacNhanDonHangAdapter(getContext(), detailList);
        recyclerView.setAdapter(xacNhanDonHangAdapter);

        GetAllProducs();
        return view;
    }
    void GetAllProducs(){
        Call<List<BillDetail>> call = ProductInterface.GETAPI().GetListxacnhan();

        call.enqueue(new Callback<List<BillDetail>>() {
            @Override
            public void onResponse(Call<List<BillDetail>> call, Response<List<BillDetail>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: lay du lieu thanh cong " + response.body().toString());
                    // cập nhật vào list và hiển th lên danh sach
                    detailList.clear();
                    detailList.addAll(response.body() ); //

                    xacNhanDonHangAdapter.notifyDataSetChanged();
                }else{
                    Log.d(TAG, "onResponse: khong lay duoc du lieu");
                }
            }


            @Override
            public void onFailure(Call<List<BillDetail>> call, Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage() );
                throwable.printStackTrace();// in ra danh sách các file liên quan tới lõi
            }
        });
    }

}
