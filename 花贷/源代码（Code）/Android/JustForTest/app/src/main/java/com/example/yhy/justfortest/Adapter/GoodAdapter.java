package com.example.yhy.justfortest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.classs.Good;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yhy on 19-6-29.
 */

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.SimpleViewHolder> {
    private Context context;
    private List<Integer> heightList;
    private List<String> goods_list;

    private OnitemClick onitemClick;   //定义点击事件接口
    private OnLongClick onLongClick;  //定义长按事件接口

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }
    //定义设置长按事件监听的方法
    public void setOnLongClickListener (OnLongClick onLongClick) {
        this.onLongClick = onLongClick;
    }

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }
    //定义一个长按事件的接口
    public interface OnLongClick {
        void onLongClick(int position);
    }

    private static final int TYPE_HEADER = 1, TYPE_ITEM = 2;
    public GoodAdapter(Context context,String goods){
        this.context = context;
        try {
            Log.e("tag","1");
            JSONArray array=new JSONArray(goods);
            Log.e("tag","2");
            array.length();
            goods_list=new ArrayList<>();
            heightList=new ArrayList<Integer>();
            for (int i = 0; i < array.length(); i++) {
                int height = new Random().nextInt(200) + 200;//[100,300)的随机数
                heightList.add(height);
                goods_list.add(array.getJSONObject(i).toString());
            }
        } catch (JSONException e) {
            Log.e("tag",e.getMessage());
        }


    }
    class SimpleViewHolder extends RecyclerView.ViewHolder {
        // 头部的控件
        Banner header_banner;
        RecyclerView header_recycle;
        JSONArray goods;
        // 内容的控件
        LinearLayout item_lv;
        ImageView item_image;
        TextView item_name;
        TextView item_price;
        RelativeLayout head_lv;
        public SimpleViewHolder(View view) {
            super(view);
            try {
                goods=new JSONArray(Good.goods);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            header_recycle=view.findViewById(R.id.head_recycle);
            head_lv=view.findViewById(R.id.head);
            header_banner=view.findViewById(R.id.head_banner);
            item_lv=view.findViewById(R.id.good_lv);
            item_image = (ImageView) view.findViewById(R.id.good_item_im);
            item_name =  view.findViewById(R.id.good_item_name);
            item_price =  view.findViewById(R.id.good_item_price);

        }
    }

    @Override
    public int getItemViewType(int position) {
        // 位置为0的话类型为头部，其余为内容
        return  position=position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        // 加一是因为添加了头部
        return goods_list.size()+1;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
          // 根据类型填充不同布局
            case TYPE_HEADER:
                view = LayoutInflater.from(context).inflate(R.layout.head_shangcheng, parent, false);
                break;
            case TYPE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.item_good, parent, false);
                break;
        }

        return new SimpleViewHolder(view);
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setImageResource((Integer) path);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            return new ImageView(context);
        }
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        Log.e("??",position+"");
        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                Log.e(String.valueOf(TYPE_HEADER),position+"");
                // 根据view类型处理不同的操作
                // 获取cardview的布局属性，记住这里要是布局的最外层的控件的布局属性，如果是里层的会报cast错误
                StaggeredGridLayoutManager.LayoutParams clp = (StaggeredGridLayoutManager.LayoutParams) holder.head_lv.getLayoutParams();
                // 最最关键一步，设置当前view占满列数，这样就可以占据两列实现头部了
                clp.setFullSpan(true);
                List<Integer> images = new ArrayList<>();
                images.add(R.drawable.lunbo1);
                images.add(R.drawable.lunbo2);
                images.add(R.drawable.lunbo3);
                images.add(R.drawable.lunbo4);
                images.add(R.drawable.lunbo5);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
                holder.header_recycle.setLayoutManager(staggeredGridLayoutManager);
                List<String>tags=new ArrayList<>();
                tags.add("全部");
                tags.add("金融");
                tags.add("衣服");
                tags.add("化妆");
                tags.add("交通");
                tags.add("酒店");
                tags.add("鞋靴");
                Item_adapter adapter=new Item_adapter(context,tags);
                holder.header_recycle.setAdapter(adapter);
                holder.header_banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
                //设置图片加载器
                holder.header_banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                holder.header_banner.setImages(images);
                //设置轮播时间
                holder.header_banner.setDelayTime(2000);

                //banner设置方法全部调用完毕时最后调用
                 holder.header_banner.start();
                break;
            case TYPE_ITEM:

                Log.e(String.valueOf(TYPE_ITEM),position+"");
                try {
                    JSONObject obj=new JSONObject(goods_list.get(position-1));
                    holder.item_name.setText(obj.getString("name"));
                    holder.item_price.setText(obj.getString("value"));
                    RequestOptions options = new RequestOptions()
                            .error(R.drawable.baiyinhuiyuan);
                    Glide.with(context).load("http://duanbanyu.picp.net:25345/images/mallstation/"+obj.getString("image")).apply(options).into(holder.item_image);
                    holder.item_lv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onitemClick.onItemClick(position-1);
                        }
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

        }

    }


}
