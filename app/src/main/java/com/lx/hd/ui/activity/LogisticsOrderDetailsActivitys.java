package com.lx.hd.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.AccountHelper;
import com.lx.hd.adapter.EWaiAdapter;
import com.lx.hd.alipay.OrderInfoUtil2_0;
import com.lx.hd.alipay.PayResult;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.EWaiEntity;
import com.lx.hd.bean.FeiLvEntity;
import com.lx.hd.bean.LogisticsOrderDetailsEntity;
import com.lx.hd.bean.ZuoBiaoDianEntity;
import com.lx.hd.utils.MapContainer;
import com.lx.paylib.PayAPI;
import com.lx.paylib.WechatPayReq;
import com.lx.paylib.wxutils.WXPayUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.lx.hd.base.Constant.REQUEST_CODE;

public class LogisticsOrderDetailsActivitys extends BackCommonActivity implements View.OnClickListener, AMapLocationListener, LocationSource, AMap.OnMyLocationChangeListener {

    private TextView tv_dingdanhao, tv_type, tv_name, tv_phone, tv_car_type, tv_yongchetime, tv_dingdantime,
            tv_address, tv_address1, tv_juli, tv_money, tv_siji_name, tv_siji_phone, tv_info;
    private TextView tv_quxiao,tv_kaishi,tv_quxiao1,tv_jieshu,tv_pingjia,tv_yuji_daoda,tv_beizhu;
    private TextView tv_xingming,tv_lianxi,tv_huowu11,tv_shijian,tv_shijian1,tv_shijian2,
            tv_xuqiu,tv_beizhumingzi,tv_jiehuoinfo,tv111,tv222,tv_zongji,tv_yuan,tv_jiaoyi;
    private ImageView img_dizhi_t,img_dizhi_f;
    private LinearLayout linear_kaishi;
    private RelativeLayout relative;

    private RelativeLayout relative1, relative2;
    private View view10;
    private RecyclerView act_recyclerView;
    private ImageView img_type;
    private Button btn_quxiao, btn_jieshu, btn_kaishi;
    private EditText ed_content;
    private String orderno;
    private String line;
    private String content = "好评";
    private List<LogisticsOrderDetailsEntity> detailsEntityList;
    private List<EWaiEntity> eWaiEntityList;
    private List<FeiLvEntity> feiLvEntityList;
    private List<ZuoBiaoDianEntity> zuoBiaoDianEntityList;
    private int pingjia = 5;
    private MapView mapView;
    private AMap aMap;
    private ScrollView scrollView;
    private MapContainer mapContainer;
    private List<LatLng> lngList=new ArrayList<>();
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener mListener;
    private AlertDialog payalertDialog;
    private double i;
    private AlertDialog alertDialog;
    private EditText ed_password;
    private boolean isok = false;
    private boolean ispayok = false;
    private boolean zhoujie=false;
    private static final int SDK_PAY_FLAG = 1;
    protected LocalBroadcastManager mManager1;
    private BroadcastReceiver mReceiver1;

    @Override
    protected int getContentView() {
        return R.layout.activity_logistics_order_detailss;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView= (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        if (aMap==null){
            aMap=mapView.getMap();
        }
        initGuiJi();

//        MyLocationStyle myLocationStyle=new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.g));
//        aMap.setMyLocationStyle(myLocationStyle);

        aMap.setLocationSource(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(this);

        scrollView= (ScrollView) findViewById(R.id.scrollView);
        mapContainer= (MapContainer) findViewById(R.id.mapContainer);
        mapContainer.setScrollView(scrollView);
//        location();
    }

    private void initGuiJi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.queryGps(params)
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
//                                showToast("订单详情暂无数据");
                                mapView.setVisibility(View.GONE);
                                mapContainer.setVisibility(View.GONE);
                            } else {
                                Gson gson = new Gson();
                                zuoBiaoDianEntityList = gson.fromJson(body, new TypeToken<List<ZuoBiaoDianEntity>>() {
                                }.getType());

                                String lattitude = "";
                                String longitude = "";
                                if (zuoBiaoDianEntityList.size() > 0) {
                                    mapView.setVisibility(View.VISIBLE);
                                    mapContainer.setVisibility(View.VISIBLE);
                                    lattitude = zuoBiaoDianEntityList.get((zuoBiaoDianEntityList.size() - 1) / 2).getLattitude();
                                    longitude = zuoBiaoDianEntityList.get((zuoBiaoDianEntityList.size() - 1) / 2).getLongitude();
                                } else {
                                    mapView.setVisibility(View.GONE);
                                    mapContainer.setVisibility(View.GONE);
                                    return;
                                }

                                //设置缩放级别
                                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                                //将地图移动到定位点
                       //        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.parseDouble(116.46+""), Double.parseDouble(39.92+""))));
                                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.parseDouble(lattitude), Double.parseDouble(longitude))));

                                LatLng latLng=new LatLng(Double.parseDouble(zuoBiaoDianEntityList.get(0).getLattitude()), Double.parseDouble(zuoBiaoDianEntityList.get(0).getLongitude()));
                                MarkerOptions markerOptions=new MarkerOptions();
                                markerOptions.position(latLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.img_zuobiandian));
                                markerOptions.title("起点");
                                markerOptions.visible(true);
//                                aMap.addMarker(markerOptions);
                                Marker marker=aMap.addMarker(markerOptions);
                                marker.setInfoWindowEnable(true);

                                LatLng latLng111=new LatLng(Double.parseDouble(zuoBiaoDianEntityList.get(zuoBiaoDianEntityList.size()-1).getLattitude()), Double.parseDouble(zuoBiaoDianEntityList.get(zuoBiaoDianEntityList.size()-1).getLongitude()));
                                MarkerOptions markerOptions111=new MarkerOptions();
                                markerOptions111.position(latLng111)
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.img_zuobiandian));
                                markerOptions111.title("终点");
                                markerOptions111.visible(true);
