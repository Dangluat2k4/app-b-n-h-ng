package com.thuydev.app_ban_an.Interface;

import com.thuydev.app_ban_an.DTO.ProductDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductInterface {
    @GET("apiadmin/product")
    Call<List<ProductDTO>> lay_danh_sach();
}
