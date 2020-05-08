package com.example.yhy.justfortest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yhy.justfortest.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private Context context;
    private List<String> mess;
    private List<String> times;
    public MessageAdapter(Context context,List<String> mess,List<String> times){
        this.context = context;
       this.mess=mess;
       this.times=times;
    }
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //找到item的布局
        View view= LayoutInflater.from(context).inflate(R.layout.item_message,parent,false);
        return new MessageAdapter.MyViewHolder(view);//将布局设置给holder
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        holder.message.setText(mess.get(position));
        holder.time.setText(times.get(position).substring(0,10));
    }

    @Override
    public int getItemCount() {
        return mess.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        TextView time;
        public MyViewHolder(View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message_text);
            time=itemView.findViewById(R.id.message_time);
        }
    }
}
