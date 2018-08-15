package com.lx.hd.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.ShoppingEntity;
import com.lx.hd.ui.activity.ConfirmOrderActivity;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/10/20.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private Context context;
    private List<ShoppingEntity> list;
    private String type;

    public OrderAdapter(Context context, List<ShoppingEntity> list, String type) {
        this.context=context;
        this.list=list;
        this.type=type;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        if (type.equals("1")){
            holder.tv_money.setText("¥");
            holder.tv_money1.setText(String.valueOf(list.get(position).getPrice())+"  /  积分"+String.valueOf(list.get(position).getPrice_jf()));
        }else {
            holder.tv_money1.setText("积分");
            holder.tv_money.setText(String.valueOf(list.get(position).getPrice()));
        }
        if (!list.get(position).getAutoname().equals("")&&!list.get(position).getFolder().equals("")) {
            Glide.with(context).load(Constant.BASE_URL +list.get(position).getFolder()+ list.get(position).getAutoname()).into(holder.img_shangpin);
        }else {
            holder.img_shangpin.setImageResource(R.mipmap.icon_default);
        }

        holder.tv_name.setText(list.get(position).getProname());
        holder.tv_name1.setText(list.get(position).getProductdes());
        holder.tv_color.setText(list.get(position).getColor());
        holder.tv_type1.setText(list.get(position).getSpecification());
//        holder.tv_money1.setText(String.valueOf(list.get(position).getPrice()));
        holder.tv_number.setText(String.valueOf(list.get(position).getCount()));
        holder.tv_price.setText(String.valueOf(list.get(position).getPrice_jf()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_shangpin;
        private TextView tv_name,tv_name1,tv_color,tv_type1,tv_money1,tv_number,tv_money;
        private TextView tv_price;
        public ViewHolder(View itemView) {
            super(itemView);
            img_shangpin= (ImageView) itemView.findViewById(R.id.img_shangpin);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_name1= (TextView) itemView.findViewById(R.id.tv_name1);
            tv_color= (TextView) itemView.findViewById(R.id.tv_color);
            tv_type1= (TextView) itemView.findViewById(R.id.tv_type1);
            tv_money1= (TextView) itemView.findViewById(R.id.tv_money1);
            tv_number= (TextView) itemView.findViewById(R.id.tv_number);
            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
            tv_price= (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}
