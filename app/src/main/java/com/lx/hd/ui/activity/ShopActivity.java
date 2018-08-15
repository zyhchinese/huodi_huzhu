package com.lx.hd.ui.activity;


import android.view.View;
import android.widget.ImageView;

import com.lx.hd.R;
import com.lx.hd.base.activity.BaseActivity;

/*
* 商城
* */
public class ShopActivity extends BaseActivity implements View.OnClickListener {
    private ImageView go_back;
    @Override
    protected int getContentView() {
        return R.layout.activity_shop;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        go_back= (ImageView) findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        finish();
    }
}
