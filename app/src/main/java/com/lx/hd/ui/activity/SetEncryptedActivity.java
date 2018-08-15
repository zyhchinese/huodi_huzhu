package com.lx.hd.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackCommonActivity;

public class SetEncryptedActivity extends BackCommonActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_set_encrypted;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("设置密保");
    }
}
