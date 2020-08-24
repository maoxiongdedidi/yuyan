package com.yueyi.yuyinfanyi.base;

import android.app.Application;

import com.yueyi.yuyinfanyi.utils.MyProgressDialog;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class MyBaseViewModel extends BaseViewModel {
    private MyProgressDialog progressDialog;
    //封装一个界面发生改变的观察者
    public UIChangeObservable ucBaseViewModel = new UIChangeObservable();

    public class UIChangeObservable {

        public SingleLiveEvent<String> openDialog = new SingleLiveEvent<>();
    }

    public MyBaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void showDialog(String msg) {
        ucBaseViewModel.openDialog.setValue(msg);

    }
    @Override
    public void dismissDialog() {
        ucBaseViewModel.openDialog.setValue("");
    }
}
