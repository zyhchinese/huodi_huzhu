package com.lx.hd.widgets.toprecyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.ShopListBean;
import com.lx.hd.ui.activity.BannerDetailsActivity;
import com.lx.hd.ui.activity.GoodsDetialActivity;
import com.lx.hd.ui.activity.ShoppingActivity;
import com.lx.hd.utils.TDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单布局-in
 * 作者：fly on 2016/8/24 0024 23:45
 * 邮箱：cugb_feiyang@163.com
 */
public class ItemOrderIn implements OrderContent {

    private List<ShopListBean> list;
    private ShopListBean orderGoods;
    private ShopListBean orderGoods2;
    RequestOptions mOptions;

    public ItemOrderIn(ShopListBean orderGoods, ShopListBean orderGoods2) {
        this.orderGoods = orderGoods;
        list = new ArrayList<>();
        list.add(orderGoods);
        if (orderGoods2 != null) {
            this.orderGoods2 = orderGoods2;
            list.add(orderGoods2);
        }
        float w = TDevice.getScreenWidth();
        float h = TDevice.getScreenHeight();
        float fW = 153 / 720 * w;
        float fH = 153 / 1280 * h;
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.icon_default)
                .fitCenter()
                .override((int) fW, (int) fH);
    }

    @Override
    public int getLayout() {
        return R.layout.item_car_part;
    }

    @Override
    public boolean isClickAble() {
        return true;
    }

    @Override
    public View getView(final Context mContext, View convertView, LayoutInflater inflater) {
        inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(getLayout(), null);
        //TODO 数据展示-订单内容
        TextView nameTv = (TextView) convertView.findViewById(R.id.item_tx);
        ImageView snTv = (ImageView) convertView.findViewById(R.id.shopimg);
        TextView dw1 = (TextView) convertView.findViewById(R.id.dw1);
        TextView jg1 = (TextView) convertView.findViewById(R.id.jg1);
        TextView dw2 = (TextView) convertView.findViewById(R.id.dw2);
        TextView jg2 = (TextView) convertView.findViewById(R.id.jg2);
        ImageView snTv2 = (ImageView) convertView.findViewById(R.id.shopimg2);
        LinearLayout sp1 = (LinearLayout) convertView.findViewById(R.id.sp1);
        LinearLayout sp2 = (LinearLayout) convertView.findViewById(R.id.sp2);
        TextView nameTv2 = (TextView) convertView.findViewById(R.id.item_tx2);
        if (orderGoods != null) {
            dw1.setVisibility(View.VISIBLE);
            jg1.setVisibility(View.VISIBLE);
            dw2.setVisibility(View.VISIBLE);
            jg2.setVisibility(View.VISIBLE);

            dw1.setTextColor(Color.RED);
            jg1.setTextColor(Color.RED);
            dw2.setTextColor(Color.RED);
            jg2.setTextColor(Color.RED);
            if (orderGoods.getSptype() == 1) {

                dw1.setText("¥");
                jg1.setText(orderGoods.getPrice() + "");
                if (orderGoods2 != null) {

                    dw2.setText("¥");
                    jg2.setText(orderGoods2.getPrice() + "");
                } else {
                    dw2.setVisibility(View.GONE);
                    jg2.setVisibility(View.GONE);
                }

            } else {
                jg1.setText("积分");
                dw1.setText(orderGoods.getPrice() + "");
                if (orderGoods2 != null) {
                    jg2.setText("积分");
                    dw2.setText(orderGoods2.getPrice() + "");
                } else {
                    dw2.setVisibility(View.GONE);
                    jg2.setVisibility(View.GONE);
                }
            }
        } else {
            dw1.setVisibility(View.GONE);
            jg1.setVisibility(View.GONE);
        }
        sp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderGoods != null) {
                    Intent itt = new Intent(mContext, GoodsDetialActivity.class);
                    if ("538".equals(orderGoods.getType()) || "537".equals(orderGoods.getType())) {
                        itt = new Intent(mContext, BannerDetailsActivity.class);
                    } else {
                        itt = new Intent(mContext, GoodsDetialActivity.class);
                    }
                    String test = orderGoods.getId() + "";
                    itt.putExtra("type", orderGoods.getSptype());
                    itt.putExtra("proid", test);
                    mContext.startActivity(itt);
                }
            }
        });
        sp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderGoods2 != null) {
                    Intent itt = new Intent(mContext, GoodsDetialActivity.class);
                    if ("538".equals(orderGoods2.getType()) || "537".equals(orderGoods2.getType())) {
                        itt = new Intent(mContext, BannerDetailsActivity.class);

                    } else {
                        itt = new Intent(mContext, GoodsDetialActivity.class);
                    }

                    String test = orderGoods2.getId() + "";
                    itt.putExtra("proid", test);
                    itt.putExtra("type", orderGoods2.getSptype());
                    mContext.startActivity(itt);
                }
            }
        });
        if (orderGoods != null) {
            nameTv.setVisibility(View.VISIBLE);
            nameTv.setText(orderGoods.getProname());
            String sb = Constant.BASE_URL + orderGoods.getFolder() + orderGoods.getAutoname();
            Glide.with(mContext.getApplicationContext())
                    .load(sb)
                    .apply(mOptions)
                    .into(snTv);
        } else {
            sp1.setVisibility(View.INVISIBLE);
        }

        if (orderGoods2 != null) {
            nameTv2.setVisibility(View.VISIBLE);
            nameTv2.setText(orderGoods2.getProname());
            String sb2 = Constant.BASE_URL + orderGoods2.getFolder() + orderGoods2.getAutoname();
            Glide.with(mContext.getApplicationContext())
                    .load(sb2)
                    .apply(mOptions)
                    .into(snTv2);
        } else {
            sp2.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public ShopListBean getSopBean() {
        return orderGoods;
    }
}
