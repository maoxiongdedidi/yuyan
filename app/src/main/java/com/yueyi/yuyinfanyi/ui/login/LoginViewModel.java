package com.yueyi.yuyinfanyi.ui.login;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.LoginBean;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.ui.home.HomeActivity;
import com.yueyi.yuyinfanyi.ui.home.HomeViewModel;
import com.yueyi.yuyinfanyi.ui.web.WebViewActivity;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.UserPreference;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LoginViewModel extends MyBaseViewModel {
    private final String SHARE_APP_TAG = "firstOpen";
    private SharedPreferences setting;
    private Handler handler = new Handler();
    public int laiyuan = 0;
    private int count = 60;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (count == 0) {
                count = 60;
                countdown.set("发送验证码");
                countdownclickable.set(true);
            } else {
                countdownclickable.set(false);
                countdown.set(count + "秒后重试");
                count--;
                handler.postDelayed(runnable, 1000);
            }
        }
    };
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (laiyuan == 1) {
                startActivity(HomeActivity.class);
            }
            finish();
        }
    });
    public BindingCommand startcount = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getVerificationCode(phone.get());

        }
    });
    public BindingCommand login = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            loginByMobile();
        }
    });
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //左侧画框是否打开或者关闭
        public SingleLiveEvent<Boolean> isClick = new SingleLiveEvent<>();
    }


    public ObservableField<String> phone = new ObservableField<>("");
    public ObservableField<String> verification = new ObservableField<>("");
    public ObservableField<String> countdown = new ObservableField<>("发送验证码");
    public ObservableField<Boolean> countdownclickable = new ObservableField<>(true);

    public LoginViewModel(@NonNull Application application) {
        super(application);
        setting = getApplication().getSharedPreferences(SHARE_APP_TAG, 0);
        uc.isClick.setValue(false);
    }

    public boolean isPhone(String phone) {
        if (TextUtils.isEmpty(phone) && phone.length() < 11) {
            return false;
        }
        String regex = "^((1[0-9][0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        boolean isMatch = m.matches();
        return isMatch;
    }

    public void getVerificationCode(String mobile) {
        if (!isPhone(mobile)) {
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("appName", Contents.APPNAME);
        map.put("mobile", mobile);

        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).sendVerifyCode(map), this, new HttpInterFace() {
            @Override
            public void success(BaseBean t) {
                super.success(t);
                if (t.isSuccess()) {
                    ToastUtils.showShort("验证码已发送");
                    handler.post(runnable);
                }
            }

            @Override
            public void error(Exception e) {
                super.error(e);
            }
        });
    }

    public void loginByMobile() {
        if (!isPhone(phone.get())) {
            return;
        }
        if (TextUtils.isEmpty(verification.get()) || verification.get().length() < 6) {
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountId", UserPreference.getInstance(getApplication().getApplicationContext()).getAccountId() + "");
        map.put("appName", Contents.APPNAME);
        map.put("brand", SystemUtil.getDeviceBrand() + "");
        map.put("channel", Contents.CHANNEL);
        map.put("deviceModel", SystemUtil.getSystemModel() + "");
        map.put("deviceType", "ANDROID");
        map.put("mobile", phone.get());
        map.put("uuid", SystemUtil.getIMEI(getApplication().getApplicationContext()) + "");
        map.put("verifyCode", verification.get());
        map.put("version", Utils.getAppVersionCode(getApplication().getApplicationContext()));
        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).loginByMobile(map)
                , this, new HttpInterFace<LoginBean>() {
                    @Override
                    public void success(BaseBean<LoginBean> result) {
                        super.success(result);
                        ToastUtils.showShort("登录成功");
                        UserPreference.getInstance(getApplication().getApplicationContext()).removeAll();
                        UserPreference.getInstance(getApplication().getApplicationContext()).setaccountId(result.getResult().getAccountId());
                        UserPreference.getInstance(getApplication().getApplicationContext()).setmobile(phone.get());
                        UserPreference.getInstance(getApplication().getApplicationContext()).settoken(result.getResult().getToken());
                        SharedPreferences.Editor edit = setting.edit();
                        edit.putBoolean("isLogin", true);
                        edit.commit();
                        startActivity(HomeActivity.class);
                        finish();

                    }
                });

    }

}
