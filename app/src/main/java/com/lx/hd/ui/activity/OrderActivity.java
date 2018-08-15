package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackActivity;

public class OrderActivity extends BackActivity implements View.OnClickListener {
    private ImageView img_icon;
    private CardView cardView0,cardView1,cardView2,cardView3;


    @Override
    protected int getContentView() {
        return R.layout.activity_order;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("我的订单");
        img_icon= (ImageView) findViewById(R.id.img_icon);
        img_icon.setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        cardView0= (CardView) findViewById(R.id.cardView0);
        cardView1= (CardView) findViewById(R.id.cardView1);
        cardView2= (CardView) findViewById(R.id.cardView2);
        cardView3= (CardView) findViewById(R.id.cardView3);
        cardView0.setOnClickListener(this);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardView0:
                startActivity(new Intent(this, ChargeOrderActivity.class));
                break;
            case R.id.cardView1:
//                startActivity(new Intent(this,CarLeaseActivity.class));
                startActivity(new Intent(this,LogisticsOrderActivity.class));
                break;
            case R.id.cardView2:
                startActivity(new Intent(this, myOrderActivity.class));
                break;
            case R.id.cardView3:
                startActivity(new Intent(this, AuditStatusActivity.class));
                break;
        }
    }
}
