package com.yueyi.yuyinfanyi.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.databinding.ActivityLoginBinding;
import com.yueyi.yuyinfanyi.ui.home.HomeActivity;
import com.yueyi.yuyinfanyi.ui.startup.StartupActivity;
import com.yueyi.yuyinfanyi.ui.web.WebViewActivity;

import androidx.lifecycle.Observer;

import static android.view.KeyEvent.KEYCODE_BACK;

public class LoginActivity extends MyBaseActivity<ActivityLoginBinding,LoginViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.loginviewmodel;
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        int laiyuan = intent.getIntExtra("laiyuan", 0);
        viewModel.laiyuan=laiyuan;
        binding.activityLoginAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name","agree");
                intent.putExtra("BUNDLE",bundle);
                startActivity(intent);
            }
        });
        binding.activityLoginPrivocy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name","privacy");
                intent.putExtra("BUNDLE",bundle);

                startActivity(intent);
            }
        });
        binding.activityLoginVerification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==6&&viewModel.isPhone(viewModel.phone.get())){
                    viewModel.uc.isClick.setValue(true);
                }else{
                    viewModel.uc.isClick.setValue(false);
                }
            }
        });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.isClick.observe(this, aBoolean -> {
            if(aBoolean){
                binding.activityLoginLogin.setBackgroundResource(R.drawable.bg_startup_dialog_button);
            }else{
                binding.activityLoginLogin.setBackgroundResource(R.drawable.bg_startup_dialog_unclick_button);
            }
        });
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            return isCosumenBackKey();
        }
        return false;
    }

    private boolean isCosumenBackKey() {
       if(viewModel.laiyuan==1){
           startActivity(HomeActivity.class);
       }
       finish();
        return true;
    }
}
