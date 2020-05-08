package com.example.yhy.justfortest.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yhy.justfortest.Adapter.GoodAdapter;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.base.BaseActivity;
import com.example.yhy.justfortest.classs.Good;

import java.util.ArrayList;
import java.util.List;

public class GoodActivity extends BaseActivity{
    private ImageView first_page;
    private ImageView good;
    private ImageView service;
    private ImageView code;
    private ImageView info;
    private ImageView search;
    private EditText search_text;
    private Good[] goods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);
        first_page=findViewById(R.id.good_page);
        good=findViewById(R.id.good_good);
        service=findViewById(R.id.good_service);
        code=findViewById(R.id.good_code);
        info=findViewById(R.id.good_info);
        search=findViewById(R.id.iv_good_search);
        search_text=findViewById(R.id.et_good_search);
        List<Good>goods_list=new ArrayList<>();
        for(int i=0;i<goods.length;i++){
            goods_list.add(goods[i]);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.good_recycleView);
        int spanCount = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);
       // GoodAdapter adapter = new GoodAdapter(goods_list);
        //recyclerView.setAdapter(adapter);





        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground);
        good.setImageBitmap(bitmap);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoodActivity.this,InfoActivity.class));
                finish();
            }
        });
        first_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoodActivity.this,MainActivity.class));
                finish();
            }
        });
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoodActivity.this,CodeActivity.class));
                finish();
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoodActivity.this,ServiceActivity.class));
                finish();
            }
        });
    }


}

