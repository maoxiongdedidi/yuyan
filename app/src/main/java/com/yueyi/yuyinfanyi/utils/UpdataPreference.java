package com.yueyi.yuyinfanyi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yueyi.yuyinfanyi.bean.YuYinCount;

public class UpdataPreference {
    public final static String TAG = UpdataPreference.class.getSimpleName();

    private static UpdataPreference mInstance;

    private SharedPreferences mPreferences;

    public static final String USER_LOGIN = "updata_popup";
    private UpdataPreference(Context context) {
        mPreferences = context.getSharedPreferences(USER_LOGIN, 0);
    }

    public synchronized static UpdataPreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UpdataPreference(context);
        }
        return mInstance;
    }

    public boolean isOpenPopup(){
        long last_open = mPreferences.getLong("last_open", 0);
        long l = System.currentTimeMillis();
       long shijiancha= l-7*24*60*60*1000;
        if(last_open==0){
            SharedPreferences.Editor edit = mPreferences.edit();
            edit.putLong("last_open",System.currentTimeMillis());
            edit.commit();
            return true;
        }else{
            if(shijiancha<last_open){
                return false;
            }else if(shijiancha==last_open||shijiancha>last_open){
                SharedPreferences.Editor edit = mPreferences.edit();
                edit.putLong("last_open",l);
                edit.commit();
                return true;
            }
        }
        return true;
    }




    //检测是否能够使用语言翻译
   public boolean checkCount (){
       String yuyin_count_json = mPreferences.getString("paizhao_count_json", null);
       if(TextUtils.isEmpty(yuyin_count_json)){
           YuYinCount yuYinCount = new YuYinCount();
           yuYinCount.setCount(1);
           yuYinCount.setTime(Utils.timeStamp2Date(System.currentTimeMillis()/1000+"","yyyy:MM:dd"));
           Gson gson = new Gson();
           String s = gson.toJson(yuYinCount);
           SharedPreferences.Editor edit = mPreferences.edit();
           edit.putString("paizhao_count_json",s);
           edit.commit();
           return true;
       }else{
           Gson gson = new Gson();
           YuYinCount yuYinCount = gson.fromJson(yuyin_count_json, YuYinCount.class);
           String timeLast = yuYinCount.getTime();
           String timeNow =Utils.timeStamp2Date(System.currentTimeMillis()/1000+"","yyyy:MM:dd");
           Log.e("=====",timeNow+"====="+timeLast);
           if(timeLast.equals(timeNow)){
               int count = yuYinCount.getCount();
               if(count==0){
                   YuYinCount yuYinCount1 = new YuYinCount();
                   yuYinCount1.setCount(1);
                   yuYinCount1.setTime(Utils.timeStamp2Date(System.currentTimeMillis()/1000+"","yyyy:MM:dd"));
                   Gson gson1 = new Gson();
                   String s = gson1.toJson(yuYinCount1);
                   SharedPreferences.Editor edit = mPreferences.edit();
                   edit.putString("paizhao_count_json",s);
                   edit.commit();
                   return true;
               }else if(count==1){
                   YuYinCount yuYinCount1 = new YuYinCount();
                   yuYinCount1.setCount(2);
                   yuYinCount1.setTime(Utils.timeStamp2Date(System.currentTimeMillis()/1000+"","yyyy:MM:dd"));
                   Gson gson1 = new Gson();
                   String s = gson1.toJson(yuYinCount1);
                   SharedPreferences.Editor edit = mPreferences.edit();
                   edit.putString("paizhao_count_json",s);
                   edit.commit();
                   return true;
               }else{
                   return false;
               }
           }else{
               YuYinCount yuYinCount1 = new YuYinCount();
               yuYinCount1.setCount(1);
               yuYinCount1.setTime(Utils.timeStamp2Date(System.currentTimeMillis()/1000+"","yyyy:MM:dd"));
               Gson gson1 = new Gson();
               String s = gson1.toJson(yuYinCount1);
               SharedPreferences.Editor edit = mPreferences.edit();
               edit.putString("paizhao_count_json",s);
               edit.commit();
               return true;
           }
       }

   }


}
