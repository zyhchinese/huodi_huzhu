package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.AuditDetailsEntity;
import com.lx.hd.ui.activity.AuditDetailsActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class RecyclerAuditDetailsAdapter extends RecyclerView.Adapter<RecyclerAuditDetailsAdapter.ViewHolder>{
    private Context context;
    private List<AuditDetailsEntity> list;
    private int a=1;
    private String shijiquche_name;
    public RecyclerAuditDetailsAdapter(Context context, List<AuditDetailsEntity> list, String shijiquche_name) {
        this.context=context;
        this.list=list;
        this.shijiquche_name=shijiquche_name;
    }

    @Override
    public RecyclerAuditDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_tiche, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAuditDetailsAdapter.ViewHolder holder, int position) {
        if (shijiquche_name.equals("")){
            holder.relative17.setVisibility(View.GONE);
        }else {
            holder.relative17.setVisibility(View.VISIBLE);
        }
        holder.tv_car_type.setText(list.get(position).getCar_name());
        holder.tv_info.setText("提车信息  ("+a+")");
        a++;
        holder.tv_number.setText(list.get(position).getLease_count()+"辆");
        if (list.get(position).getOrder_type()==0){
            holder.tv_zuche_type.setText("短租");
            holder.tv_time.setText(list.get(position).getDuring_time()+"月");
            holder.tv_price.setText("¥"+(int)(list.get(position).getMoon_price())+"/月");
        }else {
            holder.tv_zuche_type.setText("长租");
            holder.tv_time.setText(list.get(position).getDuring_time()+"年");
            holder.tv_price.setText("¥"+(int)(list.get(position).getYear_price())+"/年");
        }
        holder.tv_car_price.setText((int)(list.get(position).getTotal_money())+"元");
        holder.tv_car_time.setText(list.get(position).getExpiry_time());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_car_type,tv_info,tv_number,tv_zuche_type,tv_time,tv_price,tv_car_price,
                tv_car_time;
        private RelativeLayout relative17;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_car_type= (TextView) itemView.findViewById(R.id.tv_car_type);
            tv_info= (TextView) itemView.findViewById(R.id.tv_info);
            tv_number= (TextView) itemView.findViewById(R.id.tv_number);
            tv_zuche_type= (TextView) itemView.findViewById(R.id.tv_zuche_type);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_price= (TextView) itemView.findViewById(R.id.tv_price);
            tv_car_price= (TextView) itemView.findViewById(R.id.tv_car_price);
            tv_car_time= (TextView) itemView.findViewById(R.id.tv_car_time);
            relative17= (RelativeLayout) itemView.findViewById(R.id.relative17);
        }
    }
}
