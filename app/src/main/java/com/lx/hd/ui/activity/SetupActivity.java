package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.VersionBean;
import com.lx.hd.update.CheckUpdateManager;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.utils.TDevice;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/***
 * 设置页面activity
 * @author tb
 * 2017/8/25
 */
public class SetupActivity extends BackCommonActivity implements View.OnClickListener,
        EasyPermissions.PermissionCallbacks,CheckUpdateManager.RequestPermissions{
    private Button btn;
    private LinearLayout cvUpdate, cvSecurity,cvAdvice, about,changjianwenti;
    private TextView tvVersion;
    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限

    @Override
    protected int getContentView() {
        return R.layout.setup_activity;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        about = (LinearLayout) findViewById(R.id.about);
        cvUpdate = (LinearLayout) findViewById(R.id.update);
        cvSecurity = (LinearLayout) findViewById(R.id.security);
        cvAdvice = (LinearLayout) findViewById(R.id.cv_advice);
        changjianwenti = (LinearLayout) findViewById(R.id.changjianwenti);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("当前版本号: V"+TDevice.getVersionName());
        cvUpdate.setOnClickListener(this);
        cvSecurity.setOnClickListener(this);
        cvAdvice.setOnClickListener(this);
        about.setOnClickListener(this);
        changjianwenti.setOnClickListener(this);
        btn = (Button) findViewById(R.id.goback);
        setTitleText("设置");
//        setTitleIcon(R.mipmap.sznar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SetupActivity.this).setTitle("确认退出登录吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                                showWaitDialog("正在注销...请稍候");
                                PileApi.instance.mLogout()
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
                                                    if (body.indexOf("true") != -1) {
                                                        hideWaitDialog();

                                                        JPushInterface.stopPush(SetupActivity.this);
                                                        JPushInterface.setAlias(SetupActivity.this,"",null);

                                                        SharedPreferences sharedPreferences=getSharedPreferences("userpassword", Context.MODE_PRIVATE);
                                                        SharedPreferences.Editor editor1=sharedPreferences.edit();
                                                        editor1.putString("name","");
                                                        editor1.putString("password","");
                                                        editor1.apply();

                                                        startActivity(new Intent(SetupActivity.this, LoginActivity.class));
                                                        finish();
                                                    } else {
                                                        showToast("注销失败");
                                                        hideWaitDialog();
                                                    }
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {
                                                showToast("网络状况不好，请重试");
                                                hideWaitDialog();

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });

                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }
                        }).show();

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        // 可以进行定位、检测更新等操作
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.update:
                requestExternalStorage();
                break;
            case R.id.security:
                startActivity(new Intent(SetupActivity.this, SecurityActivity.class));
                break;
            case R.id.cv_advice:
                startActivity(new Intent(SetupActivity.this, FeedBackActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(SetupActivity.this, AboutActivity.class));
                break;
            case R.id.changjianwenti:
                initPanduan();
                break;
        }
    }

    @Override
    public void call(VersionBean version) {

    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            CheckUpdateManager manager = new CheckUpdateManager(SetupActivity.this, true);
            manager.setCaller(this);
            manager.checkUpdate();
        } else {
            EasyPermissions.requestPermissions(this, "存储权限未授权,是否去授权", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        for (String perm : perms) {
            if (perm.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                DialogHelper.getConfirmDialog(this, "温馨提示", "需要开启对您手机的存储权限才能下载安装，是否现在开启", "去开启", "取消", true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                    }
                }, null).show();

            } else {
                //  Setting.updateLocationPermission(getApplicationContext(), false);
            }

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void initPanduan() {
        PileApi.instance.checkOwnerStatus()
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

                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                //审核通过
                                Intent intent = new Intent(SetupActivity.this, DriverCertificationStateActivity.class);
                                startActivity(intent);
                            }else if (flag.equals("210")){
                                //未认证
                                Intent intent = new Intent(SetupActivity.this, DriverCertificationActivity.class);
                                startActivity(intent);
                            }else if (flag.equals("220")){
                                //正在审核中
                                Intent intent = new Intent(SetupActivity.this, DriverCertificationStateActivity.class);
                                startActivity(intent);
                            }else if (flag.equals("230")){
                                //审核未通过
                                Intent intent = new Intent(SetupActivity.this, DriverCertificationStateActivity.class);
                                startActivity(intent);
                            }


//                            String substring = body.substring(1, body.length() - 1);
//
//                            if (body.indexOf("司机已被停用") != -1) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(MywalletActivity.this)
//                                        .setTitle("提示")
//                                        .setMessage(body)
//                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//                                            }
//                                        });
//                                builder.show();
//                                return;
//                            }
//                            if (substring.equals("-1")) {
//                                startActivity(new Intent(MywalletActivity.this, DriverCertificationActivity.class));
//                            } else {
//
//                                Gson gson = new Gson();
//                                checkSiJiRenZhengEntityList = gson.fromJson(body, new TypeToken<List<CheckSiJiRenZhengEntity>>() {
//                                }.getType());
//                                String imglist = checkSiJiRenZhengEntityList.get(0).getImglist();
//                                Gson gson1 = new Gson();
//                                checkSiJiRenZhengImgEntityList = gson1.fromJson(imglist, new TypeToken<List<CheckSiJiRenZhengImgEntity>>() {
//                                }.getType());
//                                if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 0) {
//                                    //审核中
//                                    Intent intent = new Intent(MywalletActivity.this, DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
//                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 1) {
//                                    //审核通过
////                                    if (type==1){
////                                        Intent intent1 = new Intent(getContext(), HuoDSuyun1Activity.class);
////                                        intent1.putExtra("cs", tvLocation.getText().toString());
////                                        startActivity(intent1);
////                                    }else if (type==2){
////                                        Intent intent1 = new Intent(getContext(), HuoDKuaiyun1Activity.class);
////                                        intent1.putExtra("cs", tvLocation.getText().toString());
////                                        startActivity(intent1);
////                                    }
//
//                                    Intent intent = new Intent(MywalletActivity.this, DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
//                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 2) {
//                                    //审核拒绝
//                                    Intent intent = new Intent(MywalletActivity.this, DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
//                                }
//                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

}
