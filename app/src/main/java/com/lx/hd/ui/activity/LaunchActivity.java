package com.lx.hd.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.lx.hd.MainActivity;
import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.base.activity.BaseActivity;

public class LaunchActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(r, 0);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {

            startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            finish();
        }
    };
}
