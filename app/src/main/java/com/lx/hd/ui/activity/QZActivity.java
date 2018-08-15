package com.lx.hd.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lx.hd.R;
import com.lx.hd.account.AccountHelper;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.base.Constant;
import com.lx.hd.cookie.CookieJarImpl;
import com.lx.hd.cookie.SPCookieStore;
import com.lx.hd.utils.SizeUtil;
import com.lx.hd.widgets.ItemLongClickedPopWindow;
import com.lx.hd.widgets.OpenFileWebChromeClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;

import im.delight.android.webview.AdvancedWebView;
import im.delight.android.webview.BuildConfig;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class QZActivity extends Activity implements AdvancedWebView.Listener {
    private im.delight.android.webview.AdvancedWebView webView;
    // 用于记录出错页面的url 方便重新加载
    private String mFailingUrl = "http://192.168.1.235:8080/maibate//maibatewz/weixin/page/faxianapp/quanzi/_quanzi.html";
    public static final int REQUEST_SELECT_FILE = 100;
    public final static int FILECHOOSER_RESULTCODE = 1;
    public ValueCallback<Uri[]> uploadMessage;
    public ValueCallback<Uri> mUploadMessage;
    public ProgressBar mWebLoadingProgressBar;
    // 长按查看图片
    private ItemLongClickedPopWindow itemLongClickedPopWindow;
    // 手指触发屏幕的坐标
    private int downX, downY;
    // 需要保存图片的路径
    private String saveImgUrl = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qz);

        Intent intent = getIntent();
        mFailingUrl = intent.getStringExtra("url");
        webView = (AdvancedWebView) findViewById(R.id.webview);
        //设置支持JavaScript脚本
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);
        webView.setWebChromeClient(new OpenFileWebChromeClient(this));
        webView.addJavascriptInterface(new JsInterface(), "jsinterface");
        webView.addJavascriptInterface(new denglu(), "denglu");
        webView.addJavascriptInterface(new goback(), "goback");
        webView.setListener(this, this);
        webView.setGeolocationEnabled(false);
        webView.setMixedContentAllowed(true);
        webView.setCookiesEnabled(true);
        webView.setThirdPartyCookiesEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                //   Toast.makeText(QZActivity.this, "Finished loading", Toast.LENGTH_SHORT).show();
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //     Toast.makeText(QZActivity.this, title, Toast.LENGTH_SHORT).show();
            }

        });
        webView.addHttpHeader("X-Requested-With", "");


        webView.setOnTouchListener(listener);

        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                if (null == result)
                    return false;
                int type = result.getType();
                if (type == WebView.HitTestResult.UNKNOWN_TYPE)
                    return false;
                if (type == WebView.HitTestResult.EDIT_TEXT_TYPE) {

                }

                itemLongClickedPopWindow = new ItemLongClickedPopWindow(QZActivity.this,
                        ItemLongClickedPopWindow.IMAGE_VIEW_POPUPWINDOW,
                        SizeUtil.dp2px(QZActivity.this, 120), SizeUtil.dp2px(QZActivity.this, 90));

                switch (type) {
                    case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE: // TODO
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                        break;
                    case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                        // 获取图片的路径
                        saveImgUrl = result.getExtra();
                        //通过GestureDetector获取按下的位置，来定位PopWindow显示的位置
                        itemLongClickedPopWindow.showAtLocation(v, Gravity.TOP | Gravity.LEFT, downX, downY + 10);
                        break;
                    default:
                        break;
                }

                itemLongClickedPopWindow.getView(R.id.item_longclicked_viewImage)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                itemLongClickedPopWindow.dismiss();
