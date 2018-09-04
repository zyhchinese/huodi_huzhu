package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haibin.calendarview.Calendar;
import com.lx.hd.R;
import com.lx.hd.adapter.ProvinceAdapter111;
import com.lx.hd.adapter.ShuFengCheLieBiaoAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.ProvinceEntity;
import com.lx.hd.bean.QiTaYueQianDaoXinXiEntity;
import com.lx.hd.bean.ShuFengCheLieBiaoEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.lx.hd.base.Constant.REQUEST_CODE;

public class ShuFengCheLieBiaoActivity extends BackCommonActivity implements View.OnClickListener {

    private RecyclerView act_recyclerView;
    private RelativeLayout relative_chufadi,relative_mudidi;
    private TextView tv_chufadi,tv_mudidi;
    private String chufadisheng="",chufadishi="",chufadixian="",mudidisheng="",mudidishi="",mudidixian="";
    private ShuFengCheLieBiaoEntity shuFengCheLieBiaoEntity;
    private int position;
    private int position1;
    private RecyclerView recyclerView;
    private PopupWindow window;
    private int line=0;
    private int page=1;
    private List<ProvinceEntity> provinceEntityList;
    private List<ProvinceEntity> cityList;
    private List<ProvinceEntity> countyList;
    private String dingdanid="";

    @Override
    protected int getContentView() {
        return R.layout.activity_shu_feng_che_lie_biao;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("货滴顺风车列表");

        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);
        relative_chufadi= (RelativeLayout) findViewById(R.id.relative_chufadi);
        relative_mudidi= (RelativeLayout) findViewById(R.id.relative_mudidi);
        tv_chufadi= (TextView) findViewById(R.id.tv_chufadi);
        tv_mudidi= (TextView) findViewById(R.id.tv_mudidi);

        relative_chufadi.setOnClickListener(this);
        relative_mudidi.setOnClickListener(this);

        if (getIntent().getStringExtra("dingdanid")!=null){
            dingdanid = getIntent().getStringExtra("dingdanid");
            chufadisheng = getIntent().getStringExtra("chufasheng");
            chufadishi = getIntent().getStringExtra("chufashi");
            chufadixian = getIntent().getStringExtra("chufaxian");
            mudidisheng = getIntent().getStringExtra("mudisheng");
            mudidishi = getIntent().getStringExtra("mudishi");
            mudidixian = getIntent().getStringExtra("mudixian");
            tv_chufadi.setText(chufadixian);
            tv_mudidi.setText(mudidixian);
        }

