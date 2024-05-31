package com.thuydev.app_ban_an.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thuydev.app_ban_an.Account.Account;
import com.thuydev.app_ban_an.DTO.Bill;
import com.thuydev.app_ban_an.DTO.BillDetail;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.DTO.ProductDetailDTO;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XacNhanDonHangAdapter extends RecyclerView.Adapter<XacNhanDonHangAdapter.ViewHolder> {
    private final Context context;
    private final List<BillDetail> listsp;

    private List<Account> accountList;



    public XacNhanDonHangAdapter(Context context, List<BillDetail> listsp) {
        this.context = context;
        this.listsp = listsp;
        accountList = new ArrayList<>();
        accountList = GetAccount();


    }



    private List<Account> GetAccount() {
        final List<Account>[] lists = new List[]{new ArrayList<>()};
        Call<List<Account>> call = ProductInterface.GETAPI().GetListAccount();
        call.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.isSuccessful()) {
                    lists[0].clear();
                    lists[0].addAll(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Account>> call, Throwable throwable) {

            }
        });
        return lists[0];
    }
/*
    private List<Bill> GetBill(String id) {
        final List<Bill>[] lists = new List[]{new ArrayList<>()};
        Call<List<Bill>> call = ProductInterface.GETAPI().GetBills(id);
        call.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if (response.isSuccessful()) {
                    lists[0].clear();
                    lists[0].addAll(response.body());

                }
            }
            @Override
            public void onFailure(Call<List<Bill>> call, Throwable throwable) {

            }
        });
        return lists[0];
    }
    private void GetBill(String id, TextView id_nhanvien) {
        for (Bill item : billList
        ) {
            if (item.get_id().equals(id)) {
                id_nhanvien.setText(item.getIDSeller());
            }
        }
    }
    */

    private void GetAccount(String id, TextView tv_tenkh, TextView tv_sdt) {
        for (Account item : accountList
        ) {
            if (item.get_id().equals(id)) {
                tv_tenkh.setText("Tên khách hàng: " +item.getFullName());
                tv_sdt.setText("Số điện thoại: " +item.getNumberPhone());
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ql_don_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            BillDetail detail = listsp.get(position);
            GetAccount(detail.getIDUser(), holder.tv_tenkh, holder.tv_sdt);
            holder.tv_idDon.setText("Ma hoa don: "+ detail.get_id());
            holder.tv_gia.setText("Giá: "+String.valueOf(detail.getTotal()));
            holder.tv_soluong.setText("Số lượng: " +String.valueOf(detail.getAmount()));
             holder.tv_diachi.setText("Địa chỉ: " +detail.getIDBill());
    }

    @Override
    public int getItemCount() {
        return listsp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_idDon, tv_tenkh, tv_diachi, tv_sdt, tv_gia, tv_soluong;
        ImageButton btn_xoa, btn_xacnhan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_idDon = itemView.findViewById(R.id.tv_idDon);
            tv_tenkh = itemView.findViewById(R.id.tv_tenKhach);
            tv_diachi = itemView.findViewById(R.id.tv_diaChi);
            tv_sdt = itemView.findViewById(R.id.tv_sdt);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_soluong = itemView.findViewById(R.id.tv_soLuong_);
            btn_xoa = itemView.findViewById(R.id.ibtn_Huy);
            btn_xacnhan = itemView.findViewById(R.id.ibtn_XacNhan);
        }
    }
}
