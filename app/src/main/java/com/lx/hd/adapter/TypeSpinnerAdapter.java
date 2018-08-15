package com.lx.hd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.ui.activity.OrderSearchsActivity;

/**
 * Created by Administrator on 2018/1/9.
 */

public class TypeSpinnerAdapter extends BaseAdapter{
    private Context context;
    private String[] type;
    public TypeSpinnerAdapter(Context context, String[] type) {
        this.context=context;
        this.type=type;
    }

    @Override
    public int getCount() {
        return type.length;
    }

    @Override
    public Object getItem(int position) {
        return type[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item,null);
        if (convertView!=null){
            TextView textView= (TextView) convertView.findViewById(R.id.tv_name);
            textView.setText(type[position]);

        }
        return convertView;
    }
}
