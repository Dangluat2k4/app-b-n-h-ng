package com.thuydev.app_ban_an.frm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thuydev.app_ban_an.R;

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
        ChangePasswordDialogFragment dialogFragment = new ChangePasswordDialogFragment();
        dialogFragment.show(getParentFragmentManager(), "ChangePasswordDialogFragment");
    }
}
