package com.thuydev.app_ban_an.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.app_ban_an.DTO.Bill;
import com.thuydev.app_ban_an.DTO.BillDetail;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;
import com.thuydev.app_ban_an.databinding.DialogThemHangBinding;
import com.thuydev.app_ban_an.databinding.ItemChodonBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    ItemChodonBinding binding;
    List<Bill> billList;
    List<BillDetail> billDetails;
    List<ProductDTO> listPro;
    Context context;

    public BillAdapter(List<Bill> billList, List<BillDetail> billDetails, Context context) {
        this.billList = billList;
        this.billDetails = billDetails;
        this.context = context;
        listPro = new ArrayList<>();
        GetPro();
    }

    private void GetPro() {
        Call<List<ProductDTO>> call = ProductInterface.GETAPI().GetListProducts();
        call.enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if (response.isSuccessful()){
                    listPro.clear();
                    listPro.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable throwable) {

            }
        });
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
        } else if (billDetails.get(position).getStatus() == 2) {
            holder.trangthai.setText("Đơn hàng bị từ chối");
            holder.trangthai.setTextColor(Color.parseColor(Do));
            holder.xoa.setVisibility(View.VISIBLE);
        } else {
            holder.trangthai.setText("Lỗi");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDataDetail(position);
            }
        });
    }

    private void ShowDataDetail(int p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogThemHangBinding binding1 = DialogThemHangBinding.inflate(((Activity)context).getLayoutInflater(),null,false);
        builder.setView(binding1.getRoot());
        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding1.edtThemhang.setVisibility(View.GONE);
        binding1.ibtnAddhang.setVisibility(View.GONE);
        binding1.tvTittle2.setText("Đơn hàng chi tiết");
        // viet code o day
        ChiTietDonHangMuaAdapter chiTietDonHangMuaAdapter = new ChiTietDonHangMuaAdapter(billList.get(p).getIDProduct(),listPro,context);
        binding1.listHang.setAdapter(chiTietDonHangMuaAdapter);

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
