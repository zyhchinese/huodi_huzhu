package com.lx.hd.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.PrimaryNews;
import com.lx.hd.widgets.MyWebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

import cn.sharesdk.onekeyshare.share.OthreShare;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class NewsDetialActivity extends BackCommonActivity {
    private WebView webView;
    private TextView tvTitle;
    private ImageView img_fenxiang;

    private PrimaryNews primaryNew;
    public static final String KEY_BUNDLE = "KEY_BUNDLE_IN_ORDERD_ETIAL";

    public static void show(Context context, PrimaryNews primaryNews) {
        if (primaryNews == null) return;
        Intent intent = new Intent(context, NewsDetialActivity.class);
        intent.putExtra(KEY_BUNDLE, primaryNews);
        context.startActivity(intent);
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        primaryNew = (PrimaryNews) bundle.getSerializable(KEY_BUNDLE);
        if (primaryNew == null || (primaryNew.getId() <= 0)) {
            showToast("新闻不存在");
            return false;
        }
        return super.initBundle(bundle);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_banner;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("新闻详情");
        webView=(WebView)findViewById(R.id.webView);
        tvTitle=(TextView)findViewById(R.id.tv_title);
        img_fenxiang= (ImageView) findViewById(R.id.img_fenxiang);
        img_fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OthreShare.showShare(NewsDetialActivity.this,primaryNew.getTitle(),"", "http://www.huodiwulian.com/logo_hz.png", "http://www.huodiwulian.com/hdsywz/page/xinwen/xinwenxiangqingm.html?id="+primaryNew.getId()+"&type=1");
            }
        });
        tvTitle.setText(primaryNew.getTitle());
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
        webView.setWebViewClient(new MyWebViewClient(NewsDetialActivity.this));
        //设置WebChromeClient
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            //处理javascript中的alert
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result)
            {
                //构建一个Builder来显示网页中的对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsDetialActivity.this);
                builder.setTitle("提示对话框");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //点击确定按钮之后,继续执行网页中的操作
                                result.confirm();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }

            //处理javascript中的confirm
            public boolean onJsConfirm(WebView view, String url, String message,
                                       final JsResult result)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsDetialActivity.this);
                builder.setTitle("带选择的对话框");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            };
            //处理javascript中的prompt
            //message为网页中对话框的提示内
            //defaultValue在没有输入时，默认显示的内容
//			public boolean onJsPrompt(WebView view, String url, String message,
//									  String defaultValue, final JsPromptResult result) {
//				//自定义一个带输入的对话框由TextView和EditText构成
//				final LayoutInflater factory = LayoutInflater.from(AppMarketActivity.this);
//				final View dialogview = factory.inflate(R.layout.prom_dialog, null);
//				//设置TextView对应网页中的提示信息
//				((TextView) dialogview.findViewById(R.id.TextView_PROM)).setText(message);
//				//设置EditText对应网页中的输入框
//				((EditText) dialogview.findViewById(R.id.EditText_PROM)).setText(defaultValue);
//				AlertDialog.Builder builder = new AlertDialog.Builder(AppMarketActivity.this);
//				builder.setTitle("带输入的对话框");
//				builder.setView(dialogview);
//				builder.setPositiveButton(android.R.string.ok,
//						new AlertDialog.OnClickListener() {
//							public void onClick(DialogInterface dialog, int which) {
//								//点击确定之后，取得输入的值，传给网页处理
//								String value = ((EditText) dialogview.findViewById(R.id.EditText_PROM)).getText().toString();
//								result.confirm(value);
//							}
//						});
//				builder.setNegativeButton(android.R.string.cancel,
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog, int which) {
//								result.cancel();
//							}
//						});
//				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//					public void onCancel(DialogInterface dialog) {
//						result.cancel();
//					}
//				});
//				builder.show();
//				return true;
//			};
            //设置网页加载的进度条
            public void onProgressChanged(WebView view, int newProgress)
            {
                getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
                super.onProgressChanged(view, newProgress);
            }
            //设置应用程序的标题title
            public void onReceivedTitle(WebView view, String title)
            {
                setTitle(title);
                super.onReceivedTitle(view, title);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();

        HashMap<String,String> map=new HashMap<>();
        map.put("id",primaryNew.getId()+"");
        map.put("appstatus","0");
        Gson gson=new Gson();
        String params=gson.toJson(map);
        PileApi.instance.getNewsDetial(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String body=responseBody.string();
                            String note="";
                            try {
                                JSONArray jsonArray=new JSONArray(body);
                                for (int i=0;i<jsonArray.length();i++){
                                    note=jsonArray.getJSONObject(i).getString("note");
                                }
                                webView.loadDataWithBaseURL(Constant.BASE_URL,getNewContent(note),"text/html","utf-8",null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) &&
                webView.canGoBack())
        {
            //返回前一个页面
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }
}
