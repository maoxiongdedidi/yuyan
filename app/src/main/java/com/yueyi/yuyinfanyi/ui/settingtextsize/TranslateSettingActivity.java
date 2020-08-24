package com.yueyi.yuyinfanyi.ui.settingtextsize;

import androidx.appcompat.app.AppCompatActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.databinding.ActivityTranslateSettingBinding;
import com.yueyi.yuyinfanyi.utils.UserPreference;

public class TranslateSettingActivity extends MyBaseActivity<ActivityTranslateSettingBinding,TranslateSettingViewModel> {



    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        return R.layout.activity_translate_setting;
    }

    @Override
    public int initVariableId() {
        return BR.translatesettingviewmodel;
    }

    @Override
    public void initData() {
        super.initData();
        boolean autoAlound = UserPreference.getInstance(TranslateSettingActivity.this).getAutoAlound();
        binding.activityTranslateSettingSwitch.setChecked(autoAlound);
        binding.activityTranslateSettingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ToastUtils.showShort("目前只支持英文和中文朗读");
                    UserPreference.getInstance(TranslateSettingActivity.this).isAutoAlound(true);
                }else{
                    ToastUtils.showShort("已关闭自动朗读");
                    UserPreference.getInstance(TranslateSettingActivity.this).isAutoAlound(false);
                }
            }
        });
    }
}
