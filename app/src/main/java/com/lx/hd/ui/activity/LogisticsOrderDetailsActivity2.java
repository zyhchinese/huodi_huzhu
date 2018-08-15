package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.EWaiAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.EWaiEntity;
import com.lx.hd.bean.FeiLvEntity;
import com.lx.hd.bean.LogisticsOrderDetailsEntity;
import com.lx.hd.bean.LogisticsOrderDetailsEntity2;
import com.lx.hd.bean.PingYuEntity;
import com.lx.hd.bean.ZuoBiaoDianEntity;
import com.lx.hd.utils.MapContainer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.lx.hd.base.Constant.REQUEST_CODE;

public class LogisticsOrderDetailsActivity2 extends BackCommonActivity implements View.OnClickListener {

    private TextView tv_dingdanhao, tv_type, tv_name, tv_phone, tv_car_type, tv_yongchetime, tv_dingdantime,
            tv_address, tv_address1, tv_juli, tv_money, tv_siji_name, tv_siji_phone, tv_info;
    private TextView tv_quxiao,tv_kaishi,tv_quxiao1,tv_jieshu,tv_pingjia,tv_yuji_daoda,tv_beizhu;
    private TextView tv_xingming,tv_lianxi,tv_zhuangche,tv_dingdan11,tv_xuqiu11,tv111,
            tv222,tv333,tv444,tv_zongji,tv_yuan,tv_chengxing11,huowuleixing11,tv_huowuzhongliang11;
    private TextView tv_zhifufangshi,tv_fangshi;
    private TextView tv_chengxing,huowuleixing,tv_huowuzhongliang,tv_jiaoyi;
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
    private List<LogisticsOrderDetailsEntity2> detailsEntityList;
    private List<EWaiEntity> eWaiEntityList;
    private List<FeiLvEntity> feiLvEntityList;
    private List<ZuoBiaoDianEntity> zuoBiaoDianEntityList;
    private PingYuEntity pingYuEntity;
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

    private List<String> pingyulist=new ArrayList<>();
    private String xuanpingyu="";

