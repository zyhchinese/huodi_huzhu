package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.CarrenTalAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CarrenTal_order;
import com.lx.hd.bean.Lease_order_detailList;
import com.lx.hd.bean.User;
import com.lx.hd.bean.carhometopBean;
import com.lx.hd.interf.CarrenTalListener;
import com.lx.hd.widgets.myListView;
import com.lx.hd.widgets.wheelview.ChangeDatePopwindow;
import com.lx.hd.widgets.wheelview.ChangeDatePopwindow2;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
public class CarrenTalActivity extends BackActivity implements OnClickListener, CarrenTalListener {
    private TextView submit;
    private TextView ghcs;
    private TextView cs;
    private TextView qxz;
    private myListView list;
    private CarrenTalAdapter CarrenTalAdapter;
    private LinearLayout add, main;
    private RelativeLayout yjtime;
    private boolean flag = false;
    private CarrenTal_order CarrenTal_order;
    private ImageView call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("车辆租赁");
        setTitleIcon(R.mipmap.clzl_car);
        add = (LinearLayout) findViewById(R.id.add);
        main = (LinearLayout) findViewById(R.id.main);
        yjtime = (RelativeLayout) findViewById(R.id.yjtime);
        submit = (TextView) findViewById(R.id.submit);
        cs = (TextView) findViewById(R.id.cs);
        submit.setOnClickListener(this);
        call = (ImageView) findViewById(R.id.call);
        add.setOnClickListener(this);
        ghcs = (TextView) findViewById(R.id.ghcs);
        ghcs.setOnClickListener(this);
        qxz = (TextView) findViewById(R.id.qxz);
        yjtime.setOnClickListener(this);
        list = (myListView) findViewById(R.id.list);
        List<Lease_order_detailList> data = new ArrayList<Lease_order_detailList>();
        Lease_order_detailList bean = new Lease_order_detailList();
        bean.setLease_count(1 + "");
        bean.setOrder_type(1 + "");
        String name = getIntent().getStringExtra("name");
        String id = getIntent().getStringExtra("id");
        bean.setModel_id(id);
        call.setOnClickListener(this);
        data.add(bean);
        CarrenTalAdapter = new CarrenTalAdapter(this, data, cs.getText().toString(), name);
        list.setAdapter(CarrenTalAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Constant.city.equals("")) {
            cs.setText(Constant.city);
            if (CarrenTalAdapter != null) {
                CarrenTalAdapter.updatecs(Constant.city);

            }
            if (flag) {
                if (Constant.cityflag) {
                    CarrenTalAdapter.deleteall();
                    CarrenTalAdapter.additem();
                    CarrenTalAdapter.notifyDataSetChanged();
                    flag = false;
                    Constant.cityflag = false;
                }
            }
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_carrental;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.call) {
            testCallPhone();
        }
        if (v.getId() == R.id.submit) {
            int i = 1;
            if (CarrenTalAdapter.getmMarkerData().size() < 1) {
                showToast("请增加至少一个车型");
            }
            for (Lease_order_detailList temp : CarrenTalAdapter.getmMarkerData()) {

                if (temp.getModel_id() == null || "".equals(temp.getModel_id())) {
                    showToast("请选择需求（" + i + "）的车辆型号");
                    return;
                } else if (temp.getDuring_time() == null || "".equals(temp.getDuring_time())) {
                    showToast("请选择需求（" + i + "）的租车时长");
                    return;
                }
                i++;
            }
            if ("请选择".equals(qxz.getText().toString())) {
                showToast("请选择预计取车时间");
                return;
            }
            CarrenTal_order = new CarrenTal_order();
            CarrenTal_order.setTake_time(qxz.getText().toString());
            CarrenTal_order.setLease_order_detailList(CarrenTalAdapter.getmMarkerData());
            Intent intent1 = new Intent(CarrenTalActivity.this, CarrenTal2Activity.class);
            intent1.putExtra("order", new Gson().toJson(CarrenTal_order));
            startActivity(intent1);
        } else if (v.getId() == R.id.ghcs) {
            flag = true;
            Intent intent = new Intent(this, ProvinceActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.add) {
            CarrenTalAdapter.additem();
            CarrenTalAdapter.notifyDataSetChanged();
        } else if (v.getId() == R.id.yjtime) {
            final String[] str = new String[10];
            ChangeDatePopwindow2 mChangeBirthDialog = new ChangeDatePopwindow2(CarrenTalActivity.this);
            //mChangeBirthDialog.setDate("2017", "6", "20");
            mChangeBirthDialog.showAtLocation(main, Gravity.BOTTOM, 0, 0);
            mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow2.OnBirthListener() {

                @Override
                public void onClick(String year, String month, String day) {
                    // TODO Auto-generated method stub
                    //    Toast.makeText(CarrenTalAdapter.this,year + "-" + month + "-" + day,Toast.LENGTH_LONG).show();
                    // StringBuilder sb = new StringBuilder();
//                    sb.append(year.substring(0, year.length() - 1)).append("-").append(month.substring(0, day.length() - 1)).append("-").append(day);
//                    str[0] = year + "-" + month + "-" + day;
//                    str[1] = sb.toString();

                    //button5.setText(str[0]);

                    qxz.setText(year + " " + day);
                }
            });
        }
    }

    @Override
    public void delete(int id) {
        CarrenTalAdapter.delete(id);
        CarrenTalAdapter.notifyDataSetChanged();
    }

    class LocalsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //收到广播后的处理

            String location = intent.getStringExtra("location");
            if (Constant.city.equals("")) {
                cs.setText(location);
                if (Constant.cityflag) {
                    if (CarrenTalAdapter != null) {
                        CarrenTalAdapter.updatecs(location);
                        CarrenTalAdapter.deleteall();
                        CarrenTalAdapter.additem();
                        CarrenTalAdapter.notifyDataSetChanged();
                        Constant.cityflag=false;
                    }
                }

            }
        }
    }

    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(CarrenTalActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

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

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(CarrenTalActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
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
