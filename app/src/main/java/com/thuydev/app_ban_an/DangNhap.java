package com.thuydev.app_ban_an;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.thuydev.app_ban_an.Account.Account;
import com.thuydev.app_ban_an.Account.ChangePasswordRequest;
import com.thuydev.app_ban_an.Interface.IUpdateData;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.databinding.DialogDoiMatKhauBinding;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhap extends AppCompatActivity {

   public static DangNhap dangNhap ;
    public Account account;
    public IUpdateData iUpdateData = new IUpdateData() {
    @Override
    public void UpdateData(Account account) {

    }

    @Override
    public void UpdateData(ProfileUser profileUser) {
        ReloadAccount(profileUser);


    }
};
    SharedPreferences sharedPreferences ;
    private EditText edtEmail, edtMatKhau;
    String email;
    String matKhau;
    private AppCompatButton btnDangNhap, btnDangKy;
    private CheckBox check;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        // Ánh xạ EditText
        dangNhap = this;
        sharedPreferences = getSharedPreferences("Data",MODE_PRIVATE);
        edtEmail = findViewById(R.id.edt_email_dangnhap);
        edtMatKhau = findViewById(R.id.edt_matkhau_dangnhap);
        check = findViewById(R.id.cbk_luu);
        btnDangNhap = findViewById(R.id.btn_dangnhap);
        btnDangKy = findViewById(R.id.btn_dangky);
        btnDangNhap.setOnClickListener(v -> dangNhap());
        TextView quenPass = findViewById(R.id.tv_quenpass);
        CheckData();
        // Bắt sự kiện khi nhấn nút "Đăng ký"
        btnDangKy.setOnClickListener(v -> {
            // Chuyển sang activity DangKy
            Intent intent = new Intent(DangNhap.this, DangKy.class);
            startActivity(intent);
        });
        quenPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoiMatKhau();
            }
        });
    }

    public void CheckData() {
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

    public void SaveAccount(String email, String matKhau) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("Username", email);
        edit.putString("pass", matKhau);
        edit.putBoolean("remeber", check.isChecked());
        edit.apply();
    }

    private void dangNhap() {
        // Lấy thông tin từ các trường EditText
        email = edtEmail.getText().toString();
        matKhau = edtMatKhau.getText().toString();
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
                Log.e("TAG", "onFailure: "+t );
                Toast.makeText(DangNhap.this, "Đã xảy ra lỗi. Vui lòng kiểm tra kết nối và thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ReloadAccount(ProfileUser profileUser){
        email = edtEmail.getText().toString();
        matKhau = edtMatKhau.getText().toString();
        ProductInterface.GETAPI().login(new Account(email, matKhau)).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()){
                    account = response.body();
                    profileUser.user = response.body();
                    profileUser.ShowDataHeader();
                    Log.e("TAG", "UpdateData: "+profileUser.user );
                }


            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                // Xử lý lỗi khi gửi yêu cầu đăng nhập
                Toast.makeText(DangNhap.this, "Đã xảy ra lỗi. Vui lòng kiểm tra kết nối và thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void DoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogDoiMatKhauBinding binding = DialogDoiMatKhauBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());
        Dialog dialog = builder.create();
        dialog.show();
        binding.title.setText("Quên mật khẩu");

        binding.btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtNhapmail.getText().toString().trim();
                String newPassword = binding.edtNhapmkmoi.getText().toString().trim();
                if (email.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(DangNhap.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                changePassword(email, newPassword, dialog);
            }
        });
    }
    private void changePassword(String email, String newPassword, Dialog dialog) {
        ChangePasswordRequest request = new ChangePasswordRequest(email, newPassword);
        ProductInterface apiService = ProductInterface.GETAPI();
        Call<ResponseBody> call = apiService.changePassword(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Toast.makeText(DangNhap.this, "Mật khẩu đã được cập nhập", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(DangNhap.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText( DangNhap.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

