package com.yueyi.yuyinfanyi.ui.about;

import android.app.Application;

import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.ui.login.LoginActivity;
import com.yueyi.yuyinfanyi.utils.Utils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class AboutViewModel extends MyBaseViewModel {
    public ObservableField<String> version = new ObservableField<>("");
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           finish();
        }
    });
    public AboutViewModel(@NonNull Application application) {
        super(application);
        version.set("v"+ Utils.getAppVersionCode(getApplication()));
    }
}