//                                aMap.addMarker(markerOptions111);
                                Marker marker11=aMap.addMarker(markerOptions111);
                                marker11.setInfoWindowEnable(true);

//                                LatLng latLng0=new LatLng(36.665209, 117.15244);
//                                lngList.add(latLng0);
//                                LatLng latLng1=new LatLng(36.665209+0.001, 117.15244+0.001);
//                                lngList.add(latLng1);
//                                LatLng latLng3=new LatLng(36.665209+0.001, 117.15244-0.001);
//                                lngList.add(latLng3);
//                                LatLng latLng4=new LatLng(36.665209-0.001, 117.15244+0.002);
//                                lngList.add(latLng4);

                                PolylineOptions options=new PolylineOptions();
//                                options.add(lngList.get(0));
//                                options.add(lngList.get(1));
//                                options.add(lngList.get(2));
//                                options.add(lngList.get(3));
                                for (ZuoBiaoDianEntity entity:zuoBiaoDianEntityList){
                                    LatLng latLng1=new LatLng(Double.parseDouble(entity.getLattitude()),Double.parseDouble(entity.getLongitude()));
//                                    lngList.add(latLng1);
                                    options.add(latLng1);
                                }
                                options.width(5).geodesic(true).color(Color.GREEN);
                                aMap.addPolyline(options);

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

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (mManager1 != null) {
            if (mReceiver1 != null)
                mManager1.unregisterReceiver(mReceiver1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("同城小件单详情");
        orderno = getIntent().getStringExtra("orderno");
        line = getIntent().getStringExtra("line");

        btn_quxiao = (Button) findViewById(R.id.btn_quxiao);
        btn_jieshu = (Button) findViewById(R.id.btn_jieshu);
        btn_kaishi = (Button) findViewById(R.id.btn_kaishi);
        tv_dingdanhao = (TextView) findViewById(R.id.tv_dingdanhao);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_car_type = (TextView) findViewById(R.id.tv_car_type);
        tv_yongchetime = (TextView) findViewById(R.id.tv_yongchetime);
        tv_dingdantime = (TextView) findViewById(R.id.tv_dingdantime);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_address1 = (TextView) findViewById(R.id.tv_address1);
        tv_juli = (TextView) findViewById(R.id.tv_juli);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_siji_name = (TextView) findViewById(R.id.tv_siji_name);
        tv_siji_phone = (TextView) findViewById(R.id.tv_siji_phone);
        tv_info = (TextView) findViewById(R.id.tv_info);

        tv_xingming = (TextView) findViewById(R.id.tv_xingming);
        tv_lianxi = (TextView) findViewById(R.id.tv_lianxi);
        tv_huowu11 = (TextView) findViewById(R.id.tv_huowu11);
        tv_shijian = (TextView) findViewById(R.id.tv_shijian);
        tv_shijian1 = (TextView) findViewById(R.id.tv_shijian1);
        tv_shijian2 = (TextView) findViewById(R.id.tv_shijian2);
        tv_xuqiu = (TextView) findViewById(R.id.tv_xuqiu);
        tv_beizhumingzi = (TextView) findViewById(R.id.tv_beizhumingzi);
        tv_jiehuoinfo = (TextView) findViewById(R.id.tv_jiehuoinfo);
        tv111 = (TextView) findViewById(R.id.tv111);
        tv222 = (TextView) findViewById(R.id.tv222);
        tv_zongji = (TextView) findViewById(R.id.tv_zongji);
        tv_yuan = (TextView) findViewById(R.id.tv_yuan);
        tv_jiaoyi = (TextView) findViewById(R.id.tv_jiaoyi);
        img_dizhi_t = (ImageView) findViewById(R.id.img_dizhi_t);
        img_dizhi_f = (ImageView) findViewById(R.id.img_dizhi_f);

        tv_quxiao = (TextView) findViewById(R.id.tv_quxiao);
        tv_kaishi = (TextView) findViewById(R.id.tv_kaishi);
        tv_quxiao1 = (TextView) findViewById(R.id.tv_quxiao1);
        tv_jieshu = (TextView) findViewById(R.id.tv_jieshu);
        tv_pingjia = (TextView) findViewById(R.id.tv_pingjia);
        tv_yuji_daoda = (TextView) findViewById(R.id.tv_yuji_daoda);
        tv_beizhu = (TextView) findViewById(R.id.tv_beizhu);
        linear_kaishi= (LinearLayout) findViewById(R.id.linear_kaishi);
        relative= (RelativeLayout) findViewById(R.id.relative);

        img_type = (ImageView) findViewById(R.id.img_type);
        relative1 = (RelativeLayout) findViewById(R.id.relative1);
        relative2 = (RelativeLayout) findViewById(R.id.relative2);
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        view10 = findViewById(R.id.view10);
        tv_quxiao.setOnClickListener(this);
        tv_quxiao1.setOnClickListener(this);
        tv_kaishi.setOnClickListener(this);
        tv_jieshu.setOnClickListener(this);
        tv_pingjia.setOnClickListener(this);

        //加载订单详情信息
        initOrderDetails();
        initFeiLv();

        getyeData();
        initJianceZhouJie();
        registerWXPayLocalReceiver();

    }

    private void initJianceZhouJie() {
        PileApi.instance.checkRelease()
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

                            if (body.contains("nologin")){
//                                showToast("请检查登录状态");
                            }else if (body.contains("nosign")){
                                zhoujie=false;
//                                showToast("非签约用户，不能周结");
                            }else if (body.contains("true")){
                                zhoujie=true;
//                                showToast("是签约用户，可以周结");
                            }else if (body.contains("false")){
                                zhoujie=false;
//                                showToast("是签约用户，存在未结算单子");
                            }else {
//                                showToast(body);
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

    private void initFeiLv() {
        PileApi.instance.searchfee()
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
                                showToast("暂无地址，请添加");
                            } else {
                                Gson gson = new Gson();
                                feiLvEntityList = gson.fromJson(body, new TypeToken<List<FeiLvEntity>>() {
                                }.getType());

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

    private void initEWai() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", detailsEntityList.get(0).getId() + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectSuyunOrderDetail(params)
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
                            if (body.indexOf("false") != -1 || body.length() < 6) {
//                                showToast("订单详情暂无数据");
                            } else {
                                Gson gson = new Gson();
                                eWaiEntityList = gson.fromJson(body, new TypeToken<List<EWaiEntity>>() {
                                }.getType());

                                initEwaiRecyclerView();


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

    private void initEwaiRecyclerView() {
        EWaiAdapter adapter = new EWaiAdapter(this, eWaiEntityList,detailsEntityList.get(0).getCust_orderstatus());
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
    }

    private void initOrderDetails() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchSuyunOrderDetail(data)
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
                            if (body.indexOf("false") != -1 || body.length() < 6) {
                                showToast("订单详情暂无数据");
                            } else {
                                Gson gson = new Gson();
                                detailsEntityList = gson.fromJson(body, new TypeToken<List<LogisticsOrderDetailsEntity>>() {
                                }.getType());
                                //加载额外需求信息
                                initEWai();

                                initFuZhi();

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

    private void initFuZhi() {
        tv_dingdanhao.setText(detailsEntityList.get(0).getOrderno());
        if (detailsEntityList.get(0).getCust_orderstatus() == -1) {
            tv_type.setText("已取消");
            tv_quxiao.setVisibility(View.GONE);
            linear_kaishi.setVisibility(View.GONE);
            tv_jieshu.setVisibility(View.GONE);
            tv_pingjia.setVisibility(View.GONE);


            tv_dingdanhao.setTextColor(Color.parseColor("#B9B9B9"));
            tv_type.setTextColor(Color.parseColor("#B9B9B9"));
            tv_xingming.setTextColor(Color.parseColor("#B9B9B9"));
            tv_name.setTextColor(Color.parseColor("#B9B9B9"));
            tv_lianxi.setTextColor(Color.parseColor("#B9B9B9"));
            tv_phone.setTextColor(Color.parseColor("#B9B9B9"));
            tv_huowu11.setTextColor(Color.parseColor("#B9B9B9"));
            tv_car_type.setTextColor(Color.parseColor("#B9B9B9"));
            tv_shijian.setTextColor(Color.parseColor("#B9B9B9"));
            tv_yongchetime.setTextColor(Color.parseColor("#B9B9B9"));
            tv_shijian1.setTextColor(Color.parseColor("#B9B9B9"));
            tv_yuji_daoda.setTextColor(Color.parseColor("#B9B9B9"));
            tv_shijian2.setTextColor(Color.parseColor("#B9B9B9"));
            tv_dingdantime.setTextColor(Color.parseColor("#B9B9B9"));
            tv_xuqiu.setTextColor(Color.parseColor("#B9B9B9"));
            tv_address.setTextColor(Color.parseColor("#B9B9B9"));
            tv_address1.setTextColor(Color.parseColor("#B9B9B9"));
            tv_beizhumingzi.setTextColor(Color.parseColor("#B9B9B9"));
            tv_beizhu.setTextColor(Color.parseColor("#B9B9B9"));
            tv_jiehuoinfo.setTextColor(Color.parseColor("#B9B9B9"));
            tv111.setTextColor(Color.parseColor("#B9B9B9"));
            tv_siji_name.setTextColor(Color.parseColor("#B9B9B9"));
            tv222.setTextColor(Color.parseColor("#B9B9B9"));
            tv_siji_phone.setTextColor(Color.parseColor("#B9B9B9"));
            tv_info.setTextColor(Color.parseColor("#B9B9B9"));
            tv_zongji.setTextColor(Color.parseColor("#B9B9B9"));
            tv_money.setTextColor(Color.parseColor("#B9B9B9"));
            tv_yuan.setTextColor(Color.parseColor("#B9B9B9"));
            tv_jiaoyi.setTextColor(Color.parseColor("#B9B9B9"));
            img_dizhi_t.setImageResource(R.mipmap.img_huodi_weizhi_f);
            img_dizhi_f.setImageResource(R.mipmap.img_huodi_weizhi_f);
        } else if (detailsEntityList.get(0).getCust_orderstatus() == 0) {
            tv_type.setText("等待接货");
            if (detailsEntityList.get(0).getDriver_orderstatus() == 0){
                tv_quxiao.setVisibility(View.GONE);
                linear_kaishi.setVisibility(View.VISIBLE);
                tv_jieshu.setVisibility(View.GONE);
                tv_pingjia.setVisibility(View.GONE);
            }else {
                tv_quxiao.setVisibility(View.VISIBLE);
                linear_kaishi.setVisibility(View.GONE);
                tv_jieshu.setVisibility(View.GONE);
                tv_pingjia.setVisibility(View.GONE);
            }
        } else if (detailsEntityList.get(0).getCust_orderstatus() == 1) {
            tv_type.setText("服务开始");
            tv_quxiao.setVisibility(View.GONE);
            linear_kaishi.setVisibility(View.GONE);
            tv_jieshu.setVisibility(View.VISIBLE);
            tv_pingjia.setVisibility(View.GONE);
        } else {
            tv_type.setText("已完成");
            if (detailsEntityList.get(0).getIsevaluate().equals("0")){
                tv_quxiao.setVisibility(View.GONE);
                linear_kaishi.setVisibility(View.GONE);
                tv_jieshu.setVisibility(View.GONE);
                tv_pingjia.setVisibility(View.VISIBLE);
            }else {
                tv_quxiao.setVisibility(View.GONE);
                linear_kaishi.setVisibility(View.GONE);
                tv_jieshu.setVisibility(View.GONE);
                tv_pingjia.setVisibility(View.GONE);
            }

        }

        tv_name.setText(detailsEntityList.get(0).getConsigneename());
        tv_phone.setText(detailsEntityList.get(0).getConsigneephone());
        tv_car_type.setText(detailsEntityList.get(0).getWeightofgoods());
        tv_yongchetime.setText(detailsEntityList.get(0).getPickuptime());
        tv_yuji_daoda.setText(detailsEntityList.get(0).getExpectedarrivaltime());
        tv_dingdantime.setText(detailsEntityList.get(0).getCreatetime());
        tv_address.setText(detailsEntityList.get(0).getStartaddress());
        tv_address1.setText(detailsEntityList.get(0).getEndaddress());
        if (detailsEntityList.get(0).getRemarks().equals("")){
            relative.setVisibility(View.GONE);
        }else {
            relative.setVisibility(View.VISIBLE);
            tv_beizhu.setText(detailsEntityList.get(0).getRemarks());
        }
//        tv_juli.setText(detailsEntityList.get(0).getTotal_mileage() + "公里");
        tv_money.setText(detailsEntityList.get(0).getOrdertotalprice()+"");
        tv222.setText(detailsEntityList.get(0).getCust_star()+"星");
        tv_jiaoyi.setText("交易"+detailsEntityList.get(0).getCust_num()+"笔");
        if (detailsEntityList.get(0).getDriver_name().equals("")) {
            relative1.setVisibility(View.GONE);
            view10.setVisibility(View.GONE);
            relative2.setVisibility(View.GONE);
            tv_info.setVisibility(View.VISIBLE);
        } else {
            relative1.setVisibility(View.VISIBLE);
            view10.setVisibility(View.VISIBLE);
            relative2.setVisibility(View.VISIBLE);
            tv_info.setVisibility(View.GONE);
            tv_siji_name.setText(detailsEntityList.get(0).getDriver_name());
            tv_siji_phone.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG);
            tv_siji_phone.getPaint().setAntiAlias(true);
            tv_siji_phone.setTextColor(Color.BLUE);
            tv_siji_phone.setText(detailsEntityList.get(0).getDriver_phone());
            tv_siji_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this)
                            .setTitle("提示")
                            .setMessage("拨打客服电话："+detailsEntityList.get(0).getDriver_phone())
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    testCallPhone();
                                }
                            });
                    builder.show();
                }
            });
        }

