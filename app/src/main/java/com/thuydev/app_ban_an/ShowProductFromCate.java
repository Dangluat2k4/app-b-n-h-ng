package com.thuydev.app_ban_an;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thuydev.app_ban_an.Account.Account;
import com.thuydev.app_ban_an.Account.Accountfeedback;
import com.thuydev.app_ban_an.Adapter.ProductItemAdapter;
import com.thuydev.app_ban_an.DTO.CategoryDTO;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.databinding.ActivityShowMoreBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowProductFromCate extends AppCompatActivity {
  ActivityShowMoreBinding binding;
  ProductItemAdapter itemAdapter ;
  String idCate = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        idCate = intent.getStringExtra("IDCATE");
        itemAdapter = new ProductItemAdapter(this,idCate);
        binding.rcvListSanPhamMore.setAdapter(itemAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);
        binding.rcvListSanPhamMore.setLayoutManager(layoutManager);
        GetNameCate();
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetNameCate() {
        Call<CategoryDTO> call = ProductInterface.GETAPI().GetCategory(idCate);
        call.enqueue(new Callback<CategoryDTO>() {
            @Override
            public void onResponse(Call<CategoryDTO> call, Response<CategoryDTO> response) {
                if(response.isSuccessful())binding.tvTenHangShow.setText(response.body().getNameCategory());
            }

            @Override
            public void onFailure(Call<CategoryDTO> call, Throwable throwable) {

            }
        });
    }

}
