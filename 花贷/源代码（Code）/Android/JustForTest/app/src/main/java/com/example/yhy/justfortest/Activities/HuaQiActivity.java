package com.example.yhy.justfortest.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.Adapter.qishuAdapter;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.classs.Good;
import com.example.yhy.justfortest.classs.User;
import com.example.yhy.justfortest.util.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class HuaQiActivity extends AppCompatActivity {
    private static int state=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hua_qi);
        Button back=findViewById(R.id.back_qishu_select_card);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        RecyclerView mycards=findViewById(R.id.select_qishu);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mycards.setLayoutManager(staggeredGridLayoutManager);
        JSONArray array;
        List<String>mo = new ArrayList<>();
        try {
            array=new JSONArray(Good.list_);
            for (int i=0;i<array.length();i++)
                mo.add(array.getString(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<String>num=new ArrayList<>();
        num=new ArrayList<>();
        num.add("0期");
        num.add("1期");
        num.add("3期");
        num.add("6期");
        num.add("9期");
        num.add("12期");
        num.add("24期");
        qishuAdapter adapt=new qishuAdapter(HuaQiActivity.this, mo,num);
        final List<String> finalNum = num;
        adapt.setOnitemClickLintener(new qishuAdapter.OnitemClick() {
            @Override
            public void onItemClick(final int position) {
                View diew1=getLayoutInflater().inflate(R.layout.input_password,null);
                final Dialog dialog2 = new Dialog(HuaQiActivity.this);
                dialog2.setContentView(diew1);
                dialog2.setCancelable(false);
                dialog2.show();
                Button enter=diew1.findViewById(R.id.input_zhifu);
                Button can=diew1.findViewById(R.id.input_cancle);
                final EditText ed=diew1.findViewById(R.id.input_pass);
                can.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.cancel();
                    }
                });
                final PreferenceUtil pr=new PreferenceUtil(HuaQiActivity.this,"user");
                enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(Good.one_good);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String s1 = null;
                        try {
                            s1 = object.getString("commodity_id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        InternetAPI.getApi().pay(pr.getId(), ed.getText().toString(), s1, Good.num, finalNum.get(position).substring(0, 1))
                                .enqueue(new retrofit2.Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                        try {
                                            String str=response.body().string();
                                            JSONObject object1=new JSONObject(str);
                                            User.td_cd=object1.getString("td_code");
                                            if (state==0){
                                                Intent intent=new Intent(HuaQiActivity.this,ErweimaActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                finish();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });
                    }
                });
            }
        });
        mycards.setAdapter(adapt);
    }
}
