package com.lx.hd.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.account.AccountHelper;
import com.lx.hd.alipay.OrderInfoUtil2_0;
import com.lx.hd.alipay.PayResult;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.AdditionalDemandListBean;
import com.lx.hd.bean.AdditionalDemandProListBean;
import com.lx.hd.bean.AdditionalDemandProListBean;
import com.lx.hd.bean.DeliverGoodsUploadBean;
import com.lx.hd.wxapi.WXPayEntryActivity;
import com.lx.paylib.PayAPI;
import com.lx.paylib.WechatPayReq;
import com.lx.paylib.wxutils.WXPayUtils;
import com.lx.hd.R;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
确认物流订单
 */
public class LogisticsConfirmationOrderActivity extends BaseActivity {
    private ImageView img_icon;
    private RelativeLayout ewxq;
    private TextView jgmx, send_time, ewxq_text;
    private TextView yhj, tv_fwxy;
    private boolean ispayok = false;
    private ImageView img_back, xygx;
    private String zjg, zlc, data;
    private boolean isyd = true;
    private static final int SDK_PAY_FLAG = 1;
    private AlertDialog alertDialog;
    private static final int SDK_AUTH_FLAG = 2;
    private Double superzjg;
    private EditText ed_password;
    private EditText lxr, lxdh, bz;
    private DeliverGoodsUploadBean tabledata;
    private TextView submit;
    private ArrayList<AdditionalDemandProListBean> datas = new ArrayList<AdditionalDemandProListBean>();//额外需求
    private String cs = "";
    private double i;
    private boolean isok = true;
    private AlertDialog payalertDialog;
    protected LocalBroadcastManager mManager1;
    private BroadcastReceiver mReceiver1;
    private EditText lxdh1;

    @Override
    protected int getContentView() {
        return R.layout.activity_logistics_confirmation_order;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
//        setTitleIcon(R.mipmap.icon_car_home_samall);
//        setTitleText("车辆租赁");
        img_icon = (ImageView) findViewById(R.id.img_icon);
        img_icon.setVisibility(View.GONE);
        ewxq = (RelativeLayout) findViewById(R.id.ewxq);
        img_back = (ImageView) findViewById(R.id.img_back);
        send_time = (TextView) findViewById(R.id.send_time);
        tv_fwxy = (TextView) findViewById(R.id.tv_fwxy);
        xygx = (ImageView) findViewById(R.id.xygx);
        jgmx = (TextView) findViewById(R.id.jgmx);
        yhj = (TextView) findViewById(R.id.yhj);
        ewxq_text = (TextView) findViewById(R.id.ewxq_text);
        lxdh = (EditText) findViewById(R.id.lxdh);
        lxdh1 = (EditText) findViewById(R.id.lxdh1);
        bz = (EditText) findViewById(R.id.bz);
        lxr = (EditText) findViewById(R.id.lxr);
        submit = (TextView) findViewById(R.id.submit);
        String jsondata = getIntent().getStringExtra("tabledata");
        zjg = getIntent().getStringExtra("zjg");
        superzjg = Double.parseDouble(zjg);
        zlc = getIntent().getStringExtra("zlc");
        data = getIntent().getStringExtra("data");
        cs = getIntent().getStringExtra("cs");
        registerWXPayLocalReceiver();
        tabledata = new Gson().fromJson(jsondata, DeliverGoodsUploadBean.class);
        yhj.setText(tabledata.getOwner_totalprice());
        send_time.setText(tabledata.getOwner_sendtime());
        jgmx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LogisticsConfirmationOrderActivity.this, DeliverPriceDetailActivity.class);
                intent1.putExtra("data", data);
                intent1.putExtra("zjg", zjg);
                intent1.putExtra("zlc", zlc);
                intent1.putExtra("cs", cs);
                startActivity(intent1);
            }
        });
        xygx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isyd) {
                    xygx.setImageResource(R.mipmap.sfty_wxz);
                    isyd = false;
                } else {
                    xygx.setImageResource(R.mipmap.sfty_xz);
                    isyd = true;
                }
            }
        });
        ewxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("cs", cs);
                bundle.putString("type", "1");
                bundle.putString("data", new Gson().toJson(datas));
                Intent intent = new Intent(LogisticsConfirmationOrderActivity.this, AdditionalDemandActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 001);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences sp = getSharedPreferences("userxinxi", Context.MODE_PRIVATE);
