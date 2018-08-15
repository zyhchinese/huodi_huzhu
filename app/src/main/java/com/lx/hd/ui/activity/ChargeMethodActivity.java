package com.lx.hd.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.adapter.ChargeSelectGridViewAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ChargeMethodActivity extends BaseActivity {
    private GridView mGridView;
    private ArrayList<String> timeList, moneyList, chargeList;
    ChargeSelectGridViewAdapter chargeSelectGridViewAdapter;
    private TextView tvConfirm, tvBack;
    private EditText etInputMoney;
    private float confirmMoney = 0;
    private int flag = 0;//0：按时间充，1：按电量充，2：按金额充
    private int carType = 0;//0：电瓶车，1：电动车
    String chargeTime;
    private TextView tvChargeType;
    private boolean isCheckOrInput = false;
    ProgressDialog progressDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_charge_method;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        flag = getIntent().getIntExtra("flag", 0);
        mGridView = (GridView) findViewById(R.id.gv_select_money);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        etInputMoney = (EditText) findViewById(R.id.et_input_money);
        tvChargeType = (TextView) findViewById(R.id.tv_charge_type);

        timeList = new ArrayList<>();
        moneyList = new ArrayList<>();
        chargeList = new ArrayList<>();
        timeList.add("0.5小时");
        timeList.add("1小时");
        timeList.add("1小时");
        timeList.add("2小时");
        timeList.add("1.5小时");
        timeList.add("4小时");

        moneyList.add("2元");
        moneyList.add("10元");
        moneyList.add("3元");
        moneyList.add("20元");
        moneyList.add("5元");
        moneyList.add("30元");

        chargeList.add("1度");
        chargeList.add("8度");
        chargeList.add("3度");
        chargeList.add("18度");
        chargeList.add("5度");
        chargeList.add("28度");
        switch (flag) {
            case 0:
                chargeSelectGridViewAdapter = new ChargeSelectGridViewAdapter(this, timeList);
                tvChargeType.setText("请根据车辆类型选择充电时间");
                etInputMoney.setHint("也可自行输入充电时间");
                etInputMoney.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

                mGridView.setAdapter(chargeSelectGridViewAdapter);
                break;
            case 1:
                chargeSelectGridViewAdapter = new ChargeSelectGridViewAdapter(this, chargeList);
                tvChargeType.setText("请根据车辆类型选择充电电量");
                etInputMoney.setHint("也可自行输入充电电量");
                mGridView.setAdapter(chargeSelectGridViewAdapter);
                break;
            case 2:
                chargeSelectGridViewAdapter = new ChargeSelectGridViewAdapter(this, moneyList);
                tvChargeType.setText("请根据车辆类型选择充电金额");
                etInputMoney.setHint("也可自行输入充电金额");
                mGridView.setAdapter(chargeSelectGridViewAdapter);
                break;
            default:
                chargeSelectGridViewAdapter = new ChargeSelectGridViewAdapter(this, timeList);
                tvChargeType.setText("请根据车辆类型选择充电时间");
                mGridView.setAdapter(chargeSelectGridViewAdapter);
                break;
        }

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rootAccountMoney = getIntent().getStringExtra("accountmoney");
                float userMoney = Float.valueOf(rootAccountMoney);
                if (userMoney < 0.0001) {
                    showToast("当前用户金额不足，请立即充值！");
                    Intent intent = new Intent(ChargeMethodActivity.this, BalanceActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                switch (flag) {
                    case 0:
                        if (!isCheckOrInput) {
                            String temptime = timeList.get(0);
                            temptime = temptime.substring(0, temptime.length() - 2);
                            confirmMoney = Float.valueOf(temptime);
                        }
                        if (confirmMoney < 0.01) {
                            showToast("充电时间必须大于0");
                            return;
                        }
                        Intent intent1 = new Intent();
                        intent1.putExtra("confirmMoney", confirmMoney);
                        intent1.putExtra("carType", carType);
                        setResult(RESULT_OK, intent1);
                        finish();
                        break;
                    case 1:
                        if (!isCheckOrInput) {
                            String tempCharge = chargeList.get(0);
                            tempCharge = tempCharge.substring(0, tempCharge.length() - 1);
                            confirmMoney = Float.valueOf(tempCharge);
                        }
                        if (confirmMoney < 1) {
                            showToast("充电度数必须为正整数");
                            return;
                        }

                        String chargeOrderNo = getIntent().getStringExtra("chargeOrderNo");
                        if (chargeOrderNo == null) {
                            showToast("识别码解析错误");
                            return;
                        }
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("electricsbm", chargeOrderNo);
                        map.put("dushu", ((int) confirmMoney) + "");
                        Gson gson = new Gson();
                        String data = gson.toJson(map);
                        if (!progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                        PileApi.instance.duToMoney(data)
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
                                            float duToMoney = 0;
                                            if (!body.equals("")) {
                                                duToMoney = Float.valueOf(body);
                                            } else {
                                                showToast("金额转换失败");
                                            }


                                            if (getIntent().getStringExtra("accountmoney").length() == 0)
                                                return;

                                            float accountMoney = Float.valueOf(getIntent().getStringExtra("accountmoney"));
                                            if (accountMoney < duToMoney) {
                                                float money = duToMoney - accountMoney;
                                                showToast("余额不足,差" + money + "元,请立即充值");
                                                startActivity(new Intent(ChargeMethodActivity.this, PayActivity.class));
                                                finish();
                                            } else {
                                                Intent intent = new Intent();
                                                if (flag == 1) {
                                                    intent.putExtra("confirmMoney", confirmMoney);
                                                } else {
                                                    intent.putExtra("confirmMoney", duToMoney);
                                                }
                                                intent.putExtra("carType", carType);
                                                setResult(RESULT_OK, intent);
                                                finish();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                        break;
                    case 2:
                        if (!isCheckOrInput) {
                            String tempMoney = moneyList.get(0);
                            tempMoney = tempMoney.substring(0, tempMoney.length() - 1);
                            confirmMoney = Float.valueOf(tempMoney);
                        }
                        if (confirmMoney < 1) {
                            showToast("充电金额必须为正整数");
                            return;
                        }

                        String accountMoney = getIntent().getStringExtra("accountmoney");
                        float tempAccountMoney = 0;
                        if (!accountMoney.equals("")) {
                            tempAccountMoney = Float.valueOf(accountMoney);
                        } else {
                            showToast("用户金额获取失败");
                        }

                        if (tempAccountMoney < confirmMoney) {
                            float money = confirmMoney - tempAccountMoney;
                            showToast("余额不足,差" + money + "元,请立即充值");
                            startActivity(new Intent(ChargeMethodActivity.this, PayActivity.class));
                            finish();
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra("confirmMoney", confirmMoney);
                            intent.putExtra("carType", carType);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        break;
                }

            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isCheckOrInput = true;
                if (i == 0 || i == 2 || i == 4) {
                    carType = 0;
                } else {
                    carType = 1;
                }
                etInputMoney.setText("");
                chargeSelectGridViewAdapter.setSeclect(i);
                chargeSelectGridViewAdapter.notifyDataSetChanged();
                switch (flag) {
                    case 0:
                        chargeTime = timeList.get(i);
                        chargeTime = chargeTime.substring(0, chargeTime.length() - 2);
                        confirmMoney = Float.valueOf(chargeTime);
                        break;
                    case 1:
                        chargeTime = chargeList.get(i);
                        chargeTime = chargeTime.substring(0, chargeTime.length() - 1);
                        confirmMoney = Float.valueOf(chargeTime);
                        break;
                    case 2:
                        chargeTime = moneyList.get(i);
                        chargeTime = chargeTime.substring(0, chargeTime.length() - 1);
                        confirmMoney = Float.valueOf(chargeTime);
                        break;
                    default:
                        chargeTime = timeList.get(i);
                        chargeTime = chargeTime.substring(0, chargeTime.length() - 2);
                        confirmMoney = Float.valueOf(chargeTime);
                        break;
                }

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
                isCheckOrInput = true;
                chargeSelectGridViewAdapter.setSeclect(-1);
                if (s.toString().length() > 0) {
                    confirmMoney = Float.valueOf(s.toString());
                }

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