    @Override
    protected int getContentView() {
        return R.layout.activity_logistics_order_details2;
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

        scrollView= (ScrollView) findViewById(R.id.scrollView);
        mapContainer= (MapContainer) findViewById(R.id.mapContainer);
        mapContainer.setScrollView(scrollView);
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
//                                mapView.setVisibility(View.GONE);
//                                mapContainer.setVisibility(View.GONE);
                            } else {
                                Gson gson = new Gson();
                                zuoBiaoDianEntityList = gson.fromJson(body, new TypeToken<List<ZuoBiaoDianEntity>>() {
                                }.getType());
                                String lattitude="";
                                String longitude="";
                                if (zuoBiaoDianEntityList.size()>0){
                                    mapView.setVisibility(View.VISIBLE);
                                    mapContainer.setVisibility(View.VISIBLE);
                                    lattitude = zuoBiaoDianEntityList.get((zuoBiaoDianEntityList.size() - 1) / 2).getLattitude();
                                    longitude = zuoBiaoDianEntityList.get((zuoBiaoDianEntityList.size() - 1) / 2).getLongitude();
                                }else {
//                                    mapView.setVisibility(View.GONE);
//                                    mapContainer.setVisibility(View.GONE);
                                    return;
                                }


                                //设置缩放级别
                                aMap.moveCamera(CameraUpdateFactory.zoomTo(8));
                                //将地图移动到定位点
                                //        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.parseDouble(116.46+""), Double.parseDouble(39.92+""))));
                                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.parseDouble(zuoBiaoDianEntityList.get(zuoBiaoDianEntityList.size()-1).getLattitude()), Double.parseDouble(zuoBiaoDianEntityList.get(zuoBiaoDianEntityList.size()-1).getLongitude()))));

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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
        setTitleText("货滴快运单详情");
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
        tv_chengxing = (TextView) findViewById(R.id.tv_chengxing);
        huowuleixing = (TextView) findViewById(R.id.huowuleixing);
        tv_huowuzhongliang = (TextView) findViewById(R.id.tv_huowuzhongliang);
        tv_jiaoyi = (TextView) findViewById(R.id.tv_jiaoyi);


        tv_xingming = (TextView) findViewById(R.id.tv_xingming);
        tv_lianxi = (TextView) findViewById(R.id.tv_lianxi);
        tv_zhuangche = (TextView) findViewById(R.id.tv_zhuangche);
        tv_dingdan11 = (TextView) findViewById(R.id.tv_dingdan11);
        tv_xuqiu11 = (TextView) findViewById(R.id.tv_xuqiu11);
        tv111 = (TextView) findViewById(R.id.tv111);
        tv222 = (TextView) findViewById(R.id.tv222);
        tv333 = (TextView) findViewById(R.id.tv333);
        tv444 = (TextView) findViewById(R.id.tv444);
        tv_zongji = (TextView) findViewById(R.id.tv_zongji);
        tv_yuan = (TextView) findViewById(R.id.tv_yuan);
        tv_chengxing11 = (TextView) findViewById(R.id.tv_chengxing11);
        huowuleixing11 = (TextView) findViewById(R.id.huowuleixing11);
        tv_huowuzhongliang11 = (TextView) findViewById(R.id.tv_huowuzhongliang11);
        img_dizhi_t= (ImageView) findViewById(R.id.img_dizhi_t);
        img_dizhi_f= (ImageView) findViewById(R.id.img_dizhi_f);
        tv_zhifufangshi= (TextView) findViewById(R.id.tv_zhifufangshi);
        tv_fangshi= (TextView) findViewById(R.id.tv_fangshi);

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


    }

    private void initPingYu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "0");
        map.put("custid", detailsEntityList.get(0).getDriver_custid()+"");
        final Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectEvaluationList(params)
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
                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                Gson gson1=new Gson();
                                pingYuEntity=gson1.fromJson(body,PingYuEntity.class);

                                pingyulist.clear();
                                for (PingYuEntity.ResponseBean entity:pingYuEntity.getResponse()){
                                    pingyulist.add(entity.getContent());
                                }

                            }


                        } catch (IOException e) {
                        } catch (JSONException e) {
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
        PileApi.instance.searchKuaiyunOrderDetail(data)
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
                                detailsEntityList = gson.fromJson(body, new TypeToken<List<LogisticsOrderDetailsEntity2>>() {
                                }.getType());
//                                //加载额外需求信息
//                                initEWai();

                                if (zuoBiaoDianEntityList==null||zuoBiaoDianEntityList.size()==0){
                                    //设置缩放级别
                                    aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                                    //将地图移动到定位点
                                    //        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.parseDouble(116.46+""), Double.parseDouble(39.92+""))));
                                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(detailsEntityList.get(0).getStartlatitude(), detailsEntityList.get(0).getStartlongitude())));

                                    LatLng latLng=new LatLng(detailsEntityList.get(0).getStartlatitude(), detailsEntityList.get(0).getStartlongitude());
                                    MarkerOptions markerOptions=new MarkerOptions();
                                    markerOptions.position(latLng)
                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.img_zuobiandian));
                                    markerOptions.title("暂无坐标信息");
                                    markerOptions.visible(true);
