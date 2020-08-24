package com.yueyi.yuyinfanyi.httppager;

import androidx.annotation.Nullable;
import me.goldze.mvvmhabit.http.ResponseThrowable;

public class ReLogin extends ResponseThrowable {


    public ReLogin(Throwable throwable, int code) {
        super(throwable, code);
    }

}
