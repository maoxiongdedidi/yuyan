package caridentify.ding.com.adlibary.compat;

import android.app.Activity;
import android.view.ViewGroup;

import com.qq.e.ads.splash.SplashAD;

import caridentify.ding.com.adlibary.simple_iml.SdkSplashIpc;

/**
 * 广点通开屏广告
 */
public class GDTSplashAd extends AdSplashIpc{
    private SplashAD splashAD;
    private Activity activity;
    public GDTSplashAd(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public void loadSplash(String adCode, int timeOut, SdkSplashIpc splashAdListener) {
        splashAD = new SplashAD(activity,adCode,splashAdListener.getGDTAdListener(),timeOut);
        splashAD.fetchAdOnly();
    }

    @Override
    public void loadSplash(String adCode, SdkSplashIpc splashAdListener) {
        splashAD = new SplashAD(activity,adCode,splashAdListener.getGDTAdListener());
        splashAD.fetchAdOnly();
    }

    @Override
    public void showSplashAd(ViewGroup container) {
        splashAD.showAd(container);
    }
}