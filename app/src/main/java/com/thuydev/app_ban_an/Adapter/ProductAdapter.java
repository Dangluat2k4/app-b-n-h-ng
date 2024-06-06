package com.thuydev.app_ban_an.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.Extentions.Extention;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;


import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private final List<ProductDTO> list;

    public ProductAdapter(Context context, List<ProductDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuahang_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDTO dto = list.get(position);
        if (dto != null){
            if(dto.getImage().contains("https:")||dto.getImage().contains("http:")){
                Glide.with(context).load(dto.getImage())
                        .error(R.drawable.shape_btn)
                        .into(holder.imv_anh_sp_cuahang);
                Log.e("TAG", "onBindViewHolder1: "+dto.getImage() );
            }else {
                Glide.with(context).load(ProductInterface.BASE_URL_IMAGE +dto.getImage())
                        .error(R.drawable.shape_btn)
                        .into(holder.imv_anh_sp_cuahang);
                Log.e("TAG", "onBindViewHolder2: "+dto.getImage() );
            }

            holder.tv_tensp_cuahang.setText(dto.getNameProduct());
            holder.tv_giasp_cuahang.setText(Extention.MakeStyleMoney(dto.getPrice()));
        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imv_anh_sp_cuahang;
        TextView tv_tensp_cuahang;
        TextView tv_giasp_cuahang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_anh_sp_cuahang = itemView.findViewById(R.id.imv_anh_sp_cuahang);
            tv_tensp_cuahang = itemView.findViewById(R.id.tv_tensp_cuahang);
            tv_giasp_cuahang = itemView.findViewById(R.id.tv_giasp_cuahang);
        }
    }
}
