package com.lx.hd.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static android.R.attr.action;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.MainActivity;
import com.mob.tools.utils.UIHandler;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.User;
import com.lx.hd.receiver.MyJPushMessageReceiver;
import com.lx.hd.utils.TDevice;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LoginActivity extends AccountBaseActivity implements View.OnClickListener, PlatformActionListener, Handler.Callback {
    private static final int MSG_ACTION_CCALLBACK = 0;
    private EditText etUserName, etPwd;
    private TextView tvForgetPwd, tvLogin, tvRegister;
    private ImageView qq, wechat;
    private ProgressDialog progressDialog;
    public static final String HOLD_USERNAME_KEY = "holdUsernameKey";
    private String phone,password;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        etUserName = (EditText) findViewById(R.id.et_username);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        tvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvForgetPwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        qq = (ImageView) findViewById(R.id.qq);
        wechat = (ImageView) findViewById(R.id.wechat);
        tvForgetPwd.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        qq.setOnClickListener(this);
        wechat.setOnClickListener(this);
        //初始化控件状态数据
        SharedPreferences sp = getSharedPreferences(Constant.HOLD_ACCOUNT, Context.MODE_PRIVATE);
        String holdUsername = sp.getString(HOLD_USERNAME_KEY, null);
        //String holdPwd = sp.getString(HOLD_PWD_KEY, null);
        //int holdStatus = sp.getInt(HOLD_PWD_STATUS_KEY, 0);//0第一次默认/1用户设置保存/2用户设置未保存

        etUserName.setText(holdUsername);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                loginRequest();
                break;
            case R.id.tv_forget_pwd:
                startActivity(new Intent(LoginActivity.this, ForgetPwdOneActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterTwoActivity.class));
                break;
            case R.id.qq:
//                Platform qq = ShareSDK.getPlatform(QQ.NAME);
//                qq.setPlatformActionListener(this);
//                qq.SSOSetting(false);
//                authorize(qq, 2);
                break;
            case R.id.wechat:
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(this);
                wechat.SSOSetting(false);
                authorize(wechat, 1);
                break;

        }
    }

    private void loginRequest() {

        String tempUsername = etUserName.getText().toString().trim();
        String tempPwd = etPwd.getText().toString().trim();
        if (!TextUtils.isEmpty(tempPwd) && !TextUtils.isEmpty(tempUsername)) {
            //登录成功,请求数据进入用户个人中心页面
            if (TDevice.hasInternet()) {
                initpanduanshoujihao(tempUsername,tempPwd);
//                requestLogin(tempUsername, tempPwd);
            } else {
                showToast("网络连接错误");
            }

        } else {
            if (TextUtils.isEmpty(tempPwd) || TextUtils.isEmpty(tempUsername)) {
                showToast("用户名或密码不能为空");
                return;
            }
        }

    }
    private void initpanduanshoujihao(String phone, String password){
        this.phone=phone;
        this.password=password;
        HashMap<String,String> map=new HashMap<String, String>();
        map.put("phone",phone);
        Gson gson=new Gson();
        String params=gson.toJson(map);
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
                            String body=responseBody.string();
                            if(body.equals("\"false\"")){
                                showToast("该手机还未注册");
                            }else {
                                requestLogin(LoginActivity.this.phone, LoginActivity.this.password);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        showToast("检测手机号失败");
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
                                        SharedPreferences sp=getSharedPreferences("userxinxi",Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor=sp.edit();
                                        editor.putString("custname",userList.get(0).getCustname());
                                        editor.putString("custphone",userList.get(0).getCustphone());
                                        editor.apply();

                                        SharedPreferences sharedPreferences=getSharedPreferences("userpassword",Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor1=sharedPreferences.edit();
                                        editor1.putString("name",etUserName.getText().toString().trim());
                                        editor1.putString("password",etPwd.getText().toString().trim());
                                        editor1.apply();

                                        MyJPushMessageReceiver myJPushMessageReceiver=new MyJPushMessageReceiver();
                                        JPushMessage jPushMessage=new JPushMessage();
                                        jPushMessage.setAlias(userList.get(0).getId()+"");
                                        myJPushMessageReceiver.onAliasOperatorResult(LoginActivity.this,jPushMessage);

                                        JPushInterface.init(LoginActivity.this);
                                        if (JPushInterface.isPushStopped(LoginActivity.this)){
                                            JPushInterface.resumePush(LoginActivity.this);
                                        }
                                        JPushInterface.setAlias(LoginActivity.this,userList.get(0).getId()+"",null);

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
    }

    /**
     * hold account information
     */
    private void holdAccount() {
        String username = etUserName.getText().toString().trim();
        if (!TextUtils.isEmpty(username)) {
            SharedPreferences sp = getSharedPreferences(Constant.HOLD_ACCOUNT, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(HOLD_USERNAME_KEY, username);
            SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        }
    }

    //显示dialog
    public void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    //隐藏dialog
    public void hideProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    private void authorize(Platform plat, int type) {
        switch (type) {
            case 1:
                showProgressDialog("正在努力跳转微信..请稍候");
                break;
            case 2:
                showProgressDialog("正在努力跳转QQ..请稍候");
                break;
        }
        if (plat.isAuthValid()) { //如果授权就删除授权资料
            plat.removeAccount(true);
        }
        plat.showUser(null);//授权并获取用户信息
    }

    //登陆授权成功的回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> res) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);   //发送消息

    }

    //登陆授权错误的回调
    @Override
    public void onError(Platform platform, int i, Throwable t) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    //登陆授权取消的回调
    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    //登陆发送的handle消息在这里处理
    @Override
    public boolean handleMessage(Message message) {
        hideProgressDialog();
        switch (message.arg1) {
            case 1: { // 成功
                Toast.makeText(LoginActivity.this, "授权登陆成功", Toast.LENGTH_SHORT).show();

                //获取用户资料
                Platform platform = (Platform) message.obj;
                String userId = platform.getDb().getUserId();//获取用户账号
                String userName = platform.getDb().getUserName();//获取用户名字
                String userIcon = platform.getDb().getUserIcon();//获取用户头像
                String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
                Toast.makeText(LoginActivity.this, "用户信息为--用户名：" + userName + "  账号：" + userId, Toast.LENGTH_SHORT).show();

                //下面就可以利用获取的用户信息登录自己的服务器或者做自己想做的事啦!
                //。。。

            }
            break;
            case 2: { // 失败
                Toast.makeText(LoginActivity.this, "授权登陆失败", Toast.LENGTH_SHORT).show();
            }
            break;
            case 3: { // 取消
                Toast.makeText(LoginActivity.this, "授权登陆取消", Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sendPayLocalReceiver(MainActivity.class.getName());

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
}
