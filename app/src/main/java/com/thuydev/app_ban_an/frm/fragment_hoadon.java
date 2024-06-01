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

import com.thuydev.app_ban_an.Adapter.BillAdapter;
import com.thuydev.app_ban_an.DTO.Bill;
import com.thuydev.app_ban_an.DTO.BillDetail;
import com.thuydev.app_ban_an.DangNhap;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.databinding.FragmentFrgQuanLyHoaDonBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_hoadon extends Fragment {
FragmentFrgQuanLyHoaDonBinding binding;
BillAdapter billAdapter;
List<Bill> billList;
List<BillDetail> billDetails;
String idUser = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFrgQuanLyHoaDonBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SetUp();
    }

    private void SetUp() {
        idUser = DangNhap.account.get_id();
        billList = new ArrayList<>();
        billDetails = new ArrayList<>();
        billAdapter = new BillAdapter(billList,billDetails,getContext(),this);
        binding.rcvQuanLyHoaDon.setAdapter(billAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        binding.rcvQuanLyHoaDon.setLayoutManager(layoutManager);
        GetBill();
    }

    public void GetBill() {
        Call<List<Bill>> call = ProductInterface.GETAPI().GetBills(idUser);
        call.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if (response.isSuccessful()){
                    billList.clear();
                    billList.addAll(response.body());
                    Log.e("TAG1", "onBindViewHolder: "+idUser );
                    GetBillDetail();
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable throwable) {

            }
        });
    }

    private void GetBillDetail() {
        Call<List<BillDetail>> call = ProductInterface.GETAPI().GetBillDetails(idUser);
        call.enqueue(new Callback<List<BillDetail>>() {
            @Override
            public void onResponse(Call<List<BillDetail>> call, Response<List<BillDetail>> response) {
                if(response.isSuccessful()){
                    billDetails.clear();
                    billDetails.addAll(response.body());
                    billAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BillDetail>> call, Throwable throwable) {

            }
        });
    }
}
