package com.lx.hd.account;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.lx.hd.ui.activity.SecurityActivity;
import com.lx.hd.ui.activity.SetupActivity;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ModifyPwdActivity extends BackCommonActivity {

    private EditText etPhone,etCode,etPwd,etCheckPwd;
    private TextView tvAuthCode,tvConform;
    String tempPhone="",sPwd,sCheckPwd;
    private CountDownTimer mTimer;
    private String mAuthCode="",mPwd="",mCheckPwd="";
    private ImageView img_anquanzhongxin_banner;
    @Override
    protected int getContentView() {
        return R.layout.activity_modify_pwd_gaiban;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("修改密码");
        etPhone=(EditText)findViewById(R.id.et_phone);
        etCode=(EditText)findViewById(R.id.et_code);
        etPwd=(EditText)findViewById(R.id.et_new_pwd);
        etCheckPwd=(EditText)findViewById(R.id.et_check_pwd);
        tvAuthCode=(TextView)findViewById(R.id.tv_get_code);
        tvConform=(TextView)findViewById(R.id.tv_confirm);
        img_anquanzhongxin_banner= (ImageView) findViewById(R.id.img_anquanzhongxin_banner);

        tvAuthCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempPhone=etPhone.getText().toString();
                if(tempPhone.equals("")){
                    showToast("手机号不能为空");
                    return;
                }

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("phone", tempPhone);
                Gson gson = new Gson();
                String params = gson.toJson(map);
                PileApi.instance.isOwnPhone(params)
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
                                        requestSmsCode();
                                    }else {
                                        showToast("此手机非当前登录手机");
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

        tvConform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etPhone.getText().toString().trim().equals("")){
                    showToast("手机号不能为空");
                    return;
                }
                if(etCode.getText().toString().trim().equals("")){
                    showToast("验证码不能为空");
                    return;
                }

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("phone", tempPhone);
                Gson gson = new Gson();
                String params = gson.toJson(map);
                PileApi.instance.isOwnPhone(params)
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
                                        ModifyPwd(tempPhone);
                                    }else {
                                        showToast("此手机非当前登录手机");
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

    private void ModifyPwd(String mPhone){
        mPwd=etPwd.getText().toString().trim();
        mCheckPwd=etCheckPwd.getText().toString().trim();

        if(!mAuthCode.equals(etCode.getText().toString().trim())){
            showToast("验证码输入不正确");
            return;
        }

        if(mPwd.length()<6){
            showToast("密码格式不正确：\n由数字、字母、下划线组成的6—15位密码");
            return;
        }
        if(mPwd.length()>15){
            showToast("密码格式不正确：\n由数字、字母、下划线组成的6—15位密码");
            return;
        }
        if(!mPwd.equals(mCheckPwd)){
            showToast("两次密码输入不一致");
            return;
        }


        HashMap<String,String> map=new HashMap<>();
        map.put("phone",mPhone);
        map.put("password",mPwd);
        Gson gson=new Gson();
        String params=gson.toJson(map);
        PileApi.instance.changePwd(params)
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

                                showToast("密码修改成功");
                                sendPayLocalReceiver(SecurityActivity.class.getName());
                                sendPayLocalReceiver(SetupActivity.class.getName());
                                finish();
                            }else {
                                showToast("密码修改失败");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToast("密码修改失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

        protected boolean sendPayLocalReceiver(String activityName) {
        if (mManager != null) {
            Intent intent = new Intent();
            intent.setAction(ACTION_PAY_FINISH_ALL);
            intent.putExtra("className", activityName);
            return  mManager.sendBroadcast(intent);
        }

        return false;
    }
}
