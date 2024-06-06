package com.thuydev.app_ban_an.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.app_ban_an.Account.Account;
import com.thuydev.app_ban_an.DTO.Bill;
import com.thuydev.app_ban_an.DTO.BillDetail;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.DangNhap;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;
import com.thuydev.app_ban_an.databinding.DialogThemHangBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XacNhanDonHangAdapter extends RecyclerView.Adapter<XacNhanDonHangAdapter.ViewHolder>{
    private final Context context;
    private final List<BillDetail> listsp;
    List<ProductDTO> listPro ;
    private List<Account> accountList;

    public XacNhanDonHangAdapter(Context context, List<BillDetail> listsp, List<Account> lists) {
        this.context = context;
        this.listsp = listsp;
        accountList = lists;
        listPro = new ArrayList<>();
        GetListPro();
       accountList = GetAccount();
    }

    private void GetListPro() {
        Call<List<ProductDTO>> call = ProductInterface.GETAPI().GetListProducts();
        call.enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if(response.isSuccessful()){
                    listPro.clear();
                    listPro.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable throwable) {

            }
        });
    }

    private List<Account> GetAccount() {
        final List<Account>[] lists = new List[]{new ArrayList<>()};
        Call<List<Account>> call = ProductInterface.GETAPI().GetListAccount();
        call.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.isSuccessful()) {
                    lists[0].clear();
                    lists[0].addAll(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Account>> call, Throwable throwable) {

            }
        });
        return lists[0];
    }
    private void GetAccount(String id, TextView tv_tenkh, TextView tv_sdt,TextView tv_diachi) {
        for (Account item : accountList
        ) {
            if (item.get_id().equals(id)) {
                tv_tenkh.setText("Tên khách hàng: " +item.getFullName());
                tv_sdt.setText("Số điện thoại: " +item.getNumberPhone());
                tv_diachi.setText("Địa chỉ: " +item.getMyAddress());
                return;
            }
        }
    }
    @NonNull
    @Override
    public XacNhanDonHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ql_don_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XacNhanDonHangAdapter.ViewHolder holder, int position) {
        BillDetail detail = listsp.get(position);
        GetAccount(detail.getIDUser(), holder.tv_tenkh, holder.tv_sdt,holder.tv_diachi);
        holder.tv_idDon.setText("Ma hoa don: "+ detail.get_id());
        holder.tv_gia.setText("Giá: "+String.valueOf(detail.getTotal()));
        holder.tv_soluong.setText("Số lượng: " +String.valueOf(detail.getAmount()));
        // Sự kiện click cho button ibtn_Huy
        holder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác định vị trí của phần tử được click
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    // Hiển thị hộp thoại xác nhận
                    new AlertDialog.Builder(context)
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc chắn muốn xóa mục này không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Xác nhận xóa
                                    BillDetail detail = listsp.get(adapterPosition);
                                    // Xóa dữ liệu trên app
                                    listsp.remove(adapterPosition);
                                    notifyItemRemoved(adapterPosition);
                                    // Cập nhật trạng thái trên MongoDB
                                    updateStatus(detail.getIDBill(), adapterPosition);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        holder.btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác định vị trí của phần tử được click
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    // Hiển thị hộp thoại xác nhận
                    new AlertDialog.Builder(context)
                            .setTitle("Xác nhận hóa đơn")
                            .setMessage("Bạn có chắc chắn muốn xác nhận mục này không?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Xác nhận xóa
                                    BillDetail detail = listsp.get(adapterPosition);
                                    // Xóa dữ liệu trên app
                                    listsp.remove(adapterPosition);
                                    notifyItemRemoved(adapterPosition);
                                    // Cập nhật trạng thái trên MongoDB
                                    chapnhan(detail.getIDBill(), adapterPosition);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDataDetail(position);
            }
        });
    }
    private void ShowDataDetail(int p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogThemHangBinding binding1 = DialogThemHangBinding.inflate(((Activity)context).getLayoutInflater(),null,false);
        builder.setView(binding1.getRoot());
        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding1.edtThemhang.setVisibility(View.GONE);
        binding1.ibtnAddhang.setVisibility(View.GONE);
        binding1.tvTittle2.setText("Đơn hàng chi tiết");
        // viet code o day
        Log.e("TAG", "ShowDataDetail: "+listsp.get(p).getIDBill() );
       GetBill(listsp.get(p).getIDBill(),binding1);

    }

    private void GetBill(String idBill, DialogThemHangBinding binding1) {
       Call<Bill> billCall = ProductInterface.GETAPI().GetBill(idBill);
        billCall.enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if (response.isSuccessful()){
                    ChiTietDonHangMuaAdapter chiTietDonHangMuaAdapter = new ChiTietDonHangMuaAdapter(response.body().getIDProduct(),listPro,context);
                    binding1.listHang.setAdapter(chiTietDonHangMuaAdapter);

                }
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable throwable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listsp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_idDon, tv_tenkh, tv_diachi, tv_sdt, tv_gia, tv_soluong;
        ImageButton btn_xoa, btn_xacnhan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_idDon = itemView.findViewById(R.id.tv_idDon);
            tv_tenkh = itemView.findViewById(R.id.tv_tenKhach);
            tv_diachi = itemView.findViewById(R.id.tv_diaChi);
            tv_sdt = itemView.findViewById(R.id.tv_sdt);
            tv_gia = itemView.findViewById(R.id.tv_gia);
            tv_soluong = itemView.findViewById(R.id.tv_soLuong_);
            btn_xoa = itemView.findViewById(R.id.ibtn_Huy);
            btn_xacnhan = itemView.findViewById(R.id.ibtn_XacNhan);
        }
    }
    // Trong phương thức updateStatus
    private void updateStatus(String id, int position) {
        // Gọi API để cập nhật trạng thái từ 0 thành 2 trong MongoDB
        Bill bill = new Bill();
        bill.setIDSeller(DangNhap.dangNhap.account.get_id());
        Call<Void> call = ProductInterface.GETAPI().Xoahoadon(id,bill );
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Trường hợp thành công, không cần làm gì thêm
                } else {
                    // Xử lý lỗi nếu có
                    Toast.makeText(context, "Lỗi khi cập nhật trạng thái", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý lỗi nếu có
                Toast.makeText(context, "Lỗi khi kết nối đến server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void chapnhan(String id, int position) {
        // Gọi API để cập nhật trạng thái từ 0 thành 2 trong MongoDB
        Bill bill = new Bill();
        bill.setIDSeller(DangNhap.dangNhap.account.get_id());
        Call<Void> call = ProductInterface.GETAPI().chapnhanhoadon(id,bill);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Trường hợp thành công, không cần làm gì thêm
                } else {
                    // Xử lý lỗi nếu có
                    Toast.makeText(context, "Lỗi khi cập nhật trạng thái", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý lỗi nếu có
                Toast.makeText(context, "Lỗi khi kết nối đến server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
