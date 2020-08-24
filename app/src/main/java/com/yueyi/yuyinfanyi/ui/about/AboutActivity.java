package com.yueyi.yuyinfanyi.ui.about;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.databinding.ActivityAboutBinding;
import com.yueyi.yuyinfanyi.ui.login.LoginActivity;
import com.yueyi.yuyinfanyi.ui.web.WebViewActivity;

public class AboutActivity extends MyBaseActivity<ActivityAboutBinding,AboutViewModel> {



    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        return R.layout.activity_about;
    }

    @Override
    public int initVariableId() {
        return BR.aboutviewmodel;
    }

    @Override
    public void initData() {
        super.initData();
        binding.activityAboutAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name","agree");
                intent.putExtra("BUNDLE",bundle);
                startActivity(intent);
            }
        });
        binding.activityAboutPrivocy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name","privacy");
                intent.putExtra("BUNDLE",bundle);

                startActivity(intent);
            }
        });
    }
}
