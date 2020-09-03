package com.yueyi.yuyinfanyi.ui.help;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.CancelAccountBean;
import com.yueyi.yuyinfanyi.bean.LoginBean;
import com.yueyi.yuyinfanyi.bean.PayChannelBean;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.ui.buyvip.BuyVIPViewModel;
import com.yueyi.yuyinfanyi.ui.dialogactivity.ContactCustomerDialogActivity;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.UserPreference;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HelpViewModel extends MyBaseViewModel {
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    //界面问题
    public BindingCommand jiemian = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("title", "界面问题");
            startActivity(HelpCommitActivity.class, bundle);
        }
    });
    //功能问题
    public BindingCommand gongneng = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("title", "功能问题");
            startActivity(HelpCommitActivity.class, bundle);
        }
    });
    //内容问题
    public BindingCommand neirong = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("title", "内容问题");
            startActivity(HelpCommitActivity.class, bundle);
        }
    });
    //其他问题
    public BindingCommand qita = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("title", "其他问题");
            startActivity(HelpCommitActivity.class, bundle);
        }
    });
    //产品问题
    public BindingCommand chanpin = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("title", "产品建议");
            startActivity(HelpCommitActivity.class, bundle);
        }
    });
    public BindingCommand openqq = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          /*  try {
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=1404556846";//uin是发送过去的qq号码
                getApplication().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showShort("请安装QQ");
            }*/
          startActivity(ContactCustomerDialogActivity.class);
        }
    });
    //账号注销
    public BindingCommand zhanghao = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.openPopup.setValue("");
        }
    });
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<String> openPopup = new SingleLiveEvent<>();
    }

    public HelpViewModel(@NonNull Application application) {
        super(application);
    }


}
