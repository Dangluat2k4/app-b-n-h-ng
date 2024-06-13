package com.thuydev.app_ban_an;

import static java.security.AccessController.getContext;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thuydev.app_ban_an.frm.dialog_doipass;
import com.thuydev.app_ban_an.frm.fragment_quanlygiay_nhanvien;
import com.thuydev.app_ban_an.frm.fragment_quanlyhoadon_nhanvien;

public class ManHinhNhanVien extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    FragmentManager manager;
    fragment_quanlygiay_nhanvien quanlygiay_nhanvien = new fragment_quanlygiay_nhanvien();

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_nhan_vien);
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        toolbar = findViewById(R.id.toolbar_nhanvien);
        bottomNavigationView = findViewById(R.id.bnv_NhanVien);
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                LogOut();
            }
        });

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
                }
                else  if (item.getItemId() == R.id.menu_nhanvien_resetpass) {
                    dialog_doipass dialog = new dialog_doipass();
                    dialog.show(getSupportFragmentManager(), "ChangePasswordDialog");
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
    private void LogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.user1);
        builder.setMessage("Bạn có muốn đăng xuất");
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogOut2();
            }
        });
        builder.create().show();

    }

    public void LogOut2() {
        finishAffinity();
        SharedPreferences.Editor editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
        editor.remove("Username");
        editor.remove("pass");
        editor.remove("remeber");
        editor.apply();
        Intent intent = new Intent(this, DangNhap.class);
        startActivity(intent);
    }


}
