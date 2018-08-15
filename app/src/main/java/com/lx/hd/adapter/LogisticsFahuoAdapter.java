package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.WuLiuFaHuoEntity;
import com.lx.hd.ui.activity.LogisticsOrderActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class LogisticsFahuoAdapter extends RecyclerView.Adapter<LogisticsFahuoAdapter.ViewHolder> {

    private Context context;
    private List<WuLiuFaHuoEntity> list;
    private int type;
    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public LogisticsFahuoAdapter(Context context, List<WuLiuFaHuoEntity> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    public interface OnClickItem {
        void onClick(int position);
    }

    @Override
    public LogisticsFahuoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_logistics_fahuo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final LogisticsFahuoAdapter.ViewHolder holder, int position) {

        //-1已取消   0等待接货   1服务开始   2已完成

        holder.tv_dingdanhao.setText("订单号：" + list.get(position).getOwner_orderno());
        holder.tv_car_type.setText(list.get(position).getCar_type());
        holder.tv_money.setText("¥" + list.get(position).getOwner_totalprice());
        holder.tv_time.setText(list.get(position).getOwner_createtime());

        if (list.get(position).getCust_orderstatus() == -1) {
            holder.img_status.setVisibility(View.VISIBLE);
            holder.tv_status.setVisibility(View.GONE);
            holder.img_status.setImageResource(R.mipmap.img_yiquxiao);
        } else if (list.get(position).getCust_orderstatus() == 0) {
            holder.img_status.setVisibility(View.GONE);
            holder.tv_status.setVisibility(View.VISIBLE);
            holder.tv_status.setText("等待接货");
        } else if (list.get(position).getCust_orderstatus() == 1) {
            holder.img_status.setVisibility(View.GONE);
            holder.tv_status.setVisibility(View.VISIBLE);
            holder.tv_status.setText("服务开始");
        } else {
            holder.img_status.setVisibility(View.VISIBLE);
            holder.tv_status.setVisibility(View.GONE);
            holder.img_status.setImageResource(R.mipmap.img_yiwancheng);
        }

        holder.img_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onClickItem != null) {
                    onClickItem.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int num=0;
        if (type == 1) {
            if (list.size() > 3) {
                num = 3;
            } else {
                num = list.size();
            }
        } else {
            num = list.size();
        }
        return num;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_dingdanhao, tv_car_type, tv_money, tv_time, tv_status;
        private ImageView img_jiantou, img_status;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_dingdanhao = (TextView) itemView.findViewById(R.id.tv_dingdanhao);
            tv_car_type = (TextView) itemView.findViewById(R.id.tv_car_type);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            img_jiantou = (ImageView) itemView.findViewById(R.id.img_jiantou);
            img_status = (ImageView) itemView.findViewById(R.id.img_status);
        }
    }
}