        initShuJu();

    }

    private void initShuJu() {
        HashMap<String, String> map = new HashMap<>();
        if (chufadisheng.equals("全国")){
            map.put("sprovince", "");
        }else {
            map.put("sprovince", chufadisheng);
        }

        if (chufadishi.equals("全省")){
            map.put("scity", "");
        }else {
            map.put("scity", chufadishi);
        }
        if (chufadixian.equals("全市")){
            map.put("scounty", "");
        }else {
            map.put("scounty", chufadixian);
        }

        if (mudidisheng.equals("全国")){
            map.put("eprovince", "");
        }else {
            map.put("eprovince", mudidisheng);
        }

        if (mudidishi.equals("全省")){
            map.put("ecity", "");
        }else {
            map.put("ecity", mudidishi);
        }
        if (mudidixian.equals("全市")){
            map.put("ecounty", "");
        }else {
            map.put("ecounty", mudidixian);
        }
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchFreeRideList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {


                        try {
                            String body = responseBody.string();
                            System.out.println(body);
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                Gson gson = new Gson();
                                shuFengCheLieBiaoEntity = gson.fromJson(body, ShuFengCheLieBiaoEntity.class);
                                initRecyclerView();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initRecyclerView() {
        ShuFengCheLieBiaoAdapter adapter=new ShuFengCheLieBiaoAdapter(this,shuFengCheLieBiaoEntity.getResponse());
        LinearLayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new ShuFengCheLieBiaoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(ShuFengCheLieBiaoActivity.this,ShuFengCheXiangQingActivity.class).putExtra("id",shuFengCheLieBiaoEntity.getResponse().get(position).getId()+"").putExtra("dingdanid",dingdanid));
            }
        });
        adapter.setOnClickCall(new ShuFengCheLieBiaoAdapter.OnClickCall() {
            @Override
            public void onClick(int position) {
                ShuFengCheLieBiaoActivity.this.position=position;
                String consigneephone=shuFengCheLieBiaoEntity.getResponse().get(position).getContactphone();
                AlertDialog.Builder builder11=new AlertDialog.Builder(ShuFengCheLieBiaoActivity.this)
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
        adapter.setOnClickXuanZe(new ShuFengCheLieBiaoAdapter.OnClickXuanZe() {
            @Override
            public void onClick(int position) {
                position1=position;
                AlertDialog.Builder builder=new AlertDialog.Builder(ShuFengCheLieBiaoActivity.this)
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
    }


    private void initQuXiaoXingCheng() {

        HashMap<String, String> map = new HashMap<>();
        map.put("frid", shuFengCheLieBiaoEntity.getResponse().get(position1).getId()+"");
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
                                Intent intent=new Intent(ShuFengCheLieBiaoActivity.this,HuoDKuaiyunActivity.class);
                                intent.putExtra("startaddress",shuFengCheLieBiaoEntity.getResponse().get(position1).getSaddress());
                                intent.putExtra("startlongitude",shuFengCheLieBiaoEntity.getResponse().get(position1).getSlongitude());
                                intent.putExtra("startlatitude",shuFengCheLieBiaoEntity.getResponse().get(position1).getSlatitude());
                                intent.putExtra("endaddress",shuFengCheLieBiaoEntity.getResponse().get(position1).getEaddress());
                                intent.putExtra("endlongitude",shuFengCheLieBiaoEntity.getResponse().get(position1).getElongitude());
                                intent.putExtra("endlatitude",shuFengCheLieBiaoEntity.getResponse().get(position1).getElatitude());
                                intent.putExtra("total_mileage",shuFengCheLieBiaoEntity.getResponse().get(position1).getDistance());
                                intent.putExtra("sprovince",shuFengCheLieBiaoEntity.getResponse().get(position1).getSprovince());
                                intent.putExtra("scity",shuFengCheLieBiaoEntity.getResponse().get(position1).getScity());
                                intent.putExtra("scounty",shuFengCheLieBiaoEntity.getResponse().get(position1).getScounty());
                                intent.putExtra("eprovince",shuFengCheLieBiaoEntity.getResponse().get(position1).getEprovince());
                                intent.putExtra("ecity",shuFengCheLieBiaoEntity.getResponse().get(position1).getEcity());
                                intent.putExtra("ecounty",shuFengCheLieBiaoEntity.getResponse().get(position1).getEcounty());
                                intent.putExtra("selFreeRideId",shuFengCheLieBiaoEntity.getResponse().get(position1).getId()+"");
                                intent.putExtra("shunfengbiaoshi","5");
                                startActivity(intent);
                            }else if (flag.equals("300")){
                                AlertDialog.Builder builder=new AlertDialog.Builder(ShuFengCheLieBiaoActivity.this)
                                        .setTitle("提示")
                                        .setMessage(msg)
                                        .setNegativeButton("可继续发布", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Intent intent=new Intent(ShuFengCheLieBiaoActivity.this,HuoDKuaiyunActivity.class);
                                                intent.putExtra("startaddress",shuFengCheLieBiaoEntity.getResponse().get(position1).getSaddress());
                                                intent.putExtra("startlongitude",shuFengCheLieBiaoEntity.getResponse().get(position1).getSlongitude());
                                                intent.putExtra("startlatitude",shuFengCheLieBiaoEntity.getResponse().get(position1).getSlatitude());
                                                intent.putExtra("endaddress",shuFengCheLieBiaoEntity.getResponse().get(position1).getEaddress());
                                                intent.putExtra("endlongitude",shuFengCheLieBiaoEntity.getResponse().get(position1).getElongitude());
                                                intent.putExtra("endlatitude",shuFengCheLieBiaoEntity.getResponse().get(position1).getElatitude());
                                                intent.putExtra("total_mileage",shuFengCheLieBiaoEntity.getResponse().get(position1).getDistance());
                                                intent.putExtra("sprovince",shuFengCheLieBiaoEntity.getResponse().get(position1).getSprovince());
                                                intent.putExtra("scity",shuFengCheLieBiaoEntity.getResponse().get(position1).getScity());
                                                intent.putExtra("scounty",shuFengCheLieBiaoEntity.getResponse().get(position1).getScounty());
                                                intent.putExtra("eprovince",shuFengCheLieBiaoEntity.getResponse().get(position1).getEprovince());
                                                intent.putExtra("ecity",shuFengCheLieBiaoEntity.getResponse().get(position1).getEcity());
                                                intent.putExtra("ecounty",shuFengCheLieBiaoEntity.getResponse().get(position1).getEcounty());
                                                intent.putExtra("selFreeRideId",shuFengCheLieBiaoEntity.getResponse().get(position1).getId()+"");
                                                intent.putExtra("shunfengbiaoshi","5");
                                                startActivity(intent);
                                            }
                                        })
                                        .setPositiveButton("返回顺风车列表", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
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


    private void initXuanZeCiXingCheng() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderid", dingdanid);
        map.put("frid", shuFengCheLieBiaoEntity.getResponse().get(position1).getId()+"");
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
        String consigneephone=shuFengCheLieBiaoEntity.getResponse().get(position).getContactphone();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_chufadi:
                line=1;
                // 用于PopupWindow的View
                View contentView= LayoutInflater.from(this).inflate(R.layout.shaixuanqiangdanliebiao, null, false);

                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();

                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
//                window=new PopupWindow(contentView, display.getWidth(), (int) (display.getHeight()*0.85), true);
                window=new PopupWindow(contentView, display.getWidth(), act_recyclerView.getHeight(), true);
                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);
                recyclerView= (RecyclerView) contentView.findViewById(R.id.recyclerView);
                TextView tv_queren= (TextView) contentView.findViewById(R.id.tv_queren);
                tv_queren.setVisibility(View.GONE);

                initsheng();

                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                window.showAsDropDown(tv_chufadi, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//                 window.showAtLocation(parent, gravity, x, y);
                break;
            case R.id.relative_mudidi:
                mudidisheng="";
//                mudidishi="";
//                mudidixian="";
                line=2;
                // 用于PopupWindow的View
                View contentView11= LayoutInflater.from(this).inflate(R.layout.shaixuanqiangdanliebiao, null, false);

                WindowManager windowManager11 = getWindowManager();
                Display display11 = windowManager11.getDefaultDisplay();

                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
//                window=new PopupWindow(contentView11, display11.getWidth(), (int) (display11.getHeight()*0.85), true);
                window=new PopupWindow(contentView11, display11.getWidth(), act_recyclerView.getHeight(), true);
                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);
                recyclerView= (RecyclerView) contentView11.findViewById(R.id.recyclerView);
                TextView tv_queren1= (TextView) contentView11.findViewById(R.id.tv_queren);
                tv_queren1.setVisibility(View.GONE);

                initsheng();

                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                window.showAsDropDown(tv_chufadi, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//                 window.showAtLocation(parent, gravity, x, y);
                break;
        }
    }

    private void initsheng() {
        //请求省份
        PileApi.instance.loadProvince()
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

                            if (body.indexOf("false") != -1 || body.length() < 3) {
//                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                provinceEntityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                provinceEntityList.add(0,new ProvinceEntity("全国"));

                                initRecyclerView111();
                            }
                        } catch (IOException e) {
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

    private void initRecyclerView111() {
        ProvinceAdapter111 adapter111 = new ProvinceAdapter111(this, provinceEntityList);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter111);
        adapter111.setOnClickItemChild(new ProvinceAdapter111.OnClickItemChild() {
            @Override
            public void onClick(int position,String name) {
                if (line==1){
                    chufadisheng=name;
                }else if (line==2){
                    mudidisheng=name;
                }

                if (!name.equals("全国")){
                    initShi(position);
                }else {
                    if (line==1){
                        chufadishi="";
                        chufadixian="";
                        tv_chufadi.setText(chufadisheng);
                    }else if (line==2){
                        mudidishi="";
                        mudidixian="";
                        tv_mudidi.setText(mudidisheng);
                    }

                    page=1;
                    initShuJu();
                    window.dismiss();
                }



            }
        });

    }

    private void initShi(int id){
        HashMap<String, String> map = new HashMap<>();
        map.put("provinceid", id+"");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.loadCity(params)
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
                            if (body.indexOf("false") != -1 || body.length() < 2) {
                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                cityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                cityList.add(0,new ProvinceEntity("全省"));

                                ProvinceAdapter111 adapter111 = new ProvinceAdapter111(ShuFengCheLieBiaoActivity.this, cityList);
                                GridLayoutManager manager = new GridLayoutManager(ShuFengCheLieBiaoActivity.this, 4);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter111);
                                adapter111.setOnClickItemChild(new ProvinceAdapter111.OnClickItemChild() {
                                    @Override
                                    public void onClick(int position,String name) {
                                        if (line==1){
                                            chufadishi=name;
                                        }else if (line==2){
                                            mudidishi=name;
                                        }

                                        if (!name.equals("全省")){
                                            initxian(position);
                                        }else {

                                            if (line==1){
                                                chufadixian="";
                                                tv_chufadi.setText(chufadisheng);
                                            }else if (line==2){
                                                mudidixian="";
                                                tv_mudidi.setText(mudidisheng);
                                            }

                                            page=1;
                                            initShuJu();
                                            window.dismiss();
                                        }

                                    }
                                });



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


    private void initxian(int id){
        HashMap<String, String> map = new HashMap<>();
        map.put("cityid", id + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.loadCountry(params)
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
                            if (body.indexOf("false") != -1 || body.length() < 2) {
                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                countyList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                countyList.add(0,new ProvinceEntity("全市"));

                                ProvinceAdapter111 adapter111 = new ProvinceAdapter111(ShuFengCheLieBiaoActivity.this, countyList);
                                GridLayoutManager manager = new GridLayoutManager(ShuFengCheLieBiaoActivity.this, 4);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter111);
                                adapter111.setOnClickItemChild(new ProvinceAdapter111.OnClickItemChild() {
                                    @Override
                                    public void onClick(int position,String name) {
                                        if (line==1){
                                            chufadixian=name;
                                        }else if (line==2){
                                            mudidixian=name;
                                        }

                                        if (!name.equals("全市")){
                                            if (line==1){
                                                tv_chufadi.setText(chufadixian);
                                            }else if (line==2){
                                                tv_mudidi.setText(mudidixian);
                                            }
                                        }else {
                                            if (line==1){
                                                tv_chufadi.setText(chufadishi);
                                            }else if (line==2){
                                                tv_mudidi.setText(mudidishi);
                                            }
                                        }
                                        page=1;
                                        initShuJu();

                                        window.dismiss();
                                    }
                                });

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
}
