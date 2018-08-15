package com.lx.hd.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackCommonActivity;

public class XianZaiActivity extends BackCommonActivity {

    private WebView webView;
    private ImageView img_goback;

    @Override
    protected int getContentView() {
        return R.layout.activity_xian_zai;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        webView= (WebView) findViewById(R.id.webView);
        img_goback= (ImageView) findViewById(R.id.img_goback);
        img_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //设置支持JavaScript脚本
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);
        webView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(".apk")){
                    view.loadUrl(url);
                    return false;
                }else {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(url));//Url 就是你要打开的网址
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(intent);
                    return true;
                }


            }
        });

        webView.loadUrl("http://m.huodiwulian.com/sjd.html");

    }
}
