package com.lx.hd.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.account.AccountHelper;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.utils.TDevice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PayMethodActivity extends BackActivity implements View.OnClickListener,
        EasyPermissions.PermissionCallbacks {

    public static Activity instance;
    private TextView tvToPay, tvAccountBalance;
    private static final int REQUEST_CODE_PHINE = 1006;//电话权限
    private LinearLayout llyChargeByTime, llyChargeByCharge, llyChargeByMoney, llyChargeByFinish;
    private int flag = 0;//0：按时间充，1：按电量充，2：按金额充
    private int MY_RESULT_CODE = 1000;
    private View mDialogView;
    AlertDialog dialog = null;
    private boolean isCheckedPwd = false;
    ProgressDialog progressDialog;

    private String accountMoney = "";

    private String chargeOrderNo;

    private TextView tvBack, tvConfirm;
    private EditText etInputPwd;

    @Override
    protected void onResume() {
        super.onResume();
        PileApi.instance.getAccountBalance()
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
                            if (body.length() < 2) {
                                showToast("登录状态失效，请重新登陆后重试");
                            } else {
                                body = body.substring(1, body.length() - 1);
                                tvAccountBalance.setText("当前用户金额" + body);
                                accountMoney = body;
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

    @Override
    protected int getContentView() {
        return R.layout.activity_pay_method;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        instance = this;
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在验证...");
        chargeOrderNo = getIntent().getStringExtra("orderNo");
        setTitleText("充电方式");
        setTitleIcon(R.mipmap.icon_title_pile);

        llyChargeByTime = (LinearLayout) findViewById(R.id.lly_charge_time);
        llyChargeByCharge = (LinearLayout) findViewById(R.id.lly_charge_charge);
        llyChargeByMoney = (LinearLayout) findViewById(R.id.lly_charge_money);
        llyChargeByFinish = (LinearLayout) findViewById(R.id.lly_charge_finish);
        tvToPay = (TextView) findViewById(R.id.tv_topay);
        tvAccountBalance = (TextView) findViewById(R.id.tv_account_balance);

        mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_pwd_input, null, false);
        tvBack = (TextView) mDialogView.findViewById(R.id.tv_back);
        tvConfirm = (TextView) mDialogView.findViewById(R.id.tv_confirm);
        etInputPwd = (EditText) mDialogView.findViewById(R.id.et_input_pwd);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(mDialogView);
        dialog = builder.create();

        llyChargeByTime.setOnClickListener(this);
        llyChargeByCharge.setOnClickListener(this);
        llyChargeByMoney.setOnClickListener(this);
        llyChargeByFinish.setOnClickListener(this);
        tvToPay.setOnClickListener(this);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                etInputPwd.setText("");
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TDevice.startOrCloseKeyboard(tvConfirm);

                if (!TDevice.hasInternet()) {
                    showToast("网络连接错误");
                    return;
                }

                String tempPwd = etInputPwd.getText().toString().trim();
                if (tempPwd.length() == 0) {
                    DialogHelper.getConfirmDialog(PayMethodActivity.this, "温馨提示", "请到我的-设置-安全中心中进行设置。", "确定", "", false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }, null).show();
                    dialog.dismiss();
                    return;
                }
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("chongdianpassword", tempPwd);
                Gson gson = new Gson();
                String params = gson.toJson(map);

                etInputPwd.setText("");
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
                PileApi.instance.mCheckChargePwd(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull ResponseBody responseBody) {
                                try {
                                    progressDialog.dismiss();
                                    String body = responseBody.string();
                                    if (body.equals("\"true\"")) {
                                        isCheckedPwd = false;
                                        dialog.dismiss();
                                        Intent intent0 = new Intent(PayMethodActivity.this, ChargeMethodActivity.class);
                                        intent0.putExtra("flag", flag);

                                        switch (flag) {
                                            case 0:
                                                intent0.putExtra("accountmoney", accountMoney);
                                                break;
                                            case 1:
                                                intent0.putExtra("chargeOrderNo", chargeOrderNo);
                                                intent0.putExtra("accountmoney", accountMoney);
                                                break;
                                            case 2:
                                                intent0.putExtra("accountmoney", accountMoney);
                                                break;
                                        }
                                        if (MY_RESULT_CODE == 1003) {
                                            if (accountMoney.length() == 0)
                                                return;

                                            float userMoney = Float.valueOf(accountMoney);
                                            if (userMoney < 0.0001) {
                                                showToast("当前用户金额不足，请立即充值");
                                                Intent intent = new Intent(PayMethodActivity.this, BalanceActivity.class);
                                                startActivity(intent);

                                            }else {
                                                payCharge("0", "yujijine", "0");
                                            }

                                            //  startActivity(new Intent(PayMethodActivity.this,ChargeViewActivity.class));
                                        } else {
                                            startActivityForResult(intent0, MY_RESULT_CODE);
                                        }


                                    } else {
                                        DialogHelper.getConfirmDialog(PayMethodActivity.this, "温馨提示", "充电密码不匹配，请重新确认或您请到我的-设置-安全中心中进行设置。", "确定", "", false, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }, null).show();
                                        dialog.dismiss();
                                        isCheckedPwd = false;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                    isCheckedPwd = false;
                                    progressDialog.dismiss();
                                }

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onClick(View v) {

        if (!dialog.isShowing() && v.getId() != R.id.tv_topay) {
            dialog.show();
        }

        switch (v.getId()) {
            case R.id.lly_charge_time:
                flag = 0;
                MY_RESULT_CODE = 1000;
                break;
            case R.id.lly_charge_charge:
                flag = 1;
                MY_RESULT_CODE = 1001;
                break;
            case R.id.lly_charge_money:
                flag = 2;
                MY_RESULT_CODE = 1002;
                break;
            case R.id.lly_charge_finish:
                flag = 3;
                MY_RESULT_CODE = 1003;
                break;
        }
        if (isCheckedPwd) {
            isCheckedPwd = false;
            switch (v.getId()) {
                case R.id.lly_charge_time:
                    Intent intent0 = new Intent(PayMethodActivity.this, ChargeMethodActivity.class);
                    intent0.putExtra("flag", 0);
                    startActivityForResult(intent0, 1000);
                    break;
                case R.id.lly_charge_charge:
                    Intent intent1 = new Intent(PayMethodActivity.this, ChargeMethodActivity.class);
                    intent1.putExtra("flag", 1);
                    intent1.putExtra("chargeOrderNo", chargeOrderNo);
                    startActivityForResult(intent1, 1001);
                    break;
                case R.id.lly_charge_money:
                    Intent intent2 = new Intent(PayMethodActivity.this, ChargeMethodActivity.class);
                    intent2.putExtra("flag", 2);
                    intent2.putExtra("accountmoney", accountMoney);
                    startActivityForResult(intent2, 1002);
                    break;
                case R.id.lly_charge_finish:
                    break;
            }
        }

        if (v.getId() == R.id.tv_topay) {
            Intent intent = new Intent(PayMethodActivity.this, BalanceActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  if (data != null && requestCode == Constant.REQUEST_CODE) {
        if (data != null) {

            switch (requestCode) {
                case 1000:
                    float mTime = data.getExtras().getFloat("confirmMoney", 0);//得到充电的充电金额或时间或电量
                    int carType0 = data.getExtras().getInt("carType", 0);//得到充电的车辆类型
                    mTime = mTime * 60;
                    payCharge("3", "yujishijian", ((int) mTime) + "");
                    break;
                case 1001:
                    float duToMoney = data.getExtras().getFloat("confirmMoney", 0);//得到充电的充电金额或时间或电量
                    int carType1 = data.getExtras().getInt("carType", 0);//得到充电的车辆类型
                    payCharge("1", "yujidushu", ((int) duToMoney) + "");
                    break;
                case 1002:
                    float confirmMoney = data.getExtras().getFloat("confirmMoney", 0);//得到充电的充电金额或时间或电量
                    int carType2 = data.getExtras().getInt("carType", 0);//得到充电的车辆类型
                    payCharge("2", "yujijine", ((int) confirmMoney) + "");
                    break;
                case 1003:

                    if(accountMoney.equals("")){
                       return;
                    }
                    float userMoney = Float.valueOf(accountMoney);
                    if (userMoney < 0.0001) {
                        showToast("当前用户金额不足，请立即充值");
                        Intent intent = new Intent(PayMethodActivity.this, BalanceActivity.class);
                        startActivity(intent);
                    }else {
                        payCharge("0", "yujijine", "0");
                    }
                    break;
                default:

            }

        }

    }

    protected boolean sendPayLocalReceiver() {
        if (mManager != null) {
            Intent intent = new Intent();
            intent.setAction(ACTION_PAY_FINISH_ALL);
            intent.putExtra("className", ScannerActivity.class.getName());
            mManager.sendBroadcast(intent);
            Intent intent1 = new Intent();
            intent1.setAction(ACTION_PAY_FINISH_ALL);
            intent1.putExtra("className", PayMethodActivity.class.getName());
            mManager.sendBroadcast(intent);
            return true;
        }

        return false;
    }

    private void payCharge(String dianfeitype, String type, String typeValue) {
        if (!TDevice.hasInternet()) {
            showToast("网络连接错误");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("electricsbm", chargeOrderNo);
        map.put("custid", AccountHelper.getUserId() + "");
        map.put("dianfeitype", dianfeitype);
        map.put(type, typeValue);
        Gson gson = new Gson();
        String datas = gson.toJson(map);
        progressDialog.setMessage("正在验证...");
        progressDialog.show();
        PileApi.instance.addChargeOrder(datas)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        progressDialog.dismiss();
                        try {
                            String body = responseBody.string();
                            if (body.equals("\"true\"")) {
                                startActivity(new Intent(PayMethodActivity.this, ChargeViewActivity.class));
                                finish();
                            } else {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("electricsbm", chargeOrderNo);
                                Gson gson = new Gson();
                                String datas = gson.toJson(map);
                                PileApi.instance.ChargeFail(datas)
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
                                                        showToast("当前登录状态异常，请重新登录");
                                                        return;
                                                    }
                                                    final String phone = body.substring(1, body.length() - 1);
                                                    DialogHelper.getConfirmDialog(PayMethodActivity.this, "温馨提示", "充电失败，请联系电桩管理员\n" + phone, "拨打", "取消", true, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            if (phone.trim().length() != 0) {

                                                                requestPhone(phone);

                                                            } else {
                                                                showToast("手机号解析错误");
                                                            }
                                                        }
                                                    }, null).show();
                                                    // showToast("充电失败，请联系电桩管理员"+body);

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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();

                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @AfterPermissionGranted(REQUEST_CODE_PHINE)
    public void requestPhone(String phone) {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        } else {
            EasyPermissions.requestPermissions(this, "电话权限未授权,是否授权", REQUEST_CODE_PHINE, Manifest.permission.CALL_PHONE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