//        if (detailsEntityList.get(0).getCust_orderstatus() == -1) {
//            btn_quxiao.setVisibility(View.GONE);
//            btn_kaishi.setVisibility(View.GONE);
//            btn_jieshu.setVisibility(View.GONE);
//        } else if (detailsEntityList.get(0).getCust_orderstatus() == 0) {
//            if (detailsEntityList.get(0).getDriver_orderstatus() == 0) {
//                btn_quxiao.setVisibility(View.VISIBLE);
//                btn_kaishi.setVisibility(View.VISIBLE);
//                btn_jieshu.setVisibility(View.GONE);
//            } else {
//                btn_quxiao.setVisibility(View.VISIBLE);
//                btn_kaishi.setVisibility(View.GONE);
//                btn_jieshu.setVisibility(View.GONE);
//            }
//
//
//        } else if (detailsEntityList.get(0).getCust_orderstatus() == 1) {
//            btn_quxiao.setVisibility(View.GONE);
//            btn_kaishi.setVisibility(View.GONE);
//            btn_jieshu.setVisibility(View.VISIBLE);
//        } else {
//            btn_quxiao.setVisibility(View.GONE);
//            btn_kaishi.setVisibility(View.GONE);
//            btn_jieshu.setVisibility(View.GONE);
//        }


    }

    //打电话
    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            } else {
                callPhone("0531-88807916");
            }

        } else {
            callPhone("0531-88807916");
        }
    }
    private void callPhone(String phoneNum) {
        //直接拨号
        Uri uri = Uri.parse("tel:" + detailsEntityList.get(0).getDriver_phone());
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(LogisticsOrderDetailsActivitys.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            startActivity(intent);
        }
    }
    /**
     * 请求权限的回调方法
     *
     * @param requestCode  请求码
     * @param permissions  请求的权限
     * @param grantResults 权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(LogisticsOrderDetailsActivitys.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-88807916");
        } else {
            Toast.makeText(LogisticsOrderDetailsActivitys.this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quxiao1:
                final AlertDialog dialog1 = new AlertDialog.Builder(this).create();
                dialog1.setCancelable(false);
                dialog1.show();
                dialog1.getWindow().setContentView(R.layout.dialog_querenquxiao);
                dialog1.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
                TextView textView1= (TextView) dialog1.getWindow().findViewById(R.id.tv_feilv);
                textView1.setText("司机接单三分钟后取消，将扣您"+feiLvEntityList.get(0).getFee()*100+"%的费用");
                dialog1.getWindow().findViewById(R.id.btn_jixudingdan)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                dialog1.getWindow().findViewById(R.id.btn_quxiaodingdan)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                initQuxiao();

                                dialog1.dismiss();

                            }
                        });
                break;
            case R.id.tv_quxiao:
                final AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setCancelable(false);
                dialog.show();
                dialog.getWindow().setContentView(R.layout.dialog_querenquxiao);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
                TextView textView= (TextView) dialog.getWindow().findViewById(R.id.tv_feilv);
                textView.setText("司机接单三分钟后取消，将扣您"+feiLvEntityList.get(0).getFee()*100+"%的费用");
                dialog.getWindow().findViewById(R.id.btn_jixudingdan)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                dialog.getWindow().findViewById(R.id.btn_quxiaodingdan)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                initQuxiao();

                                dialog.dismiss();

                            }
                        });
                break;
            case R.id.tv_jieshu:

                AlertDialog.Builder builder1=new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定结束订单吗")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //结束订单
                                initJieshu();

                            }
                        });
                builder1.show();



                break;
            case R.id.tv_kaishi:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定支付订单吗")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
//                                initKaishi();
                                showpaydialog(detailsEntityList.get(0).getOrdertotalprice(), detailsEntityList.get(0).getUuid());

                            }
                        });
                builder.show();
                break;
            case R.id.tv_pingjia:
                AlertDialog.Builder dialog11 = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this);
                View view = LayoutInflater.from(LogisticsOrderDetailsActivitys.this).inflate(R.layout.dialog_pingjiadingdan, null, false);
                dialog11.setView(view);
                final AlertDialog alertDialog = dialog11.create();
                alertDialog.setCancelable(false);
                Button button = (Button) view.findViewById(R.id.btn_tijiao);
                final ImageView imageView1 = (ImageView) view.findViewById(R.id.img1);
                final ImageView imageView2 = (ImageView) view.findViewById(R.id.img2);
                final ImageView imageView3 = (ImageView) view.findViewById(R.id.img3);
                final ImageView imageView4 = (ImageView) view.findViewById(R.id.img4);
                final ImageView imageView5 = (ImageView) view.findViewById(R.id.img5);
                ed_content = (EditText) view.findViewById(R.id.ed_content);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!ed_content.getText().toString().trim().equals("")) {
                            content = ed_content.getText().toString().trim();
                        }
                        initPingjia();
                        alertDialog.dismiss();
                    }
                });
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pingjia = 1;
                        imageView1.setImageResource(R.mipmap.img_pingjia_t);
                        imageView2.setImageResource(R.mipmap.img_pingjia_f);
                        imageView3.setImageResource(R.mipmap.img_pingjia_f);
                        imageView4.setImageResource(R.mipmap.img_pingjia_f);
                        imageView5.setImageResource(R.mipmap.img_pingjia_f);
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pingjia = 2;
                        imageView1.setImageResource(R.mipmap.img_pingjia_t);
                        imageView2.setImageResource(R.mipmap.img_pingjia_t);
                        imageView3.setImageResource(R.mipmap.img_pingjia_f);
                        imageView4.setImageResource(R.mipmap.img_pingjia_f);
                        imageView5.setImageResource(R.mipmap.img_pingjia_f);
                    }
                });
                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pingjia = 3;
                        imageView1.setImageResource(R.mipmap.img_pingjia_t);
                        imageView2.setImageResource(R.mipmap.img_pingjia_t);
                        imageView3.setImageResource(R.mipmap.img_pingjia_t);
                        imageView4.setImageResource(R.mipmap.img_pingjia_f);
                        imageView5.setImageResource(R.mipmap.img_pingjia_f);
                    }
                });
                imageView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pingjia = 4;
                        imageView1.setImageResource(R.mipmap.img_pingjia_t);
                        imageView2.setImageResource(R.mipmap.img_pingjia_t);
                        imageView3.setImageResource(R.mipmap.img_pingjia_t);
                        imageView4.setImageResource(R.mipmap.img_pingjia_t);
                        imageView5.setImageResource(R.mipmap.img_pingjia_f);
                    }
                });
                imageView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pingjia = 5;
                        imageView1.setImageResource(R.mipmap.img_pingjia_t);
                        imageView2.setImageResource(R.mipmap.img_pingjia_t);
                        imageView3.setImageResource(R.mipmap.img_pingjia_t);
                        imageView4.setImageResource(R.mipmap.img_pingjia_t);
                        imageView5.setImageResource(R.mipmap.img_pingjia_t);
                    }
                });
                alertDialog.show();
                break;
        }
    }

    private void showpaydialog(final double summoney, final String orderid) {

        payalertDialog = new AlertDialog.Builder(this).create();
        payalertDialog.show();
        payalertDialog.getWindow().setContentView(R.layout.layout_pay3);
        payalertDialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams p = payalertDialog.getWindow().getAttributes();
        p.width = (int) (display.getWidth() * 0.8);
//        payalertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                if (!ispayok) {
//                    deletepay(orderid);
//                }
//            }
//        });
        payalertDialog.getWindow().setAttributes(p);
        payalertDialog.getWindow()
                .findViewById(R.id.rb1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this)
                                .setTitle("提示")
                                .setMessage("确定要支付吗")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        startPay(summoney, 0, orderid);
//                                        deletepay(orderid);
//                                        showToast("正在开发中");
                                        startPay(summoney, 0, orderid);
                                        dialog.dismiss();

                                    }
                                })
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    }
                });
        payalertDialog.getWindow()
                .findViewById(R.id.rb2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this)
                                .setTitle("提示")
                                .setMessage("确定要支付吗")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        startPay(summoney, 1, orderid);
//                                        deletepay(orderid);
//                                        showToast("正在开发中");
                                        startPay(summoney, 1, orderid);
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    }
                });
        if (detailsEntityList.get(0).getOrdertotalprice() < i) {
            LinearLayout radioButton = (LinearLayout) payalertDialog.getWindow().findViewById(R.id.rb3);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this)
                            .setTitle("提示")
                            .setMessage("确定要支付吗")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //  showToast(Float.parseFloat(tabledata.getOwner_totalprice()) + "");
                                    //orderYuEZhiFu(orderid, Float.parseFloat(tabledata.getSettheprice()));

                                    //检查支付密码
                                    initCheckPassword(orderid);


                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
            });

        } else {

            LinearLayout radioButton = (LinearLayout) payalertDialog.getWindow().findViewById(R.id.rb3);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this)
                            .setTitle("提示")
                            .setMessage("余额不足，请充值再进行支付")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder1.show();
                }
            });

        }

        TextView textView = (TextView) payalertDialog.getWindow().findViewById(R.id.tv_yue);
        textView.setText(i + "");

        payalertDialog.getWindow()
                .findViewById(R.id.rb4)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this)
                                .setTitle("提示")
                                .setMessage("确定要支付吗")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        initXianXiaZhouJie("3");
                                    }
                                })
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                });

        LinearLayout zhoujie11= (LinearLayout) payalertDialog.getWindow().findViewById(R.id.rb5);
        if (zhoujie){
            zhoujie11.setVisibility(View.VISIBLE);
        }else {
            zhoujie11.setVisibility(View.GONE);
        }
        zhoujie11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this)
                        .setTitle("提示")
                        .setMessage("确定要支付吗")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                initXianXiaZhouJie("4");
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

    }

    private void startPay(final double confirmMoney, final int payType, final String orderid) {
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

//                                                        return;

                                //支付宝支付
                                if (payType == 0) {
                                    // showToast(confirmMoney + "-" + orderid);
                                    /**
                                     * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                                     * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                                     * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                                     *
                                     * orderInfo的获取必须来自服务端；
                                     */
                                    boolean rsa2 = (Constant.RSA_PRIVATE.length() > 0);
                                    Map<String, String> params = OrderInfoUtil2_0.kuaiyunzhifu(Constant.APPID, rsa2, confirmMoney + "", orderid + "_" + AccountHelper.getUserId());
                                    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                                    String privateKey = Constant.RSA_PRIVATE;  //私钥
                                    String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                                    final String orderInfo = orderParam + "&" + sign;

                                    Runnable payRunnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            PayTask alipay = new PayTask(LogisticsOrderDetailsActivitys.this);
                                            Map<String, String> result = alipay.payV2(orderInfo, true);
                                            Log.i("msp", result.toString());
                                            Message msg = new Message();
                                            msg.what = SDK_PAY_FLAG;
                                            msg.obj = result;
                                            mHandler.sendMessage(msg);
                                        }
                                    };

                                    Thread payThread = new Thread(payRunnable);
                                    payThread.start();

                                } else {     //微信支付
//                                                                if(!isWXAppInstalledAndSupported()){
//                                                                   showToast("请安装微信客户端后重试");
//                                                                    return;
//                                                                }
                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                    map.put("wx_appid", Constant.APP_ID);
                                    map.put("wx_mch_id", Constant.MCH_ID);
                                    map.put("wx_key", Constant.API_KEY);
                                    map.put("orderNo", orderid + "_" + AccountHelper.getUserId());                //订单号
                                    map.put("orderMoney", confirmMoney * 100);//支付金额
                                    //     map.put("orderMoney",1);
//                                    map.put("notify_url", "http://www.maibat.com/maibate/shipperWeChat");
                                    map.put("notify_url", "http://www.huodiwulian.com/weixin/weixinnews");
                                    map.put("body", "商品描述");
                                    new WXPayUtils().init(LogisticsOrderDetailsActivitys.this, map)
                                            .setListener(new WXPayUtils.BackResult() {
                                                @Override
                                                public void getInfo(String prepayId, String sign) {
                                                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                                                            .with(LogisticsOrderDetailsActivitys.this) //activity instance
                                                            .setAppId(Constant.APP_ID) //wechat pay AppID
                                                            .setPartnerId(Constant.MCH_ID)//wechat pay partner id
                                                            .setPrepayId(prepayId)//pre pay id
                                                            .setNonceStr("")
                                                            .setTimeStamp("")//time stamp
                                                            .setSign(sign)//sign
                                                            .create();
                                                    //2. send the request with wechat pay
                                                    PayAPI.getInstance().sendPayRequest(wechatPayReq);
                                                    Constant.WXPAY_STARTNAME = HuoDSuyunActivity.class.getName();
                                                }
                                            });
                                }

                            } else {
                                showToast("当前用户未登录,无法获取单号");
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showToast("支付成功");
                        final AlertDialog dialog = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this).create();
                        ispayok = true;
                        payalertDialog.dismiss();
                        dialog.show();
                        dialog.getWindow().setContentView(R.layout.dialog_zhifuchenggong);
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);

                        WindowManager windowManager = getWindowManager();
                        Display display = windowManager.getDefaultDisplay();
                        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
                        p.width = (int) (display.getWidth() * 0.6);
                        dialog.getWindow().setAttributes(p);

                        TextView tv_success= (TextView) dialog.getWindow().findViewById(R.id.tv_success);
                        tv_success.setText("支付成功");
                        TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isok = false;
                                dialog.dismiss();



