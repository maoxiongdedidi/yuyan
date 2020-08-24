package com.yueyi.yuyinfanyi.ui.help;

import android.app.Application;

import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.ui.home.HomeActivity;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class CancellationViewModel extends MyBaseViewModel {
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           startActivity(HomeActivity.class);
        }
    });
    public ObservableField<String> applyTime = new ObservableField<>();
    public ObservableField<String> day  = new ObservableField<>();
    public ObservableField<String> finishTime  = new ObservableField<>();
    public ObservableField<String> mobile  = new ObservableField<>();
    public CancellationViewModel(@NonNull Application application) {
        super(application);
    }
}
