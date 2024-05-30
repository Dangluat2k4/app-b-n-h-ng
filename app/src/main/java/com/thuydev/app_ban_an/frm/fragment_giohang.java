package com.thuydev.app_ban_an.frm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thuydev.app_ban_an.Adapter.CartAdapter;
import com.thuydev.app_ban_an.DTO.CartDTO;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.Extentions.Extention;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.ManHinhKhachHang;
import com.thuydev.app_ban_an.databinding.FragmentGioHangBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_giohang extends Fragment {
    CartAdapter cartAdapter;
    List<CartDTO> list;
    List<ProductDTO> listPro;
    FragmentGioHangBinding binding;

    int tongGia = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGioHangBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        listPro = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext(),list,listPro);
        binding.rcvListgio.setAdapter(cartAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        binding.rcvListgio.setLayoutManager(layoutManager);
        GetCart(ProductInterface.idUser);


    }

    private void TinhTong() {
        tongGia = 0;
        for (CartDTO item:list
             ) {
            tongGia+=(item.getAmount()*GetPricePro(item.getIDProduct()));
            Log.e("TAG", "TinhTong: "+tongGia );
        }

        binding.tvGioGia.setText(Extention.MakeStyleMoney(tongGia)+" VND");
        cartAdapter.notifyDataSetChanged();
    }

    private Integer GetPricePro(String idProduct) {
        int tong = 0;
        for (ProductDTO item:listPro) {
            if(idProduct.equals(item.get_id())){
                tong = item.getPrice();
                break;
            }
        }
        return tong;
    }
    private void GetCart(String idUser) {
        Call<List<CartDTO>> call = ProductInterface.GETAPI().GetListCarts(idUser);
        call.enqueue(new Callback<List<CartDTO>>() {
            @Override
            public void onResponse(Call<List<CartDTO>> call, Response<List<CartDTO>> response) {
                if (response.isSuccessful()){
                    list.clear();
                    list.addAll(response.body());
                    GetListPro();
                }
            }

            @Override
            public void onFailure(Call<List<CartDTO>> call, Throwable throwable) {

            }
        });
    }
    private void GetListPro() {
        Call<List<ProductDTO>> call = ProductInterface.GETAPI().GetListProducts();
        call.enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if(response.isSuccessful()){
                    listPro.clear();
                    listPro.addAll(response.body());
                    cartAdapter.notifyDataSetChanged();
                    TinhTong();
                }
            }
            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable throwable) {

            }
        });
    }


}
