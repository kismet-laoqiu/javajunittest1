package com.example.yhy.justfortest.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yhy.justfortest.Framents.erweimaFragment;
import com.example.yhy.justfortest.Framents.firstPageFragment;
import com.example.yhy.justfortest.Framents.jinRongFragment;
import com.example.yhy.justfortest.Framents.shangchengFragment;
import com.example.yhy.justfortest.Framents.wodeFragment;
import com.example.yhy.justfortest.R;
import com.example.yhy.justfortest.UI.MyCircle;
import com.example.yhy.justfortest.base.BaseActivity;
import com.example.yhy.justfortest.util.PreferenceUtil;

public class MainActivity extends BaseActivity {
    public firstPageFragment first_page=new firstPageFragment();
    public Fragment jinrong=new jinRongFragment();
    public Fragment erweima=new erweimaFragment();
    public Fragment shangcheng=new shangchengFragment();
    public wodeFragment wode=new wodeFragment();
    private ImageView bt_first_page;
    private ImageView bt_jinrong;
    private ImageView bt_erweima;
    private ImageView bt_shangcheng;
    private ImageView bt_wode;
    private TextView fir;
    private TextView jin;
    private TextView sha;
    private TextView wod;
    public static int state=0;
    public static int state_refresh_wode=-1;
    public static int state_refresh_first=-2;
    @Override
    protected void onResume() {
        super.onResume();
        PreferenceUtil pr =new PreferenceUtil(getApplicationContext(),"user");
        if (pr.getQuit()){
            pr.setQuit(false);
            Intent intent =new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (state==state_refresh_wode){
            state=0;
            wode.refresh();
        }
        if (state==state_refresh_first){
            state=0;
            first_page.setnum(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm= getSupportFragmentManager();
        //2、开启事务
        FragmentTransaction ft= fm.beginTransaction();
        ft.add(R.id.main_fragment, first_page);
        ft.add(R.id.main_fragment, jinrong);
        ft.add(R.id.main_fragment, erweima);
        ft.add(R.id.main_fragment,shangcheng);
        ft.add(R.id.main_fragment, wode);
        ft.show(first_page);
        ft.hide(jinrong);
        ft.hide(erweima);
        ft.hide(shangcheng);
        ft.hide(wode);
        fir=findViewById(R.id.first_page_flag);
        jin=findViewById(R.id.jinrong_flag);
        sha=findViewById(R.id.shangcheng_flag);
        wod=findViewById(R.id.wode_flag);
        fir.setVisibility(View.VISIBLE);
        jin.setVisibility(View.GONE);
        sha.setVisibility(View.GONE);
        wod.setVisibility(View.GONE);
        //---------帧布局框架id--Fragment碎片---自定义tag标签
        //4、提交事务
        ft.commit();

        bt_first_page=findViewById(R.id.first_page);
        bt_jinrong=findViewById(R.id.jinrong_page);
        bt_erweima=findViewById(R.id.erweima);
        bt_shangcheng=findViewById(R.id.shangcheng_page);
        bt_wode=findViewById(R.id.wode);
        bt_first_page.setImageResource(R.drawable.shouye2);

        bt_first_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm= getSupportFragmentManager();
                //2、开启事务
                FragmentTransaction ft= fm.beginTransaction();
                ft.show(first_page);
                ft.hide(jinrong);
                ft.hide(erweima);
                ft.hide(shangcheng);
                ft.hide(wode);
                ft.commit();
                bt_first_page.setImageResource(R.drawable.shouye2);
                bt_jinrong.setImageResource(R.drawable.product2);
                bt_shangcheng.setImageResource(R.drawable.shangcheng);
                bt_wode.setImageResource(R.drawable.wode);
                fir.setVisibility(View.VISIBLE);
                jin.setVisibility(View.GONE);
                sha.setVisibility(View.GONE);
                wod.setVisibility(View.GONE);
            }
        });
        bt_jinrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm= getSupportFragmentManager();
                //2、开启事务
                FragmentTransaction ft= fm.beginTransaction();
                ft.hide(first_page);
                ft.show(jinrong);
                ft.hide(erweima);
                ft.hide(shangcheng);
                ft.hide(wode);
                ft.commit();
                bt_first_page.setImageResource(R.drawable.shouye);
                bt_jinrong.setImageResource(R.drawable.product);
                bt_shangcheng.setImageResource(R.drawable.shangcheng);
                bt_wode.setImageResource(R.drawable.wode);
                fir.setVisibility(View.GONE);
                jin.setVisibility(View.VISIBLE);
                sha.setVisibility(View.GONE);
                wod.setVisibility(View.GONE);
            }
        });
        bt_erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm= getSupportFragmentManager();
                //2、开启事务
                FragmentTransaction ft= fm.beginTransaction();
                ft.hide(first_page);
                ft.hide(jinrong);
                ft.show(erweima);
                ft.hide(shangcheng);
                ft.hide(wode);
                ft.commit();
                bt_first_page.setImageResource(R.drawable.shouye);
                bt_jinrong.setImageResource(R.drawable.product2);
                bt_shangcheng.setImageResource(R.drawable.shangcheng);
                bt_wode.setImageResource(R.drawable.wode);
                fir.setVisibility(View.GONE);
                jin.setVisibility(View.GONE);
                sha.setVisibility(View.GONE);
                wod.setVisibility(View.GONE);
            }
        });
        bt_shangcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm= getSupportFragmentManager();
                //2、开启事务
                FragmentTransaction ft= fm.beginTransaction();
                ft.hide(first_page);
                ft.hide(jinrong);
                ft.hide(erweima);
                ft.show(shangcheng);
                ft.hide(wode);
                ft.commit();
                bt_first_page.setImageResource(R.drawable.shouye);
                bt_jinrong.setImageResource(R.drawable.product2);
                bt_shangcheng.setImageResource(R.drawable.shangcheng2);
                bt_wode.setImageResource(R.drawable.wode);
                fir.setVisibility(View.GONE);
                jin.setVisibility(View.GONE);
                sha.setVisibility(View.VISIBLE);
                wod.setVisibility(View.GONE);
            }
        });
        bt_wode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm= getSupportFragmentManager();
                //2、开启事务
                FragmentTransaction ft= fm.beginTransaction();
                ft.hide(first_page);
                ft.hide(jinrong);
                ft.hide(erweima);
                ft.hide(shangcheng);
                ft.show(wode);
                ft.commit();
                bt_first_page.setImageResource(R.drawable.shouye);
                bt_jinrong.setImageResource(R.drawable.product2);
                bt_shangcheng.setImageResource(R.drawable.shangcheng);
                bt_wode.setImageResource(R.drawable.wode2);
                fir.setVisibility(View.GONE);
                jin.setVisibility(View.GONE);
                sha.setVisibility(View.GONE);
                wod.setVisibility(View.VISIBLE);
            }
        });

    }
}
