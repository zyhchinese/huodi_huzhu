package com.lx.hd.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.ForgetPwdOneActivity;
import com.lx.hd.account.ForgetPwdTwoActivity;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CItem;
import com.lx.hd.bean.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class SetPayPwdActivity extends BackCommonActivity {
    private String mAuthCode = "";
    private TextView tvAuthCode, tv_confirm, et_new_pwd, et_check_pwd;
    private EditText etPhone,et_code;
    private CountDownTimer mTimer;
    private ImageView img_anquanzhongxin_banner;

    @Override
    protected int getContentView() {
        return R.layout.activity_set_pay_pwd_gaiban;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("修改支付密码");
        tvAuthCode = (TextView) findViewById(R.id.tvAuthCode);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        et_new_pwd = (TextView) findViewById(R.id.et_new_pwd);
        et_check_pwd = (TextView) findViewById(R.id.et_check_pwd);
        etPhone = (EditText) findViewById(R.id.etPhone);
        et_code = (EditText) findViewById(R.id.et_code);
        img_anquanzhongxin_banner= (ImageView) findViewById(R.id.img_anquanzhongxin_banner);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPhone.getText().length() == 0) {
                    showToast("手机号不能为空");
                    return;
                }
                if (et_code.getText().length() == 0) {
                    showToast("验证码不能为空");
                    return;
                }
                if (et_new_pwd.getText().length() == 0) {
                    showToast("密码不能为空");
                    return;
                }
                if (et_new_pwd.getText().length()<6) {
                    showToast("密码格式不正确：\n需为6-15位的字母与数字");
                    return;
                }
                if (et_new_pwd.getText().length()>15) {
                    showToast("密码格式不正确：\n需为6-15位的字母与数字");
                    return;
                }
                if (et_check_pwd.getText().length() == 0 || !et_new_pwd.getText().toString().equals(et_check_pwd.getText().toString())) {
                    showToast("两次输入的密码不一致");
                    return;
                }
                String a=et_code.getText().toString().trim();
                if (mAuthCode.equals(et_code.getText().toString().trim())) {
                    upzhifuPassword(etPhone.getText().toString(), et_new_pwd.getText().toString());
                } else {
                    showToast("验证码填写错误");
                }
            }
        });
        tvAuthCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPhone.getText().length() == 0) {
                    showToast("手机号不能为空");
                    return;
                }
                mCheckPhone(etPhone.getText().toString());
            }
        });
        initGaoDu();
    }

    private void initGaoDu() {
        WindowManager windowManager=getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        double i = display.getWidth() / 720.0;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img_anquanzhongxin_banner.getLayoutParams();
        layoutParams.height= (int) (360.0*i);
        img_anquanzhongxin_banner.setLayoutParams(layoutParams);

    }


    private void mCheckPhone(String phone) {
        showWaitDialog("正在校验信息...");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.mCheckPhone(params)
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

                            if(body.equals("\"true\"")){
                                requestSmsCode();
                            }else {
                                showToast("此手机非当前登录手机");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            hideWaitDialog();
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

    private void requestSmsCode() {

        if (tvAuthCode.getTag() == null) {
            //    mRequestType = 1;
            tvAuthCode.setAlpha(0.6f);
            tvAuthCode.setTag(true);
            mTimer = new CountDownTimer(60 * 1000, 1000) {

                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {
                    tvAuthCode.setText(String.format("%s%s%d%s", "等待", "(", millisUntilFinished / 1000, ")"));
                }

                @Override
                public void onFinish() {
                    tvAuthCode.setTag(null);
                    tvAuthCode.setText("获取验证码");
                    tvAuthCode.setAlpha(1.0f);
                }
            }.start();
            String phoneNumber = etPhone.getText().toString().trim();
            HashMap<String, String> map = new HashMap<>();
            map.put("phone", phoneNumber);
            Gson gson = new Gson();
            String params = gson.toJson(map);
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
                                String body = responseBody.string();
                                if (body.length() == 6) {
                                    body = body.substring(1, 5);
                                    mAuthCode = body;
                                    showToast("短信发送成功，请注意查收");
                                } else {
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

    /**
     * 设置充电密码
     */
    private void upzhifuPassword(String phone, String password) {
        showWaitDialog("正在保存信息...");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);
        map.put("password", password);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.upzhifuPassword(params)
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
                            String body = responseBody.string();
                            if (body.equals("\"false\"")) {
                                showToast("设置失败，请检查网络后重试！");
                            } else {
                                showToast("设置成功！");
                                finish();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
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
}
