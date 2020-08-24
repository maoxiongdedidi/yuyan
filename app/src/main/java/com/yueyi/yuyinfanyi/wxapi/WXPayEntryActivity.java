package com.yueyi.yuyinfanyi.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import me.goldze.mvvmhabit.utils.ToastUtils;


/**
 * Created by Administrator on 2019/5/22.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, "wxf698c72de7cd8be7");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == 0) {//支付成功
            ToastUtils.showShort("支付成功,可在我的订单中查看详细信息");
            sendOrder();
            finish();
        } else if (resp.errCode == -1) {//支付失败
            ToastUtils.showShort("支付失败，请稍后重试");
            finish();
        } else {//取消
            ToastUtils.showShort("取消支付");
            finish();
        }
    }
    public void sendOrder() {
        Intent intent = new Intent();
        intent.setAction("OrderPaySuccess");
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.sendBroadcast(intent);
    }

}