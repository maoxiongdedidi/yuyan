package com.yueyi.yuyinfanyi.ui.extension;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.databinding.ActivityExtensionBinding;
import com.yueyi.yuyinfanyi.utils.Contents;

public class ExtensionActivity extends MyBaseActivity<ActivityExtensionBinding,ExtensionViewModel> {



    @Override
    public int initContentView(Bundle savedInstanceState) {

        return R.layout.activity_extension;
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.activityExtensionResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, Contents.TRANSLATERESULTSIZE);
    }

    @Override
    public void initData() {

        Bundle extras = getIntent().getExtras();
        String result = extras.getString("result");
        viewModel.target_language_text.set(result);
    }

    @Override
    public int initVariableId() {
        return BR.extensionviewmodel;
    }
}
