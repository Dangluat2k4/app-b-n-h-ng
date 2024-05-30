package com.thuydev.app_ban_an.frm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thuydev.app_ban_an.Adapter.CategoryAdapter;
import com.thuydev.app_ban_an.Adapter.ProductAdapter;
import com.thuydev.app_ban_an.DTO.CategoryDTO;
import com.thuydev.app_ban_an.DTO.ProductDTO;
import com.thuydev.app_ban_an.Interface.ProductInterface;
import com.thuydev.app_ban_an.R;

import com.thuydev.app_ban_an.databinding.FragmentCuahangBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_cuahang extends Fragment {
    List<CategoryDTO>list;
    String TAG = "vvvvvvvvvvv";
    CategoryAdapter categoryAdapter;
    FragmentCuahangBinding binding;

    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCuahangBinding.inflate(getActivity().getLayoutInflater());
        View view =binding.getRoot()  ;

        list = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvCuaHang.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(getContext(), list);
        binding.rcvCuaHang.setAdapter(categoryAdapter);
        getCategory();
        return view;
    }

    public void getCategory() {
       Call<List<CategoryDTO>> call = ProductInterface.GETAPI().GetListCategory();
       call.enqueue(new Callback<List<CategoryDTO>>() {
           @Override
           public void onResponse(Call<List<CategoryDTO>> call, Response<List<CategoryDTO>> response) {
               if(response.isSuccessful()){
                   list.clear();
                   list.addAll(response.body());
                   categoryAdapter.notifyDataSetChanged();

               }else Log.e(TAG, "onResponse: "+response.body() );
           }

           @Override
           public void onFailure(Call<List<CategoryDTO>> call, Throwable throwable) {

           }
       });
    }


}
