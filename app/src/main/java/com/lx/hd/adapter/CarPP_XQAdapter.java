package com.lx.hd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.CarPP;
import com.lx.hd.bean.carhometopBean;
import com.lx.hd.ui.activity.CarHome_XQActivity;

import java.util.List;

/**
 * 车辆品牌详情列表adapter
 * Created by TB on 2017/9/19.
 */

public class CarPP_XQAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<carhometopBean> mMarkerData = null;
    LayoutInflater inflater;

    public CarPP_XQAdapter(Context context, List<carhometopBean> markerItems) {
        mContext = context;
        mMarkerData = markerItems;
        this.inflater = LayoutInflater.from(context);
    }

    public void setMarkerData(List<carhometopBean> markerItems) {
        mMarkerData = markerItems;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (null != mMarkerData) {
            count = mMarkerData.size();
        }
        return count;
    }

    public carhometopBean getItem(int position) {
        carhometopBean item = null;

        if (null != mMarkerData) {
            item = mMarkerData.get(position);
        }

        return item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_carpp_xq, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.jg = (TextView) convertView.findViewById(R.id.jg);
            viewHolder.logo = (ImageView) convertView
                    .findViewById(R.id.logo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        carhometopBean CarPP = getItem(position);
        if (null != CarPP) {
            viewHolder.title.setText(CarPP.getGroupname());
            viewHolder.jg.setText(CarPP.getGroupprice() + "");
            String sb = Constant.BASE_URL + CarPP.getFolder() + CarPP.getAutoname();
            Glide.with(mContext.getApplicationContext())
                    .load(sb)
                    .into(viewHolder.logo);

        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView logo;
        TextView title;
        TextView jg;
    }

}
