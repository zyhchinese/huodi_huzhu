package com.lx.hd.ui.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.widgets.OpenFileWebChromeClient;

import im.delight.android.webview.AdvancedWebView;

public class EtcActivity extends BackCommonActivity {

    private WebView webView;


    @Override
    protected int getContentView() {
        return R.layout.activity_etc;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("");
        webView= (WebView) findViewById(R.id.webView);
        //设置webview支持js
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//禁止缓存
        webSettings.setDomStorageEnabled(true);//开启DOM储存API
        //屏蔽掉长按事件 因为webview长按时将会调用系统的复制控件:
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //要加载的地址
        webView.loadUrl("http://124.128.225.21:28003/WebRoot/sglzfp.jsp");
        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub

                //      super.onPageFinished(view, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

        });

    }

}
