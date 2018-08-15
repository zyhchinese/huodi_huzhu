package com.lx.hd.update;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.VersionBean;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.utils.TDevice;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 在线更新对话框
 */

public class NoUpdateActivity extends BaseActivity implements View.OnClickListener,
        EasyPermissions.PermissionCallbacks{
    TextView mTextUpdateInfo;
    Button btnUpdate;
    ImageButton btnClose;
    private VersionBean mVersion;
    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限

    public static void show(Activity activity, VersionBean version) {
        Intent intent = new Intent(activity, NoUpdateActivity.class);
        intent.putExtra("version", version);
        activity.startActivityForResult(intent, 0x01);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_no_new_version;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTextUpdateInfo=(TextView)findViewById(R.id.tv_update_info);
        btnUpdate=(Button)findViewById(R.id.btn_update);
        btnClose=(ImageButton) findViewById(R.id.btn_close);
        btnUpdate.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initData() {
        super.initData();
        setTitle("");
     //   setBackgroundAlpha(0.5f);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mVersion = (VersionBean) getIntent().getSerializableExtra("version");
        mTextUpdateInfo.setText("当前版本号："+mVersion.getApp_version());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                if (!TDevice.isWifiOpen()) {
                    DialogHelper.getConfirmDialog(this, "当前非wifi环境，是否升级？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestExternalStorage();
                            finish();
                        }
                    }).show();
                } else {
                    requestExternalStorage();
                    finish();
                }
                break;
            case R.id.btn_close:
                finish();
                break;
        }

    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            mVersion.setApp_url("http://192.168.1.231:8080/showtime/img_avatar/aaa.apk");
            DownloadService.startService(this, mVersion.getApp_url());
        } else {
            EasyPermissions.requestPermissions(this, "", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        DialogHelper.getConfirmDialog(this, "温馨提示", "需要开启货滴货主对您手机的存储权限才能下载安装，是否现在开启", "去开启", "取消", true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
            }
        }, null).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //设置黑色半透明背景，建议设置0.4f,默认不设置
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        getWindow().setAttributes(lp);
    }

}
