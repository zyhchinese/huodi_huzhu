package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.User;

import io.reactivex.annotations.NonNull;

import static com.lx.hd.base.Constant.REQUEST_CODE;

/**
 * 租车界面
 */
public class CarrenTal3Activity extends BackActivity implements OnClickListener {


    private TextView tv_check;
    private RelativeLayout tv_save;
    private ImageView img_back, call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("车辆租赁");
        setTitleIcon(R.mipmap.clzl_car);

        tv_save = (RelativeLayout) findViewById(R.id.tv_save);
        tv_check = (TextView) findViewById(R.id.tv_check);
        img_back = (ImageView) findViewById(R.id.img_back);
        call = (ImageView) findViewById(R.id.call);

        tv_save.setOnClickListener(this);
        img_back.setOnClickListener(this);
        call.setOnClickListener(this);
        tv_check.setOnClickListener(this);

    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_carrental3;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                sendPayLocalReceiver(CarrenTalActivity.class.getName());
                sendPayLocalReceiver(CarrenTal2Activity.class.getName());
                finish();
                break;
            case R.id.img_back:
                sendPayLocalReceiver(CarrenTalActivity.class.getName());
                sendPayLocalReceiver(CarrenTal2Activity.class.getName());
                finish();
                break;
            case R.id.call:
                testCallPhone();
                break;
            case R.id.tv_check:
                sendPayLocalReceiver(CarrenTalActivity.class.getName());
                sendPayLocalReceiver(CarrenTal2Activity.class.getName());
                sendPayLocalReceiver(CarAudit1Activity.class.getName());
                Intent intent1 = new Intent(this, AuditStatusActivity.class);
                startActivity(intent1);
                finish();
                break;
        }

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

    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(CarrenTal3Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            } else {
                callPhone("0531-80969707");
            }

        } else {
            callPhone("0531-80969707");
        }
    }

    private void callPhone(String phoneNum) {
        //直接拨号
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            startActivity(intent);
        }
    }

    /**
     * 请求权限的回调方法
     *
     * @param requestCode  请求码
     * @param permissions  请求的权限
     * @param grantResults 权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(CarrenTal3Activity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-80969707");
        } else {
            Toast.makeText(this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }


}
