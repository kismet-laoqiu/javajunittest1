package com.example.yhy.justfortest.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.yhy.justfortest.R;

public class baoxiaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoxiao);
        Button baoxiao=findViewById(R.id.baoxiao1);
        baoxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View  diew5= getLayoutInflater().inflate(R.layout.zhifu_huadai,null);

                final Dialog dialog5 = new Dialog(baoxiaoActivity.this);
                dialog5.setContentView(diew5);
                dialog5.show();
            }
        });
    }
}
