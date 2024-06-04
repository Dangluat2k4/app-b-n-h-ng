package com.thuydev.app_ban_an.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;
import com.thuydev.app_ban_an.ShowDetailProduct;
import com.thuydev.app_ban_an.databinding.ItemCuahangBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {

    private final Context context;
    private final List<ProductDTO> list;
    private ItemCuahangBinding binding;
    public ProductItemAdapter(Context context, String idCate, ItemCuahangBinding binding) {
        this.context = context;
        this.list = new ArrayList<>();
        this.binding = binding;
        GetData(idCate);
    }
    public ProductItemAdapter(Context context, String idCate) {
        this.context = context;
        this.list = new ArrayList<>();
        GetData(idCate);
    }
    private void GetData(String idCate) {
        Call<List<ProductDTO>> call = ProductInterface.GETAPI().GetListProductToCate(idCate);
        call.enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if(response.isSuccessful()){
                    list.clear();
                    list.addAll(response.body());
                    notifyDataSetChanged();
                    if(binding==null)return;
                    if(list.size()<=0){
                        binding.tvTenhang.setVisibility(View.GONE);
                        binding.llXemthemMoi.setVisibility(View.GONE);
                        binding.rcvListSpKhach.setVisibility(View.GONE);
                    }
                }
                else Log.e("TAG", "onResponse: "+response );
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable throwable) {

            }
        });
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
                Log.e("TAG", "onBindViewHolder2: "+ProductInterface.BASE_URL_IMAGE+dto.getImage() );
            }
            holder.tv_tensp_cuahang.setText("Tên : " + dto.getNameProduct());
            holder.tv_giasp_cuahang.setText("Giá " + String.valueOf(dto.getPrice()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowDetailProduct.class);
                intent.putExtra("IDPRODUCT",dto.get_id());
                ((Activity)context).startActivity(intent);
            }
        });
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
