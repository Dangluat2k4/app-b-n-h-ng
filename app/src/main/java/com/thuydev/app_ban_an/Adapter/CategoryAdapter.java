package com.thuydev.app_ban_an.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.app_ban_an.DTO.CategoryDTO;
import com.thuydev.app_ban_an.databinding.ItemCuahangBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    Context context;
    List<CategoryDTO> list;
    ItemCuahangBinding binding;

    public CategoryAdapter(Context context, List<CategoryDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCuahangBinding.inflate(((Activity)context).getLayoutInflater());
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItemAdapter itemAdapter = new ProductItemAdapter(context,list.get(position).get_id(),binding);
        holder.tenHang.setText(list.get(position).getNameCategory());
        holder.xemThem.setText("Xem thêm");
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        holder.rcListSp.setLayoutManager(layoutManager);
        holder.rcListSp.setAdapter(itemAdapter);
        list.get(position).toString();
        holder.xemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // xem san pham theo loai
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenHang,xemThem;
        RecyclerView rcListSp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenHang = binding.tvTenhang;
            rcListSp = binding.rcvListSpKhach;
            xemThem = binding.llXemthemMoi;

        }
    }
}
