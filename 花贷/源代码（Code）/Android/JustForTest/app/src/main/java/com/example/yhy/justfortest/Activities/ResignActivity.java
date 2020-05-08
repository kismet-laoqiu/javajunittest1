package com.example.yhy.justfortest.Activities;

import android.content.Intent;
import android.os.Bundle;
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

public class ResignActivity extends BaseActivity  {
    public String user_name;
    public String password;
    public String check_code;
    public String id_code;
    public String enter;
    public String tel_num;
    public String bank_account;
    public String bank_password;
    public EditText et_user_name;
    public EditText et_password;
    public EditText et_check_code;
    public EditText et_id_code;
    public EditText et_enter;
    public EditText et_tel_num;
    public EditText et_bank_account;
    public EditText et_bank_password;
    public Button send_check;
    public Button resign_bt;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resign);
        back=findViewById(R.id.bt_resign_back);
        et_user_name=findViewById(R.id.resign_user_name);
        et_password=findViewById(R.id.resign_password);
        et_check_code=findViewById(R.id.resign_check);
        et_id_code=findViewById(R.id.resign_id);
        et_enter=findViewById(R.id.resign_enterprisename);
        et_tel_num=findViewById(R.id.resign_tel_num);
        et_bank_account=findViewById(R.id.resign_bank_account);
        et_bank_password=findViewById(R.id.resign_bank_password);
        resign_bt=findViewById(R.id.resign_bt);
        send_check=findViewById(R.id.bt_send_check);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplication(),LoginActivity.class);
                startActivity(intent);
                finish();
                }
        });
        send_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_tel_num.getText().toString().trim().length()==0){
                    Toast.makeText(getApplicationContext(),"请输入手机号",Toast.LENGTH_SHORT).show();
                }
                else{
                    tel_num=et_tel_num.getText().toString();
                    Log.e("???123",tel_num);
                    InternetAPI.getApi().sendCheck(tel_num)
                            .enqueue(new retrofit2.Callback<ResponseBody>() {
                                @Override
                                public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                    try {
                                        JSONObject object=new JSONObject(response.body().string());
                                        if(!object.getBoolean("state")){
                                            Toast.makeText(getApplicationContext(),"该手机号已被绑定",Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"请检查网络状况",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        resign_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resign();
            }
        });
    }

    public void resign(){
        user_name=et_user_name.getText().toString().trim();
        password=et_password.getText().toString().trim();
        check_code=et_check_code.getText().toString().trim();
        id_code=et_id_code.getText().toString().trim();
        enter=et_enter.getText().toString().trim();
        bank_account=et_bank_account.getText().toString().trim();
        bank_password=et_bank_password.getText().toString().trim();
        if (user_name.length()==0||
        password.length()==0||
        check_code.length()==0||
        id_code.length()==0||
        enter.length()==0||
        tel_num.length()==0||
        bank_account.length()==0||
        bank_password.length()==0) {
            Toast.makeText(getApplicationContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        InternetAPI.getApi().resign(id_code,user_name,password,tel_num,enter,check_code,bank_account,bank_password)
            .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        try{
                            JSONObject object = new JSONObject(response.body().string());//获取json数据

                            String str=id_code;
                            Boolean flag=object.getBoolean("state");
                            if (!flag){
                                Toast.makeText(getApplicationContext(),"您的账号已存在",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            PreferenceUtil pr =new PreferenceUtil(getApplicationContext(),"user");
                            pr.setId(str);
                            Intent intent =new Intent(ResignActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"请检查网络",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
