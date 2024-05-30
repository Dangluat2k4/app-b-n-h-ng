package com.thuydev.app_ban_an;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thuydev.app_ban_an.frm.fragment_doimk_nhanvien;
import com.thuydev.app_ban_an.frm.fragment_hotro_nhanvien;
import com.thuydev.app_ban_an.frm.fragment_quanlygiay_nhanvien;
import com.thuydev.app_ban_an.frm.fragment_quanlyhoadon_nhanvien;

public class ManHinhNhanVien extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    FragmentManager manager;
    fragment_quanlygiay_nhanvien quanlygiay_nhanvien = new fragment_quanlygiay_nhanvien();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Log.e("TAG", "onCreate: "+DangNhap.account );
        setContentView(R.layout.activity_man_hinh_nhan_vien);
        toolbar = findViewById(R.id.toolbar_nhanvien);
        bottomNavigationView = findViewById(R.id.bnv_NhanVien);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Quản Lý Sản Phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fcv_Nhanvien, quanlygiay_nhanvien).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_nhanvien_qlsp) {
                    replaceFragment(quanlygiay_nhanvien);
                    getSupportActionBar().setTitle("Quản Lý Sản Phẩm");
                } else if (item.getItemId() == R.id.menu_nhanvien_qlhd) {
                    replaceFragment(new fragment_quanlyhoadon_nhanvien());
                    getSupportActionBar().setTitle("Quản Lý Hóa Đơn");
                } else if (item.getItemId() == R.id.menu_nhanvien_hotro) {
                    replaceFragment(new fragment_hotro_nhanvien());
                    getSupportActionBar().setTitle("Hỗ trợ khách hàng");
                }
                else if (item.getItemId() == R.id.menu_nhanvien_resetpass) {
                    replaceFragment(new fragment_doimk_nhanvien());
                    getSupportActionBar().setTitle("Đổi mật khẩu");
                }
                else {
                    Toast.makeText(ManHinhNhanVien.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Xử lý các sự kiện khi chọn các item trên ActionBar ở đây
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment fragment) {
        manager.beginTransaction().replace(R.id.fcv_Nhanvien, fragment).commit();
    }
}
