package com.thuydev.app_ban_an;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.thuydev.app_ban_an.Adapter.SizeAdapter;
import com.thuydev.app_ban_an.DTO.CartDTO;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.DTO.ProductDetailDTO;
import com.thuydev.app_ban_an.Extentions.Extention;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.databinding.ActivitySanphamShowBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailProduct extends AppCompatActivity {
    ActivitySanphamShowBinding binding;

    List<String> listSize;
    SizeAdapter sizeAdapter;
    int so = 0;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySanphamShowBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        id = intent.getStringExtra("IDPRODUCT");
        SetUp();
        GetData(id);
        SoluongCongTru();
        ThemVaoGio();
    }

    private void ThemVaoGio() {
        binding.btnThemgio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(binding.edtSoluongShow.getText().toString())==0){
                    Toast.makeText(ShowDetailProduct.this, "Bạn phải chọn ít nhất 1 sản phẩm ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sizeAdapter.GetSize().isEmpty()){
                    Toast.makeText(ShowDetailProduct.this, "Hãy chọn kích cỡ", Toast.LENGTH_SHORT).show();
                    return;
                }
                // thực hiện thêm vào giỏ
                AddCart();

            }
        });
    }

    private void AddCart() {
        CartDTO cartDTO = new CartDTO();
        // sau nay co dang nhap thay sau
        cartDTO.setIDUser(ProductInterface.idUser);
        cartDTO.setIDProduct(id);
        cartDTO.setSize(sizeAdapter.GetSize());
        cartDTO.setAmount(so);
        Call<String> call = ProductInterface.GETAPI().AddCart(cartDTO);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful())
                    Toast.makeText(ShowDetailProduct.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    private void SoluongCongTru() {
        binding.edtSoluongShow.setText(so+"");
        binding.edtSoluongShow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    s="0";
                }
                if (Integer.parseInt(s.toString())>30){
                    Toast.makeText(ShowDetailProduct.this, "Không sản phẩm không được vượt quá 30", Toast.LENGTH_SHORT).show();
                    so=30;
                    binding.edtSoluongShow.setText(so+"");
                    return;
                }
                so=Integer.parseInt(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.bntTruSoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinh("-");
            }
        });
        binding.bntCongSoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinh("+");
            }
        });
    }
    private void SetUp() {
        listSize = new ArrayList<>();
        sizeAdapter = new SizeAdapter(this, listSize);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rcvListco.setLayoutManager(layoutManager);
        binding.rcvListco.setAdapter(sizeAdapter);
    }
    private void GetData(String id) {
        GetSp(id);
        GetSpDetail(id);
    }
    private void GetSpDetail(String id) {
        Call<ProductDetailDTO> call = ProductInterface.GETAPI().GetProductDetail(id);
        call.enqueue(new Callback<ProductDetailDTO>() {
            @Override
            public void onResponse(Call<ProductDetailDTO> call, Response<ProductDetailDTO> response) {
                if (response.isSuccessful()) {
                    listSize.clear();
                    listSize.addAll(response.body().getSize());
                    binding.tvNamspShow.setText(response.body().getDate());
                    sizeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailDTO> call, Throwable throwable) {

            }
        });
    }
    private void GetSp(String id) {
        Call<ProductDTO> call = ProductInterface.GETAPI().GetProduct(id);
        call.enqueue(new Callback<ProductDTO>() {
            @Override
            public void onResponse(Call<ProductDTO> call, Response<ProductDTO> response) {
                if (response.isSuccessful()) {
                    ProductDTO dto = response.body();
                    if (dto == null) return;
                    Glide.with(ShowDetailProduct.this).load(dto.getImage())
                            .into(binding.imvAnhSpLon);
                    binding.tvTenspShow.setText(dto.getNameProduct());
                    binding.tvGiaspShow.setText(Extention.MakeStyleMoney(dto.getPrice()));
                }
            }

            @Override
            public void onFailure(Call<ProductDTO> call, Throwable throwable) {

            }
        });
    }
    private void tinh(String dau) {
        if ("-".equals(dau)) {
            so -= 1;
            if (so == -1) {
                so = 0;
            }
        } else {
            so += 1;
        }
        if (so>30){
            Toast.makeText(this, "Sản phẩm mua không được vượt quá 30 sản phẩm", Toast.LENGTH_SHORT).show();
            so=30;
            binding.edtSoluongShow.setText(so+"");
            return;
        }
        binding.edtSoluongShow.setText(so + "");
    }

}