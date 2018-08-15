package com.lx.hd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.walletBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 钱包列表adapter
 * Created by TB on 2017/8/28.
 */

public class WalletAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<walletBean> mMarkerData = null;

    public WalletAdapter(Context context, List<walletBean> markerItems) {
        mContext = context;
        mMarkerData = markerItems;
    }

    public void setMarkerData(List<walletBean> markerItems) {
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

    public walletBean getItem(int position) {
        walletBean item = null;

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
            convertView = mInflater.inflate(R.layout.item_wallet, null);

            viewHolder.Price1 = (TextView) convertView.findViewById(R.id.Price);
            viewHolder.time1 = (TextView) convertView
                    .findViewById(R.id.time);
            viewHolder.oddnumbers1 = (TextView) convertView
                    .findViewById(R.id.oddnumbers);
            viewHolder.rechargeoddnumber1 = (TextView) convertView
                    .findViewById(R.id.rechargeoddnumber);
            viewHolder.rechargetype1 = (TextView) convertView
                    .findViewById(R.id.rechargetype);
            viewHolder.czlx_title = (TextView) convertView
                    .findViewById(R.id.czlx_title);
            viewHolder.img_icon = (ImageView) convertView
                    .findViewById(R.id.img_icon);
            viewHolder.rechargetype111= (TextView) convertView.findViewById(R.id.rechargetype111);
            viewHolder.czdh_super = (RelativeLayout) convertView.findViewById(R.id.czdh_super);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        walletBean walletBean = getItem(position);
        if (null != walletBean) {
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
//            String format = decimalFormat.format(Float.parseFloat(walletBean.getMoney()));
//            viewHolder.Price1.setText(format);
            viewHolder.rechargetype111.setText(walletBean.getOrdertype());
            viewHolder.time1.setText(walletBean.getCreatetime());
            viewHolder.rechargeoddnumber1.setText(walletBean.getChargeno());
            if (walletBean.getType() == 0) {
                viewHolder.img_icon.setImageResource(R.mipmap.img_huodiliushu1);
                viewHolder.oddnumbers1.setText(walletBean.getTradeno());
                viewHolder.czlx_title.setText("类型");
                viewHolder.czdh_super.setVisibility(View.GONE);
                String format = decimalFormat.format(Float.parseFloat(walletBean.getMoney()));
                viewHolder.Price1.setText("+"+format);
            } else {
                viewHolder.img_icon.setImageResource(R.mipmap.img_huodiliushui);
                viewHolder.czdh_super.setVisibility(View.GONE);
                viewHolder.oddnumbers1.setText(walletBean.getTradeno());
                viewHolder.czlx_title.setText("类型");
                String format = decimalFormat.format(Float.parseFloat(walletBean.getMoney()));
                viewHolder.Price1.setText("-"+format);
            }
            switch (walletBean.getPaytype()) {
                case 10:
                    viewHolder.rechargetype1.setText("平台盈利-司机找货");
                    break;
                case 9:
                    viewHolder.rechargetype1.setText("司机找货-盈利");
                    break;
                case 8:
                    viewHolder.rechargetype1.setText("平台盈利-取消发货");
                    break;
                case 7:
                    viewHolder.rechargetype1.setText("货主发货-超时取消");
                    break;
                case 6:
                    viewHolder.rechargetype1.setText("货主发货");
                    break;
                case 5:
                    viewHolder.rechargetype1.setText("货主取消发货");
                    break;
                case 4:
                    viewHolder.rechargetype1.setText("商城支付");
                    break;
                case 3:
                    viewHolder.rechargetype1.setText("充电支付");
//                    if (viewHolder.oddnumbers1.getText().toString().substring(0,3).equals("YDD")){
//                        viewHolder.rechargetype1.setText("商城（余额）");
//                    }else {
//                        if (walletBean.getType() == 0){
//                            viewHolder.rechargetype1.setText("余额");
//                        }else {
//                            viewHolder.rechargetype1.setText("充电（余额）");
//                        }
//
//                    }
                    break;
                case 2:
//                    viewHolder.rechargetype1.setText("银行卡提现");
                    viewHolder.rechargetype1.setText("余额提现");
                    break;
                case 1:
                    viewHolder.rechargetype1.setText("支付宝充值");
//                    if (viewHolder.oddnumbers1.getText().toString().substring(0,3).equals("YDD")){
//                        viewHolder.rechargetype1.setText("商城（支付宝）");
//                    }else {
//                        if (walletBean.getType() == 0){
//                            viewHolder.rechargetype1.setText("支付宝");
//                        }else {
//                            viewHolder.rechargetype1.setText("充电（支付宝）");
//                        }
//                    }
                    break;
                case 0:
                    viewHolder.rechargetype1.setText("微信充值");
//                    if (viewHolder.oddnumbers1.getText().toString().substring(0,3).equals("YDD")){
//                        viewHolder.rechargetype1.setText("商城（微信）");
//                    }else {
//
//                        if (walletBean.getType() == 0){
//                            viewHolder.rechargetype1.setText("微信");
//                        }else {
//                            viewHolder.rechargetype1.setText("充电（微信）");
//                        }
//                    }
                    break;
                default:
                    break;
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView Price1;
        TextView time1;
        TextView oddnumbers1;
        TextView rechargeoddnumber1;
        TextView rechargetype1;
        ImageView img_icon;
        RelativeLayout czdh_super;
        TextView czlx_title,rechargetype111;
    }

}
