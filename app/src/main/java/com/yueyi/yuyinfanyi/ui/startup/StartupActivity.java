package com.yueyi.yuyinfanyi.ui.startup;



import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.widget.LinearLayout;


import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.advertissdk.RewardAdManager;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.databinding.ActivityStartupBinding;
import com.yueyi.yuyinfanyi.ui.home.HomeActivity;


import androidx.annotation.NonNull;
import com.yueyi.yuyinfanyi.R;

public class StartupActivity extends MyBaseActivity<ActivityStartupBinding,StartupViewModel> implements RewardAdManager.RewardAdManagerListener{
    private final String SHARE_APP_TAG = "firstOpen";
    private SharedPreferences setting;
    private Boolean first;

    private LinearLayout viewById;

    private StartupPopup startupPopup;


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
        viewById = (LinearLayout) findViewById(R.id.activity_startup_all_lauyout);
        setting = getSharedPreferences(SHARE_APP_TAG, 0);
        first = setting.getBoolean("FIRST", true);
        defaultSecond=2;
        handler.sendEmptyMessageDelayed(1, 1000);
        RewardAdManager rewardAdManager = new RewardAdManager();
        rewardAdManager.ShowSplashAd(this,this);
    }

    private int defaultSecond = 2;  //显示默认图时间2s
    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if(msg.what==1){
                defaultSecond--;
                if (defaultSecond == 0) {
                    if(first){
                        openPopW();

                    }else{
                        startActivity(HomeActivity.class);
                        finish();
                    }

                } else {
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
            }
            return true;
        }
    });


    @Override
    protected void onResume() {
        super.onResume();

    }
    public void openPopW(){
        startActivity(StartupPopup.class);
        finish();
    }

    @Override
    public void rewardVideAdComplete() {

    }

    @Override
    public void rewardVideAdClose() {

    }
}
