package com.lx.hd.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.AccountHelper;
import com.lx.hd.alipay.OrderInfoUtil2_0;
import com.lx.hd.alipay.PayResult;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.AdditionalDemandListBean;
import com.lx.hd.bean.AdditionalDemandProListBean;
import com.lx.hd.bean.cxbean;
import com.lx.hd.bean.huowuzhongliangBean;
import com.lx.hd.bean.selectAllAreaBean;
import com.lx.hd.bean.suyundatatable;
import com.lx.hd.widgets.duoxuandialog.ShuJuEntity;
import com.lx.hd.widgets.wheelview.ChangeDatePopwindow3;
import com.lx.paylib.PayAPI;
import com.lx.paylib.WechatPayReq;
import com.lx.paylib.wxutils.WXPayUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
* 速运
* */
public class HuoDSuyunActivity extends BaseActivity implements RouteSearch.OnRouteSearchListener {
    private ImageView imageView6;
    private ImageView go_back;
    private TextView tv_chengshi;
    private EditText ed_password;
    private boolean isok = false;
    private RelativeLayout qhsj, qd, zd, yjddsj, hwzl, ewxq;
    private RelativeLayout main;
    private TextView submit, ordertotalprice;
    private TextView qhtimetext, qdtext, zdtext, text_yjddsj, hwzl_text, ewxq_text, tv_shoufei;
    protected LocalBroadcastManager mManager1;
    private ArrayList<AdditionalDemandProListBean> datas = new ArrayList<AdditionalDemandProListBean>();//额外需求
    private suyundatatable tabledata = new suyundatatable();
    private AlertDialog payalertDialog;
    private EditText consigneename, consigneephone, floorhousenumber, remarks;
    private BroadcastReceiver mReceiver1;
    private String cs = "";
    private boolean ispayok = false;
    private double i;
    private static final int SDK_PAY_FLAG = 1;
    private AlertDialog alertDialog;
    private ArrayList<huowuzhongliangBean> temp;
    private Double jiage = 0.0;
    private String custname;
    private String custphone;
    private TextView tv_ssx1, tv_ssx2;
    private EditText consigneephone1;
    private final int ROUTE_TYPE_DRIVE = 2;
    private RouteSearch mRouteSearch;
    private TextView tv_juli11;

