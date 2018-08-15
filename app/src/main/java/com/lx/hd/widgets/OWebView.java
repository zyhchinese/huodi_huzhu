package com.lx.hd.widgets;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lx.hd.AppConfig;
import com.lx.hd.AppContext;
import com.lx.hd.interf.OnWebViewImageListener;
import com.lx.hd.utils.StringUtils;
import com.lx.hd.utils.TDevice;
import com.lx.hd.utils.TLog;
import com.lx.hd.utils.UIHelper;
import com.lx.hd.utils.gson.AppOperator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OWebView extends WebView {
    public OWebView(Context context) {
        super(context);
        init();
    }

    public OWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public OWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init();
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void init() {
        setClickable(false);
        setFocusable(false);

        setHorizontalScrollBarEnabled(false);

        WebSettings settings = getSettings();
        settings.setDefaultFontSize(14);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            addJavascriptInterface(new OnWebViewImageListener() {
                @Override
                @JavascriptInterface
                public void showImagePreview(String bigImageUrl) {
                    if (bigImageUrl != null && !StringUtils.isEmpty(bigImageUrl)) {
                       //-- ImageGalleryActivity.show(getContext(), bigImageUrl);
                    }
                }
            }, "mWebViewImageListener");
        }
    }

    public void loadDetailDataAsync(final String content) {
        Context context = getContext();
        if (context != null && context instanceof Activity) {
            final Activity activity = (Activity) context;
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    final String body = setupWebContent(content, true, true, "");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //loadData(body, "text/html; charset=UTF-8", null);
                            loadDataWithBaseURL("", body, "text/html", "UTF-8", "");
                        }
                    });
                }
            });
        } else {
            TLog.e(OWebView.class.getName(), "loadDetailDataAsync error, the Context isn't ok");
        }
    }

    public void loadDetailDataAsync(final String content, Runnable finishCallback) {
        this.setWebViewClient(new OWebClient(finishCallback));
        Context context = getContext();
        if (context != null && context instanceof Activity) {
            final Activity activity = (Activity) context;
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    final String body = setupWebContent(content, true, true, "");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //loadData(body, "text/html; charset=UTF-8", null);
                            loadDataWithBaseURL("", body, "text/html", "UTF-8", "");
                        }
                    });
                }
            });
        } else {
            TLog.e(OWebView.class.getName(), "loadDetailDataAsync error, the Context isn't ok");
        }
    }


    public void loadTweetDataAsync(final String content, Runnable finishCallback) {
        this.setWebViewClient(new OWebClient(finishCallback));
        Context context = getContext();
        if (context != null && context instanceof Activity) {
            final Activity activity = (Activity) context;
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    final String body = setupWebContent(content, "");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //loadData(body, "text/html; charset=UTF-8", null);
                            loadDataWithBaseURL("", body, "text/html", "UTF-8", "");
                        }
                    });
                }
            });
        } else {
            TLog.e(OWebView.class.getName(), "loadDetailDataAsync error, the Context isn't ok");
        }
    }

    @Override
    public void destroy() {
        setWebViewClient(null);

        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(false);

        removeJavascriptInterface("mWebViewImageListener");
        removeAllViewsInLayout();

        removeAllViews();
        //clearCache(true);

        super.destroy();
    }

    private static String setupWebContent(String content, String style) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(content.trim()))
            return "";
        if (AppContext.get(AppConfig.KEY_LOAD_IMAGE, true)
                || TDevice.isWifiOpen()) {
            Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*[\"\']([^\"\']*)[\"\'](\\s*data-url\\s*=\\s*[\"\']([^\"\']*)[\"\'])*");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String snippet = String.format(
                        "<img style=\"vertical-align: bottom;\" src=\"%s\" onClick=\"javascript:mWebViewImageListener.showImagePreview('%s')\"",
                        matcher.group(1),
                        matcher.group(3) == null ? matcher.group(1) : matcher.group(3));
                content = content.replace(matcher.group(0), snippet);
            }
        } else {
            // 过滤掉 img标签
            content = content.replaceAll("<\\s*img\\s+([^>]*)\\s*>", "");
        }
        return String.format(
                "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common_new.css\">"
                        + "</head>"
                        + "<body>"
                        + "<div class='contentstyle' id='article_id' style='%s'>"
                        + "%s"
                        + "</div>"
                        + "</body>"
                        + "</html>"
                , style == null ? "" : style, content);
    }

    private static String setupWebContent(String content, boolean isShowHighlight, boolean isShowImagePreview, String css) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(content.trim()))
            return "";

        // 读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
        if (AppContext.get(AppConfig.KEY_LOAD_IMAGE, true)
                || TDevice.isWifiOpen()) {
            content = content.replaceAll("<([u|o])l.*>", "<$1l style=\"padding-left:20px\">");
            // 过滤掉 img标签的width,height属性
            content = content.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
            content = content.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");

            // 添加点击图片放大支持
            if (isShowImagePreview) {
                // TODO 用一个正则就搞定??
                content = content.replaceAll("<img[^>]+src=\"([^\"\'\\s]+)\"[^>]*>",
                        "<img src=\"$1\" onClick=\"javascript:mWebViewImageListener.showImagePreview('$1')\"/>");
                content = content.replaceAll(
                        "<a\\s+[^<>]*href=[\"\']([^\"\']+)[\"\'][^<>]*>\\s*<img\\s+src=\"([^\"\']+)\"[^<>]*/>\\s*</a>",
                        "<a href=\"$1\"><img src=\"$2\"/></a>");
            }
        } else {
            // 过滤掉 img标签
            content = content.replaceAll("<\\s*img\\s+([^>]*)\\s*/>", "");
        }

        // 过滤table的内部属性
        content = content.replaceAll("(<table[^>]*?)\\s+border\\s*=\\s*\\S+", "$1");
        content = content.replaceAll("(<table[^>]*?)\\s+cellspacing\\s*=\\s*\\S+", "$1");
        content = content.replaceAll("(<table[^>]*?)\\s+cellpadding\\s*=\\s*\\S+", "$1");


        return String.format(
                "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + (isShowHighlight ? "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/shCore.css\">" : "")
                        + (isShowHighlight ? "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/shThemeDefault.css\">" : "")
                        + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common_detail.css\">"
                        + "%s"
                        + "</head>"
                        + "<body>"
                        + "<div class='body-content'>"
                        + "%s"
                        + "</div>"
                        + (isShowHighlight ? "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>" : "")
                        + (isShowHighlight ? "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>" : "")
                        + (isShowHighlight ? "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>" : "")
                        + "</body>"
                        + "</html>"
                , (css == null ? "" : css), content);
    }

    private static class OWebClient extends WebViewClient implements Runnable {
        private Runnable mFinishCallback;
        private boolean mDone = false;

        OWebClient(Runnable finishCallback) {
            super();
            mFinishCallback = finishCallback;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mDone = false;
            // 当webview加载2秒后强制回馈完成
            view.postDelayed(this, 2800);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            run();
        }

        @Override
        public synchronized void run() {
            if (!mDone) {
                mDone = true;
                if (mFinishCallback != null) mFinishCallback.run();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            UIHelper.showUrlRedirect(view.getContext(), url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // TODO
            handler.cancel();
        }
    }
}
