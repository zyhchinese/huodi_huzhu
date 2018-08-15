package com.lx.hd.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.ErShouCheShouYeLieBiaoAdapter;
import com.lx.hd.adapter.ProvinceAdapter111;
import com.lx.hd.adapter.ProvinceAdapter222;
import com.lx.hd.adapter.ProvinceAdapter333;
import com.lx.hd.adapter.ProvinceAdapter444;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.ErShouCheShouYeEntity;
import com.lx.hd.bean.ErShouCheShouYeLieBiaoEntity;
import com.lx.hd.bean.KuaiYun_XiangQing_JiBen;
import com.lx.hd.bean.ProvinceEntity;
import com.lx.hd.utils.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONArray;
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

public class ErShouCheShouYeActivity extends BackCommonActivity implements OnRefreshListener, OnLoadmoreListener, View.OnClickListener {
    private TextView tv_title1;
    private SmartRefreshLayout smart;
    private TextView tv_mycar;
    private TextView tv_diqu,tv_pinpai,tv_chexing;
    private Banner banner;
    private RecyclerView act_recyclerView;
    private ErShouCheShouYeEntity erShouCheShouYeEntity;
    private ErShouCheShouYeLieBiaoEntity erShouCheShouYeLieBiaoEntity;
    private ErShouCheShouYeLieBiaoEntity erShouCheShouYeLieBiaoEntity1;
    private PopupWindow window;
    private RecyclerView recyclerView;
    private List<ProvinceEntity> provinceEntityList;
    private List<ProvinceEntity> cityList;
    private List<ProvinceEntity> countyList;
    private String chufadisheng="",chufadishi="",chufadixian="";
    private int page=1;
    private ErShouCheShouYeLieBiaoAdapter adapter;
    private ArrayList<String> bannerList;

    @Override
    protected int getContentView() {
        return R.layout.activity_er_shou_che_shou_ye;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("二手车市场");

        tv_title1 = (TextView) findViewById(R.id.tv_title1);
        tv_title1.setTextColor(Color.parseColor("#de1515"));

        smart = (SmartRefreshLayout) findViewById(R.id.smart);
        tv_mycar = (TextView) findViewById(R.id.tv_mycar);
        tv_diqu = (TextView) findViewById(R.id.tv_diqu);
        tv_pinpai = (TextView) findViewById(R.id.tv_pinpai);
        tv_chexing = (TextView) findViewById(R.id.tv_chexing);
        banner = (Banner) findViewById(R.id.banner);
        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);


