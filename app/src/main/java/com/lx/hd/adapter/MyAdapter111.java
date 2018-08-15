package com.lx.hd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lx.hd.R;
import com.lx.hd.bean.cxbean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class MyAdapter111 extends RecyclerView.Adapter<MyAdapter111.ViewHolder> {

    private Context context;
    private List<cxbean> list;

    public MyAdapter111(Context context, List<cxbean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public MyAdapter111.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter111.ViewHolder holder, final int position) {
        holder.btn_province.setText(list.get(position).getCartypename());
        holder.btn_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (list.get(position).isType()) {
                        list.get(position).setType(false);
                    } else {
                        list.get(position).setType(true);
                    }

                notifyDataSetChanged();

            }
        });

        if (list.get(position).isType()) {
            holder.btn_province.setBackgroundResource(R.drawable.bg_orovince_bg);
            holder.btn_province.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.btn_province.setBackgroundResource(R.drawable.bg_orovince_bg_f);
            holder.btn_province.setTextColor(Color.parseColor("#6b6b6b"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn_province;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_province = (Button) itemView.findViewById(R.id.btn_province);
        }
    }
}
