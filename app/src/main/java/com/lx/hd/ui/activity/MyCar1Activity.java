package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.MycarsAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.ChongDianSocket;
import com.lx.hd.bean.DZapplyEntity;
import com.lx.hd.bean.MycarsEntity;
import com.lx.hd.interf.myCarListener;
import com.lx.hd.webscket.OnWebSocketListener;
import com.lx.hd.webscket.SocketCallBack;
import com.lx.hd.webscket.WebSocketClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MyCar1Activity extends BackActivity implements View.OnClickListener, myCarListener, OnWebSocketListener, SocketCallBack {
    private RecyclerView act_recyclerView;
    private Button btn_apply;
    private List<MycarsEntity> mycarsEntityList;
    private WebSocketClient client;
    private MycarsAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_car1;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("我的车辆");
        setTitleIcon(R.mipmap.img_mycar);

        initView();

        //请求我的车辆列表
        initMyCarHttp();
        client = new WebSocketClient(MyCar1Activity.this, Constant.BASE_WEBSOCKET_URL, this);
        client.setOnWebSocketListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMyCarHttp();

    }

    private void initMyCarHttp() {

        PileApi.instance.getMyCar()
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
                                showToast("无车辆信息，请添加");
                            } else {

                                Gson gson = new Gson();
                                mycarsEntityList = gson.fromJson(body, new TypeToken<List<MycarsEntity>>() {
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
        adapter = new MycarsAdapter(this, mycarsEntityList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
    }

    private void initView() {
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        btn_apply = (Button) findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, CarInfoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null) {
            client.disconnect();
        }
    }

    @Override
    public void Ondelete(int id) {
        initMyCarHttp();

    }

    @Override
    public void sussess() {
      //  showToast("连接成功");
        client.connect();
    }

    public void ifcartype(final MycarsEntity Cartype) {
        HashMap<String, String> map = new HashMap<>();
        map.put("chepaihao", Cartype.getChepaihao());
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchmyCarToId(data)
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
                            if (body.length() > 3) {
                                body = body.replace("\"", "");

                            }
                            if ("false".equals(body)) {

                            } else {
                                for (MycarsEntity temp : mycarsEntityList) {
                                    if (body.equals(temp.getId() + "")) {
                                        temp.setChesu(Cartype.getChesu());
                                        temp.setZongdianliu(Cartype.getZongdianliu());
                                        temp.setZongdianya(Cartype.getZongdianya());
                                        temp.setWendu(Cartype.getWendu());
                                        temp.setMaxpeople(Cartype.getMaxpeople());


                                    }
                                }
                                 adapter.notifyDataSetChanged();
                            }
                        } catch (IOException e) {
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


    public void updateSocketDataToView(MycarsEntity my) {
        ifcartype(my);
    }

    @Override
    public void onResponse(String response) {
        String body = response;
        if (!"".equals(response)) {
            Gson gson = new Gson();
            MycarsEntity my = gson.fromJson(body, MycarsEntity.class);
            if (my != null) {
                updateSocketDataToView(my);
            }
        }
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnectError(String e) {

    }

    @Override
    public void onDisConnected() {

    }
}
