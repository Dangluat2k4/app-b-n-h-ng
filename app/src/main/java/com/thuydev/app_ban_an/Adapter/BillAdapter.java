package com.thuydev.app_ban_an.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.app_ban_an.DTO.Bill;
import com.thuydev.app_ban_an.DTO.BillDetail;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.databinding.ItemChodonBinding;
import com.thuydev.app_ban_an.frm.fragment_hoadon;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    ItemChodonBinding binding;
    List<Bill> billList;
    List<BillDetail> billDetails;
    Context context;
    fragment_hoadon fragmentHoadon;
    public BillAdapter(List<Bill> billList, List<BillDetail> billDetails, Context context, fragment_hoadon fragmentHoadon) {
        this.billList = billList;
        this.billDetails = billDetails;
        this.context = context;
        this.fragmentHoadon = fragmentHoadon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemChodonBinding.inflate(((Activity)context).getLayoutInflater(),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String Xanh = "#44cc00";
        String Do = "#FF0000";
        String Cam = "#FFC107";

        BillDetail billDetail = GetBillDetail(billList.get(position).get_id());
        holder.tenSP.setText("Mã hàng: " + billList.get(position).get_id());
        holder.giaSP.setText("Tổng giá: " +  NumberFormat.getNumberInstance(Locale.getDefault()).format(billDetail.getTotal()) + "đ");
        holder.soLuong.setText("Số lượng: " + billDetail.getAmount() + " SP");
        holder.ngay.setText("Ngày mua: " + billDetail.getDate());

        holder.nguoiduyet.setText("Người duyệt: "+billList.get(position).getIDSeller());
        if (billDetails.get(position).getStatus() == 0) {
            holder.trangthai.setText("Đang chờ xác nhận");
            holder.trangthai.setTextColor(Color.parseColor(Cam));
            holder.xoa.setVisibility(View.VISIBLE);
        } else if (billDetails.get(position).getStatus() == 1) {
            holder.trangthai.setText("Đã xác nhận đơn");
            holder.xoa.setVisibility(View.GONE);
            holder.trangthai.setTextColor(Color.parseColor(Xanh));
        } else if (billDetails.get(position).getStatus() == 3) {
            holder.trangthai.setText("Đơn hàng bị từ chối");
            holder.trangthai.setTextColor(Color.parseColor(Do));
            holder.xoa.setVisibility(View.VISIBLE);
        } else {
            holder.trangthai.setText("Lỗi");
        }
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xoa(billList.get(position).get_id());
            }
        });
    }

    private void Xoa(String id) {
        Call<String> call = ProductInterface.GETAPI().DeleteBill(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                fragmentHoadon.GetBill();
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    private BillDetail GetBillDetail(String id) {
        for (BillDetail item: billDetails) {
            if(id.equals(item.getIDBill())) return item;
        }
        return new BillDetail();
    }



    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenSP, soLuong, giaSP, trangthai, xoa, ngay,nguoiduyet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSP = binding.tvTenspGioHang;
            giaSP = binding.tvGiaspGiohang;
            soLuong = binding.tvSoluongspGiohang;
            trangthai = binding.tvMuaGiohang;
            ngay =binding.tvNgayMua;
            xoa =binding.tvXoaGiohang;
            nguoiduyet =binding.tvNguoiduyet;
        }
    }
}