    @Override
    protected int getContentView() {
        return R.layout.activity_huodisuyun;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        SharedPreferences sp = getSharedPreferences("userxinxi", Context.MODE_PRIVATE);
        custname = sp.getString("custname", "");
        custphone = sp.getString("custphone", "");
        consigneename = (EditText) findViewById(R.id.consigneename);
        consigneephone = (EditText) findViewById(R.id.consigneephone);
        consigneephone1 = (EditText) findViewById(R.id.consigneephone1);
        floorhousenumber = (EditText) findViewById(R.id.floorhousenumber);
        remarks = (EditText) findViewById(R.id.remarks);
        ordertotalprice = (TextView) findViewById(R.id.ordertotalprice);
        qhsj = (RelativeLayout) findViewById(R.id.qhsj);
        imageView6 = (ImageView) findViewById(R.id.imageView6);
        go_back = (ImageView) findViewById(R.id.go_back);
        qhtimetext = (TextView) findViewById(R.id.qhtimetext);
        main = (RelativeLayout) findViewById(R.id.main);
        ewxq = (RelativeLayout) findViewById(R.id.ewxq);
        hwzl = (RelativeLayout) findViewById(R.id.hwzl);
        hwzl_text = (TextView) findViewById(R.id.hwzl_text);
        submit = (TextView) findViewById(R.id.submit);
        ewxq_text = (TextView) findViewById(R.id.ewxq_text);
        qd = (RelativeLayout) findViewById(R.id.qd);
        zd = (RelativeLayout) findViewById(R.id.zd);
        yjddsj = (RelativeLayout) findViewById(R.id.yjddsj);
        text_yjddsj = (TextView) findViewById(R.id.text_yjddsj);
        qdtext = (TextView) findViewById(R.id.qdtext);
        zdtext = (TextView) findViewById(R.id.zdtext);
        tv_ssx1 = (TextView) findViewById(R.id.tv_ssx1);
        tv_ssx2 = (TextView) findViewById(R.id.tv_ssx2);
        tv_juli11 = (TextView) findViewById(R.id.tv_juli11);
        cs = getIntent().getStringExtra("cs");
        tv_shoufei = (TextView) findViewById(R.id.tv_shoufei);
        tv_chengshi = (TextView) findViewById(R.id.tv_chengshi);
//        consigneename.setText(custname);
//        consigneephone.setText(custphone);
        consigneephone1.setText(custphone);
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        tv_chengshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuoDSuyunActivity.this, ProvinceActivity.class);
                startActivity(intent);
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent1 = new Intent(HuoDSuyunActivity.this, DeliverGoodsActivity.class);
                Intent1.putExtra("crty", tv_chengshi.getText().toString());
                startActivity(Intent1);
            }
        });

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        qhtimetext.setText(str);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        qhsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeDatePopwindow3 mChangeBirthDialog = new ChangeDatePopwindow3(HuoDSuyunActivity.this);
                mChangeBirthDialog.showAtLocation(main, Gravity.BOTTOM, 0, 0);
                mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow3.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day, String day1) {
                        // TODO Auto-generated method stub
                        String yydate = year + " " + day1.replace("点", "") + ":" + day.replace("分", "");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//年-月-日 时-分-秒
                        try {
                            Date date1 = dateFormat.parse(yydate);
                            Date date2 = dateFormat.parse(qhtimetext.getText().toString());//开始时间
                            if (date1.getTime() <= date2.getTime()) {
                                showToast("选择时间需大于当前时间");
                            } else {
                                qhtimetext.setText(yydate);
                                text_yjddsj.setText("");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //  showToast(yydate);
                    }
                });
            }
        });
        ewxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("cs", cs);
                bundle.putString("data", new Gson().toJson(datas));
                bundle.putString("type", "0");
                Intent intent = new Intent(HuoDSuyunActivity.this, AdditionalDemandActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 001);
            }
        });
        yjddsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeDatePopwindow3 mChangeBirthDialog = new ChangeDatePopwindow3(HuoDSuyunActivity.this);
                mChangeBirthDialog.showAtLocation(main, Gravity.BOTTOM, 0, 0);
                mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow3.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day, String day1) {
                        // TODO Auto-generated method stub
                        String yydate = year + " " + day1.replace("点", "") + ":" + day.replace("分", "");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//年-月-日 时-分-秒
                        try {
                            Date date1 = dateFormat.parse(yydate);
                            Date date2 = dateFormat.parse(qhtimetext.getText().toString());//开始时间
                            if (date1.getTime() <= date2.getTime()) {
                                showToast("选择时间需大于取货时间");
                            } else {
                                text_yjddsj.setText(yydate);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //  showToast(yydate);
                    }
                });
            }
        });
        hwzl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gethwzl();
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
                bundle.putString("x", tabledata.getStartlatitude());
                bundle.putString("y", tabledata.getStartlongitude());
                bundle.putString("type", "suyun");
                Intent intent = new Intent(HuoDSuyunActivity.this, DeliverMapActivity.class);
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
                bundle.putString("type", "suyun");
                bundle.putString("question", question);
                bundle.putString("x", tabledata.getEndlatitude());
                bundle.putString("y", tabledata.getEndlongitude());
                Intent intent = new Intent(HuoDSuyunActivity.this, DeliverMapActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 001);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (tabledata.getHuodi_suyun_detailsList().equals("")){
//                    searchCarServices();
//                }else {
                if (qdtext.getText().toString().equals("请选择起点")) {
                    Toast.makeText(HuoDSuyunActivity.this, "请选择起点", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (zdtext.getText().toString().equals("请选择终点")) {
                    Toast.makeText(HuoDSuyunActivity.this, "请选择终点", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (hwzl_text.getText().toString().equals("请选择重量")) {
                    Toast.makeText(HuoDSuyunActivity.this, "请选择重量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (text_yjddsj.getText().toString().equals("请选择预计到达时间")) {
                    Toast.makeText(HuoDSuyunActivity.this, "请选择预计到达时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (consigneename.getText().toString().trim().equals("")) {
                    Toast.makeText(HuoDSuyunActivity.this, "请填写收货人姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (consigneephone.getText().toString().trim().equals("")) {
                    Toast.makeText(HuoDSuyunActivity.this, "请填写收货人电话", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (consigneephone.getText().toString().trim().length()!=11){
                    Toast.makeText(HuoDSuyunActivity.this, "收货人电话格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (consigneephone1.getText().toString().trim().equals("")) {
                    Toast.makeText(HuoDSuyunActivity.this, "请填写发货人电话", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (consigneephone1.getText().toString().trim().length()!=11){
                    Toast.makeText(HuoDSuyunActivity.this, "发货人电话格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (qdtext.getText().toString().equals(zdtext.getText().toString())) {
                    Toast.makeText(HuoDSuyunActivity.this, "起点和目的地址选择不能一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                tabledata.setConsigneename(consigneename.getText().toString());
                tabledata.setConsigneephone(consigneephone.getText().toString());
                tabledata.setConsignorphone(consigneephone1.getText().toString());
                tabledata.setFloorhousenumber(floorhousenumber.getText().toString());
                tabledata.setRemarks(remarks.getText().toString());
                tabledata.setPickuptime(qhtimetext.getText().toString());
                tabledata.setOrdertotalprice(ordertotalprice.getText().toString()); //总价需做计算处理，请与苹果端确认计算方法
                tabledata.setWeightofgoods(hwzl_text.getText().toString());
                tabledata.setExpectedarrivaltime(text_yjddsj.getText().toString());

                AlertDialog.Builder builder=new AlertDialog.Builder(HuoDSuyunActivity.this)
                        .setTitle("提示")
                        .setMessage("确认发布订单？")
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

                                if (datas.size() > 0) {
                                    upload();
                                } else {
                                    searchCarServices();
                                }

                            }
                        });
                builder.show();

//                }


            }
        });

        tv_shoufei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收费标准
                Intent intent = new Intent(HuoDSuyunActivity.this, SuYunShouFeiActivity.class);
                startActivity(intent);
            }
        });
        getyeData();
        initJianceZhouJie();
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
                                AlertDialog.Builder builder=new AlertDialog.Builder(HuoDSuyunActivity.this)
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
    private boolean phone(String mobiles){
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public void gethwzl() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "0");
        Gson gson3 = new Gson();
        String params = gson3.toJson(map);
        PileApi.instance.searchWeightOfGoods(params)
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
                            if (body == null || "null".equals(body) || "".equals(body)) {
                                Toast.makeText(HuoDSuyunActivity.this, "暂无货物重量", Toast.LENGTH_LONG).show();
                                return;
                            }
                            temp = new Gson().fromJson(body, new TypeToken<ArrayList<huowuzhongliangBean>>() {
                            }.getType());
                            if (temp.size() == 0) {
                                Toast.makeText(HuoDSuyunActivity.this, "暂无货物重量", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ArrayList<ShuJuEntity> datas = new ArrayList<ShuJuEntity>();
                            for (huowuzhongliangBean data : temp) {
                                datas.add(new ShuJuEntity(Integer.parseInt(data.getId()), data.getTimename(), false));
                            }

                            showhwzlDialog(hwzl_text, datas);
                            //    showdialog((TextView) v, temp, type);
                            //  showDuoXuanDiaLog(datas, false, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(HuoDSuyunActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void showhwzlDialog(final TextView name, final ArrayList<ShuJuEntity> temp) {
        //弹框
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(HuoDSuyunActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                name.setText(temp.get(options1).getName());
                ordertotalprice.setText(HuoDSuyunActivity.this.temp.get(options1).getDuring_time());
                jiage = Double.parseDouble(ordertotalprice.getText().toString());
            }
        })

                .setSubCalSize(18)//确定和取消文字大小
                .setSubmitColor(Color.parseColor("#333333"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#333333"))//取消按钮文字颜色
                .setContentTextSize(18)//滚轮文字大小
                .setTextColorCenter(Color.parseColor("#000000"))//设置选中项的颜色
                .setDividerColor(Color.parseColor("#ffffff"))
                .setTitleBgColor(Color.parseColor("#ffffff"))
                .build();
        ArrayList<String> temp2 = new ArrayList<String>();
        for (ShuJuEntity bean : temp) {
            temp2.add(bean.getName());
        }
        pvOptions.setPicker(temp2);
        pvOptions.show();
    }

    private void upload() {
        showWaitDialog("正在刷新信息...");
        Gson gson = new Gson();
        String params = gson.toJson(tabledata);
        //  showToast(params);
        PileApi.instance.addSuyunOrder(params)
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
                            // showToast(body);
                            if (body.indexOf("soverstep")!=-1){
                                showToast("出发地超出配送范围");
                            }else if (body.indexOf("eoverstep")!=-1){
                                showToast("目的地超出配送范围");
                            }else if (body.indexOf("false")!=-1){
                                showToast("提交订单失败，请检查信息后重试");
                            }else {
//                                showpaydialog(Float.parseFloat(tabledata.getOrdertotalprice()), body.replace("\"", ""));


                                final AlertDialog dialog1 = new AlertDialog.Builder(HuoDSuyunActivity.this).create();
                                ispayok = true;
//                                                payalertDialog.dismiss();
                                dialog1.show();
                                dialog1.getWindow().setContentView(R.layout.dialog_zhifuchenggong);
                                dialog1.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);

                                WindowManager windowManager = getWindowManager();
                                Display display = windowManager.getDefaultDisplay();
                                WindowManager.LayoutParams p = dialog1.getWindow().getAttributes();
                                p.width = (int) (display.getWidth() * 0.6);
                                dialog1.getWindow().setAttributes(p);

                                TextView tv_success= (TextView) dialog1.getWindow().findViewById(R.id.tv_success);
                                tv_success.setText("订单发布成功");
                                TextView textView = (TextView) dialog1.getWindow().findViewById(R.id.tv_chakan);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        isok = false;
                                        dialog1.dismiss();
                                        finish();
                                        sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                        Intent intent = new Intent(HuoDSuyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                        intent.putExtra("type", 0);
                                        startActivity(intent);


//                                        finish();
                                    }
                                });
                                dialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                        sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                    }
                                });
                            }
                            hideWaitDialog();
                        } catch (IOException e) {
                            showToast(e.getLocalizedMessage());
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
                        final AlertDialog dialog = new AlertDialog.Builder(HuoDSuyunActivity.this).create();
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

                        TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isok = false;
                                dialog.dismiss();
                                finish();
                                sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                Intent intent = new Intent(HuoDSuyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                intent.putExtra("type", 0);
                                startActivity(intent);


//                                        finish();
                            }
                        });
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                                finish();
                                sendPayLocalReceiver(DeliverGoodsActivity.class.getName());

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

    protected boolean sendPayLocalReceiver(String className) {
        if (mManager != null) {
            Intent intent = new Intent();
            intent.putExtra("className", className);
            intent.setAction(ACTION_PAY_FINISH_ALL);
            return mManager.sendBroadcast(intent);
        }

        return false;
    }

    private void showpaydialog(final float summoney, final String orderid) {

        payalertDialog = new AlertDialog.Builder(this).create();
        payalertDialog.show();
        payalertDialog.getWindow().setContentView(R.layout.layout_pay2);
        payalertDialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams p = payalertDialog.getWindow().getAttributes();
        p.width = (int) (display.getWidth() * 0.8);
        payalertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!ispayok) {
                    deletepay(orderid);
                }
            }
        });
        payalertDialog.getWindow().setAttributes(p);
        payalertDialog.getWindow()
                .findViewById(R.id.rb1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HuoDSuyunActivity.this)
                                .setTitle("提示")
                                .setMessage("确定要支付吗")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        startPay(summoney, 0, orderid);
