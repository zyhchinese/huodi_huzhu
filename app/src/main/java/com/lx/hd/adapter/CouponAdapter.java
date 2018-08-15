package com.lx.hd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.CouponBean;

import java.util.List;

/**
 * 优惠券列表adapter
 * Created by TB on 2017/8/28.
 */

public class CouponAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<CouponBean> mMarkerData = null;

    public CouponAdapter(Context context, List<CouponBean> markerItems) {
        mContext = context;
        mMarkerData = markerItems;
    }

    public void setMarkerData(List<CouponBean> markerItems) {
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

    public CouponBean getItem(int position) {
        CouponBean item = null;

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
            convertView = mInflater.inflate(R.layout.item_coupon, null);
            viewHolder.cheap_name = (TextView) convertView.findViewById(R.id.cheap_name);
            viewHolder.cheap_descript = (TextView) convertView
                    .findViewById(R.id.cheap_descript);
            viewHolder.cheap_equal_money = (TextView) convertView
                    .findViewById(R.id.cheap_equal_money);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        CouponBean CouponBean = getItem(position);
        if (null != CouponBean) {
            viewHolder.cheap_name.setText(CouponBean.getCheap_name() + "");
            viewHolder.cheap_descript.setText(CouponBean.getCheap_descript());
            viewHolder.cheap_equal_money.setText(""+CouponBean.getCheap_equal_money()+"");

        }

        return convertView;
    }

    private static class ViewHolder {
        TextView cheap_name, cheap_descript, cheap_equal_money;
    }

}
