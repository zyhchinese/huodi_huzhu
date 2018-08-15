package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.lx.hd.R;
import com.lx.hd.adapter.DriverZhaoHuoAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.AuditOrderEntity;
import com.lx.hd.bean.DriverZhaohuoEntity;
import com.lx.hd.bean.DriverZhaohuoEntity1;
import com.lx.hd.bean.JuLiEntity;
import com.lx.hd.refresh.RecyclerRefreshLayout;
import com.lx.hd.utils.SwipeRefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.math.BigDecimal;
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

import static com.lx.hd.base.Constant.REQUEST_CODE;

public class DriverZhaoHuoActivity extends BackActivity implements AMapLocationListener, RouteSearch.OnRouteSearchListener, OnRefreshListener, OnLoadmoreListener {
    private RecyclerView act_recyclerView;
    private RelativeLayout rly_title_root;
    private TextView tv_title;
    private ImageView img_back;
    private SmartRefreshLayout swipeRefreshLayout;
    private String city;
    private List<DriverZhaohuoEntity> driverZhaohuoEntityList;
    private DriverZhaohuoEntity1 driverZhaohuoEntity1;
    private DriverZhaohuoEntity1 driverZhaohuoEntity2;
    private List<DriverZhaohuoEntity1.Rows> list = new ArrayList<>();
    private RouteSearch mRouteSearch;
    private int position;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    private double latitude;//纬度
    private double longitude;//精度
    private final int ROUTE_TYPE_DRIVE = 0;
    private DriverZhaoHuoAdapter adapter;
    private LinearLayoutManager manager;
    private double licheng;
    private int i1 = 0;
    private int page = 1;
    private int lastVisibleItem;
    private int type = 0;


