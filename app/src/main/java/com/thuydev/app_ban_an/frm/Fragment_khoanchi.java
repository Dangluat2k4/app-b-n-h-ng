package com.thuydev.app_ban_an.frm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thuydev.app_ban_an.Account.Account;
import com.thuydev.app_ban_an.Adapter.BillAdapter;
import com.thuydev.app_ban_an.Adapter.BillToMonthAdapter;
import com.thuydev.app_ban_an.DTO.Bill;
import com.thuydev.app_ban_an.DTO.BillDetail;
import com.thuydev.app_ban_an.DTO.Recharge;
import com.thuydev.app_ban_an.DangNhap;
import com.thuydev.app_ban_an.Extentions.Extention;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.ProfileUser;
import com.thuydev.app_ban_an.R;
import com.thuydev.app_ban_an.databinding.TabKhoanchiBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_khoanchi extends Fragment {
    LocalDate dateStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    LocalDate dateEnd= LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    String ngayStart = formatter.format(dateStart);
    String ngayEnd=formatter.format(dateEnd);
    TabKhoanchiBinding binding;
    List<Bill> billList ;
    List<BillDetail> billDetails;
    BillToMonthAdapter billAdapter ;
    Account account;
    Long tong=0l;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=TabKhoanchiBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        account = DangNhap.dangNhap.account;
        billList = new ArrayList<>();
        billDetails = new ArrayList<>();
        billAdapter = new BillToMonthAdapter(billList, billDetails, getContext());
        binding.rcvListKhoanchi.setAdapter(billAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rcvListKhoanchi.setLayoutManager(layoutManager);
        GetBill();
    }

    private void GetBill() {
        Call<List<Bill>> call = ProductInterface.GETAPI().GetBills(account.get_id());
        call.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if (response.isSuccessful()){
                    billList.clear();
                    billList.addAll(response.body());
                    GetBillDeTail();
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable throwable) {

            }
        });
    }

    private void GetBillDeTail() {
        Call<List<BillDetail>> call = ProductInterface.GETAPI().GetBillDetailsToMonth(account.get_id(),ngayStart,ngayEnd);
        call.enqueue(new Callback<List<BillDetail>>() {
            @Override
            public void onResponse(Call<List<BillDetail>> call, Response<List<BillDetail>> response) {
                if (response.isSuccessful()){
                    billDetails.clear();
                    billDetails.addAll(response.body());
                    billAdapter.notifyDataSetChanged();
                    ShowTotal();
                }
            }

            @Override
            public void onFailure(Call<List<BillDetail>> call, Throwable throwable) {

            }
        });
    }

    private void ShowTotal() {
        for (BillDetail item:billDetails
             ) {
            tong+=item.getTotal();
            binding.tvTonggiaKhoanchi.setText(Extention.MakeStyleMoney(tong)+" đ");
        }
    }
}