//                                Intent intent = new Intent(QZActivity.this, ShowImgActivity.class);
//                                intent.putExtra("info", saveImgUrl);
//                                startActivity(intent);
                                itemLongClickedPopWindow.dismiss();
                            }
                        });
                itemLongClickedPopWindow.getView(R.id.item_longclicked_saveImage)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (Build.VERSION.SDK_INT>=23) {

                                    if (ContextCompat.checkSelfPermission(QZActivity.this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(QZActivity.this, new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 1);
                                    } else {
                                        new SaveImage().execute(); // Android 4.0以后要使用线程来访问网络
                                    }
                                }else {
                                    new SaveImage().execute(); // Android 4.0以后要使用线程来访问网络
                                }
                                itemLongClickedPopWindow.dismiss();

                            }
                        });
                return true;
            }
        });
        List<Cookie> cookis = new SPCookieStore(this).getCookie(HttpUrl.get(URI.create(Constant.BASE_URL + "mbtwz/mallLogin?action=checkMallLogin")));
        if (cookis.size() > 0) {
            cookis.get(0);
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format(cookis.get(0).name() + "=%s", cookis.get(0).value()));
            //webview在使用cookie前会前判断保存cookie的domain和当前要请求的domain是否相同，相同才会发送cookie
            sbCookie.append(String.format(";domain=%s", cookis.get(0).domain())); //注意，是getHost()，不是getAuthority(),
            sbCookie.append(String.format(";path=%s", "/"));
            String cookieValue = sbCookie.toString();
            cookieManager.setCookie(Constant.BASE_URL + "mbtwz/mallLogin?action=checkMallLogin", cookieValue);
            CookieSyncManager.getInstance().sync();
        }
        webView.loadUrl(mFailingUrl);

    }

    View.OnTouchListener listener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            downX = (int) arg1.getX();
            downY = (int) arg1.getY();
            return false;
        }
    };

    /***
     * 功能：用线程保存图片
     *
     * @author wangyp
     */
    private class SaveImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }
                int idx = saveImgUrl.lastIndexOf(".");
                String ext = saveImgUrl.substring(idx);
                file = new File(sdcard + "/Download/" + new Date().getTime() + ext);
                InputStream inputStream = null;
                URL url = new URL(saveImgUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(20000);
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                }
                byte[] buffer = new byte[4096];
                int len = 0;
                FileOutputStream outStream = new FileOutputStream(file);
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                result = "图片已保存至：" + file.getAbsolutePath();

                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                final Uri localUri = Uri.fromFile(file);
                localIntent.setData(localUri);
                sendBroadcast(localIntent);
            } catch (Exception e) {
                result = "保存失败！" + e.getLocalizedMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
                Toast.makeText(QZActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

    class denglu {
        @JavascriptInterface
        public void login() {
            startActivity(new Intent(QZActivity.this, LoginActivity.class));

        }
    }

    class goback {
        @JavascriptInterface
        public void tuichu() {
            //finish();
            System.exit(0);
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
    protected void onResume() {
        super.onResume();
        webView.onResume();
        List<Cookie> cookis = new SPCookieStore(this).getCookie(HttpUrl.get(URI.create(Constant.BASE_URL + "mbtwz/mallLogin?action=checkMallLogin")));
        if (cookis.size() > 0) {
            cookis.get(0);
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format(cookis.get(0).name() + "=%s", cookis.get(0).value()));
            //webview在使用cookie前会前判断保存cookie的domain和当前要请求的domain是否相同，相同才会发送cookie
            sbCookie.append(String.format(";domain=%s", cookis.get(0).domain())); //注意，是getHost()，不是getAuthority(),
            sbCookie.append(String.format(";path=%s", "/"));
            String cookieValue = sbCookie.toString();
            cookieManager.setCookie(Constant.BASE_URL + "mbtwz/mallLogin?action=checkMallLogin", cookieValue);
            CookieSyncManager.getInstance().sync();
        }
        //   webView.loadUrl("http://192.168.1.235:8080/maibate//maibatewz/weixin/page/faxianapp/quanzi/quanziandroid.html");
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        webView.onPause();
        // ...
        super.onPause();
    }




    @Override
    protected void onDestroy() {
        releaseAllWebViewCallback();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.clearCache(true);
        webView.onDestroy();
        // ...
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        webView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!webView.onBackPressed()) {
            return;
        }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPageFinished(String url) {
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        //  Toast.makeText(QZActivity.this, "onPageError(errorCode = " + errorCode + ",  description = " + description + ",  failingUrl = " + failingUrl + ")", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        // Toast.makeText(QZActivity.this, "onDownloadRequested(url = " + url + ",  suggestedFilename = " + suggestedFilename + ",  mimeType = " + mimeType + ",  contentLength = " + contentLength + ",  contentDisposition = " + contentDisposition + ",  userAgent = " + userAgent + ")", Toast.LENGTH_LONG).show();

		/*if (AdvancedWebView.handleDownload(this, url, suggestedFilename)) {
            // download successfully handled
		}
		else {
			// download couldn't be handled because user has disabled download manager app on the device
		}*/
    }

    @Override
    public void onExternalPageRequest(String url) {
        // Toast.makeText(QZActivity.this, "onExternalPageRequest(url = " + url + ")", Toast.LENGTH_SHORT).show();
    }

    public void releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    new SaveImage().execute(); // Android 4.0以后要使用线程来访问网络
                }else {
                    Toast.makeText(this, "储存卡权限未授权", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
