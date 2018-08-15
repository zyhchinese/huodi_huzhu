package com.lx.hd.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.AccountHelper;
import com.lx.hd.alipay.OrderInfoUtil2_0;
import com.lx.hd.alipay.PayResult;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.KuYunCheChangEntity;
import com.lx.hd.bean.KuaiYunTableBean;
import com.lx.hd.bean.MoRenQiDianAddressEntity;
import com.lx.hd.bean.cxbean;
import com.lx.hd.bean.huowuzhongliangBean;
import com.lx.hd.bean.hwlxBean;
import com.lx.hd.widgets.duoxuandialog.MyAdapter;
import com.lx.hd.widgets.duoxuandialog.ShuJuEntity;
import com.lx.hd.widgets.duoxuandialog.ShuJuEntity1;
import com.lx.hd.widgets.wheelview.ChangeDatePopwindow3;
import com.lx.hd.widgets.wheelview.ChangeDatePopwindow4;
import com.lx.paylib.PayAPI;
import com.lx.paylib.WechatPayReq;
import com.lx.paylib.wxutils.WXPayUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
* 快运
* */
public class HuoDKuaiyunActivity extends BaseActivity implements RouteSearch.OnRouteSearchListener {
    private ImageView go_back;
    private RelativeLayout qhsj, qd, zd;
    private RelativeLayout main;
    private EditText ed_password;
    private EditText ed_dunshu;
    private RelativeLayout cx, hwlx;
    private RelativeLayout relative_moren_lianxiren, relative_check_morenlianxiren;
    private TextView qhtimetext, qdtext, zdtext, tv_cx_text, tv_hwlx, submit, shipmenttime;
    private TextView tv_save_shouhuo;
    private ImageView img_changyong_qidian, img_changyong_zhongdian, img_changyong_lianxiren;
    private TextView tv_qidian_address, tv_zhongdian_address;
    private ImageView img_shouhuoren, img_show11;
    private boolean isok = false;
    private AlertDialog payalertDialog;
    protected LocalBroadcastManager mManager1;
    private EditText tv_qtlx, weight, volume, remarks, contactname, contactphone, settheprice;
    private BroadcastReceiver mReceiver1;
    private String cs = "";
    private boolean ispayok = false;
    private AlertDialog alertDialog;
    private double i;
    private static final int SDK_PAY_FLAG = 1;
    private KuaiYunTableBean tabledata;
    private Dialog dialog;
    private List<ShuJuEntity> duoxuanlist = new ArrayList<>();
    private List<ShuJuEntity1> duoxuanlist1 = new ArrayList<>();
    //显示单选或多选Dialog  true为单选，false为多选
    private boolean isOk = true;
    private TextView tv_ssx1, tv_ssx2;
    private String str;
    private EditText contactphone1;
    private final int ROUTE_TYPE_DRIVE = 2;
    private RouteSearch mRouteSearch;
    private TextView tv_juli11;
    private boolean isQianYue = false;
    private String morendizhiq = "";
    private String morendizhiz = "";
    private boolean morenshouhuoren = true;
    private String morenshouhuoren11 = "";
    private ArrayList<ShuJuEntity> cexiangdatas;
    private ArrayList<ShuJuEntity> chechangdatas;
    private String yongcheleixing="2";

