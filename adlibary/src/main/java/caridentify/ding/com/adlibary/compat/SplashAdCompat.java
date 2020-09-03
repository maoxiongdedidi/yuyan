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
 * 开屏广告实现类
 */
public class SplashAdCompat implements AdSplashCompatIpc{

    private AdSplashIpc adSplashIpc;
    private Activity context;
    public SplashAdCompat(Activity context) {
        this.context = context;
    }

    public void setAdSplashIpc(AdSplashIpc adSplashIpc) {
        this.adSplashIpc = adSplashIpc;
    }

    @Override
    public void loadSplash(String adCode, int timeOut, SdkSplashIpc splashAdListener) {
        if(adSplashIpc!=null){
            adSplashIpc.loadSplash(adCode,timeOut,splashAdListener);
        }
    }

    @Override
    public void loadSplash(String adCode, SdkSplashIpc splashAdListener) {
        if(adSplashIpc!=null){
            adSplashIpc.loadSplash(adCode,splashAdListener);
        }
    }

    @Override
    public void showSplashAd(ViewGroup container) {
        if(adSplashIpc!=null){
            adSplashIpc.showSplashAd(container);
        }
    }









}
