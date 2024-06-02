package com.thuydev.app_ban_an.frm;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.thuydev.app_ban_an.Account.ChangePasswordRequest;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;
import com.thuydev.app_ban_an.databinding.DialogDoiMatKhauBinding;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_doimk_nhanvien extends Fragment {
    private Button btnOpenDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doimk_nhanvien, container, false);
        btnOpenDialog = view.findViewById(R.id.btn_open_dialog);

        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePasswordDialog();
            }
        });

        return view;
    }
    private void openChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        DialogDoiMatKhauBinding binding = DialogDoiMatKhauBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());
        Dialog dialog = builder.create();
        dialog.show();
        binding.btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtNhapmail.getText().toString().trim();
                String newPassword = binding.edtNhapmkmoi.getText().toString().trim();
                if (email.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                changePassword(email, newPassword,dialog);
            }
        });

    }
    private void changePassword(String email, String newPassword,Dialog dialog) {
        ChangePasswordRequest request = new ChangePasswordRequest(email, newPassword);
        ProductInterface apiService = ProductInterface.GETAPI();
        Call<ResponseBody> call = apiService.changePassword(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