//                                aMap.addMarker(markerOptions);
                                    Marker marker=aMap.addMarker(markerOptions);
                                    marker.setInfoWindowEnable(true);
                                }

                                initFuZhi();

                                initPingYu();

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
            tv_zhuangche.setTextColor(Color.parseColor("#B9B9B9"));
            tv_car_type.setTextColor(Color.parseColor("#B9B9B9"));
            tv_dingdan11.setTextColor(Color.parseColor("#B9B9B9"));
            tv_dingdantime.setTextColor(Color.parseColor("#B9B9B9"));
            tv_xuqiu11.setTextColor(Color.parseColor("#B9B9B9"));
            tv_address.setTextColor(Color.parseColor("#B9B9B9"));
            tv_address1.setTextColor(Color.parseColor("#B9B9B9"));
            tv111.setTextColor(Color.parseColor("#B9B9B9"));
            tv_beizhu.setTextColor(Color.parseColor("#B9B9B9"));
            tv222.setTextColor(Color.parseColor("#B9B9B9"));
            tv333.setTextColor(Color.parseColor("#B9B9B9"));
            tv_siji_name.setTextColor(Color.parseColor("#B9B9B9"));
            tv444.setTextColor(Color.parseColor("#B9B9B9"));
            tv_siji_phone.setTextColor(Color.parseColor("#B9B9B9"));
            tv_info.setTextColor(Color.parseColor("#B9B9B9"));
            tv_zongji.setTextColor(Color.parseColor("#B9B9B9"));
            tv_money.setTextColor(Color.parseColor("#B9B9B9"));
            tv_yuan.setTextColor(Color.parseColor("#B9B9B9"));
            tv_chengxing.setTextColor(Color.parseColor("#B9B9B9"));
            tv_chengxing11.setTextColor(Color.parseColor("#B9B9B9"));
            huowuleixing11.setTextColor(Color.parseColor("#B9B9B9"));
            huowuleixing.setTextColor(Color.parseColor("#B9B9B9"));
            tv_huowuzhongliang11.setTextColor(Color.parseColor("#B9B9B9"));
            tv_huowuzhongliang.setTextColor(Color.parseColor("#B9B9B9"));
            tv_jiaoyi.setTextColor(Color.parseColor("#B9B9B9"));
            tv_zhifufangshi.setTextColor(Color.parseColor("#B9B9B9"));
            tv_fangshi.setTextColor(Color.parseColor("#B9B9B9"));
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

        tv_name.setText(detailsEntityList.get(0).getContactname());
        tv_phone.setText(detailsEntityList.get(0).getContactphone());
        tv_car_type.setText(detailsEntityList.get(0).getShipmenttime());
//        tv_yongchetime.setText(detailsEntityList.get(0).getPickuptime());
//        tv_yuji_daoda.setText(detailsEntityList.get(0).getExpectedarrivaltime());
        tv_dingdantime.setText(detailsEntityList.get(0).getCreatetime());
        tv_address.setText(detailsEntityList.get(0).getStartaddress());
        tv_address1.setText(detailsEntityList.get(0).getEndaddress());
        tv_chengxing.setText(detailsEntityList.get(0).getCartypenames());
        huowuleixing.setText(detailsEntityList.get(0).getCargotypenames());
        if (detailsEntityList.get(0).getWeight()==0){
            tv_huowuzhongliang.setText(detailsEntityList.get(0).getVolume()+"件");
        }else if (detailsEntityList.get(0).getVolume()==0){
            tv_huowuzhongliang.setText(detailsEntityList.get(0).getWeight()+"kg");

        }else {
            tv_huowuzhongliang.setText(detailsEntityList.get(0).getWeight()+"kg/"+detailsEntityList.get(0).getVolume()+"件");
        }

        if (detailsEntityList.get(0).getRemarks().equals("")){
            relative.setVisibility(View.GONE);
        }else {
            relative.setVisibility(View.VISIBLE);
            tv_beizhu.setText(detailsEntityList.get(0).getRemarks());
        }
