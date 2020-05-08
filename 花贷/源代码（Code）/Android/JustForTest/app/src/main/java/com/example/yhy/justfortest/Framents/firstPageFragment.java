package com.example.yhy.justfortest.Framents;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.notificatoinbutton.library.NotificationButton;
import com.example.yhy.justfortest.API.InternetAPI;
import com.example.yhy.justfortest.Activities.MessageCenterActivity;
import com.example.yhy.justfortest.Activities.ZhuanZhangActivity;
import com.example.yhy.justfortest.R;
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
import retrofit2.Response;

public class firstPageFragment extends Fragment {
    public firstPageFragment() {
        // Required empty public constructor
    }
    NotificationButton notificationButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ImageView imageView;
    static  int state_shangsheng=1;
    static  int state_xiajiang=-1;
    static int state1=state_shangsheng;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.fragment_first_page, container, false);
        final Button button=v.findViewById(R.id.draw_bt);
        Button button1=v.findViewById(R.id.yanqi);
        final NavigationView navigationView=v.findViewById(R.id.nav_view);
        View headView=navigationView.getHeaderView(0);
        notificationButton=v.findViewById(R.id.first_message);
        final TextView daikuan_qingkuang=v.findViewById(R.id.daikuan_qingkuang);
        final TextView daikuan_yuer=v.findViewById(R.id.yuer_first);
        final TextView daikuan_percent=v.findViewById(R.id.first_percent);
        final TextView tag_1=v.findViewById(R.id.daikuan_1);
        Button zhuanhuan2=v.findViewById(R.id.zhuanhuan2);
        Button huankuan=v.findViewById(R.id.huankuan);
        final Double[] mini_repay = new Double[1];
        final Double[] loan_ammount = new Double[1];
        final List<Float>total_ammount=new ArrayList<>();
        final List<String>times=new ArrayList<>();
        final PreferenceUtil pr=new PreferenceUtil(getActivity(),"user");
        final Double[] loan_ammount_1 = new Double[2];
        final Double[] total_ammount1 = new Double[2];
        Button zhuanzhang=v.findViewById(R.id.zhuanzhang);
        zhuanzhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ZhuanZhangActivity.class);
                startActivity(intent);
            }
        });
        huankuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view123=getLayoutInflater().inflate(R.layout.layout_huankuan,null);
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(view123);
                dialog.setCancelable(false);
                TextView textView=view123.findViewById(R.id.huankuan_num);
                textView.setText(mini_repay[0].intValue()+"");
                final EditText editText=view123.findViewById(R.id.huankuan_jiner);
                Button button_cancel=view123.findViewById(R.id.cancel_huankaun);
                button_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                Button button_huan=view123.findViewById(R.id.huankuan_bt);
                button_huan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Double.parseDouble(editText.getText().toString())<mini_repay[0]){
                            Toast.makeText(getActivity(),"还款金额过少",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            InternetAPI.getApi().repay(pr.getId(),editText.getText().toString())
                                    .enqueue(new retrofit2.Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                            dialog.cancel();
                                            Toast.makeText(getActivity(),"还款成功",Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                                            }
                                    });
                        }
                    }
                });
                dialog.show();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InternetAPI.getApi().delay(pr.getId())
                        .enqueue(new retrofit2.Callback<ResponseBody>() {
                            @Override
                            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                try {
                                    JSONObject object=new JSONObject(response.body().string());
                                    View view123=getLayoutInflater().inflate(R.layout.dialog_yanqi,null);
                                    final Dialog dialog = new Dialog(getActivity());
                                    dialog.setContentView(view123);
                                    dialog.setCancelable(true);
                                    ImageView im=view123.findViewById(R.id.yanqi_png);
                                    Resources resources = getActivity().getResources();
                                    Drawable drawable;
                                    if(!object.getBoolean("state")){
                                        drawable = resources.getDrawable(R.drawable.yanqishibai);
                                    }else
                                        drawable = resources.getDrawable(R.drawable.yanqi_chenggong);

                                    im.setImageDrawable(drawable);
                                    dialog.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                                }
                        });
            }
        });
        final LinearLayout lv=v.findViewById(R.id.lv_bt);
        lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MessageCenterActivity.class);
                startActivity(intent);
            }
        });
        final RelativeLayout rl=v.findViewById(R.id.first_page_back);
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(tag_1.getText().toString().equals("现金贷")) {
                   tag_1.setText("消费贷");

                   daikuan_qingkuang.setText(loan_ammount_1[0].intValue()+"/"+total_ammount1[0].intValue());
                   if (state1==state_shangsheng) {
                       Resources resources = getActivity().getResources();
                       Drawable drawable = resources.getDrawable(R.drawable.shangshengxiaofei1);
                       rl.setBackground(drawable);
                   }
                   else{
                       Resources resources = getActivity().getResources();
                       Drawable drawable = resources.getDrawable(R.drawable.xiajiangxiaofei1);
                       rl.setBackground(drawable);
                   }
               }
               else{
                   tag_1.setText("现金贷");
                   daikuan_qingkuang.setText(loan_ammount_1[1].intValue()+"/"+total_ammount1[1].intValue());
                   if (state1==state_shangsheng) {
                       Resources resources = getActivity().getResources();
                       Drawable drawable = resources.getDrawable(R.drawable.shangshengxianjin1);
                       rl.setBackground(drawable);
                   }
                   else{
                       Resources resources = getActivity().getResources();
                       Drawable drawable = resources.getDrawable(R.drawable.xiajiangxianjin1);
                       rl.setBackground(drawable);
                   }
               }
            }
        };
        zhuanhuan2.setOnClickListener(listener);
        InternetAPI.getApi().first_page(pr.getId())
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject object=new JSONObject(response.body().string());
                            User.cards=object.getString("list_card");
                            mini_repay[0] =object.getDouble("mini_repay");
                            JSONArray array=object.getJSONArray("list_record");
                            JSONArray array1=object.getJSONArray("list_information");
                            loan_ammount_1[0]=array1.getJSONObject(0).getDouble("loan_amount");
                            loan_ammount_1[1]=array1.getJSONObject(1).getDouble("loan_amount");
                            total_ammount1[0]=array1.getJSONObject(0).getDouble("total_amount");
                            total_ammount1[1]=array1.getJSONObject(1).getDouble("total_amount");
                            for (int i=0;i<array.length();i++){
                                total_ammount.add(Float.valueOf(array.getJSONObject(i).getString("total_amount")));
                                times.add(array.getJSONObject(i).getString("time"));
                                loan_ammount[0]=array.getJSONObject(i).getDouble("loan_amount");
                            }
                            Log.e("tag123", String.valueOf(object.getInt("sign")));
                            notificationButton.setNotificationNumber(object.getInt("sign"));
                        } catch (JSONException | IOException e) {

                            Log.e("tag123",  e.getMessage());
                        }
                        daikuan_qingkuang.setText(loan_ammount_1[0].intValue()+"/"+total_ammount1[0].intValue());
                        daikuan_yuer.setText(loan_ammount[0]-total_ammount.get(total_ammount.size()-1)+"");
                        float dou=total_ammount.get(total_ammount.size()-1)/total_ammount.get(total_ammount.size()-2);

                        if (dou>1){
                            daikuan_percent.setText((dou-1)+"");
                            state1=state_shangsheng;
                            Resources resources = getActivity().getResources();
                            Drawable drawable = resources.getDrawable(R.drawable.shangshengxianjin1);
                            rl.setBackground(drawable);
                        }
                        else {
                            daikuan_percent.setText((1-dou)+"");
                            state1=state_xiajiang;
                            Resources resources = getActivity().getResources();
                            Drawable drawable = resources.getDrawable(R.drawable.xiajiangxianjin1);
                            rl.setBackground(drawable);
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
        final TextView name=headView.findViewById(R.id.draw_text_name);
        final TextView tel=headView.findViewById(R.id.draw_text_tel);
        final TextView id=headView.findViewById(R.id.menu_id);
        final TextView ent=headView.findViewById(R.id.menu_ent);
        final DrawerLayout layout=v.findViewById(R.id.draw_layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InternetAPI.getApi().person_info(pr.getId())
                        .enqueue(new retrofit2.Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                id.setText("id:"+pr.getId());
                                JSONObject object= null;
                                try {
                                    object = new JSONObject(response.body().string());
                                    String person=object.getString("person");
                                    JSONObject per=new JSONObject(person);
                                    name.setText(per.getString("username"));
                                    tel.setText(per.getString("phonenum"));

                                    ent.setText("工作地点："+per.getString("enterprisename"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                layout.openDrawer(GravityCompat.START);
            }
        });
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    public void setnum(int num) {
        notificationButton.setNotificationNumber(num);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
