package com.lx.hd.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.WalletAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.LogisticsAgreementbean;
import com.lx.hd.bean.walletBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
物流协议
 */
public class LogisticsAgreementActivity extends BaseActivity {
    private WebView webview;
    private TextView tv_title;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setTitleText("账单明细");
        webview = (WebView) findViewById(R.id.webview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("货物托运服务协议");
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置支持JavaScript脚本
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);
        PileApi.instance.searchCarDeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showWaitDialog("努力加载中...");

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            ArrayList<LogisticsAgreementbean> bean = new Gson().fromJson(body, new TypeToken<List<LogisticsAgreementbean>>() {
                            }.getType());
                            webview.loadDataWithBaseURL(Constant.BASE_URL, getNewContent(bean.get(0).getContent()), "text/html", "utf-8", null);
                            hideWaitDialog();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_logistics_agreement;
    }

    public String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }
}
