package com.lx.hd.ui.activity;
/**
 * 安全中心
 * tb
 * 2017/09/02
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lx.hd.R;
import com.lx.hd.account.ModifyPwdActivity;
import com.lx.hd.base.activity.BackCommonActivity;

public class SecurityActivity extends BackCommonActivity implements View.OnClickListener {
    private RelativeLayout xgmm, bdyx, bd, szmb, szzfmm;
    private ImageView img_anquanzhongxin_banner;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("安全中心");
        bdyx = (RelativeLayout) findViewById(R.id.bdyx);
        bd = (RelativeLayout) findViewById(R.id.bd);
        szmb = (RelativeLayout) findViewById(R.id.szmb);
        xgmm = (RelativeLayout) findViewById(R.id.xgmm);
        szzfmm = (RelativeLayout) findViewById(R.id.szzfmm);
        img_anquanzhongxin_banner= (ImageView) findViewById(R.id.img_anquanzhongxin_banner);
        xgmm.setOnClickListener(this);
        bdyx.setOnClickListener(this);
        bd.setOnClickListener(this);
        szmb.setOnClickListener(this);
        szzfmm.setOnClickListener(this);
        initGaoDu();
    }

    private void initGaoDu() {
        WindowManager windowManager=getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        double i = display.getWidth() / 720.0;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img_anquanzhongxin_banner.getLayoutParams();
        layoutParams.height= (int) (360.0*i);
        img_anquanzhongxin_banner.setLayoutParams(layoutParams);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_security_gaiban;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.xgmm:
                startActivity(new Intent(SecurityActivity.this, ModifyPwdActivity.class));
                break;
            case R.id.bdyx:
                startActivity(new Intent(SecurityActivity.this, BindEmailActivity.class));
                break;
            case R.id.bd:
                startActivity(new Intent(SecurityActivity.this, BindQWActivity.class));
                break;
            case R.id.szmb:
                startActivity(new Intent(SecurityActivity.this, SetEncryptedActivity.class));
                break;
            case R.id.szzfmm:
                startActivity(new Intent(SecurityActivity.this, SetPayPwdActivity.class));
                break;


        }
    }
}
