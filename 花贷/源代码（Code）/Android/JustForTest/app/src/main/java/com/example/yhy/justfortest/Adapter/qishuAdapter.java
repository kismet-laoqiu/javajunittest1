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
import com.example.yhy.justfortest.classs.Good;

import java.util.ArrayList;
import java.util.List;

public class qishuAdapter extends RecyclerView.Adapter<qishuAdapter.MyViewHolder>{
    private Context context;
    private List<String> list;
    private List<String> list_re;
    private List<String> num;
    public qishuAdapter(Context context,List<String>list1,List<String>li){
        this.context = context;
        list=list1;
        num=li;
        list_re=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            list_re.add(String.valueOf(Integer.parseInt(Good.num)*Double.parseDouble(Good.per_price)-Double.parseDouble(list.get(i))));

        }
    }
    private qishuAdapter.OnitemClick onitemClick;   //定义点击事件接口

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (qishuAdapter.OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }
    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }

    @Override
    public qishuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //找到item的布局
        View view= LayoutInflater.from(context).inflate(R.layout.huaqi_item,parent,false);
        return new qishuAdapter.MyViewHolder(view);//将布局设置给holder
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull qishuAdapter.MyViewHolder holder, final int position) {

        String str=list.get(position);

        holder.num.setText(num.get(position));
        holder.huaqi_price.setText(str);
        Double temp=0.0;
        if (position<=4)
            temp=(Double.parseDouble(str)*Double.parseDouble(num.get(position).substring(0,1))-Double.parseDouble(Good.per_price)*Double.parseDouble(Good.num));
        else{
            temp=(Double.parseDouble(str)*Double.parseDouble(num.get(position).substring(0,2))-Double.parseDouble(Good.per_price)*Double.parseDouble(Good.num));

        }
        if (position==0)
            holder.huaqi_price_re.setText("0");
        else
        holder.huaqi_price_re.setText(String.valueOf(temp.intValue()));

        Log.e("???","zxzcz");
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onitemClick.onItemClick(position);
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView huaqi_price;
        RelativeLayout rl;
        TextView huaqi_price_re;
        TextView num;

        public MyViewHolder(View itemView) {
            super(itemView);
            huaqi_price    =itemView.findViewById(R.id.huaqi_item_pri);
            huaqi_price_re =itemView.findViewById(R.id.huaqi_item_pri_rest);
            num            =itemView.findViewById(R.id.huaqi_num);
            rl=itemView.findViewById(R.id.xxx);
        }
    }
}
