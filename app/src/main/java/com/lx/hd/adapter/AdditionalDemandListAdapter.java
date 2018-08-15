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
import com.lx.hd.bean.AdditionalDemandListBean;
import com.lx.hd.bean.AdditionalDemandProListBean;
import com.lx.hd.bean.activebean;

import java.util.ArrayList;
import java.util.List;

/**
 * 額外需求adapter
 * Created by TB on 2018/1/9.
 */

public class AdditionalDemandListAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<AdditionalDemandListBean> mMarkerData;
    LayoutInflater inflater;


    public ArrayList<AdditionalDemandListBean> getmMarkerData() {
        return mMarkerData;
    }


    public AdditionalDemandListAdapter(Context context, ArrayList<AdditionalDemandListBean> markerItems) {
        mContext = context;
        mMarkerData = markerItems;
        this.inflater = LayoutInflater.from(context);
    }

    public void setMarkerData(ArrayList<AdditionalDemandListBean> markerItems) {
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


    public AdditionalDemandListBean getItem(int position) {
        return mMarkerData.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_additional_demand, null);
            viewHolder.isselect = (ImageView) convertView.findViewById(R.id.isselect);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        AdditionalDemandListBean CarPP = getItem(position);
        if (null != CarPP) {
            if (CarPP.isselect()) {
                viewHolder.isselect.setImageResource(R.mipmap.ewxq_xz);
            } else {
                viewHolder.isselect.setImageResource(R.mipmap.ewxq_wxz);
            }
            if (CarPP.getService_price() == 0) {
                mMarkerData.get(position).setIsselect(true);
                viewHolder.isselect.setImageResource(R.mipmap.ewxq_xz);
                viewHolder.title.setText(CarPP.getService_name() + "(免费)");
            } else {
                viewHolder.title.setText(CarPP.getService_name() + "(附加" + (CarPP.getService_price() * 100) + "%路费)");
            }
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.isselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMarkerData.get(position).isselect()) {
                        finalViewHolder.isselect.setImageResource(R.mipmap.ewxq_wxz);
                        mMarkerData.get(position).setIsselect(false);


                    } else {
                        finalViewHolder.isselect.setImageResource(R.mipmap.ewxq_xz);
                        mMarkerData.get(position).setIsselect(true);
                    }
                }
            });
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        ImageView isselect;
    }

}
