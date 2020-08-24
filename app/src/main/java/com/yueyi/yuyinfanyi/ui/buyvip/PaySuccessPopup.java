package com.yueyi.yuyinfanyi.ui.buyvip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.ui.home.HomeActivity;
import com.yueyi.yuyinfanyi.utils.Utils;

public class PaySuccessPopup extends Activity {
    private TextView commit;
    private ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_success_popup);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        lp.width = Utils.getScreenWidth(this);
        lp.height = Utils.getScreenHeight(this);
        dialogWindow.setAttributes(lp);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        commit = (TextView) findViewById(R.id.pay_success_popup_commit_button);
        close = (ImageView) findViewById(R.id.pay_success_popup_finish_image);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PaySuccessPopup.this, HomeActivity.class));
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
