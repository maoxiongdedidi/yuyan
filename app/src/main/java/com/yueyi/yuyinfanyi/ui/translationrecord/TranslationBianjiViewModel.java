package com.yueyi.yuyinfanyi.ui.translationrecord;

import android.app.Application;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.ui.checklanguage.CheckLanguageItemViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class TranslationBianjiViewModel extends MyBaseViewModel {
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand bianji = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    //给RecyclerView添加items
    public final ObservableList<TranslationBianJiItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    //封装一个界面发生改变的观察者
    public BindingCommand quanxuan = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //拿到position

            uc.quanxuan.setValue(0);
        }
    });
    public BindingCommand shanchu = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //拿到position
            uc.shanchu.setValue(0);
        }
    });
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //点击的条目
        public SingleLiveEvent<Integer> delete = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> kuozhan = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> yuedu = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> check = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> quanxuan = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> shanchu = new SingleLiveEvent<>();
    }
    public final ItemBinding<TranslationBianJiItemViewModel> itemBinding = ItemBinding.of(BR.translationbianjiitemviewmodel, R.layout.translation_bianji_item);
    public TranslationBianjiViewModel(@NonNull Application application) {
        super(application);
    }
}
