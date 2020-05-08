package com.example.yhy.justfortest.Framents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.classs.User;
import com.example.yhy.justfortest.util.PreferenceUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class erweimaFragment extends Fragment {
    public erweimaFragment() {
        // Required empty public constructor
    }
    private int state=0;
    private List<String>names=new ArrayList<>();
    private List<String>td_codes=new ArrayList<>();
    ImageView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_erweima, container, false);
        view=v.findViewById(R.id.erweima_im);
        PreferenceUtil pr=new PreferenceUtil(getActivity(),"user");
        names.add("付款二维码");
        td_codes.add(pr.getId());
        final Button button1=v.findViewById(R.id.first_bt);
        final TextView button2=v.findViewById(R.id.second_bt);
        final TextView button3=v.findViewById(R.id.third_bt);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state=(state+1)%names.size();
                button1.setText(names.get(state));
                button2.setText(names.get((state+1)%names.size()));
                button3.setText(names.get((state+2)%names.size()));
                setImage(td_codes.get(state));

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state=(state+2)%names.size();
                button1.setText(names.get(state));
                button2.setText(names.get((state+1)%names.size()));
                button3.setText(names.get((state+2)%names.size()));
                setImage(td_codes.get(state));
            }
        });
        InternetAPI.getApi().codecenter(pr.getId(),pr.getPassword())
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject object=new JSONObject(response.body().string());
                            JSONArray array=object.getJSONArray("td_codes");
                            for(int i=0;i<array.length();i++){
                                Log.e("x","i"+i);
                                JSONObject object1=array.getJSONObject(i);
                                names.add(object1.getString("name"));
                                td_codes.add(object1.toString());
                            }
                            button1.setText(names.get(state));
                            button2.setText(names.get((state+1)%names.size()));
                            button3.setText(names.get((state+2)%names.size()));
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(),"请检查网络",Toast.LENGTH_SHORT).show();
                        Log.e("tag",t.getMessage());
                    }
                });
        setImage(td_codes.get(0));
        return v;
    }
    public void setImage(String td_code){
        PreferenceUtil pr=new PreferenceUtil(getActivity(),"user");
        InternetAPI.getApi().qr_code(pr.getId(),td_code)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            InputStream stream=response.body().byteStream();
                            Bitmap bitmap =BitmapFactory.decodeStream(stream);
                            view.setImageBitmap(bitmap);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(),"请检查网络",Toast.LENGTH_SHORT).show();
                        Log.e("tag",t.getMessage());
                    }
                });
    }
    public byte[] InputStream2Bytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        int readCount = -1;
        try {
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
