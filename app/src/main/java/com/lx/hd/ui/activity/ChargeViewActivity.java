package com.lx.hd.ui.activity;
/**
 * 充电中页面
 * tb
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.ChongDianOrder;
import com.lx.hd.bean.ChongDianSocket;
import com.lx.hd.bean.SumChongDian;
import com.lx.hd.webscket.OnWebSocketListener;
import com.lx.hd.webscket.SocketCallBack;
import com.lx.hd.webscket.WebSocketClient;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.mob.tools.utils.ResHelper.getScreenWidth;

public class ChargeViewActivity extends BaseActivity implements OnWebSocketListener, SocketCallBack {
    private ViewPager mViewPager;
    private ImageView goback;
    private WebSocketClient client;
    private TextView orderno, createtime, electricsbm, electricno, realmoney, ycd, ycxs, zje, zds;
    private RelativeLayout cdtx1;
    private int orderid;
    private ProgressDialog progressDialog;
    private ImageView wave;
    private boolean flag = false;
    private TextView ds;
   // CountDownTimer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mTimer = new CountDownTimer(5 * 1000, 1000) {
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//            //showToast("延迟一秒========");
//            }
//
//            @Override
//            public void onFinish() {
//                isds();
//            }
//        };
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在结束充电...");
        flag = false;
        client = new WebSocketClient(ChargeViewActivity.this, Constant.BASE_WEBSOCKET_URL, this);
        client.setOnWebSocketListener(this);
        mViewPager = (ViewPager) findViewById(R.id.viewpage);
        //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(ChargeViewActivity.this);
        View view1 = mLi.inflate(R.layout.fragment_chargeview_1, null);
        //    initView1(view1);
        View view2 = mLi.inflate(R.layout.fragment_chargeview_2, null);
        orderno = (TextView) findViewById(R.id.orderno);
        createtime = (TextView) findViewById(R.id.createtime);
        electricsbm = (TextView) findViewById(R.id.electricsbm);
        electricno = (TextView) findViewById(R.id.electricno);
        realmoney = (TextView) findViewById(R.id.realmoney);
        cdtx1 = (RelativeLayout) findViewById(R.id.cdtx1);
        zje = (TextView) findViewById(R.id.zje);
        zds = (TextView) findViewById(R.id.zds);
        goback = (ImageView) findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //    initView2(view2);
        //每个页面的view数据
        final ArrayList<View> views = new ArrayList();
        views.add(view1);
//        views.add(view2);
        //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(views.get(position));
            }


        };
        mViewPager.setAdapter(mPagerAdapter);
        ycd = (TextView) view1.findViewById(R.id.ycd);
        ycxs = (TextView) view1.findViewById(R.id.ycxs);
        wave = (ImageView) view1.findViewById(R.id.qiu);
        int screenWidth = getScreenWidth(this); // 获取屏幕宽度
        ViewGroup.LayoutParams lp = wave.getLayoutParams();
        lp.width = screenWidth;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        wave.setLayoutParams(lp);

        wave.setMaxWidth(screenWidth/2-40);
        wave.setMaxHeight(screenWidth/2-40); //这里其实可以根据需求而定，我这里测试为最大宽度的5倍
        ds = (TextView) view1.findViewById(R.id.ds);
        Glide.with(this)
                .load(R.mipmap.chongdiand)
                //   .apply(mOptions)
                .into(wave);

        client.setOnWebSocketListener(this);
        cdtx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endChongDian(electricno.getText().toString(), electricsbm.getText().toString());
            }
        });
        getChongDianOrderInfo();
        sendPayLocalReceiver();

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_charge_view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null) {
            client.disconnect();
        }
       // mTimer.cancel();
    }

    @Override
    public void onResponse(String response) {
        String body = response;
        if (!"".equals(response)) {
            Gson gson = new Gson();
            ChongDianSocket userList = gson.fromJson(body, ChongDianSocket.class);
            if (userList != null) {
                updateSocketDataToView(userList);
            }
        }
    }

    @Override
    public void onConnected() {
        showToast("连接成功");
        hideWaitDialog();

    }

    @Override
    public void onConnectError(String e) {
        showToast("可能由于网络等原因，获取信息失败");
    }

    @Override
    public void onDisConnected() {
        showWaitDialog("正在获取信息...");
        // showToast("断开");
    }

    @Override
    public void sussess() {
        showWaitDialog("正在获取信息...");
        client.connect();
    }

    private void updateSocketDataToView(ChongDianSocket data) {
        if (Integer.valueOf(data.getOrderid()) == orderid) {
            if (!data.getBiaoshi().equals("01")) {
                //showToast("状态码不为01，跳转账单界面");
                startActivity(new Intent(ChargeViewActivity.this, ChargeOrderActivity.class));
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                finish();
                return;
            }
            if (flag) {
                endChongDian(electricno.getText().toString(), electricsbm.getText().toString());
            }
            if (!"null".equals(data.getRealmoney()) && data.getRealmoney() != null) {
                Float yc = Float.parseFloat(data.getRealmoney());
                BigDecimal bd = new BigDecimal((double) yc);
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                yc = bd.floatValue();
                realmoney.setText(yc + "元");
            } else {
                realmoney.setText("0.00元");
            }
            ycd.setText("已充：" + data.getChongdiandushu() + "度电");
//            wave.setMProgressText(data.getChongdiandushu());
            ds.setText(data.getChongdiandushu() + "°");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
            try {
                Date enddate = sdf.parse(data.getEndtime());
                Date startdate = sdf.parse(createtime.getText().toString());
                ycxs.setText("已充：" + getDatePoor(enddate, startdate) + "小时");
            } catch (ParseException e) {
                showToast("时间格式错误");
            }
        }

    }

    private void updateSumChongDian(SumChongDian sm) {
        if (sm != null) {
            if ("null".equals(sm.getTotalrealmoney()) || sm.getTotalrealmoney() == null) {
                zje.setText("0元>");
            } else {

                zje.setText(sm.getTotalrealmoney() + "元>");
            }

            if ("null".equals(sm.getTotalcount()) || sm.getTotalcount() == null) {
                zds.setText("0°>");
            } else {
                zds.setText(sm.getTotalcount() + "°>");
            }
            if ("null".equals(sm.getTime()) || sm.getTime() == null) {
                ycxs.setText("已冲0小时");
            } else {
                ycxs.setText("已冲" + Math.abs(Float.parseFloat(sm.getTime())) + "小时");
            }
        }
    }

    private void updateChongDianData(ChongDianOrder ls) {
        //       orderno,createtime,electricsbm,electricno,realmoney
        if (ls != null) {
            orderno.setText(ls.getOrderno());
            createtime.setText(ls.getCreatetime());
            electricsbm.setText(ls.getElectricsbm());
            electricno.setText(ls.getElectricno());
            if (!"null".equals(ls.getRealmoney()) && ls.getRealmoney() != null) {
                Float yc = Float.parseFloat(ls.getRealmoney());
                BigDecimal bd = new BigDecimal((double) yc);
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                yc = bd.floatValue();
                realmoney.setText(yc + "元");
            } else {
                realmoney.setText("0.00元");
            }
            ycd.setText("已充：" + ls.getCount() + "度电");
//            wave.setMProgressText(ls.getCount());
            ds.setText(ls.getCount() + "°");
            orderid = ls.getOrderid();
            //mTimer.start();
            getSumInfo();

        }
    }


    private void isds() {
        String ds = "已充：0度电";
//        if (true) {
        if (ds.equals(ycd.getText().toString())) {
        //    Toast.makeText(ChargeViewActivity.this, "判断为0度电，调用接口", Toast.LENGTH_SHORT).show();
            uplodedd();
        } else {
           // mTimer.cancel();
        }
    }

    private void getChongDianOrderInfo() {
        showWaitDialog("正在获取信息...");
        PileApi.instance.getZhengZaiChongDianOrder()
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
                                showToast("获取信息失败，请登录后重试");
                            } else {
                                Gson gson = new Gson();
                                List<ChongDianOrder> userList = gson.fromJson(body, new TypeToken<List<ChongDianOrder>>() {
                                }.getType());
                                updateChongDianData(userList.get(0));
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

    private void uplodedd() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderid", orderid + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        showWaitDialog("拉取信息中...");
        PileApi.instance.ziciFaSongDingdan(params)
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
                                hideWaitDialog();
                                //mTimer.start();
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

    private void getSumInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("createtimeGE", "");
        map.put("createtimeLE", "");
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

                            updateSumChongDian(userList);
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

    private void endChongDian(String zhongduanhao, String qiangkouhao) {
        HashMap<String, String> map = new HashMap<>();
        map.put("electricno", zhongduanhao);
        map.put("electricsbm", qiangkouhao);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.endChongDian(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showWaitDialog("正在发送结束请求...");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            if (body.indexOf("true") != -1) {
                                if (!progressDialog.isShowing()) {
                                    progressDialog.show();
                                    flag = true;
                                }
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

    private float getDatePoor(Date endDate, Date nowDate) {
        long nh = 1000 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        float diff = endDate.getTime() - nowDate.getTime();
        float hour = diff / nh;
        BigDecimal bd = new BigDecimal((double) hour);
        bd = bd.setScale(2, 4);
        hour = bd.floatValue();
        return hour;
    }
}
