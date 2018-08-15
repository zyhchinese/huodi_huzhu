package com.lx.hd.ui.activity;

import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackActivity;

public class SysTextActivity extends BackActivity {
    private TextView text;

    @Override
    protected int getContentView() {
        return R.layout.activity_sys_text;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("扫码结果");
        text = (TextView) findViewById(R.id.text);
        text.setText(getIntent().getStringExtra("text"));
    }

}
