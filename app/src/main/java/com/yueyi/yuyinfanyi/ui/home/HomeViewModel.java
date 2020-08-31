package com.yueyi.yuyinfanyi.ui.home;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.bean.APPBean;
import com.yueyi.yuyinfanyi.bean.BaseBean;
import com.yueyi.yuyinfanyi.bean.LoginBean;
import com.yueyi.yuyinfanyi.bean.TranslateBean;
import com.yueyi.yuyinfanyi.bean.UserBean;
import com.yueyi.yuyinfanyi.database.DataBaseUtils;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.httppager.RetrofitClientBaiDu;
import com.yueyi.yuyinfanyi.ui.about.AboutActivity;
import com.yueyi.yuyinfanyi.ui.buyvip.BuyVIPActivity;
import com.yueyi.yuyinfanyi.ui.checklanguage.CheckLanguageActivity;
import com.yueyi.yuyinfanyi.ui.checklanguage.CheckLanguageViewModel;
import com.yueyi.yuyinfanyi.ui.dialogactivity.SeniorDialogActivity;
import com.yueyi.yuyinfanyi.ui.extension.ExtensionActivity;
import com.yueyi.yuyinfanyi.ui.help.HelpActivity;
import com.yueyi.yuyinfanyi.ui.login.LoginActivity;
import com.yueyi.yuyinfanyi.ui.settingtextsize.TranslateSettingActivity;
import com.yueyi.yuyinfanyi.ui.translationrecord.TranslationRecordActivity;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.PaiZhaoCountPreference;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.SystemUtil;
import com.yueyi.yuyinfanyi.utils.TTSUtils;
import com.yueyi.yuyinfanyi.utils.UserPreference;
import com.yueyi.yuyinfanyi.utils.Utils;
import com.yueyi.yuyinfanyi.utils.YuYinCountPreference;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.drawerlayout.widget.DrawerLayout;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HomeViewModel extends MyBaseViewModel {
    private final String SHARE_APP_TAG = "firstOpen";
    public boolean isLogin=false;

    //设置
    public BindingCommand setting = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            Log.e("====","dianji le "+uc.isOpen.getValue());
            if(uc.isOpen.getValue()){
                uc.isOpen.setValue(false);
            }else{
                uc.isOpen.setValue(true);
            }


        }
    });
    //翻译记录
    public BindingCommand translation_record = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            startActivity(TranslationRecordActivity.class);
        }
    });
    //清空输入
    public BindingCommand clean_et = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            staff_language_et.set("");
        }
    });
    //复制
    public BindingCommand copy = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            if (TextUtils.isEmpty(staff_language_et.get())) {
                return;
            }
            Utils.copy(getApplication().getApplicationContext(), staff_language_et.get());
        }
    });
    //复制
    public BindingCommand copy2 = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            if (TextUtils.isEmpty(target_language_text.get())) {
                return;
            }
            Utils.copy(getApplication().getApplicationContext(), target_language_text.get());
        }
    });
    //翻译
    public BindingCommand translate = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            if (TextUtils.isEmpty(staff_language_et.get())) {
                ToastUtils.showShort("输入框不能为空");
                return;
            }
            String type = UserPreference.getInstance(getApplication()).getType();
            if(type.equals("NORMAL")){
                if(staff_language_et.get().length()>300){
                    ToastUtils.showShort("最多支持300字数的翻译");
                    startActivity(SeniorDialogActivity.class);
                }else{
                    fanyi(staff_language_et.get());
                }
            }else{
                if(staff_language_et.get().getBytes().length>6000){
                    ToastUtils.showShort("最多支持6000字节的翻译,请设置多次翻译");
                }else{
                    fanyi(staff_language_et.get());
                }
            }






        }
    });
    //扩展
    public BindingCommand broad = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            if(TextUtils.isEmpty(target_language_text.get())){
               return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("result",target_language_text.get());
            startActivity(ExtensionActivity.class,bundle);
        }
    });
    //转换位置
    public BindingCommand exchange = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            String flag=staff_language.get();
            staff_language.set(target_language.get());
            target_language.set(flag);
            Contents.STAFF_LANGUAGE=staff_language.get();
            Contents.TARGET_LANGUAGE=target_language.get();


        }
    });


    //朗读
    public BindingCommand happen = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            if (target_language.get().equals("中文") || target_language.get().equals("英文")) {
                TTSUtils.getInstance().speak(target_language_text.get());
            } else {
                ToastUtils.showShort("暂时只支持中文和英文播放");
            }

        }
    });

    //拍照
    public BindingCommand photograph = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            uc.openCamera.setValue("");


        }
    });
    //语音
    public BindingCommand voice = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            if(UserPreference.getInstance(getApplication()).getType().equals("NORMAL")){
                if(YuYinCountPreference.getInstance(getApplication()).checkCount()){
                    uc.openYuYin.setValue("");
                }else{
                    startActivity(SeniorDialogActivity.class);
                }
            }else{
                uc.openYuYin.setValue("");
            }
        }
    });
    //翻译设置
    public BindingCommand translatesetting = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.closeDrawerLayout.setValue("");
            drawerlayout_click.set("translatesetting");


          //  uc.isOpen.setValue(false);
        }
    });
    //购买会员
    public BindingCommand buyvip = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String type = UserPreference.getInstance(getApplication().getApplicationContext()).getType();
            if(!TextUtils.isEmpty(type)&&type.equals("FOREVER_VIP")){
                return;
            }
            uc.closeDrawerLayout.setValue("");
            drawerlayout_click.set("buyvip");


         //   uc.isOpen.setValue(false);
        }
    });
    //帮助反馈
    public BindingCommand help = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.closeDrawerLayout.setValue("");
            drawerlayout_click.set("help");

          //  uc.isOpen.setValue(false);
        }
    });
    //关于我们
    public BindingCommand about = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.closeDrawerLayout.setValue("");
            drawerlayout_click.set("about");




          //  uc.isOpen.setValue(false);
        }
    });
    //头像点击
    public BindingCommand tuichu = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            drawerlayout_click.set("tuichu");

            uc.closeDrawerLayout.setValue("");

        }
    });
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //左侧画框是否打开或者关闭
        public SingleLiveEvent<Boolean> isOpen = new SingleLiveEvent<Boolean>();
        public SingleLiveEvent<APPBean> appinfo = new SingleLiveEvent<>();
        public SingleLiveEvent<String> openYuYin = new SingleLiveEvent<>();
        public SingleLiveEvent<String> openCamera = new SingleLiveEvent<>();
        public SingleLiveEvent<String> openTuiChu = new SingleLiveEvent<>();
        public SingleLiveEvent<String> closeDrawerLayout = new SingleLiveEvent<>();

        public UIChangeObservable() {
        }
    }
    public ObservableField<String> drawerlayout_click = new ObservableField<>("");


    public ObservableField<Integer> huangguanxianshi = new ObservableField<Integer>(View.GONE);

    public ObservableField<String> staff_language = new ObservableField<>("中文");
    public ObservableField<String> target_language = new ObservableField<>("英文");
    public ObservableField<String> staff_language_et = new ObservableField<>("");
    public ObservableField<String> target_language_text = new ObservableField<>("翻译结果");
    public ObservableField<Integer> target_language_text_color = new ObservableField<>(0xffe8e8e8);
    public ObservableField<String> phone = new ObservableField<>("");
    public ObservableField<String> user_icon = new ObservableField<>("");
    public ObservableField<String> vipExpireTime  = new ObservableField<>("");
    public HomeViewModel(@NonNull Application application) {
        super(application);
        uc.isOpen.setValue(false);
    }

    public void fanyi(final String s) {
        showDialog("翻译中，请稍后");
        String tranlateurl = Utils.getTranlateurl(s, staff_language.get(), target_language.get(),false);
        RetrofitClientBaiDu.getInstance().create(ApiService.class).translate(tranlateurl)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //  showDialog("正在请求...");
                    }
                })
                .subscribe(new Consumer<TranslateBean>() {
                    @Override
                    public void accept(TranslateBean translateBean) throws Exception {
                        dismissDialog();
                        if (translateBean != null && translateBean.getTrans_result() != null) {
                            List<TranslateBean.TransResultBean> trans_result = translateBean.getTrans_result();
                            if (trans_result.size() > 0) {
                                target_language_text.set(trans_result.get(0).getDst());
                                target_language_text_color.set(0xff000000);
                                DataBaseUtils.getInstance(HomeViewModel.this.getApplication()).insert(staff_language_et.get(),target_language_text.get(),staff_language.get(),target_language.get());
                                boolean autoAlound = UserPreference.getInstance(getApplication().getApplicationContext()).getAutoAlound();
                                if(autoAlound&&(target_language.get().equals("英文")||target_language.get().equals("中文"))){
                                    TTSUtils.getInstance().speak(target_language_text.get());
                                }
                            }
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ToastUtils.showShort("翻译失败，未能识别语言");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

    }
    public void getvsToken(){
        getAcctoutInfoAfter();
        getAppinfo();
    }

    public void getAccountInfo() {
        if(!isLogin&&TextUtils.isEmpty(UserPreference.getInstance(getApplication().getApplicationContext()).getAccountId())&&TextUtils.isEmpty(UserPreference.getInstance(getApplication().getApplicationContext()).gettoken())){
            getvsToken();
        }else{
            getAcctoutInfoAfter();
            getAppinfo();
        }

    }
    public void getAcctoutInfoAfter(){
        Log.e("==========","调用车速");
        if (isLogin) {
            Log.e("==========","调用车速2");
            HashMap<String, Object> map = new HashMap<>();
            map.put("accountId", UserPreference.getInstance(getApplication().getApplicationContext()).getAccountId()+"");
            map.put("appName", Contents.APPNAME);
            map.put("brand", SystemUtil.getDeviceBrand()+"");
            map.put("channel", Contents.CHANNEL);
            map.put("deviceModel", SystemUtil.getSystemModel()+"");
            map.put("deviceType", "ANDROID");
            map.put("uuid", SystemUtil.getIMEI(getApplication().getApplicationContext())+"");
            map.put("version", Utils.getAppVersionCode(getApplication().getApplicationContext()));
            ReTrofitClientUtils.get(RetrofitClient.getInstance().getProxy(ApiService.class).getAccountInfo(map),this,
                    new HttpInterFace<UserBean>(){
                        @Override
                        public void success(BaseBean<UserBean> result) {
                            super.success(result);
                            UserPreference.getInstance(getApplication().getApplicationContext()).setmobile(result.getResult().getMobile());
                            UserPreference.getInstance(getApplication().getApplicationContext()).settype(result.getResult().getType());
                            UserPreference.getInstance(getApplication().getApplicationContext()).setnickname(result.getResult().getNickname());
                            UserPreference.getInstance(getApplication().getApplicationContext()).setapplyCancelTime(result.getResult().getApplyCancelTime());
                            phone.set(result.getResult().getMobile());
                            user_icon.set(result.getResult().getHeadImg());
                            if(result.getResult().getType().equals("NORMAL")){
                                vipExpireTime.set("立即开通");
                                huangguanxianshi.set(View.GONE);
                            }else if(result.getResult().getType().equals("VIP")){
                                huangguanxianshi.set(View.VISIBLE);
                                vipExpireTime.set(result.getResult().getVipExpireTime());
                            }else {
                                huangguanxianshi.set(View.VISIBLE);
                                vipExpireTime.set("永久");
                            }

                        }
                    });
        }
    }




    public void getAppinfo(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("appName", Contents.APPNAME);
        map.put("brand", SystemUtil.getDeviceBrand()+"");
        map.put("channel", Contents.CHANNEL);
        map.put("deviceModel", SystemUtil.getSystemModel()+"");
        map.put("deviceType", "ANDROID");
        map.put("uuid", SystemUtil.getIMEI(getApplication().getApplicationContext())+"");
        map.put("version", Utils.getAppVersionCode(getApplication().getApplicationContext()));
        ReTrofitClientUtils.get(RetrofitClient.getInstance().create(ApiService.class).appinfo(map)
        ,this,new HttpInterFace<APPBean>(){
                    @Override
                    public void success(BaseBean<APPBean> result) {
                        super.success(result);
                        uc.appinfo.setValue(result.getResult());
                    }
                });


    }

  public   DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
            drawerlayout_click.set(null);
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
          if(drawerlayout_click.get()!=null){
              if (!isLogin) {
                  startActivity(LoginActivity.class);
                  return;
              }
              if(drawerlayout_click.get().equals("tuichu")){
                  uc.openTuiChu.setValue("");
              }else if(drawerlayout_click.get().equals("about")){
                  startActivity(AboutActivity.class);
              }else if(drawerlayout_click.get().equals("help")){
                  startActivity(HelpActivity.class);
              }else if(drawerlayout_click.get().equals("buyvip")){

                  Bundle bundle = new Bundle();
                  bundle.putString("vipExpireTime",vipExpireTime.get());
                  startActivity(BuyVIPActivity.class,bundle);
              }else if(drawerlayout_click.get().equals("translatesetting")){
                  startActivity(TranslateSettingActivity.class);
              }
              drawerlayout_click.set(null);
          }
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };




}
