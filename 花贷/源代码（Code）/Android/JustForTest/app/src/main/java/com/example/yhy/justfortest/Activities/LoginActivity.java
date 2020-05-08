package com.example.yhy.justfortest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.base.BaseActivity;
import com.example.yhy.justfortest.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    Button LoginButton;
    EditText et_account;
    EditText et_password;
    Button bt_resign;
    private Handler handler;
    PreferenceUtil pr;
    private ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("login","test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final String URL="http://39.105.133.19/picture/1.jpg";
        pr =new PreferenceUtil(getApplicationContext(),"user");
        LoginButton=findViewById(R.id.bt_login);
        et_account=findViewById(R.id.et_login_account);
        et_password=findViewById(R.id.et_login_password);
        bt_resign=findViewById(R.id.bt_resign);
        InternetAPI.getApi().login("430482196811020012","password")
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("?","??");
                        try {
                            Log.e("tag",response.body().string());
                        } catch (IOException e) {
                            Log.e("tag",e.getMessage());
                        }

                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("?","???");
                        Toast.makeText(LoginActivity.this,"网络状况存在问题",Toast.LENGTH_SHORT).show();
                    }
                });

        bt_resign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this,ResignActivity.class);
                startActivity(intent);
                finish();
            }
        });
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account;
                String password;
                String check;
                if (!TextUtils.isEmpty(et_account.getText())) {
                    account = et_account.getText().toString();
                    if (!TextUtils.isEmpty(et_password.getText())){
                        password=et_password.getText().toString();
                        sendService(account,password,null);
                    }
                    else
                        Toast.makeText(LoginActivity.this,"未输入密码",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(LoginActivity.this,"未输入账号",Toast.LENGTH_SHORT).show();
              }
        });
        if (pr.getState()){
            Intent intent =new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void sendService(final String ac, final String pw, final String ch){
         InternetAPI.getApi().login(ac,pw)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("?","??");
                        JSONObject object = null;//获取json数据
                        try {
                            object = new JSONObject(response.body().string());
                            Boolean bo=object.getBoolean("state");
                            Log.e("??????",object.toString());
                            if (bo){
                                pr.setState(bo);
                                pr.setId(ac);
                                pr.setPassword(pw);
                                Log.e("?",pr.getId());
                                Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"密码或账号错误",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("?","???");
                        Toast.makeText(LoginActivity.this,"网络状况存在问题",Toast.LENGTH_SHORT).show();
                    }
                });



    }
}
