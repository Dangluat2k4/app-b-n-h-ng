package com.thuydev.app_ban_an.frm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
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
    List<CategoryDTO>list,tempA;
    String TAG = "vvvvvvvvvvv";
    CategoryAdapter categoryAdapter;
    FragmentCuahangBinding binding;

    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCuahangBinding.inflate(getActivity().getLayoutInflater());
        View view =binding.getRoot()  ;

        list = new ArrayList<>();
        tempA = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvCuaHang.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(getContext(), list);
        binding.rcvCuaHang.setAdapter(categoryAdapter);
        getCategory();
        binding.seachHangKH.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Search(newText);
                return false;
            }
        });
        return view;
    }

    private void Search(String newText) {
        if(newText.isEmpty()){
            getCategory();
        }else {
            List<CategoryDTO> temp = new ArrayList<>();
            for (CategoryDTO item:tempA) {
                if(item.getNameCategory().toLowerCase().trim().contains(newText.toLowerCase().trim())){
                    temp.add(item);
                    Log.e(TAG, "Search: "+item.getNameCategory() );
                }
            }
            list.clear();
            list.addAll(temp);
            categoryAdapter.notifyDataSetChanged();

        }
    }

    public void getCategory() {
       Call<List<CategoryDTO>> call = ProductInterface.GETAPI().GetListCategory();
       call.enqueue(new Callback<List<CategoryDTO>>() {
           @Override
           public void onResponse(Call<List<CategoryDTO>> call, Response<List<CategoryDTO>> response) {
               if(response.isSuccessful()){
                   list.clear();
                   list.addAll(response.body());
                   tempA.addAll(list);
                   categoryAdapter.notifyDataSetChanged();

               }else Log.e(TAG, "onResponse: "+response.body() );
           }

           @Override
           public void onFailure(Call<List<CategoryDTO>> call, Throwable throwable) {

           }
       });
    }


}
