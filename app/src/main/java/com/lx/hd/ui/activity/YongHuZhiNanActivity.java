package com.lx.hd.ui.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackCommonActivity;

public class YongHuZhiNanActivity extends BackCommonActivity {

    private WebView webView;

    @Override
    protected int getContentView() {
        return R.layout.activity_yong_hu_zhi_nan;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("用户指南");

        webView= (WebView) findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置可以访问文件
        settings.setAllowFileAccess(true);
        //设置支持缩放
        settings.setBuiltInZoomControls(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        webView.loadUrl("http://m.huodiwulian.com/yhzn/yhznhz.html");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.clearCache(true);
        webView.destroy();
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
