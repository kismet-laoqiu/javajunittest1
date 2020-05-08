package com.example.yhy.justfortest.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.UI.HeadPart;
import com.example.yhy.justfortest.classs.User;
import com.example.yhy.justfortest.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {
    private HeadPart title;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        title=findViewById(R.id.user_info_title);
        save=findViewById(R.id.user_info_save);
        title.setTop_left(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final EditText user_info=findViewById(R.id.et_user_info_userName);
        final EditText user_tel=findViewById(R.id.et_user_info_telephone);

        title.setTop_title("个人信息");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtil pr=new PreferenceUtil(getApplication(),"user");
                if (user_info.getText().toString().trim().length()==0||
                        user_tel.getText().toString().trim().length()==0){
                    Toast.makeText(UserInfoActivity.this,"请补全信息",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.e(user_info.getText().toString(),user_tel.getText().toString());
                    InternetAPI.getApi().updateInfo(pr.getId(),pr.getPassword(),user_info.getText().toString(),null,user_tel.getText().toString())
                            .enqueue(new retrofit2.Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    MainActivity.state=MainActivity.state_refresh_wode;
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }

                            });
                }
            }
        });
    }
}
