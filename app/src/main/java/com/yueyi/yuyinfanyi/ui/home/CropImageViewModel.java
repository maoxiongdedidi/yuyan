package com.yueyi.yuyinfanyi.ui.home;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.yueyi.yuyinfanyi.base.MyBaseViewModel;
import com.yueyi.yuyinfanyi.bean.AccessTokenBean;
import com.yueyi.yuyinfanyi.bean.RecognitionResultBean;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.httppager.RetrofitClientBaiDu;
import com.yueyi.yuyinfanyi.ui.login.LoginActivity;
import com.yueyi.yuyinfanyi.utils.Contents;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class CropImageViewModel  extends MyBaseViewModel {
    //转换位置
    public BindingCommand exchange = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String flag=staff_language.get();
            staff_language.set(target_language.get());
            target_language.set(flag);
            Contents.STAFF_LANGUAGE=staff_language.get();
            Contents.TARGET_LANGUAGE=target_language.get();

        }
    });
    public BindingCommand finish = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
         finish();
        }
    });

    public BindingCommand startfanyi = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.click.setValue("");
        }
    });
    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        //左侧画框是否打开或者关闭
        public SingleLiveEvent<String> click = new SingleLiveEvent<>();
        public SingleLiveEvent<List<RecognitionResultBean.WordsResultBean>> getResult = new SingleLiveEvent<>();
    }
    public ObservableField<String> staff_language = new ObservableField<>("中文");
    public ObservableField<String> target_language = new ObservableField<>("英文");
    public ObservableField<String> accesstoken = new ObservableField<>();
    public CropImageViewModel(@NonNull Application application) {
        super(application);
    }





    public void getToken(){
        showDialog("文字识别中");
        HashMap<String,Object> map = new HashMap<>();
        map.put("grant_type","client_credentials");
        map.put("client_id","l6eeyQCSeSZyPckxvgoIN3U6");
        map.put("client_secret","zfAAdcImmM06XZWZ52L289Z4B885OYGr");
        RetrofitClientBaiDu.getInstance().create(ApiService.class).getaccess_token("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=l6eeyQCSeSZyPckxvgoIN3U6&client_secret=zfAAdcImmM06XZWZ52L289Z4B885OYGr",map)
                .compose(RxUtils.bindToLifecycle(CropImageViewModel.this.getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //  showDialog("正在请求...");
                    }
                })
                .subscribe(new Consumer<AccessTokenBean>() {

                    @Override
                    public void accept(AccessTokenBean accessTokenBean) throws Exception {
                        general_basic(accessTokenBean.getAccess_token());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("图片识别失败，请退出重试");
                        dismissDialog();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                });
    }

    public void general_basic(String access_token){
        File zuizhong = new File(getApplication().getApplicationContext().getExternalCacheDir(),
                "zuizhong.jpg");
        RetrofitClientBaiDu.getInstance().create(ApiService.class).general_basic("https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token="+access_token,bitmapToString(BitmapFactory.decodeFile(zuizhong.toString())))
                .compose(RxUtils.bindToLifecycle(CropImageViewModel.this.getLifecycleProvider())) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //  showDialog("正在请求...");
                    }
                })
                .subscribe(new Consumer<RecognitionResultBean>() {

                    @Override
                    public void accept(RecognitionResultBean resultBean) throws Exception {
                        dismissDialog();
                       if(resultBean!=null){
                           if(resultBean.getWords_result()!=null&&resultBean.getWords_result().size()>0){
                               Log.e("====cropviewmodel","数据识别成功");
                               uc.getResult.setValue(resultBean.getWords_result());
                           }else{
                               Log.e("====","数据识别失败");
                               uc.getResult.setValue(null);
                           }
                       }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("图片识别失败，请退出重试");
                        dismissDialog();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissDialog();
                    }
                });
    }


    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

}
