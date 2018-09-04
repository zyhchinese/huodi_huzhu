package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.KuaiYunSiJiLieBiaoEntity;
import com.lx.hd.bean.ShuFengCheXiangQingEntity;

import org.json.JSONException;
import org.json.JSONObject;

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

public class ShuFengCheXiangQingActivity extends BackCommonActivity {

    private TextView tv_qidian,tv_zhongdian,tv_tujingchengshi,tv_zongchechang,
            tv_keyongchechang,tv_time,tv_createtime,tv_fabuxingcheng,tv_sijiname,
            tv_call;
    private ShuFengCheXiangQingEntity entity;
    private String dingdanid="";

    @Override
    protected int getContentView() {
        return R.layout.activity_shu_feng_che_xiang_qing;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("货滴顺风车详情");
        if (!getIntent().getStringExtra("dingdanid").equals("")){
            dingdanid = getIntent().getStringExtra("dingdanid");
        }

        initView();

        initShuJu();
    }

    private void initView() {
        tv_qidian= (TextView) findViewById(R.id.tv_qidian);
        tv_zhongdian= (TextView) findViewById(R.id.tv_zhongdian);
        tv_tujingchengshi= (TextView) findViewById(R.id.tv_tujingchengshi);
        tv_zongchechang= (TextView) findViewById(R.id.tv_zongchechang);
        tv_keyongchechang= (TextView) findViewById(R.id.tv_keyongchechang);
        tv_time= (TextView) findViewById(R.id.tv_time);
        tv_createtime= (TextView) findViewById(R.id.tv_createtime);
        tv_fabuxingcheng= (TextView) findViewById(R.id.tv_fabuxingcheng);
        tv_sijiname= (TextView) findViewById(R.id.tv_sijiname);
        tv_call= (TextView) findViewById(R.id.tv_call);

        tv_fabuxingcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ShuFengCheXiangQingActivity.this)
                        .setTitle("提示")
                        .setMessage("确认用车吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (dingdanid.equals("")){
                                    initQuXiaoXingCheng();
                                }else {
                                    initXuanZeCiXingCheng();
                                }