//        tv_juli.setText(detailsEntityList.get(0).getTotal_mileage() + "公里");
        tv_money.setText(detailsEntityList.get(0).getSettheprice()+"");
        tv444.setText(detailsEntityList.get(0).getCust_star()+"星");
        tv_jiaoyi.setText("交易"+detailsEntityList.get(0).getCust_num()+"笔");
        if (detailsEntityList.get(0).getPaytype().equals("0")){
            tv_fangshi.setText("支付宝");
        }else if (detailsEntityList.get(0).getPaytype().equals("1")){
            tv_fangshi.setText("微信");
        }else if (detailsEntityList.get(0).getPaytype().equals("2")){
            tv_fangshi.setText("余额");
        }else if (detailsEntityList.get(0).getPaytype().equals("3")){
            tv_fangshi.setText("线下");
        }else if (detailsEntityList.get(0).getPaytype().equals("4")){
            tv_fangshi.setText("签约用户");
        }else if (detailsEntityList.get(0).getPaytype().equals("20")){
            tv_fangshi.setText("未支付");
        }
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
                    AlertDialog.Builder builder=new AlertDialog.Builder(LogisticsOrderDetailsActivity2.this)
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
        if (ActivityCompat.checkSelfPermission(LogisticsOrderDetailsActivity2.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

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

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(LogisticsOrderDetailsActivity2.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-88807916");
        } else {
            Toast.makeText(LogisticsOrderDetailsActivity2.this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
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
                        .setMessage("确定开始订单吗")
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
                                initKaishi();
                            }
                        });
                builder.show();
                break;
            case R.id.tv_pingjia:
                AlertDialog.Builder dialog11 = new AlertDialog.Builder(LogisticsOrderDetailsActivity2.this);
                View view = LayoutInflater.from(LogisticsOrderDetailsActivity2.this).inflate(R.layout.dialog_pingjiadingdan, null, false);
                dialog11.setView(view);
                final AlertDialog alertDialog = dialog11.create();
                alertDialog.setCancelable(false);
                final TagFlowLayout tagFlow= (TagFlowLayout) view.findViewById(R.id.tagFlow);
                tagFlow.setAdapter(new TagAdapter<String>(pingyulist) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView tv_name= (TextView) LayoutInflater.from(LogisticsOrderDetailsActivity2.this).inflate(R.layout.pingyu_item,tagFlow,false);
                        tv_name.setText(pingyulist.get(position)+"("+pingYuEntity.getResponse().get(position).getUsenumber()+")");
                        return tv_name;
                    }

                });
                tagFlow.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        List<String> list=new ArrayList<String>();
                        if (selectPosSet.size()!=0){
                            for (int xuanzhong:selectPosSet){
                                list.add(pingYuEntity.getResponse().get(xuanzhong).getId()+"");

                            }
                            xuanpingyu="";
                            for (int i = 0; i < list.size(); i++) {
                                xuanpingyu=xuanpingyu+list.get(i)+",";
                            }
                        }
                    }
                });

                Button button = (Button) view.findViewById(R.id.btn_tijiao);
                Button btn_quxiao = (Button) view.findViewById(R.id.btn_quxiao);
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
                btn_quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

    private void initKaishi() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.startKuaiyunOrder(data)
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
        map.put("order_money", detailsEntityList.get(0).getSettheprice() + "");
//        map.put("owner_createtime", detailsEntityList.get(0).getOwner_createtime());
//        map.put("siji_findtime", detailsEntityList.get(0).getSiji_findtime());
        map.put("driverid", detailsEntityList.get(0).getDriver_custid() + "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.finishKuaiyunOrder(data)
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
        if (!xuanpingyu.equals("")){
            xuanpingyu=xuanpingyu.substring(0,xuanpingyu.length()-1);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", detailsEntityList.get(0).getId() + "");
        map.put("driver_fraction", pingjia + "");
        map.put("note", content);
        map.put("driverid", detailsEntityList.get(0).getDriver_custid() + "");
        map.put("ordertype", line);
        map.put("evalute_type", "0");
        map.put("selevalueids", xuanpingyu);
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
        map.put("order_money", detailsEntityList.get(0).getSettheprice() + "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.cancelKuaiyunOrder(data)
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
                                final AlertDialog dialog = new AlertDialog.Builder(LogisticsOrderDetailsActivity2.this).create();
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
//                                //加载订单详情信息
//                                initOrderDetails();

                            }else {
                                Toast.makeText(LogisticsOrderDetailsActivity2.this, "111", Toast.LENGTH_SHORT).show();
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
