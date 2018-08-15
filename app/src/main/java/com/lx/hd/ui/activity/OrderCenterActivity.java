package com.lx.hd.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.utils.DialogHelper;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class OrderCenterActivity extends BackCommonActivity {
    private LinearLayout myorder;
    private LinearLayout tx;
    private LinearLayout custRecharge;
    private TextView tv_shouye,tv_fenlei,tv_gouwuche;
    private boolean isLogin = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_order_center;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("个人中心");

        //我的订单
        myorder = (LinearLayout) findViewById(R.id.myorder);
        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderCenterActivity.this, myOrderActivity.class));
            }
        });
        //我的预约
        tx= (LinearLayout) findViewById(R.id.tx);
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderCenterActivity.this,MyReservationActivity.class);
                startActivity(intent);
            }
        });

        //地址管理
        custRecharge= (LinearLayout) findViewById(R.id.custRecharge);
        custRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(OrderCenterActivity.this,AddressActivity.class);
                intent1.putExtra("line",2);
                startActivity(intent1);
            }
        });

        tv_shouye= (TextView) findViewById(R.id.tv_shouye);
        tv_shouye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderCenterActivity.this,ShangChengActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_fenlei= (TextView) findViewById(R.id.tv_fenlei);
        tv_fenlei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getSharedPreferences("fenlei", Context.MODE_PRIVATE);
                Intent intent=new Intent(OrderCenterActivity.this,ShangPinFenLeiActivity.class);
                intent.putExtra("protypeid1", sp.getString("protypeid1",""));
                intent.putExtra("protypeid2", sp.getString("protypeid2",""));
                intent.putExtra("protypeid3", sp.getString("protypeid3",""));
                intent.putExtra("proname1", sp.getString("proname1",""));
                intent.putExtra("proname2", sp.getString("proname2",""));
                intent.putExtra("proname3", sp.getString("proname3",""));

                intent.putExtra("proname", sp.getString("proname",""));
                startActivity(intent);
                finish();
            }
        });
        tv_gouwuche= (TextView) findViewById(R.id.tv_gouwuche);
        tv_gouwuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderCenterActivity.this,ShoppingCartActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
    //判断登录
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
                                DialogHelper.getConfirmDialog(OrderCenterActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(OrderCenterActivity.this, LoginActivity.class));
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

    @Override
    protected void onResume() {
        super.onResume();
        initLogin();
    }
}
