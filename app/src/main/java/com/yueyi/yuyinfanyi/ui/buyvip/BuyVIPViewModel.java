package com.yueyi.yuyinfanyi.ui.buyvip;

import android.app.Application;
import android.text.TextUtils;

import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.PayChannelBean;
import com.yueyi.yuyinfanyi.bean.ProductListBean;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.ui.checklanguage.CheckLanguageItemViewModel;
import com.yueyi.yuyinfanyi.ui.checklanguage.CheckLanguageViewModel;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.UserPreference;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import retrofit2.http.PUT;

public class BuyVIPViewModel extends MyBaseViewModel {
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand pay = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(TextUtils.isEmpty(price.get())){
                ToastUtils.showShort("您还没有选中套餐");
                return;
            }
            orderCreate();
        }
    });
    public ObservableField<String> payButton = new ObservableField<>("立即购买");
    public final ItemBinding<BuyVIPItemViewmodel> itemBinding = ItemBinding.of(BR.buyvipitemviewmodel, R.layout.buy_vip_item);
    //给RecyclerView添加items
    public final ObservableList<BuyVIPItemViewmodel> observableList = new ObservableArrayList<>();
    public BuyVIPViewModel(@NonNull Application application) {
        super(application);
        //getData();

    }
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Integer> positon = new SingleLiveEvent<>();
        public SingleLiveEvent<List<PayChannelBean>> pauChannel = new SingleLiveEvent<>();
        public SingleLiveEvent<HashMap<String,String>> orderAndPrice = new SingleLiveEvent<>();
    }
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<String> vipExpireTime = new ObservableField<>();


    public void orderCreate(){
        showDialog("创建订单中");
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountId", UserPreference.getInstance(getApplication().getApplicationContext()).getAccountId()+"");
        map.put("appName", Contents.APPNAME);
        map.put("brand", SystemUtil.getDeviceBrand()+"");
        map.put("channel", Contents.CHANNEL);
        map.put("deviceModel", SystemUtil.getSystemModel()+"");
        map.put("deviceType", "ANDROID");
        map.put("uuid", SystemUtil.getIMEI(getApplication().getApplicationContext())+"");
        map.put("version", Utils.getAppVersionCode(getApplication().getApplicationContext()));
        for (int i = 0; i < observableList.size(); i++) {
            ProductListBean bean = observableList.get(i).getBean();
            if(bean.getAutoType().equals("YES")){
                map.put("productId", bean.getProductId());
            }
        }
        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).create(map)
                ,this,new HttpInterFace<String>(){
                    @Override
                    public void success(BaseBean<String> result) {
                        super.success(result);
                        HashMap<String,String> map = new HashMap<>();
                        map.put("orderId",result.getResult());
                        map.put("price",price.get());
                        payChannel(map);
                    }
                });
    }

    //此方式暂时不用，当需要有其他支付方式的时候就需要更改了
    public void payChannel(HashMap<String,String> orderMap){
        showDialog("正在获取支付渠道");
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountId", UserPreference.getInstance(getApplication().getApplicationContext()).getAccountId()+"");
        map.put("appName", Contents.APPNAME);
        map.put("brand", SystemUtil.getDeviceBrand()+"");
        map.put("channel", Contents.CHANNEL);
        map.put("deviceModel", SystemUtil.getSystemModel()+"");
        map.put("deviceType", "ANDROID");
        map.put("uuid", SystemUtil.getIMEI(getApplication().getApplicationContext())+"");
        map.put("version", Utils.getAppVersionCode(getApplication().getApplicationContext()));

        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).payChannel(map)
        ,this,new HttpInterFace<List<PayChannelBean>>(){
                    @Override
                    public void success(BaseBean<List<PayChannelBean>> result) {
                        super.success(result);
                        dismissDialog();
                       if(result.getResult()!=null&&result.getResult().size()>0){
                            if(result.getResult().size()==1){
                                if(result.getResult().get(0).getType().equals("ALIPAY")){
                                    orderMap.put("qudao","ALIPAY");
                                }else{
                                    orderMap.put("qudao","weChatPay");
                                }
                            }else{
                                orderMap.put("qudao","ALL");
                            }
                           uc.orderAndPrice.setValue(orderMap);
                       }else{
                           ToastUtils.showShort("未获取到支付渠道");
                       }

                    }

                    @Override
                    public void error(Exception e) {
                        super.error(e);
                        dismissDialog();
                    }
                });
    }


}