//                                        deletepay(orderid);
                                        showToast("正在开发中");
//                                        startPay(0.01f, 0, orderid);
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


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HuoDSuyunActivity.this)
                                .setTitle("提示")
                                .setMessage("确定要支付吗")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        startPay(summoney, 1, orderid);
//                                        deletepay(orderid);
                                        showToast("正在开发中");
//                                        startPay(0.01f, 1, orderid);
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
        if (Double.parseDouble(tabledata.getOrdertotalprice()) < i) {
            LinearLayout radioButton = (LinearLayout) payalertDialog.getWindow().findViewById(R.id.rb3);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HuoDSuyunActivity.this)
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
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(HuoDSuyunActivity.this)
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(HuoDSuyunActivity.this)
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
                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(HuoDSuyunActivity.this);
                                View view = LayoutInflater.from(HuoDSuyunActivity.this).inflate(R.layout.dialog_zhifumima, null, false);
                                dialog1.setView(view);
                                alertDialog = dialog1.create();
                                alertDialog.setCancelable(false);
                                ed_password = (EditText) view.findViewById(R.id.ed_password);
                                Button button = (Button) view.findViewById(R.id.btn_queding);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ed_password.getText().toString().trim().equals("")) {
                                            Toast.makeText(HuoDSuyunActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                                        } else {
//                                            hintKeyboard();
                                            orderYuEZhiFu(alertDialog, ed_password.getText().toString().trim(), orderid, Float.parseFloat(tabledata.getOrdertotalprice()));
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

    private void startPay(final float confirmMoney, final int payType, final String orderid) {
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
                                            PayTask alipay = new PayTask(HuoDSuyunActivity.this);
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
                                    map.put("notify_url", "http://www.maibat.com/maibate/shipperWeChat");
                                    map.put("body", "商品描述");
                                    new WXPayUtils().init(HuoDSuyunActivity.this, map)
                                            .setListener(new WXPayUtils.BackResult() {
                                                @Override
                                                public void getInfo(String prepayId, String sign) {
                                                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                                                            .with(HuoDSuyunActivity.this) //activity instance
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

    //余额支付
    private void orderYuEZhiFu(final AlertDialog alertDialog, String password, String orderno, float totalmoney) {
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
                                final AlertDialog dialog = new AlertDialog.Builder(HuoDSuyunActivity.this).create();
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

                                TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        isok = false;
                                        dialog.dismiss();
                                        finish();
                                        sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                        Intent intent = new Intent(HuoDSuyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                        intent.putExtra("type", 0);
                                        startActivity(intent);


//                                        finish();
                                    }
                                });
                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                        sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
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

    //余额支付
    private void deletepay(String orderno) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("uuid", orderno + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.deleteSuyunOrder(params)
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
                            System.err.println(body);
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
                                        final AlertDialog dialog = new AlertDialog.Builder(HuoDSuyunActivity.this).create();
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

                                        TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                                        textView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                isok = false;
                                                dialog.dismiss();
                                                finish();
                                                sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                                Intent intent = new Intent(HuoDSuyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                                intent.putExtra("type", 0);
                                                startActivity(intent);


//                                        finish();
                                            }
                                        });
                                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {

                                                finish();


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mManager1 != null) {
            if (mReceiver1 != null)
                mManager1.unregisterReceiver(mReceiver1);
        }
        SharedPreferences sp = getSharedPreferences("chengshi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        SharedPreferences sp1 = getSharedPreferences("shengshixian1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.clear();
        editor1.apply();
        SharedPreferences sp2 = getSharedPreferences("shengshixian2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sp2.edit();
        editor2.clear();
        editor2.apply();
    }

    /**
     * activity回调
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 001 && resultCode == 002) {
            tv_ssx1.setVisibility(View.VISIBLE);
            qdtext.setText(data.getStringExtra("myresuly"));
            tv_ssx1.setText(data.getStringExtra("ssx1"));
            tabledata.setStartaddress(data.getStringExtra("myresuly"));
            tabledata.setStartlongitude(data.getStringExtra("y"));
            tabledata.setStartlatitude(data.getStringExtra("x"));
            tabledata.setSprovinceid(data.getStringExtra("shengid"));
            tabledata.setScityid(data.getStringExtra("shiid"));
            tabledata.setScountyid(data.getStringExtra("xianid"));
            tabledata.setSprocitcou(data.getStringExtra("ssx"));
        } else if (requestCode == 001 && resultCode == 003) {
            tv_ssx2.setVisibility(View.VISIBLE);
            tabledata.setEprovinceid(data.getStringExtra("shengid"));
            tabledata.setEcityid(data.getStringExtra("shiid"));
            tabledata.setEcountyid(data.getStringExtra("xianid"));
            tabledata.setEprocitcou(data.getStringExtra("ssx"));
            zdtext.setText(data.getStringExtra("myresuly"));
            tv_ssx2.setText(data.getStringExtra("ssx1"));
            tabledata.setEndaddress(data.getStringExtra("myresuly"));
            tabledata.setEndlongitude(data.getStringExtra("y"));
            tabledata.setEndlatitude(data.getStringExtra("x"));
        } else if (requestCode == 001 && resultCode == 004) {
            String datajson = data.getStringExtra("datas");
            String titles = data.getStringExtra("titles");
            ewxq_text.setText(titles);
            //此处为获取额外需求后计算额外价格部分，具体价格公式需要和苹果端确定一下再作计算
            //额外需求在datajson中  请在计算完毕后放入tabledata的   huodi_suyun_detailsList   字段中
            //物流发货参考代码如下：
            datas.clear();
            datas = new Gson().fromJson(datajson, new TypeToken<List<AdditionalDemandProListBean>>() {
            }.getType());
            double ewjg = 0.0;
//            zjg = superzjg + "";
            for (int i = 0; i < datas.size(); i++) {
                if (Double.parseDouble(datas.get(i).getOwner_service_price()) > 0) {
                    ewjg = ewjg + (jiage * Double.parseDouble(datas.get(i).getOwner_service_price()));
                    datas.get(i).setOwner_service_price(jiage * Double.parseDouble(datas.get(i).getOwner_service_price()) + "");
                }
            }
            tabledata.setHuodi_suyun_detailsList(datas);

            ordertotalprice.setText(ewjg + jiage + "");
//            zjg = (Double.parseDouble(zjg) + ewjg) + "";
//            BigDecimal b = new BigDecimal(zjg);
//            zjg = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
//            tabledata.setOwner_totalprice(zjg);
//            //  showToast(zjg);
//            ewxq_text.setText(titles);
//            yhj.setText(zjg);
        }

        if (!qdtext.getText().toString().equals("请选择起点") && !zdtext.getText().toString().equals("请选择终点") && !qdtext.getText().toString().equals("") && !zdtext.getText().toString().equals("")){
            if (!qdtext.getText().toString().trim().equals(zdtext.getText().toString().trim())){
                LatLonPoint start = new LatLonPoint(Double.parseDouble(tabledata.getStartlatitude()), Double.parseDouble(tabledata.getStartlongitude()));
                LatLonPoint end = new LatLonPoint(Double.parseDouble(tabledata.getEndlatitude()), Double.parseDouble(tabledata.getEndlongitude()));
                searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);
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


    private void searchCarServices() {
        showWaitDialog("正在加载额外需求...");
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "0");
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
                                datas.clear();
                                for (AdditionalDemandListBean temp : AdditionalDemandList) {
                                    if (temp.getService_price() == 0) {
                                        datas.add(new AdditionalDemandProListBean(temp.getId() + "", temp.getService_price() + "", "huodi_suyun_details"));
                                    }
                                }

                                tabledata.setHuodi_suyun_detailsList(datas);
                                upload();

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
        if (!Constant.city.equals("")){
            tv_chengshi.setText(Constant.city);
        }else {
            tv_chengshi.setText(cs);
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        if (i == 1000) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    final DrivePath drivePath = driveRouteResult.getPaths()
                            .get(0);
                    double dis = drivePath.getDistance();
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
                    BigDecimal b = new BigDecimal(dis / 1000);
                    double v = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    tv_juli11.setText(v+"km");
                    tabledata.setTotal_mileage(v+"");
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
}
