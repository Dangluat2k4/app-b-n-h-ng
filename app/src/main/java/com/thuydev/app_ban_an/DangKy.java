package com.thuydev.app_ban_an;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thuydev.app_ban_an.Account.Account;
import com.thuydev.app_ban_an.Account.Accountfeedback;
import com.thuydev.app_ban_an.Interface.ProductInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKy extends AppCompatActivity {
    private EditText edtEmail, edtMatKhau, edtNhapLaiMatKhau, edtFullname, edtNumberPhone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        // Ánh xạ các trường EditText
        edtEmail = findViewById(R.id.edt_email_dangnky);
        edtMatKhau = findViewById(R.id.edt_matkhau_dangky);
        edtNhapLaiMatKhau = findViewById(R.id.edt_rematkhau_dangky);
        edtFullname = findViewById(R.id.edt_fullname_dangky);
        edtNumberPhone = findViewById(R.id.edt_numberphone_dangky);

        // Bắt sự kiện khi nhấn nút Đăng ký
        findViewById(R.id.btn_dangky_on).setOnClickListener(v -> dangKy());

    }
    private void dangKy() {
        // Lấy thông tin từ các trường EditText
        String email = edtEmail.getText().toString();
        String matKhau = edtMatKhau.getText().toString();
        String nhapLaiMatKhau = edtNhapLaiMatKhau.getText().toString();
        String fullName = edtFullname.getText().toString();
        String numberPhone = edtNumberPhone.getText().toString();

        // Kiểm tra mật khẩu và nhập lại mật khẩu có khớp nhau không
        if (!matKhau.equals(nhapLaiMatKhau)) {
            Toast.makeText(this, "Mật khẩu không khớp. Vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng Account từ dữ liệu nhập vào
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(matKhau);
        account.setFullName(fullName);
        account.setNumberPhone(numberPhone);

        // Gọi phương thức đăng ký từ ProductInterface
        ProductInterface.GETAPI().accountfeedback(account).enqueue(new Callback<Accountfeedback>() {
            @Override
            public void onResponse(Call<Accountfeedback> call, Response<Accountfeedback> response) {
                if (response.isSuccessful()) {
                    // Đăng ký thành công
                    Toast.makeText(DangKy.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                    // Chuyển sang activity ManHinhNhanVien
                    Intent intent = new Intent(DangKy.this, DangNhap.class);
                    startActivity(intent);
                    finish(); // Kết thúc activity hiện tại

                } else {
                    // Đăng ký không thành công
                    Toast.makeText(DangKy.this, "Đăng ký không thành công. Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Accountfeedback> call, Throwable t) {
                // Xử lý lỗi khi gửi yêu cầu đăng ký
                Toast.makeText(DangKy.this, "Đã xảy ra lỗi. Vui lòng kiểm tra kết nối và thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
