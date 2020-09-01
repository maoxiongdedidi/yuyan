package caridentify.ding.com.adlibary.compat;

import android.content.Context;



/**
 * 开屏广告基础类
 */
public abstract class AdSplashIpc implements AdSplashCompatIpc {
    private Context context;

    public AdSplashIpc(Context context) {
        this.context = context;
    }
}