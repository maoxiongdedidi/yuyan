package com.yueyi.yuyinfanyi.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.ToastUtils;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yueyi.yuyinfanyi.BR;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.bean.APPBean;
import com.yueyi.yuyinfanyi.bean.CacheBaseBean;
import com.yueyi.yuyinfanyi.bean.RecognitionResultBean;
import com.yueyi.yuyinfanyi.database.DataBaseUtils;
import com.yueyi.yuyinfanyi.databinding.ActivityHomeBinding;
import com.yueyi.yuyinfanyi.ui.about.AboutActivity;
import com.yueyi.yuyinfanyi.ui.checklanguage.CheckLanguageActivity;
import com.yueyi.yuyinfanyi.ui.help.CancellationActivity;
import com.yueyi.yuyinfanyi.ui.help.CancellationPopup;
import com.yueyi.yuyinfanyi.ui.help.HelpActivity;
import com.yueyi.yuyinfanyi.ui.login.LoginActivity;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.UpdataPreference;
import com.yueyi.yuyinfanyi.utils.UserPreference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HomeActivity extends MyBaseActivity<ActivityHomeBinding, HomeViewModel> {
    private final String SHARE_APP_TAG = "firstOpen";
    private SharedPreferences setting;
    private boolean isLogin;
    private int flag=0;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        setting = getSharedPreferences(SHARE_APP_TAG, 0);
        return R.layout.activity_home;
    }

    @Override
    public int initVariableId() {
        return BR.homeviewmodel;
    }


    @Override
    protected void onResume() {
        super.onResume();
        CacheBaseBean cacheBaseBean = Contents.cacheBaseBean;
        if(cacheBaseBean!=null){
            String staff_language_et = cacheBaseBean.getCacheDataBean().getYuanwen();
            String target_language_text =cacheBaseBean.getCacheDataBean().getFanyi();
            viewModel.staff_language_et.set(staff_language_et);
            viewModel.target_language_text.set(target_language_text);
            cacheBaseBean=null;
        }

     //   binding.activityHomeDrawerlayout.closeDrawer(Gravity.LEFT);

        isLogin = setting.getBoolean("isLogin", false);
        viewModel.isLogin = isLogin;
        viewModel.staff_language.set(Contents.STAFF_LANGUAGE);
        viewModel.target_language.set(Contents.TARGET_LANGUAGE);
        binding.activityHomeResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, Contents.TRANSLATERESULTSIZE);
        binding.activityHomeStaffLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    Intent intent = new Intent(HomeActivity.this, CheckLanguageActivity.class);
                    intent.putExtra("name", "源语言");
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(LoginActivity.class);
                }

            }
        });
        binding.activityHomeTargetLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    Intent intent = new Intent(HomeActivity.this, CheckLanguageActivity.class);
                    intent.putExtra("name", "目标语言");
                    startActivityForResult(intent, 2);
                } else {
                    startActivity(LoginActivity.class);
                }

            }
        });
        if(flag==1){
            if (!isLogin && TextUtils.isEmpty(UserPreference.getInstance(getApplication().getApplicationContext()).getAccountId()) && TextUtils.isEmpty(UserPreference.getInstance(getApplication().getApplicationContext()).gettoken())) {
            } else {
                viewModel.getAcctoutInfoAfter();
            }
        }else{

        }
    }
    private Handler handler = new Handler();
    @Override
    public void initData() {
        super.initData();

        isLogin = setting.getBoolean("isLogin", false);
        viewModel.isLogin = isLogin;
        viewModel.getAccountInfo();
        binding.activityHomeResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        binding.activityHomeDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    @Override
    protected void onPause() {
        super.onPause();
        flag=1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String string = data.getExtras().getString("name");
                viewModel.staff_language.set(string);
                Contents.STAFF_LANGUAGE = string;
            } else if (requestCode == 2) {
                String string = data.getExtras().getString("name");
                viewModel.target_language.set(string);
                Contents.TARGET_LANGUAGE = string;
            } else if (requestCode == 3) {
                startPhotoZoom();
            } else if (requestCode == 4) {
                if (Contents.wordsResultBeans == null) {
                    return;
                } else {
                    List<RecognitionResultBean.WordsResultBean> wordsResultBeans = Contents.wordsResultBeans;
                    for (int i = 0; i < wordsResultBeans.size(); i++) {
                        if (i == 0) {
                            viewModel.staff_language_et.set(wordsResultBeans.get(0).getWords());
                        } else {
                            String s = viewModel.staff_language_et.get();
                            viewModel.staff_language_et.set(s + wordsResultBeans.get(i).getWords());
                        }
                    }
                    viewModel.staff_language.set(Contents.STAFF_LANGUAGE);
                    viewModel.target_language.set(Contents.TARGET_LANGUAGE);
                    viewModel.fanyi(viewModel.staff_language_et.get());
                }
            }else if(requestCode==6){


            }
        }
    }

    public void startPhotoZoom() {
        startActivityForResult(new Intent(this, CropImageActivity.class), 4);
    }



    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.isOpen.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.activityHomeDrawerlayout.openDrawer(Gravity.LEFT);
                } else {
                    binding.activityHomeDrawerlayout.closeDrawers();
                }
            }
        });
        viewModel.uc.appinfo.observe(this, new Observer<APPBean>() {
            @Override
            public void onChanged(APPBean appBean) {
                if (appBean.getVersionInfoVo() != null && !TextUtils.isEmpty(appBean.getVersionInfoVo().getLinkUrl())) {
                    Gson gson = new Gson();
                    String s = gson.toJson(appBean);

                    if (!appBean.getVersionInfoVo().isForceUp()) {
                        if (UpdataPreference.getInstance(HomeActivity.this).isOpenPopup()) {
                            Bundle bundle = new Bundle();
                            bundle.putString("data",s);
                            startActivity(UpdatePopup.class,bundle);
                        }
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("data",s);
                        startActivity(UpdatePopup.class,bundle);
                    }
                }
            }
        });
        viewModel.uc.openYuYin.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String s1 = viewModel.staff_language.get();
                String s2 = viewModel.target_language.get();
                String form = "";
                if (s1.equals("中文")) {
                    form = "zh";
                } else if (s1.equals("英文")) {
                    form = "en";
                } else if (s1.equals("日语")) {
                    form = "jp";
                } else if (s1.equals("韩语")) {
                    form = "kor";
                } else if (s1.equals("葡萄牙语")) {
                    form = "pt";
                } else if (s1.equals("法语")) {
                    form = "fra";
                } else if (s1.equals("德语")) {
                    form = "de";
                } else if (s1.equals("俄语")) {
                    form = "ru";
                }
                String to = "";
                if (s2.equals("中文")) {
                    to = "zh";
                } else if (s2.equals("英文")) {
                    to = "en";
                } else if (s2.equals("日语")) {
                    to = "jp";
                } else if (s2.equals("韩语")) {
                    to = "kor";
                } else if (s2.equals("葡萄牙语")) {
                    to = "pt";
                } else if (s2.equals("法语")) {
                    to = "fra";
                } else if (s2.equals("德语")) {
                    to = "de";
                } else if (s2.equals("俄语")) {
                    to = "ru";
                }
                RxPermissions rxPermissions = new RxPermissions(HomeActivity.this);
                String finalForm = form;
                String finalTo = to;
                rxPermissions.request(Manifest.permission.RECORD_AUDIO)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if(aBoolean){
                                    VoicePop voicePop = new VoicePop(HomeActivity.this, finalForm, finalTo, new VoicePop.VoicePopCallBack() {
                                        @Override
                                        public void success(String asrResult, String ransResult) {
                                            viewModel.staff_language_et.set(asrResult);
                                            viewModel.target_language_text.set(ransResult);
                                            DataBaseUtils.getInstance(HomeActivity.this.getApplication()).insert(viewModel.staff_language_et.get(), viewModel.target_language_text.get(),viewModel.staff_language.get(),viewModel.target_language.get());
                                            viewModel.target_language_text_color.set(0xff000000);
                                        }
                                    });
                                    voicePop.showAtLocation(binding.activityHomeTargetLanguage, Gravity.CENTER, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

                                }else{
                                    ToastUtils.showShort("缺少语音权限，无法使用语言翻译");
                                }
                            }
                        });


            }
        });
        viewModel.uc.openCamera.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                RxPermissions rxPermissions = new RxPermissions(HomeActivity.this);
                if (rxPermissions.isGranted(Manifest.permission.CAMERA)) {
                    if (rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        File cameraPhoto = new File(getExternalCacheDir(),
                                "yuyin.jpg");
                        try {
                            if (cameraPhoto.exists()) {
                                cameraPhoto.delete();
                            }
                            cameraPhoto.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri photoUri = FileProvider.getUriForFile(
                                HomeActivity.this,
                                getPackageName() + ".fileprovider",
                                cameraPhoto);
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(takePhotoIntent, 3);

                    } else {
                        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (!aBoolean) {
                                    ToastUtils.showShort("您无法使用拍照翻译功能");
                                }
                            }
                        });
                    }
                } else {
                    rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (!aBoolean) {
                                ToastUtils.showShort("您将无法使用拍照功能");
                            }
                        }
                    });
                }


            }
        });
        viewModel.uc.openTuiChu.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                startActivity(ExitPopup.class);
            }
        });
        viewModel.uc.closeDrawerLayout.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
               // binding.activityHomeDrawerlayout.closeDrawer(GravityCompat.START);
                binding.activityHomeDrawerlayout.closeDrawers();
            }
        });
    }


    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

                if ((System.currentTimeMillis() - exitTime) > 2000) {// 不是2s内，连续按下
                    //OwnUtils.showMessage("再按一次退出程序");

                    ToastUtils.showShort("再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                    return false;
                } else {// 连续按下俩次，关闭定位退出
                    //  MyApplication.getInstance().exit();
                    System.exit(0);
                    return true;
                }

        }
        return false;
    }


}
