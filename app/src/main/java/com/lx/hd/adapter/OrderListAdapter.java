package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.OrderDetailsListEntity;
import com.lx.hd.bean.ShoppingEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/21.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{
    private Context context;
    private List<OrderDetailsListEntity> list;

    public OrderListAdapter(Context context, List<OrderDetailsListEntity> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderListAdapter.ViewHolder holder, int position) {
        if (!list.get(position).getAutoname().equals("")&&!list.get(position).getFolder().equals("")) {
            Glide.with(context).load(Constant.BASE_URL +list.get(position).getFolder()+ list.get(position).getAutoname()).into(holder.img_shangpin);
        }else {
            holder.img_shangpin.setImageResource(R.mipmap.icon_default);
        }

        holder.tv_name.setText(list.get(position).getProname());
        holder.tv_name1.setText(list.get(position).getProductdes());
        holder.tv_color.setText(list.get(position).getColor());
        holder.tv_type1.setText(list.get(position).getSpecification());
        holder.tv_money1.setText(String.valueOf(list.get(position).getPrice()));
        holder.tv_number.setText(String.valueOf(list.get(position).getCount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_shangpin;
        private TextView tv_name,tv_name1,tv_color,tv_type1,tv_money1,tv_number;
        public ViewHolder(View itemView) {
            super(itemView);
            img_shangpin= (ImageView) itemView.findViewById(R.id.img_shangpin);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_name1= (TextView) itemView.findViewById(R.id.tv_name1);
            tv_color= (TextView) itemView.findViewById(R.id.tv_color);
            tv_type1= (TextView) itemView.findViewById(R.id.tv_type1);
            tv_money1= (TextView) itemView.findViewById(R.id.tv_money1);
            tv_number= (TextView) itemView.findViewById(R.id.tv_number);
        }
    }
}
