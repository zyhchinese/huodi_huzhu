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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.AccountHelper;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.adapter.OrderAdapter;
import com.lx.hd.adapter.SpinnerAdapter;
import com.lx.hd.alipay.OrderInfoUtil2_0;
import com.lx.hd.alipay.PayResult;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CarPP;
import com.lx.hd.bean.ProvinceEntity;
import com.lx.hd.bean.QueryListEntity;
import com.lx.hd.bean.ShoppingEntity;
import com.lx.hd.utils.DialogHelper;
import com.lx.paylib.PayAPI;
import com.lx.paylib.WechatPayReq;
import com.lx.paylib.wxutils.WXPayUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ConfirmOrderActivity extends BackCommonActivity implements View.OnClickListener {
    private RecyclerView act_confirm_recyclerView;
    private TextView tv_number, tv_money1, tv_yuan;
    private TextView tv_jifen;
    private Button btn_pay;
    private LinearLayout linear;
    private boolean isLogin = false;
    private List<ShoppingEntity> list;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private TextView tv_name, tv_phone, tv_address;
    private float summoney;
    private float summoney1;
    private double i;
    private String orderid;
    private String uuid;
    private String type;
    private List<QueryListEntity> queryList;
    private double jinfen;
    private RelativeLayout isyh;
    private TextView zxzftext;
    private TextView yhje;
    private int id = 0;//优惠券ID
    private Double jg = 0.00;
    protected LocalBroadcastManager mManager1;
    private BroadcastReceiver mReceiver1;
    private boolean zhuangtai=true;

    @Override
    protected int getContentView() {
        return R.layout.activity_confirm;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("确认订单");

        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");
        type = intent.getStringExtra("type");

        initView();
        registerWXPayLocalReceiver();
//        //请求地址信息
//        initAddressHttp();
        //请求列表和列表之外的信息
        getCarHomeListData();
        //余额
        getCarHomeData();
        //积分
        initYueHttp();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //请求地址信息
        initAddressHttp();
    }

    //积分余额
    private void initYueHttp() {

        PileApi.instance.getCustScores()
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
                                showToast("获取信息失败，请重试");
                            } else {

                                body = body.substring(11, body.length() - 2);
                                System.out.println(body);

                                jinfen = Double.parseDouble(body);


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

    private void initAddressHttp() {

        PileApi.instance.searchDefaultAddress()
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
                                showToast("暂无地址信息，请添加");
                            } else {

                                Gson gson = new Gson();
                                queryList = gson.fromJson(body, new TypeToken<List<QueryListEntity>>() {
                                }.getType());
                                tv_name.setText(queryList.get(0).getShcustname());
                                tv_phone.setText(queryList.get(0).getShphone());
                                tv_address.setText(queryList.get(0).getAddress());


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

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);


    }


    private void getCarHomeListData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getProductOrder_0(params)
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
                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                list = gson.fromJson(body, new TypeToken<List<ShoppingEntity>>() {
                                }.getType());
                                updateInfo(list);
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

    private void updateInfo(List<ShoppingEntity> list) {
        OrderAdapter adapter = new OrderAdapter(ConfirmOrderActivity.this, list, type);
        LinearLayoutManager manager = new LinearLayoutManager(ConfirmOrderActivity.this);
        act_confirm_recyclerView.setLayoutManager(manager);
        act_confirm_recyclerView.setAdapter(adapter);

        tv_number.setText("共计" + list.get(0).getTotalcount() + "件商品");
        tv_money1.setText("¥"+list.get(0).getTotalmoney() + " / "+list.get(0).getTotaljifen() + "积分");
        tv_jifen.setText(list.get(0).getTotaljifen() + "");
        summoney = (float) list.get(0).getTotalmoney();
        summoney1 = (float) list.get(0).getTotaljifen();
        orderid = list.get(0).getOrderno();
    }

    private void initView() {
        act_confirm_recyclerView = (RecyclerView) findViewById(R.id.act_confirm_recyclerView);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_money1 = (TextView) findViewById(R.id.tv_money1);
        linear = (LinearLayout) findViewById(R.id.linear);
        linear.setOnClickListener(this);
        btn_pay = (Button) findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(this);
        yhje = (TextView) findViewById(R.id.yhje);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_yuan = (TextView) findViewById(R.id.tv_yuan);
        isyh = (RelativeLayout) findViewById(R.id.isyh);
        zxzftext = (TextView) findViewById(R.id.zxzftext);
        tv_jifen = (TextView) findViewById(R.id.tv_jifen);

        if (type.equals("1")) {
            tv_yuan.setText("元");
            btn_pay.setText("去支付");
            isyh.setVisibility(View.VISIBLE);
            zxzftext.setText("在线支付（支付宝、微信、余额支付）");
        } else {
            isyh.setVisibility(View.GONE);
            tv_yuan.setText("积分");
            btn_pay.setText("积分支付");
            zxzftext.setText("在线支付（积分支付）");
        }
        isyh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int requestCode = 1;
                Intent intent2 = new Intent(ConfirmOrderActivity.this, CouponActivity.class);
                intent2.putExtra("code", 1);
                startActivityForResult(intent2, requestCode);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 2) {
//Intent intent = getIntent();   // data 本身就是一个 Inten  所以不需要再new了 直接调用里面的方法就行了
            int thisid = Integer.parseInt(data.getStringExtra("id"));
            id = thisid;
            BigDecimal bd = new BigDecimal(data.getStringExtra("jg"));
            jg = bd.doubleValue();
            if (jg >= summoney) {
                showToast("优惠券金额不可大于等于商品金额");
                yhje.setVisibility(View.GONE);
                getCarHomeListData();
                id = 0;
                return;
            }

            yhje.setText("优惠金额" + jg + "元");
            DecimalFormat decimalFormat = new DecimalFormat("###0.00");
            tv_money1.setText(decimalFormat.format(summoney - jg));
            yhje.setVisibility(View.VISIBLE);
        }
        if (requestCode == 8 && resultCode == 9) {
            String name = data.getStringExtra("name");
            String number = data.getStringExtra("number");
            String address = data.getStringExtra("address");
            tv_name.setText(name);
            tv_phone.setText(number);
            tv_address.setText(address);
        }

    }

    //请求余额信息
    private void getCarHomeData() {

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

                                isLogin = true;
                                Intent intent1 = new Intent(ConfirmOrderActivity.this, AddressActivity.class);
                                intent1.putExtra("line", 1);
//                                startActivity(intent1);
                                startActivityForResult(intent1, 8);
                            } else {
                                isLogin = false;
                                DialogHelper.getConfirmDialog(ConfirmOrderActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ConfirmOrderActivity.this, LoginActivity.class));
                                    }
                                }, null).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLogin = false;
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:


                if ("".equals(tv_address.getText().toString())) {
                    showToast("请先添加地址！");
                    return;
                }
                if (type.equals("1")) {

                    final AlertDialog builder = new AlertDialog.Builder(this).create();
                    builder.show();
                    builder.getWindow().setContentView(R.layout.layout_pay2);
                    builder.getWindow()
                            .findViewById(R.id.rb1)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmOrderActivity.this)
                                            .setTitle("提示")
                                            .setMessage("确定要支付吗")
                                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
