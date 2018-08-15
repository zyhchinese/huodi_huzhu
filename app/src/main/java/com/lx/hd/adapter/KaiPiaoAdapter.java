package com.lx.hd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.lx.hd.ui.activity.KaiPiaoActivity;
import com.lx.hd.ui.activity.KaiPiaoActivity1Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/2.
 */

public class KaiPiaoAdapter extends RecyclerView.Adapter<KaiPiaoAdapter.ViewHolder>{

    private Context context;
    private List<KaiPiaoEntity> list;
    private CheckBox img_xuanshang;
    private TextView tv_total_money;
    private Button btn_next;
    private int a=0;
    private double totalmoney=0.0;
    private String type;

    public KaiPiaoAdapter(Context context, List<KaiPiaoEntity> list, CheckBox img_xuanshang, TextView tv_total_money, Button btn_next, String type) {
        this.context=context;
        this.list=list;
        this.img_xuanshang=img_xuanshang;
        this.tv_total_money=tv_total_money;
        this.btn_next=btn_next;
        this.type=type;
    }

    @Override
    public KaiPiaoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.kaipiao_item, viewGroup, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final KaiPiaoAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tv_conent1.setText("起点："+list.get(i).getStartaddress()+"");
        viewHolder.tv_conent2.setText("终点："+list.get(i).getEndaddress()+"");
        viewHolder.tv_time.setText("行程时间："+list.get(i).getCreatetime()+"");
        viewHolder.tv_money.setText("¥"+list.get(i).getOrdertotalprice()+"元");


        if (img_xuanshang.isChecked()){
            viewHolder.img_xuanshang1.setChecked(true);
            viewHolder.img_xuanshang1.setButtonDrawable(R.mipmap.img_huodixuanze1);
        }else {
            viewHolder.img_xuanshang1.setChecked(false);
            viewHolder.img_xuanshang1.setButtonDrawable(R.mipmap.img_huodixuanze);
        }
        viewHolder.img_xuanshang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.img_xuanshang1.isChecked()){
                    list.get(i).setType(true);
                    viewHolder.img_xuanshang1.setButtonDrawable(R.mipmap.img_huodixuanze1);
                }else {
                    list.get(i).setType(false);
                    viewHolder.img_xuanshang1.setButtonDrawable(R.mipmap.img_huodixuanze);
                    img_xuanshang.setChecked(false);
                    img_xuanshang.setButtonDrawable(R.mipmap.img_huodixuanze);
                }

                a=0;
                totalmoney=0.0;

                for (KaiPiaoEntity entity:list){
                    if (entity.isType()){
                        a++;
                        totalmoney=totalmoney+entity.getOrdertotalprice();
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
                    for (KaiPiaoEntity entity:list){
                        entity.setType(true);
                        totalmoney=totalmoney+entity.getOrdertotalprice();
                    }
                }else {
                    totalmoney=0.0;
                    img_xuanshang.setButtonDrawable(R.mipmap.img_huodixuanze);
                    notifyDataSetChanged();
                    for (KaiPiaoEntity entity:list){
                        entity.setType(false);
                    }
                }
                tv_total_money.setText("共"+totalmoney+"元");
            }
        });

//        if (Double.parseDouble(tv_total_money.getText().toString().substring(1,tv_total_money.getText().toString().length()-2))>0){
//            btn_next.setClickable(true);
//        }else {
//            btn_next.setClickable(false);
//            Toast.makeText(context, "请至少选择一个发票", Toast.LENGTH_SHORT).show();
//        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = tv_total_money.getText().toString();
                if (Double.parseDouble(s.substring(1,s.length()-1))>0){
                    Intent intent=new Intent(context, KaiPiaoActivity1Activity.class);
                    intent.putExtra("money",s.substring(1,s.length()-1));
                    intent.putExtra("id",list.get(i).getId()+"");
                    intent.putExtra("ordertype",type);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "请选择开发票的单据", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_conent1,tv_conent2,tv_time,tv_money;
        private CheckBox img_xuanshang1;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_conent1= (TextView) itemView.findViewById(R.id.tv_conent1);
            tv_conent2= (TextView) itemView.findViewById(R.id.tv_conent2);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
            img_xuanshang1= (CheckBox) itemView.findViewById(R.id.img_xuanshang1);
        }
    }
}
