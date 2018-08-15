package com.lx.hd.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.fragment.BaseFragment;
import com.lx.hd.ui.activity.ActiveListActivity;
import com.lx.hd.ui.activity.AddressActivity;
import com.lx.hd.ui.activity.CarHomeActivity;
import com.lx.hd.ui.activity.ConfirmOrderActivity;
import com.lx.hd.ui.activity.QZActivity;
import com.lx.hd.utils.DialogHelper;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class QZFragment extends BaseFragment {
    private RelativeLayout active, quanzi, wd, dianche;
    private boolean isLogin = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find_backup;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        active = findView(R.id.active);
        quanzi = findView(R.id.quanzi);
        dianche = findView(R.id.dianche);
        wd = findView(R.id.wd);
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActiveListActivity.class));
            }
        });
        quanzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //登录状态
                PileApi.instance.mCheckLoginState()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull ResponseBody responseBody) {
                                try {
                                    String body = responseBody.string();
                                    if (body.equals("\"true\"")) {

                                        isLogin = true;
                                        //执行内容
                                        Intent intent = new Intent(getActivity(), QZActivity.class);
                                        intent.putExtra("url", Constant.BASE_URL + "hdsywz/weixin/page/faxianapp/quanzi/_quanzi.html");
                                        startActivity(intent);
                                    } else {
                                        isLogin = false;
                                        DialogHelper.getConfirmDialog(getActivity(), "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                            }
                                        }, null).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                isLogin = false;
                            }

                            @Override
                            public void onComplete() {

                            }
                        });


            }
        });
        wd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QZActivity.class);
                intent.putExtra("url", Constant.BASE_URL + "hdsywz/weixin/page/faxianapp/quanzi/_question.html");
                startActivity(intent);

            }
        });
        dianche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CarHomeActivity.class));
            }
        });
    }


}
