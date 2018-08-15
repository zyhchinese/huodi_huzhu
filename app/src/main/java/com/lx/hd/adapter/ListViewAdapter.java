package com.lx.hd.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.myorderbeanforlist;

public class ListViewAdapter extends BaseAdapter {
    private List<myorderbeanforlist> activityList;
    @SuppressWarnings("unused")
    private Context context;
    private LayoutInflater inflater = null;
    private int type;

    public ListViewAdapter(List<myorderbeanforlist> zlistdata, Context context, int type) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.activityList = zlistdata;
        this.inflater = LayoutInflater.from(context);
        this.type = type;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return activityList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return activityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_myorder_list, null);
            holder.xh = (TextView) convertView.findViewById(R.id.xh);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.sl = (TextView) convertView.findViewById(R.id.sl);
            holder.je = (TextView) convertView.findViewById(R.id.je);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.yuan = (TextView) convertView.findViewById(R.id.yuan);
            holder.tv_price_jf = (TextView) convertView.findViewById(R.id.tv_price_jf);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (type == 1) {
            holder.yuan.setText("元");
        } else {
            holder.yuan.setText("积分");
        }
        holder.xh.setText(activityList.get(position).getSpecification());
        holder.name.setText(activityList.get(position).getProname());
        holder.sl.setText(activityList.get(position).getCount() + "");
        holder.je.setText(activityList.get(position).getPrice() + "");
        holder.tv_price_jf.setText(activityList.get(position).getPrice_jf() + "");
        String sb = Constant.BASE_URL + activityList.get(position).getFolder() + activityList.get(position).getAutoname();
        try {
            Glide.with(context.getApplicationContext())
                    .load(sb)
                    .into(holder.image);
        } catch (Exception e) {

        }

        return convertView;
    }

    public final class ViewHolder {
        public TextView xh, name, sl, je, yuan,tv_price_jf;
        public ImageView image;

    }
}
