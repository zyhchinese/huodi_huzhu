package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;

public class WoDeErShouCheActivity extends BackCommonActivity implements View.OnClickListener {

    private Banner banner;
    private RelativeLayout wode_ershouche,fabu_ershouche;
    private ArrayList<String> bannerList;

    @Override
    protected int getContentView() {
        return R.layout.activity_wo_de_er_shou_che;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("我的");
        bannerList = getIntent().getStringArrayListExtra("bannerList");

        banner= (Banner) findViewById(R.id.banner);
        wode_ershouche= (RelativeLayout) findViewById(R.id.wode_ershouche);
        fabu_ershouche= (RelativeLayout) findViewById(R.id.fabu_ershouche);

        wode_ershouche.setOnClickListener(this);
        fabu_ershouche.setOnClickListener(this);
        initBanner();
    }

    private void initBanner() {
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
            case R.id.wode_ershouche:
                Intent intent1=new Intent(this,WoDeErShouCheLieBiaoActivity.class);
                startActivity(intent1);
                break;
            case R.id.fabu_ershouche:
                Intent intent=new Intent(this,FaBuErShouCheActivity.class);
                startActivity(intent);
                break;
        }
    }
}
