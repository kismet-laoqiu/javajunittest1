package com.example.yhy.justfortest.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.UI.HeadPart;

public class CorrectPasswordActivity extends AppCompatActivity {
    private HeadPart title;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_password);
        title=findViewById(R.id.correct_title);
        save=findViewById(R.id.correct_save);
        title.setTop_left(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