    @Override
    protected int getContentView() {
        return R.layout.activity_huodikuaiyun;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tabledata = new KuaiYunTableBean();
        weight = (EditText) findViewById(R.id.weight);
        volume = (EditText) findViewById(R.id.volume);
        remarks = (EditText) findViewById(R.id.remarks);
        contactname = (EditText) findViewById(R.id.contactname);
        contactphone = (EditText) findViewById(R.id.contactphone);
        contactphone1 = (EditText) findViewById(R.id.contactphone1);
        ed_dunshu = (EditText) findViewById(R.id.ed_dunshu);
        settheprice = (EditText) findViewById(R.id.settheprice);
        qhsj = (RelativeLayout) findViewById(R.id.zcsj);
        relative_moren_lianxiren = (RelativeLayout) findViewById(R.id.relative_moren_lianxiren);
        relative_check_morenlianxiren = (RelativeLayout) findViewById(R.id.relative_check_morenlianxiren);
        tv_save_shouhuo = (TextView) findViewById(R.id.tv_save_shouhuo);
        tv_qidian_address = (TextView) findViewById(R.id.tv_qidian_address);
        tv_zhongdian_address = (TextView) findViewById(R.id.tv_zhongdian_address);
        img_shouhuoren = (ImageView) findViewById(R.id.img_shouhuoren);
        img_show11 = (ImageView) findViewById(R.id.img_show11);
        img_changyong_qidian = (ImageView) findViewById(R.id.img_changyong_qidian);
        img_changyong_zhongdian = (ImageView) findViewById(R.id.img_changyong_zhongdian);
        img_changyong_lianxiren = (ImageView) findViewById(R.id.img_changyong_lianxiren);
        submit = (TextView) findViewById(R.id.submit);
        shipmenttime = (TextView) findViewById(R.id.shipmenttime);
        shipmenttime = (TextView) findViewById(R.id.shipmenttime);
        tv_cx_text = (TextView) findViewById(R.id.cx_text);
        tv_hwlx = (TextView) findViewById(R.id.hwlx_text);
        go_back = (ImageView) findViewById(R.id.go_back);
        qhtimetext = (TextView) findViewById(R.id.qhtimetext);
        main = (RelativeLayout) findViewById(R.id.main);
        qd = (RelativeLayout) findViewById(R.id.qd);
        zd = (RelativeLayout) findViewById(R.id.zd);
        cs = Constant.city;
        qdtext = (TextView) findViewById(R.id.qdtext);
        zdtext = (TextView) findViewById(R.id.zdtext);
        tv_ssx1 = (TextView) findViewById(R.id.tv_ssx1);
        tv_ssx2 = (TextView) findViewById(R.id.tv_ssx2);
        tv_juli11 = (TextView) findViewById(R.id.tv_juli11);
        cx = (RelativeLayout) findViewById(R.id.cx);
        hwlx = (RelativeLayout) findViewById(R.id.hwlx);
        cs = getIntent().getStringExtra("cs");
        SharedPreferences sp = getSharedPreferences("userxinxi", Context.MODE_PRIVATE);
//        contactname.setText(sp.getString("custname", ""));
//        contactphone.setText(sp.getString("custphone", ""));
        contactphone1.setText(sp.getString("custphone", ""));
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("请选择起点".equals(qdtext.getText().toString())) {
                    showToast("请选择起点");
                    return;
                }
                if ("请选择终点".equals(zdtext.getText().toString())) {
                    showToast("请选择终点");
                    return;
                }
                if ("请选择装车时间".equals(shipmenttime.getText().toString())) {
                    showToast("请选择装车时间");
                    return;
                }


//                if ("请选择".equals(tv_cx_text.getText().toString())) {
//                    showToast("请选择车型");
//                    return;
//                }
                if (tabledata.getLengthname()==null||tabledata.getLengthname().equals("请选择")){
                    showToast("请选择车长");
                    return;
                }
                if (tabledata.getCartypenames()==null||tabledata.getCartypenames().equals("")){
                    showToast("请选择车型");
                    return;
                }
                if (tv_hwlx.getText().toString().equals("请选择")){
                    showToast("请选择货物类型");
                    return;
                }


//                if ("".equals(weight.getText().toString())) {
//                    showToast("请填写货物数量");
//                    return;
//                }
                if ("".equals(volume.getText().toString().trim()) && "".equals(ed_dunshu.getText().toString().trim())) {
                    showToast("请填写货物重量");
                    return;
                }
//                if ("".equals(contactname.getText().toString())) {
//                    showToast("请填写收货人姓名");
//                    return;
//                }
//                if ("".equals(contactphone.getText().toString())) {
//                    showToast("请填写收货联系人电话");
//                    return;
//                }
//                if (contactphone.getText().toString().trim().length()!=11){
//                    showToast("收货联系人电话格式不正确");
//                    return;
//                }
                if ("".equals(contactphone1.getText().toString())) {
                    showToast("请填写发货联系人电话");
                    return;
                }
                if (contactphone1.getText().toString().trim().length() != 11) {
                    showToast("发货联系人电话格式不正确");
                    return;
                }
//                if (qdtext.getText().toString().equals(zdtext.getText().toString())){
//                    showToast("起点和目的地址选择不能一致");
//                    return;
//                }
//                if ("".equals(settheprice.getText().toString())) {
//                    showToast("请填写金额");
//                    return;
//                }

                if (morendizhiq.equals("1") && !morendizhiz.equals("1") && !morenshouhuoren11.equals("1")) {

                    tabledata.setRelevant_type("2");
                } else if (!morendizhiq.equals("1") && morendizhiz.equals("1") && !morenshouhuoren11.equals("1")) {

                    tabledata.setRelevant_type("3");
                } else if (!morendizhiq.equals("1") && !morendizhiz.equals("1") && morenshouhuoren11.equals("1")) {

                    tabledata.setRelevant_type("1");
                } else if (morendizhiq.equals("1") && morendizhiz.equals("1") && !morenshouhuoren11.equals("1")) {

                    tabledata.setRelevant_type("6");
                } else if (morendizhiq.equals("1") && !morendizhiz.equals("1") && morenshouhuoren11.equals("1")) {

                    tabledata.setRelevant_type("4");
                } else if (!morendizhiq.equals("1") && morendizhiz.equals("1") && morenshouhuoren11.equals("1")) {

                    tabledata.setRelevant_type("5");
                } else if (morendizhiq.equals("1") && morendizhiz.equals("1") && morenshouhuoren11.equals("1")) {

                    tabledata.setRelevant_type("7");
                } else if (!morendizhiq.equals("1") && !morendizhiz.equals("1") && !morenshouhuoren11.equals("1")) {

                    tabledata.setRelevant_type("0");
                }
                tabledata.setContactname(contactname.getText().toString());
                tabledata.setCargotypenames(tv_hwlx.getText().toString());
                tabledata.setConsignorphone(contactphone1.getText().toString());
//                tabledata.setCartypenames(tv_cx_text.getText().toString());
                tabledata.setContactphone(contactphone.getText().toString());
                tabledata.setStartaddress(qdtext.getText().toString());
                tabledata.setEndaddress(zdtext.getText().toString());
                tabledata.setShipmenttime(shipmenttime.getText().toString());
                if (weight.getText().toString().trim().equals("")) {
                    tabledata.setVolume("0");
                } else {
                    tabledata.setVolume(weight.getText().toString());
                }
                if (volume.getText().toString().trim().equals("")) {
                    tabledata.setWeight("0");
                } else {
                    tabledata.setWeight(volume.getText().toString());
                }
                if (ed_dunshu.getText().toString().trim().equals("")) {
                    tabledata.setTon_weight("0");
                } else {
                    tabledata.setTon_weight(ed_dunshu.getText().toString());
                }

                tabledata.setRemarks(remarks.getText().toString());
                if (settheprice.getText().toString().trim().equals("")) {
                    tabledata.setSettheprice("0");
                } else {
                    tabledata.setSettheprice(settheprice.getText().toString());
                }
//                tabledata.setSettheprice(settheprice.getText().toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
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
                                upload();

                            }
                        });
                builder.show();


            }
        });
        cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadcx();
