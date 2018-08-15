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

import com.lx.hd.R;

import java.util.ArrayList;

public class PayMoneyGridViewAdapter extends BaseAdapter {
    private ArrayList<String> mNameList = new ArrayList<String>();
    private LayoutInflater mInflater;
    private Context mContext;
    LinearLayout.LayoutParams params;

    private int clickTemp = 0;

    // 标识选择的Item
    public void setSeclect(int position) {
        this.clickTemp = position;
        notifyDataSetChanged();
    }

    public PayMoneyGridViewAdapter(Context context, ArrayList<String> nameList) {
        mNameList = nameList;
        mContext = context;
        mInflater = LayoutInflater.from(context);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

    }

    public int getCount() {
        return mNameList.size();
    }

    public Object getItem(int position) {
        return mNameList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ItemViewTag viewTag;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_gridview_pay, null);

            // construct an item tag
            viewTag = new ItemViewTag( (TextView) convertView.findViewById(R.id.item_grida_text));
            convertView.setTag(viewTag);
        } else
        {
            viewTag = (ItemViewTag) convertView.getTag();
        }


        if(clickTemp==position){
            viewTag.mName.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            viewTag.mName.setSelected(true);
        }else {
            viewTag.mName.setTextColor(ContextCompat.getColor(mContext,R.color.black_alpha_240));
            viewTag.mName.setSelected(false);
        }

        // set name
        viewTag.mName.setText(mNameList.get(position));

        return convertView;
    }



    class ItemViewTag
    {
        protected ImageView mIcon;
        protected TextView mName;

        /**
         * The constructor to construct a navigation view tag
         *
         * @param name
         *            the name view of the item
         */
        public ItemViewTag( TextView name)
        {
            this.mName = name;
        }
    }
}
