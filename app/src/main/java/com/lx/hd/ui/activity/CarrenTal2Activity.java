package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.adapter.CarrenTalAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.CarrenTal_order;
import com.lx.hd.bean.User;
import com.lx.hd.utils.StringUtils;

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

import static com.lx.hd.base.Constant.REQUEST_CODE;

/**
 * 租车界面
 */
public class CarrenTal2Activity extends BackActivity implements OnClickListener {
    private EditText qymc, lxrname, sjh, yx;
    private CarrenTal_order CarrenTal_order = new CarrenTal_order();
    private TextView ye, submit;
    private ImageView call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("车辆租赁");
        setTitleIcon(R.mipmap.clzl_car);
        qymc = (EditText) findViewById(R.id.qymc);
        lxrname = (EditText) findViewById(R.id.lxrname);
        sjh = (EditText) findViewById(R.id.sjh);
        yx = (EditText) findViewById(R.id.yx);
        call = (ImageView) findViewById(R.id.call);
        submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        call.setOnClickListener(this);
        CarrenTal_order = new Gson().fromJson(getIntent().getStringExtra("order"), CarrenTal_order.getClass());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_carrental2;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.call) {
            testCallPhone();
        }
        if (v.getId() == R.id.submit) {
            if (lxrname.getText().toString().trim().length() == 0) {
                showToast("请填写联系人姓名");
                return;
            } else if (sjh.getText().toString().trim().length() == 0) {
                showToast("请填写联系人手机");
                return;
            }
            if (yx.getText().toString().length() > 0) {
                if (!StringUtils.emailFormat(yx.getText().toString())) {
                    showToast("请填写正确的邮箱");
                    return;
                }
            }
            if (!phone(sjh.getText().toString().trim()) && sjh.getText().toString().trim().length() != 0) {
                showToast("请填写正确的手机号");
                return;
            }
            CarrenTal_order.setLink_org_name(qymc.getText().toString());
            CarrenTal_order.setLink_email(yx.getText().toString());
            CarrenTal_order.setLink_name(lxrname.getText().toString());
            CarrenTal_order.setLink_phone(sjh.getText().toString());
            gosubmit(CarrenTal_order);
        }

    }

    private boolean phone(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private void gosubmit(CarrenTal_order data) {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("createtimeGE", "");
//        map.put("createtimeLE", "");
        Gson gson = new Gson();
        String params = gson.toJson(data);
        PileApi.instance.addLeaseOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showWaitDialog("正在获取信息...");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            //   Gson gson = new Gson();
//                            SumChongDian userList = gson.fromJson(body, new TypeToken<SumChongDian>() {
//                            }.getType());
//
                            if (body.indexOf("true") != -1) {
                                startActivity(new Intent(CarrenTal2Activity.this, CarrenTal3Activity.class));
                            } else {
                                showToast("提交失败，请检查网络");
                            }
//                            updateSumChongDian(userList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {
                        hideWaitDialog();
                    }
                });

    }

    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(CarrenTal2Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            } else {
                callPhone("0531-80969707");
            }

        } else {
            callPhone("0531-80969707");
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

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(CarrenTal2Activity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-80969707");
        } else {
            Toast.makeText(this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
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
}
