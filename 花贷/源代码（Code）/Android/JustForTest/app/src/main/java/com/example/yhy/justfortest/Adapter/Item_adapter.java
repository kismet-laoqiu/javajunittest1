package com.example.yhy.justfortest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yhy.justfortest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Item_adapter extends RecyclerView.Adapter<Item_adapter.MyViewHolder> {
    private Context context;
    private List<String>list;
    public Item_adapter(Context context,List<String>list1){
        this.context = context;
        list=list1;
    }
    @Override
    public Item_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //找到item的布局
        View view= LayoutInflater.from(context).inflate(R.layout.recycle_item,parent,false);
        return new Item_adapter.MyViewHolder(view);//将布局设置给holder
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tag.setText(list.get(position));
        if(position==0){
            holder.tag.setTextSize(18);
            holder.tag.setTextColor(Color.BLACK);
            holder.tag_2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tag;
        TextView tag_2;
        public MyViewHolder(View itemView) {
            super(itemView);
            tag=itemView.findViewById(R.id.recycle_item_name);
            tag_2=itemView.findViewById(R.id.recycle_item_block);
        }
    }
}