//                                        finish();
                            }
                        });
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                                Intent intent = new Intent(LogisticsOrderDetailsActivitys.this, LogisticsOrderDetailsActivity.class);
                                intent.putExtra("orderno",detailsEntityList.get(0).getOrderno());
                                intent.putExtra("line","1");
                                startActivity(intent);
                            }
                        });

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败");
                        // showToast(resultStatus);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    /**
     * 微信支付广播
     */
    private void registerWXPayLocalReceiver() {
        if (mManager1 == null)
            mManager1 = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("MicroMsg.SDKSample.WXPayEntryActivity");
        if (mReceiver1 == null)
            mReceiver1 = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    //showToast("广播内容：" + action);
                    if ("MicroMsg.SDKSample.WXPayEntryActivity".equals(action)) {
                        if (intent != null) {
                            int result = intent.getIntExtra("result", -1);
                            switch (result) {
                                case 0:
                                    // showToast("支付成功******");
                                    if (Constant.WXPAY_STARTNAME.equals(HuoDSuyunActivity.class.getName())) {
                                        Constant.WXPAY_STARTNAME = "";
                                        final AlertDialog dialog = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this).create();
                                        ispayok = true;
                                        payalertDialog.dismiss();
                                        dialog.show();
                                        dialog.getWindow().setContentView(R.layout.dialog_zhifuchenggong);
                                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);

                                        WindowManager windowManager = getWindowManager();
                                        Display display = windowManager.getDefaultDisplay();
                                        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
                                        p.width = (int) (display.getWidth() * 0.6);
                                        dialog.getWindow().setAttributes(p);

                                        TextView tv_success= (TextView) dialog.getWindow().findViewById(R.id.tv_success);
                                        tv_success.setText("支付成功");
                                        TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                                        textView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                isok = false;
                                                dialog.dismiss();



//                                        finish();
                                            }
                                        });
                                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {
                                                finish();
                                                Intent intent = new Intent(LogisticsOrderDetailsActivitys.this, LogisticsOrderDetailsActivity.class);
                                                intent.putExtra("orderno",detailsEntityList.get(0).getOrderno());
                                                intent.putExtra("line","1");
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                    break;
                                case -2:
                                    //     showToast("取消支付");
                                    break;
                                case -1:
                                    showToast("支付失败");
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            };
        mManager1.registerReceiver(mReceiver1, filter);
    }

    private void initXianXiaZhouJie(String fangshi) {
        HashMap<String, String> map = new HashMap<>();
        map.put("ordertype", "1");
        map.put("pay_method",fangshi);
        map.put("uuid", detailsEntityList.get(0).getUuid());
        map.put("zhifuprice", detailsEntityList.get(0).getOrdertotalprice()+"");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.shipperBalancePay_underline(data)
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
                            if (body.contains("nologin")){
                                showToast("请检查登录状态");
                            }else if (body.contains("false")){
                                showToast("支付失败，请重试");
                            }else if (body.contains("true")){
                                final AlertDialog dialog = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this).create();
                                ispayok = true;
                                payalertDialog.dismiss();
                                dialog.show();
                                dialog.getWindow().setContentView(R.layout.dialog_zhifuchenggong);
                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);

                                WindowManager windowManager = getWindowManager();
                                Display display = windowManager.getDefaultDisplay();
                                WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
                                p.width = (int) (display.getWidth() * 0.6);
                                dialog.getWindow().setAttributes(p);

                                TextView tv_success= (TextView) dialog.getWindow().findViewById(R.id.tv_success);
                                tv_success.setText("前往开始订单");
                                TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        isok = false;
                                        dialog.dismiss();



