package com.yueyi.yuyinfanyi.ui.translationrecord;

import android.app.Application;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class TranslationRecordViewModel extends MyBaseViewModel {
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand bianji = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(TranslationBianJiActivity.class);
        }
    });
    //给RecyclerView添加items
    public final ObservableList<TranslationRecordItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //点击的条目
        public SingleLiveEvent<Integer> delete = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> kuozhan = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> yuedu = new SingleLiveEvent<>();
    }
    public final ItemBinding<TranslationRecordItemViewModel> itemBinding = ItemBinding.of(BR.translationitemviewmodel, R.layout.translation_record_item);
    public TranslationRecordViewModel(@NonNull Application application) {
        super(application);
    }



}
