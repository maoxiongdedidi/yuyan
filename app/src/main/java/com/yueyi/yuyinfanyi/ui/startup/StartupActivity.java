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
import caridentify.ding.com.adlibary.compat.SplashAdHolper;
import caridentify.ding.com.adlibary.config.SDKAdBuild;
import caridentify.ding.com.adlibary.simple_iml.SdkSplashIpc;
import caridentify.ding.com.adlibary.type.AdType;

import com.yueyi.yuyinfanyi.R;


public class StartupActivity extends MyBaseActivity<ActivityStartupBinding,StartupViewModel> {
    private final String SHARE_APP_TAG = "firstOpen";
    private SharedPreferences setting;
    private Boolean first;

    private StartupPopup startupPopup;
    private FrameLayout splash_container;
    private boolean isShowAd;

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
        isShowAd = getIntent().getBooleanExtra("isShowAd", false);
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
    private void setSplashAd(){
        SplashAdHolper splashAdHolper = new SplashAdHolper(this, AdType.AD_TT);
        splashAdHolper.showAD(SDKAdBuild.CSJ_CODEID, SDKAdBuild.GDT_POS_ID, 2000, new SplashAdHolper.ShowAdCallBack() {
            @Override
            public void splashComplete() {
                splash_container.removeAllViews();
                splash_container.setVisibility(View.VISIBLE);
                splashAdHolper.showSplashAd(splash_container);
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

            @Override
            public void error() {
                goToMainActivity();
            }
        });
    }

    public void goToMainActivity(){
        if(isShowAd){
            finish();
            return;
        }
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