//                                        finish();
                                    }
                                });
                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                        Intent intent = new Intent(LogisticsOrderDetailsActivitys.this, LogisticsOrderDetailsActivity.class);
                                        intent.putExtra("orderno",detailsEntityList.get(0).getOrderno());
                                        intent.putExtra("line","1");
                                        startActivity(intent);
                                    }
                                });
                            }else {
                                showToast(body);
                            }

                        } catch (IOException e) {
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        System.out.println(e + "");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initCheckPassword(final String orderid) {
        PileApi.instance.checkZhiFuPassword()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("000000000000开始");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {
                            String body = responseBody.string();
                            System.out.println(body);
                            System.out.println("000000000000" + body);
                            if (body.indexOf("false") != -1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this)
                                        .setTitle("提示")
                                        .setMessage("请到：个人中心-设置-安全中心-设置支付密码中进行设置")
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.show();
                            } else {
                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this);
                                View view = LayoutInflater.from(LogisticsOrderDetailsActivitys.this).inflate(R.layout.dialog_zhifumima, null, false);
                                dialog1.setView(view);
                                alertDialog = dialog1.create();
                                alertDialog.setCancelable(false);
                                ed_password = (EditText) view.findViewById(R.id.ed_password);
                                Button button = (Button) view.findViewById(R.id.btn_queding);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ed_password.getText().toString().trim().equals("")) {
                                            Toast.makeText(LogisticsOrderDetailsActivitys.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                                        } else {
//                                            hintKeyboard();
                                            orderYuEZhiFu(alertDialog, ed_password.getText().toString().trim(), orderid, detailsEntityList.get(0).getOrdertotalprice());
//                                            alertDialog.dismiss();
                                        }

                                    }
                                });
                                Button button1 = (Button) view.findViewById(R.id.btn_quxiao);
                                button1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        hintKeyboard();
                                        alertDialog.dismiss();
                                    }
                                });

                                alertDialog.show();

