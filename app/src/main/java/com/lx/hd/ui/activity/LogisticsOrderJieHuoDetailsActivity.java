package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.EWaiAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.DriverZhaoHuoDetailsEntity;
import com.lx.hd.bean.EWaiEntity;
import com.lx.hd.bean.EWaiEntity1;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.lx.hd.base.Constant.REQUEST_CODE;

public class LogisticsOrderJieHuoDetailsActivity extends BackCommonActivity implements View.OnClickListener {

    private RecyclerView act_recyclerView;
    private TextView tv_dingdanhao,tv_type,tv_name,tv_address,tv_address1,
            tv_car_type,tv_yongchetime,tv_dingdantime,tv_juli,tv_money;
    private ImageView img_type,img_tu,img_call;
    private String id;
    private List<DriverZhaoHuoDetailsEntity> driverZhaoHuoDetailsEntityList;
    private List<EWaiEntity> eWaiEntityList;

    @Override
    protected int getContentView() {
        return R.layout.activity_logistics_order_jie_huo_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("司机接货单订单详情");
        id= getIntent().getStringExtra("id");

        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);
        tv_dingdanhao= (TextView) findViewById(R.id.tv_dingdanhao);
        tv_type= (TextView) findViewById(R.id.tv_type);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_address1= (TextView) findViewById(R.id.tv_address1);
        tv_car_type= (TextView) findViewById(R.id.tv_car_type);
        tv_yongchetime= (TextView) findViewById(R.id.tv_yongchetime);
        tv_dingdantime= (TextView) findViewById(R.id.tv_dingdantime);
        tv_juli= (TextView) findViewById(R.id.tv_juli);
        tv_money= (TextView) findViewById(R.id.tv_money);
        img_type= (ImageView) findViewById(R.id.img_type);
        img_tu= (ImageView) findViewById(R.id.img_tu);
        img_call= (ImageView) findViewById(R.id.img_call);
        img_call.setOnClickListener(this);

        initDetails();
    }

    private void initDetails() {
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

                                Toast.makeText(LogisticsOrderJieHuoDetailsActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
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

    private void initFuzhi() {
        tv_dingdanhao.setText("订单号："+driverZhaoHuoDetailsEntityList.get(0).getOwner_orderno());
        if (driverZhaoHuoDetailsEntityList.get(0).getDriver_orderstatus()==-1){
            tv_type.setVisibility(View.GONE);
            img_type.setVisibility(View.VISIBLE);
            img_type.setImageResource(R.mipmap.img_yiquxiao1);
        }else if (driverZhaoHuoDetailsEntityList.get(0).getDriver_orderstatus()==0){
            tv_type.setVisibility(View.VISIBLE);
            img_type.setVisibility(View.GONE);
            tv_type.setText("前往中");
        }else if (driverZhaoHuoDetailsEntityList.get(0).getDriver_orderstatus()==1){
            tv_type.setVisibility(View.VISIBLE);
            img_type.setVisibility(View.GONE);
            tv_type.setText("开始订单");
        }else {
            tv_type.setVisibility(View.GONE);
            img_type.setVisibility(View.VISIBLE);
            img_type.setImageResource(R.mipmap.img_yiwancheng1);
        }
        Glide.with(this).load(Constant.BASE_URL + driverZhaoHuoDetailsEntityList.get(0).getFolder() + "/" + driverZhaoHuoDetailsEntityList.get(0).getAutoname()).into(img_tu);
        tv_name.setText(driverZhaoHuoDetailsEntityList.get(0).getOwner_link_name());
        tv_address.setText(driverZhaoHuoDetailsEntityList.get(0).getOwner_address());
        tv_address1.setText(driverZhaoHuoDetailsEntityList.get(0).getOwner_send_address());
        tv_car_type.setText(driverZhaoHuoDetailsEntityList.get(0).getCar_type());
//        String substring = driverZhaoHuoDetailsEntityList.get(0).getOwner_sendtime().substring(0, driverZhaoHuoDetailsEntityList.get(0).getOwner_sendtime().length() - 3);
//        String substring1 = driverZhaoHuoDetailsEntityList.get(0).getOwner_createtime().substring(0, driverZhaoHuoDetailsEntityList.get(0).getOwner_createtime().length() - 3);

        tv_yongchetime.setText(driverZhaoHuoDetailsEntityList.get(0).getOwner_sendtime());
        tv_dingdantime.setText(driverZhaoHuoDetailsEntityList.get(0).getOwner_createtime());
        tv_juli.setText(driverZhaoHuoDetailsEntityList.get(0).getTotal_mileage() + "公里");
        tv_money.setText(driverZhaoHuoDetailsEntityList.get(0).getSiji_money() + "元");

    }

    private void initEWai() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", id);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchorderdetail(data)
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
                            if (body.indexOf("false") != -1 || body.length() < 6) {
//                                showToast("订单详情暂无数据");
                            } else {
                                Gson gson = new Gson();
                                eWaiEntityList = gson.fromJson(body, new TypeToken<List<EWaiEntity>>() {
                                }.getType());

                                initEwaiRecyclerView();


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

    private void initEwaiRecyclerView() {
        EWaiAdapter adapter=new EWaiAdapter(this,eWaiEntityList,driverZhaoHuoDetailsEntityList.get(0).getCust_orderstatus());
        LinearLayoutManager manager=new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        testCallPhone();
    }

    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(LogisticsOrderJieHuoDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

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

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(LogisticsOrderJieHuoDetailsActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-80969707");
        } else {
            Toast.makeText(this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }
}
