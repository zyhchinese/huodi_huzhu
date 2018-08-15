package com.lx.hd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.EWaiEntity;
import com.lx.hd.ui.activity.LogisticsOrderDetailsActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class EWaiAdapter extends RecyclerView.Adapter<EWaiAdapter.ViewHolder>{
    private Context context;
    private List<EWaiEntity> eWaiEntityList;
    private int a;
    public EWaiAdapter(Context context, List<EWaiEntity> eWaiEntityList,int a) {
        this.context=context;
        this.eWaiEntityList=eWaiEntityList;
        this.a=a;
    }

    @Override
    public EWaiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ewaixuqiu_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EWaiAdapter.ViewHolder holder, int position) {
        if (a==-1){
            holder.tv_xuqiu_name.setTextColor(Color.parseColor("#B9B9B9"));
            holder.tv_xuqiu.setTextColor(Color.parseColor("#B9B9B9"));
        }
        holder.tv_xuqiu_name.setText(eWaiEntityList.get(position).getService_name());
        if (eWaiEntityList.get(position).getOwner_service_price()==0){
            holder.tv_xuqiu.setText("免费");
        }else {
            holder.tv_xuqiu.setText(eWaiEntityList.get(position).getOwner_service_price()+"元");
        }


    }

    @Override
    public int getItemCount() {
        return eWaiEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_xuqiu,tv_xuqiu_name;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_xuqiu= (TextView) itemView.findViewById(R.id.tv_xuqiu);
            tv_xuqiu_name= (TextView) itemView.findViewById(R.id.tv_xuqiu_name);
        }
    }
}
