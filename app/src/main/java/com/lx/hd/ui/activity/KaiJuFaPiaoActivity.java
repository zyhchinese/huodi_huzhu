package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackCommonActivity;

public class KaiJuFaPiaoActivity extends BackCommonActivity implements View.OnClickListener {

    private CardView pinion1,pinion2,pinion3,pinion4;

    @Override
    protected int getContentView() {
        return R.layout.activity_kai_ju_fa_piao;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("开具发票");
        pinion1= (CardView) findViewById(R.id.pinion1);
        pinion2= (CardView) findViewById(R.id.pinion2);
        pinion3= (CardView) findViewById(R.id.pinion3);
        pinion4= (CardView) findViewById(R.id.pinion4);

        pinion1.setOnClickListener(this);
        pinion2.setOnClickListener(this);
        pinion3.setOnClickListener(this);
        pinion4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pinion1:
                Intent intent=new Intent(this,KaiPiaoActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
                break;
            case R.id.pinion2:
                Intent intent1=new Intent(this,KaiPiaoActivity.class);
                intent1.putExtra("type","1");
                startActivity(intent1);
                break;
            case R.id.pinion3:
                Intent intent2=new Intent(this,KaiPiaoActivity.class);
                intent2.putExtra("type","2");
                startActivity(intent2);
                break;
            case R.id.pinion4:
                Intent intent3=new Intent(this,KaiPiaoLiShiActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
