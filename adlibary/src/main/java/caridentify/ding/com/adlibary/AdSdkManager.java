package caridentify.ding.com.adlibary;

import android.content.Context;

import com.qq.e.comm.managers.GDTADManager;

import androidx.annotation.NonNull;
import caridentify.ding.com.adlibary.config.SDKAdBuild;
import caridentify.ding.com.adlibary.type.AdType;

/**
 * created by ding
 * on 2020/9/1
 */
public class AdSdkManager {
    private static Context mContext;
    private SDKAdBuild sdkAdBuild;
    private static AdSdkManager adSdkManager;

    private AdSdkManager(Context context) {
        mContext = context;
    }

    public static AdSdkManager getInstance(Context context) {
        if (null == adSdkManager){
            adSdkManager = new AdSdkManager(context);
        }
        return adSdkManager;
    }

    /**
     * 初始化广告SDK
     * 需要在使用之前进行初始化
     * */
    public void initSDKAd(@NonNull SDKAdBuild sdkAdBuild){
        this.sdkAdBuild = sdkAdBuild;
        switch (sdkAdBuild.type){
            case AD_TT:
                TTAdManagerHolder.init(mContext,sdkAdBuild);
                break;
            case AD_GDT:
                GDTADManager.getInstance().initWith(mContext, sdkAdBuild.GDT_APP_ID);
        }
    }

    public AdType getAdType(){
        return sdkAdBuild.type;
    }
}
