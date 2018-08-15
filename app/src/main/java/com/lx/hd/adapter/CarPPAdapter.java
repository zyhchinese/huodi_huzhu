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
import com.lx.hd.bean.CarPP;

import java.util.List;

/**
 * 车辆品牌列表adapter
 * Created by TB on 2017/9/19.
 */

public class CarPPAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<CarPP> mMarkerData = null;

    public CarPPAdapter(Context context, List<CarPP> markerItems) {
        mContext = context;
        mMarkerData = markerItems;
    }

    public void setMarkerData(List<CarPP> markerItems) {
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

    public CarPP getItem(int position) {
        CarPP item = null;

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
            convertView = mInflater.inflate(R.layout.item_carpp, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.logo = (ImageView) convertView
                    .findViewById(R.id.logo);
            viewHolder.tv_index = (TextView) convertView
                    .findViewById(R.id.tv_index);
            viewHolder.tv_index1 = (View) convertView
                    .findViewById(R.id.tv_index1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        CarPP CarPP = mMarkerData.get(position);
        if (null != CarPP) {
            viewHolder.title.setText(CarPP.getBrandname());

            // 根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position);

            // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)) {
                viewHolder.tv_index.setVisibility(View.VISIBLE);
                viewHolder.tv_index1.setVisibility(View.VISIBLE);
                viewHolder.tv_index.setText(CarPP.getIndex());
            } else {
                viewHolder.tv_index.setVisibility(View.GONE);
                viewHolder.tv_index1.setVisibility(View.GONE);

            }
        }
        String sb = Constant.BASE_URL + CarPP.getFolder() + CarPP.getAutoname();
        Glide.with(mContext.getApplicationContext())
                .load(sb)
                .into(viewHolder.logo);
        return convertView;
    }

    private static class ViewHolder {
        ImageView logo;
        TextView title;
        TextView tv_index;
        View tv_index1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mMarkerData.get(position).getIndex().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mMarkerData.get(i).getIndex();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

}
