package com.yueyi.yuyinfanyi.ui.home;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.bean.APPBean;
import com.yueyi.yuyinfanyi.utils.Utils;

import me.goldze.mvvmhabit.utils.ToastUtils;

public class UpdatePopup extends Activity {
    private TextView title, content, commit;
    private ImageView delete;
    private APPBean appBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_popup);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = Utils.getScreenWidth(this);
        lp.height = Utils.getScreenHeight(this);
        dialogWindow.setAttributes(lp);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);

        Bundle extras = getIntent().getExtras();
        String data = extras.getString("data");
        if (data != null) {
            Gson gson = new Gson();
            appBean = gson.fromJson(data, APPBean.class
            );
        }


        initView();
    }


    private void initView() {
        title = (TextView) findViewById(R.id.update_popup_title);
        content = (TextView) findViewById(R.id.update_popup_content);
        commit = (TextView) findViewById(R.id.update_popup_commit);
        delete = (ImageView) findViewById(R.id.update_popup_delete);
        if (appBean.getVersionInfoVo().isForceUp()) {
            delete.setVisibility(View.GONE);
        } else {
            delete.setVisibility(View.VISIBLE);
        }
        title.setText(appBean.getVersionInfoVo().getTitle());
        content.setText(appBean.getVersionInfoVo().getContent());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowser(UpdatePopup.this, appBean.getVersionInfoVo().getLinkUrl());
                finish();
            }
        });

    }

    public void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ( appBean != null && appBean.getVersionInfoVo() != null) {
                if (appBean.getVersionInfoVo().isForceUp()) {
                    return false;
                }else{
                    finish();
                    return true;
                }
            } else {
                finish();
                return true;
            }
        }
        return false;
    }
}
