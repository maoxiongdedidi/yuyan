package com.yueyi.yuyinfanyi.ui.help;

import android.app.Application;
import android.text.TextUtils;

import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.UserPreference;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HelpCommitViewModel  extends MyBaseViewModel {
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    public BindingCommand commit = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            commitQuestion();
        }
    });
    public ObservableField<String> result = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //左侧画框是否打开或者关闭
        public SingleLiveEvent<Boolean> isClick = new SingleLiveEvent<>();
    }
    public HelpCommitViewModel(@NonNull Application application) {
        super(application);
        uc.isClick.setValue(false);
    }

    public void commitQuestion(){
        if(TextUtils.isEmpty(result.get())){
            ToastUtils.showShort("不能为空～");
            return;
        }
        if(result.get().length()<4){
            ToastUtils.showShort("至少输入4个字符～");
            return;
        }
        showDialog("提交中，请稍后");
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountId", UserPreference.getInstance(getApplication().getApplicationContext()).getAccountId()+"");
        map.put("appName", Contents.APPNAME);
        map.put("brand", SystemUtil.getDeviceBrand()+"");
        map.put("channel", Contents.CHANNEL);
        map.put("deviceModel", SystemUtil.getSystemModel()+"");
        map.put("deviceType", "ANDROID");
        map.put("uuid", SystemUtil.getIMEI(getApplication().getApplicationContext())+"");
        map.put("version", Utils.getAppVersionCode(getApplication().getApplicationContext()));
        map.put("content", result.get());
        if(title.get().equals("功能问题：")){
            map.put("type","FUNCTION");
        }else if(title.get().equals("界面问题：")){
            map.put("type","UI");
        }else if(title.get().equals("内容问题：")){
            map.put("type","CONTENT");
        }else if(title.get().equals("其他问题：")){
            map.put("type","OTHER");
        }else if(title.get().equals("产品建议：")){
            map.put("type","PROD_SUGGEST");
        }
        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).addFeedback(map)
        ,this,new HttpInterFace<BaseBean>(){
                    @Override
                    public void success(BaseBean<BaseBean> result) {
                        super.success(result);
                        dismissDialog();
                        ToastUtils.showShort("感谢您的反馈～");
                        finish();
                    }

                    @Override
                    public void error(Exception e) {
                        super.error(e);
                        dismissDialog();
                    }
                });
    }



}
