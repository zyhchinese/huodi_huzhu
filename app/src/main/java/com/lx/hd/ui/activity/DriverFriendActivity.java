package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;

import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.utils.DialogHelper;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class DriverFriendActivity extends BackCommonActivity implements View.OnClickListener {

    private CardView pinion2,pinion3,pinion5,pinion6,pinion7,pinion8,pinion9,pinion10;

    @Override
    protected int getContentView() {
        return R.layout.activity_driver_friend;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("货主之家");
        initView();
    }

    private void initView() {
        pinion2= (CardView) findViewById(R.id.pinion2);
        pinion3= (CardView) findViewById(R.id.pinion3);
        pinion5= (CardView) findViewById(R.id.pinion5);
        pinion6= (CardView) findViewById(R.id.pinion6);
        pinion7= (CardView) findViewById(R.id.pinion7);
        pinion8= (CardView) findViewById(R.id.pinion8);
        pinion9= (CardView) findViewById(R.id.pinion9);
        pinion10= (CardView) findViewById(R.id.pinion10);
        pinion2.setOnClickListener(this);
        pinion3.setOnClickListener(this);
        pinion5.setOnClickListener(this);
        pinion6.setOnClickListener(this);
        pinion7.setOnClickListener(this);
        pinion8.setOnClickListener(this);
        pinion9.setOnClickListener(this);
        pinion10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pinion2:
                startActivity(new Intent(this,NewsListActivity.class));
                break;
            case R.id.pinion3:
                startActivity(new Intent(this,YongHuZhiNanActivity.class));
                break;
            case R.id.pinion5:
                startActivity(new Intent(this,ChangJianWenTiActivity.class));
                break;
            case R.id.pinion6:
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
                                        //执行内容
                                        Intent intent = new Intent(DriverFriendActivity.this, QZActivity.class);
                                        intent.putExtra("url", Constant.BASE_URL + "hdsywz/weixin/page/faxianapp/quanzi/_quanzi.html");
                                        startActivity(intent);
                                    } else {
                                        DialogHelper.getConfirmDialog(DriverFriendActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(DriverFriendActivity.this, LoginActivity.class));
                                            }
                                        }, null).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case R.id.pinion7:
                Intent intent = new Intent(this, QZActivity.class);
                intent.putExtra("url", Constant.BASE_URL + "hdsywz/weixin/page/faxianapp/quanzi/_question.html");
                startActivity(intent);
                break;
            case R.id.pinion8:
                startActivity(new Intent(this, CarHomeActivity.class));
                break;
            case R.id.pinion9:
                startActivity(new Intent(this, ActiveListActivity.class));
                break;
            case R.id.pinion10:
                startActivity(new Intent(this, ErShouCheShouYeActivity.class));
                break;
        }
    }
}
