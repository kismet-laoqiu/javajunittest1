package com.example.yhy.justfortest.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.UI.HeadPart;
import com.example.yhy.justfortest.util.PreferenceUtil;

public class AccountManageActivity extends AppCompatActivity {
    private Button quit;
    private RelativeLayout rel;
    private HeadPart rel2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        rel2=findViewById(R.id.title_manage);
        rel2.setTop_title("账号管理");
        quit=findViewById(R.id.quit);
        rel=findViewById(R.id.correct_password);
        rel2.setTop_left(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtil pr =new PreferenceUtil(getApplicationContext(),"user");
                pr.setState(false);
                pr.setQuit(true);
                finish();
            }
        });
        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccountManageActivity.this,CorrectPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
