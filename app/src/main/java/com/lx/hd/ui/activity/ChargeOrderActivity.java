package com.lx.hd.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.TabIndicatorAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.SumChongDian;
import com.lx.hd.ui.fragment.ChargingOrderFragment;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ChargeOrderActivity extends BackActivity {
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private ArrayList<Fragment> fmList;
    public static final String ACTION_UPDATE_DATA = "com.lx.hd.action.update.data";
    private BroadcastReceiver mReceiver;

    private ViewPager viewPager;
    private String tempBeginTime="", tempFinishTime = "";
    private ChargingOrderFragment chargingOrderFragment;
    private TextView tvDu,tvTime;
    private ImageView img_icon;

    @Override
    protected int getContentView() {
        return R.layout.activity_charge_order;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
//        setTitleIcon(R.mipmap.icon_title_order);
        img_icon= (ImageView) findViewById(R.id.img_icon);
        img_icon.setVisibility(View.GONE);
        setTitleText("充电订单");
        showSearchIcon(true);
        getSumInfo("","");
        chargingOrderFragment=new ChargingOrderFragment();
        tvDu=(TextView)findViewById(R.id.tv_du);
        tvTime=(TextView)findViewById(R.id.tv_time);
        viewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        indicator = (FixedIndicatorView) findViewById(R.id.guide_indicator);
        fmList = new ArrayList<>();
        fmList.add(chargingOrderFragment);
        //   fmList.add(new ChargedOrderFragment());
        // 将viewPager和indicator使用
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        ViewPagerSetting();


        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ChargeOrderActivity.this,SearchActivity.class),1005);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        registerLocalReceiver();
    }

    private void registerLocalReceiver() {
        if (mManager == null)
            mManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_DATA);
        if (mReceiver == null)
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (ACTION_UPDATE_DATA.equals(action)) {
                        getSumInfo(tempBeginTime,tempFinishTime);
                        tempBeginTime="";
                        tempFinishTime="";
                    }
                }
            };
        mManager.registerReceiver(mReceiver, filter);
    }

    /**
     * 滑动界面tab标题的配置
     */
    private void ViewPagerSetting() {

        // 指示条
        //  indicator.setScrollBar(new ColorBar(getContext(), R.color.colorAccent, 5));
        // 未选中字体大小
        float unSelectSize = 20;
        // 选中字体大小
        float selectSize = unSelectSize * 1.0f;
        // 未选中的颜色
        int unSelectColor = Color.parseColor("#bbbbbb");
        // 选中时的颜色
        int selectColor = ContextCompat.getColor(this, R.color.main_red);
        // 设置tab字体的变化
        indicator.setOnTransitionListener(new OnTransitionTextListener(
                selectSize, unSelectSize, selectColor, unSelectColor));
        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);

        // 设置适配器
        indicatorViewPager.setAdapter(new TabIndicatorAdapter(getSupportFragmentManager(), this, fmList));

        indicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                if (currentItem == 1) {
                    //   mLocation.setVisibility(View.VISIBLE);
                } else {
                    //     mLocation.setVisibility(View.INVISIBLE);
                }
                viewPager.setCurrentItem(currentItem);

            }
        });
    }

    private void getSumInfo(String createtimeGE, String createtimeLE) {
        HashMap<String, String> map = new HashMap<>();
        map.put("createtimeGE", createtimeGE);
        map.put("createtimeLE", createtimeLE);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getSumzongdianliangandjine(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showWaitDialog("正在获取信息...");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            Gson gson = new Gson();
                            SumChongDian userList = gson.fromJson(body, new TypeToken<SumChongDian>() {
                            }.getType());

                            if(userList!=null){
                                if(userList.getTotalcount()==null){
                                    tvDu.setText("总度数：0°");
                                    tvTime.setText("总金额：0元");
                                }else {
                                    tvDu.setText("总度数："+userList.getTotalcount()+"°");
                                    tvTime.setText("总金额："+userList.getTotalrealmoney());
                                }
                               // tvDu.setText();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {
                        hideWaitDialog();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            tempBeginTime = data.getExtras().getString("tempBeginTime");//得到新Activity 关闭后返回的数据
            tempFinishTime = data.getExtras().getString("tempFinishTime");//得到新Activity 关闭后返回的数据
            chargingOrderFragment.beginTimes=tempBeginTime;
            chargingOrderFragment.finishTimes=tempFinishTime;
       //     chargingOrderFragment.getData(tempBeginTime,tempFinishTime);
            getSumInfo(tempBeginTime,tempFinishTime);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mManager != null) {
            if (mReceiver != null)
                mManager.unregisterReceiver(mReceiver);
        }
    }
}
