package com.example.yhy.justfortest.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.base.BaseActivity;

public class InfoActivity extends BaseActivity {
    private ImageView first_page;
    private ImageView good;
    private ImageView service;
    private ImageView code;
    private ImageView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        first_page=findViewById(R.id.info_page);
        good=findViewById(R.id.info_good);
        service=findViewById(R.id.info_service);
        code=findViewById(R.id.info_code);
        info=findViewById(R.id.info_info);

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground);
        info.setImageBitmap(bitmap);
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this,GoodActivity.class));
                finish();
            }
        });
        first_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this,MainActivity.class));
                finish();
            }
        });
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this,CodeActivity.class));
                finish();
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this,ServiceActivity.class));
                finish();
            }
        });
        Toast.makeText(getApplicationContext(),"该页面还在制作中",Toast.LENGTH_SHORT).show();
    }
}
