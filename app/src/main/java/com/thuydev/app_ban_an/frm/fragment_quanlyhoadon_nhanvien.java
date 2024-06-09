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

import com.thuydev.app_ban_an.Account.Account;
import com.thuydev.app_ban_an.Adapter.XacNhanDonHangAdapter;
import com.thuydev.app_ban_an.DTO.Bill;
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
    List<Account>lists ;
    XacNhanDonHangAdapter xacNhanDonHangAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_quan_ly_hoa_don, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_quanLyHoaDon);


        detailList = new ArrayList<>();
        lists = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        xacNhanDonHangAdapter = new XacNhanDonHangAdapter(getContext(), detailList,lists);
        recyclerView.setAdapter(xacNhanDonHangAdapter);

        GetAllProducs();

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
                    GetAccount();

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



    private void GetAccount() {
        Call<List<Account>> call = ProductInterface.GETAPI().GetListAccount();
        call.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.isSuccessful()) {
                    lists.clear();
                    lists.addAll(response.body());
                    xacNhanDonHangAdapter.notifyDataSetChanged();
                    Log.d(TAG, "onResponse: dfghjunuhduwdw " + response.body().toString());

                }else {
                    Log.d(TAG, "onResponse: fail " + response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<List<Account>> call, Throwable throwable) {
                Log.d(TAG, "onResponse: gnrugbrurubgr " + throwable.getMessage());

            }
        });

    }
}
