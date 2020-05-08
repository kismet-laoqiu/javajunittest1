package com.example.yhy.justfortest.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.Adapter.MessageAdapter;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.UI.HeadPart;
import com.example.yhy.justfortest.util.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MessageCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        HeadPart headPart=findViewById(R.id.message_center_title);
        headPart.setTop_left(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        headPart.setTop_title("消息中心");
        final RecyclerView recyclerView=findViewById(R.id.message_list);
        final List<String>names=new ArrayList<>();
        final List<String>times=new ArrayList<>();
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        PreferenceUtil pr=new PreferenceUtil(MessageCenterActivity.this,"user");
        InternetAPI.getApi().message_center(pr.getId())
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject object=new JSONObject(response.body().string());
                            JSONArray array=object.getJSONArray("message");
                            for(int i=0;i<array.length();i++){
                                Log.e("x","i"+i);
                                JSONObject object1=array.getJSONObject(i);
                                names.add(object1.getString("content"));
                                times.add(object1.getString("times"));
                            }
                            MessageAdapter adapter=new MessageAdapter(getApplicationContext(),names,times);
                            recyclerView.setAdapter(adapter);
                            MainActivity.state=MainActivity.state_refresh_first;
                            }
                            catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"请检查网络",Toast.LENGTH_SHORT).show();
                        Log.e("tag",t.getMessage());
                    }
                });

        recyclerView.setLayoutManager(manager);
    }
}
