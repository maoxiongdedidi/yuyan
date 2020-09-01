package caridentify.ding.com.adlibary.compat;

import android.app.Activity;
import android.view.ViewGroup;



import caridentify.ding.com.adlibary.AdSdkManager;
import caridentify.ding.com.adlibary.kind.TTSplashAd;
import caridentify.ding.com.adlibary.simple_iml.SdkSplashIpc;
import caridentify.ding.com.adlibary.type.AdType;

/**
 * created by yiwei010
 * on 2020/9/1
 */
public class SplashAdCompat implements AdSplashCompatIpc{
    private AdType adType = AdType.AD_TT;
    private AdSplashIpc adSplashIpc;
    public SplashAdCompat(Activity context) {
        this.adType = AdSdkManager.getInstance(context).getAdType();
        switch (adType){
            case AD_GDT:
                adSplashIpc = new GDTSplashAd(context);
                break;
            case AD_TT:
                adSplashIpc = new TTSplashAd(context);
                break;
        }
    }

    @Override
    public void loadSplash(String adCode, int timeOut, SdkSplashIpc splashAdListener) {
        adSplashIpc.loadSplash(adCode,timeOut,splashAdListener);
    }

    @Override
    public void loadSplash(String adCode, SdkSplashIpc splashAdListener) {
        adSplashIpc.loadSplash(adCode,splashAdListener);
    }

    @Override
    public void showSplashAd(ViewGroup container) {
        adSplashIpc.showSplashAd(container);
    }
}
