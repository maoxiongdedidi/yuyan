package com.yueyi.yuyinfanyi.ui.startup;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.ui.login.LoginActivity;
import com.yueyi.yuyinfanyi.ui.web.WebViewActivity;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;
import io.reactivex.functions.Consumer;

public class StartupPopup extends FragmentActivity implements Utils.ClickListener {

    private TextView tv,agree,unagree;
    private ArrayList<String> str_list;
    private ArrayList<Integer> color_list;
    private final String SHARE_APP_TAG = "firstOpen";
    private SharedPreferences setting;
    private Boolean first;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.startup_dialog);

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
        permissions();
    }
    public void permissions(){
        RxPermissions rxPermissions =new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                    }
                });
    }

    private void initView() {
        setting = getSharedPreferences(SHARE_APP_TAG, 0);
        first = setting.getBoolean("FIRST", true);
        tv=(TextView)findViewById(R.id.startup_dialog_content);
        agree=(TextView)findViewById(R.id.startup_dialog_agree);
        unagree=(TextView)findViewById(R.id.startup_dialog_unagree);
        str_list = new ArrayList<String>();
        color_list = new ArrayList<Integer>();
        str_list.add("您好，在您使用本应用前，请您认真阅读并了解");
        str_list.add("《用户协议》");
        str_list.add("和");
        str_list.add("《隐私政策》");
        str_list.add("。点击“同意”即表示已阅读并同意全部条款。");
        color_list.add(0xff333333);
        color_list.add(0xff1B91FF);
        color_list.add(0xff333333);
        color_list.add(0xff1B91FF);
        color_list.add(0xff333333);
        Utils.setText(this, tv, str_list, color_list,
                this);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartupPopup.this, LoginActivity.class);
                intent.putExtra("laiyuan",1);
                startActivity(intent);
                setting.edit().putBoolean("FIRST", false).commit();
                finish();
            }
        });
        unagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    @Override
    public void click(int positon) {
        if(positon==1){
            Intent intent = new Intent(this, WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name","agree");
            intent.putExtra("BUNDLE",bundle);
            startActivity(intent);

        }else if(positon==3){
            Intent intent = new Intent(this, WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name","privacy");
            intent.putExtra("BUNDLE",bundle);
            startActivity(intent);
        }
    }

}
