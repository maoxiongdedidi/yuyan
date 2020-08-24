package com.yueyi.yuyinfanyi.utils;


import com.yueyi.yuyinfanyi.bean.BaseBean;

/**
 * Created by Administrator on 2019/8/1.
 */

public interface HttpInterFaceClass<T> {
    void startDialog();

    void dismissloading();

    void noLogin();

    void success(BaseBean<T> result);

    void error(Exception e);
}
