package com.lx.hd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.lx.hd.R;
import com.lx.hd.bean.KaiPiaoEntity;
import com.lx.hd.bean.QianYueJieSuanEntity;
import com.lx.hd.ui.activity.KaiPiaoActivity1Activity;
import com.lx.hd.ui.activity.QianYueJieSuanActivity;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/5/18.
 */

public class QianYueJieSuanAdapter extends RecyclerView.Adapter<QianYueJieSuanAdapter.ViewHolder>{

    private Context context;
    private List<QianYueJieSuanEntity> list;
    private CheckBox img_xuanshang;
    private TextView tv_total_money;
    private Button btn_next;
    private int a=0;
    private double totalmoney=0.0;
    private ZhiFuChuang zhiFuChuang;
    private OnClickItem onClickItem;
    public QianYueJieSuanAdapter(Context context, List<QianYueJieSuanEntity> list, CheckBox img_xuanshang, TextView tv_total_money, Button btn_next) {
        this.context=context;
        this.list=list;
        this.img_xuanshang=img_xuanshang;
        this.tv_total_money=tv_total_money;
        this.btn_next=btn_next;
    }

    public void setZhiFuChuang(ZhiFuChuang zhiFuChuang) {
        this.zhiFuChuang = zhiFuChuang;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface ZhiFuChuang{
        void onClick(int position,String qian);
    }
    public interface OnClickItem{
        void onClick(int position);
    }

    @Override
    public QianYueJieSuanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.qianyuejiesuan_item, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem!=null){
                    onClickItem.onClick(viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QianYueJieSuanAdapter.ViewHolder holder, final int position) {
        holder.tv_dingdanhao.setText("订单号："+list.get(position).getOrderno()+"");
        holder.tv_money.setText("金额："+list.get(position).getMoney()+"元");
        holder.tv_name.setText("收货人："+list.get(position).getLinkname()+"");
        holder.tv_phone.setText("电话："+list.get(position).getLinkphone()+"");
        holder.tv_address.setText(""+list.get(position).getEaddress()+"");

        if (img_xuanshang.isChecked()){
            holder.img_xuanshang1.setChecked(true);
            holder.img_xuanshang1.setButtonDrawable(R.mipmap.img_huodixuanze1);
        }else {
            holder.img_xuanshang1.setChecked(false);
            holder.img_xuanshang1.setButtonDrawable(R.mipmap.img_huodixuanze);
        }
        holder.img_xuanshang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.img_xuanshang1.isChecked()){
                    list.get(position).setLine(true);
                    holder.img_xuanshang1.setButtonDrawable(R.mipmap.img_huodixuanze1);
                }else {
                    list.get(position).setLine(false);
                    holder.img_xuanshang1.setButtonDrawable(R.mipmap.img_huodixuanze);
                    img_xuanshang.setChecked(false);
                    img_xuanshang.setButtonDrawable(R.mipmap.img_huodixuanze);
                }

                a=0;
                totalmoney=0.0;

                for (QianYueJieSuanEntity entity:list){
                    if (entity.isLine()){
                        a++;
                        totalmoney=totalmoney+entity.getMoney();
                    }
                }
                tv_total_money.setText("共"+totalmoney+"元");

                if (a==list.size()){
                    img_xuanshang.setChecked(true);
                    img_xuanshang.setButtonDrawable(R.mipmap.img_huodixuanze1);
                }else {
                    img_xuanshang.setChecked(false);
                    img_xuanshang.setButtonDrawable(R.mipmap.img_huodixuanze);
                }
            }
        });
        img_xuanshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img_xuanshang.isChecked()){
                    img_xuanshang.setButtonDrawable(R.mipmap.img_huodixuanze1);
                    notifyDataSetChanged();
                    for (QianYueJieSuanEntity entity:list){
                        entity.setLine(true);
                        totalmoney=totalmoney+entity.getMoney();
                    }
                }else {
                    totalmoney=0.0;
                    img_xuanshang.setButtonDrawable(R.mipmap.img_huodixuanze);
                    notifyDataSetChanged();
                    for (QianYueJieSuanEntity entity:list){
                        entity.setLine(false);
                    }
                }
                tv_total_money.setText("共"+totalmoney+"元");
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = tv_total_money.getText().toString();
                if (Double.parseDouble(s.substring(1,s.length()-1))>0){
                    if (zhiFuChuang!=null){
                        zhiFuChuang.onClick(position,s.substring(1,s.length()-1));
                    }
                }else {
                    Toast.makeText(context, "请选择未结算订单", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_dingdanhao,tv_money,tv_name,tv_phone,tv_address;
        private CheckBox img_xuanshang1;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            tv_dingdanhao= (TextView) itemView.findViewById(R.id.tv_dingdanhao);
            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
            tv_address= (TextView) itemView.findViewById(R.id.tv_address);
            img_xuanshang1= (CheckBox) itemView.findViewById(R.id.img_xuanshang1);

        }
    }
}
