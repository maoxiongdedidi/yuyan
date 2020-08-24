package com.yueyi.yuyinfanyi.ui.buyvip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import me.goldze.mvvmhabit.utils.ToastUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.PayChannelBean;
import com.yueyi.yuyinfanyi.bean.ProductListBean;
import com.yueyi.yuyinfanyi.databinding.ActivityBuyVipBinding;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.UserPreference;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuyVIPActivity extends MyBaseActivity<ActivityBuyVipBinding,BuyVIPViewModel> {
    List<ProductListBean> listBeans;
    BuyVIPItemAdapter buyVIPItemAdapter;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        return R.layout.activity_buy_vip;
    }

    @Override
    public int initVariableId() {
        return BR.buyvipviewmodel;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            String vipExpireTime = extras.getString("vipExpireTime","");
            if(vipExpireTime!=""&& !TextUtils.isEmpty(vipExpireTime)&&!vipExpireTime.equals("立即开通")){
                viewModel.vipExpireTime.set("您已是VIP用户，会员将于"+vipExpireTime+"到期。");
                viewModel.payButton.set("立即续费");
            }else{
                viewModel.payButton.set("立即购买");
            }

        }

        // viewModel.getData();
        getData();
//        ProductListBean productListBean = new ProductListBean();
//        productListBean.setAutoType("YES");
//        productListBean.setDays(10);
//        productListBean.setDiscountPrice(100);
//        productListBean.setPrice(100);
//        productListBean.setProductId("1");
//        productListBean.setProductName("悠久");
//
//        viewModel.observableList.add(new BuyVIPItemViewmodel(viewModel,productListBean));
//        ProductListBean productListBean1 = new ProductListBean();
//        productListBean1.setAutoType("NO");
//        productListBean1.setDays(10);
//        productListBean1.setDiscountPrice(10);
//        productListBean1.setPrice(9);
//        productListBean1.setProductId("1");
//        productListBean1.setProductName("悠久");
//
//        viewModel.observableList.add(new BuyVIPItemViewmodel(viewModel,productListBean1));
//     //   viewModel.observableList.add(new BuyVIPItemViewmodel(viewModel,productListBean1));
//        listBeans= new ArrayList<>();
//        listBeans.add(productListBean);
//        listBeans.add(productListBean1);
        buyVIPItemAdapter= new BuyVIPItemAdapter(listBeans);
        binding.setAdapter(buyVIPItemAdapter);





    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    public void getData(){
        showDialog("获取信息中");
        HashMap<String, Object> map = new HashMap<>();
      //  map.put("accountId", UserPreference.getInstance(getApplication().getApplicationContext()).getAccountId()+"");
        map.put("appName", Contents.APPNAME);
        map.put("brand", SystemUtil.getDeviceBrand()+"");
        map.put("channel", Contents.CHANNEL);
        map.put("deviceModel", SystemUtil.getSystemModel()+"");
        map.put("deviceType", "ANDROID");
        map.put("uuid", SystemUtil.getIMEI(getApplication().getApplicationContext())+"");
        map.put("version", Utils.getAppVersionCode(getApplication().getApplicationContext()));
        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).productList(map)
                ,this,new HttpInterFace<List<ProductListBean>>(){
                    @Override
                    public void success(BaseBean<List<ProductListBean>> result) {
                        super.success(result);
                        dismissDialog();
                        if(result.getResult()!=null&&result.getResult().size()>0){
                            listBeans=result.getResult();
                            for (int i = 0; i < result.getResult().size(); i++) {
                                ProductListBean productListBean = result.getResult().get(i);
                                if(productListBean.getProductName().equals("永久VIP")){
                                    productListBean.setAutoType("YES");
                                }else{
                                    productListBean.setAutoType("NO");
                                }
                                BuyVIPItemViewmodel buyVIPItemViewmodel = new BuyVIPItemViewmodel(viewModel,productListBean,i);
                                viewModel.observableList.add(buyVIPItemViewmodel);
                            }

                        }
                    }

                    @Override
                    public void error(Exception e) {
                        super.error(e);
                        dismissDialog();
                    }
                });

    }
    @Override
    public void initViewObservable() {

        viewModel.uc.positon.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                for (int i = 0; i < listBeans.size(); i++) {
                    ProductListBean bean = viewModel.observableList.get(i).getBean();
                    if(integer==i){
                        bean.setAutoType("YES");
                    }else{
                        bean.setAutoType("NO");
                    }

                    viewModel.observableList.get(i).setBean(bean);
                }
                buyVIPItemAdapter.notifyDataSetChanged();
            }
        });
        viewModel.uc.orderAndPrice.observe(this, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> map) {
                PayPopup payPopup = new PayPopup(BuyVIPActivity.this, map, new PayPopup.PayInterfaceCallBack() {
                    @Override
                    public void onSuccess() {
                     startActivity(PaySuccessPopup.class);
                    }

                    @Override
                    public void onError(String message) {
                        ToastUtils.showShort(message==null?"支付失败，请稍后重试":message);
                    }
                });
                payPopup.showAtLocation(binding.activityBuyVipPayText, Gravity.BOTTOM, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
            }
        });


    }






}
