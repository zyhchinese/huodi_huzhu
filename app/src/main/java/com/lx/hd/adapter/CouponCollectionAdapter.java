package com.lx.hd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.CouponBean;
import com.lx.hd.bean.CouponCollection;
import com.lx.hd.interf.CouponCollectionListener;

import java.util.List;

/**
 * 领取优惠券列表adapter
 * Created by TB on 2017/8/28.
 */

public class CouponCollectionAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<CouponCollection> mMarkerData = null;
    CouponCollectionListener ccl;

    public CouponCollectionAdapter(Context context, List<CouponCollection> markerItems) {
        mContext = context;
        mMarkerData = markerItems;
        ccl = (CouponCollectionListener) context;
    }

    public void setMarkerData(List<CouponCollection> markerItems) {
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

    public CouponCollection getItem(int position) {
        CouponCollection item = null;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_couponcollection, null);
            viewHolder.cheap_name = (TextView) convertView.findViewById(R.id.cheap_name);
            viewHolder.cheap_descript = (TextView) convertView
                    .findViewById(R.id.cheap_descript);
            viewHolder.cheap_coupon_count = (TextView) convertView
                    .findViewById(R.id.cheap_coupon_count);
            viewHolder.cheap_equal_money = (TextView) convertView
                    .findViewById(R.id.cheap_equal_money);
            viewHolder.button2 = (Button) convertView
                    .findViewById(R.id.button2);
            convertView.setTag(viewHolder);
            viewHolder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ccl.OnClickItem(getItem(position));
                }
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        CouponCollection CouponBean = getItem(position);
        if (null != CouponBean) {
            viewHolder.cheap_name.setText(CouponBean.getCheap_name());
            viewHolder.cheap_descript.setText(CouponBean.getCheap_descript());
            viewHolder.cheap_coupon_count.setText("当前剩余:" + CouponBean.getCheap_coupon_count() + "张");
            viewHolder.cheap_equal_money.setText(CouponBean.getCheap_equal_money() + "");
            if (CouponBean.getCheap_coupon_count() > 0) {
                viewHolder.button2.setVisibility(View.VISIBLE);
            } else {
                viewHolder.button2.setVisibility(View.GONE);
            }

        }

        return convertView;
    }

    private static class ViewHolder {
        TextView cheap_name, cheap_descript, cheap_coupon_count, cheap_equal_money;
        Button button2;
    }

}
