package com.lx.hd.widgets.toprecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lx.hd.R;

/**
 * 订单布局-top
 * 作者：fly on 2016/8/24 0024 23:45
 * 邮箱：cugb_feiyang@163.com
 */
public class ItemOrderTop implements OrderContent {
    String title;

    public ItemOrderTop(String title) {
        this.title = title;
    }


    @Override
    public int getLayout() {
        return R.layout.shop_list_top;
    }

    @Override
    public boolean isClickAble() {
        return true;
    }

    @Override
    public View getView(Context mContext, View convertView, LayoutInflater inflater) {
        inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(getLayout(), null);
        TextView type = (TextView) convertView.findViewById(R.id.type);
        type.setText(title);
        //TODO 数据展示
        return convertView;
    }
}
