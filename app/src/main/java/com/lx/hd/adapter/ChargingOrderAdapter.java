package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.base.fragment.BaseRecyclerAdapter;
import com.lx.hd.bean.ChargeOrder;


public class ChargingOrderAdapter extends BaseRecyclerAdapter<ChargeOrder> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {

    public ChargingOrderAdapter(Context context, int mode) {
        super(context, mode);

        setOnLoadingHeaderCallBack(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new HeaderViewHolder(mHeaderView);
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new NewsViewHolder(mInflater.inflate(R.layout.item_charging_state, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final ChargeOrder item, int position) {
        final NewsViewHolder vh = (NewsViewHolder) holder;
        if(item!=null){
            vh.tvCurrentTime.setText(item.getCreatetime());
            vh.tvOrderNo.setText(item.getOrderno());
            vh.tvEquipmentNo.setText(item.getElectricsbm());
            vh.tvChargeMoney.setText("¥ "+item.getRealmoney());
        }
    }
    private static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrentTime,tvOrderNo,tvEquipmentNo,tvChargeMoney;
        public NewsViewHolder(View itemView) {
            super(itemView);
            tvCurrentTime=(TextView)itemView.findViewById(R.id.tv_current_time);
            tvOrderNo=(TextView)itemView.findViewById(R.id.tv_orderno);
            tvEquipmentNo=(TextView)itemView.findViewById(R.id.tv_equipmentno);
            tvChargeMoney=(TextView)itemView.findViewById(R.id.tv_charge_money);
        }
    }
}
