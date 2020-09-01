package caridentify.ding.com.adlibary.config;


import caridentify.ding.com.adlibary.type.AdType;

/**
 * 广告配置类
 * */
public class SDKAdBuild{
    /**
     * 穿山甲的appId， 必选参数，设置应用的AppId
     */
    public String CSJ_APPID = "5098536";
    /**
     * 必选参数，设置应用名称
     */
    public String AppName = "语言翻译";
    /**
     * 穿山甲开屏广告
     */
    public String CSJ_CODEID = "887368004";
    /**
     * 广点通的appId， 必选参数，设置应用的AppId
     */
    public String GDT_APP_ID = "1110865472";
    /**
     * 广点通开平广告
     */
    public String GDT_POS_ID = "4071622600410903";
    /**
     * 广告类型
     */
    public AdType type = AdType.AD_TT;
}