//                loadcc();
                initDialog();
            }
        });
        hwlx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadhwlx();
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        str = formatter.format(curDate);
        qhsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeDatePopwindow3 mChangeBirthDialog = new ChangeDatePopwindow3(HuoDKuaiyunActivity.this);
                mChangeBirthDialog.showAtLocation(main, Gravity.BOTTOM, 0, 0);
                mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow3.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day, String day1) {
                        // TODO Auto-generated method stub
                        String yydate = year + " " + day1.replace("点", "") + ":" + day.replace("分", "");

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//年-月-日 时-分-秒
                        try {
                            Date date1 = dateFormat.parse(yydate);
                            Date date2 = dateFormat.parse(str);
                            if (date1.getTime() <= date2.getTime()) {
                                showToast("选择时间需大于当前时间");
                            } else {
                                shipmenttime.setText(yydate);
                                //  showToast(yydate);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });
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
                bundle.putString("type", "kuaiyun");
                Intent intent = new Intent(HuoDKuaiyunActivity.this, DeliverMapActivity.class);
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
                bundle.putString("x", tabledata.getEndlatitude());
                bundle.putString("y", tabledata.getEndlongitude());
                bundle.putString("type", "kuaiyun");
                Intent intent = new Intent(HuoDKuaiyunActivity.this, DeliverMapActivity.class);
                intent.putExtras(bundle);

                startActivityForResult(intent, 001);
            }
        });
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!qdtext.getText().toString().equals("请选择起点") && !zdtext.getText().toString().equals("请选择终点") && !weight.getText().toString().trim().equals("") && !volume.getText().toString().trim().equals("") && isQianYue) {
                    initQianYueMoney(tabledata.getSprovince(), tabledata.getScity(),
                            tabledata.getEprovince(), tabledata.getEcity(),
                            tabledata.getEcounty(), weight.getText().toString().trim(),
                            volume.getText().toString().trim());
                }
            }
        });

        volume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!qdtext.getText().toString().equals("请选择起点") && !weight.getText().toString().trim().equals("") && !volume.getText().toString().trim().equals("") && isQianYue) {
                    initQianYueMoney(tabledata.getSprovince(), tabledata.getScity(),
                            tabledata.getEprovince(), tabledata.getEcity(),
                            tabledata.getEcounty(), weight.getText().toString().trim(),
                            volume.getText().toString().trim());
                }
            }
        });
        relative_moren_lianxiren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!contactname.getText().toString().equals("") && !contactphone.getText().toString().equals("")) {
                    if (morenshouhuoren) {
                        morenshouhuoren = false;
                        morenshouhuoren11 = "1";
                        img_show11.setVisibility(View.VISIBLE);
                        tv_save_shouhuo.setTextColor(Color.parseColor("#e40e0e"));
                        img_shouhuoren.setVisibility(View.INVISIBLE);
                    } else {
                        morenshouhuoren = true;
                        morenshouhuoren11 = "2";
                        img_show11.setVisibility(View.INVISIBLE);
                        tv_save_shouhuo.setTextColor(Color.parseColor("#bfb0b0"));
                        img_shouhuoren.setVisibility(View.INVISIBLE);
                    }
                } else {
                    showToast("请填写收货人姓名或电话");
                }
            }
        });
        tv_qidian_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuoDKuaiyunActivity.this, MoRenQiDianAddressActivity.class);
                startActivityForResult(intent, 001);
            }
        });
        tv_zhongdian_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuoDKuaiyunActivity.this, MoRenQiDianAddressActivity1.class);
                startActivityForResult(intent, 001);
            }
        });
        relative_check_morenlianxiren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuoDKuaiyunActivity.this, MoRenLianXiRenActivity.class);
                startActivityForResult(intent, 001);
            }
        });
        img_changyong_qidian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuoDKuaiyunActivity.this, MoRenQiDianAddressActivity.class);
                startActivityForResult(intent, 001);
            }
        });
        img_changyong_zhongdian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuoDKuaiyunActivity.this, MoRenQiDianAddressActivity1.class);
                startActivityForResult(intent, 001);
            }
        });
        img_changyong_lianxiren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuoDKuaiyunActivity.this, MoRenLianXiRenActivity.class);
                startActivityForResult(intent, 001);
            }
        });
        getyeData();
        initJianceZhouJie();
        initjiance();

        initmorendizhilianxiren();

        loadcx();
        loadcc();
    }

    private void initDialog() {
        //初始化Dialog
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_item111, null);
        //初始化控件
        TextView tv_dismiss=(TextView) inflate.findViewById(R.id.tv_dismiss);
        TextView tv_queren=(TextView) inflate.findViewById(R.id.tv_queren);
        final EditText ed_chechang= (EditText) inflate.findViewById(R.id.ed_chechang);
        RecyclerView act_recyclerView1 = (RecyclerView) inflate.findViewById(R.id.recyclerView1);
        RecyclerView act_recyclerView2 = (RecyclerView) inflate.findViewById(R.id.recyclerView2);
        final TextView tv_yongcheleixing11= (TextView) inflate.findViewById(R.id.tv_yongcheleixing11);
        final TextView tv_yongcheleixing22= (TextView) inflate.findViewById(R.id.tv_yongcheleixing22);
        final TextView tv_yongcheleixing33= (TextView) inflate.findViewById(R.id.tv_yongcheleixing33);

        if (yongcheleixing.equals("2")){
            tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg);
            tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg_f);
            tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg_f);
            tv_yongcheleixing11.setTextColor(Color.parseColor("#ffffff"));
            tv_yongcheleixing22.setTextColor(Color.parseColor("#666666"));
            tv_yongcheleixing33.setTextColor(Color.parseColor("#666666"));
        }else if (yongcheleixing.equals("0")){
            tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg_f);
            tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg);
            tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg_f);
            tv_yongcheleixing11.setTextColor(Color.parseColor("#666666"));
            tv_yongcheleixing22.setTextColor(Color.parseColor("#ffffff"));
            tv_yongcheleixing33.setTextColor(Color.parseColor("#666666"));
        }else if (yongcheleixing.equals("1")){
            tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg_f);
            tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg_f);
            tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg);
            tv_yongcheleixing11.setTextColor(Color.parseColor("#666666"));
            tv_yongcheleixing22.setTextColor(Color.parseColor("#666666"));
            tv_yongcheleixing33.setTextColor(Color.parseColor("#ffffff"));
        }

        tv_yongcheleixing11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg);
                tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                tv_yongcheleixing11.setTextColor(Color.parseColor("#ffffff"));
                tv_yongcheleixing22.setTextColor(Color.parseColor("#666666"));
                tv_yongcheleixing33.setTextColor(Color.parseColor("#666666"));
                yongcheleixing="2";
            }
        });
        tv_yongcheleixing22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg);
                tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                tv_yongcheleixing11.setTextColor(Color.parseColor("#666666"));
                tv_yongcheleixing22.setTextColor(Color.parseColor("#ffffff"));
                tv_yongcheleixing33.setTextColor(Color.parseColor("#666666"));
                yongcheleixing="0";
            }
        });
        tv_yongcheleixing33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_yongcheleixing11.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                tv_yongcheleixing22.setBackgroundResource(R.drawable.bg_orovince_bg_f);
                tv_yongcheleixing33.setBackgroundResource(R.drawable.bg_orovince_bg);
                tv_yongcheleixing11.setTextColor(Color.parseColor("#666666"));
                tv_yongcheleixing22.setTextColor(Color.parseColor("#666666"));
                tv_yongcheleixing33.setTextColor(Color.parseColor("#ffffff"));
                yongcheleixing="1";
            }
        });
        //初始化列表
        MyAdapter adapter = new MyAdapter(this, chechangdatas, false);
        GridLayoutManager manager = new GridLayoutManager(this, 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView1.setLayoutManager(manager);
        act_recyclerView1.setAdapter(adapter);

        MyAdapter adapter1 = new MyAdapter(this, cexiangdatas, false);
        GridLayoutManager manager1 = new GridLayoutManager(this, 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView2.setLayoutManager(manager1);
        act_recyclerView2.setAdapter(adapter1);
        //取消按钮的点击事件
        tv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                dialog = null;
            }


        });
        //确认按钮的点击事件
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_cx_text.setText("请选择");

                if (yongcheleixing.equals("0")){
                    tv_cx_text.setText("整车");
                }else if (yongcheleixing.equals("1")){
                    tv_cx_text.setText("零担");
                }
                //用车类型
                tabledata.setUse_car_type(yongcheleixing);
                //车长
                duoxuanlist1.clear();
                for (ShuJuEntity entity:chechangdatas){
                    duoxuanlist1.add(new ShuJuEntity1(entity.getId(),entity.getName(),entity.isType()));
                }

                for (ShuJuEntity1 temp : duoxuanlist1) {

                    if (temp.isType()) {
                            if (tv_cx_text.getText().toString().equals("请选择")) {
                                tv_cx_text.setText(temp.getName());

                            } else {
                                tv_cx_text.setText(tv_cx_text.getText().toString() + "," + temp.getName());

                            }

                    }
                }
                if (!ed_chechang.getText().toString().trim().equals("")){
                    if (tv_cx_text.getText().toString().equals("请选择")){
                        tv_cx_text.setText(ed_chechang.getText().toString().trim());
                    }else {
                        tv_cx_text.setText(tv_cx_text.getText().toString() + "," + ed_chechang.getText().toString().trim());
                    }

                }
                if (yongcheleixing.equals("0")||yongcheleixing.equals("1")){
                    if (tv_cx_text.getText().toString().length()==2){
                        tabledata.setLengthname("请选择");
                    }else {
                        tabledata.setLengthname(tv_cx_text.getText().toString().substring(3,tv_cx_text.getText().toString().length()));
                    }

                }else {
                    tabledata.setLengthname(tv_cx_text.getText().toString());
                }


                //车型
                duoxuanlist1.clear();
                for (ShuJuEntity entity:cexiangdatas){
                    duoxuanlist1.add(new ShuJuEntity1(entity.getId(),entity.getName(),entity.isType()));
                }
                for (ShuJuEntity1 temp : duoxuanlist1) {

                    if (temp.isType()) {
                        if (tv_cx_text.getText().toString().equals("请选择")) {
                            tv_cx_text.setText(temp.getName());

                        } else {
                            tv_cx_text.setText(tv_cx_text.getText().toString() + "," + temp.getName());

                        }

                    }
                }
                String chexing="";
                for (ShuJuEntity1 temp : duoxuanlist1){
                    if (temp.isType()){
                        chexing=chexing+temp.getName()+",";
                    }
                }
                if (!chexing.equals("")){
                    tabledata.setCartypenames(chexing.substring(0,chexing.length()-1));
                }else {
                    tabledata.setCartypenames("");
                }


                dialog.dismiss();
                dialog = null;
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);

        WindowManager windowManager = getWindowManager();
        //为获取屏幕宽、高
        Display display = windowManager.getDefaultDisplay();
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置Dialog距离底部的距离
        lp.y = 0;
        //设置Dialog宽度
        lp.width = display.getWidth();
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        //显示对话框
        dialog.show();
    }

    private void loadcc() {
        PileApi.instance.searchVehicleLength()
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
                            System.out.println("body = " + body);
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                KuYunCheChangEntity kuYunCheChangEntity = new Gson().fromJson(body, KuYunCheChangEntity.class);


                                if (kuYunCheChangEntity.getResponse().size() == 0) {
                                    Toast.makeText(HuoDKuaiyunActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                chechangdatas = new ArrayList<ShuJuEntity>();
                                for (KuYunCheChangEntity.ResponseBean data : kuYunCheChangEntity.getResponse()) {
                                    chechangdatas.add(new ShuJuEntity(data.getId(), data.getLengthname(), false));
                                }
                            }

//                            showDuoXuanDiaLog(datas, false, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(HuoDKuaiyunActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initmorendizhilianxiren() {
        PileApi.instance.selectUserRelevantDef()
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

                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                Gson gson = new Gson();
                                MoRenQiDianAddressEntity moRenQiDianAddressEntity = gson.fromJson(body, MoRenQiDianAddressEntity.class);

                                if (moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().size() != 0) {
                                    qdtext.setText(moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(0).getSaddress());
                                    tabledata.setStartlongitude(moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(0).getSlongitude());
                                    tabledata.setStartlatitude(moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(0).getSlatitude());
                                    tabledata.setSprovince(moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(0).getSprovince());
                                    tabledata.setScity(moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(0).getScity());
                                    tabledata.setScounty(moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(0).getScounty());
                                    morendizhiq = "";
                                }
                                if (moRenQiDianAddressEntity.getResponse().get(0).getEaddlist().size() != 0) {
                                    zdtext.setText(moRenQiDianAddressEntity.getResponse().get(0).getEaddlist().get(0).getEaddress());
                                    tabledata.setEprovince(moRenQiDianAddressEntity.getResponse().get(0).getEaddlist().get(0).getEprovince());
                                    tabledata.setEcity(moRenQiDianAddressEntity.getResponse().get(0).getEaddlist().get(0).getEcity());
                                    tabledata.setEcounty(moRenQiDianAddressEntity.getResponse().get(0).getEaddlist().get(0).getEcounty());
                                    tabledata.setEndlongitude(moRenQiDianAddressEntity.getResponse().get(0).getEaddlist().get(0).getElongitude());
                                    tabledata.setEndlatitude(moRenQiDianAddressEntity.getResponse().get(0).getEaddlist().get(0).getElatitude());
                                    morendizhiz = "";
                                }
                                if (moRenQiDianAddressEntity.getResponse().get(0).getCustinfo().size() != 0) {
                                    contactname.setText(moRenQiDianAddressEntity.getResponse().get(0).getCustinfo().get(0).getName());
                                    contactphone.setText(moRenQiDianAddressEntity.getResponse().get(0).getCustinfo().get(0).getCall());
                                }

                                if (!qdtext.getText().toString().equals("请选择起点") && !zdtext.getText().toString().equals("请选择终点") && !qdtext.getText().toString().equals("") && !zdtext.getText().toString().equals("")) {
//            if (!qdtext.getText().toString().trim().equals(zdtext.getText().toString().trim())){
                                    LatLonPoint start = new LatLonPoint(Double.parseDouble(tabledata.getStartlatitude()), Double.parseDouble(tabledata.getStartlongitude()));
                                    LatLonPoint end = new LatLonPoint(Double.parseDouble(tabledata.getEndlatitude()), Double.parseDouble(tabledata.getEndlongitude()));
                                    searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);
//            }
                                }

                            }


                        } catch (IOException e) {
                            e.printStackTrace();
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

                            if (body.contains("nologin")) {
//                                showToast("请检查登录状态");
                            } else if (body.contains("nosign")) {

//                                showToast("非签约用户，不能周结");
                            } else if (body.contains("true")) {

//                                showToast("是签约用户，可以周结");
                            } else if (body.contains("false")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
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
                            } else {
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

    private void initjiance() {
        PileApi.instance.checkUserSign()
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
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            String msg = jsonObject.getString("msg");
                            if (flag.equals("100")) {
                                showToast("请检查您的登录状态");
                            } else if (flag.equals("210")) {
                                isQianYue = false;
                            } else if (flag.equals("200")) {
                                isQianYue = true;
                            } else {
                                isQianYue = false;
                                showToast(msg);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(HuoDKuaiyunActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initQianYueMoney(String sprovince, String scity, String eprovince, String ecity, String eArea, String volume, String weight) {
        HashMap<String, String> map = new HashMap<>();
        map.put("provice_name", sprovince);
        map.put("city_name", scity);
        map.put("to_provice_name", eprovince);
        map.put("to_city_name", ecity);
        map.put("to_area_name", eArea);
        map.put("volume", volume);
        map.put("weight", weight);
        Gson gson3 = new Gson();
        String params = gson3.toJson(map);
        PileApi.instance.selectSignSetPrice(params)
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

                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                JSONArray response = jsonObject.getJSONArray("response");
                                JSONObject jsonObject1 = response.getJSONObject(0);
                                String price = jsonObject1.getString("price");
                                settheprice.setText(price);

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(HuoDKuaiyunActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private boolean phone(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * activity回调
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 001 && resultCode == 002) {

//            tv_ssx1.setVisibility(View.VISIBLE);
//            tv_ssx1.setText(data.getStringExtra("ssx1"));
            qdtext.setText(data.getStringExtra("myresuly") + " " + data.getStringExtra("address"));
            tabledata.setStartlongitude(data.getStringExtra("y"));
            tabledata.setStartlatitude(data.getStringExtra("x"));
            tabledata.setSprovince(data.getStringExtra("dwsheng"));
            tabledata.setScity(data.getStringExtra("dwshi"));
            tabledata.setScounty(data.getStringExtra("dwxian"));
            morendizhiq = data.getStringExtra("morendizhi");
            if (!zdtext.getText().toString().equals("请选择终点") && !weight.getText().toString().trim().equals("") && !volume.getText().toString().trim().equals("") && isQianYue) {
                initQianYueMoney(tabledata.getSprovince(), tabledata.getScity(),
                        tabledata.getEprovince(), tabledata.getEcity(),
                        tabledata.getEcounty(), weight.getText().toString().trim(),
                        volume.getText().toString().trim());
            }
        } else if (requestCode == 001 && resultCode == 003) {
//            tv_ssx2.setVisibility(View.VISIBLE);
//            tv_ssx2.setText(data.getStringExtra("ssx1"));
            zdtext.setText(data.getStringExtra("myresuly") + " " + data.getStringExtra("address"));
            tabledata.setEprovince(data.getStringExtra("dwsheng"));
            tabledata.setEcity(data.getStringExtra("dwshi"));
            tabledata.setEcounty(data.getStringExtra("dwxian"));
            tabledata.setEndlongitude(data.getStringExtra("y"));
            tabledata.setEndlatitude(data.getStringExtra("x"));
            morendizhiz = data.getStringExtra("morendizhi");
            if (!qdtext.getText().toString().equals("请选择起点") && !weight.getText().toString().trim().equals("") && !volume.getText().toString().trim().equals("") && isQianYue) {
                initQianYueMoney(tabledata.getSprovince(), tabledata.getScity(),
                        tabledata.getEprovince(), tabledata.getEcity(),
                        tabledata.getEcounty(), weight.getText().toString().trim(),
                        volume.getText().toString().trim());
            }
        } else if (requestCode == 001 && resultCode == 006) {
            contactname.setText(data.getStringExtra("morenname"));
            contactphone.setText(data.getStringExtra("morenphone"));
        }


        if (!qdtext.getText().toString().equals("请选择起点") && !zdtext.getText().toString().equals("请选择终点") && !qdtext.getText().toString().equals("") && !zdtext.getText().toString().equals("")) {
//            if (!qdtext.getText().toString().trim().equals(zdtext.getText().toString().trim())){
            LatLonPoint start = new LatLonPoint(Double.parseDouble(tabledata.getStartlatitude()), Double.parseDouble(tabledata.getStartlongitude()));
            LatLonPoint end = new LatLonPoint(Double.parseDouble(tabledata.getEndlatitude()), Double.parseDouble(tabledata.getEndlongitude()));
            searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);
//            }
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

    private void upload() {
        showWaitDialog("正在刷新信息...");
        Gson gson = new Gson();
        String params = gson.toJson(tabledata);
        PileApi.instance.addKuaiyunOrder(params)
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
                            if (!body.contains("true")) {
                                //   if(body.equals("\"false\""))
                                showToast("提交订单失败，请检查信息后重试");
                            } else {
//                                showpaydialog(Float.parseFloat(tabledata.getSettheprice()), body.replace("\"", ""));

                                final AlertDialog dialog1 = new AlertDialog.Builder(HuoDKuaiyunActivity.this).create();
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

                                TextView tv_success = (TextView) dialog1.getWindow().findViewById(R.id.tv_success);
                                tv_success.setText("订单发布成功");
                                TextView textView = (TextView) dialog1.getWindow().findViewById(R.id.tv_chakan);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        isok = false;
                                        dialog1.dismiss();
                                        finish();
                                        sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                        Intent intent = new Intent(HuoDKuaiyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                        intent.putExtra("type", 2);
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


//                                Gson gson = new Gson();
//                                List<CarPP> CarPPList = gson.fromJson(body, new TypeToken<List<CarPP>>() {
//                                }.getType());
//                                updateInfo(CarPPList);
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
                        //   startActivity(new Intent(ConfirmOrderActivity.this, CustomerPositionActivity.class));
//                        sendPayLocalReceiver(GoodsDetialActivity.class.getName());
//                        sendPayLocalReceiver(ShoppingActivity.class.getName());
//                        sendPayLocalReceiver(ConfirmOrderActivity.class.getName());
//                        sendPayLocalReceiver(myOrderActivity.class.getName());
//                        Intent it = new Intent(ConfirmOrderActivity.this, myOrderActivity.class);
//                        startActivity(it);
//                        finish();

                        final AlertDialog dialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this).create();
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
                                Intent intent = new Intent(HuoDKuaiyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                intent.putExtra("type", 2);
                                startActivity(intent);

//                                        finish();
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
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
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


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
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
        if (Double.parseDouble(tabledata.getSettheprice()) < i) {
            LinearLayout radioButton = (LinearLayout) payalertDialog.getWindow().findViewById(R.id.rb3);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
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
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
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
//        HashMap<String, String> map = new HashMap<>();
//        map.put("", "");
//        Gson gson = new Gson();
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(HuoDKuaiyunActivity.this)
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
                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(HuoDKuaiyunActivity.this);
                                View view = LayoutInflater.from(HuoDKuaiyunActivity.this).inflate(R.layout.dialog_zhifumima, null, false);
                                dialog1.setView(view);
                                alertDialog = dialog1.create();
                                alertDialog.setCancelable(false);
                                ed_password = (EditText) view.findViewById(R.id.ed_password);
                                Button button = (Button) view.findViewById(R.id.btn_queding);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ed_password.getText().toString().trim().equals("")) {
                                            Toast.makeText(HuoDKuaiyunActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                                        } else {
//                                            hintKeyboard();
                                            orderYuEZhiFu(alertDialog, ed_password.getText().toString().trim(), orderid, Float.parseFloat(tabledata.getSettheprice()));
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
                                            PayTask alipay = new PayTask(HuoDKuaiyunActivity.this);
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
                                    new WXPayUtils().init(HuoDKuaiyunActivity.this, map)
                                            .setListener(new WXPayUtils.BackResult() {
                                                @Override
                                                public void getInfo(String prepayId, String sign) {
                                                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                                                            .with(HuoDKuaiyunActivity.this) //activity instance
                                                            .setAppId(Constant.APP_ID) //wechat pay AppID
                                                            .setPartnerId(Constant.MCH_ID)//wechat pay partner id
                                                            .setPrepayId(prepayId)//pre pay id
                                                            .setNonceStr("")
                                                            .setTimeStamp("")//time stamp
                                                            .setSign(sign)//sign
                                                            .create();
                                                    //2. send the request with wechat pay
                                                    PayAPI.getInstance().sendPayRequest(wechatPayReq);
                                                    Constant.WXPAY_STARTNAME = HuoDKuaiyunActivity.class.getName();
//                                                                                wechatPayReq.setOnWechatPayListener(new WechatPayReq.OnWechatPayListener() {
//                                                                                    @Override
//                                                                                    public void onPaySuccess(int errorCode) {
//
////                                                                                        if(PayMethodActivity.instance!=null){
////                                                                                            PayMethodActivity.instance.finish();
////                                                                                        }
////                                                                                        startActivity(new Intent(PayActivity.this,CustomerPositionActivity.class));
////                                                                                        finish();
//                                                                                    }
//
//                                                                                    @Override
//                                                                                    public void onPayFailure(int errorCode) {
//
//                                                                                    }
//                                                                                });
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
        map.put("ordertype", "2");
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
                            //       showToast(body);
                            if ("nologin".equals(body)) {
                                showToast("当前帐号未登录，请检查登录状态");
                            } else if ("false".equals(body) || "".equals(body)) {
                                showToast("支付失败");
                            } else if ("true".equals(body)) {
                                showToast("支付成功");
//                                sendPayLocalReceiver(GoodsDetialActivity.class.getName());
//                                sendPayLocalReceiver(ShoppingActivity.class.getName());
//                                sendPayLocalReceiver(ConfirmOrderActivity.class.getName());
//                                sendPayLocalReceiver();
//                                Intent it = new Intent(ConfirmOrderActivity.this, myOrderActivity.class);
//                                startActivity(it);

                                final AlertDialog dialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this).create();
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
                                        Intent intent = new Intent(HuoDKuaiyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                        intent.putExtra("type", 2);
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
        PileApi.instance.deleteKuaiyunOrder(params)
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
                                    if (Constant.WXPAY_STARTNAME.equals(HuoDKuaiyunActivity.class.getName())) {
                                        Constant.WXPAY_STARTNAME = "";
                                        final AlertDialog dialog = new AlertDialog.Builder(HuoDKuaiyunActivity.this).create();
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
                                                Intent intent = new Intent(HuoDKuaiyunActivity.this, DingDanZhiFuChengGongActivity.class);
                                                intent.putExtra("type", 2);
                                                startActivity(intent);

//                                        finish();
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

    //加载车型
    public void loadcx() {

        PileApi.instance.searchKuaiyunCartype()
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
                            ArrayList<cxbean> temp = new Gson().fromJson(body, new TypeToken<ArrayList<cxbean>>() {
                            }.getType());
                            if (temp.size() == 0) {
                                Toast.makeText(HuoDKuaiyunActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                                return;
                            }
                            cexiangdatas = new ArrayList<ShuJuEntity>();
                            for (cxbean data : temp) {
                                cexiangdatas.add(new ShuJuEntity(Integer.parseInt(data.getId()), data.getCartypename(), false));
                            }

//                            showDuoXuanDiaLog(datas, false, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(HuoDKuaiyunActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //加载货物类型
    public void loadhwlx() {
        PileApi.instance.searchKuaiyunCargotype()
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
                            ArrayList<hwlxBean> temp = new Gson().fromJson(body, new TypeToken<ArrayList<hwlxBean>>() {
                            }.getType());
                            if (temp.size() == 0) {
                                Toast.makeText(HuoDKuaiyunActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
                                return;
                            }
                            ArrayList<ShuJuEntity> datas = new ArrayList<ShuJuEntity>();
                            for (hwlxBean data : temp) {
                                datas.add(new ShuJuEntity(Integer.parseInt(data.getId()), data.getCargotypename(), false));
                            }
                            //    showdialog((TextView) v, temp, type);
                            showDuoXuanDiaLog(datas, true, 2);
                            //    showdialog((TextView) v, temp, type);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(HuoDKuaiyunActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void showDuoXuanDiaLog(final List<ShuJuEntity> list, boolean isOk, final int type) {
        //初始化Dialog
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_item, null);
        //初始化控件
        TextView tv_dismiss = (TextView) inflate.findViewById(R.id.tv_dismiss);
        TextView tv_tltle = (TextView) inflate.findViewById(R.id.tv_tltle);
        TextView cx_bz = (TextView) inflate.findViewById(R.id.cx_bz);
        tv_qtlx = (EditText) inflate.findViewById(R.id.tv_qtlx);
        RelativeLayout qtlx = (RelativeLayout) inflate.findViewById(R.id.qtlx);
        if (type == 1) {
            tv_tltle.setText("选择车型");
            tv_cx_text.setText("请选择");
            cx_bz.setVisibility(View.VISIBLE);
            qtlx.setVisibility(View.GONE);
        } else if (type == 2) {
            qtlx.setVisibility(View.VISIBLE);
            cx_bz.setVisibility(View.GONE);
            tv_qtlx.setText("");
            tv_hwlx.setText("请选择");
            tv_tltle.setText("选择货物类型");
        }
        TextView tv_queren = (TextView) inflate.findViewById(R.id.tv_queren);
        RecyclerView act_recyclerView = (RecyclerView) inflate.findViewById(R.id.act_recyclerView);
        //初始化列表
        MyAdapter adapter = new MyAdapter(this, list, isOk);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        //取消按钮的点击事件
        tv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                dialog = null;
            }


        });
        //确认按钮的点击事件
        tv_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duoxuanlist.clear();
                duoxuanlist = list;
                for (ShuJuEntity temp : duoxuanlist) {
                    if (!tv_qtlx.getText().toString().trim().equals("")) {
                        String text = tv_qtlx.getText().toString();
                        tv_hwlx.setText(tv_qtlx.getText());
                        break;
                    }
                    if (temp.isType()) {
                        if (type == 2) {
                            tv_hwlx.setText(temp.getName());
                            break;
                        } else if (type == 1) {
                            if (tv_cx_text.getText().toString().equals("请选择")) {
                                tv_cx_text.setText(temp.getName());

                            } else {
                                tv_cx_text.setText(tv_cx_text.getText().toString() + "," + temp.getName());

                            }
                        }
                    }
                }
                dialog.dismiss();
                dialog = null;
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);

        WindowManager windowManager = getWindowManager();
        //为获取屏幕宽、高
        Display display = windowManager.getDefaultDisplay();
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置Dialog距离底部的距离
        lp.y = 0;
        //设置Dialog宽度
        lp.width = display.getWidth();
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        //显示对话框
        dialog.show();

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
                    tv_juli11.setText(v + "km");
                    tabledata.setTotal_mileage(v + "");
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
