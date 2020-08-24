package com.yueyi.yuyinfanyi.ui.web;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyi.yuyinfanyi.R;
import com.yueyi.yuyinfanyi.utils.StatusBarUtil;

public class WebViewActivity extends AppCompatActivity {

    private String name="";
    private WebView webView;
    private TextView title;
    private ImageView finish;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        StatusBarUtil.setStatusBarFullTransparent(this);
        StatusBarUtil.statusBarLightMode(this);
        webView=(WebView)findViewById(R.id.activity_webview);
        finish=(ImageView)findViewById(R.id.activity_web_finish);
        title=(TextView)findViewById(R.id.activity_web_title);

        Intent intent = getIntent();
        Bundle build = intent.getBundleExtra("BUNDLE");

        name = build.getString("name");
        if(name.equals("agree")){
            webView.loadUrl("file:///android_asset/network.html");
            title.setText("用户协议");
        }else if (name.equals("privacy")){
            webView.loadUrl("file:///android_asset/privacy.html");
            title.setText("隐私政策");
        }
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptObject(this), "Android");
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.evaluateJavascript("javascript:setAppName('语言翻译')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
        });


    }
    class JavaScriptObject{
        Context mContxt;

        public JavaScriptObject(Context mContxt) {
            this.mContxt = mContxt;
        }
        @JavascriptInterface
        public String setAppName(String val){
            return "语言翻译";
        }
    }
}
