package com.yueyi.yuyinfanyi.utils;

import com.yueyi.yuyinfanyi.MyApplication;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.bean.CacheBaseBean;
import com.yueyi.yuyinfanyi.bean.RecognitionResultBean;

import java.util.List;

public class Contents {
    public static final String BAIDUKEY = "20190515000297961";
    public static final String BAIDUSECRET = "Z0P8nBPSg6bJ4a_EXxNl";
    public static int TRANSLATERESULTSIZE = 26;
    public static String STAFF_LANGUAGE = "中文";
    public static String TARGET_LANGUAGE = "英文";
    public static final String APPNAME = "YUYINFANYI";
    public static final String CHANNEL = "HUAWEI";
    public static List<RecognitionResultBean.WordsResultBean> wordsResultBeans;
    public static CacheBaseBean cacheBaseBean = null;
    public static final String CSJ_APPID = "5098536";
    public static final String CSJ_CODEID = "887368478";//840073445
    public static final String GDT_APP_ID = "1110865472";
    public static final String GDT_POS_ID = "4071622600410903";

    public static final String APP_NAME = MyApplication.getInstance().getString(R.string.app_name);
}

