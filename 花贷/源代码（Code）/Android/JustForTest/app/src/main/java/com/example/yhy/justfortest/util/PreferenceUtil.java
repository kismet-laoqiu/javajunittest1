package com.example.yhy.justfortest.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by yhy on 19-6-26.
 */

public class PreferenceUtil extends Activity{
    private SharedPreferences sp;
    private SharedPreferences.Editor ed;
    public void setState(boolean flag){
        ed.putBoolean("exist_flag",flag);
        ed.apply();
    }
    public void setQuit(boolean flag){
        ed.putBoolean("quit_flag",flag);
        ed.apply();
    }
    public boolean getQuit(){
        return sp.getBoolean("quit_flag",false);
    }
    public boolean getState(){
        return sp.getBoolean("exist_flag",false);
    }
    public void setId(String idCode){
        ed.putString("id",idCode);
        ed.apply();
    }
    public String getId(){
        return sp.getString("id",null);
    }
    public PreferenceUtil(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        ed=sp.edit();
    }
    public void setPassword(String idCode){
        ed.putString("password",idCode);
        ed.apply();
    }
    public String getPassword(){
        return sp.getString("password",null);
    }
}
