package com.thuydev.app_ban_an.frm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.thuydev.app_ban_an.ProfileUser;
import com.thuydev.app_ban_an.R;
import com.thuydev.app_ban_an.databinding.TabKhoanchiBinding;
import com.thuydev.app_ban_an.databinding.TabThongtincanhanBinding;

public class Fragment_thongtin extends Fragment {
    TabThongtincanhanBinding binding;
    ProfileUser profileUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TabThongtincanhanBinding.inflate(getLayoutInflater(),container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileUser = ProfileUser.profileUser;
        binding.llThongtincanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUser.UpdateProfile();
            }
        });
        binding.llDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });
        binding.llLichsumua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUser.LichSuMuaHang();
            }
        });
        binding.llDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUser.DiaChi();
            }
        });
        binding.llDoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUser.DoiMatKhau();
            }
        });
        binding.llLichnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUser.LichSuNap();
            }
        });
    }

    private void LogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                profileUser.LogOut();
            }
        });
        builder.create().show();

    }
}
