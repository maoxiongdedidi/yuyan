package com.yueyi.yuyinfanyi.ui.startup;



import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.databinding.ActivityStartupBinding;
import com.yueyi.yuyinfanyi.ui.home.HomeActivity;


import androidx.annotation.NonNull;
import caridentify.ding.com.adlibary.compat.SplashAdCompat;
import caridentify.ding.com.adlibary.simple_iml.SdkSplashIpc;

import com.yueyi.yuyinfanyi.R;


public class StartupActivity extends MyBaseActivity<ActivityStartupBinding,StartupViewModel> {
    private final String SHARE_APP_TAG = "firstOpen";
    private SharedPreferences setting;
    private Boolean first;

    private StartupPopup startupPopup;
    private FrameLayout splash_container;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_startup;
    }

    @Override
    public int initVariableId() {
        return BR.startupviewmodel;
    }

    @Override
    public void permissions() {

    }

    @Override
    public void initData() {
        super.initData();
        splash_container = findViewById(R.id.splash_container);
        setting = getSharedPreferences(SHARE_APP_TAG, 0);
        first = setting.getBoolean("FIRST", true);
        setSplashAd();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    public void openPopW(){
        startActivity(StartupPopup.class);
        finish();
    }

    /**
     * 设置开屏广告
     * */
    SplashAdCompat splashAdCompat;
    private void setSplashAd(){
        splashAdCompat = new SplashAdCompat(this);
        splashAdCompat.loadSplash("4071622600410903", 3000, new SdkSplashIpc() {
            @Override
            public void splashComplete() {
                splash_container.removeAllViews();
                splash_container.setVisibility(View.VISIBLE);
                splashAdCompat.showSplashAd(splash_container);
            }

            @Override
            public void splashOnError(int i, String s) {
                Log.e("YM","出错");
            }

            @Override
            public void splashOnTimeout() {
                Log.e("YM","超时");
            }

            @Override
            public void OnAdSkip() {
                goToMainActivity();
                splash_container.removeAllViews();
                splash_container.setVisibility(View.GONE);
            }

            @Override
            public void OnAdTimeOver() {
                goToMainActivity();


            }
        });
    }

    public void goToMainActivity(){
        if(first){
            openPopW();

        }else{
            startActivity(HomeActivity.class);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splash_container.removeAllViews();
        splash_container.setVisibility(View.GONE);
    }
}
