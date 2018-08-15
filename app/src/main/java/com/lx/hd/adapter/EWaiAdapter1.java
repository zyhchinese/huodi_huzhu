package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.EWaiEntity;
import com.lx.hd.bean.EWaiEntity1;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class EWaiAdapter1 extends RecyclerView.Adapter<EWaiAdapter1.ViewHolder>{
    private Context context;
    private List<EWaiEntity1> eWaiEntityList;
    public EWaiAdapter1(Context context, List<EWaiEntity1> eWaiEntityList) {
        this.context=context;
        this.eWaiEntityList=eWaiEntityList;
    }

    @Override
    public EWaiAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ewaixuqiu_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EWaiAdapter1.ViewHolder holder, int position) {
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
