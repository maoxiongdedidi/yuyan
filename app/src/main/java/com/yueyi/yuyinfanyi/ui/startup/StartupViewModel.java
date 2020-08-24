package com.yueyi.yuyinfanyi.ui.startup;

import android.app.Application;
import android.text.TextUtils;

import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.LoginBean;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.HashMap;

import androidx.annotation.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class StartupViewModel extends MyBaseViewModel {
    public StartupViewModel(@NonNull Application application) {
        super(application);
    }

}
