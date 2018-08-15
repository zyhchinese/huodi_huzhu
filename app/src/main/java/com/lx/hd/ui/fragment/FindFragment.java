package com.lx.hd.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.base.fragment.BaseFragment;
import com.lx.hd.ui.activity.BannerActivity;
import com.lx.hd.widgets.MyWebViewClient;
import com.lx.hd.widgets.OpenFileWebChromeClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {
    private WebView webView;
    // 用于记录出错页面的url 方便重新加载
    private String mFailingUrl = "http://192.168.1.235:8080/maibate//maibatewz/weixin/page/findapp/quanzi/quanzi.html";
    public static final int REQUEST_SELECT_FILE = 100;
    public final static int FILECHOOSER_RESULTCODE = 1;
    public ValueCallback<Uri[]> uploadMessage;
    public ValueCallback<Uri> mUploadMessage;
    public ProgressBar mWebLoadingProgressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        webView = findView(R.id.webView);
        //设置支持JavaScript脚本
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);

        //   webView.setDownloadListener(new MyWebViewDownLoadListener(AppMarketActivity.this));
        //webView.loadUrl("http://app.hiapk.com/essential/");
        //     webView.loadUrl("http://a.3533.com/ruanjian/");

        //设置WebViewClient
        webView.setWebChromeClient(new OpenFileWebChromeClient(getActivity()));
        //设置WebChromeClient
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            //处理javascript中的alert
//            public boolean onJsAlert(WebView view, String url, String message,
//                                     final JsResult result) {
//                //构建一个Builder来显示网页中的对话框
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("提示对话框");
//                builder.setMessage(message);
//                builder.setPositiveButton(android.R.string.ok,
//                        new AlertDialog.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                //点击确定按钮之后,继续执行网页中的操作
//                                result.confirm();
//                            }
//                        });
//                builder.setCancelable(false);
//                builder.create();
//                builder.show();
//                return true;
//            }
//
//            //处理javascript中的confirm
//            public boolean onJsConfirm(WebView view, String url, String message,
//                                       final JsResult result) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("带选择的对话框");
//                builder.setMessage(message);
//                builder.setPositiveButton(android.R.string.ok,
//                        new AlertDialog.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.confirm();
//                            }
//                        });
//                builder.setNegativeButton(android.R.string.cancel,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                result.cancel();
//                            }
//                        });
//                builder.setCancelable(false);
//                builder.create();
//                builder.show();
//                return true;
//            }

        ;
//
//            //设置网页加载的进度条
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//            }
//
//            //设置应用程序的标题title
//            public void onReceivedTitle(WebView view, String title) {
//
//                super.onReceivedTitle(view, title);
//            }
//        });
        webView.addJavascriptInterface(new JsInterface(), "jsinterface");
        webView.addJavascriptInterface(new denglu(), "denglu");
        webView.loadUrl("http://192.168.1.235:8080/maibate//maibatewz/weixin/page/findapp/quanzi/quanzi.html");


    }

    class denglu {
        @JavascriptInterface
        public void login() {
            startActivity(new Intent(getActivity(), LoginActivity.class));

        }

        public void goback() {
            getActivity().finish();
        }
    }

    class JsInterface {
        @JavascriptInterface
        public void errorReload() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    if (mFailingUrl != null) {
                        webView.loadUrl(mFailingUrl);
                    }
                }
            });

        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == REQUEST_SELECT_FILE) {
            if (uploadMessage == null) return;
            uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
            uploadMessage = null;
        }
        class MyWebViewClient1 extends WebViewClient {
            private Context mContext;

            public MyWebViewClient1(Context mContext) {
                this.mContext = mContext;
            }

            // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
            // 而不是新开Android的系统browser中响应该链接，必须覆盖 webview的WebViewClient对象。
            public boolean shouldOverviewUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            public void onPageFinished(WebView view, String url) {

            }

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                mFailingUrl = failingUrl;
                view.loadUrl("file:///android_asset/error.html");
            }


        }
    }
}