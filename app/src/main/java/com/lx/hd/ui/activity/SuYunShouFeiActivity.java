package com.lx.hd.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.ShouFeiBiaoZhunAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.ShouFeiBZBean;
import com.lx.hd.bean.huowuzhongliangBean;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class SuYunShouFeiActivity extends BackCommonActivity {

    private RecyclerView act_recyclerView;
    private ImageView img_back1;

    @Override
    protected int getContentView() {
        return R.layout.activity_su_yun_shou_fei;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        img_back1 = (ImageView) findViewById(R.id.img_back1);
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        img_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getsearchFeescale();

    }

    public void getsearchFeescale() {
        PileApi.instance.searchFeescale()
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

                            ArrayList<ShouFeiBZBean> temp = new Gson().fromJson(body, new TypeToken<ArrayList<ShouFeiBZBean>>() {
                            }.getType());

                            if (temp.size() == 0) {
                                Toast.makeText(SuYunShouFeiActivity.this, "暂无收费标准", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ShouFeiBiaoZhunAdapter adapter = new ShouFeiBiaoZhunAdapter(SuYunShouFeiActivity.this, temp);
                            LinearLayoutManager manager = new LinearLayoutManager(SuYunShouFeiActivity.this);
                            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            act_recyclerView.setLayoutManager(manager);
                            act_recyclerView.setAdapter(adapter);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(SuYunShouFeiActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
