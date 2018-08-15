package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.ViewPageAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.AdditionalDemandListBean;
import com.lx.hd.bean.DeliverGoodsCarDataBean;

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

/*
收费标准
 */
public class ChargeStandardActivity extends BaseActivity {
    private ImageView img_back, syy, xyy;
    private TextView car_load, car_size, car_volume, qbj, qblc, clc;
    private List<ImageView> list_view;
    private ViewPager mViewPager;
    private LinearLayout ewxq, jgmx_city;
    private TextView title, tv_cs;
    private List<DeliverGoodsCarDataBean> CarPPListdata = new ArrayList<DeliverGoodsCarDataBean>();
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private int page = 0;//当前viewpage的页码缓存
    private ViewPageAdapter adpter;
    private String ctry = "";
    private boolean flag = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_charge_standard;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        syy = (ImageView) findViewById(R.id.syy);
        xyy = (ImageView) findViewById(R.id.xyy);
        title = (TextView) findViewById(R.id.title);
        qbj = (TextView) findViewById(R.id.qbj);
        qblc = (TextView) findViewById(R.id.qblc);
        clc = (TextView) findViewById(R.id.clc);
        car_load = (TextView) findViewById(R.id.car_load);
        car_size = (TextView) findViewById(R.id.car_size);
        car_volume = (TextView) findViewById(R.id.car_volume);
        syy = (ImageView) findViewById(R.id.syy);
        tv_cs = (TextView) findViewById(R.id.tv_cs);
        xyy = (ImageView) findViewById(R.id.xyy);
        ewxq = (LinearLayout) findViewById(R.id.ewxq);
        jgmx_city = (LinearLayout) findViewById(R.id.jgmx_city);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("收费标准");
        syy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);

            }
        });
        xyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);

            }
        });
        jgmx_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                Intent intent = new Intent(ChargeStandardActivity.this, ProvinceActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
        list_view = new ArrayList<ImageView>();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageSelected(int arg0) {
                car_load.setText(CarPPListdata.get(arg0).getCar_load());
                car_size.setText(CarPPListdata.get(arg0).getCar_size());
                car_volume.setText(CarPPListdata.get(arg0).getCar_volume());
                qbj.setText(CarPPListdata.get(arg0).getStarting_price() + "元");
                qblc.setText(CarPPListdata.get(arg0).getStarting_mileage() + "公里");
                clc.setText(CarPPListdata.get(arg0).getMileage_price() + "元");
                title.setText(CarPPListdata.get(arg0).getCar_type());
                page = arg0;

            }
        });
        ctry = getIntent().getStringExtra("crty");
        // showToast(ctry);
        searchCarType();
        searchCarServices();
    }

    private void updatecardata(List<DeliverGoodsCarDataBean> CarPPList) {
        if (CarPPList != null) {
            if (CarPPList.size() > 0) {
                mTitleList.clear();
                list_view.clear();
                for (DeliverGoodsCarDataBean bean : CarPPList) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_deliver_image, null);
                    mTitleList.add(bean.getCar_type());
                    ImageView txt_num = (ImageView) view.findViewById(R.id.car_images);
                    Glide.with(ChargeStandardActivity.this).load(Constant.BASE_URL + bean.getFolder() + bean.getAutoname()).into(txt_num);
                    list_view.add(txt_num);
                }
                adpter = new ViewPageAdapter(list_view, mTitleList);
                mViewPager.setAdapter(adpter);
                adpter.notifyDataSetChanged();
                page = 0;
                mViewPager.setCurrentItem(0);
                title.setText(CarPPListdata.get(page).getCar_type());
                car_load.setText(CarPPListdata.get(page).getCar_load());
                car_size.setText(CarPPListdata.get(page).getCar_size());
                car_volume.setText(CarPPListdata.get(page).getCar_volume());
                qbj.setText(CarPPListdata.get(page).getStarting_price() + "元");
                qblc.setText(CarPPListdata.get(page).getStarting_mileage() + "公里");
                clc.setText(CarPPListdata.get(page).getMileage_price() + "元");
            }
        }
    }

    private void searchCarType() {
        showWaitDialog("正在刷新信息...");
        HashMap<String, String> map = new HashMap<>();
        map.put("cityname", ctry);
        //showToast(ctry);
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
                            //  showToast(body);
                            if (body.length() < 10) {
                                //   if(body.equals("\"false\""))
                                showToast("获取信息失败，请选择城市");
                                //   Intent intent = new Intent(ChargeStandardActivity.this, ProvinceActivity.class);
                                //  startActivity(intent);
                            } else {
                                CarPPListdata.clear();
                                Gson gson = new Gson();
                                List<DeliverGoodsCarDataBean> CarPPList = gson.fromJson(body, new TypeToken<List<DeliverGoodsCarDataBean>>() {
                                }.getType());
                                //showToast(body);
                                CarPPListdata = CarPPList;
                                updatecardata(CarPPList);
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

    private void setData(ArrayList<AdditionalDemandListBean> AdditionalDemandList) {
        for (AdditionalDemandListBean bean : AdditionalDemandList) {
            TextView tv = new TextView(this);
            //2.把信息设置为文本框的内容
            if (bean.getService_price() == 0) {
                tv.setText(bean.getService_name() + "：免费");
            } else {
                tv.setText(bean.getService_name() + "：附加" + Math.floor(bean.getService_price() * 100) + "%" + "路费");
            }
            tv.setTextSize(12);
            tv.setPadding(8, 4, 8, 4);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(35, 0, 0, 35);
            tv.setLayoutParams(lp);
            //3.把textView设置为线性布局的子节点
            ewxq.addView(tv);
        }
    }

    private void searchCarServices() {
        showWaitDialog("正在刷新信息...");
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "1");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchCarServices(params)
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
                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                ArrayList<AdditionalDemandListBean> AdditionalDemandList = gson.fromJson(body, new TypeToken<List<AdditionalDemandListBean>>() {
                                }.getType());
                                setData(AdditionalDemandList);
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

    @Override
    protected void onResume() {
        super.onResume();
        tv_cs.setText(ctry);
        if (flag) {
            if (!Constant.wl_jgmxcity.equals("")) {
                ctry = Constant.wl_jgmxcity;
                tv_cs.setText(ctry);
                searchCarType();
            }
            flag = false;
        }
    }
}