        smart.setOnRefreshListener(this);
        smart.setOnLoadmoreListener(this);
        tv_mycar.setOnClickListener(this);
        tv_diqu.setOnClickListener(this);
        tv_pinpai.setOnClickListener(this);
        tv_chexing.setOnClickListener(this);

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context);//指定为经典Footer，默认是 BallPulseFooter
            }
        });
        smart.setDisableContentWhenRefresh(true);
        smart.setDisableContentWhenLoading(true);


        initShuJu();

        tv_diqu.setText(Constant.HUOYUAN_SHENG);
        initLieBiaoShuJu(Constant.HUOYUAN_SHENG,"","");

    }

    private void initLieBiaoShuJu(String region,String brand_name,String type_name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("region", region);
        map.put("brand_name", brand_name);
        map.put("type_name", type_name);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchSecondhandcarListByPage(1,8,params)
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
                                Gson gson=new Gson();
                                erShouCheShouYeLieBiaoEntity = gson.fromJson(body, ErShouCheShouYeLieBiaoEntity.class);

                                initRecyclerView();
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

    private void initLieBiaoShuJu1(String region,String brand_name,String type_name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("region", region);
        map.put("brand_name", brand_name);
        map.put("type_name", type_name);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchSecondhandcarListByPage(page,8,params)
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
                                Gson gson=new Gson();
                                erShouCheShouYeLieBiaoEntity1 = gson.fromJson(body, ErShouCheShouYeLieBiaoEntity.class);

                                if (erShouCheShouYeLieBiaoEntity1.getResponse().getRows().size()==0){
                                    showToast("没有更多数据了");
                                }else {
                                    erShouCheShouYeLieBiaoEntity.getResponse().getRows().addAll(erShouCheShouYeLieBiaoEntity1.getResponse().getRows());
                                    adapter.notifyDataSetChanged();
                                }



                                if (smart.isLoading()) {
                                    smart.finishLoadmore();
                                }


//                                initRecyclerView();
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

    private void initRecyclerView() {
        adapter=new ErShouCheShouYeLieBiaoAdapter(this,erShouCheShouYeLieBiaoEntity.getResponse());
        GridLayoutManager manager=new GridLayoutManager(this,2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new ErShouCheShouYeLieBiaoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(ErShouCheShouYeActivity.this,ErShouCheShouYeXiangQingActivity.class);
                intent.putExtra("id",erShouCheShouYeLieBiaoEntity.getResponse().getRows().get(position).getId()+"");
                intent.putExtra("line","2");
                startActivity(intent);
            }
        });
    }

    private void initShuJu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("region", Constant.HUOYUAN_SHENG);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectSecondhandcarIndex(params)
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
                                Gson gson = new Gson();
                                erShouCheShouYeEntity = gson.fromJson(body, ErShouCheShouYeEntity.class);

                                initBanner();
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

    private void initBanner() {
        bannerList=new ArrayList<>();
        for (ErShouCheShouYeEntity.ResponseBean.ToppicBean entity:erShouCheShouYeEntity.getResponse().get(0).getToppic()){
            bannerList.add(Constant.BASE_URL+entity.getFolder()+entity.getAutoname());
        }

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(bannerList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page=1;
        if (tv_diqu.getText().toString().equals("全国")||tv_diqu.getText().toString().equals("地区")){
            if (tv_pinpai.getText().toString().equals("品牌")){
                if (tv_chexing.getText().toString().equals("车型")){
                    initLieBiaoShuJu("","","");
                }else {
                    initLieBiaoShuJu("","",tv_chexing.getText().toString());
                }
            }else {
                if (tv_chexing.getText().toString().equals("车型")){
                    initLieBiaoShuJu("",tv_pinpai.getText().toString(),"");
                }else {
                    initLieBiaoShuJu("",tv_pinpai.getText().toString(),tv_chexing.getText().toString());
                }
            }
        }else {
            if (tv_pinpai.getText().toString().equals("品牌")){
                if (tv_chexing.getText().toString().equals("车型")){
                    initLieBiaoShuJu(tv_diqu.getText().toString(),"","");
                }else {
                    initLieBiaoShuJu(tv_diqu.getText().toString(),"",tv_chexing.getText().toString());
                }
            }else {
                if (tv_chexing.getText().toString().equals("车型")){
                    initLieBiaoShuJu(tv_diqu.getText().toString(),tv_pinpai.getText().toString(),"");
                }else {
                    initLieBiaoShuJu(tv_diqu.getText().toString(),tv_pinpai.getText().toString(),tv_chexing.getText().toString());
                }
            }
        }

        if (smart.isRefreshing()) {
            smart.finishRefresh();
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        if (tv_diqu.getText().toString().equals("全国")||tv_diqu.getText().toString().equals("地区")){
            if (tv_pinpai.getText().toString().equals("品牌")){
                if (tv_chexing.getText().toString().equals("车型")){
                    initLieBiaoShuJu1("","","");
                }else {
                    initLieBiaoShuJu1("","",tv_chexing.getText().toString());
                }
            }else {
                if (tv_chexing.getText().toString().equals("车型")){
                    initLieBiaoShuJu1("",tv_pinpai.getText().toString(),"");
                }else {
                    initLieBiaoShuJu1("",tv_pinpai.getText().toString(),tv_chexing.getText().toString());
                }
            }
        }else {
            if (tv_pinpai.getText().toString().equals("品牌")){
                if (tv_chexing.getText().toString().equals("车型")){
                    initLieBiaoShuJu1(tv_diqu.getText().toString(),"","");
                }else {
                    initLieBiaoShuJu1(tv_diqu.getText().toString(),"",tv_chexing.getText().toString());
                }
            }else {
                if (tv_chexing.getText().toString().equals("车型")){
                    initLieBiaoShuJu1(tv_diqu.getText().toString(),tv_pinpai.getText().toString(),"");
                }else {
                    initLieBiaoShuJu1(tv_diqu.getText().toString(),tv_pinpai.getText().toString(),tv_chexing.getText().toString());
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_mycar:
                Intent intent=new Intent(this,WoDeErShouCheActivity.class);
                intent.putStringArrayListExtra("bannerList",bannerList);
                startActivity(intent);
                break;
            case R.id.tv_diqu:
                // 用于PopupWindow的View
                View contentView= LayoutInflater.from(this).inflate(R.layout.shaixuanqiangdanliebiao, null, false);

                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();

                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                window=new PopupWindow(contentView, display.getWidth(), (int) (display.getHeight()*0.8), true);
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
                window.showAsDropDown(tv_diqu, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//                 window.showAtLocation(parent, gravity, x, y);
                break;
            case R.id.tv_pinpai:
                // 用于PopupWindow的View
                View contentView111= LayoutInflater.from(this).inflate(R.layout.shaixuanqiangdanliebiao, null, false);

                WindowManager windowManager111 = getWindowManager();
                Display display111 = windowManager111.getDefaultDisplay();

                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                window=new PopupWindow(contentView111, display111.getWidth(), (int) (display111.getHeight()*0.8), true);
                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);
                recyclerView= (RecyclerView) contentView111.findViewById(R.id.recyclerView);
                TextView tv_queren111= (TextView) contentView111.findViewById(R.id.tv_queren);
                tv_queren111.setVisibility(View.GONE);

                initpinpai();

                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                window.showAsDropDown(tv_pinpai, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//                 window.showAtLocation(parent, gravity, x, y);
                break;
            case R.id.tv_chexing:
                // 用于PopupWindow的View
                View contentView222= LayoutInflater.from(this).inflate(R.layout.shaixuanqiangdanliebiao, null, false);

                WindowManager windowManager222 = getWindowManager();
                Display display222 = windowManager222.getDefaultDisplay();

                // 创建PopupWindow对象，其中：
                // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
                // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
                window=new PopupWindow(contentView222, display222.getWidth(), (int) (display222.getHeight()*0.8), true);
                // 设置PopupWindow的背景
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
                // 设置PopupWindow是否能响应外部点击事件
                window.setOutsideTouchable(true);
                // 设置PopupWindow是否能响应点击事件
                window.setTouchable(true);
                recyclerView= (RecyclerView) contentView222.findViewById(R.id.recyclerView);
                TextView tv_queren222= (TextView) contentView222.findViewById(R.id.tv_queren);
                tv_queren222.setVisibility(View.GONE);

                initchexing();

                // 显示PopupWindow，其中：
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
                window.showAsDropDown(tv_chexing, 0, 0);
                // 或者也可以调用此方法显示PopupWindow，其中：
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//                 window.showAtLocation(parent, gravity, x, y);
                break;
        }
    }

    private void initchexing() {
        ProvinceAdapter444 adapter111 = new ProvinceAdapter444(this, erShouCheShouYeEntity.getResponse().get(0).getCartypeList());
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter111);
        adapter111.setOnClickItemChild(new ProvinceAdapter444.OnClickItemChild() {
            @Override
            public void onClick(int position,String name) {
                tv_chexing.setText(name);
                page=1;
                if (tv_diqu.getText().toString().equals("全国")||tv_diqu.getText().toString().equals("地区")){
                    if (tv_pinpai.getText().toString().equals("品牌")){
                        initLieBiaoShuJu("","",name);
                    }else {
                        initLieBiaoShuJu("",tv_pinpai.getText().toString(),name);
                    }

                }else {
                    if (tv_pinpai.getText().toString().equals("品牌")){
                        initLieBiaoShuJu(tv_diqu.getText().toString(),"",name);
                    }else {
                        initLieBiaoShuJu(tv_diqu.getText().toString(),tv_pinpai.getText().toString(),name);
                    }

                }

                window.dismiss();



            }
        });
    }

    private void initpinpai() {
        ProvinceAdapter333 adapter111 = new ProvinceAdapter333(this, erShouCheShouYeEntity.getResponse().get(0).getCarbrandList());
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter111);
        adapter111.setOnClickItemChild(new ProvinceAdapter333.OnClickItemChild() {
            @Override
            public void onClick(int position,String name) {
                tv_pinpai.setText(name);
                page=1;
                if (tv_diqu.getText().toString().equals("全国")||tv_diqu.getText().toString().equals("地区")){
                    if (tv_chexing.getText().toString().equals("车型")){
                        initLieBiaoShuJu("",name,"");
                    }else {
                        initLieBiaoShuJu("",name,tv_chexing.getText().toString());
                    }

                }else {
                    if (tv_chexing.getText().toString().equals("车型")){
                        initLieBiaoShuJu(tv_diqu.getText().toString(),name,"");
                    }else {
                        initLieBiaoShuJu(tv_diqu.getText().toString(),name,tv_chexing.getText().toString());
                    }

                }

                window.dismiss();



            }
        });
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
        ProvinceAdapter222 adapter111 = new ProvinceAdapter222(this, provinceEntityList);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter111);
        adapter111.setOnClickItemChild(new ProvinceAdapter222.OnClickItemChild() {
            @Override
            public void onClick(int position,String name) {
                chufadisheng=name;


                if (!name.equals("全国")){
                    initShi(position);
                }else {

                    chufadishi="";
                    chufadixian="";
                    tv_diqu.setText(chufadisheng);

                    page=1;

                    if (tv_pinpai.getText().toString().equals("品牌")){
                        if (tv_chexing.getText().toString().equals("车型")){
                            initLieBiaoShuJu("","","");
                        }else {
                            initLieBiaoShuJu("","",tv_chexing.getText().toString());
                        }
                    }else {
                        if (tv_chexing.getText().toString().equals("车型")){
                            initLieBiaoShuJu("",tv_pinpai.getText().toString(),"");
                        }else {
                            initLieBiaoShuJu("",tv_pinpai.getText().toString(),tv_chexing.getText().toString());
                        }
                    }

                    window.dismiss();
                }


            }
        });
    }

    private void initShi(int id) {
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
//                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                cityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                cityList.add(0,new ProvinceEntity("全省"));

                                ProvinceAdapter222 adapter111 = new ProvinceAdapter222(ErShouCheShouYeActivity.this, cityList);
                                GridLayoutManager manager = new GridLayoutManager(ErShouCheShouYeActivity.this, 4);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter111);
                                adapter111.setOnClickItemChild(new ProvinceAdapter222.OnClickItemChild() {
                                    @Override
                                    public void onClick(int position,String name) {
                                        chufadishi=name;


                                        if (!name.equals("全省")){
                                            initxian(position);
                                        }else {

                                            chufadixian="";
                                            tv_diqu.setText(chufadisheng);


                                            page=1;

                                            if (tv_pinpai.getText().toString().equals("品牌")){
                                                if (tv_chexing.getText().toString().equals("车型")){
                                                    initLieBiaoShuJu(chufadisheng,"","");
                                                }else {
                                                    initLieBiaoShuJu(chufadisheng,"",tv_chexing.getText().toString());
                                                }
                                            }else {
                                                if (tv_chexing.getText().toString().equals("车型")){
                                                    initLieBiaoShuJu(chufadisheng,tv_pinpai.getText().toString(),"");
                                                }else {
                                                    initLieBiaoShuJu(chufadisheng,tv_pinpai.getText().toString(),tv_chexing.getText().toString());
                                                }
                                            }

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

    private void initxian(int id) {
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
//                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                countyList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                countyList.add(0,new ProvinceEntity("全市"));

                                ProvinceAdapter111 adapter111 = new ProvinceAdapter111(ErShouCheShouYeActivity.this, countyList);
                                GridLayoutManager manager = new GridLayoutManager(ErShouCheShouYeActivity.this, 4);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter111);
                                adapter111.setOnClickItemChild(new ProvinceAdapter111.OnClickItemChild() {
                                    @Override
                                    public void onClick(int position,String name) {
                                        chufadixian=name;

                                        if (!name.equals("全市")){
                                            tv_diqu.setText(chufadixian);
                                        }else {
                                            tv_diqu.setText(chufadishi);
                                        }
                                        page=1;

                                        if (!name.equals("全市")){
                                            if (tv_pinpai.getText().toString().equals("品牌")){
                                                if (tv_chexing.getText().toString().equals("车型")){
                                                    initLieBiaoShuJu(chufadixian,"","");
                                                }else {
                                                    initLieBiaoShuJu(chufadixian,"",tv_chexing.getText().toString());
                                                }
                                            }else {
                                                if (tv_chexing.getText().toString().equals("车型")){
                                                    initLieBiaoShuJu(chufadixian,tv_pinpai.getText().toString(),"");
                                                }else {
                                                    initLieBiaoShuJu(chufadixian,tv_pinpai.getText().toString(),tv_chexing.getText().toString());
                                                }
                                            }

                                        }else {
                                            if (tv_pinpai.getText().toString().equals("品牌")){
                                                if (tv_chexing.getText().toString().equals("车型")){
                                                    initLieBiaoShuJu(chufadishi,"","");
                                                }else {
                                                    initLieBiaoShuJu(chufadishi,"",tv_chexing.getText().toString());
                                                }
                                            }else {
                                                if (tv_chexing.getText().toString().equals("车型")){
                                                    initLieBiaoShuJu(chufadishi,tv_pinpai.getText().toString(),"");
                                                }else {
                                                    initLieBiaoShuJu(chufadishi,tv_pinpai.getText().toString(),tv_chexing.getText().toString());
                                                }
                                            }
                                        }


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
