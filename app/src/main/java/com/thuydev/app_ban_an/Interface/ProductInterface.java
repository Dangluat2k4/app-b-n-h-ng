package com.thuydev.app_ban_an.Interface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thuydev.app_ban_an.DTO.CategoryDTO;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.DTO.ProductDetailDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductInterface {
   public static String BASE_URL = "http://10.0.2.2:3000/";
    public  static  String idUser = "id Fake";

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

    @GET("apiuser/product")
    Call<List<ProductDTO>> GetListProducts();
    @GET("apiuser/product/{id}")
    Call<ProductDTO> GetProduct(@Path("id") String id);
    @GET("apiuser/productdetail")
    Call<List<ProductDetailDTO>> GetProductDetails();
    @GET("apiuser/productdetail/{id}")
    Call<ProductDetailDTO> GetProductDetail(@Path("id") String id);
    @GET("apiuser/category")
    Call<List<CategoryDTO>> GetListCategory();
    @GET("apiuser/product/category/{id}")
    Call<List<ProductDTO>> GetListProductToCate(@Path("id")String id);

    @GET("apiuser/user/cart/{id}")
    Call<List<CartDTO>> GetListCarts(@Path("id") String id);
    @POST("apiuser/user/cart/add")
    Call<String> AddCart(@Body CartDTO dto);
    @PUT("apiuser/cart/edit/{id}")
    Call<String> EditCart(@Path("id")String id);
    @DELETE("apiuser/cart/delete/{id}")
    Call<String> DeleteCart(@Path("id")String id);


}
