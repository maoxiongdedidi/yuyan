package caridentify.ding.com.adlibary;

import android.content.Context;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdSdk;

import caridentify.ding.com.adlibary.config.SDKAdBuild;

/**
 * created by ding
 * on 2020/9/1
 * 广告管理类
 */
public class TTAdManagerHolder {
    private static boolean sInit;


    public static TTAdManager get() {
        if (!sInit) {
            throw new RuntimeException("TTAdSdk is not init, please check.");
        }
        return TTAdSdk.getAdManager();
    }

    public static void init(Context context, SDKAdBuild sdkAdBuild) {
        doInit(context,sdkAdBuild);
    }
    private static void doInit(Context context,SDKAdBuild sdkAdBuild) {
        if (!sInit) {
            TTAdSdk.init(context, buildConfig(sdkAdBuild));
            sInit = true;
        }
    }

    private static TTAdConfig buildConfig(SDKAdBuild sdkAdBuild) {
        return new TTAdConfig.Builder()
                .appId(sdkAdBuild.CSJ_APPID)
                //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .useTextureView(true)
                .appName(sdkAdBuild.AppName)
                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                //是否允许sdk展示通知栏提示
                .allowShowNotify(true)
                //是否在锁屏场景支持展示广告落地页
                .allowShowPageWhenScreenLock(true)
                //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .debug(true)
                //允许直接下载的网络状态集合
                .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G)
                //是否支持多进程
                .supportMultiProcess(false)
                //.httpStack(new MyOkStack3())//自定义网络库，demo中给出了okhttp3版本的样例，其余请自行开发或者咨询工作人员。
                .build();
    }
}
