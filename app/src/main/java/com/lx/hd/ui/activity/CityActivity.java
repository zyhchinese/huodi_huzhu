package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.CityAdapter;
import com.lx.hd.adapter.ProvinceAdapter;
import com.lx.hd.adapter.SpinnerAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.AdditionalDemandListBean;
import com.lx.hd.bean.ProvinceEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CityActivity extends BackCommonActivity implements View.OnClickListener {
    private RecyclerView act_recyclerView;
    private Button btn_save;
    private List<ProvinceEntity> cityList;
    private String id;
    private String name;
    private int type = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_city;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("定位选择");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        type = getIntent().getIntExtra("type", 0);

        initView();
        initCityHttp();

    }


    private void initView() {
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }


    private void initCityHttp() {
        HashMap<String, String> map = new HashMap<>();
        map.put("provinceid", id);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.loadCity(params)
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
                            if (body.indexOf("false") != -1 || body.length() < 2) {
                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                cityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                initRecyclerView();


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

    private void initRecyclerView() {
        CityAdapter adapter = new CityAdapter(this, cityList);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new CityAdapter.OnClickItem() {
            @Override
            public void onClick(String name) {
                CityActivity.this.name = name;
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (CityActivity.this.name != null) {
            Constant.city = "";
            if (type == 1) {
                searchCarServices();
            } else if (type == 2) {
                sendPayLocalReceiver(ProvinceActivity.class.getName());
                Constant.cityflag = true;
                Constant.city = CityActivity.this.name;
                Intent intent2 = new Intent(CityActivity.this, DeliverGoodsActivity.class);
                intent2.putExtra("crty", CityActivity.this.name);
                startActivity(intent2);
                finish();
            } else {
                sendPayLocalReceiver(ProvinceActivity.class.getName());
                Constant.cityflag = true;
                Constant.city = CityActivity.this.name;
                finish();
            }

        } else {
            Toast.makeText(this, "请选择城市", Toast.LENGTH_SHORT).show();
        }

    }

    protected boolean sendPayLocalReceiver(String className) {
        if (mManager != null) {
            Intent intent = new Intent();
            intent.putExtra("className", className);
            intent.setAction(ACTION_PAY_FINISH_ALL);
            return mManager.sendBroadcast(intent);
        }
        return false;
    }

    private void searchCarServices() {
        showWaitDialog("正在刷新信息...");
        HashMap<String, String> map = new HashMap<>();
        map.put("cityname", name);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchCarType(params)
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
                            if (body.length() < 10) {
                                //   if(body.equals("\"false\""))
                                showNormalDialog("提示", "暂无数据", true);
                                sendPayLocalReceiver(ProvinceActivity.class.getName());

                                //  new AlertView("提示", "暂无数据", null, new String[]{"确定"}, null, CityActivity.this, AlertView.Style.Alert, null).setCancelable(false).show();
                            } else {
                                //新增
                                Constant.city=CityActivity.this.name;

                                Constant.wl_jgmxcity = CityActivity.this.name;
                                sendPayLocalReceiver(ProvinceActivity.class.getName());
                                finish();
                                //  setData(AdditionalDemandList);
                            }
                            hideWaitDialog();
                        } catch (IOException e) {
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

    private void showNormalDialog(String title, String Message, boolean isfp) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(CityActivity.this);
        normalDialog.setTitle(title);
        normalDialog.setMessage(Message);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        finish();
                        dialog.dismiss();
                    }
                });
//        normalDialog.setNegativeButton("关闭",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //...To-do
//                    }
//                });
        // 显示
        if (isfp) {

            normalDialog.setCancelable(false);

        } else {
            normalDialog.setCancelable(true);
        }
        normalDialog.show();
    }
}