//        lxr.setText(sp.getString("custname", ""));
//        lxdh.setText(sp.getString("custphone", ""));
        lxdh1.setText(sp.getString("custphone", ""));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabledata.setOwner_link_name(lxr.getText().toString());
                tabledata.setOwner_link_phone(lxdh.getText().toString());
                tabledata.setConsignorphone(lxdh1.getText().toString());
                tabledata.setOwner_note(bz.getText().toString());
                tabledata.setLease_order_detailList(datas);

                if (lxr.getText().toString().equals("必填") || tabledata.getOwner_link_name().equals("") || lxr.getText().toString().equals("")) {
                    showToast("请填写收货人姓名");
                    return;
                }
                if (lxdh.getText().toString().equals("必填") || tabledata.getOwner_link_phone().equals("") || lxdh.getText().toString().equals("")) {
                    showToast("请填写收货人联系电话");
                    return;
                }
                if (lxdh.getText().toString().trim().length()!=11) {
                    showToast("收货人联系电话格式不正确");
                    return;

                }
                if (lxdh1.getText().toString().equals("必填") || tabledata.getConsignorphone().equals("") || lxdh1.getText().toString().equals("")) {
                    showToast("请填写发货人联系电话");
                    return;
                }
                if (lxdh1.getText().toString().trim().length()!=11) {
                    showToast("发货人联系电话格式不正确");
                    return;

                }

                AlertDialog.Builder builder=new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this)
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
                                if (datas.size() == 0) {
                                    searchCarServices();//加载默认的免费额外需求后提交
                                } else {
                                    if (isyd) {
                                        upload();//直接提交
                                    } else {
                                        showToast("请先阅读并同意《货物托运服务协议》");
                                    }
                                }


                            }
                        });
                builder.show();

            }
        });
        tv_fwxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogisticsConfirmationOrderActivity.this, LogisticsAgreementActivity.class));
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
                                AlertDialog.Builder builder=new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this)
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

        if (requestCode == 001 && resultCode == 004) {
            String datajson = data.getStringExtra("datas");
            String titles = data.getStringExtra("titles");
            datas.clear();
            datas = new Gson().fromJson(datajson, new TypeToken<List<AdditionalDemandProListBean>>() {
            }.getType());
            double ewjg = 0.0;
            zjg = superzjg + "";
            for (int i = 0; i < datas.size(); i++) {
                if (Double.parseDouble(datas.get(i).getOwner_service_price()) > 0) {
                    ewjg = ewjg + (superzjg * Double.parseDouble(datas.get(i).getOwner_service_price()));
                    datas.get(i).setOwner_service_price(superzjg * Double.parseDouble(datas.get(i).getOwner_service_price()) + "");
                }
            }
            zjg = (Double.parseDouble(zjg) + ewjg) + "";
            BigDecimal b = new BigDecimal(zjg);
            zjg = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
            tabledata.setOwner_totalprice(zjg);
            //  showToast(zjg);
            ewxq_text.setText(titles);
            yhj.setText(zjg);
        }

    }

    private void upload() {
        showWaitDialog("正在刷新信息...");
        Gson gson = new Gson();
        String params = gson.toJson(tabledata);
        PileApi.instance.addSendCarOrder(params)
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
                                showToast("提交订单失败，请检查信息后重试");
                            } else {
//                                showpaydialog(Float.parseFloat(tabledata.getOwner_totalprice()), body.replace("\"", ""));


                                final AlertDialog dialog1 = new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this).create();
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
                                        sendPayLocalReceiver(HuoDSuyunActivity.class.getName());
                                        Intent intent = new Intent(LogisticsConfirmationOrderActivity.this, DingDanZhiFuChengGongActivity.class);
                                        intent.putExtra("type", 1);
                                        startActivity(intent);


