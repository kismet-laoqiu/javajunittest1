package com.example.yhy.justfortest.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.classs.User;
import com.example.yhy.justfortest.util.PreferenceUtil;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ErweimaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erweima);
        final ImageView imageView=findViewById(R.id.erweima_ac_im);
        Button ba=findViewById(R.id.back_erweima);
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        PreferenceUtil pr=new PreferenceUtil(ErweimaActivity.this,"user");
        InternetAPI.getApi().qr_code(pr.getId(), User.td_cd)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            InputStream stream=response.body().byteStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(stream);
                            imageView.setImageBitmap(bitmap);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ErweimaActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
                        Log.e("tag",t.getMessage());
                    }
                });
    }
}
