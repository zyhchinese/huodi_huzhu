package com.lx.hd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.GoodsBean;
import com.lx.hd.bean.carhometopBean;
import com.lx.hd.interf.onShopPingListener;

import java.util.List;

import static com.lx.hd.R.id.Price;
import static com.lx.hd.R.id.sl;

/**
 * 购物车adapter
 * Created by TB on 2017/10/9.
 */

public class ShoppingCart_Adapter extends BaseAdapter {
    private Context mContext = null;
    private List<GoodsBean> mMarkerData = null;
    private onShopPingListener onShopPingListener;
    LayoutInflater inflater;


    public ShoppingCart_Adapter(Context context, List<GoodsBean> markerItems) {
        mContext = context;
        mMarkerData = markerItems;
        this.inflater = LayoutInflater.from(context);
        onShopPingListener = (com.lx.hd.interf.onShopPingListener) context;
    }

    public void setMarkerData(List<GoodsBean> markerItems) {
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

    public GoodsBean getItem(int position) {
        GoodsBean item = null;

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
            convertView = inflater.inflate(R.layout.item_shopping_cart_list, null);
            viewHolder.xh = (TextView) convertView.findViewById(R.id.xh);
            viewHolder.sl = (TextView) convertView.findViewById(sl);
            viewHolder.jg = (TextView) convertView.findViewById(R.id.jg);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.gwc_zj = (ImageView) convertView.findViewById(R.id.gwc_zj);
            viewHolder.delete = (ImageView) convertView.findViewById(R.id.delete);
            viewHolder.gwc_js = (ImageView) convertView.findViewById(R.id.gwc_js);
            viewHolder.xz = (ImageView) convertView.findViewById(R.id.xz);
            viewHolder.dw = (TextView) convertView.findViewById(R.id.dw);
            viewHolder.jg1 = (TextView) convertView.findViewById(R.id.jg1);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final GoodsBean CarPP = getItem(position);
        // set item values to the viewHolder:
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShopPingListener.Ondelete(CarPP.getId() + "");
            }
        });

        if (null != CarPP) {
            viewHolder.name.setText(CarPP.getProname());
            viewHolder.jg.setText(CarPP.getPrice() + "");
            viewHolder.xh.setText(CarPP.getFitcar() + "");
            viewHolder.sl.setText(CarPP.getCount() + "");
            viewHolder.jg1.setText(CarPP.getPrice_jf() + "");
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.gwc_zj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String count = finalViewHolder.sl.getText().toString();
                    int sl = Integer.valueOf(count);
                    sl = sl + 1;
                    finalViewHolder.sl.setText(sl + "");
                    CarPP.setCount(sl);
                    onShopPingListener.OnListener();
                }
            });

            if (CarPP.isok()) {
                viewHolder.xz.setImageResource(R.mipmap.bg_radio_select);
            } else {
                viewHolder.xz.setImageResource(R.mipmap.gwc_wxz);
            }
            viewHolder.gwc_js.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String count = finalViewHolder.sl.getText().toString();
                    int sl = Integer.valueOf(count);
                    if (sl > 1) {
                        sl = sl - 1;
                        finalViewHolder.sl.setText(sl + "");
                        CarPP.setCount(sl);
                        onShopPingListener.OnListener();
                    }
                }
            });
            if (CarPP.getType() == 1) {
                viewHolder.dw.setText("元");
            } else {
                viewHolder.dw.setText("积分");
            }
            viewHolder.xz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CarPP.isok()) {
                        CarPP.setIsok(false);
                        finalViewHolder.xz.setImageResource(R.mipmap.gwc_wxz);
                        onShopPingListener.OnListener();

                    } else {
                        CarPP.setIsok(true);
                        finalViewHolder.xz.setImageResource(R.mipmap.bg_radio_select);
                        onShopPingListener.OnListener();
                    }
                }
            });
            String sb = Constant.BASE_URL + CarPP.getFolder() + CarPP.getAutoname();
            Glide.with(mContext.getApplicationContext())
                    .load(sb)
                    .into(viewHolder.image);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView image, gwc_zj, gwc_js, xz, delete;
        TextView xh, sl, dw;
        TextView jg, name;
        TextView jg1,dw1;


    }

}
