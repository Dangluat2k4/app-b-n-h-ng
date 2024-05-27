package com.thuydev.app_ban_an;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.thuydev.app_ban_an.Account.Account;
import com.thuydev.app_ban_an.Account.LoginResponse;
import com.thuydev.app_ban_an.Interface.ProductInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhap extends AppCompatActivity {
    private EditText edtEmail, edtMatKhau;
    private AppCompatButton btnDangNhap, btnDangKy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        // Ánh xạ EditText
        edtEmail = findViewById(R.id.edt_email_dangnhap);
        edtMatKhau = findViewById(R.id.edt_matkhau_dangnhap);

        btnDangNhap  = findViewById(R.id.btn_dangnhap);
        btnDangKy = findViewById(R.id.btn_dangky);
        btnDangNhap.setOnClickListener(v -> dangNhap());

        // Bắt sự kiện khi nhấn nút "Đăng ký"
        btnDangKy.setOnClickListener(v -> {
            // Chuyển sang activity DangKy
            Intent intent = new Intent(DangNhap.this, DangKy.class);
            startActivity(intent);
        });
    }
    private void dangNhap() {
        // Lấy thông tin từ các trường EditText
        String email = edtEmail.getText().toString();
        String matKhau = edtMatKhau.getText().toString();

        // Tạo đối tượng Account từ dữ liệu nhập vào
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(matKhau);

        // Gọi phương thức đăng nhập từ ProductInterface
        ProductInterface.GETAPI().login(account).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Đăng nhập thành công
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    // Chuyển sang activity ManHinhNhanVien
                    Intent intent = new Intent(DangNhap.this, ManHinhNhanVien.class);
                    startActivity(intent);
                    finish(); // Kết thúc activity hiện tại
                } else {
                    // Đăng nhập không thành công
                    Toast.makeText(DangNhap.this, "Email hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Xử lý lỗi khi gửi yêu cầu đăng nhập
                Toast.makeText(DangNhap.this, "Đã xảy ra lỗi. Vui lòng kiểm tra kết nối và thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
