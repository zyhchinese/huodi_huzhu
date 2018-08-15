package com.lx.hd.widgets.toprecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 接口回调
 * 作者：fly on 2016/8/24 0024 23:33
 * 邮箱：cugb_feiyang@163.com
 */
public interface OrderContent {

    public int getLayout();

    public boolean isClickAble();

    public View getView(Context mContext, View convertView, LayoutInflater inflater);
}

