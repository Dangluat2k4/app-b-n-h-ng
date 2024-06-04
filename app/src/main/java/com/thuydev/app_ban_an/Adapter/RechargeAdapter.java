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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.app_ban_an.DTO.Bill;
import com.thuydev.app_ban_an.DTO.BillDetail;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.DTO.Recharge;
import com.thuydev.app_ban_an.Extentions.Extention;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.databinding.DialogThemHangBinding;
import com.thuydev.app_ban_an.databinding.ItemChodonBinding;
import com.thuydev.app_ban_an.databinding.ItemLichsuggBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.ViewHolder> {

    ItemLichsuggBinding binding;
    List<Recharge> list;
    Context context;

    public RechargeAdapter(List<Recharge> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemLichsuggBinding.inflate(((Activity)context).getLayoutInflater(),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String Xanh = "#44cc00";
        String Cam = "#FFC107";
        holder.ma.setText(list.get(position).get_id());
        holder.sotien.setText(Extention.MakeStyleMoney(list.get(position).getMoney()) +" VND");
        holder.ngay.setText(list.get(position).getTime());
        if (list.get(position).getStatus()==0){
            holder.trangthai.setText("Đang chờ");
            holder.trangthai.setTextColor(Color.parseColor(Cam));
        }else {
            holder.trangthai.setText("Đã duyêt");
            holder.trangthai.setTextColor(Color.parseColor(Xanh));
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ma, sotien, ngay,trangthai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ma = binding.tvMaGG;
            sotien = binding.tvSotienGg;
            trangthai = binding.tvTrangThaiGg;
            ngay =binding.tvThoigiangg;

        }
    }
}
