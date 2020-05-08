package com.example.yhy.justfortest.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yhy.justfortest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;//数据
    private List<String> list_price;
    private List<Integer> heightList;//装产出的随机数

    public ServiceAdapter(Context context,List<String>name,List<String>price){
        this.context = context;
        this.list = name;
        list_price=price;
        heightList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int height = new Random().nextInt(500) + 500;//[100,300)的随机数
            heightList.add(height);
        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //找到item的布局
        View view= LayoutInflater.from(context).inflate(R.layout.service_item,parent,false);
        return new MyViewHolder(view);//将布局设置给holder
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //填充数据
        holder.textView.setText(list.get(position) + "");
        holder.textView2.setText(list_price.get(position)+"");
        //由于需要实现瀑布流的效果,所以就需要动态的改变控件的高度了
        ViewGroup.LayoutParams params = holder.textView.getLayoutParams();
        params.height = heightList.get(position);
        holder.layout.setLayoutParams(params);
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout layout;
        TextView textView;
        TextView textView2;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_sevice_name);
            textView2=itemView.findViewById(R.id.item_sevice_price);
            layout=itemView.findViewById(R.id.service_item_layout);
        }
    }
}
