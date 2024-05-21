package com.thuydev.app_ban_an.Interface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thuydev.app_ban_an.DTO.ProductDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ProductInterface {
    static String BASE_URL = "http://10.0.2.2:3000/";


    public  static  ProductInterface GETAPI(){

            // Tạo Gson converter
            Gson gson = new GsonBuilder().setLenient().create();

            // Khởi tạo Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            // Tạo interface
            return retrofit.create(ProductInterface.class);
    }

    @GET("apiadmin/product")
    Call<List<ProductDTO>> lay_danh_sach();
}
