package com.thuydev.app_ban_an;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.thuydev.app_ban_an.Account.Account;
import com.thuydev.app_ban_an.Account.ChangePasswordRequest;
import com.thuydev.app_ban_an.Adapter.Adapter_diachi;
import com.thuydev.app_ban_an.Adapter.Adapter_thongtin;
import com.thuydev.app_ban_an.Adapter.BillAdapter;
import com.thuydev.app_ban_an.Adapter.RechargeAdapter;
import com.thuydev.app_ban_an.DTO.Bill;
import com.thuydev.app_ban_an.DTO.BillDetail;
import com.thuydev.app_ban_an.DTO.Recharge;
import com.thuydev.app_ban_an.Extentions.Extention;
import com.thuydev.app_ban_an.Extentions.RegexCheckName;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.databinding.ActivityThongtintaikhoanBinding;
import com.thuydev.app_ban_an.databinding.DialogDoiMatKhauBinding;
import com.thuydev.app_ban_an.databinding.DialogLichsuBinding;
import com.thuydev.app_ban_an.databinding.DialogNaptienBinding;
import com.thuydev.app_ban_an.databinding.DialogThemHangBinding;
import com.thuydev.app_ban_an.databinding.DialogUpdateprofileBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileUser extends AppCompatActivity {
    public static ProfileUser profileUser;
    ActivityThongtintaikhoanBinding binding;
    Adapter_thongtin adapterThongtin;
    TabLayoutMediator mediator;
    Account user;
    Uri uri = null;
    ImageView tempAvatar, tempBill;
    int change = 0, isNapTien = 0;
    private static final int CODE_QUYEN = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongtintaikhoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = DangNhap.dangNhap.account;
        profileUser = this;
        ShowDataHeader();
        ShowTab();
        HeaderData();
        binding.llNaptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NapTien();
            }
        });
    }

    private void NapTien() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogNaptienBinding binding1 = DialogNaptienBinding.inflate(getLayoutInflater());
        builder.setView(binding1.getRoot());
        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tempBill = binding1.imvAnhchupmanhinh;
        binding1.btnHuyYc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        binding1.imvAnhchupmanhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeucauquyen(ProfileUser.this);
                isNapTien = 0;
            }
        });
        binding1.btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding1.edtSotien.getText().toString().isEmpty()||binding1.edtEmailNaptien.getText().toString().isEmpty())
                {
                    Toast.makeText(ProfileUser.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                String time =String.format("%02d/%02d/%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH), (Calendar.getInstance().get(Calendar.MONTH)+1), Calendar.getInstance().get(Calendar.YEAR)) +"/ - "+
                        String.format("%02d:%02d:%02d",new Date().getHours(),new Date().getMinutes(),new Date().getSeconds());
                String email = binding1.edtEmailNaptien.getText().toString();
                if(RegexCheckName.CheckRegex(RegexCheckName.CheckEmail,email)){
                    Toast.makeText(ProfileUser.this, "Hãy nhập nhập đúng email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Long.parseLong(binding1.edtSotien.getText().toString())<=100000){
                    Toast.makeText(ProfileUser.this, "Số tiền naạp phải trên 100.000 đ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(uri==null){
                    Toast.makeText(ProfileUser.this, "Bạn phải chọn ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }
                File file = new File(Extention.getRealPath(ProfileUser.this,uri));
               Call<String> call = ProductInterface.GETAPI().YeuCauNap(
                       UpImage("imgAnh",file.getName(),ChangTypeFile(file)),
                       ChangTypeFile(email),
                       ChangTypeFile(user.get_id()),
                       ChangTypeFile(binding1.edtSotien.getText().toString()),
                       ChangTypeFile(time)
               );
               call.enqueue(new Callback<String>() {
                   @Override
                   public void onResponse(Call<String> call, Response<String> response) {
                       if(response.isSuccessful())
                           Toast.makeText(ProfileUser.this, response.body(), Toast.LENGTH_SHORT).show();
                       dialog.dismiss();
                       uri=null;
                       dialog.dismiss();
                   }

                   @Override
                   public void onFailure(Call<String> call, Throwable throwable) {

                   }
               });
            }
        });
    }


    public void ShowDataHeader() {

        binding.tvUsernameKhach.setText(user.getFullName());
        binding.tvSoduKhach.setText(Extention.MakeStyleMoney(user.getCredit()) + " đ");
        Glide.with(this).load(ProductInterface.BASE_URL + user.getAvatar()).error(R.drawable.user1).into(binding.imvAvatar);
    }

    private void HeaderData() {
        binding.imvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.imvUpdatethongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
                isNapTien = 1;
            }
        });
    }

    private void ShowTab() {
        adapterThongtin = new Adapter_thongtin(this);
        binding.viewPage2ThongtinKhach.setAdapter(adapterThongtin);
        mediator = new TabLayoutMediator(binding.tabLayoutThongtinkhach, binding.viewPage2ThongtinKhach, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("Thông tin cá nhân");
                } else {
                    tab.setText("Khoản chi trong tháng");
                }
            }
        });
        mediator.attach();
    }

    public void GetBill(List<Bill> billList, List<BillDetail> billDetails, BillAdapter billAdapter) {
        Call<List<Bill>> call = ProductInterface.GETAPI().GetBills(user.get_id());
        call.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if (response.isSuccessful()) {
                    billList.clear();
                    billList.addAll(response.body());
                    GetBillDetail(billDetails, billAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable throwable) {

            }
        });
    }

    private void GetBillDetail(List<BillDetail> billDetails, BillAdapter billAdapter) {
        Call<List<BillDetail>> call = ProductInterface.GETAPI().GetBillDetails(user.get_id());
        call.enqueue(new Callback<List<BillDetail>>() {
            @Override
            public void onResponse(Call<List<BillDetail>> call, Response<List<BillDetail>> response) {
                if (response.isSuccessful()) {
                    billDetails.clear();
                    billDetails.addAll(response.body());
                    billAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BillDetail>> call, Throwable throwable) {

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
                        Toast.makeText(ProfileUser.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ProfileUser.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ProfileUser.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void LogOut() {
        finishAffinity();
        SharedPreferences.Editor editor = getSharedPreferences("Data", MODE_PRIVATE).edit();
        editor.remove("Username");
        editor.remove("pass");
        editor.remove("remeber");
        editor.apply();
        Intent intent = new Intent(this, DangNhap.class);
        startActivity(intent);
    }

    public void UpdateProfile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogUpdateprofileBinding binding1 = DialogUpdateprofileBinding.inflate(getLayoutInflater());
        builder.setView(binding1.getRoot());
        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tempAvatar = binding1.imvAddAnhEdit;
        Glide.with(this).load(ProductInterface.BASE_URL + user.getAvatar()).error(R.drawable.user1).into(binding1.imvAddAnhEdit);
        binding1.edtEmailEdit.setText(user.getEmail());
        binding1.edtHotenEdit.setText(user.getFullName());
        binding1.edtSdtEdit.setText(user.getNumberPhone());
        // update profile in here
        binding1.imvAddAnhEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeucauquyen(ProfileUser.this);
            }
        });
        binding1.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, name, numPhome;
                email = binding1.edtEmailEdit.getText().toString();
                name = binding1.edtHotenEdit.getText().toString();
                numPhome = binding1.edtSdtEdit.getText().toString();
                if (email.isEmpty() || name.isEmpty() || numPhome.isEmpty()) {
                    Toast.makeText(ProfileUser.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CheckRegex(RegexCheckName.CheckPhone, numPhome)) {
                    Toast.makeText(ProfileUser.this, "Số điện thoại không đúng vui lòng nhập lại ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CheckRegex(RegexCheckName.CheckEmail, email)) {
                    Toast.makeText(ProfileUser.this, "Email không đúng vui lòng nhập lại ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.length() <= 5) {
                    Toast.makeText(ProfileUser.this, "Tên của bạn phải trên 5 ký tự ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (uri == null) {
                    Account newAcc = new Account();
                    newAcc.setEmail(email);
                    newAcc.setFullName(name);
                    newAcc.setNumberPhone(numPhome);
                    UpdateNoneImg(newAcc);
                } else {
                    UpdateImg(email, name, numPhome);
                }
                dialog.dismiss();
            }
        });
    }

    private boolean CheckRegex(String regex, String input) {
        return !Pattern.compile(regex).matcher(input).matches();
    }

    private void SaveAccount(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("Username", email);
        edit.apply();
    }

    private void UpdateImg(String email, String name, String numPhome) {
        File file = new File(Extention.getRealPath(this, uri));
        Call<String> call = ProductInterface.GETAPI().UpdateProfile(user.get_id(),
                UpImage("imgAnh", file.getName(), ChangTypeFile(file)),
                ChangTypeFile(email),
                ChangTypeFile(name),
                ChangTypeFile(numPhome)
        );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful())
                    Toast.makeText(ProfileUser.this, response.body(), Toast.LENGTH_SHORT).show();
                SaveAccount(email);
                DangNhap.dangNhap.CheckData();
                DangNhap.dangNhap.iUpdateData.UpdateData(ProfileUser.this);
                ShowDataHeader();
                uri = null;


            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    private RequestBody ChangTypeFile(String data) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), data);
    }

    private RequestBody ChangTypeFile(File data) {
        return RequestBody.create(MediaType.parse("image/*"), data);
    }

    private MultipartBody.Part UpImage(String fiel, String name, RequestBody file) {
        return MultipartBody.Part.createFormData(fiel, name, file);
    }

    private void UpdateNoneImg(Account newAcc) {
        Call<String> call = ProductInterface.GETAPI().UpdateProfile(user.get_id(), newAcc);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    SaveAccount(newAcc.getEmail());
                    DangNhap.dangNhap.CheckData();
                    Toast.makeText(ProfileUser.this, response.body(), Toast.LENGTH_SHORT).show();
                    DangNhap.dangNhap.iUpdateData.UpdateData(ProfileUser.this);

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    private void XoaDiaChi(List<String> list_diaChi, int position, Adapter_diachi adapter) {
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(this);
        builder1.setTitle("Cảnh báo").setIcon(R.drawable.cancel).setMessage("Nếu bạn xác nhận dữ liệu sẽ mất mãi mãi");
        builder1.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // xóa phần tử list ở đây
                list_diaChi.remove(position);
                user.setAddress(list_diaChi);
                adapter.notifyDataSetChanged();
                UpdateNoneImg(user);
                Toast.makeText(ProfileUser.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });
        builder1.create().show();
    }

    private void ChonDiaChiGiaoHang(List<String> listDiaChi, int position) {
        user.setMyAddress(listDiaChi.get(position));
        UpdateNoneImg(user);
    }

    private void AdiaChi(EditText edt_diachi, Adapter_diachi adapter, List<String> listDiaChi) {
        Log.e("TAG", "AdiaChi: " + change);
        if (change == 0) {
            edt_diachi.setVisibility(View.VISIBLE);
            change = 1;
        } else {
            if (edt_diachi.getText().toString().isEmpty()) {
                Toast.makeText(this, "Người dùng không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }
            edt_diachi.setVisibility(View.GONE);
            change = 0;
            listDiaChi.add(edt_diachi.getText().toString().trim());
            adapter.notifyDataSetChanged();
            user.setMyAddress(edt_diachi.getText().toString().trim());
            user.setAddress(listDiaChi);
            UpdateNoneImg(user);
            edt_diachi.setText("");
        }
    }


    public void DiaChi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogThemHangBinding binding1 = DialogThemHangBinding.inflate(getLayoutInflater());
        builder.setView(binding1.getRoot());
        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // viet code o day
        final int change = 0;
        binding1.edtThemhang.setHint("Số nhà,Ngõ,Đường,Quận,Thành Phố");
        binding1.tvTittle2.setText("Địa chỉ");
        binding1.edtThemhang.setVisibility(View.GONE);
        List<String> list_diaChi = new ArrayList<>();
        if (user.getAddress() != null) {
            list_diaChi.addAll(user.getAddress());
        }
        Adapter_diachi adapter = new Adapter_diachi(list_diaChi, this);
        binding1.listHang.setAdapter(adapter);
        binding1.listHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                XoaDiaChi(list_diaChi, position, adapter);
                return false;
            }
        });
        binding1.listHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChonDiaChiGiaoHang(list_diaChi, position);
                dialog.dismiss();
            }
        });
        binding1.ibtnAddhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdiaChi(binding1.edtThemhang, adapter, list_diaChi);
            }
        });
    }

    public void LichSuMuaHang() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogLichsuBinding binding1 = DialogLichsuBinding.inflate(getLayoutInflater());
        builder.setView(binding1.getRoot());
        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // viet code o day
        List<Bill> billList = new ArrayList<>();
        List<BillDetail> billDetails = new ArrayList<>();
        BillAdapter billAdapter = new BillAdapter(billList, billDetails, this);
        binding1.rcvListLichsu.setAdapter(billAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding1.rcvListLichsu.setLayoutManager(layoutManager);
        GetBill(billList, billDetails, billAdapter);
    }

    public void DoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileUser.this);
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
                    Toast.makeText(ProfileUser.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                changePassword(email, newPassword, dialog);
            }
        });
    }

    public void LichSuNap() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogLichsuBinding binding1 = DialogLichsuBinding.inflate(getLayoutInflater());
        builder.setView(binding1.getRoot());
        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // viet code o day
        binding1.title.setText("Lịch sử nạp");
        List<Recharge> list = new ArrayList<>();
        RechargeAdapter rechargeAdapter = new RechargeAdapter(list,this);
        binding1.rcvListLichsu.setAdapter(rechargeAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding1.rcvListLichsu.setLayoutManager(layoutManager);
        GetLichSuNap(list,rechargeAdapter);
    }

    private void GetLichSuNap(List<Recharge> list, RechargeAdapter rechargeAdapter) {
        Call<List<Recharge>> call = ProductInterface.GETAPI().GetRecharge(user.get_id());
        call.enqueue(new Callback<List<Recharge>>() {
            @Override
            public void onResponse(Call<List<Recharge>> call, Response<List<Recharge>> response) {
                if(response.isSuccessful()){
                    list.clear();
                    list.addAll(response.body());
                    rechargeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Recharge>> call, Throwable throwable) {

            }
        });
    }

    public void KhoanChiTrongThang() {

    }

    //
    //
    //
    //
    public void layAnh() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_QUYEN) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                layAnh();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void yeucauquyen(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            layAnh();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String[] quyen = new String[]{android.Manifest.permission.READ_MEDIA_IMAGES};
            requestPermissions(quyen, CODE_QUYEN);
            return;
        }
        if (context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // xử lý sau
            layAnh();
        } else {
            String[] quyen = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(quyen, CODE_QUYEN);
        }
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        Intent intent = o.getData();
                        if (intent == null) {
                            return;
                        }
                        uri = intent.getData();
                        if (isNapTien == 1) {
                            Glide.with(ProfileUser.this).load(uri).into(tempAvatar);
                            //
                        } else {
                            Glide.with(ProfileUser.this).load(uri).into(tempBill);
                        }

                    }

                }
            });
}
