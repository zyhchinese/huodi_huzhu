package com.lx.hd.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.CarPP;
import com.lx.hd.bean.GoodsColosBean;
import com.lx.hd.utils.ZETTool;

import java.util.ArrayList;
import java.util.List;

public class GoodsColorsAdapter extends BaseAdapter {
    private List<GoodsColosBean> mNameList = new ArrayList<GoodsColosBean>();
    private LayoutInflater mInflater;
    private Context mContext;
    private int colnum;
    LinearLayout.LayoutParams params;
    private int clickTemp = 0;


    public GoodsColorsAdapter(Context context, List<GoodsColosBean> nameList, int colnum) {
        mNameList = nameList;
        mContext = context;
        this.colnum = colnum;
        mInflater = LayoutInflater.from(context);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

    }

    public int getCount() {
        return mNameList.size();
    }

    public GoodsColosBean getItem(int position) {
        return mNameList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ItemViewTag viewTag;

        if (convertView == null) {
            // 根据列数计算项目宽度，以使总宽度尽量填充屏幕
            convertView = mInflater.inflate(R.layout.item_goods_colors_select, parent, false);
//            DisplayMetrics dm = new DisplayMetrics();
//            ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
//            int height = dm.heightPixels ;//高度
//            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height/5));

            viewTag = new ItemViewTag((TextView) convertView.findViewById(R.id.thiscolor));
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }


        // set name
        viewTag.thiscolor.setText(mNameList.get(position).getColosname());
        if (mNameList.get(position).isok() && mNameList.get(position).getType() == 1) {
            viewTag.thiscolor.setTextColor(Color.parseColor("#c30e21"));
            viewTag.thiscolor.setBackgroundResource(R.drawable.textview_border_true);
        } else if (!mNameList.get(position).isok() && mNameList.get(position).getType() == 1) {
            viewTag.thiscolor.setTextColor(Color.parseColor("#000000"));
            viewTag.thiscolor.setBackgroundResource(R.drawable.textview_border);
        } else if (mNameList.get(position).isok() && mNameList.get(position).getType() == 2) {
            viewTag.thiscolor.setTextColor(Color.parseColor("#c30e21"));
            viewTag.thiscolor.setBackgroundResource(R.drawable.textview_border_true);
        } else if (!mNameList.get(position).isok() && mNameList.get(position).getType() == 2) {
            viewTag.thiscolor.setTextColor(Color.parseColor("#000000"));
            viewTag.thiscolor.setBackgroundResource(R.drawable.textview_border);
        }
        // 根据列数计算项目宽度，以使总宽度尽量填充屏幕

//        int itemWidth = (ZETTool.getScreenWidth((Activity) mContext) - colnum -20 * 10) / colnum;
//        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
//                itemWidth,
//                AbsListView.LayoutParams.WRAP_CONTENT);
//       // convertView.setLayoutParams(new GridView.LayoutParams(colnum / 3, colnum / 3));
//        convertView.setLayoutParams(param);
        return convertView;
    }


    class ItemViewTag {
        protected TextView thiscolor;

        /**
         * The constructor to construct a navigation view tag
         *
         * @param thiscolor the name view of the item
         */
        public ItemViewTag(TextView thiscolor) {
            this.thiscolor = thiscolor;
        }
    }
}
