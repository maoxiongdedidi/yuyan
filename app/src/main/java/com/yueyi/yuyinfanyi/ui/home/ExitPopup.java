package com.yueyi.yuyinfanyi.ui.home;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.ui.login.LoginActivity;
import com.yueyi.yuyinfanyi.utils.Utils;

public class ExitPopup extends Activity {
    private TextView commit;
    private TextView delete;
    private ImageView finish;
    private final String SHARE_APP_TAG = "firstOpen";
    private boolean isLogin;
    private SharedPreferences setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_popup);
        setting = getSharedPreferences(SHARE_APP_TAG, 0);
        isLogin = setting.getBoolean("isLogin", false);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
//        lp.x = 0; // 新位置X坐标
//        lp.y = 0; // 新位置Y坐标
//        lp.alpha =1f; // 透明度
        lp.width= Utils.getScreenWidth(this);
        lp.height=Utils.getScreenHeight(this);
        dialogWindow.setAttributes(lp);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        initView();
    }



    private void initView() {
        commit=(TextView)findViewById(R.id.exit_commit);
        delete = (TextView)findViewById(R.id.exit_finish);
        finish=(ImageView)findViewById(R.id.exit_delete);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = setting.edit();
                edit.putBoolean("isLogin",false);
                edit.commit();
                Intent intent = new Intent(ExitPopup.this, LoginActivity.class);
                intent.putExtra("laiyuan",1);
                startActivity(intent);
                finish();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
