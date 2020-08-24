package com.yueyi.yuyinfanyi.ui.help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.WindowManager;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.databinding.ActivityHelpBinding;
import com.yueyi.yuyinfanyi.utils.UserPreference;

public class HelpActivity extends MyBaseActivity<ActivityHelpBinding,HelpViewModel> {



    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        return R.layout.activity_help;
    }

    @Override
    public int initVariableId() {
        return BR.helpviewmpodel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.openPopup.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(TextUtils.isEmpty(UserPreference.getInstance(HelpActivity.this).getApplyCancelTime())){
                    CancellationPopup popup = new CancellationPopup(HelpActivity.this);
                    popup.showAtLocation(binding.activityHelpTitle, Gravity.CENTER, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

                }else{
                    Intent intent = new Intent(HelpActivity.this,CancellationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("applyTime",UserPreference.getInstance(HelpActivity.this).getApplyCancelTime());
                    bundle.putString("day","15");
                    bundle.putString("finishTime","");
                    bundle.putString("mobile",UserPreference.getInstance(HelpActivity.this).getMobile());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }
}