                                dialog.dismiss();
                            }
                        });
                builder.show();

            }
        });

        tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consigneephone=entity.getResponse().get(0).getFreerideDet().getContactphone();
                AlertDialog.Builder builder11=new AlertDialog.Builder(ShuFengCheXiangQingActivity.this)
                        .setTitle("提示")
                        .setMessage("确定拨打电话："+consigneephone)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                testCallPhone();
                            }
                        });
                builder11.show();
            }
        });
    }

    private void initXuanZeCiXingCheng() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderid", dingdanid);
        map.put("frid", getIntent().getStringExtra("id"));
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.joinFreeRideOne(data)
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
                            String msg = jsonObject.getString("msg");

                            if (flag.equals("200")){
                                showToast(msg);
                            }else {
                                showToast(msg);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

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
        String consigneephone=entity.getResponse().get(0).getFreerideDet().getContactphone();
        //直接拨号
        Uri uri = Uri.parse("tel:" + consigneephone );
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

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-80969707");
        } else {
            Toast.makeText(this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void initQuXiaoXingCheng() {

        HashMap<String, String> map = new HashMap<>();
        map.put("frid", getIntent().getStringExtra("id"));
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.checkJoinFreeRideOne(data)
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
                            String msg = jsonObject.getString("msg");

                            if (flag.equals("200")){
                                Intent intent=new Intent(ShuFengCheXiangQingActivity.this,HuoDKuaiyunActivity.class);
                                intent.putExtra("startaddress",entity.getResponse().get(0).getFreerideDet().getSaddress());
                                intent.putExtra("startlongitude",entity.getResponse().get(0).getFreerideDet().getSlongitude());
                                intent.putExtra("startlatitude",entity.getResponse().get(0).getFreerideDet().getSlatitude());
                                intent.putExtra("endaddress",entity.getResponse().get(0).getFreerideDet().getEaddress());
                                intent.putExtra("endlongitude",entity.getResponse().get(0).getFreerideDet().getElongitude());
                                intent.putExtra("endlatitude",entity.getResponse().get(0).getFreerideDet().getElatitude());
                                intent.putExtra("total_mileage",entity.getResponse().get(0).getFreerideDet().getDistance());
                                intent.putExtra("sprovince",entity.getResponse().get(0).getFreerideDet().getSprovince());
                                intent.putExtra("scity",entity.getResponse().get(0).getFreerideDet().getScity());
                                intent.putExtra("scounty",entity.getResponse().get(0).getFreerideDet().getScounty());
                                intent.putExtra("eprovince",entity.getResponse().get(0).getFreerideDet().getEprovince());
                                intent.putExtra("ecity",entity.getResponse().get(0).getFreerideDet().getEcity());
                                intent.putExtra("ecounty",entity.getResponse().get(0).getFreerideDet().getEcounty());
                                intent.putExtra("selFreeRideId",entity.getResponse().get(0).getFreerideDet().getId()+"");
                                intent.putExtra("shunfengbiaoshi","5");
                                startActivity(intent);
                            }else if (flag.equals("300")){
                                AlertDialog.Builder builder=new AlertDialog.Builder(ShuFengCheXiangQingActivity.this)
                                        .setTitle("提示")
                                        .setMessage(msg)
                                        .setNegativeButton("可继续发布", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Intent intent=new Intent(ShuFengCheXiangQingActivity.this,HuoDKuaiyunActivity.class);
                                                intent.putExtra("startaddress",entity.getResponse().get(0).getFreerideDet().getSaddress());
                                                intent.putExtra("startlongitude",entity.getResponse().get(0).getFreerideDet().getSlongitude());
                                                intent.putExtra("startlatitude",entity.getResponse().get(0).getFreerideDet().getSlatitude());
                                                intent.putExtra("endaddress",entity.getResponse().get(0).getFreerideDet().getEaddress());
                                                intent.putExtra("endlongitude",entity.getResponse().get(0).getFreerideDet().getElongitude());
                                                intent.putExtra("endlatitude",entity.getResponse().get(0).getFreerideDet().getElatitude());
                                                intent.putExtra("total_mileage",entity.getResponse().get(0).getFreerideDet().getDistance());
                                                intent.putExtra("sprovince",entity.getResponse().get(0).getFreerideDet().getSprovince());
                                                intent.putExtra("scity",entity.getResponse().get(0).getFreerideDet().getScity());
                                                intent.putExtra("scounty",entity.getResponse().get(0).getFreerideDet().getScounty());
                                                intent.putExtra("eprovince",entity.getResponse().get(0).getFreerideDet().getEprovince());
                                                intent.putExtra("ecity",entity.getResponse().get(0).getFreerideDet().getEcity());
                                                intent.putExtra("ecounty",entity.getResponse().get(0).getFreerideDet().getEcounty());
                                                intent.putExtra("selFreeRideId",entity.getResponse().get(0).getFreerideDet().getId()+"");
                                                intent.putExtra("shunfengbiaoshi","5");
                                                startActivity(intent);
                                            }
                                        })
                                        .setPositiveButton("返回顺风车列表", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                builder.show();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

    private void initShuJu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.selectFreeRideDet(data)
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
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {

                                entity=new Gson().fromJson(body,ShuFengCheXiangQingEntity.class);

                                initFuZhi();


                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

    private void initFuZhi() {
        if (entity.getResponse().get(0).getFreerideDet().getState()==0){
            tv_fabuxingcheng.setVisibility(View.GONE);
        }else {
            tv_fabuxingcheng.setVisibility(View.VISIBLE);
        }
        tv_qidian.setText(entity.getResponse().get(0).getFreerideDet().getScity());
        tv_zhongdian.setText(entity.getResponse().get(0).getFreerideDet().getEcity());
        tv_tujingchengshi.setText(entity.getResponse().get(0).getFreerideDet().getWaytocitysshow());
        tv_zongchechang.setText(entity.getResponse().get(0).getFreerideDet().getTotalvehicle());
        tv_keyongchechang.setText(entity.getResponse().get(0).getFreerideDet().getAvailablevehicle());
        tv_time.setText(entity.getResponse().get(0).getFreerideDet().getDeptime());
        tv_createtime.setText(entity.getResponse().get(0).getFreerideDet().getCretime());
        tv_sijiname.setText(entity.getResponse().get(0).getFreerideDet().getContactname());

    }
}
