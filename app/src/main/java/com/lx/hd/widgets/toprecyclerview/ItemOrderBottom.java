package com.lx.hd.widgets.toprecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lx.hd.R;

/**
 * 订单布局-in
 * 作者：fly on 2016/8/24 0024 23:45
 * 邮箱：cugb_feiyang@163.com
 */
public class ItemOrderBottom implements OrderContent {

    public ItemOrderBottom() {
    }

    @Override
    public int getLayout() {
        //暂时不需要底部布局  如果需要时此处传相应布局
        return R.layout.shop_list_top;
    }

    @Override
    public boolean isClickAble() {
        return true;
    }

    @Override
    public View getView(Context mContext, View convertView, LayoutInflater inflater) {
        inflater = LayoutInflater.from(mContext);
        convertView =  inflater.inflate(getLayout(),null);
        //TODO 数据展示-订单内容
        return convertView;
    }
}
