package com.thuydev.app_ban_an;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.thuydev.app_ban_an.frm.fragment_cuahang;

public class ManHinhKhachHang extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_khach_hang);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment cuaHangFrm  = new fragment_cuahang();
        fragmentTransaction.add(R.id.fcv_KhachHang, cuaHangFrm);
        fragmentTransaction.commit();
    }
}
