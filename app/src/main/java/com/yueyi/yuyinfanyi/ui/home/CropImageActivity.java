package com.yueyi.yuyinfanyi.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.translate.ocr.OcrCallback;
import com.baidu.translate.ocr.OcrClient;
import com.baidu.translate.ocr.OcrClientFactory;
import com.baidu.translate.ocr.entity.OcrResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.yueyi.yuyinfanyi.MainActivity;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.BR;

import com.yueyi.yuyinfanyi.base.MyBaseActivity;
import com.yueyi.yuyinfanyi.bean.AccessTokenBean;
import com.yueyi.yuyinfanyi.bean.RecognitionResultBean;
import com.yueyi.yuyinfanyi.databinding.ActivityCropImageBinding;
import com.yueyi.yuyinfanyi.httppager.ApiService;
import com.yueyi.yuyinfanyi.httppager.RetrofitClient;
import com.yueyi.yuyinfanyi.ui.buyvip.BuyVIPActivity;
import com.yueyi.yuyinfanyi.ui.checklanguage.CheckLanguageActivity;
import com.yueyi.yuyinfanyi.ui.dialogactivity.SeniorDialogActivity;
import com.yueyi.yuyinfanyi.ui.login.LoginActivity;
import com.yueyi.yuyinfanyi.utils.Contents;
import com.yueyi.yuyinfanyi.utils.HttpInterFace;
import com.yueyi.yuyinfanyi.utils.PaiZhaoCountPreference;
import com.yueyi.yuyinfanyi.utils.ReTrofitClientUtils;
import com.yueyi.yuyinfanyi.utils.UserPreference;
import com.yueyi.yuyinfanyi.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CropImageActivity extends MyBaseActivity<ActivityCropImageBinding, CropImageViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        setbarfull();
        statusBarLightMode();
        return R.layout.activity_crop_image;
    }

    private int rotate = 0;

    @Override
    public int initVariableId() {
        return BR.cropimageviewmodel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.staff_language.set(Contents.STAFF_LANGUAGE);
        viewModel.target_language.set(Contents.TARGET_LANGUAGE);
        binding.activityCropImageStaffLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CropImageActivity.this, CheckLanguageActivity.class);
                intent.putExtra("name", "源语言");
                startActivityForResult(intent, 1);


            }
        });
        binding.activityCropImageTargetLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropImageActivity.this, CheckLanguageActivity.class);
                intent.putExtra("name", "目标语言");
                startActivityForResult(intent, 2);

            }
        });
        binding.activityCropImageXuanzhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.activityCropImageCropimageview.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                if (rotate == 3) {
                    rotate = 0;
                } else {
                    rotate++;
                }

            }
        });
        binding.activityCropImageHuanyuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rotate == 0) {
                    return;
                } else if (rotate == 1) {
                    binding.activityCropImageCropimageview.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
                } else if (rotate == 2) {
                    binding.activityCropImageCropimageview.rotateImage(CropImageView.RotateDegrees.ROTATE_M180D);
                } else if (rotate == 3) {
                    binding.activityCropImageCropimageview.rotateImage(CropImageView.RotateDegrees.ROTATE_M270D);
                }
                rotate = 0;
            }
        });
    }

    private Uri photoUri;

    @Override
    public void initData() {
        super.initData();
        File cameraPhoto = new File(getExternalCacheDir(),
                "yuyin.jpg");
        photoUri = FileProvider.getUriForFile(
                CropImageActivity.this,
                getPackageName() + ".fileprovider",
                cameraPhoto);
        binding.activityCropImageCropimageview.load(photoUri).execute(new LoadCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
        binding.activityCropImageCropimageview.setInitialFrameScale(0.7f);
        binding.activityCropImageCropimageview.setCropMode(CropImageView.CropMode.FREE);
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
            }
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.click.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(UserPreference.getInstance(getApplication()).getType().equals("NORMAL")){
                    if(PaiZhaoCountPreference.getInstance(getApplication()).checkCount()){
                        binding.activityCropImageCropimageview.crop(photoUri)
                                .execute(new CropCallback() {
                                    @Override
                                    public void onSuccess(Bitmap cropped) {
                                        //自己写保存方法，使用框架的总是报错
                                        if (Utils.saveBitmapAsFile(CropImageActivity.this,cropped)) {
                                            viewModel.getToken();
                                        } else {
                                            ToastUtils.showShort("文件解析失败，请重试");
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }
                                });
                    }else{
                        startActivity(SeniorDialogActivity.class);
                    }
                }else{
                    showDialog("图片剪切中");
                    binding.activityCropImageCropimageview.crop(photoUri)
                            .execute(new CropCallback() {
                                @Override
                                public void onSuccess(Bitmap cropped) {
                                    //自己写保存方法，使用框架的总是报错
                                    showDialog("图片上传中");
                                    if (Utils.saveBitmapAsFile(CropImageActivity.this,cropped)) {
                                        viewModel.getToken();
                                    } else {
                                        dismissDialog();
                                        ToastUtils.showShort("文件解析失败，请重试");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                }
                            });
                }

            }
        });

        viewModel.uc.getResult.observe(this, new Observer<List<RecognitionResultBean.WordsResultBean>>() {
            @Override
            public void onChanged(List<RecognitionResultBean.WordsResultBean> wordsResultBeans) {
                if (wordsResultBeans != null) {
                    Contents.wordsResultBeans = wordsResultBeans;
                    setResult(RESULT_OK, new Intent(CropImageActivity.this, HomeActivity.class));
                    finish();

                } else {
                    ToastUtils.showShort("未能成功识别信息，请重新剪切或返回拍照");
                }

            }
        });
    }


}
