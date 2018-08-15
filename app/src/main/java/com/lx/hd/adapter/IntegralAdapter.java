package com.lx.hd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.IntegralBean;

import java.util.List;

/**
 * 积分列表adapter
 * Created by TB on 2017/9/1.
 */

public class IntegralAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<IntegralBean> mMarkerData = null;

    public IntegralAdapter(Context context, List<IntegralBean> markerItems) {
        mContext = context;
        mMarkerData = markerItems;
    }

    public void setMarkerData(List<IntegralBean> markerItems) {
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

    public IntegralBean getItem(int position) {
        IntegralBean item = null;

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
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_integral_list, null);

            viewHolder.type = (ImageView) convertView.findViewById(R.id.type);
            viewHolder.reason = (TextView) convertView
                    .findViewById(R.id.reason);
            viewHolder.createtime = (TextView) convertView
                    .findViewById(R.id.createtime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // set item values to the viewHolder:
        IntegralBean IntegralBean = getItem(position);
        if (null != IntegralBean) {
            viewHolder.createtime.setText(IntegralBean.getCreatetime());
            switch (IntegralBean.getType()) {
                default:
                    break;
                case 1:
                    viewHolder.type.setImageResource(R.mipmap.jf_j2);
                    viewHolder.reason.setText("积分增加 " + IntegralBean.getScore());
                    break;
                case 0:
                    viewHolder.type.setImageResource(R.mipmap.jf_j1);
                    viewHolder.reason.setText("积分减少 " + IntegralBean.getScore());
                    break;
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView type;
        TextView reason;
        TextView createtime;
    }

}
