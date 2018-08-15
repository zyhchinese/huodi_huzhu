package com.lx.hd.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.CouponAdapter;
import com.lx.hd.adapter.CouponCollectionAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CouponBean;
import com.lx.hd.bean.CouponCollection;
import com.lx.hd.bean.User;
import com.lx.hd.interf.CouponCollectionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

//优惠券领取
public class CouponCollectionActivity extends BackCommonActivity implements CouponCollectionListener {
    List<CouponCollection> ls = new ArrayList<CouponCollection>();
    ListView listView;
    LinearLayout bg;
    CouponCollectionAdapter ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("领取优惠券");

    }

    @Override
    protected void initWidget() {
        listView = (ListView) findViewById(R.id.couponlist);
        bg = (LinearLayout) findViewById(R.id.bg);
        getCoupon();


    }

    private void loaddata() {
        ad = new CouponCollectionAdapter(CouponCollectionActivity.this, ls);
        listView.setAdapter(ad);
        ad.notifyDataSetChanged();
    }

    private void getCoupon() {

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
                                ls = new Gson().fromJson(body, new TypeToken<List<CouponCollection>>() {
                                }.getType());
                                loaddata();
//                                isLogin = true;
//                                yhj.setVisibility(View.VISIBLE);
//                                //跳转
//                            } else {
//                                isLogin = false;
//                                yhj.setVisibility(View.GONE);
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

    private void getCustTicketsMax(final String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("couponid", id + "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.getCustTicketsMax(data)
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
                                body = body.substring(body.indexOf(":") + 1, body.indexOf("}"));
                                int a = Integer.parseInt(body);
                                if (a > 0) {
                                    addMyCoupon(id);

                                } else {
                                    showToast("这个优惠券已经被抢光了！");
                                    getCoupon();
                                }
//                                isLogin = true;
//                                yhj.setVisibility(View.VISIBLE);
//                                //跳转
//                            } else {
//                                isLogin = false;
//                                yhj.setVisibility(View.GONE);
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

    private void addMyCoupon(String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("couponid", id + "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.addMyCoupon(data)
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
                            if (body.indexOf("true") != -1) {
                                showToast("领取成功！");
                            } else {
                                showToast("领取失败！");
                            }
                            getCoupon();
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

    @Override
    public void OnClickItem(CouponCollection ccl) {
        getCustTicketsMax(ccl.getId() + "");
    }
}
