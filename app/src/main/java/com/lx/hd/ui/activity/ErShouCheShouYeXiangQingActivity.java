package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.ErShouCheShouYeEntity;
import com.lx.hd.bean.ErShouCheShouYeXiangQingEntity;
import com.lx.hd.utils.GlideImageLoader;
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

public class ErShouCheShouYeXiangQingActivity extends BackCommonActivity implements View.OnClickListener {

    private Banner banner;
    private TextView tv_title,tv_chexing,tv_pinpai,tv_chexinghao,tv_price,tv_kancheaddress,
            tv_zhucenianfen,tv_gonglishu,tv_lianxiren,tv_lianxidianhua,tv_diqu,tv_miaoshu,tv_delete;
    private String id,line;
    private ErShouCheShouYeXiangQingEntity erShouCheShouYeXiangQingEntity;

    @Override
    protected int getContentView() {
        return R.layout.activity_er_shou_che_shou_ye_xiang_qing;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("二手车详情");
        id = getIntent().getStringExtra("id");
        line = getIntent().getStringExtra("line");

        initView();


        initShuJu();
    }

    private void initView() {
        banner= (Banner) findViewById(R.id.banner);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_chexing= (TextView) findViewById(R.id.tv_chexing);
        tv_pinpai= (TextView) findViewById(R.id.tv_pinpai);
        tv_chexinghao= (TextView) findViewById(R.id.tv_chexinghao);
        tv_price= (TextView) findViewById(R.id.tv_price);
        tv_kancheaddress= (TextView) findViewById(R.id.tv_kancheaddress);
        tv_zhucenianfen= (TextView) findViewById(R.id.tv_zhucenianfen);
        tv_gonglishu= (TextView) findViewById(R.id.tv_gonglishu);
        tv_lianxiren= (TextView) findViewById(R.id.tv_lianxiren);
        tv_lianxidianhua= (TextView) findViewById(R.id.tv_lianxidianhua);
        tv_diqu= (TextView) findViewById(R.id.tv_diqu);
        tv_miaoshu= (TextView) findViewById(R.id.tv_miaoshu);
        tv_delete= (TextView) findViewById(R.id.tv_delete);

        if (line.equals("1")){
            tv_delete.setVisibility(View.VISIBLE);
        }else if (line.equals("2")){
            tv_delete.setVisibility(View.GONE);
        }
        tv_delete.setOnClickListener(this);
    }

    private void initShuJu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("selectid", id);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchSecondhandcarDet(params)
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
                                erShouCheShouYeXiangQingEntity = gson.fromJson(body, ErShouCheShouYeXiangQingEntity.class);



                                initBanner();

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
        tv_title.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getTitle());
        tv_chexing.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getCar_type_name());
        tv_pinpai.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getCar_brand_name());
        tv_chexinghao.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getModel_number());
        tv_price.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getPrice());
        tv_kancheaddress.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getWatch_car_add());
        tv_zhucenianfen.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getRegistered_year());
        tv_gonglishu.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getKilometre());
        tv_lianxiren.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getContacts());
        tv_lianxidianhua.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getContact_number());
        tv_diqu.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getRegion());
        tv_miaoshu.setText(erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarinfo().getDescribe());
    }

    private void initBanner() {
        List<String> bannerList=new ArrayList<>();
        for (int i = 0; i < erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarpics().size(); i++) {
            bannerList.add(Constant.BASE_URL+erShouCheShouYeXiangQingEntity.getResponse().get(0).getTwoScarpics().get(i));
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_delete:
                AlertDialog.Builder builder=new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定删除当前车辆？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                initShanChu();
                                dialog.dismiss();
                            }
                        });
                builder.show();

                break;

        }
    }

    private void initShanChu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("selectid", id);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.deleteSelfSecondhandcar(data)
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
                                showToast("删除成功");
                                finish();
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
}
