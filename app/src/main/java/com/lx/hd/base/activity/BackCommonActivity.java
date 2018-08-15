package com.lx.hd.base.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lx.hd.R;

public abstract class BackCommonActivity extends BaseActivity {
    protected LinearLayout mTitleBar;
    protected ImageView imgBack;
    protected TextView tvTitle;

    @Override
    protected void initWindow() {
        super.initWindow();
        mTitleBar = (LinearLayout) findViewById(R.id.lly_title_root);
        imgBack = (ImageView) findViewById(R.id.img_back1);
        tvTitle = (TextView) findViewById(R.id.tv_title1);
        if (mTitleBar != null) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void setTitleText(String titleText) {
        if (tvTitle != null) {
            tvTitle.setText(titleText);
        } else {
            throw new IllegalStateException("如果有标题，请在布局中通过include标签添加后再操作");
        }
    }
}
