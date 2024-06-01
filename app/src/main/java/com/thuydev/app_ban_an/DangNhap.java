package com.thuydev.app_ban_an;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.thuydev.app_ban_an.Account.Account;
import com.thuydev.app_ban_an.Interface.ProductInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhap extends AppCompatActivity {

    public static Account account;
    SharedPreferences sharedPreferences ;
    private EditText edtEmail, edtMatKhau;
    private AppCompatButton btnDangNhap, btnDangKy;
    private CheckBox check;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        // Ánh xạ EditText
        sharedPreferences = getSharedPreferences("Data",MODE_PRIVATE);
        edtEmail = findViewById(R.id.edt_email_dangnhap);
        edtMatKhau = findViewById(R.id.edt_matkhau_dangnhap);
        check = findViewById(R.id.cbk_luu);
        btnDangNhap = findViewById(R.id.btn_dangnhap);
        btnDangKy = findViewById(R.id.btn_dangky);
        btnDangNhap.setOnClickListener(v -> dangNhap());
        CheckData();
        // Bắt sự kiện khi nhấn nút "Đăng ký"
        btnDangKy.setOnClickListener(v -> {
            // Chuyển sang activity DangKy
            Intent intent = new Intent(DangNhap.this, DangKy.class);
            startActivity(intent);
        });
    }

    private void CheckData() {
        boolean checkRMB = false;
            checkRMB = sharedPreferences.getBoolean("remeber",false);
        if (checkRMB) {
            check.setChecked(true);
            String name, pass;
            name = sharedPreferences.getString("Username", "");
            pass = sharedPreferences.getString("pass", "");
            edtEmail.setText(name);
            edtMatKhau.setText(pass);
        }
    }

    private void SaveAccount(String email, String matKhau) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("Username", email);
        edit.putString("pass", matKhau);
        edit.putBoolean("remeber", check.isChecked());
        edit.apply();
    }

    private void dangNhap() {
        // Lấy thông tin từ các trường EditText
        String email = edtEmail.getText().toString();
        String matKhau = edtMatKhau.getText().toString();
        ProductInterface.GETAPI().login(new Account(email, matKhau)).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    // Đăng nhập thành công
                    account = response.body();
                    Log.e("TAG", "onResponse: " + account);
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangNhap.this, account.getLevel() == 0 ? ManHinhKhachHang.class : ManHinhNhanVien.class);
                    startActivity(intent);
                    if (check.isChecked()) {
                        SaveAccount(email, matKhau);

                    }
                } else {
                    // Đăng nhập không thành công
                    Toast.makeText(DangNhap.this, "Email hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                // Xử lý lỗi khi gửi yêu cầu đăng nhập
                Toast.makeText(DangNhap.this, "Đã xảy ra lỗi. Vui lòng kiểm tra kết nối và thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