//                                        finish();
                                    }
                                });
                                dialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                        sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                        sendPayLocalReceiver(HuoDSuyunActivity.class.getName());
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

    private void searchCarServices() {
        showWaitDialog("正在加载额外需求...");
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
                                datas.clear();
                                for (AdditionalDemandListBean temp : AdditionalDemandList) {
                                    if (temp.getService_price() == 0) {
                                        datas.add(new AdditionalDemandProListBean(temp.getId() + "", temp.getService_price() + "", "logistics_owner_orderDetails"));
                                    }
                                }

                                if (isyd) {
                                    upload();
                                } else {
                                    showToast("请先阅读并同意《货物托运服务协议》");
                                }
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

                        final AlertDialog dialog = new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this).create();
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
                                Intent intent = new Intent(LogisticsConfirmationOrderActivity.this, DingDanZhiFuChengGongActivity.class);
                                intent.putExtra("type", 1);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this)
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
                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this);
                                View view = LayoutInflater.from(LogisticsConfirmationOrderActivity.this).inflate(R.layout.dialog_zhifumima, null, false);
                                dialog1.setView(view);
                                alertDialog = dialog1.create();
                                alertDialog.setCancelable(false);
                                ed_password = (EditText) view.findViewById(R.id.ed_password);
                                Button button = (Button) view.findViewById(R.id.btn_queding);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ed_password.getText().toString().trim().equals("")) {
                                            Toast.makeText(LogisticsConfirmationOrderActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                                        } else {
//                                            hintKeyboard();
                                            orderYuEZhiFu(alertDialog, ed_password.getText().toString().trim(), orderid, Float.parseFloat(tabledata.getOwner_totalprice()));
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
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this)
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


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this)
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
        if (Double.parseDouble(tabledata.getOwner_totalprice()) < i) {
            LinearLayout radioButton = (LinearLayout) payalertDialog.getWindow().findViewById(R.id.rb3);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this)
                            .setTitle("提示")
                            .setMessage("确定要支付吗")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //  showToast(Float.parseFloat(tabledata.getOwner_totalprice()) + "");
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
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this)
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
                                    Map<String, String> params = OrderInfoUtil2_0.wuliubuildOrderParamMap(Constant.APPID, rsa2, confirmMoney + "", orderid + "_" + AccountHelper.getUserId());
                                    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                                    String privateKey = Constant.RSA_PRIVATE;  //私钥
                                    String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                                    final String orderInfo = orderParam + "&" + sign;

                                    Runnable payRunnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            PayTask alipay = new PayTask(LogisticsConfirmationOrderActivity.this);
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
                                    new WXPayUtils().init(LogisticsConfirmationOrderActivity.this, map)
                                            .setListener(new WXPayUtils.BackResult() {
                                                @Override
                                                public void getInfo(String prepayId, String sign) {
                                                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                                                            .with(LogisticsConfirmationOrderActivity.this) //activity instance
                                                            .setAppId(Constant.APP_ID) //wechat pay AppID
                                                            .setPartnerId(Constant.MCH_ID)//wechat pay partner id
                                                            .setPrepayId(prepayId)//pre pay id
                                                            .setNonceStr("")
                                                            .setTimeStamp("")//time stamp
                                                            .setSign(sign)//sign
                                                            .create();
                                                    //2. send the request with wechat pay
                                                    PayAPI.getInstance().sendPayRequest(wechatPayReq);
                                                    Constant.WXPAY_STARTNAME = LogisticsConfirmationOrderActivity.class.getName();
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
        map.put("ordertype", "0");
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
                            //   showToast(body);
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

                                final AlertDialog dialog = new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this).create();
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
                                        sendPayLocalReceiver(HuoDSuyunActivity.class.getName());
                                        Intent intent = new Intent(LogisticsConfirmationOrderActivity.this, DingDanZhiFuChengGongActivity.class);
                                        intent.putExtra("type", 1);
                                        startActivity(intent);
                                    }
                                });
                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                        sendPayLocalReceiver(DeliverGoodsActivity.class.getName());
                                        sendPayLocalReceiver(HuoDSuyunActivity.class.getName());
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
        PileApi.instance.deleteShipperSendOrder(params)
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
                                    if (Constant.WXPAY_STARTNAME.equals(LogisticsConfirmationOrderActivity.class.getName())) {
                                        Constant.WXPAY_STARTNAME = "";
                                        final AlertDialog dialog = new AlertDialog.Builder(LogisticsConfirmationOrderActivity.this).create();
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
                                                Intent intent = new Intent(LogisticsConfirmationOrderActivity.this, DingDanZhiFuChengGongActivity.class);
                                                intent.putExtra("type", 1);
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
    }
}
