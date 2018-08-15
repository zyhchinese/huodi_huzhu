package com.lx.hd.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.AccountHelper;
import com.lx.hd.adapter.KaiPiaoAdapter;
import com.lx.hd.adapter.QianYueJieSuanAdapter;
import com.lx.hd.alipay.OrderInfoUtil2_0;
import com.lx.hd.alipay.PayResult;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.KaiPiaoEntity;
import com.lx.hd.bean.LogisticsOrderDetailsEntity;
import com.lx.hd.bean.QianYueJieSuanEntity;
import com.lx.hd.bean.selectAllAreaBean;
import com.lx.paylib.PayAPI;
import com.lx.paylib.WechatPayReq;
import com.lx.paylib.wxutils.WXPayUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class QianYueJieSuanActivity extends BackCommonActivity {

    private RecyclerView act_recyclerView;
    private CheckBox img_xuanshang;
    private TextView tv_total_money;
    private Button btn_next;
    private List<QianYueJieSuanEntity> qianYueJieSuanEntityList;
    private String orderno="";
    private String uuid="";
    private int number=0;
    private AlertDialog payalertDialog;
    private double i;
    private AlertDialog alertDialog;
    private EditText ed_password;
    private boolean isok = false;
    private boolean ispayok = false;
    private QianYueJieSuanAdapter adapter;
    private static final int SDK_PAY_FLAG = 1;
    protected LocalBroadcastManager mManager1;
    private BroadcastReceiver mReceiver1;
    @Override
    protected int getContentView() {
        return R.layout.activity_qian_yue_jie_suan;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("签约用户结算");
        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);
        img_xuanshang= (CheckBox) findViewById(R.id.img_xuanshang);
        tv_total_money= (TextView) findViewById(R.id.tv_total_money);
        btn_next= (Button) findViewById(R.id.btn_next);


        initShuju();
        getyeData();
        registerWXPayLocalReceiver();
    }

    private void initShuju() {
        PileApi.instance.selectSignOrders()
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
                            JSONArray response = jsonObject.getJSONArray("response");
                            if (flag.equals("100")){
                                showToast("请检查您的登录状态");
                            }else if (flag.equals("210")){
                                showToast("暂无数据");
                                if (qianYueJieSuanEntityList!=null&&adapter!=null){
                                    qianYueJieSuanEntityList.clear();
                                    adapter.notifyDataSetChanged();
                                }
                            }else if (flag.equals("200")){
                                Gson gson=new Gson();
                                qianYueJieSuanEntityList=gson.fromJson(String.valueOf(response),new TypeToken<List<QianYueJieSuanEntity>>(){}.getType());
                                initRecyclerView();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(QianYueJieSuanActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initRecyclerView() {

        adapter=new QianYueJieSuanAdapter(this,qianYueJieSuanEntityList,img_xuanshang,tv_total_money,btn_next);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setZhiFuChuang(new QianYueJieSuanAdapter.ZhiFuChuang() {
            @Override
            public void onClick(int position,String qian) {
                for (QianYueJieSuanEntity entity:qianYueJieSuanEntityList){
                    if (entity.isLine()){
                        orderno=orderno+entity.getOrderno()+",";
                        uuid=uuid+entity.getType()+"-"+entity.getUuid()+",";
                        number++;
                    }
                }
                orderno=orderno.substring(0,orderno.length()-1);
                uuid=uuid.substring(0,uuid.length()-1);
                showpaydialog(qian);
            }
        });
        adapter.setOnClickItem(new QianYueJieSuanAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                if (qianYueJieSuanEntityList.get(position).getType().equals("0")){
                    Intent intent=new Intent(QianYueJieSuanActivity.this,LogisticsOrderDetailsActivity1.class);
                    intent.putExtra("orderno",qianYueJieSuanEntityList.get(position).getOrderno());
                    intent.putExtra("line","0");
                    startActivity(intent);
                }else if (qianYueJieSuanEntityList.get(position).getType().equals("1")){
                    Intent intent=new Intent(QianYueJieSuanActivity.this,LogisticsOrderDetailsActivity.class);
                    intent.putExtra("orderno",qianYueJieSuanEntityList.get(position).getOrderno());
                    intent.putExtra("line","1");
                    startActivity(intent);
                }else if (qianYueJieSuanEntityList.get(position).getType().equals("2")){
                    Intent intent=new Intent(QianYueJieSuanActivity.this,LogisticsOrderDetailsActivity2.class);
                    intent.putExtra("orderno",qianYueJieSuanEntityList.get(position).getOrderno());
                    intent.putExtra("line","2");
                    startActivity(intent);
                }
            }
        });
    }

    private void showpaydialog(final String qian) {

        payalertDialog = new AlertDialog.Builder(this).create();
        payalertDialog.show();
        payalertDialog.getWindow().setContentView(R.layout.layout_pay2);
        payalertDialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams p = payalertDialog.getWindow().getAttributes();
        p.width = (int) (display.getWidth() * 0.8);
        payalertDialog.getWindow().setAttributes(p);
        payalertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                orderno="";
                uuid="";
                number=0;
            }
        });
        payalertDialog.getWindow()
                .findViewById(R.id.rb1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(QianYueJieSuanActivity.this)
                                .setTitle("提示")
                                .setMessage("确定要支付吗")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        startPay(summoney, 0, orderid);
//                                        deletepay(orderid);
//                                        showToast("正在开发中");
                                        pinJieUuid(Float.parseFloat(qian),0,number+"",uuid);
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


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(QianYueJieSuanActivity.this)
                                .setTitle("提示")
                                .setMessage("确定要支付吗")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        startPay(summoney, 1, orderid);
//                                        deletepay(orderid);
//                                        showToast("正在开发中");
                                        pinJieUuid(Float.parseFloat(qian),1,number+"",uuid);
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
        if (Double.parseDouble(qian) < i) {
            LinearLayout radioButton = (LinearLayout) payalertDialog.getWindow().findViewById(R.id.rb3);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(QianYueJieSuanActivity.this)
                            .setTitle("提示")
                            .setMessage("确定要支付吗")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //  showToast(Float.parseFloat(tabledata.getOwner_totalprice()) + "");
                                    //orderYuEZhiFu(orderid, Float.parseFloat(tabledata.getSettheprice()));

                                    //检查支付密码
                                    initCheckPassword(qian);


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
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(QianYueJieSuanActivity.this)
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

    private void pinJieUuid(final float qian, final int type, String num, String uuid){
        HashMap<String, String> map = new HashMap<>();
        map.put("selectsize", num);
        map.put("typeuuids", uuid);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.payBefGetUuid(data)
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
                                JSONArray response = jsonObject.getJSONArray("response");
                                JSONObject jsonObject1 = response.getJSONObject(0);
                                String newuuid = jsonObject1.getString("newuuid");

                                startPay(qian,type,newuuid);

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
                                            PayTask alipay = new PayTask(QianYueJieSuanActivity.this);
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
                                    new WXPayUtils().init(QianYueJieSuanActivity.this, map)
                                            .setListener(new WXPayUtils.BackResult() {
                                                @Override
                                                public void getInfo(String prepayId, String sign) {
                                                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                                                            .with(QianYueJieSuanActivity.this) //activity instance
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

                        payalertDialog.dismiss();
                        initShuju();
                        QianYueJieSuanActivity.this.orderno="";
                        QianYueJieSuanActivity.this.uuid="";
                        number=0;
                        img_xuanshang.setButtonDrawable(R.mipmap.img_huodixuanze);
                        tv_total_money.setText("共"+"0"+"元");

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

                                        payalertDialog.dismiss();
                                        initShuju();
                                        QianYueJieSuanActivity.this.orderno="";
                                        QianYueJieSuanActivity.this.uuid="";
                                        number=0;
                                        img_xuanshang.setButtonDrawable(R.mipmap.img_huodixuanze);
                                        tv_total_money.setText("共"+"0"+"元");
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

    private void initCheckPassword(final String qian) {
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(QianYueJieSuanActivity.this)
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
                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(QianYueJieSuanActivity.this);
                                View view = LayoutInflater.from(QianYueJieSuanActivity.this).inflate(R.layout.dialog_zhifumima, null, false);
                                dialog1.setView(view);
                                alertDialog = dialog1.create();
                                alertDialog.setCancelable(false);
                                ed_password = (EditText) view.findViewById(R.id.ed_password);
                                Button button = (Button) view.findViewById(R.id.btn_queding);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ed_password.getText().toString().trim().equals("")) {
                                            Toast.makeText(QianYueJieSuanActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                                        } else {
//                                            hintKeyboard();
                                            orderYuEZhiFu(alertDialog, ed_password.getText().toString().trim(), orderno, Float.parseFloat(qian));
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
    private void orderYuEZhiFu(final AlertDialog alertDialog, String password, String orderno, float totalmoney) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("ordernos", orderno);
        map.put("paypassword", password);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectSignOrdersToPay(params)
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
                            System.out.println(body);
                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("100")) {
                                showToast("请检查您的登录状态");
                            } else if (flag.equals("310")){
                                showToast("参数异常");
                            } else if (flag.equals("320")){
                                showToast("您未设置支付密码");
                            } else if (flag.equals("330")){
                                showToast("余额不足，请充值");
                            } else if (flag.equals("340")){
                                showToast("支付密码不正确");
                            } else if (flag.equals("350")){
                                showToast("订单金额异常");
                            } else if (flag.equals("300")){
                                showToast("支付失败");
                            } else if (flag.equals("200")) {
                                showToast("支付成功");
                                payalertDialog.dismiss();
                                alertDialog.dismiss();
                                initShuju();
                                QianYueJieSuanActivity.this.orderno="";
                                QianYueJieSuanActivity.this.uuid="";
                                number=0;
                                img_xuanshang.setButtonDrawable(R.mipmap.img_huodixuanze);
                                tv_total_money.setText("共"+"0"+"元");

//                                final AlertDialog dialog = new AlertDialog.Builder(QianYueJieSuanActivity.this).create();
//                                ispayok = true;
//                                dialog.show();
//                                dialog.getWindow().setContentView(R.layout.dialog_zhifuchenggong);
//                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);
//
//                                WindowManager windowManager = getWindowManager();
//                                Display display = windowManager.getDefaultDisplay();
//                                WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
//                                p.width = (int) (display.getWidth() * 0.6);
//                                dialog.getWindow().setAttributes(p);
//
//                                TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
//                                textView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        isok = false;
//                                        dialog.dismiss();
//                                        initShuju();
//
//                                    }
//                                });
                            } else {
                                showToast(body);
                            }
                            hideWaitDialog();
                            //
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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
}
