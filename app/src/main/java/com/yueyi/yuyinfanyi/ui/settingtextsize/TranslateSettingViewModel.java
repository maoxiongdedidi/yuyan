package com.yueyi.yuyinfanyi.ui.settingtextsize;

import android.app.Application;

import com.yueyi.yuyinfanyi.base.MyBaseViewModel;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class TranslateSettingViewModel extends MyBaseViewModel {
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    //设置字体
    public BindingCommand settingtextsize = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          startActivity(SettingTextsizeActivity.class);
        }
    });
    public TranslateSettingViewModel(@NonNull Application application) {
        super(application);
    }
}
