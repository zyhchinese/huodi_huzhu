package com.lx.hd.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackCommonActivity;

public class NewFeatureIntroduceActivity extends BackCommonActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_new_feature_introduce;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("新功能介绍");
    }
}
