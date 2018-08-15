package com.lx.hd.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.SiJiPingJiaAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.QueryListEntity;
import com.lx.hd.bean.SiJiPingJiaEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class SiJiPingJiaActivity extends BackActivity {

    private RecyclerView act_recyclerView;
    private List<SiJiPingJiaEntity> siJiPingJiaEntityList;

    @Override
    protected int getContentView() {
        return R.layout.activity_si_ji_ping_jia;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("司机评价");
        setTitleIcon(R.mipmap.img_biaoti_pingjia);

        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);




        initSijiPingJia();
    }

    private void initSijiPingJia() {
        PileApi.instance.selectDriverEvaluation()
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
                                showToast("暂无司机评价");
                            } else {
                                Gson gson = new Gson();
                                siJiPingJiaEntityList = gson.fromJson(body, new TypeToken<List<SiJiPingJiaEntity>>() {
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
        SiJiPingJiaAdapter adapter=new SiJiPingJiaAdapter(this,siJiPingJiaEntityList);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
    }
}
