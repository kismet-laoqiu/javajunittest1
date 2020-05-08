package com.example.yhy.justfortest.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.Adapter.SelectCardAdapter;
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

public class SelectYin extends AppCompatActivity {
    public static int type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_yin);
        Button back=findViewById(R.id.back_wode_select_card);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        RecyclerView mycards=findViewById(R.id.select_cards);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mycards.setLayoutManager(staggeredGridLayoutManager);
        try {
            JSONArray array=new JSONArray(User.cards);
            final List<String> lis=new ArrayList<>();
            JSONObject object = null;
            for (int i=0;i<array.length();i++){
                object=array.getJSONObject(i);
                Log.e("my_tag",object.getString("card"));
                lis.add( object.getString("card"));
            }
            SelectCardAdapter adapter=new SelectCardAdapter(this,lis);
            mycards.setAdapter(adapter);
            adapter.setOnitemClickLintener(new SelectCardAdapter.OnitemClick() {
                @Override
                public void onItemClick(final int position) {
                    try {
                        JSONArray array=new JSONArray(User.cards);
                        User.card_temp=array.getJSONObject(position).getString("card");
                        View diew1=getLayoutInflater().inflate(R.layout.input_password,null);
                        final Dialog dialog2 = new Dialog(SelectYin.this);
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
                            public void onClick(View view){
                                if (ed.getText().toString().length()!=0) {
                                    String s = ed.getText().toString();
                                    String s1 = null;
                                    try {
                                        final JSONObject object = new JSONObject(Good.one_good);
                                        s1 = object.getString("commodity_id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    PreferenceUtil pr = new PreferenceUtil(SelectYin.this, "user");
                                    InternetAPI.getApi().pay_card(pr.getId(), User.card_temp, s, s1, Good.num, String.valueOf(0))
                                            .enqueue(new retrofit2.Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                                    if (type==0){
                                                        try {
                                                            JSONObject object1=new JSONObject(response.body().string());
                                                            User.td_cd=object1.getString("td_code");
                                                            Intent intent=new Intent(SelectYin.this,ErweimaActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                    else{
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                }
                                            });
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
