package com.thuydev.app_ban_an.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.R;


import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private final List<ProductDTO> list;
    static String BASE_URL = "http://10.0.2.2:3000/";

    String TAG = "zzzzzzzzzzzz";

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
        holder.tv_tensp_cuahang.setText("sam pham" + dto.getNameProduct());
        holder.tv_giasp_cuahang.setText("sam pham" + String.valueOf(dto.getPrice()));
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
