package caridentify.ding.com.adlibary.compat;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import caridentify.ding.com.adlibary.AdSdkManager;
import caridentify.ding.com.adlibary.kind.TTSplashAd;
import caridentify.ding.com.adlibary.simple_iml.SdkSplashIpc;
import caridentify.ding.com.adlibary.type.AdType;

/**
 * created by yiwei010
 * on 2020/9/2
 */
public class SplashAdHolper {
    private AdType adType = AdType.AD_TT;
    private Activity context;
    private SplashAdCompat splashAdCompat;
    private AdSplashIpc adSplashIpc;
    public SplashAdHolper(Activity context, AdType adType) {
        this.context = context;
        splashAdCompat = new SplashAdCompat(context);
        this.adType = adType==null? AdSdkManager.getInstance(context).getAdType():adType;
    }


    public void showAD(String ttCode,String gstCode,int timeOut,ShowAdCallBack showAdCallBack){
        if(adType == AdType.AD_TT){
            showTTad(ttCode,gstCode,timeOut,showAdCallBack);
        }else{
            showGDTad(ttCode,gstCode,timeOut,showAdCallBack);
        }
    }

    private void showTTad(String ttCode, final String gstCode, final int timeOut, final ShowAdCallBack showAdCallBack){
        adSplashIpc = new TTSplashAd(context);
        splashAdCompat.setAdSplashIpc(adSplashIpc);
        splashAdCompat.loadSplash(ttCode, timeOut, new SdkSplashIpc() {
            @Override
            public void splashOnError(int i, String s) {
                adSplashIpc = new GDTSplashAd(context);
                splashAdCompat.setAdSplashIpc(adSplashIpc);
                splashAdCompat.loadSplash(gstCode, timeOut, new SdkSplashIpc() {
                    @Override
                    public void splashOnError(int i, String s) {
                        showAdCallBack.error();
                    }

                    @Override
                    public void splashOnTimeout() {
                        showAdCallBack.error();
                    }

                    @Override
                    public void splashComplete() {
                        showAdCallBack.splashComplete();
                    }

                    @Override
                    public void OnAdSkip() {
                        showAdCallBack.OnAdSkip();
                    }

                    @Override
                    public void OnAdTimeOver() {
                        showAdCallBack.OnAdTimeOver();
                    }
                });
            }

            @Override
            public void splashOnTimeout() {
                adSplashIpc = new GDTSplashAd(context);
                splashAdCompat.setAdSplashIpc(adSplashIpc);
                splashAdCompat.loadSplash(gstCode, timeOut, new SdkSplashIpc() {
                    @Override
                    public void splashOnError(int i, String s) {
                        showAdCallBack.error();
                    }

                    @Override
                    public void splashOnTimeout() {
                        showAdCallBack.error();
                    }

                    @Override
                    public void splashComplete() {
                        showAdCallBack.splashComplete();
                    }

                    @Override
                    public void OnAdSkip() {
                        showAdCallBack.OnAdSkip();
                    }

                    @Override
                    public void OnAdTimeOver() {
                        showAdCallBack.OnAdTimeOver();
                    }
                });
            }

            @Override
            public void splashComplete() {
                showAdCallBack.splashComplete();
            }

            @Override
            public void OnAdSkip() {
                showAdCallBack.OnAdSkip();
            }

            @Override
            public void OnAdTimeOver() {
                showAdCallBack.OnAdTimeOver();
            }
        });
    }



    private void showGDTad(final String ttCode, final String gstCode, final int timeOut, final ShowAdCallBack showAdCallBack){
        adSplashIpc = new GDTSplashAd(context);
        splashAdCompat.setAdSplashIpc(adSplashIpc);
        splashAdCompat.loadSplash(gstCode, timeOut, new SdkSplashIpc() {
            @Override
            public void splashOnError(int i, String s) {
                adSplashIpc = new TTSplashAd(context);
                splashAdCompat.setAdSplashIpc(adSplashIpc);
                splashAdCompat.loadSplash(gstCode, timeOut, new SdkSplashIpc() {
                    @Override
                    public void splashOnError(int i, String s) {
                        showAdCallBack.error();
                    }

                    @Override
                    public void splashOnTimeout() {
                        showAdCallBack.error();
                    }

                    @Override
                    public void splashComplete() {
                        showAdCallBack.splashComplete();
                    }

                    @Override
                    public void OnAdSkip() {
                        showAdCallBack.OnAdSkip();
                    }

                    @Override
                    public void OnAdTimeOver() {
                        showAdCallBack.OnAdTimeOver();
                    }
                });
            }

            @Override
            public void splashOnTimeout() {
                adSplashIpc = new GDTSplashAd(context);
                splashAdCompat.setAdSplashIpc(adSplashIpc);
                splashAdCompat.loadSplash(gstCode, timeOut, new SdkSplashIpc() {
                    @Override
                    public void splashOnError(int i, String s) {
                        showAdCallBack.error();
                    }

                    @Override
                    public void splashOnTimeout() {
                        showAdCallBack.error();
                    }

                    @Override
                    public void splashComplete() {
                        showAdCallBack.splashComplete();
                    }

                    @Override
                    public void OnAdSkip() {
                        showAdCallBack.OnAdSkip();
                    }

                    @Override
                    public void OnAdTimeOver() {
                        showAdCallBack.OnAdTimeOver();
                    }
                });
            }

            @Override
            public void splashComplete() {
                showAdCallBack.splashComplete();
            }

            @Override
            public void OnAdSkip() {
                showAdCallBack.OnAdSkip();
            }

            @Override
            public void OnAdTimeOver() {
                showAdCallBack.OnAdTimeOver();
            }
        });
    }




    public interface ShowAdCallBack{
        void splashComplete();
        void OnAdSkip();
        void OnAdTimeOver();
        void error();
    }


    public void showSplashAd(ViewGroup container){
        if(splashAdCompat!=null){
            splashAdCompat.showSplashAd(container);
        }
    }


}
