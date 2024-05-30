package com.thuydev.app_ban_an.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.app_ban_an.Model.SanPhamUserDTO;
import com.thuydev.app_ban_an.R;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private  final Context context;
    private final List<SanPhamUserDTO> sanPhamUserDTOS;


    public SanPhamAdapter(Context context, List<SanPhamUserDTO> sanPhamUserDTOS) {
        this.context = context;
        this.sanPhamUserDTOS = sanPhamUserDTOS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuahang_sanpham,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     SanPhamUserDTO userDTO = sanPhamUserDTOS.get(position);

     holder.txtImage.setImageResource(Integer.parseInt(userDTO.getImage()));
     holder.txtTen.setText(userDTO.getNameProduct());
     holder.txtGia.setText(String.valueOf(userDTO.getPrice()));

    }

    @Override
    public int getItemCount() {
        return sanPhamUserDTOS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder   {
        TextView  txtTen, txtGia;
        ImageView txtImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtImage = itemView.findViewById(R.id.imv_anh_sp_cuahang);
            txtTen = itemView.findViewById(R.id.tv_tensp_cuahang);
            txtGia = itemView.findViewById(R.id.tv_giasp_cuahang);


        }
    }
}
