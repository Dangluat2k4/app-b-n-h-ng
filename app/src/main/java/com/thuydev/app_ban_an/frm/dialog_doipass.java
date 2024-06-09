package com.thuydev.app_ban_an.frm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.thuydev.app_ban_an.Account.ChangePasswordRequest;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dialog_doipass  extends DialogFragment {
    EditText edtemail, edtnewpass;
    AppCompatButton btndoipass;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog__doi_mat_khau, null);

        edtemail = view.findViewById(R.id.edt_nhapmail);
        edtnewpass = view.findViewById(R.id.edt_nhapmkmoi);
        btndoipass = view.findViewById(R.id.btn_doiMK);
        btndoipass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý logic đổi mật khẩu ở đây
                String email = edtemail.getText().toString();
                String newPass = edtnewpass.getText().toString();
                if (email.isEmpty() || newPass.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                changePassword(email, newPass);
            }
        });


        builder.setView(view);
        return builder.create();
    }
    private void changePassword(String email, String newPass) {
        ChangePasswordRequest request = new ChangePasswordRequest(email, newPass);
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
}
