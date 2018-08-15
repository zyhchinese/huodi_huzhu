package com.lx.hd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.activebean;
import com.lx.hd.bean.carhometopBean;

import java.util.List;

/**
 * 活动列表adapter
 * Created by TB on 2017/10/11.
 */

public class ActiveListAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<activebean> mMarkerData = null;
    LayoutInflater inflater;

    public ActiveListAdapter(Context context, List<activebean> markerItems) {
        mContext = context;
        mMarkerData = markerItems;
        this.inflater = LayoutInflater.from(context);
    }

    public void setMarkerData(List<activebean> markerItems) {
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

    public activebean getItem(int position) {
        activebean item = null;

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
            convertView = inflater.inflate(R.layout.item_active_list, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.img_icon = (ImageView) convertView
                    .findViewById(R.id.img_icon);
            viewHolder.tv_nr = (TextView) convertView.findViewById(R.id.tv_nr);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        activebean CarPP = getItem(position);
        if (null != CarPP) {
            viewHolder.tv_title.setText(CarPP.getActivityname());
            viewHolder.tv_time.setText(CarPP.getCreatetime() + "");
            viewHolder.tv_nr.setText(CarPP.getContent() + "");
            String sb = Constant.BASE_URL + CarPP.getFolder() + CarPP.getAutoname();
            Glide.with(mContext.getApplicationContext())
                    .load(sb)
                    .into(viewHolder.img_icon);

        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView img_icon;
        TextView tv_title;
        TextView tv_time;
        TextView tv_nr;
    }

}
