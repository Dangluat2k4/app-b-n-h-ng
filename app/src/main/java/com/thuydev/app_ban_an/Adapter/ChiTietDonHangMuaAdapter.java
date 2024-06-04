package com.thuydev.app_ban_an.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.thuydev.app_ban_an.DTO.ProductCart;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.DTO.ProductDetailDTO;
import com.thuydev.app_ban_an.Extentions.Extention;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;
import com.thuydev.app_ban_an.databinding.ItemListSanphamMuaBinding;

import java.util.List;

public class ChiTietDonHangMuaAdapter extends BaseAdapter {
    List<ProductCart> listProCart;
    List<ProductDTO> listPro;
    Context context;

    public ChiTietDonHangMuaAdapter(List<ProductCart> listProCart, List<ProductDTO> listPro, Context context) {
        this.listProCart = listProCart;
        this.listPro = listPro;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listProCart.size();
    }

    @Override
    public Object getItem(int position) {
        return listProCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemListSanphamMuaBinding binding = ItemListSanphamMuaBinding.inflate(((Activity)context).getLayoutInflater());
        ProductDTO productDTO = GetPro(listProCart.get(position).getIDProduct());
        binding.tvMaspCt.setText("Mã sản phẩm: "+listProCart.get(position).getIDProduct());
        binding.tvTenspCt.setText("Tên sản phẩm: "+productDTO.getNameProduct());
        binding.tvGiaCt.setText("Giá: "+Extention.MakeStyleMoney(productDTO.getPrice()));
        binding.tvSoluongCt.setText("Số lượng mua: "+listProCart.get(position).getAmount());
        binding.tvSoluongTrongKhoCt.setText("Kích cỡ : "+listProCart.get(position).getSize());
        if(productDTO.getImage().contains("https:")||productDTO.getImage().contains("http:")){
            Glide.with(context).load(productDTO.getImage())
                    .error(R.drawable.shape_btn)
                    .into(binding.imvAnhSpCt);
        }else {
            Glide.with(context).load(ProductInterface.BASE_URL_IMAGE +productDTO.getImage())
                    .error(R.drawable.shape_btn)
                    .into(binding.imvAnhSpCt);
        }
        return binding.getRoot();
    }

    private ProductDTO GetPro(String idProduct) {
        for (ProductDTO dto:listPro
             ) {
            if(idProduct.equals(dto.get_id())) return dto;
        }
        return new ProductDTO();
    }
}
