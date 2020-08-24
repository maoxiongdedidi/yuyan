package com.yueyi.yuyinfanyi.utils;

import com.yueyi.yuyinfanyi.bean.CountryBean;

import java.util.List;

public class Language {
    private static Language mInstance;



    public static final String USER_LOGIN = "user_login";
    private Language() {

    }

    public synchronized static Language getInstance() {
        if (mInstance == null) {
            mInstance = new Language();
        }
        return mInstance;
    }

    private List<CountryBean> list;

    public List<CountryBean> getList() {




        return list;
    }
}
