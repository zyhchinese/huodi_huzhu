package com.lx.hd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.KaiPiaoLiShiEntity;
import com.lx.hd.ui.activity.KaiPiaoLiShiActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class KaiPiaoLiShiAdapter extends RecyclerView.Adapter<KaiPiaoLiShiAdapter.ViewHolder>{
    private Context context;
    private List<KaiPiaoLiShiEntity> list;
    public KaiPiaoLiShiAdapter(Context context, List<KaiPiaoLiShiEntity> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public KaiPiaoLiShiAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.kaipiaolishi_item, viewGroup, false);
        ViewHolder viewHolder=new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(KaiPiaoLiShiAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tv_time.setText(list.get(i).getCreatetime());
        if (list.get(i).getSpstatus()==0){
            viewHolder.tv_type.setTextColor(Color.BLACK);
            viewHolder.tv_type.setText("审核中");
        }else if (list.get(i).getSpstatus()==1){
            viewHolder.tv_type.setTextColor(Color.GREEN);
            viewHolder.tv_type.setText("审核通过");
        }else {
            viewHolder.tv_type.setTextColor(Color.RED);
            viewHolder.tv_type.setText("审核拒绝");
        }
        viewHolder.tv_money.setText(list.get(i).getInvoicemoney()+"");
        if (list.get(i).getInvoicetype()==0){
            viewHolder.tv_qiye_type.setText("企业发票");
        }else {
            viewHolder.tv_qiye_type.setText("个人非企业发票");
        }
        if (list.get(i).getTypeorder()==0){
            viewHolder.tv_fapiao_type.setText("同城小件");
        }else if (list.get(i).getTypeorder()==1){
            viewHolder.tv_fapiao_type.setText("搬家订单");
        }else {
            viewHolder.tv_fapiao_type.setText("快运订单");
        }
        viewHolder.tv_beizhu.setText(list.get(i).getNote());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_time,tv_type,tv_money,tv_qiye_type,tv_fapiao_type,tv_beizhu;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_type= (TextView) itemView.findViewById(R.id.tv_type);
            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
            tv_qiye_type= (TextView) itemView.findViewById(R.id.tv_qiye_type);
            tv_fapiao_type= (TextView) itemView.findViewById(R.id.tv_fapiao_type);
            tv_beizhu= (TextView) itemView.findViewById(R.id.tv_beizhu);
        }
    }
}
