package com.yueyi.yuyinfanyi.ui.home;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.translate.asr.OnRecognizeListener;
import com.baidu.translate.asr.TransAsrClient;
import com.baidu.translate.asr.TransAsrConfig;
import com.baidu.translate.asr.data.Language;
import com.baidu.translate.asr.data.RecognitionResult;
import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.utils.UserPreference;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class VoicePop extends PopupWindow {
    private Context context;
    private View mMenuView;
    private TextView commit;
    private String from;
    private String to;
    private VoicePopCallBack voicePopCallBack;






    public VoicePop(Context context,String from,String to,VoicePopCallBack voicePopCallBack) {
        super(context);
        this.context=context;
        this.from=from;
        this.to=to;
        this.voicePopCallBack=voicePopCallBack;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.voice_pop, null);
        commit=(TextView)mMenuView.findViewById(R.id.voice_popup_commit);

        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(false);
        ColorDrawable dw = new ColorDrawable(0x50000000);
        this.setBackgroundDrawable(dw);
        initView();

    }

    private void initView() {


        boolean autoAlound = UserPreference.getInstance(context).getAutoAlound();
        TransAsrConfig config = new TransAsrConfig("20190515000297961", "Z0P8nBPSg6bJ4a_EXxNl");
        config.setPartialCallbackEnabled(false);
        if(autoAlound&&(from.equals("zh")||to.equals("en"))){
            config.setAutoPlayTts(true);
        }else{
            config.setAutoPlayTts(false);
        }
        TransAsrClient client = new TransAsrClient(context, config);
        client.setRecognizeListener(new OnRecognizeListener() {
            @Override
            public void onRecognized(int resultType, @NonNull RecognitionResult result) {
                if (resultType == OnRecognizeListener.TYPE_PARTIAL_RESULT) { // 中间结果


                } else if (resultType == OnRecognizeListener.TYPE_FINAL_RESULT) { // 最终结果
                    if (result.getError() == 0) { // 表示正常，有识别结果
                        // 语音识别结果
//                        Log.d(TAG, "最终识别结果：" + result.getAsrResult());
//                        Log.d(TAG, "翻译结果：" + result.getTransResult());
                        voicePopCallBack.success(result.getAsrResult(),result.getTransResult());
                        dismiss();
                    } else { // 翻译出错

                        Log.d("=====", "语言翻译出错 错误码：" + result.getError() + " 错误信息：" + result.getErrorMsg());
                        if(result.getError()==7001){
                            ToastUtils.showShort("未能匹配到识别结果");
                        }else if(result.getError()==9001){
                            ToastUtils.showShort("没有录音权限");
                        }else if(result.getError()==3001){
                            ToastUtils.showShort("录音机打开失败，请检查权限或稍后重试");
                        }else if(result.getError()==3002){
                            ToastUtils.showShort("录音机参数错误，请检查权限或稍后重试");
                        }else if(result.getError()==3003){
                            ToastUtils.showShort("录音机不可用，请检查权限或稍后重试");
                        }else if(result.getError()==3006){
                            ToastUtils.showShort("录音机读取失败，请检查权限或稍后重试");
                        }else if(result.getError()==6001){
                            ToastUtils.showShort("暂时只支持60S以内的语音识别");
                        }else if(result.getError()==8001){
                            ToastUtils.showShort("识别忙，请稍后重试");
                        }else if(result.getError()==1001){
                            ToastUtils.showShort("网络超时");
                        }else if(result.getError()==2000){
                            ToastUtils.showShort("网络超时");
                        }else{
                            ToastUtils.showShort("语言翻译出错，请稍后重试");
                        }

                        dismiss();
                    }
                }
            }

        });




        client.startRecognize(from, to);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.stopRecognize();
            }
        });
    }
   public interface VoicePopCallBack{
        void success(String asrResult,String ransResult);
   }

}
