package com.yueyi.yuyinfanyi.advertissdk;

import android.content.Context;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.yueyi.yuyinfanyi.utils.Contents;

public class TTAdManagerHolder {
    private static boolean sInit;


    public static TTAdManager get() {
        if (!sInit) {
            throw new RuntimeException("TTAdSdk is not init, please check.");
        }
        return TTAdSdk.getAdManager();
    }

    public static void init(Context context) {
        doInit(context);
    }

    //step1:接入网盟广告sdk的初始化操作，详情见接入文档和穿山甲平台说明
    private static void doInit(Context context) {
        if (!sInit && context != null) {
            try {
                TTAdSdk.init(context, buildConfig());
            } catch (Exception e){
                e.printStackTrace();
            }
            sInit = true;
        }
    }

    private static TTAdConfig buildConfig() {
        return new TTAdConfig.Builder()
                .appId(Contents.CSJ_APPID)
                .useTextureView(true)
                //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .appName(Contents.APP_NAME)
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowShowNotify(true)
                //是否允许sdk展示通知栏提示
                .allowShowPageWhenScreenLock(true)
                //是否在锁屏场景支持展示广告落地页
                .debug(true)
                //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G)
                //允许直接下载的网络状态集合
                .supportMultiProcess(true)
                //是否支持多进程
                .build();
    }
}
