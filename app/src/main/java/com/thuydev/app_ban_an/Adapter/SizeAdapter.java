package com.thuydev.app_ban_an.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.thuydev.app_ban_an.databinding.ItemKichcoBinding;
import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder>{
    Context context;
    List<String> list;
    ItemKichcoBinding binding;
    int i = -1;
    public SizeAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemKichcoBinding.inflate(((Activity)context).getLayoutInflater());
        return new ViewHolder(binding.getRoot());
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.size.setText(list.get(position));
        String cam = "#FF4800";
        String vang = "#FFC107";
        if (position==i){
            holder.item.setBackgroundColor(Color.parseColor(cam));
        }else {
            holder.item.setBackgroundColor(Color.parseColor(vang));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public String GetSize(){
        if(i==-1){
            return null;
        }
        return list.get(i);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView size;
        LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            size = binding.tvKichcoShow1;
            item = binding.cvKichco;
        }
    }
}
