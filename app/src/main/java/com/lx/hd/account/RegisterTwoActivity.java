package com.lx.hd.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.User;
import com.lx.hd.receiver.MyJPushMessageReceiver;
import com.lx.hd.utils.TDevice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class RegisterTwoActivity extends AccountBaseActivity {

    private EditText etUserName, etPwd, etConfimPwd, ed_zhifumima, et_phone, etAuthCode;
    private TextView tvSubmit;
    private String mUserName = "", mPwd = "", mCheckPwd = "", mPhone = "", mAuthCode = "";
    private ImageView goback;
    private TextView tvAuthCode;
    private CountDownTimer mTimer;
    public static final String HOLD_USERNAME_KEY = "holdUsernameKey";

    @Override
    protected int getContentView() {
        return R.layout.activity_register_two;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
//        mPhone = getIntent().getStringExtra("phone");
//        mAuthCode = getIntent().getStringExtra("authcode");
        etUserName = (EditText) findViewById(R.id.et_username);
        goback = (ImageView) findViewById(R.id.goback);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etConfimPwd = (EditText) findViewById(R.id.et_confirm_pwd);
        ed_zhifumima = (EditText) findViewById(R.id.ed_zhifumima);
        et_phone = (EditText) findViewById(R.id.et_phone);
        tvAuthCode = (TextView) findViewById(R.id.tv_auth_code);
        etAuthCode = (EditText) findViewById(R.id.et_auth_code);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserName = etUserName.getText().toString().trim();
                mPwd = etPwd.getText().toString().trim();
                mCheckPwd = etConfimPwd.getText().toString().trim();
                if (mUserName.equals("")) {
                    showToast("用户名不能为空");
                    return;
                }
                if (et_phone.getText().toString().trim().equals("")) {
                    showToast("手机号不能为空");
                    return;
                }
                if (!isMobile(et_phone.getText().toString().trim())) {
                    showToast("手机号格式有误");
                    return;
                }
                if (etAuthCode.getText().length() == 0) {
                    showToast("验证码不能为空");
                    return;
                }

                if (mPwd.equals("")) {
                    showToast("密码不能为空");
                    return;
                }

                if (mPwd.length() < 6) {
                    showToast("密码格式不正确：\n由数字、字母、下划线组成的6—15位密码");
                    return;
                }
                if (mPwd.length() > 15) {
                    showToast("密码格式不正确：\n由数字、字母、下划线组成的6—15位密码");
                    return;
                }
//                if (!mPwd.equals(mCheckPwd)) {
//                    showToast("两次密码输入不一致");
//                    return;
//                }
//                if (ed_zhifumima.getText().toString().trim().equals("")){
//                    showToast("支付密码不能为空");
//                    return;
//                }
//                if (ed_zhifumima.getText().toString().trim().length()<6){
//                    showToast("支付密码格式不正确：\n由数字、字母组成的6—15位密码");
//                    return;
//                }
//                if (ed_zhifumima.getText().toString().trim().length()>15){
//                    showToast("支付密码格式不正确：\n由数字、字母组成的6—15位密码");
//                    return;
//                }

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("phone", et_phone.getText().toString().trim());
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
                                    if (body.equals("\"false\"")) {

                                        if (mAuthCode.equals(etAuthCode.getText().toString().trim())) {
                                            mRegister();
                                        } else {
                                            showToast("验证码填写错误");
                                        }
                                    } else {
                                        showToast("该手机已注册");
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

        tvAuthCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPhone = et_phone.getText().toString().trim();
                if (!isMobile(tempPhone)) {
                    showToast("手机号格式有误");
                    return;
                }
                if (!TDevice.hasInternet()) {
                    showToast("网络连接错误");
                    return;
                }

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("phone", tempPhone);
                Gson gson = new Gson();
                String params = gson.toJson(map);
                showWaitDialog("");
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
                                    String body = responseBody.string();
                                    if (body.equals("\"false\"")) {
                                        requestSmsCode();
                                    } else {
                                        showToast("该手机已注册");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                showToast("检测手机号异常");
                                hideWaitDialog();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

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
            String phoneNumber = et_phone.getText().toString().trim();
            HashMap<String, String> map = new HashMap<>();
            map.put("phone", phoneNumber);
            Gson gson = new Gson();
            String params = gson.toJson(map);
            showWaitDialog("正在获取验证码...");
            PileApi.instance.mGetAuthCode(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull ResponseBody responseBody) {

                            hideWaitDialog();
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
                            hideWaitDialog();
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

    private void mRegister() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("custname", mUserName);
        map.put("custphone", et_phone.getText().toString().trim());
        map.put("password", mPwd);
        map.put("paypassword", ed_zhifumima.getText().toString().trim());
        Gson gson = new Gson();
        String params = gson.toJson(map);
        showWaitDialog("正在注册...");
        PileApi.instance.mRegister(params)
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
                            if (body.equals("\"true\"")) {
//                                showToast("注册成功");
//                                if (RegisterOneActivity.instance != null) {
//                                    RegisterOneActivity.instance.finish();
//                                }
                                requestLogin(et_phone.getText().toString().trim(), mPwd);
                                finish();
                            } else {
                                showToast("注册失败，请稍后重试");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            onError(e);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        showToast("注册失败，请稍后重试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void requestLogin(String tempUsername, String tempPwd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("account", tempUsername);
        map.put("password", tempPwd);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.mLogin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showWaitDialog("正在登录...");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        //  if (AccountHelper.login(user, headers)) {
                        hideWaitDialog();
                        try {
                            String body = responseBody.string();
                            if (body.length() < 10) {
                                if (body.equals("\"false\""))
                                    showToast("用户名或密码错误");
                            } else {
                                Gson gson = new Gson();
                                try {
                                    List<User> userList = gson.fromJson(body, new TypeToken<List<User>>() {
                                    }.getType());
                                    if (AccountHelper.login(userList.get(0))) {
                                        SharedPreferences sp = getSharedPreferences("userxinxi", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("custname", userList.get(0).getCustname());
                                        editor.putString("custphone", userList.get(0).getCustphone());
                                        editor.apply();

                                        SharedPreferences sharedPreferences = getSharedPreferences("userpassword", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor1 = sharedPreferences.edit();
                                        editor1.putString("name", etUserName.getText().toString().trim());
                                        editor1.putString("password", etPwd.getText().toString().trim());
                                        editor1.apply();

                                        MyJPushMessageReceiver myJPushMessageReceiver = new MyJPushMessageReceiver();
                                        JPushMessage jPushMessage = new JPushMessage();
                                        jPushMessage.setAlias(userList.get(0).getId() + "");
                                        myJPushMessageReceiver.onAliasOperatorResult(RegisterTwoActivity.this, jPushMessage);

                                        JPushInterface.init(RegisterTwoActivity.this);
                                        if (JPushInterface.isPushStopped(RegisterTwoActivity.this)) {
                                            JPushInterface.resumePush(RegisterTwoActivity.this);
                                        }
                                        JPushInterface.setAlias(RegisterTwoActivity.this, userList.get(0).getId() + "", null);

                                        logSucceed();

                                    } else {
                                        showToast("登录异常");
                                    }
                                } catch (Exception e) {
                                    showToast("用户信息获取异常，请重新登录");
                                }

                            }

                        } catch (IOException e) {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                        showToast("登录失败，请稍后重试");
                        etUserName.setFocusableInTouchMode(false);
                        etUserName.clearFocus();
                        etPwd.requestFocus();
                        etPwd.setFocusableInTouchMode(true);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private void logSucceed() {
        View view;
        if ((view = getCurrentFocus()) != null) {
            hideKeyBoard(view.getWindowToken());
        }
        showToast("登录成功");
        setResult(RESULT_OK);
        sendLocalReceiver();
        holdAccount();
//        if (LoginActivity.instance != null) {
//            LoginActivity.instance.finish();
//        }
    }

    /**
     * hold account information
     */
    private void holdAccount() {
        String username = et_phone.getText().toString().trim();
        if (!TextUtils.isEmpty(username)) {
            SharedPreferences sp = getSharedPreferences(Constant.HOLD_ACCOUNT, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(HOLD_USERNAME_KEY, username);
            SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        }
    }
}
