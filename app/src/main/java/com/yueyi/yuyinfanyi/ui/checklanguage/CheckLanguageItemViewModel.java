package com.yueyi.yuyinfanyi.ui.checklanguage;

import android.app.Application;
import android.view.View;

import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.bean.CountryBean;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class CheckLanguageItemViewModel extends ItemViewModel<CheckLanguageViewModel> {
    public CountryBean countryBean;
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //拿到position
            int position = viewModel.observableList.indexOf(CheckLanguageItemViewModel.this);

            viewModel.uc.positon.setValue(position);
        }
    });
    public ObservableField<Integer> isLock = new ObservableField<>(View.VISIBLE);

    public CheckLanguageItemViewModel(@NonNull CheckLanguageViewModel viewModel,CountryBean countryBean) {
        super(viewModel);

        this.countryBean=countryBean;
        if(countryBean.isLock()){
            isLock.set(View.VISIBLE);
        }else{
            isLock.set(View.GONE);
        }
    }

}
