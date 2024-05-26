package com.thuydev.app_ban_an.frm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thuydev.app_ban_an.databinding.FragmentFrgQuanLyHoaDonBinding;
import com.thuydev.app_ban_an.databinding.FragmentGioHangBinding;

public class fragment_hoadon extends Fragment {
FragmentFrgQuanLyHoaDonBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFrgQuanLyHoaDonBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
