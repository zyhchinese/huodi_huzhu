package com.lx.hd.ui.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BaseActivity;

public class CarLeaseActivity extends BackActivity {
    private LinearLayout linear;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_lease;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
//        setTitleIcon(R.mipmap.icon_car_home_samall);
//        setTitleText("车辆租赁");
        linear= (LinearLayout) findViewById(R.id.linear);
        linear.setVisibility(View.GONE);
    }
}
