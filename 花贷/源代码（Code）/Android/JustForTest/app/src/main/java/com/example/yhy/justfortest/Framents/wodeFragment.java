package com.example.yhy.justfortest.Framents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.Activities.AccountManageActivity;
import com.example.yhy.justfortest.Activities.CardActivity;
import com.example.yhy.justfortest.Activities.MessageCenterActivity;
import com.example.yhy.justfortest.Activities.UserInfoActivity;
import com.example.yhy.justfortest.Activities.baoxiaoActivity;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.classs.User;
import com.example.yhy.justfortest.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class wodeFragment extends Fragment {
    public wodeFragment() {
        // Required empty public constructor
    }
    private ImageView head_img;
    private RelativeLayout account_manage;
    private RelativeLayout message_center;
    private RelativeLayout my_money;
    private RelativeLayout my_fold;
    private RelativeLayout my_bank_card;
    private TextView name;
    private TextView tel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_wode, container, false);
        head_img=v.findViewById(R.id.user_head_img);
        account_manage=v.findViewById(R.id.account_manage);
        message_center=v.findViewById(R.id.message_center);
        name=v.findViewById(R.id.user_name);
        tel=v.findViewById(R.id.user_tel);
        my_money=v.findViewById(R.id.my_money);
        my_fold=v.findViewById(R.id.my_fold);
        my_bank_card=v.findViewById(R.id.my_bank_card);
        my_bank_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), CardActivity.class);
                startActivity(intent);
            }
        });
        account_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AccountManageActivity.class);
                startActivity(intent);
            }
        });
        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        });
        message_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MessageCenterActivity.class);
                startActivity(intent);
            }
        });
        my_fold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), baoxiaoActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        refresh();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public void refresh(){
        PreferenceUtil pr=new PreferenceUtil(this.getActivity(),"user");
        Log.e("tag",pr.getId());
        InternetAPI.getApi().person_info(pr.getId())
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject object=new JSONObject(response.body().string());
                            String person=object.getString("person");
                            JSONObject per=new JSONObject(person);
                            Log.e("???",per.getString("username"));
                            Log.e("???",per.getString("phonenum"));
                            name.setText(per.getString("username"));
                            tel.setText(per.getString("phonenum"));
                            User.cards=per.getString("cardList");

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
    }
}
