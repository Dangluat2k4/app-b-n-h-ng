package com.thuydev.app_ban_an.Interface;

import com.thuydev.app_ban_an.Model.SanPhamUserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SanPhamInterface {
    @GET("apiUser/product")
    Call<List<SanPhamUserDTO>> lay_danh_sach();
}
