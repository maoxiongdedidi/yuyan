package com.yueyi.yuyinfanyi.ui.dialogactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.ui.buyvip.BuyVIPActivity;
import com.yueyi.yuyinfanyi.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SeniorDialogActivity extends Activity {
    private TextView commitTV;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_dialog);
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
        commitTV=(TextView)findViewById(R.id.activitu_senior_dialog_commit_button);
        imageView = (ImageView)findViewById(R.id.activitu_senior_dialog_finish_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        commitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("vipExpireTime","");
                Intent intent = new Intent(SeniorDialogActivity.this,BuyVIPActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
