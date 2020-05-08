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

public class CodeActivity extends BaseActivity {
    private ImageView first_page;
    private ImageView good;
    private ImageView service;
    private ImageView code;
    private ImageView info;
    private ImageView code_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        first_page=findViewById(R.id.code_page);
        good=findViewById(R.id.code_good);
        service=findViewById(R.id.code_service);
        code=findViewById(R.id.code_code);
        info=findViewById(R.id.code_info);
        code_image=findViewById(R.id.code_image);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground);
        code.setImageBitmap(bitmap);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CodeActivity.this,InfoActivity.class));
                finish();
            }
        });
        first_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CodeActivity.this,MainActivity.class));
                finish();
            }
        });
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CodeActivity.this,GoodActivity.class));
                finish();
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CodeActivity.this,ServiceActivity.class));
                finish();
            }
        });
        Toast.makeText(getApplicationContext(),"蓝色区域为二维码显示区域",Toast.LENGTH_SHORT).show();
    }
}
