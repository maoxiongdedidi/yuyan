package com.yueyi.yuyinfanyi.ui.buyvip;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.PayResult;
import com.yueyi.yuyinfanyi.bean.PayResultBean;
import com.yueyi.yuyinfanyi.bean.WechatBean;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.UserPreference;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class PayPopup extends PopupWindow {
    private final String qudao;
    private Context context;
    private View mMenuView;
    private TextView price, paybutton;
    private ImageView close;
    private LinearLayout wechatLayout, alipyLayout;
    private CheckBox wechatCB, alipyCB;
    private String payChannel, orderId;
    private PayInterfaceCallBack payInterfaceCallBack;

    public PayPopup(Context context, HashMap<String, String> map,PayInterfaceCallBack payInterfaceCallBack) {
        super(context);
        this.context = context;
        this.orderId = map.get("orderId");
        qudao = map.get("qudao");
        this.payInterfaceCallBack=payInterfaceCallBack;
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pay_popup, null);
        price = (TextView) mMenuView.findViewById(R.id.pay_popup_price);
        paybutton = (TextView) mMenuView.findViewById(R.id.pay_popup_new_pay);
        close = (ImageView) mMenuView.findViewById(R.id.pay_popup_close);
        wechatLayout = (LinearLayout) mMenuView.findViewById(R.id.pay_popup_wechat_layout);
        alipyLayout = (LinearLayout) mMenuView.findViewById(R.id.pay_popup_alipy_layout);
        wechatCB = (CheckBox) mMenuView.findViewById(R.id.pay_popup_wechat_cb);
        alipyCB = (CheckBox) mMenuView.findViewById(R.id.pay_popup_alipy_cb);
        if(qudao.equals("ALIPAY")){
            wechatLayout.setVisibility(View.GONE);
            alipyLayout.setVisibility(View.VISIBLE);
        }else if(qudao.equals("weChatPay")){
            wechatLayout.setVisibility(View.VISIBLE);
            alipyLayout.setVisibility(View.GONE);
        }else{
            wechatLayout.setVisibility(View.VISIBLE);
            alipyLayout.setVisibility(View.VISIBLE);
        }



        price.setText("支付金额：" + map.get("price"));
        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setFocusable(false);
        ColorDrawable dw = new ColorDrawable(0x50000000);
        this.setBackgroundDrawable(dw);
        initView();
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver,new IntentFilter("OrderPaySuccess"));
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
            }
        });
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            queryPayOrder();
        }
    };
    private void initView() {
        payChannel = "WECHAT_PAY";
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        wechatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payChannel = "WECHAT_PAY";
                wechatCB.setChecked(true);
                alipyCB.setChecked(false);
            }
        });
        alipyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payChannel = "ALIPAY";
                wechatCB.setChecked(false);
                alipyCB.setChecked(true);
            }
        });
        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });
    }

    public void pay() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountId", UserPreference.getInstance(context).getAccountId());
        map.put("appName", Contents.APPNAME);
        map.put("orderId", orderId);
        map.put("payChannel", payChannel);
        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).submitOrder(map), context
                , new HttpInterFace<PayResultBean>() {
                    @Override
                    public void success(BaseBean<PayResultBean> result) {
                        super.success(result);
                        if(payChannel.equals("WECHAT_PAY")){
                            Gson gson = new Gson();
                            WechatBean wechatBean = gson.fromJson(result.getResult().getPayInfo(), WechatBean.class);
                            wechatPay(wechatBean);
                        }else{
                            alipy(result.getResult().getPayInfo());
                        }

                    }
                });
    }


    public void wechatPay(WechatBean wechatBean) {
        IWXAPI  api = WXAPIFactory.createWXAPI(context, wechatBean.getAppid(), true);
        api.registerApp(wechatBean.getAppid());
        PayReq request = new PayReq(); //调起微信APP的对象
        //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
        request.appId = wechatBean.getAppid();
        request.partnerId = wechatBean.getPartnerid();
        request.prepayId = wechatBean.getPrepayid();
        request.packageValue = wechatBean.getPackageValue();
        request.nonceStr = wechatBean.getNoncestr();
        request.timeStamp = wechatBean.getTimestamp();
        request.sign = wechatBean.getSign();
        api.sendReq(request);//发送调起微信的请求
    }

    public void alipy(String info) {
        final String orderInfo = info;   // 订单信息
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) context);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            if (TextUtils.equals(resultStatus, "9000")) {

               queryPayOrder();
            } else {
                Log.e("====fanhui",resultStatus+resultInfo+"");
               payInterfaceCallBack.onError(TextUtils.isEmpty(resultInfo)?"支付失败，请稍后重试":resultInfo);
                dismiss();
            }
        };
    };


    public void queryPayOrder(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountId", UserPreference.getInstance(context).getAccountId()+"");
        map.put("appName", Contents.APPNAME);
        map.put("brand", SystemUtil.getDeviceBrand()+"");
        map.put("channel", Contents.CHANNEL);
        map.put("deviceModel", SystemUtil.getSystemModel()+"");
        map.put("deviceType", "ANDROID");
        map.put("uuid", SystemUtil.getIMEI(context)+"");
        map.put("version", Utils.getAppVersionCode(context));
        map.put("orderId", orderId);
        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).queryPayOrder(map)
        ,context,new HttpInterFace<PayResultBean>(){
                    @Override
                    public void success(BaseBean<PayResultBean> result) {
                        super.success(result);
                        dismiss();
                        payInterfaceCallBack.onSuccess();
                    }

                    @Override
                    public void error(Exception e) {
                        super.error(e);
                        payInterfaceCallBack.onError(e.getMessage());
                    }
                });
    }




    public interface PayInterfaceCallBack{
        void onSuccess();
        void onError(String message);
    }
}
