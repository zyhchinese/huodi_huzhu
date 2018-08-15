package com.lx.hd.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.bean.ShopListBean;
import com.lx.hd.bean.ShopListType;
import com.lx.hd.bean.carhometopBean;
import com.lx.hd.bean.myorderbean;
import com.lx.hd.bean.myorderbeanforlist;
import com.lx.hd.interf.onOrderListener;
import com.lx.hd.widgets.LoadListView;
import com.lx.hd.widgets.myListView;
import com.lx.hd.widgets.toprecyclerview.ItemOrderIn;
import com.lx.hd.widgets.toprecyclerview.ItemOrderTop;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MyOrderAdapter extends BaseAdapter {
    private List<myorderbean> activityList;
    @SuppressWarnings("unused")
    private Context context;
    private LayoutInflater inflater = null;
    private ListViewAdapter listViewAdapter;
    private List<myorderbeanforlist> zlistdata;//内部商品列表数据源
    private OnClickItemChild onClickItemChild;
    private OnClickChild onClickChild;

    public void setOnClickChild(OnClickChild onClickChild) {
        this.onClickChild = onClickChild;
    }

    public void setOnClickItemChild(OnClickItemChild onClickItemChild) {
        this.onClickItemChild = onClickItemChild;
    }

    public interface OnClickItemChild {
        void onClick(int position);
    }

    public interface OnClickChild {
        void onClick(int position);
    }

    public MyOrderAdapter(List<myorderbean> data1, Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.activityList = data1;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return activityList.size();
    }

    public List<myorderbean> getActivityList() {
        return activityList;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_myorder_mainlist, null);
            holder.ddh = (TextView) convertView.findViewById(R.id.ddh);
            holder.zt = (TextView) convertView.findViewById(R.id.zt);
            holder.sl = (TextView) convertView.findViewById(R.id.sl);
            holder.jg = (TextView) convertView.findViewById(R.id.jg);
            holder.dw = (TextView) convertView.findViewById(R.id.dw);
            holder.jg1 = (TextView) convertView.findViewById(R.id.jg1);
            holder.orderlist = (myListView) convertView.findViewById(R.id.orderlist);
            holder.btn_quxiao = (Button) convertView.findViewById(R.id.btn_quxiao);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (activityList.get(position).getOrderstatus() == 0) {
            holder.btn_quxiao.setVisibility(View.VISIBLE);
            holder.btn_quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickChild.onClick(position);
                }
            });
        } else {
            holder.btn_quxiao.setVisibility(View.GONE);
        }

        holder.ddh.setText(activityList.get(position).getOrderno());
        if (activityList.get(position).getType() == 1) {
            holder.dw.setText("元");
        } else {
            holder.dw.setText("积分");
        }
        switch (activityList.get(position).getOrderstatus()) {
            case 0:
                holder.zt.setText("待支付");
                holder.zt.setTextColor(Color.parseColor("#d0121b"));
                break;
            case 1:
                holder.zt.setText("待发货");
                holder.zt.setTextColor(Color.parseColor("#ff9400"));
                break;
            case 2:
                holder.zt.setText("待收货");
                holder.zt.setTextColor(Color.parseColor("#ffcc00"));
                break;
            case 4:
                holder.zt.setText("已完成");
                holder.zt.setTextColor(Color.parseColor("#20ab12"));
                break;
            default:
                break;
        }
        holder.sl.setText(activityList.get(position).getTotalcount() + "");
        holder.jg.setText(activityList.get(position).getTotalmoney() + "");
        holder.jg1.setText(activityList.get(position).getTotaljifen() + "");
        holder.orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                onClickItemChild.onClick(position);
            }
        });
//        holder.orderlist.setAdapter(listViewAdapter);
//        listViewAdapter.notifyDataSetChanged();
//        setListViewHeightBasedOnChildren(holder.orderlist);
        updateInfo(activityList.get(position).getItemlist(), holder.orderlist, activityList.get(position).getType());
        return convertView;
    }

    public void setMarkerData(List<myorderbean> markerItems) {
        activityList = markerItems;
    }

    public final class ViewHolder {
        public TextView ddh, zt, sl, jg, dw,jg1;
        public myListView orderlist;
        private Button btn_quxiao;

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void updateInfo(List<myorderbeanforlist> data, myListView listview, int type) {
        listViewAdapter = new ListViewAdapter(data, context, type);
        listview.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listview);


    }


}
