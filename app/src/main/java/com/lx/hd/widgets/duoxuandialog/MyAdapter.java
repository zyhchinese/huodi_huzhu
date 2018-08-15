package com.lx.hd.widgets.duoxuandialog;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lx.hd.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<ShuJuEntity> list;
    private boolean isOk;

    public MyAdapter(Context context, List<ShuJuEntity> list, boolean isOk) {
        this.context = context;
        this.list = list;
        this.isOk = isOk;

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
        holder.btn_province.setText(list.get(position).getName());
        holder.btn_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOk) {
                    for (ShuJuEntity shuJuEntity : list) {
                        shuJuEntity.setType(false);
                    }
                    list.get(position).setType(true);
                } else {

                    if (list.get(position).isType()) {
                        list.get(position).setType(false);
                    } else {
                        list.get(position).setType(true);
                    }
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
