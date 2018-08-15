package com.lx.hd.widgets;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MyWebViewClient extends WebViewClient {
    private Context mContext;
    public MyWebViewClient(Context mContext){
        this.mContext=mContext;
    }
    // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
    // 而不是新开Android的系统browser中响应该链接，必须覆盖 webview的WebViewClient对象。
    public boolean shouldOverviewUrlLoading(WebView view, String url) {

        view.loadUrl(url);
        return true;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {

    //    showProgressDialog();
    }

    public void onPageFinished(WebView view, String url) {

       // closeProgressDialog();
    }

    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        closeProgressDialog();
   //     view.loadUrl("file:///android_asset/error.html");
    }
    private ProgressDialog mDialog;
    private void showProgressDialog(){
        if(mDialog==null){
            mDialog = new ProgressDialog(mContext);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
            mDialog.setMessage("正在加载 ，请等待...");
            mDialog.setIndeterminate(false);//设置进度条是否为不明确
            mDialog.setCancelable(true);//设置进度条是否可以按退回键取消
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    mDialog=null;
                }
            });
            mDialog.show();// bug    android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@3c64fedc is not valid; is your activity running?

        }
    }
    private void closeProgressDialog(){
        if(mDialog!=null){
            mDialog.dismiss();
            mDialog=null;
        }
    }

}