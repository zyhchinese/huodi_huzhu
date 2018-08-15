package com.lx.hd.ui.activity;
/*
搬家
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.autonavi.ae.route.model.GeoPoint;
import com.autonavi.ae.route.route.Route;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.adapter.ViewPageAdapter;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.CarPP;
import com.lx.hd.bean.DeliverGoodsCarDataBean;
import com.lx.hd.bean.DeliverGoodsUploadBean;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.utils.map.AMapUtil;
import com.lx.hd.widgets.wheelview.ChangeDatePopwindow2;
import com.lx.hd.widgets.wheelview.ChangeDatePopwindow3;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class DeliverGoodsActivity extends BackActivity implements RouteSearch.OnRouteSearchListener {
    private ImageView img_icon;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView qd, zd, car_load, car_size, car_volume, starting_price, submit, jgmx;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<ImageView> list_view;
    private ViewPageAdapter adpter;
    private ImageView syy, xyy, goback_wlzh;
    private String yydate = "";
    private RelativeLayout yhj_layout;
    private LinearLayout yuyue;
    private RelativeLayout main;
    private int page = 0;//当前viewpage的页码缓存
    private int isok = 0; //是否已经选择了起点终点
    private List<DeliverGoodsCarDataBean> CarPPListdata;
    private DeliverGoodsUploadBean tabledata; //表单数据
    private double zlc = 0.0;
    private String ctry = "";
    private RouteSearch mRouteSearch;
    private final int ROUTE_TYPE_BUS = 1;
    private final int ROUTE_TYPE_DRIVE = 2;
    private final int ROUTE_TYPE_WALK = 3;
    private final int ROUTE_TYPE_CROSSTOWN = 4;
    private boolean flag = false;
    private boolean zhuangtai=false;
    private TextView tv_juli11;

    @Override
    protected int getContentView() {
        return R.layout.activity_deliver_goods;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        img_icon= (ImageView) findViewById(R.id.img_icon);
        img_icon.setVisibility(View.GONE);
        tabledata = new DeliverGoodsUploadBean();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.mtablayout);
        car_load = (TextView) findViewById(R.id.car_load);
        yhj_layout = (RelativeLayout) findViewById(R.id.yhj_layout);
        submit = (TextView) findViewById(R.id.submit);
        car_size = (TextView) findViewById(R.id.car_size);
        starting_price = (TextView) findViewById(R.id.starting_price);
        car_volume = (TextView) findViewById(R.id.car_volume);
        syy = (ImageView) findViewById(R.id.syy);
        xyy = (ImageView) findViewById(R.id.xyy);
        goback_wlzh = (ImageView) findViewById(R.id.img_back);
        qd = (TextView) findViewById(R.id.qd);
        zd = (TextView) findViewById(R.id.zd);
        jgmx = (TextView) findViewById(R.id.jgmx);
        tv_juli11 = (TextView) findViewById(R.id.tv_juli11);
        yuyue = (LinearLayout) findViewById(R.id.yuyue);
        main = (RelativeLayout) findViewById(R.id.main);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        yydate = format.format(new Date());
        ctry = getIntent().getStringExtra("crty");
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
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
        yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isok == 1) {
                    ChangeDatePopwindow3 mChangeBirthDialog = new ChangeDatePopwindow3(DeliverGoodsActivity.this);
                    mChangeBirthDialog.showAtLocation(main, Gravity.BOTTOM, 0, 0);
                    mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow3.OnBirthListener() {

                        @Override
                        public void onClick(String year, String month, String day, String day1) {
                            // TODO Auto-generated method stub
                            yydate = year + " " + day1.replace("点", "") + ":" + day.replace("分", "");
                            tabledata.setOwner_sendtime(yydate);
                            tabledata.setOwner_cartype_id(CarPPListdata.get(page).getCar_id() + "");
                            tabledata.setOwner_starting_price(CarPPListdata.get(page).getStarting_price() + "");
                            tabledata.setOwner_mileage_price(CarPPListdata.get(page).getMileage_price() + "");
                            Intent it1 = new Intent(DeliverGoodsActivity.this, LogisticsConfirmationOrderActivity.class);
                            it1.putExtra("tabledata", new Gson().toJson(tabledata));
                            it1.putExtra("data", new Gson().toJson(CarPPListdata.get(page)));
                            it1.putExtra("zjg", tabledata.getOwner_totalprice());
                            it1.putExtra("zlc", tabledata.getTotal_mileage());
                            it1.putExtra("cs", ctry);
                            startActivity(it1);
                            //  showToast(yydate);
                        }
                    });

                } else if (isok == 0) {
                    showToast("请选择地址");
                } else if (isok == 2) {
                    showToast("起点和目的地选择不能一致");
                }
            }
        });
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取问题
                String question = "qidian";
                //包装数据
                Bundle bundle = new Bundle();
                bundle.putString("question", question);
                bundle.putString("x", tabledata.getLatitude());
                bundle.putString("y", tabledata.getLongitude());
                Intent intent = new Intent(DeliverGoodsActivity.this, DeliverMapActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 001);
            }
        });
        zd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取问题
                String question = "zhongdian";
                //包装数据
                Bundle bundle = new Bundle();
                bundle.putString("question", question);
                bundle.putString("x", tabledata.getSend_latitude());
                bundle.putString("y", tabledata.getSend_longitude());
                Intent intent = new Intent(DeliverGoodsActivity.this, DeliverMapActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 001);
            }
        });
        //提交已有数据到下一页继续拼装
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isok == 1) {
                    tabledata.setOwner_sendtime(yydate);
                    Intent it1 = new Intent(DeliverGoodsActivity.this, LogisticsConfirmationOrderActivity.class);
                    it1.putExtra("tabledata", new Gson().toJson(tabledata));
                    it1.putExtra("data", new Gson().toJson(CarPPListdata.get(page)));
                    it1.putExtra("zjg", tabledata.getOwner_totalprice());
                    it1.putExtra("zlc", tabledata.getTotal_mileage());
                    it1.putExtra("cs", ctry);
                    startActivity(it1);
                } else if (isok == 0) {
                    showToast("请选择地址");
                } else if (isok == 2) {
                    showToast("起点和目的地选择不能一致");
                }

            }
        });
        jgmx.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        zhuangtai=true;
                                        Intent intent1 = new Intent(DeliverGoodsActivity.this, DeliverPriceDetailActivity.class);
                                        intent1.putExtra("data", new Gson().toJson(CarPPListdata.get(page)));
                                        intent1.putExtra("zjg", tabledata.getOwner_totalprice());
                                        intent1.putExtra("zlc", tabledata.getTotal_mileage());
                                        intent1.putExtra("cs", ctry);
                                        startActivity(intent1);
                                    }
                                }

        );
        goback_wlzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为系统默认模式
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
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
                tabledata.setOwner_cartype_id(CarPPListdata.get(arg0).getCar_id() + "");
                tabledata.setOwner_starting_price(CarPPListdata.get(arg0).getStarting_price() + "");
                tabledata.setOwner_mileage_price(CarPPListdata.get(arg0).getMileage_price() + "");
                page = arg0;
                if (isok == 1) {
                    double zjg = 0.0;
                    double zlc1 = 0.0;
                    if (zlc > CarPPListdata.get(page).getStarting_mileage()) {
                        zlc1 = zlc - CarPPListdata.get(page).getStarting_mileage();
                        zjg = CarPPListdata.get(page).getStarting_price() + (zlc1 * CarPPListdata.get(page).getMileage_price());
                        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                        tabledata.setOwner_totalprice(df.format(zjg));
                        starting_price.setText(df.format(zjg));
                    } else {
                        starting_price.setText(CarPPListdata.get(page).getStarting_price() + "");
                        tabledata.setOwner_totalprice(CarPPListdata.get(page).getStarting_price() + "");
                    }

                }
            }
        });
        mViewPager.setCurrentItem(0);

        searchCarType();
        initJianceZhouJie();
        initLogin();
    }

    //检测是否是签约用户
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

//                                showToast("非签约用户，不能周结");
                            }else if (body.contains("true")){

//                                showToast("是签约用户，可以周结");
                            }else if (body.contains("false")){
                                AlertDialog.Builder builder=new AlertDialog.Builder(DeliverGoodsActivity.this)
                                        .setTitle("提示")
                                        .setMessage("不能发布订单，上一个周期存在未周结完的单子")
                                        .setCancelable(false)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                builder.show();
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

    private void updatecardata(List<DeliverGoodsCarDataBean> CarPPList) {
        mTabLayout.removeAllTabs();
        mTitleList.clear();
        for (DeliverGoodsCarDataBean bean : CarPPList) {
            mTabLayout.addTab(mTabLayout.newTab().setText(bean.getCar_type()));
            mTitleList.add(bean.getCar_type());
            View view = LayoutInflater.from(this).inflate(R.layout.item_deliver_image, null);
            ImageView txt_num = (ImageView) view.findViewById(R.id.car_images);
            Glide.with(DeliverGoodsActivity.this).load(Constant.BASE_URL + bean.getFolder() + bean.getAutoname()).into(txt_num);
            list_view.add(txt_num);
        }
        adpter = new ViewPageAdapter(list_view, mTitleList);
        mViewPager.setAdapter(adpter);
        adpter.notifyDataSetChanged();
        car_load.setText(CarPPListdata.get(page).getCar_load());
        car_size.setText(CarPPListdata.get(page).getCar_size());
        car_volume.setText(CarPPListdata.get(page).getCar_volume());
        tabledata.setOwner_cartype_id(CarPPListdata.get(page).getCar_id() + "");
        tabledata.setOwner_starting_price(CarPPListdata.get(page).getStarting_price() + "");
        tabledata.setOwner_mileage_price(CarPPListdata.get(page).getMileage_price() + "");
    }

    private void searchCarType() {
        showWaitDialog("正在刷新信息...");
        HashMap<String, String> map = new HashMap<>();
        map.put("cityname", ctry);
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
                            if (body.length() < 10) {
                                //   if(body.equals("\"false\""))
                                showNormalDialog("提示", "该城市暂无发货车辆信息，请重新选择城市", true);

                            } else {
                                Gson gson = new Gson();
                                List<DeliverGoodsCarDataBean> CarPPList = gson.fromJson(body, new TypeToken<List<DeliverGoodsCarDataBean>>() {
                                }.getType());
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

    @Override
    protected void onResume() {
        super.onResume();
//        if (flag) {
//            if (!Constant.city.equals("")) {
//                ctry = Constant.city;
//                searchCarType();
//            }
//            flag = false;
//        }
    }

    /**
     * activity回调
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 001 && resultCode == 002) {
            qd.setText(data.getStringExtra("myresuly"));
            tabledata.setLatitude(data.getStringExtra("x"));
            tabledata.setLongitude(data.getStringExtra("y"));
            tabledata.setOwner_address(data.getStringExtra("myresuly"));
        } else if (requestCode == 001 && resultCode == 003) {
            zd.setText(data.getStringExtra("myresuly"));
            tabledata.setOwner_send_address(data.getStringExtra("myresuly"));
            tabledata.setSend_longitude(data.getStringExtra("y"));
            tabledata.setSend_latitude(data.getStringExtra("x"));
        }

        if (!qd.getText().toString().equals("按此输入起点") && !zd.getText().toString().equals("按此输入目的地") && !qd.getText().toString().equals("") && !zd.getText().toString().equals("")) {
            if (qd.getText().toString().trim().equals(zd.getText().toString().trim())) {
                isok = 2;
                yhj_layout.setVisibility(View.INVISIBLE);
                return;
            }
            yhj_layout.setVisibility(View.VISIBLE);
            isok = 1;
            LatLonPoint start = new LatLonPoint(Double.parseDouble(tabledata.getLatitude()), Double.parseDouble(tabledata.getLongitude()));
            LatLonPoint end = new LatLonPoint(Double.parseDouble(tabledata.getSend_latitude()), Double.parseDouble(tabledata.getSend_longitude()));
            searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);
            //  zlc = AMapUtils.calculateLineDistance(start, end);
        } else {
            isok = 0;
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
            showWaitDialog("正在加载...").setCancelable(false);
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    //驾车路线规划回调
    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        hideWaitDialog();
        if (i == 1000) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    final DrivePath drivePath = driveRouteResult.getPaths()
                            .get(0);
                    double dis = drivePath.getDistance();
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                    BigDecimal b = new BigDecimal(dis / 1000);
                    zlc = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    //showToast(zlc + "");
                    tv_juli11.setText("起点距终点:"+zlc+"km");
                    tabledata.setTotal_mileage(zlc + "");
                    double zjg = 0.0;
                    double zlc1 = 0.0;
                    if (zlc > CarPPListdata.get(page).getStarting_mileage()) {
                        zlc1 = zlc - CarPPListdata.get(page).getStarting_mileage();
                        zjg = CarPPListdata.get(page).getStarting_price() + (zlc1 * CarPPListdata.get(page).getMileage_price());
                        tabledata.setOwner_totalprice(df.format(zjg));
                        starting_price.setText(df.format(zjg));
                    } else {
                        starting_price.setText(CarPPListdata.get(page).getStarting_price() + "");
                        tabledata.setOwner_totalprice(CarPPListdata.get(page).getStarting_price() + "");
                    }
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

//                                Intent intent1 = new Intent(DeliverGoodsActivity.this, LoginActivity.class);
//                                intent1.putExtra("line", 1);
////                                startActivity(intent1);
//                                startActivityForResult(intent1, 8);
                            } else {
                                DialogHelper.getConfirmDialog(DeliverGoodsActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(DeliverGoodsActivity.this, LoginActivity.class));
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

    }

    private void showNormalDialog(String title, String Message, boolean isfp) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(DeliverGoodsActivity.this);
        normalDialog.setTitle(title);
        normalDialog.setMessage(Message);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        flag = true;
                        Intent intent = new Intent(DeliverGoodsActivity.this, ProvinceActivity.class);
                        intent.putExtra("type", 2);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                });
//        normalDialog.setNegativeButton("关闭",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //...To-do
//                    }
//                });
        // 显示
        if (isfp) {

            normalDialog.setCancelable(false);

        } else {
            normalDialog.setCancelable(true);
        }
        normalDialog.show();
    }

}

