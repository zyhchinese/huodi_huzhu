package com.lx.hd.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.DeliverGoodsCarDataBean;

/*
价格明细
 */
public class DeliverPriceDetailActivity extends BaseActivity {
    private ImageView img_back, image;
    private TextView sfbz, jg, zlc, qbj, tv_title;
    private String ctry;
    private DeliverGoodsCarDataBean data;


    @Override
    protected int getContentView() {
        return R.layout.activity_deliver_price_detail;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
//        setTitleIcon(R.mipmap.icon_car_home_samall);
//        setTitleText("车辆租赁");
        img_back = (ImageView) findViewById(R.id.img_back);
        image = (ImageView) findViewById(R.id.image);
        jg = (TextView) findViewById(R.id.jg);
        zlc = (TextView) findViewById(R.id.zlc);
        qbj = (TextView) findViewById(R.id.qbj);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("价格明细");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sfbz = (TextView) findViewById(R.id.sfbz);
        String jsondata = getIntent().getStringExtra("data");
        ctry = getIntent().getStringExtra("cs");
        //showToast(ctry);
        data = new Gson().fromJson(jsondata, DeliverGoodsCarDataBean.class);
        jg.setText(getIntent().getStringExtra("zjg"));
        zlc.setText("总里程" + getIntent().getStringExtra("zlc") + "公里");
        qbj.setText(data.getCar_type() + "起步价：" + data.getStarting_price());
        Glide.with(DeliverPriceDetailActivity.this).load(Constant.BASE_URL + data.getFolder() + data.getAutoname()).into(image);
        sfbz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent(DeliverPriceDetailActivity.this, ChargeStandardActivity.class);
                it1.putExtra("crty", ctry);
                startActivity(it1);
            }
        });
    }
}
