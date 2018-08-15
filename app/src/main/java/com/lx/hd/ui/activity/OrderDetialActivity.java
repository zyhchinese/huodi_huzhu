package com.lx.hd.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.ChargeOrder;

public class OrderDetialActivity extends BackCommonActivity {

    private TextView tvDianLiang;
    private ChargeOrder chargeOrder;
    public static final String KEY_BUNDLE = "KEY_BUNDLE_IN_ORDERD_ETIAL";
    TextView tvCurrentTime,tvOrderNo,tvEquipmentNo,tvChargeTime;
    private TextView tvDu,tvTime;
    public static void show(Context context, ChargeOrder orderBean) {
        if (orderBean == null) return;
        Intent intent = new Intent(context, OrderDetialActivity.class);
        intent.putExtra(KEY_BUNDLE, orderBean);
        context.startActivity(intent);
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        chargeOrder = (ChargeOrder) bundle.getSerializable(KEY_BUNDLE);
        if (chargeOrder == null || (chargeOrder.getId() <= 0)) {
            showToast("该账单不存在");
            return false;
        }
        return super.initBundle(bundle);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detial;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("账单详情");
        tvDianLiang=(TextView)findViewById(R.id.tv_dianliang);
        tvOrderNo=(TextView)findViewById(R.id.tv_orderno);
        tvEquipmentNo=(TextView)findViewById(R.id.tv_equipmentno);
        tvChargeTime=(TextView)findViewById(R.id.tv_charge_time);
        tvDu=(TextView)findViewById(R.id.tv_du);
        tvTime=(TextView)findViewById(R.id.tv_time);
    }

    @Override
    protected void initData() {
        super.initData();
        if(chargeOrder!=null){
            tvDianLiang.setText(chargeOrder.getCount()+"°");
            tvDu.setText("充电"+chargeOrder.getCount()+"度");
            tvTime.setText(chargeOrder.getCreatetime());
            tvOrderNo.setText(chargeOrder.getOrderno());
            tvEquipmentNo.setText(chargeOrder.getElectricsbm());
            tvChargeTime.setText(chargeOrder.getTotalmoney()+"分钟");
        }

    }
}