    @Override
    protected int getContentView() {
        return R.layout.activity_driver_zhao_huo;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("物流找货");
        setTitleIcon(R.mipmap.img_title_xiaoche);
        city = getIntent().getStringExtra("city");
        if (city.indexOf("定位中") != -1) {
            city = "济南市";
        }
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);

        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        rly_title_root = (RelativeLayout) findViewById(R.id.rly_title_root);
        tv_title = (TextView) findViewById(R.id.tv_title);
        img_back = (ImageView) findViewById(R.id.img_back);
        swipeRefreshLayout = (SmartRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        rly_title_root.setBackgroundResource(R.mipmap.img_title_baitiao);
        tv_title.setTextColor(Color.BLACK);
        img_back.setImageResource(R.mipmap.goback_wlzh);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadmoreListener(this);
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context);//指定为经典Footer，默认是 BallPulseFooter
            }
        });
        swipeRefreshLayout.setDisableContentWhenRefresh(true);
        swipeRefreshLayout.setDisableContentWhenLoading(true);


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
    protected void onResume() {
        super.onResume();
//        initQiangDanLieBiao();
        type = 0;
        i1 = 0;
        page = 1;
        location();

    }

    private void initQiangDanLieBiao() {

        HashMap<String, String> map = new HashMap<>();
        map.put("city", city);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectLogisticsOrderList(page, 3, params)
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


                            if (body.indexOf("false") != -1 || body.length() < 10) {

                                Toast.makeText(DriverZhaoHuoActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
//                                driverZhaohuoEntityList = gson.fromJson(body, new TypeToken<List<DriverZhaohuoEntity>>() {
//                                }.getType());
                                driverZhaohuoEntity1 = gson.fromJson(body, DriverZhaohuoEntity1.class);

                                if (driverZhaohuoEntity1.getTotal()==0){
                                    Toast.makeText(DriverZhaoHuoActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
                                }

                                //加载列表
                                initRecyclerView();


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

    private void initQiangDanLieBiao1() {

        HashMap<String, String> map = new HashMap<>();
        map.put("city", city);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectLogisticsOrderList(page, 3, params)
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


                            if (body.indexOf("false") != -1 || body.length() < 10) {

                                Toast.makeText(DriverZhaoHuoActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
//                                driverZhaohuoEntityList = gson.fromJson(body, new TypeToken<List<DriverZhaohuoEntity>>() {
//                                }.getType());
                                driverZhaohuoEntity1 = gson.fromJson(body, DriverZhaohuoEntity1.class);

                                if (driverZhaohuoEntity1.getTotal()!=0){
                                    LatLonPoint start = new LatLonPoint(latitude, longitude);
                                    LatLonPoint end = new LatLonPoint(driverZhaohuoEntity1.getRows().get(i1).getLatitude(), driverZhaohuoEntity1.getRows().get(i1).getLongitude());
                                    searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);
                                }else {
                                    Toast.makeText(DriverZhaoHuoActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
                                }



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

    private void initRecyclerView() {
        adapter = new DriverZhaoHuoAdapter(this, driverZhaohuoEntity1.getRows(), latitude, longitude);
        manager = new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);

        adapter.setOnClickItem(new DriverZhaoHuoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(DriverZhaoHuoActivity.this, DriverZhaoHuoDetailsActivity.class);
                intent.putExtra("id", driverZhaohuoEntity1.getRows().get(position).getId() + "");
                intent.putExtra("licheng", driverZhaohuoEntity1.getRows().get(position).getLicheng() + "");
                startActivity(intent);
            }
        });
        adapter.setOnClickItem1(new DriverZhaoHuoAdapter.OnClickItem1() {
            @Override
            public void onClick(int position) {
                DriverZhaoHuoActivity.this.position = position;
                testCallPhone();
            }
        });


    }

    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(DriverZhaoHuoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            } else {
                callPhone("0531-80969707");
            }

        } else {
            callPhone("0531-80969707");
        }
    }

    private void callPhone(String phoneNum) {
        //直接拨号
        Uri uri = Uri.parse("tel:" + driverZhaohuoEntity1.getRows().get(position).getOwner_link_phone());
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

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

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(DriverZhaoHuoActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-80969707");
        } else {
            Toast.makeText(this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                latitude = aMapLocation.getLatitude();//获取纬度
                longitude = aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息

//                Toast.makeText(this, aMapLocation.getLatitude() + "    " + aMapLocation.getLongitude() + "", Toast.LENGTH_SHORT).show();
                initQiangDanLieBiao1();
//                for (DriverZhaohuoEntity driverZhaohuoEntity:driverZhaohuoEntityList){
////                    Toast.makeText(this, "00000000", Toast.LENGTH_SHORT).show();
//                    LatLonPoint start = new LatLonPoint(latitude, longitude);
//                    LatLonPoint end = new LatLonPoint(driverZhaohuoEntity.getLatitude(), driverZhaohuoEntity.getLongitude());
//                    searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);
//
//                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
                initQiangDanLieBiao();
//                adapter = new DriverZhaoHuoAdapter(this, driverZhaohuoEntity1.getRows(), latitude, longitude);
//                manager = new LinearLayoutManager(this);
//                act_recyclerView.setLayoutManager(manager);
//                act_recyclerView.setAdapter(adapter);
//
//                adapter.setOnClickItem(new DriverZhaoHuoAdapter.OnClickItem() {
//                    @Override
//                    public void onClick(int position) {
//                        Intent intent = new Intent(DriverZhaoHuoActivity.this, DriverZhaoHuoDetailsActivity.class);
//                        intent.putExtra("id", driverZhaohuoEntity1.getRows().get(position).getId() + "");
//                        startActivity(intent);
//                    }
//                });
//                adapter.setOnClickItem1(new DriverZhaoHuoAdapter.OnClickItem1() {
//                    @Override
//                    public void onClick(int position) {
//                        DriverZhaoHuoActivity.this.position = position;
//                        testCallPhone();
//                    }
//                });

            }
        }
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode, LatLonPoint startpoint, LatLonPoint endporint) {

        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startpoint, endporint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
//            showWaitDialog("正在加载...").setCancelable(false);
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        if (i == 1000) {
            i1++;
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    final DrivePath drivePath = driveRouteResult.getPaths()
                            .get(0);
                    double dis = drivePath.getDistance();
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                    BigDecimal b = new BigDecimal(dis / 1000);


                    licheng = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                    DriverZhaohuoEntity driverZhaohuoEntity=new DriverZhaohuoEntity();
//                    driverZhaohuoEntity.setLicheng(licheng);
//                    Toast.makeText(this, licheng + "", Toast.LENGTH_SHORT).show();
                    if (type == 0) {
                        driverZhaohuoEntity1.getRows().get(i1 - 1).setLicheng(licheng);

                        if (i1 < driverZhaohuoEntity1.getRows().size()) {

                            LatLonPoint start = new LatLonPoint(latitude, longitude);
                            LatLonPoint end = new LatLonPoint(driverZhaohuoEntity1.getRows().get(i1).getLatitude(), driverZhaohuoEntity1.getRows().get(i1).getLongitude());
                            searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);

                        }


                        if (i1 == driverZhaohuoEntity1.getRows().size()) {
                            adapter = new DriverZhaoHuoAdapter(this, driverZhaohuoEntity1.getRows(), latitude, longitude);
                            manager = new LinearLayoutManager(this);
                            act_recyclerView.setLayoutManager(manager);
                            act_recyclerView.setAdapter(adapter);

                            adapter.setOnClickItem(new DriverZhaoHuoAdapter.OnClickItem() {
                                @Override
                                public void onClick(int position) {
                                    Intent intent = new Intent(DriverZhaoHuoActivity.this, DriverZhaoHuoDetailsActivity.class);
                                    intent.putExtra("id", driverZhaohuoEntity1.getRows().get(position).getId() + "");
                                    intent.putExtra("licheng", driverZhaohuoEntity1.getRows().get(position).getLicheng() + "");
                                    startActivity(intent);
                                }
                            });
                            adapter.setOnClickItem1(new DriverZhaoHuoAdapter.OnClickItem1() {
                                @Override
                                public void onClick(int position) {
                                    DriverZhaoHuoActivity.this.position = position;
                                    testCallPhone();
                                }
                            });
                        }
                    } else if (type == 1) {
                        driverZhaohuoEntity2.getRows().get(i1 - 1).setLicheng(licheng);

                        if (i1 < driverZhaohuoEntity2.getRows().size()) {

                            LatLonPoint start = new LatLonPoint(latitude, longitude);
                            LatLonPoint end = new LatLonPoint(driverZhaohuoEntity2.getRows().get(i1).getLatitude(), driverZhaohuoEntity2.getRows().get(i1).getLongitude());
                            searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);

                        }


                        if (i1 == driverZhaohuoEntity2.getRows().size()) {
                            driverZhaohuoEntity1.getRows().addAll(driverZhaohuoEntity2.getRows());
                            adapter.notifyDataSetChanged();
                        }
                    }


                    //showToast(zlc + "");

                }
            }
        } else {
            showToast("路线信息拉取失败");
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
//        swipeRefreshLayout.finishRefresh(2000);
        type = 0;
        i1 = 0;
        page = 1;
        initQiangDanLieBiao1();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
//        swipeRefreshLayout.finishLoadmore(2000);
        type = 1;
        i1 = 0;
        page++;
        HashMap<String, String> map = new HashMap<>();
        map.put("city", city);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectLogisticsOrderList(page, 3, params)
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


                            if (body.indexOf("false") != -1 || body.length() < 10) {

                                Toast.makeText(DriverZhaoHuoActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
//                                driverZhaohuoEntityList = gson.fromJson(body, new TypeToken<List<DriverZhaohuoEntity>>() {
//                                }.getType());
                                driverZhaohuoEntity2 = gson.fromJson(body, DriverZhaohuoEntity1.class);
                                if (!driverZhaohuoEntity2.getRows().isEmpty()){
                                    LatLonPoint start = new LatLonPoint(latitude, longitude);
                                    LatLonPoint end = new LatLonPoint(driverZhaohuoEntity2.getRows().get(i1).getLatitude(), driverZhaohuoEntity2.getRows().get(i1).getLongitude());
                                    searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);
                                }else {
                                    Toast.makeText(DriverZhaoHuoActivity.this, "暂无更多数据", Toast.LENGTH_SHORT).show();
                                }


//                                DriverZhaoHuoActivity.this.driverZhaohuoEntity1.getRows().addAll(driverZhaohuoEntity2.getRows());
//                                adapter.notifyDataSetChanged();
                                if (swipeRefreshLayout.isLoading()) {
                                    swipeRefreshLayout.finishLoadmore();
                                }


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
