package com.thuydev.app_ban_an;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thuydev.app_ban_an.DTO.CartDTO;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.databinding.ActivityManHinhKhachHangBinding;
import com.thuydev.app_ban_an.frm.fragment_cuahang;
import com.thuydev.app_ban_an.frm.fragment_giohang;
import com.thuydev.app_ban_an.frm.fragment_hoadon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhKhachHang extends AppCompatActivity {
    Fragment cuaHangFrm, giohangFrm, hoadonFrm;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ActivityManHinhKhachHangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityManHinhKhachHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        CreatedToolBar();
        CreatedFrag();
        CreatedNavi();
        fragmentTransaction.add(R.id.fcv_KhachHang, cuaHangFrm);
        fragmentTransaction.commit();
    }

    private void CreatedNavi() {
        binding.bnvKhachhang.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_khachhang_danhsachsp) {
                    SetTitleToolBar("Cửa hàng");
                    ChangeFrag(cuaHangFrm);
                } else if (item.getItemId() == R.id.menu_khachhang_giohang) {
                    SetTitleToolBar("Giỏ hàng");
                    ChangeFrag(giohangFrm);
                } else if (item.getItemId() == R.id.menu_khachhang_hoadon) {
                    SetTitleToolBar("Hóa đơn");
                    ChangeFrag(hoadonFrm);
                }
                return true;
            }
        });
    }

    private void ChangeFrag(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.fcv_KhachHang, fragment).commit();
    }

    private void SetTitleToolBar(String s) {
        getSupportActionBar().setTitle(s);
    }

    private void CreatedToolBar() {
        setSupportActionBar(binding.toolbarKhachhang);
        getSupportActionBar().setTitle("Cửa hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void CreatedFrag() {
        cuaHangFrm = new fragment_cuahang();
        giohangFrm = new fragment_giohang();
        hoadonFrm = new fragment_hoadon();
    }

}
