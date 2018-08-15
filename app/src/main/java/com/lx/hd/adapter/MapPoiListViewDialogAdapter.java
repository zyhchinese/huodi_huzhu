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
import com.lx.hd.bean.mappoibean;

import java.util.List;

/**
 * 地图poi列表adapter
 * Created by TB on 2018/01/11.
 */

public class MapPoiListViewDialogAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<mappoibean> mMarkerData = null;
    LayoutInflater inflater;

    public MapPoiListViewDialogAdapter(Context context, List<mappoibean> markerItems) {
        mContext = context;
        mMarkerData = markerItems;
        this.inflater = LayoutInflater.from(context);
    }

    public void setMarkerData(List<mappoibean> markerItems) {
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

    public mappoibean getItem(int position) {
        mappoibean item = null;

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
            convertView = inflater.inflate(R.layout.item_map_poilist, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tv_text= (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        mappoibean CarPP = getItem(position);
        if (null != CarPP) {
            viewHolder.tv_title.setText(CarPP.getTitle());
            viewHolder.tv_text.setText(CarPP.getText());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tv_title;
        TextView tv_text;
    }

}
