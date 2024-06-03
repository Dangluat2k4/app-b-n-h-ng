package com.thuydev.app_ban_an.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thuydev.app_ban_an.DTO.CartDTO;
import com.thuydev.app_ban_an.DTO.CategoryDTO;
import com.thuydev.app_ban_an.DTO.ProductCart;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.Extentions.Extention;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;
import com.thuydev.app_ban_an.databinding.ItemGiohangBinding;
import com.thuydev.app_ban_an.frm.fragment_giohang;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    ItemGiohangBinding binding;
    Context context;
    List<CartDTO> list;
    List<ProductDTO> listPro;
    List<CategoryDTO> listCate;
    fragment_giohang fragmentGiohang;
    public CartAdapter(Context context, List<CartDTO> list, List<ProductDTO> listPro, fragment_giohang fragmentGiohang) {
        this.context = context;
        this.list = list;
        this.listPro = listPro;
        this.fragmentGiohang = fragmentGiohang;
        listCate = new ArrayList<>();
        GetLitsCate();

    }

    private void GetLitsCate() {

        Call<List<CategoryDTO>> call = ProductInterface.GETAPI().GetListCategory();
        call.enqueue(new Callback<List<CategoryDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<CategoryDTO>> call, @NonNull Response<List<CategoryDTO>> response) {
                if(response.isSuccessful()){
                    listCate.clear();
                    listCate.addAll(response.body());
                    Log.e("TAG", "GetCate: "+listCate.size() );
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CategoryDTO>> call, @NonNull Throwable throwable) {

            }
        });
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemGiohangBinding.inflate(((Activity)context).getLayoutInflater(),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductDTO tempPro = GetPro(list.get(position).getIDProduct());
        CategoryDTO tempCate = GetCate(tempPro.getIDCategory());
        CheckNull(tempPro,tempCate);
        Log.e("TAG", "onBindViewHolder: "+tempPro );
        Glide.with(context).load(tempPro.getImage()).error(R.drawable.logo2).into(binding.imvAnhSpGioHang);
        holder.tenSP.setText(tempPro.getNameProduct());
        holder.thuongHieu.setText(tempCate.getNameCategory());
        holder.kichCo.setText("Kích cỡ: "+list.get(position).getSize());
        holder.soLuong.setText("Số lượng: "+list.get(position).getAmount());
        holder.gia.setText("Giá: "+ Extention.MakeStyleMoney(tempPro.getPrice()));
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xoa(list.get(position).get_id());
            }
        });
        holder.mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mua(position);
            }
        });
    }

    private void Mua(int position) {
        if (fragmentGiohang.CheckAddressUser()){
            Toast.makeText(context, "Bạn phải điền địa chỉ trong Quản lý người dùng > Địa chỉ ", Toast.LENGTH_SHORT).show();
            return;
        }
        List<ProductCart> listCartPro = new ArrayList<>();
        List<String> listIDCart = new ArrayList<>();
        HashMap<String,Object> data = new HashMap<>();
        CartDTO cartDTO = list.get(position);
        ProductDTO tempPro = GetPro(cartDTO.getIDProduct());
        listIDCart.add(cartDTO.get_id());
        Calendar lich = Calendar.getInstance();
        int ngay = lich.get(Calendar.DAY_OF_MONTH);
        int thang = lich.get(Calendar.MONTH) + 1;
        int nam = lich.get(Calendar.YEAR);
        String ngayMua = String.format("%02d/%02d/%02d",nam,thang,ngay);
        listCartPro.add(new ProductCart(cartDTO.getIDProduct(),cartDTO.getAmount(),tempPro.getPrice()));
        data.put("IDUser",cartDTO.getIDUser());
        data.put("IDSeller","?");
        data.put("IDProduct",listCartPro);
        data.put("Status",0);
        data.put("Date",ngayMua);
        data.put("Amount",listCartPro);
        data.put("IDCart",listIDCart);

        Call<String> call = ProductInterface.GETAPI().AddBill(data);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                fragmentGiohang.GetCart(fragmentGiohang.id);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });

    }

    private void Xoa(String id) {
        Call<String> call = ProductInterface.GETAPI().DeleteCart(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                    fragmentGiohang.GetCart(fragmentGiohang.id);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    private void CheckNull(ProductDTO tempPro, CategoryDTO tempCate) {
        if(tempPro==null||tempCate==null)
            notifyDataSetChanged();
        return;
    }

    private CategoryDTO GetCate(String idCategory) {
        CategoryDTO dto = new CategoryDTO();
        if(idCategory==null) return dto;
        for (CategoryDTO item:listCate) {
            if (idCategory.equals(item.get_id())) {
                dto = item;
                break;
            }

        }
        return dto;
    }

    private ProductDTO GetPro(String idProduct) {
        ProductDTO dto = new ProductDTO();
        for (ProductDTO item: listPro) {
            if (idProduct.equals(item.get_id())){
                dto = item;
                break;
            };
        }
        return dto;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenSP,thuongHieu,kichCo,soLuong,gia;
        ImageView anh;
        TextView mua,xoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSP = binding.tvTenspGioHang;
            thuongHieu= binding.tvThuonghieuGioHang;
            kichCo = binding.tvKichcospGiohang;
            soLuong = binding.tvSoluongspGiohang;
            gia = binding.tvGiaspGiohang;
            anh = binding.imvAnhSpGioHang;
            mua = binding.tvMuaGiohang;
            xoa = binding.tvXoaGiohang;
        }
    }
}
