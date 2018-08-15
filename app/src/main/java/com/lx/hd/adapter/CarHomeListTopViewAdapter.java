package com.lx.hd.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.CarPP;

import java.util.ArrayList;
import java.util.List;

public class CarHomeListTopViewAdapter extends BaseAdapter {
    private List<CarPP> mNameList = new ArrayList<CarPP>();
    private LayoutInflater mInflater;
    private Context mContext;
    LinearLayout.LayoutParams params;
    private int clickTemp = 0;


    public CarHomeListTopViewAdapter(Context context, List<CarPP> nameList) {
        mNameList = nameList;
        if (mNameList.size() > 10) {
            mNameList= mNameList.subList(0, 10);
        }
        mContext = context;
        mInflater = LayoutInflater.from(context);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

    }

    public int getCount() {
        return mNameList.size();
    }

    public CarPP getItem(int position) {
        return mNameList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ItemViewTag viewTag;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_carhome_topview, null);

            // construct an item tag
            viewTag = new ItemViewTag((TextView) convertView.findViewById(R.id.mName), (ImageView) convertView.findViewById(R.id.car_bm));
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }


        // set name
        viewTag.mName.setText(mNameList.get(position).getBrandname());
        String sb = Constant.BASE_URL + mNameList.get(position).getFolder() + mNameList.get(position).getAutoname();
        Glide.with(mContext)
                .load(sb)
                .into(viewTag.car_bm);
        return convertView;
    }


    class ItemViewTag {
        protected ImageView car_bm;
        protected TextView mName;

        /**
         * The constructor to construct a navigation view tag
         *
         * @param name the name view of the item
         */
        public ItemViewTag(TextView name, ImageView car_bm) {
            this.mName = name;
            this.car_bm = car_bm;
        }
    }
}
