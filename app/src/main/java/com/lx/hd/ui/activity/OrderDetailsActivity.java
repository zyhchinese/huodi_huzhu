package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.adapter.OrderAdapter;
import com.lx.hd.adapter.OrderListAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.OrderDetailsInfoEntity;
import com.lx.hd.bean.OrderDetailsListEntity;
import com.lx.hd.bean.ShoppingEntity;
import com.lx.hd.utils.DialogHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class OrderDetailsActivity extends BackCommonActivity implements View.OnClickListener {
    private RecyclerView act_confirm_recyclerView;
    private TextView tv_number, tv_bianhao, tv_time, tv_money,tv_name1,tv_address;
    private Button btn_pay;

    private String id;
    private int type;


    private boolean isLogin = false;
    private List<OrderDetailsInfoEntity> list;


    @Override
    protected int getContentView() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("订单详情");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        type = intent.getIntExtra("type1", 0);

        initView();
        getCarHomeListData();

    }

    private void initView() {
        act_confirm_recyclerView = (RecyclerView) findViewById(R.id.act_confirm_recyclerView);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_bianhao = (TextView) findViewById(R.id.tv_bianhao);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_name1= (TextView) findViewById(R.id.tv_name1);
        tv_address= (TextView) findViewById(R.id.tv_address);
        btn_pay = (Button) findViewById(R.id.btn_pay);
        if (type == 0) {
            btn_pay.setVisibility(View.GONE);
        } else {
            btn_pay.setVisibility(View.VISIBLE);
            btn_pay.setOnClickListener(this);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        initLogin();

    }

    //是否登录
    private void initLogin() {
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
                            } else {
                                isLogin = false;
                                DialogHelper.getConfirmDialog(OrderDetailsActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(OrderDetailsActivity.this, LoginActivity.class));
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


    //请求列表之外的信息
    private void getCarHomeListData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getOneOrder(params)
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

                            if (body.indexOf("false") != -1 || body.length() < 10) {
                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                list = gson.fromJson(body, new TypeToken<List<OrderDetailsInfoEntity>>() {
                                }.getType());
                                updateInfo(list);
                                getCarListData(list);

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

    private void updateInfo(List<OrderDetailsInfoEntity> list) {
        if (list.get(0).getOrderstatus() == 0) {
            tv_number.setText("待支付");
        } else if (list.get(0).getOrderstatus() == 1) {
            tv_number.setText("待发货");
        } else if (list.get(0).getOrderstatus() == 2) {
            tv_number.setText("待收货");
        } else {
            tv_number.setText("已完成");
        }
        tv_bianhao.setText(list.get(0).getOrderno());
        tv_time.setText(list.get(0).getCreatetime());
        tv_money.setText(list.get(0).getTotalmoney() + "");
        tv_name1.setText(list.get(0).getShcustname()+"      "+list.get(0).getShphone());
        tv_address.setText(list.get(0).getCustaddress());


    }


    //请求列表信息
    private void getCarListData(List<OrderDetailsInfoEntity> list) {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderid", list.get(0).getId() + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getOrderProList(params)
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
                            if (body.indexOf("false") != -1) {
                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                List<OrderDetailsListEntity> list1 = gson.fromJson(body, new TypeToken<List<OrderDetailsListEntity>>() {
                                }.getType());
                                update(list1);
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

    private void update(List<OrderDetailsListEntity> list1) {
        OrderListAdapter adapter = new OrderListAdapter(this, list1);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        act_confirm_recyclerView.setLayoutManager(manager);
        act_confirm_recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        HashMap<String, String> map = new HashMap<>();
        map.put("orderid", list.get(0).getId() + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.updateOrder_4(params)
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
                            System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" + body);
                            if (body.indexOf("false") != -1) {
                                showToast("获取信息失败，请重试");
                            } else {

                                Toast.makeText(OrderDetailsActivity.this, "收货成功", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent("abc");
                                sendBroadcast(intent);
                                finish();


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
}
