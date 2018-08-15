package com.lx.hd.account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.utils.TDevice;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ForgetPwdOneActivity extends AccountBaseActivity {

    private EditText etPhone,etAuthCode;
    private TextView tvAuthCode,tvNext;
    private String mAuthCode="";
    private CountDownTimer mTimer;
    public static Activity instance;
    @Override
    protected int getContentView() {
        return R.layout.activity_register_one;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        instance=this;
        etPhone=(EditText)findViewById(R.id.et_phone);
        etAuthCode=(EditText)findViewById(R.id.et_auth_code);
        tvAuthCode=(TextView)findViewById(R.id.tv_auth_code);
        tvNext=(TextView)findViewById(R.id.tv_next);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etPhone.getText().length()==0){
                    showToast("手机号不能为空");
                    return;
                }
                if(etAuthCode.getText().length()==0){
                    showToast("验证码不能为空");
                    return;
                }

                if(mAuthCode.equals(etAuthCode.getText().toString().trim())){
                    Intent intent = new Intent(ForgetPwdOneActivity.this, ForgetPwdTwoActivity.class);
                    intent.putExtra("phone",etPhone.getText().toString().trim());
                    intent.putExtra("authcode",etAuthCode.getText().toString().trim());
                    startActivity(intent);
                }else {
                    showToast("验证码填写错误");
                }
            }
        });
        tvAuthCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPhone=etPhone.getText().toString().trim();
                if (!isMobile(tempPhone)) {
                    showToast("手机号格式有误");
                    return;
                }
                if (!TDevice.hasInternet()) {
                    showToast("网络连接错误");
                    return;
                }

                HashMap<String,String> map=new HashMap<String, String>();
                map.put("phone",tempPhone);
                Gson gson=new Gson();
                String params=gson.toJson(map);
                showWaitDialog("正在获取验证码...");
                PileApi.instance.mCheckPhone(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull ResponseBody responseBody) {
                                hideWaitDialog();
                                try {
                                    String body=responseBody.string();
                                    if(body.equals("\"false\"")){
                                        showToast("该手机还未注册");
                                    }else {
                                        requestSmsCode();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                hideWaitDialog();
                                showToast("检测手机号失败");
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

    private void requestSmsCode() {

        if (tvAuthCode.getTag() == null) {
        //    mRequestType = 1;
            tvAuthCode.setAlpha(0.6f);
            tvAuthCode.setTag(true);
            mTimer = new CountDownTimer(60 * 1000, 1000) {

                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {
                    tvAuthCode.setText(String.format("%s%s%d%s","等待", "(", millisUntilFinished / 1000, ")"));
                }

                @Override
                public void onFinish() {
                    tvAuthCode.setTag(null);
                    tvAuthCode.setText("获取验证码");
                    tvAuthCode.setAlpha(1.0f);
                }
            }.start();
            String phoneNumber = etPhone.getText().toString().trim();
            HashMap<String,String> map=new HashMap<>();
            map.put("phone",phoneNumber);
            Gson gson=new Gson();
            String params=gson.toJson(map);
            PileApi.instance.mGetAuthCode(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull ResponseBody responseBody) {

                            //"status": true, "msg": "短信发送成功，请注意接收短信！","spstatus":"null"}
                            try {
                                String body=responseBody.string();
                                if(body.length()==6){
                                    body=body.substring(1,5);
                                    mAuthCode=body;
                                    showToast("短信发送成功，请注意查收");
                                }else {
                                    showToast("短信发送失败，请稍后重试");
                                }
//                                    JSONObject jsonObject=new JSONObject(body);
//                                    if(jsonObject.getString("status").equals("true")){
//                                        mAuthCode=jsonObject.getString("smscode");
//                                        showToast(jsonObject.getString("msg"));
//                                    }else {
//                                        showToast(jsonObject.getString("msg"));
//                                    }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            showToast("短信发送失败，请稍后重试");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else {
            showToast("别激动,休息一下呗");
        }
    }
}
