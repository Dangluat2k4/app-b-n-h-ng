package com.thuydev.app_ban_an.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.app_ban_an.DTO.Bill;
import com.thuydev.app_ban_an.DTO.BillDetail;
import com.thuydev.app_ban_an.databinding.ItemChodonBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    ItemChodonBinding binding;
    List<Bill> billList;
    List<BillDetail> billDetails;
    Context context;

    public BillAdapter(List<Bill> billList, List<BillDetail> billDetails, Context context) {
        this.billList = billList;
        this.billDetails = billDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemChodonBinding.inflate(((Activity)context).getLayoutInflater(),parent,false);
        return new ViewHolder(binding.getRoot());
    }

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
