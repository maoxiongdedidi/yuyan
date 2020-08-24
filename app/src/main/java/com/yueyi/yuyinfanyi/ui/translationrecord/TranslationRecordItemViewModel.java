package com.yueyi.yuyinfanyi.ui.translationrecord;

import com.yueyi.yuyinfanyi.bean.CacheBaseBean;
import com.yueyi.yuyinfanyi.utils.Utils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class TranslationRecordItemViewModel extends ItemViewModel<TranslationRecordViewModel> {
    public CacheBaseBean cacheBaseBean;
    public BindingCommand delete = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //拿到position
            int position = viewModel.observableList.indexOf(TranslationRecordItemViewModel.this);
            viewModel.uc.delete.setValue(position);
        }
    });
    public BindingCommand kuozhan = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //拿到position
            int position = viewModel.observableList.indexOf(TranslationRecordItemViewModel.this);
            viewModel.uc.kuozhan.setValue(position);
        }
    });
    public BindingCommand yuedu = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //拿到position
            int position = viewModel.observableList.indexOf(TranslationRecordItemViewModel.this);
            viewModel.uc.yuedu.setValue(position);
        }
    });
    public ObservableField<String> time = new ObservableField<>();
    public TranslationRecordItemViewModel(@NonNull TranslationRecordViewModel viewModel, CacheBaseBean cacheBaseBean) {
        super(viewModel);
        this.cacheBaseBean=cacheBaseBean;
        String db_id = cacheBaseBean.getDb_Id();
        long l = Long.valueOf(db_id) / 1000;
        String s = Utils.timeStamp2Date(l + "", "MM-dd HH:mm");
        time.set(s);
    }
}
