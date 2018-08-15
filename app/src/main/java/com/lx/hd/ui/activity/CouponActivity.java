package com.lx.hd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.CouponAdapter;
import com.lx.hd.adapter.CouponCollectionAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.CouponBean;
import com.lx.hd.bean.CouponCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CouponActivity extends BackCommonActivity {
    List<CouponBean> ls = new ArrayList<CouponBean>();
    ListView listView;
    CouponAdapter ad;
    int code = 0;
    private TextView yhj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("优惠券");
        Intent gointent = getIntent();
        if (gointent != null) {
            code = gointent.getIntExtra("code", 0);
        }
        yhj = (TextView) findViewById(R.id.lingdang1);
        yhj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CouponActivity.this, CouponCollectionActivity.class));
            }
        });
        iflogin();
    }

    @Override
    protected void initWidget() {
        listView = (ListView) findViewById(R.id.couponlist);
        getCoupon();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (code == 1) {
                    CouponBean temp = ad.getItem(position);
                    int resultCode = 2;
                    String Couponid = temp.getCouponid() + "";
                    String jg = temp.getCheap_equal_money() + "";
                    Intent data = new Intent(); //同调用者一样 需要一个意图 把数据封装起来
                    data.putExtra("id", Couponid);
                    data.putExtra("jg", jg);
                    setResult(resultCode, data);
                    finish();
                }
            }
        });

    }

//    private void loaddata() {
//        ad = new CouponCollectionAdapter(CouponCollectionActivity.this, ls);
//        listView.setAdapter(ad);
//        ad.notifyDataSetChanged();
//    }

    private void loaddata() {
        ad = new CouponAdapter(CouponActivity.this, ls);
        listView.setAdapter(ad);
        ad.notifyDataSetChanged();
    }

    private void getCoupon() {

        PileApi.instance.getCustTickets()
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
                            if (body.length() > 3) {
                                ls = new Gson().fromJson(body, new TypeToken<List<CouponBean>>() {
                                }.getType());
                                loaddata();
                            } else {
                                ls.clear();
                                showToast("暂无更多优惠券");
                                loaddata();
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

    }

    @Override
    protected int getContentView() {
//        return R.layout.activity_coupon;
        return R.layout.activity_couponcollection;
    }

    private void getNewCoupon() {

        PileApi.instance.getCoupon()
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
                            if (body.length() > 3) {
                                yhj.setVisibility(View.VISIBLE);
//
//                                isLogin = true;
//                                yhj.setVisibility(View.VISIBLE);
//                                //跳转
//                            } else {
//                                isLogin = false;
//                                yhj.setVisibility(View.GONE);
                            } else {
                                yhj.setVisibility(View.GONE);
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

    }

    private void iflogin() {

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

                                getNewCoupon();

                                //跳转
                            } else {
                                yhj.setVisibility(View.GONE);
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCoupon();
        iflogin();
    }
}
