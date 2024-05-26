package com.thuydev.app_ban_an;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.thuydev.app_ban_an.frm.fragment_quanlygiay_nhanvien;

public class ManHinhNhanVien extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_nhan_vien);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment cuaHangFrm  = new fragment_quanlygiay_nhanvien();
        fragmentTransaction.add(R.id.fcv_Nhanvien, cuaHangFrm);
        fragmentTransaction.commit();

    }
}
