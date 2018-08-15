package com.lx.hd.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.IntegralAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.IntegralBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class IntegralActivity extends BackCommonActivity {
    ListView list;
    TextView jf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = (ListView) findViewById(R.id.ls);
        jf = (TextView) findViewById(R.id.jf);
        setTitleText("积分");
        PileApi.instance.getCustScoreList()
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
                                List<IntegralBean> listdata = new Gson().fromJson(body, new TypeToken<List<IntegralBean>>() {
                                }.getType());
                                if (listdata.size() > 0) {
                                    list.setVisibility(View.VISIBLE);
                                    jf.setText(listdata.get(0).getScores());
                                    list.setAdapter(new IntegralAdapter(IntegralActivity.this, listdata));
                                } else {
                                    showToast("无积分数据！");
                                    list.setVisibility(View.INVISIBLE);
                                }
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
        return R.layout.activity_integral;
    }
}
