package com.thuydev.app_ban_an.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thuydev.app_ban_an.DTO.CategoryDTO;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.DTO.ProductDetailDTO;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyGiayItemAdapter_Nhanvien extends RecyclerView.Adapter<QuanLyGiayItemAdapter_Nhanvien.ViewHolder> {
    private final Context context;
    private final List<ProductDTO> listsp;
    private List<ProductDetailDTO> productDetailDTOS;
    private List<CategoryDTO> categoryDTOS;

    public QuanLyGiayItemAdapter_Nhanvien(Context context, List<ProductDTO> listsp) {
        this.context = context;
        this.listsp = listsp;
        productDetailDTOS = new ArrayList<>();
        productDetailDTOS = GetDetailPD();
        categoryDTOS = new ArrayList<>();
        categoryDTOS = GetCate();
    }

    private List<CategoryDTO> GetCate() {
        final List<CategoryDTO>[] lists = new List[]{new ArrayList()};
        Call<List<CategoryDTO>> call = ProductInterface.GETAPI().GetListCategory();
        call.enqueue(new Callback<List<CategoryDTO>>() {
            @Override
            public void onResponse(Call<List<CategoryDTO>> call, Response<List<CategoryDTO>> response) {
                if (response.isSuccessful()){
                    lists[0].clear();
                    lists[0].addAll(response.body());
                    Log.e(TAG, "onResponse: 11"+lists[0].size());
                }
            }

            @Override
            public void onFailure(Call<List<CategoryDTO>> call, Throwable throwable) {

            }
        });
        return lists[0];
    }

    private List<ProductDetailDTO> GetDetailPD() {
        final List<ProductDetailDTO>[] productDetailDTOS = new List[]{new ArrayList<>()};
        Call<List<ProductDetailDTO>> call = ProductInterface.GETAPI().GetProductDetails();
        call.enqueue(new Callback<List<ProductDetailDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDetailDTO>> call, Response<List<ProductDetailDTO>> response) {
                if (response.isSuccessful()) {
                    productDetailDTOS[0].clear();
                    productDetailDTOS[0].addAll(response.body());
                    Log.e("TAG", "onResponse: " + productDetailDTOS[0].size());
                }
            }
            @Override
            public void onFailure(Call<List<ProductDetailDTO>> call, Throwable throwable) {

            }
        });
        return productDetailDTOS[0];
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanlyqiay_nhanvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDTO dtosp = listsp.get(position);
        if (dtosp != null) {
            Glide.with(context).load(dtosp.getImage())
                    .error(R.drawable.baseline_account_balance_wallet_24)
                    .into(holder.imv_anh_sp_cuahang);
            holder.tv_tensp.setText("Tên sản phẩm: " + dtosp.getNameProduct());
            holder.tv_gia.setText("Giá: " + String.valueOf(dtosp.getPrice()));
            GetDetail(dtosp.get_id(), holder.tv_soLuong, holder.tv_namSanXuat);
            GetCatesp(dtosp.getIDCategory(), holder.tv_thuongHieu);

        }
    }

    private void GetCatesp(String id, TextView tv_hang) {
        for (CategoryDTO item : categoryDTOS
        ) {
            if (item.get_id().equals(id)) {
                tv_hang.setText("Hang: "+item.getNameCategory());
            }
        }
    }
    private void GetDetail(String id, TextView tv_soLuong, TextView tv_namSanXuat) {
        for (ProductDetailDTO item : productDetailDTOS
        ) {
            if (item.getIDProduct().equals(id)) {
                tv_soLuong.setText("So luong: "+item.getAmount());
                tv_namSanXuat.setText("Nam san xuat: "+item.getDate());
            }
            Log.e("TAG", "GetDetail: " + item.get_id());
            Log.e("TAG", "GetDetail: " + id);

        }
        Log.e("TAG", "GetDetail: " + productDetailDTOS.size());
    }

    @Override
    public int getItemCount() {
        return listsp.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imv_anh_sp_cuahang;
        TextView tv_tensp, tv_gia, tv_soLuong, tv_thuongHieu, tv_namSanXuat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_anh_sp_cuahang = itemView.findViewById(R.id.imv_anhsp);
            tv_tensp = itemView.findViewById(R.id.tv_Tensp_nv);
            tv_gia = itemView.findViewById(R.id.tv_Gia_nv);
            tv_soLuong = itemView.findViewById(R.id.tv_Soluong_nv);
            tv_thuongHieu = itemView.findViewById(R.id.tv_Thuonghieu_nv);
            tv_namSanXuat = itemView.findViewById(R.id.tv_Namsx_nv);
        }
    }
}
