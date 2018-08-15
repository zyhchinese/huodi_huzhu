package com.lx.hd.ui.activity;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.activity.BaseActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BindEmailActivity extends BackCommonActivity {
    private TextView tvAuthCode,tvConfirm;
    private String mAuthCode = "";
    private CountDownTimer mTimer;
    private EditText et_phone,etCode;
    private ImageView img_anquanzhongxin_banner;

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_email_gaiban;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("绑定邮箱");
        tvAuthCode = (TextView) findViewById(R.id.tv_get_code);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        et_phone = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        img_anquanzhongxin_banner= (ImageView) findViewById(R.id.img_anquanzhongxin_banner);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCode.getText().toString().trim().length()==0){
                    showToast("验证码不能为空");
                    return;
                }
                if(et_phone.getText().toString().trim().length()==0){
                    showToast("手机号不能为空");
                    return;
                }
                if(!mAuthCode.equals(etCode.getText().toString().trim())){
                    showToast("验证码填写不正确");
                    return;
                }
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("email",et_phone.getText().toString().trim());
                Gson gson=new Gson();
                String data=gson.toJson(map);
                PileApi.instance.bindEmail(data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull ResponseBody responseBody) {

                                try {
                                    String body=responseBody.string();
                                    if(body.equals("\"true\"")){
                                        showToast("邮箱绑定成功");
                                        finish();
                                    }else {
                                        showToast("邮箱绑定失败，请稍后重试");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                showToast("邮箱绑定失败，请稍后重试");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        tvAuthCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempEmail=et_phone.getText().toString().trim();
                if(tempEmail.length()==0){
                    showToast("邮箱地址不能为空");
                    return;
                }
                if(!isEmail(tempEmail)){
                    showToast("邮箱格式不正确");
                    return;
                }
                requestSmsCode();
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
            String email = et_phone.getText().toString().trim();
            HashMap<String, String> map = new HashMap<>();
            map.put("email", email);
            Gson gson = new Gson();
            String params = gson.toJson(map);
            PileApi.instance.sendEmailNum(params)
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
                                body=body+"";
                                if (body.length() == 6) {
                                    body = body.substring(1, 5);
                                    mAuthCode = body;
                                    showToast("验证邮件发送成功，请注意查收");
                                } else {
                                    showToast("验证邮件发送失败，请稍后重试");
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
                            showToast("验证邮件发送失败，请稍后重试");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else {
            showToast("别激动,休息一下呗");
        }
    }

    private boolean isEmail(String email){
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }
}
