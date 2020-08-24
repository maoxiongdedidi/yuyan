package com.yueyi.yuyinfanyi.ui.settingtextsize;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.SeekBar;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;


import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.databinding.ActivitySettingTextsizeBinding;
import com.yueyi.yuyinfanyi.utils.Contents;

public class SettingTextsizeActivity extends MyBaseActivity<ActivitySettingTextsizeBinding, SettingTextsizeViewModel> {



    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        return R.layout.activity_setting_textsize;
    }

    @Override
    public int initVariableId() {
        return BR.settingtextsizeviewmodel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.activitySettingTextsizeSb.setProgress(Contents.TRANSLATERESULTSIZE);
        binding.activitySettingTextsizeShili.setTextSize(TypedValue.COMPLEX_UNIT_SP,Contents.TRANSLATERESULTSIZE);
    }

    @Override
    public void initData() {
        super.initData();
        binding.activitySettingTextsizeSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Contents.TRANSLATERESULTSIZE=progress;
                binding.activitySettingTextsizeSb.setProgress(Contents.TRANSLATERESULTSIZE);
                binding.activitySettingTextsizeShili.setTextSize(TypedValue.COMPLEX_UNIT_SP,Contents.TRANSLATERESULTSIZE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