//                                                    if (zhuangtai){
//                                                        showToast("正在开发中");
//                                                        return;
//                                                    }
                                                    startPay((float) (summoney - jg), 0, orderid);
//                                                    startPay(0.01f, 0, orderid);
                                                    QueryListEntity tempdz = queryList.get(0);
                                                    uploadAddress(tempdz.getProvinceid(), tempdz.getCityid(), tempdz.getAreaid(), tempdz.getAddress(), tempdz.getShphone(), tempdz.getShcustname(), uuid);
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

                                    builder.dismiss();
                                }
                            });
                    builder.getWindow()
                            .findViewById(R.id.rb2)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmOrderActivity.this)
                                            .setTitle("提示")
                                            .setMessage("确定要支付吗")
                                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
//                                                    if (zhuangtai){
//                                                        showToast("正在开发中");
//                                                        return;
//                                                    }
                                                    startPay((float) (summoney - jg), 1, orderid);
//                                                    startPay(0.01f, 1, orderid);
                                                    QueryListEntity tempdz = queryList.get(0);
                                                    uploadAddress(tempdz.getProvinceid(), tempdz.getCityid(), tempdz.getAreaid(), tempdz.getAddress(), tempdz.getShphone(), tempdz.getShcustname(), uuid);
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

                                    builder.dismiss();
                                }
                            });

                    LinearLayout radioButton = (LinearLayout) builder.getWindow().findViewById(R.id.rb3);
                    if (i >= list.get(0).getTotalmoney()) {
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmOrderActivity.this)
                                        .setTitle("提示")
                                        .setMessage("确定要支付吗")
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                orderYuEZhiFu(orderid, summoney);
                                                QueryListEntity tempdz = queryList.get(0);
                                                uploadAddress(tempdz.getProvinceid(), tempdz.getCityid(), tempdz.getAreaid(), tempdz.getAddress(), tempdz.getShphone(), tempdz.getShcustname(), uuid);
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

                                builder.dismiss();
                            }
                        });
                    } else {
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                builder.dismiss();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ConfirmOrderActivity.this)
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


                    TextView textView = (TextView) builder.getWindow().findViewById(R.id.tv_yue);
                    textView.setText(i + "");

                    LinearLayout radioButton1 = (LinearLayout) builder.getWindow().findViewById(R.id.rb4);
                    if (jinfen >= list.get(0).getTotaljifen()) {
                        radioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmOrderActivity.this)
                                        .setTitle("提示")
                                        .setMessage("确定要支付吗")
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                updateorderaddress(orderid, summoney1);
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
                                builder.dismiss();
                            }
                        });
                    } else {
                        radioButton1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                builder.dismiss();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ConfirmOrderActivity.this)
                                        .setTitle("提示")
                                        .setMessage("积分不足，不能购买")
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

                } else {


                    final AlertDialog builder = new AlertDialog.Builder(this).create();
                    builder.show();
                    builder.getWindow().setContentView(R.layout.layout_jinfen_pay);
                    RelativeLayout relative = (RelativeLayout) builder.getWindow().findViewById(R.id.relative);
                    if (jinfen >= list.get(0).getTotalmoney()) {
                        relative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmOrderActivity.this)
                                        .setTitle("提示")
                                        .setMessage("确定要支付吗")
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                updateorderaddress(orderid, summoney);
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
                                builder.dismiss();
                            }
                        });
                    } else {
                        relative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                builder.dismiss();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ConfirmOrderActivity.this)
                                        .setTitle("提示")
                                        .setMessage("积分不足，不能购买")
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


                    TextView textView = (TextView) builder.getWindow().findViewById(R.id.tv_yue);
                    textView.setText(jinfen + "");


                }


                break;
            case R.id.linear:
                initLogin();

                break;
        }

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
                        sendPayLocalReceiver(GoodsDetialActivity.class.getName());
                        sendPayLocalReceiver(ShoppingActivity.class.getName());
                        sendPayLocalReceiver(ConfirmOrderActivity.class.getName());
                        sendPayLocalReceiver(myOrderActivity.class.getName());
                        Intent it = new Intent(ConfirmOrderActivity.this, myOrderActivity.class);
                        startActivity(it);
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

                                    /**
                                     * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                                     * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                                     * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                                     *
                                     * orderInfo的获取必须来自服务端；
                                     */
                                    boolean rsa2 = (Constant.RSA_PRIVATE.length() > 0);
                                    Map<String, String> params = OrderInfoUtil2_0.buildShopOrderParamMap(Constant.APPID, rsa2, confirmMoney + "", orderid + "-" + AccountHelper.getUserId() + "-" + id);
                                    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                                    String privateKey = Constant.RSA_PRIVATE;  //私钥
                                    String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                                    final String orderInfo = orderParam + "&" + sign;

                                    Runnable payRunnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            PayTask alipay = new PayTask(ConfirmOrderActivity.this);
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
                                    map.put("orderNo", orderid + "-" + AccountHelper.getUserId() + "-" + id);                //订单号
                                    map.put("orderMoney", confirmMoney * 100);//支付金额
                                    //     map.put("orderMoney",1);
                                    map.put("notify_url", "http://www.huodiwulian.com/mbtwz/weixinzhifujieguo");
                                    map.put("body", "商品描述");
                                    new WXPayUtils().init(ConfirmOrderActivity.this, map)
                                            .setListener(new WXPayUtils.BackResult() {
                                                @Override
                                                public void getInfo(String prepayId, String sign) {
                                                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                                                            .with(ConfirmOrderActivity.this) //activity instance
                                                            .setAppId(Constant.APP_ID) //wechat pay AppID
                                                            .setPartnerId(Constant.MCH_ID)//wechat pay partner id
                                                            .setPrepayId(prepayId)//pre pay id
                                                            .setNonceStr("")
                                                            .setTimeStamp("")//time stamp
                                                            .setSign(sign)//sign
                                                            .create();
                                                    //2. send the request with wechat pay
                                                    PayAPI.getInstance().sendPayRequest(wechatPayReq);
                                                    Constant.WXPAY_STARTNAME = ConfirmOrderActivity.class.getName();
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

    //提交地址信息
    private void uploadAddress(int provinceid, int cityid, int areaid, String custaddress, String shphone, String shcustname, String uuid) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("provinceid", provinceid + "");
        map.put("cityid", cityid + "");
        map.put("areaid", areaid + "");
        map.put("custaddress", custaddress);
        map.put("shphone", shphone);
        map.put("shcustname", shcustname);
        map.put("uuid", uuid);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.updateorderaddress(params)
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
                            if ("false".equals(body)) {
                                showToast("提交地址失败");
                            } else {
//                                loadShopList();
//                                Intent it = new Intent(GoodsDetialActivity.this, ConfirmOrderActivity.class);
//                                it.putExtra("uuid", body);
//                                startActivity(it);
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
    private void orderYuEZhiFu(String orderno, float totalmoney) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno + "");
        map.put("totalmoney", totalmoney + "");
        if (id == 0) {
            map.put("conpouid", "");
        } else {
            map.put("conpouid", id + "");
        }
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.orderYuEZhiFu(params)
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
                            if ("false".equals(body) || "".equals(body)) {
                                showToast("支付失败");
                            } else {
                                showToast("支付成功");
                                sendPayLocalReceiver(GoodsDetialActivity.class.getName());
                                sendPayLocalReceiver(ShoppingActivity.class.getName());
                                sendPayLocalReceiver(ConfirmOrderActivity.class.getName());
                                sendPayLocalReceiver();
                                Intent it = new Intent(ConfirmOrderActivity.this, myOrderActivity.class);
                                startActivity(it);
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

    //积分支付
    private void updateorderaddress(String orderno, float totalmoney) {
        showWaitDialog("提交信息中...");
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno + "");
        map.put("totalmoney", totalmoney + "");
        map.put("conpouid", "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.orderScoresZhiFu(params)
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
                            if ("false".equals(body)) {
                                showToast("支付失败");
                            } else {
                                showToast("支付成功");
                                sendPayLocalReceiver(GoodsDetialActivity.class.getName());
                                sendPayLocalReceiver(ShoppingActivity.class.getName());
                                sendPayLocalReceiver(ConfirmOrderActivity.class.getName());
                                Intent it = new Intent(ConfirmOrderActivity.this, myOrderActivity.class);
                                startActivity(it);


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


    private void islogin() {
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
                            } else {
                                DialogHelper.getConfirmDialog(ConfirmOrderActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ConfirmOrderActivity.this, LoginActivity.class));
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
                                    if (Constant.WXPAY_STARTNAME.equals(ConfirmOrderActivity.class.getName())) {
                                        Constant.WXPAY_STARTNAME = "";
                                        sendPayLocalReceiver(GoodsDetialActivity.class.getName());
                                        sendPayLocalReceiver(ShoppingActivity.class.getName());
                                        sendPayLocalReceiver(ConfirmOrderActivity.class.getName());
                                        Intent it = new Intent(ConfirmOrderActivity.this, myOrderActivity.class);
                                        startActivity(it);
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
