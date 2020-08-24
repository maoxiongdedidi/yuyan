package com.yueyi.yuyinfanyi.ui.checklanguage;

import android.app.Application;
import android.database.Observable;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class CheckLanguageViewModel extends MyBaseViewModel {
    public ObservableField<String> title = new ObservableField<String>("");
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
finish();
        }
    });
    //给RecyclerView添加items
    public final ObservableList<CheckLanguageItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //点击的条目
        public SingleLiveEvent<Integer> positon = new SingleLiveEvent<>();
    }
    public final ItemBinding<CheckLanguageItemViewModel> itemBinding = ItemBinding.of(BR.checklanguageitemviewmodel, R.layout.check_language_item);
    public CheckLanguageViewModel(@NonNull Application application) {
        super(application);

    }


}
