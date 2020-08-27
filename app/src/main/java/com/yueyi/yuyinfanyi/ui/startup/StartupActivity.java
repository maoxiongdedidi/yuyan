package com.yueyi.yuyinfanyi.ui.startup;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.MainActivity;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.LoginBean;
import com.yueyi.yuyinfanyi.databinding.ActivityStartupBinding;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.ui.home.HomeActivity;
import com.yueyi.yuyinfanyi.ui.login.LoginActivity;
import com.yueyi.yuyinfanyi.ui.web.WebViewActivity;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.UserPreference;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;


public class StartupActivity extends MyBaseActivity<ActivityStartupBinding,StartupViewModel> {
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

}
