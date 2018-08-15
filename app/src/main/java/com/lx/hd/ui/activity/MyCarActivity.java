package com.lx.hd.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackActivity;

public class MyCarActivity extends BackActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_my_car;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleIcon(R.mipmap.icon_title_car);
        setTitleText("我的车辆");
    }
}
