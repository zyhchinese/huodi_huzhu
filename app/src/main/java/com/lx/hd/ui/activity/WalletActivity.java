package com.lx.hd.ui.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.adapter.WalletAdapter;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.walletBean;

import java.util.ArrayList;
import java.util.List;

/***
 * 我的钱包activity
 * @author tb
 * 2017/08/25
 *
 */
public class WalletActivity extends BaseActivity {
    ListView ls;
    WalletAdapter wa;
    private ScaleAnimation scaleAnim1;
    private ScaleAnimation scaleAnim2;
    private ImageView imageView;
    private LinearLayout menu1;
    private TextView balance;
    private TextView coupon;
    boolean flag = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        ls = (ListView) findViewById(R.id.ls);
        imageView = (ImageView) findViewById(R.id.imageView);
        balance = (TextView) findViewById(R.id.balance);
        coupon = (TextView) findViewById(R.id.coupon);
        menu1 = (LinearLayout) findViewById(R.id.menu1);
        List<walletBean> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            walletBean w = new walletBean(1, "500.00", "2017/8/28", "afafdaf1" + i, "dafd21213123123" + i, "支付宝");
//            list.add(w);
//        }
        wa = new WalletAdapter(WalletActivity.this, list);
        ls.setAdapter(wa);
        scaleAnim1 = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.scale_anim);
        scaleAnim2 = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.scale_anims);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    menu1.startAnimation(scaleAnim1);
                    flag = false;
                } else {
                    menu1.startAnimation(scaleAnim2);
                    flag = true;
                }
            }
        });
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletActivity.this,
                        MywalletActivity.class);
                startActivity(intent);
            }
        });
        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletActivity.this,
                        CouponActivity.class);
                startActivity(intent);
            }
        });
    }


}
