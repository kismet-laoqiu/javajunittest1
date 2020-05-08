package com.example.yhy.justfortest.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.UI.HeadPart;
import com.example.yhy.justfortest.classs.Good;
import com.example.yhy.justfortest.classs.User;
import com.example.yhy.justfortest.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class GoodInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_info);
        HeadPart headPart=findViewById(R.id.good_info_title);
        headPart.setTop_left(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView im=findViewById(R.id.image_good_info);
        TextView name=findViewById(R.id.tv_good_item_name);
        TextView price=findViewById(R.id.tv_good_item_price);
        TextView describe=findViewById(R.id.tv_good_item_describe);
        ImageView jianshao=findViewById(R.id.jianshao);
        ImageView zengjia=findViewById(R.id.zengjia);
        Button buy=findViewById(R.id.good_zhifu);

        int pri=0;
        final int[] cou = {0};
        final TextView total_price=findViewById(R.id.tv_good_total_price);
        final TextView count=findViewById(R.id.buy_num);
        try {
            final JSONObject object=new JSONObject(Good.one_good);
            pri=object.getInt("value");
            Good.per_price= String.valueOf(pri);
            cou[0] =1;
            price.setText("$"+pri);
            count.setText("1");
            total_price.setText("$"+pri* cou[0]);
            Glide.with(GoodInfoActivity.this).load("http://duanbanyu.picp.net:25345/images/mallstation/"+object.getString("image")).into(im);
            name.setText(object.getString("name"));
            describe.setText(object.getString("description"));
            final int finalPri = pri;
            jianshao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cou[0] ==1){
                        Toast.makeText(GoodInfoActivity.this,"购买数不能少于1",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        cou[0]--;
                        total_price.setText("$"+cou[0]* finalPri);
                        count.setText(""+cou[0]);
                    }
                }
            });
            zengjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cou[0] ==99){
                        Toast.makeText(GoodInfoActivity.this,"购买数不能多于99",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        cou[0]++;
                        total_price.setText("$"+cou[0]* finalPri);
                        count.setText(""+cou[0]);
                    }
                }
            });
            final PreferenceUtil pr=new PreferenceUtil(GoodInfoActivity.this,"user");
            final String finalPri1 = String.valueOf(pri);
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Good.num=count.getText().toString();
                    InternetAPI.getApi().buy(pr.getId(), count.getText().toString(), finalPri1)
                            .enqueue(new retrofit2.Callback<ResponseBody>() {
                                @Override
                                public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                    try {
                                        final JSONObject object1=new JSONObject(response.body().string());
                                        Good.list_= object1.getString("doubleList");
                                        View  diew= getLayoutInflater().inflate(R.layout.zhifufangshi,null);

                                        final Dialog dialog = new Dialog(GoodInfoActivity.this);
                                        dialog.setContentView(diew);
                                        Button zhijie=diew.findViewById(R.id.zhijiezhifu);
                                        Button baoxiao=diew.findViewById(R.id.baoxiao);
                                        dialog.show();
                                        dialog.setCancelable(true);
                                        zhijie.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.cancel();
                                                View  diew5= getLayoutInflater().inflate(R.layout.zhifu_huadai,null);

                                                final Dialog dialog5 = new Dialog(GoodInfoActivity.this);
                                                dialog5.setContentView(diew5);
                                                Button huadai=diew5.findViewById(R.id.huadai);
                                                Button yinahng=diew5.findViewById(R.id.yinhangka);

                                                huadai.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        dialog5.cancel();
                                                        Intent intent=new Intent(GoodInfoActivity.this,HuaQiActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                                yinahng.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        dialog5.cancel();
                                                        Intent intent=new Intent(GoodInfoActivity.this,SelectYin.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                                dialog5.show();
                                                dialog5.setCancelable(true);


                                            }
                                        });
                                        baoxiao.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.cancel();
                                                View diew1=getLayoutInflater().inflate(R.layout.input_password,null);
                                                final Dialog dialog2 = new Dialog(GoodInfoActivity.this);
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
                                                enter.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        if (ed.getText().toString().length()!=0){
                                                            String com = null;
                                                            try {
                                                                com=object.getString("commodity_id");
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                            Log.e("?",com);
                                                            InternetAPI.getApi().reim(pr.getId(),ed.getText().toString(),null,com,count.getText().toString())
                                                                    .enqueue(new retrofit2.Callback<ResponseBody>() {
                                                                        @Override
                                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                            Log.e("?","??");
                                                                            try {
                                                                                dialog2.cancel();
                                                                                JSONObject object2=new JSONObject(response.body().string());
                                                                                User.td_cd=object2.getString("td_code");
                                                                                Intent intent=new Intent(GoodInfoActivity.this,ErweimaActivity.class);
                                                                                startActivity(intent);
                                                                                finish();
                                                                            } catch (IOException e) {
                                                                                Log.e("tag",e.getMessage());
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }

                                                                        }
                                                                        @Override
                                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                                            Log.e("?","???");
                                                                            Toast.makeText(GoodInfoActivity.this,"网络状况存在问题",Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });


                                            }
                                        });
                                        } catch (IOException | JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"请检查网络状况",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
