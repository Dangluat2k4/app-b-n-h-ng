package com.thuydev.app_ban_an.frm;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.thuydev.app_ban_an.Account.ChangePasswordRequest;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordDialogFragment extends DialogFragment {
    private EditText edtNhapMail;
    private EditText edtNhapMKMoi;
    private Button btnDoiMK;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog__doi_mat_khau, container, false);

        edtNhapMail = view.findViewById(R.id.edt_nhapmail);
        edtNhapMKMoi = view.findViewById(R.id.edt_nhapmkmoi);
        btnDoiMK = view.findViewById(R.id.btn_doiMK);

        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtNhapMail.getText().toString().trim();
                String newPassword = edtNhapMKMoi.getText().toString().trim();

                if (email.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                changePassword(email, newPassword);
            }
        });

        return view;
    }
    private void changePassword(String email, String newPassword) {
        ChangePasswordRequest request = new ChangePasswordRequest(email, newPassword);
        ProductInterface apiService = ProductInterface.GETAPI();
        Call<ResponseBody> call = apiService.changePassword(request);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        dismiss();
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(requireContext(), getTheme());
    }
}
