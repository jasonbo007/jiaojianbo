package com.andbase.jjb.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webview;
    private Button zhibo_btn;
    private String decodeType = "hardware";  //解码类型，默认软件解码 software  硬件解码 hardware
    private String mediaType = "livestream"; //媒体类型，默认网络直播

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("JavascriptInterface")
    private void init() {
        webview = findViewById(R.id.webview);
        zhibo_btn = findViewById(R.id.zhibo_btn);
        zhibo_btn.setOnClickListener(this);
//        webview.loadUrl("http://192.168.1.180:8080/broadcast/broadcast.html");
        webview.addJavascriptInterface(this, "android");//添加js监听 这样html就能调用客户端
        WebSettings webSettings = webview.getSettings();
        webview.setWebChromeClient(new MyWebChromeClient());
        webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setUseWideViewPort(true);
        webview.addJavascriptInterface(new JavaScriptObject(this), "android");
        webview.loadUrl("http://192.168.1.180:8080/rtmp/index.html");
        webview.setWebViewClient(new WebViewClient() { //当点击链接时,希望覆盖而不是打开新窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); //加载新的url return true; //返回true,代表事件已处理,事件流到此终止
                return true;
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        webview.onPause();
        webview.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        webview.resumeTimers();
        webview.onResume();
    }


    @Override
    protected void onDestroy() {
        webview.destroy();
        webview = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhibo_btn:
//                String url = "rtmp://58.200.131.2:1935/livetv/hunantv";
                String url = "rtmp://47.106.188.71:1935/live/efa60tt7";
//                String url = "rtmp://rtmp.open.ys7.com/openlive/f01018a141094b7fa138b9d0b856507b.hd";
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ZhiboActivity.class);
                intent.putExtra("media_type", mediaType);
                intent.putExtra("decode_type", decodeType);
                intent.putExtra("videoPath", url);
                startActivity(intent);
                break;
        }
    }

    private class JavaScriptObject {
        public JavaScriptObject(MainActivity mainActivity) {
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
    }
}
