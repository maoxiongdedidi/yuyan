package com.yueyi.yuyinfanyi;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.yueyi.yuyinfanyi.advertissdk.SplashConfig;
import com.yueyi.yuyinfanyi.utils.TTSUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

import static com.yueyi.yuyinfanyi.utils.UserPreference.TAG;

public class MyApplication extends BaseApplication implements Application.ActivityLifecycleCallbacks {
    // 正常状态
    public static final int STATE_NORMAL = 0;
    // 从后台回到前台
    public static final int STATE_BACK_TO_FRONT = 1;
    // 从前台进入后台
    public static final int STATE_FRONT_TO_BACK = 2;
    // APP状态
    private static int sAppState = STATE_NORMAL;
    // 标记程序是否已进入后台(依据onStop回调)
    private boolean flag;
    // 标记程序是否已进入后台(依据onTrimMemory回调)
    private boolean background;
    // 从前台进入后台的时间
    private static long frontToBackTime;
    // 从后台返回前台的时间
    private static long backToFrontTime;
    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.setApplication(this);
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
        //广告
        SplashConfig.init(this.getApplicationContext());
        //友盟
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        TTSUtils.getInstance().init(getApplicationContext());
    }
        //进入后台30秒打开页面的判断，到时直接让application实现ActivityLifecycleCallbacks即可
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // TRIM_MEMORY_UI_HIDDEN是UI不可见的回调, 通常程序进入后台后都会触发此回调,大部分手机多是回调这个参数
        // TRIM_MEMORY_BACKGROUND也是程序进入后台的回调, 不同厂商不太一样, 魅族手机就是回调这个参数
        if (level == Application.TRIM_MEMORY_UI_HIDDEN || level == Application.TRIM_MEMORY_BACKGROUND) {
            background = true;
        } else if (level == Application.TRIM_MEMORY_COMPLETE) {
            background = !isCurAppTop(this);
        }
        if (background) {
            frontToBackTime = System.currentTimeMillis();
            sAppState = STATE_FRONT_TO_BACK;
            Log.e(TAG, "onTrimMemory: TRIM_MEMORY_UI_HIDDEN || TRIM_MEMORY_BACKGROUND");
        } else {
            sAppState = STATE_NORMAL;
        }

    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (background || flag) {
            background = false;
            flag = false;
            sAppState = STATE_BACK_TO_FRONT;
            backToFrontTime = System.currentTimeMillis();
            Log.e(TAG, "onResume: STATE_BACK_TO_FRONT");
            if (canShowAd()) {
                // TODO: 2020/3/10 显示广告
                // ShowADActivity.show(activity);
            }
        } else {
            sAppState = STATE_NORMAL;
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        //判断当前activity是否处于前台
        if (!isCurAppTop(activity)) {
            // 从前台进入后台
            sAppState = STATE_FRONT_TO_BACK;
            frontToBackTime = System.currentTimeMillis();
            flag = true;
            Log.e(TAG, "onStop: " + "STATE_FRONT_TO_BACK");
        } else {
            // 否则是正常状态
            sAppState = STATE_NORMAL;
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }


    /**
     * 进入后台间隔1分钟以后可以再次显示广告
     *
     * @return 是否能显示广告
     */

    public static boolean canShowAd() {
        return sAppState == STATE_BACK_TO_FRONT &&
                (backToFrontTime - frontToBackTime) > 1 * 60 * 1000;
    }

    /**
     * 判断当前程序是否前台进程
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public  boolean isCurAppTop(Context context) {
        if (context == null) {
            return false;
        }
        String curPackageName = context.getPackageName();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ActivityManager.RunningTaskInfo info = list.get(0);
            String topPackageName = info.topActivity.getPackageName();
            String basePackageName = info.baseActivity.getPackageName();
            if (topPackageName.equals(curPackageName) && basePackageName.equals(curPackageName)) {
                return true;
            }
        }
        return false;
    }

}
