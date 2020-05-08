package com.example.yhy.justfortest.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.Adapter.CardAdapter;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.classs.User;
import com.example.yhy.justfortest.util.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Button back=findViewById(R.id.back_wode_card);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        RecyclerView mycards=findViewById(R.id.my_cards);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mycards.setLayoutManager(staggeredGridLayoutManager);
        try {
            JSONArray array=new JSONArray(User.cards);
            JSONObject object = null;
            final List<String>lis=new ArrayList<>();
            for (int i=0;i<array.length();i++){
                object=array.getJSONObject(i);
                Log.e("my_tag",object.getString("card"));
                lis.add( object.getString("card"));
            }
            CardAdapter adapter=new CardAdapter(getApplication(),lis);
            mycards.setAdapter(adapter);
            adapter.setOnitemClickLintener(new CardAdapter.OnitemClick() {
                @Override
                public void onItemClick(final int position) {
                    if (position==lis.size()-1){
                        MainActivity.state=MainActivity.state_refresh_wode;
                        View  view= getLayoutInflater().inflate(R.layout.add_card_menu,null);
                        final Dialog dialog = new Dialog(CardActivity.this);
                        dialog.setContentView(view);
                        dialog.show();
                        final EditText account=dialog.findViewById(R.id.add_card_account);
                        final EditText password=dialog.findViewById(R.id.add_card_password);
                        Button add=dialog.findViewById(R.id.add_card_add);
                        final PreferenceUtil pr=new PreferenceUtil(getApplication(),"user");
                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MainActivity.state=MainActivity.state_refresh_wode;
                                if (account.getText().toString().trim().length()!=0
                                        &&password.getText().toString().trim().length()!=0){
                                    InternetAPI.getApi().bindCard(pr.getId(),account.getText().toString().trim(),password.getText().toString().trim())
                                            .enqueue(new retrofit2.Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    finish();

                                                }
                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    Log.e("?","???");
                                                    Toast.makeText(getApplication(),"网络状况存在问题",Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }

                            }
                        });

                    }
                    else{
                        View  view= getLayoutInflater().inflate(R.layout.delete_card_menu,null);
                        final Dialog dialog = new Dialog(CardActivity.this);
                        dialog.setContentView(view);
                        dialog.setCancelable(false);
                        dialog.show();
                        Button del=dialog.findViewById(R.id.delete);
                        final Button cancel=dialog.findViewById(R.id.delete_cancel);
                        final PreferenceUtil pr=new PreferenceUtil(CardActivity.this,"user");
                        del.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                InternetAPI.getApi().revokeCard(pr.getId(),lis.get(position),pr.getPassword())
                                        .enqueue(new retrofit2.Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                                dialog.cancel();
                                                finish();
                                            }

                                            @Override
                                            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                                                dialog.cancel();
                                                finish();
                                                Toast.makeText(getApplicationContext(),"请检查网络状况",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                        MainActivity.state=MainActivity.state_refresh_wode;
                        finish();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
