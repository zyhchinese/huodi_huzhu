package com.lx.hd.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lx.hd.R;
import com.lx.hd.adapter.PayMoneyGridViewAdapter;
import com.lx.hd.alipay.AuthResult;
import com.lx.hd.alipay.OrderInfoUtil2_0;
import com.lx.hd.alipay.PayResult;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.widgets.MyGridView;
import com.lx.paylib.AliPayAPI;
import com.lx.paylib.AliPayReq;
import com.lx.paylib.PayAPI;
import com.lx.paylib.WechatPayReq;
import com.lx.paylib.wxutils.WXPayUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class PayActivity extends BackCommonActivity {
    private ArrayList<String> payList;
    private MyGridView mGridView;
    PayMoneyGridViewAdapter payMoneyGridViewAdapter;
    private RadioButton rb0, rb1;
    private EditText etInputMoney;
    private TextView tvPayConfirm;
    int index = 0;
    int payType = 0;//0代表支付宝，1代表微信
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private boolean isSelectOrInput = false;
    protected LocalBroadcastManager mManager1;
    private BroadcastReceiver mReceiver1;
    private int confirmMoney = 0;
    public static Activity instance;
    private boolean gongneng=true;

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
                        startActivity(new Intent(PayActivity.this, CustomerPositionActivity.class));
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        instance = this;
        setTitleText("账户充值");
        etInputMoney = (EditText) findViewById(R.id.et_money_input);
        payList = new ArrayList<>();
        payList.add("30元");
        payList.add("50元");
        payList.add("100元");
        payList.add("200元");
        payList.add("300元");
        payList.add("500元");
        rb0 = (RadioButton) findViewById(R.id.rb0);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        registerWXPayLocalReceiver();
        payMoneyGridViewAdapter = new PayMoneyGridViewAdapter(this, payList);
        mGridView = (MyGridView) findViewById(R.id.gv_sing_style);
        tvPayConfirm = (TextView) findViewById(R.id.tv_pay_confirm);
        mGridView.setAdapter(payMoneyGridViewAdapter);
        tvPayConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gongneng){
                    showToast("正在开发中");
                    return;
                }
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

                                        PileApi.instance.getPayOrderNo()
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
                                                            if (body.length() < 5) {
                                                                return;
                                                            }

                                                            body = body.substring(1, body.length() - 1);

                                                            if (!etInputMoney.getText().toString().trim().equals("")) {
                                                                confirmMoney = Integer.parseInt(etInputMoney.getText().toString().trim());
                                                            }
                                                            if (!isSelectOrInput) {
                                                                String asd = payList.get(0).toString();
                                                                asd = asd.substring(0, asd.length() - 1);
                                                                confirmMoney = Integer.parseInt(asd);
                                                            }
                                                            if (confirmMoney == 0)
                                                                return;

                                                            //支付宝支付
                                                            if (payType == 0) {

                                                                /**
                                                                 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                                                                 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                                                                 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                                                                 *
                                                                 * orderInfo的获取必须来自服务端；
                                                                 */
                                                                boolean rsa2 = (Constant.RSA_PRIVATE.length() > 0);
                                                                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Constant.APPID, rsa2, confirmMoney + "", body);
                                                                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

                                                                String privateKey = Constant.RSA_PRIVATE;  //私钥
                                                                String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                                                                final String orderInfo = orderParam + "&" + sign;

                                                                Runnable payRunnable = new Runnable() {

                                                                    @Override
                                                                    public void run() {
                                                                        PayTask alipay = new PayTask(PayActivity.this);
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

                                                                map.put("orderNo", body);                //订单号
                                                                map.put("orderMoney", confirmMoney * 100);//支付金额
                                                                //     map.put("orderMoney",1);
                                                                map.put("notify_url", Constant.BASE_URL + "weixin/weixinzhifuaa");
                                                                map.put("body", "商品描述");
                                                                new WXPayUtils().init(PayActivity.this, map)
                                                                        .setListener(new WXPayUtils.BackResult() {
                                                                            @Override
                                                                            public void getInfo(String prepayId, String sign) {
                                                                                WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                                                                                        .with(PayActivity.this) //activity instance
                                                                                        .setAppId(Constant.APP_ID) //wechat pay AppID
                                                                                        .setPartnerId(Constant.MCH_ID)//wechat pay partner id
                                                                                        .setPrepayId(prepayId)//pre pay id
                                                                                        .setNonceStr("")
                                                                                        .setTimeStamp("")//time stamp
                                                                                        .setSign(sign)//sign
                                                                                        .create();
                                                                                //2. send the request with wechat pay
                                                                                PayAPI.getInstance().sendPayRequest(wechatPayReq);
                                                                                Constant.WXPAY_STARTNAME = PayActivity.class.getName();
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
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etInputMoney.setText("");
                isSelectOrInput = true;
                payMoneyGridViewAdapter.setSeclect(i);
                payMoneyGridViewAdapter.notifyDataSetChanged();
                switch (i) {
                    case 0:
                        confirmMoney = 30;
                        break;
                    case 1:
                        confirmMoney = 50;
                        break;
                    case 2:
                        confirmMoney = 100;
                        break;
                    case 3:
                        confirmMoney = 200;
                        break;
                    case 4:
                        confirmMoney = 300;
                        break;
                    case 5:
                        confirmMoney = 500;
                        break;
                    default:
                        confirmMoney = 0;
                }

                index = i;

            }
        });

        etInputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                payMoneyGridViewAdapter.setSeclect(-1);
                isSelectOrInput = true;
                if (s.toString().trim().length() != 0) {
                    confirmMoney = Integer.parseInt(s.toString().trim());
                }
            }
        });

        rb0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb1.setChecked(false);
                    payType = 0;
                }
            }
        });
        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb0.setChecked(false);
                    payType = 1;
                }
            }
        });
    }

    private boolean isWXAppInstalledAndSupported() {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(Constant.APP_ID);

        boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled()
                && msgApi.isWXAppSupportAPI();

        return sIsWXAppInstalledAndSupported;
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
                                    if (Constant.WXPAY_STARTNAME.equals(PayActivity.class.getName())) {
                                        Constant.WXPAY_STARTNAME = "";
                                        startActivity(new Intent(PayActivity.this, CustomerPositionActivity.class));
                                    }
                                    break;
                                case -2:
                                    //    showToast("取消支付");
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
