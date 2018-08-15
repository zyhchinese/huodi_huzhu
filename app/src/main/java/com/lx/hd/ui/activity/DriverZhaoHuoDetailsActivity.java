package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.EWaiAdapter1;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.DriverZhaoHuoDetailsEntity;
import com.lx.hd.bean.DriverZhaohuoEntity;
import com.lx.hd.bean.EWaiEntity1;
import com.lx.hd.bean.SiJiJieHuoEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.lx.hd.base.Constant.REQUEST_CODE;

public class DriverZhaoHuoDetailsActivity extends BackActivity implements View.OnClickListener {

    private RecyclerView act_recyclerView;
    private CircleImageView img_tu;
    private TextView tv_name, tv_address, tv_address1, tv_qidianjuli, tv_car_type, tv_yongchetime,
            tv_dingdantime, tv_juli, tv_money;
    private Button btn_kaishi;
    private ImageView img_call;
    private RelativeLayout rly_title_root;
    private TextView tv_title;
    private ImageView img_back;
    private String id;
    private String licheng;
    private List<DriverZhaoHuoDetailsEntity> driverZhaoHuoDetailsEntityList;
    private List<EWaiEntity1> eWaiEntity1List;


    @Override
    protected int getContentView() {
        return R.layout.activity_driver_zhao_huo_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("物流找货");
        setTitleIcon(R.mipmap.img_title_xiaoche);
        id = getIntent().getStringExtra("id");
        licheng = getIntent().getStringExtra("licheng");

        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        img_tu = (CircleImageView) findViewById(R.id.img_tu);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_address1 = (TextView) findViewById(R.id.tv_address1);
        tv_qidianjuli = (TextView) findViewById(R.id.tv_qidianjuli);
        tv_car_type = (TextView) findViewById(R.id.tv_car_type);
        tv_yongchetime = (TextView) findViewById(R.id.tv_yongchetime);
        tv_dingdantime = (TextView) findViewById(R.id.tv_dingdantime);
        tv_juli = (TextView) findViewById(R.id.tv_juli);
        tv_money = (TextView) findViewById(R.id.tv_money);
        btn_kaishi = (Button) findViewById(R.id.btn_kaishi);
        img_call= (ImageView) findViewById(R.id.img_call);

        rly_title_root= (RelativeLayout) findViewById(R.id.rly_title_root);
        tv_title= (TextView) findViewById(R.id.tv_title);
        img_back= (ImageView) findViewById(R.id.img_back);
        rly_title_root.setBackgroundResource(R.mipmap.img_title_baitiao);
        tv_title.setTextColor(Color.BLACK);
        img_back.setImageResource(R.mipmap.goback_wlzh);
        btn_kaishi.setOnClickListener(this);
        img_call.setOnClickListener(this);

        initQiangdanDetails();
    }

    private void initQiangdanDetails() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectLogisticsOrderDetail(params)
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


                            if (body.indexOf("false") != -1 || body.length() < 10) {

                                Toast.makeText(DriverZhaoHuoDetailsActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                driverZhaoHuoDetailsEntityList = gson.fromJson(body, new TypeToken<List<DriverZhaoHuoDetailsEntity>>() {
                                }.getType());
                                //加载额外列表
                                initEWai();

                                initFuzhi();


                            }
                        } catch (IOException e) {
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

    private void initEWai() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectLogisticsOrderDetailServ(params)
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


                            if (body.indexOf("false") != -1 || body.length() < 10) {

//                                Toast.makeText(DriverZhaoHuoDetailsActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                eWaiEntity1List = gson.fromJson(body, new TypeToken<List<EWaiEntity1>>() {
                                }.getType());

                                initEWaiRecyclerView();


                            }
                        } catch (IOException e) {
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

    private void initEWaiRecyclerView() {
        EWaiAdapter1 adapter = new EWaiAdapter1(this, eWaiEntity1List);
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
    }

    private void initFuzhi() {
        Glide.with(this).load(Constant.BASE_URL + driverZhaoHuoDetailsEntityList.get(0).getFolder() + "/" + driverZhaoHuoDetailsEntityList.get(0).getAutoname()).into(img_tu);
        tv_name.setText(driverZhaoHuoDetailsEntityList.get(0).getOwner_link_name());
        tv_address.setText(driverZhaoHuoDetailsEntityList.get(0).getOwner_address());
        tv_address1.setText(driverZhaoHuoDetailsEntityList.get(0).getOwner_send_address());
        tv_qidianjuli.setText(licheng+"公里");
        tv_car_type.setText(driverZhaoHuoDetailsEntityList.get(0).getCar_type());
        String substring = driverZhaoHuoDetailsEntityList.get(0).getOwner_sendtime().substring(0, driverZhaoHuoDetailsEntityList.get(0).getOwner_sendtime().length() - 3);
        String substring1 = driverZhaoHuoDetailsEntityList.get(0).getOwner_createtime().substring(0, driverZhaoHuoDetailsEntityList.get(0).getOwner_createtime().length() - 3);

        tv_yongchetime.setText(substring);
        tv_dingdantime.setText(substring1);
        tv_juli.setText(driverZhaoHuoDetailsEntityList.get(0).getTotal_mileage() + "公里");
        tv_money.setText(driverZhaoHuoDetailsEntityList.get(0).getSiji_money() + "元");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_kaishi:
                initQiangdan();
                break;
            case R.id.img_call:
                testCallPhone();
                break;
        }

    }

    private void initQiangdan() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.updateLogisticsOrderGrabASingle(params)
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


                            if (body.indexOf("true") != -1) {
                                final AlertDialog dialog = new AlertDialog.Builder(DriverZhaoHuoDetailsActivity.this).create();
                                dialog.show();
                                dialog.getWindow().setContentView(R.layout.dialog_qiangdanchenggong);
                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);

                                WindowManager windowManager=getWindowManager();
                                Display display=windowManager.getDefaultDisplay();
                                WindowManager.LayoutParams p=dialog.getWindow().getAttributes();
                                p.width= (int) (display.getWidth()*0.6);
                                dialog.getWindow().setAttributes(p);

                                TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(DriverZhaoHuoDetailsActivity.this, JiHuoOrderActivity.class);
                                        startActivity(intent);
                                        dialog.dismiss();
//                                        finish();
                                    }
                                });
                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                    }
                                });

                            } else if (body.indexOf("self") != -1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DriverZhaoHuoDetailsActivity.this)
                                        .setTitle("提示：抢单失败")
                                        .setMessage("自己不能抢自己的订单")
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.show();

                            } else if (body.indexOf("berobbed") != -1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DriverZhaoHuoDetailsActivity.this)
                                        .setTitle("提示：抢单失败")
                                        .setMessage("该订单已被抢")
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DriverZhaoHuoDetailsActivity.this)
                                        .setTitle("提示")
                                        .setMessage("抢单失败")
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.show();
                            }
                        } catch (IOException e) {
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

    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(DriverZhaoHuoDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

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
        Uri uri = Uri.parse("tel:" + driverZhaoHuoDetailsEntityList.get(0).getOwner_link_phone());
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

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(DriverZhaoHuoDetailsActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-80969707");
        } else {
            Toast.makeText(this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }
}
