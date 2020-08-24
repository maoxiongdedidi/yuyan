package com.yueyi.yuyinfanyi.ui.help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import me.goldze.mvvmhabit.base.BaseViewModel;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.databinding.ActivityHelpCommitBinding;

public class HelpCommitActivity extends MyBaseActivity<ActivityHelpCommitBinding,HelpCommitViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        return R.layout.activity_help_commit;
    }

    @Override
    public int initVariableId() {
        return BR.helpcommitviewmodel;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        viewModel.title.set(title+"：");
        binding.activityHelpCommitContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString()!=null&&editable.toString().length()>=4){
                    viewModel.uc.isClick.setValue(true);
                }else{
                    viewModel.uc.isClick.setValue(false);
                }
            }
        });
    }

    @Override
    public void initViewObservable() {

        viewModel.uc.isClick.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.activityHelpCommitButton.setBackgroundResource(R.drawable.bg_startup_dialog_button);
                }else{
                    binding.activityHelpCommitButton.setBackgroundResource(R.drawable.bg_startup_dialog_unclick_button);
                }
            }
        });
    }
}
