package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.SiJiJieHuoEntity;
import com.lx.hd.ui.activity.LogisticsOrderActivity;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class LogisticsJehuoAdapter extends RecyclerView.Adapter<LogisticsJehuoAdapter.ViewHolder> {

    private Context context;
    private List<SiJiJieHuoEntity> list;
    private int type;

    private OnClickItem onClickItem;
    private OnClickItem1 onClickItem1;
    private DecimalFormat decimalFormat=new DecimalFormat("###0.00");

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setOnClickItem1(OnClickItem1 onClickItem1) {
        this.onClickItem1 = onClickItem1;
    }

    public LogisticsJehuoAdapter(Context context, List<SiJiJieHuoEntity> list, int type) {

        this.context = context;
        this.list = list;
        this.type=type;
    }

    public interface OnClickItem {
        void onClick(int position);
    }
    public interface OnClickItem1{
        void onClick(int position);
    }

    @Override
    public LogisticsJehuoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_logistics_jehuo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final LogisticsJehuoAdapter.ViewHolder holder, int position) {

        //-1  已取消   0  前往中   1 开始订单   2  已完成
        holder.tv_dingdanhao.setText("订单号：" + list.get(position).getOwner_orderno());
        holder.tv_name.setText(list.get(position).getOwner_link_name());
        holder.tv_type.setText(list.get(position).getCar_type());
        holder.tv_address.setText(list.get(position).getOwner_address());
        holder.tv_address1.setText(list.get(position).getOwner_send_address());

        String format = decimalFormat.format(list.get(position).getSiji_money());
        holder.tv_money.setText("¥" + format);
        holder.tv_time.setText(list.get(position).getOwner_createtime());
        if (list.get(position).getDriver_orderstatus() == -1) {
            holder.img_status.setVisibility(View.VISIBLE);
            holder.tv_status.setVisibility(View.GONE);
            holder.img_status.setImageResource(R.mipmap.img_yiquxiao);
        } else if (list.get(position).getDriver_orderstatus() == 0) {
            holder.img_status.setVisibility(View.GONE);
            holder.tv_status.setVisibility(View.VISIBLE);
            holder.tv_status.setText("前往中");
        } else if (list.get(position).getDriver_orderstatus() == 1) {
            holder.img_status.setVisibility(View.GONE);
            holder.tv_status.setVisibility(View.VISIBLE);
            holder.tv_status.setText("开始订单");
        } else {
            holder.img_status.setVisibility(View.VISIBLE);
            holder.tv_status.setVisibility(View.GONE);
            holder.img_status.setImageResource(R.mipmap.img_yiwancheng);
        }

        holder.img_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem!=null){
                    onClickItem.onClick(holder.getAdapterPosition());
                }
            }
        });
        holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem1!=null){
                    onClickItem1.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int num=0;
        if (type==1){
            if (list.size()>3){
                num=3;
            }else {
                num=list.size();
            }
        }else {
            num=list.size();
        }
        return num;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_dingdanhao, tv_name, tv_type, tv_address, tv_address1, tv_money, tv_time,
                tv_status;
        private ImageView img_status, img_jiantou,img_call;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_dingdanhao = (TextView) itemView.findViewById(R.id.tv_dingdanhao);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_address1 = (TextView) itemView.findViewById(R.id.tv_address1);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            img_status = (ImageView) itemView.findViewById(R.id.img_status);
            img_jiantou = (ImageView) itemView.findViewById(R.id.img_jiantou);
            img_call = (ImageView) itemView.findViewById(R.id.img_call);
        }
    }
}
