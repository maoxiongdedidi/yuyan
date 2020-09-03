package com.yueyi.yuyinfanyi.ui.dialogactivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yueyi.yuyinfanyi.utils.Utils;
import com.yueyi.yuyinfanyi.R;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author ding
 */
public class ContactCustomerDialogActivity extends Activity{
    private ImageView close;
    private LinearLayout contantcustomer_wechat ,contantcustomer_qq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactcustomer);
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
        initClick();
    }

    private void initClick() {
        close.setOnClickListener(view->{
            finish();
        });
        contantcustomer_wechat.setOnClickListener(v -> {
            Utils.copy(this,"rhinox01");
            try {
                String url="weixin://";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }catch (Exception e){
                e.printStackTrace();
                ToastUtils.showShort("请安装微信");
            }

        });
        contantcustomer_qq.setOnClickListener(v -> {
            try {
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=1404556846";//uin是发送过去的qq号码
                getApplication().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showShort("请安装QQ");
            }
        });
    }

    private void initView() {
        close = findViewById(R.id.contactcustomer_delete);
        contantcustomer_wechat = findViewById(R.id.contantcustomer_wechat);
        contantcustomer_qq = findViewById(R.id.contantcustomer_qq);
    }
}
