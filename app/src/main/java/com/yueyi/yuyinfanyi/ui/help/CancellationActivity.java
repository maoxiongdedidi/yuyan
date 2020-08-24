package com.yueyi.yuyinfanyi.ui.help;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.databinding.ActivityCancellationBinding;

public class CancellationActivity extends MyBaseActivity<ActivityCancellationBinding,CancellationViewModel> {



    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        return R.layout.activity_cancellation;
    }

    @Override
    public int initVariableId() {
        return BR.cancellationviewmodel;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle extras = getIntent().getExtras();
        String applyTime = extras.getString("applyTime");
        String day  = extras.getString("day");
        String finishTime  = extras.getString("finishTime");
        String mobile  = extras.getString("mobile");


        viewModel.day.set("账号注销将于"+day+"日以内完成，您需要了解以下信息：");
        viewModel.mobile.set("注销账号："+mobile);
        viewModel.applyTime.set("注销申请时间："+applyTime);




    }
}
