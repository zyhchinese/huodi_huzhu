package com.lx.hd.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.MyOrderAdapter;
import com.lx.hd.adapter.ProvinceAdapter;
import com.lx.hd.adapter.SpinnerAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CityEntity;
import com.lx.hd.bean.ProvinceEntity;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ProvinceActivity extends BackCommonActivity {
    private RecyclerView act_recyclerView;
    private List<ProvinceEntity> provinceEntityList;
    private int type;

    @Override
    protected int getContentView() {
        return R.layout.activity_province;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("定位选择");
        type = getIntent().getIntExtra("type", 0);
        initView();
        initProvinceHttp();
    }


    private void initView() {
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);

    }

    private void initProvinceHttp() {


        //请求省份
        PileApi.instance.loadProvince()
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
                            System.out.println(body);

                            if (body.indexOf("false") != -1 || body.length() < 3) {
                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                provinceEntityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());

                                initRecyclerView();


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("2222");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });

    }

    private void initRecyclerView() {
        ProvinceAdapter adapter = new ProvinceAdapter(this, provinceEntityList);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItemChild(new ProvinceAdapter.OnClickItemChild() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(ProvinceActivity.this, CityActivity.class);
                intent.putExtra("id", position + "");
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

    }

}