//                                    Button button = (Button) view.findViewById(R.id.btn_tijiao);
//                                    ed_content = (EditText) view.findViewById(R.id.ed_content);
                            }
                        } catch (IOException e) {
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("000000000000++++" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //余额支付
    private void orderYuEZhiFu(final AlertDialog alertDialog, String password, String orderno, double totalmoney) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("ordertype", "1");
        map.put("uuid", orderno + "");
        map.put("paypassword", password);
        map.put("zhifuprice", totalmoney + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.shipperBalancePay(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

//                        try {
                        try {
                            String body = responseBody.string();
                            body = body.replace("\"", "");
                            //    showToast(body);
                            if ("false".equals(body) || "".equals(body)) {
                                showToast("支付失败");
                            } else if ("true".equals(body)) {
                                showToast("支付成功");
                                final AlertDialog dialog = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this).create();
                                ispayok = true;
                                payalertDialog.dismiss();
                                dialog.show();
                                dialog.getWindow().setContentView(R.layout.dialog_zhifuchenggong);
                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);

                                WindowManager windowManager = getWindowManager();
                                Display display = windowManager.getDefaultDisplay();
                                WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
                                p.width = (int) (display.getWidth() * 0.6);
                                dialog.getWindow().setAttributes(p);

                                TextView tv_success= (TextView) dialog.getWindow().findViewById(R.id.tv_success);
                                tv_success.setText("支付成功");
                                TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        isok = false;
                                        dialog.dismiss();



//                                        finish();
                                    }
                                });
                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                        Intent intent = new Intent(LogisticsOrderDetailsActivitys.this, LogisticsOrderDetailsActivity.class);
                                        intent.putExtra("orderno",detailsEntityList.get(0).getOrderno());
                                        intent.putExtra("line","1");
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                showToast(body);
                            }
                            hideWaitDialog();
                            //
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

                    }
                });
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

    //请求余额信息
    private void getyeData() {

        PileApi.instance.loadCustomerBalance()
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

                            if (body.indexOf("false") != -1 || body.length() < 1) {
                                showToast("获取信息失败，请重试");
                            } else {
                                body = body.substring(1, body.length() - 1);
                                i = Double.parseDouble(body);


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

    private void initKaishi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.startSuyunOrder(data)
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
                            if (body.indexOf("false") != -1 || body.length() < 6) {
                                showToast("订单开始失败");
                            } else {
                                showToast("订单已开始");
                                //加载订单详情信息
                                initOrderDetails();

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

    //结束订单
    private void initJieshu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
        map.put("order_money", detailsEntityList.get(0).getOrdertotalprice() + "");
//        map.put("owner_createtime", detailsEntityList.get(0).getOwner_createtime());
//        map.put("siji_findtime", detailsEntityList.get(0).getSiji_findtime());
        map.put("driverid", detailsEntityList.get(0).getDriver_custid() + "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.finishSuyunOrder(data)
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
                            if (body.indexOf("false") != -1 || body.length() < 6) {
                                showToast("结束订单失败");
                            } else {
                                showToast("订单结束成功");
                                initOrderDetails();

                            }
                        } catch (IOException e) {
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        System.out.println(e + "");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initPingjia() {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", detailsEntityList.get(0).getId() + "");
        map.put("driver_fraction", pingjia + "");
        map.put("note", content);
        map.put("driverid", detailsEntityList.get(0).getDriver_custid() + "");
        map.put("ordertype", line);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.evlateOrder(data)
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
                            //加载订单详情信息
                            initOrderDetails();

                            if (body.indexOf("false") != -1 || body.length() < 6) {
                                showToast("评价失败");
                            } else {
                                showToast("评价成功");

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

    //取消订单
    private void initQuxiao() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
//        map.put("owner_createtime", detailsEntityList.get(0).getOwner_createtime());
        map.put("siji_findtime", detailsEntityList.get(0).getSiji_singletime());
        map.put("order_money", detailsEntityList.get(0).getOrdertotalprice() + "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.cancelSuyunOrder(data)
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
                            if (body.indexOf("false") != -1 || body.length() < 1) {
                                showToast("订单取消失败");
                            } else if (body.indexOf("true") != -1){
                                showToast("订单取消成功");
                                final AlertDialog dialog = new AlertDialog.Builder(LogisticsOrderDetailsActivitys.this).create();
                                dialog.setCancelable(false);
                                dialog.show();
                                dialog.getWindow().setContentView(R.layout.dialog_quxiaochenggong);
                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
                                TextView textView= (TextView) dialog.getWindow().findViewById(R.id.tv_feilv1);
                                if (body.substring(1,body.length()-1).equals("trueTimeout")){
                                    textView.setVisibility(View.VISIBLE);
                                }else if (body.substring(1,body.length()-1).equals("true")){
                                    textView.setVisibility(View.INVISIBLE);
                                }
                                textView.setText("已扣除您"+feiLvEntityList.get(0).getFee()*100+"%费用");

                                dialog.getWindow().findViewById(R.id.img_close)
                                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                //加载订单详情信息
                                initOrderDetails();

                            }else {
                                Toast.makeText(LogisticsOrderDetailsActivitys.this, "111", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener!=null){
            mListener.onLocationChanged(aMapLocation);
        }
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        //将地图移动到定位点
//        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.parseDouble(116.46+""), Double.parseDouble(39.92+""))));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));


        LatLng latLng=new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng)
        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        aMap.addMarker(markerOptions);

        LatLng latLng0=new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        lngList.add(latLng0);
        LatLng latLng1=new LatLng(aMapLocation.getLatitude()+0.001, aMapLocation.getLongitude()+0.001);
        lngList.add(latLng1);
        LatLng latLng3=new LatLng(aMapLocation.getLatitude()+0.001, aMapLocation.getLongitude()-0.001);
        lngList.add(latLng3);
        LatLng latLng4=new LatLng(aMapLocation.getLatitude()-0.001, aMapLocation.getLongitude()+0.002);
        lngList.add(latLng4);
        PolylineOptions options=new PolylineOptions();
        options.add(lngList.get(0));
        options.add(lngList.get(1));
        options.add(lngList.get(2));
        options.add(lngList.get(3));
        options.width(5).geodesic(true).color(Color.GREEN);
        aMap.addPolyline(options);

    }




    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener=onLocationChangedListener;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onMyLocationChange(Location location) {

    }
}
