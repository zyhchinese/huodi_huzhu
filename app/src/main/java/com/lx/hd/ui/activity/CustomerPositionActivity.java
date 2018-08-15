package com.lx.hd.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.IntegralAdapter;
import com.lx.hd.adapter.WalletAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.IntegralBean;
import com.lx.hd.bean.walletBean;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CustomerPositionActivity extends BackCommonActivity {
    ListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("账单明细");
        ls = (ListView) findViewById(R.id.ls);
        PileApi.instance.getCustRecharge()
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
                            if (!body.equals("")) {
                                List<walletBean> listdata = new Gson().fromJson(body, new TypeToken<List<walletBean>>() {
                                }.getType());
                                if (listdata.size() > 0) {
                                    ls.setAdapter(new WalletAdapter(CustomerPositionActivity.this, listdata));
                                } else {
                                    showToast("无明细数据！");
                                }
                            } else {
                                showToast("无明细数据！");
                            }
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
        return R.layout.activity_customer_position;
    }
}
