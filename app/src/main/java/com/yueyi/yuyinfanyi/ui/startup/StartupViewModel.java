package com.yueyi.yuyinfanyi.ui.startup;

import android.app.Application;
import android.text.TextUtils;

import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.bean.APPBean;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.LoginBean;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.HashMap;

import androidx.annotation.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class StartupViewModel extends MyBaseViewModel {
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        public SingleLiveEvent<APPBean> appinfo = new SingleLiveEvent<>();
        public UIChangeObservable() {
        }
    }
    public StartupViewModel(@NonNull Application application) {
        super(application);
    }
    public void getAppinfo(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("appName", Contents.APPNAME);
        map.put("brand", SystemUtil.getDeviceBrand()+"");
        map.put("channel", Contents.CHANNEL);
        map.put("deviceModel", SystemUtil.getSystemModel()+"");
        map.put("deviceType", "ANDROID");
        map.put("uuid", SystemUtil.getIMEI(getApplication().getApplicationContext())+"");
        map.put("version", Utils.getAppVersionCode(getApplication().getApplicationContext()));
        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).appinfo(map)
                ,this,new HttpInterFace<APPBean>(){
                    @Override
                    public void success(BaseBean<APPBean> result) {
                        super.success(result);
                        uc.appinfo.setValue(result.getResult());
                    }

                    @Override
                    public void error(Exception e) {
                        super.error(e);
                        uc.appinfo.setValue(null);
                    }
                });


    }
}
