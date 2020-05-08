package com.example.yhy.justfortest.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yhy.justfortest.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder>  {
    private Context context;
    private List<String> list;
    public CardAdapter(Context context,List<String>list1){
        this.context = context;
        list=list1;
        list.add("???");
    }

    private CardAdapter.OnitemClick onitemClick;   //定义点击事件接口

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (CardAdapter.OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }
    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }

    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //找到item的布局
        View view= LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new CardAdapter.MyViewHolder(view);//将布局设置给holder
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.MyViewHolder holder, final int position) {
        if (position==list.size()-1){
            Resources resources = context.getResources();
            Drawable drawable = resources.getDrawable(R.drawable.add_card);
            holder.rl.setBackground(drawable);
        }else{
            String str=list.get(position);
            holder.card_account.setText(str.substring(str.length()-4,str.length()));

        }
        Log.e("???","zxzcz");
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onitemClick.onItemClick(position);
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView card_account;
        RelativeLayout rl;
        public MyViewHolder(View itemView) {
            super(itemView);
            card_account=itemView.findViewById(R.id.tv_card_item);
            rl=itemView.findViewById(R.id.rl_card_item);

        }
    }
}
