package com.yueyi.yuyinfanyi.ui.help;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.CancelAccountBean;
import com.yueyi.yuyinfanyi.bean.LoginBean;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.UserPreference;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.HashMap;

public class CancellationPopup extends PopupWindow {
    private Context context;
    private View mMenuView;
    private TextView finish,commit;
    private ImageView delete;
    private String applyTime,day,finishTime,mobile;
    public CancellationPopup(Context context) {
        super(context);
        this.context=context;  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.cancellation_popup, null);
        commit= (TextView)mMenuView.findViewById(R.id.cancellation_commit);
        delete= (ImageView)mMenuView.findViewById(R.id.cancellation_delete);
        finish= (TextView)mMenuView.findViewById(R.id.cancellation_finish);
        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(false);
        ColorDrawable dw = new ColorDrawable(0x50000000);
        this.setBackgroundDrawable(dw);
        initView();

    }

    private void initView() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAccount();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public void cancelAccount(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountId", UserPreference.getInstance(context).getAccountId()+"");
        map.put("appName", Contents.APPNAME);
        map.put("brand", SystemUtil.getDeviceBrand()+"");
        map.put("channel", Contents.CHANNEL);
        map.put("deviceModel", SystemUtil.getSystemModel()+"");
        map.put("deviceType", "ANDROID");
        map.put("uuid", SystemUtil.getIMEI(context)+"");
        map.put("version", Utils.getAppVersionCode(context));
        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).cancelAccount(map)
                ,context,new HttpInterFace<CancelAccountBean>(){
                    @Override
                    public void success(BaseBean<CancelAccountBean> result) {
                        super.success(result);
                        applyTime=result.getResult().getApplyTime();
                        day=result.getResult().getDay()+"";
                        finishTime=result.getResult().getFinishTime();
                        mobile=result.getResult().getMobile();
                        Intent intent = new Intent(context,CancellationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("applyTime",applyTime);
                        bundle.putString("day",day);
                        bundle.putString("finishTime",finishTime);
                        bundle.putString("mobile",mobile);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        dismiss();
                    }
                });
    }


}
