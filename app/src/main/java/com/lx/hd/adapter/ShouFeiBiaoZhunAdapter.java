package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.ShouFeiBZBean;
import com.lx.hd.ui.activity.SuYunShouFeiActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */

public class ShouFeiBiaoZhunAdapter extends RecyclerView.Adapter<ShouFeiBiaoZhunAdapter.ViewHolder> {
    private Context context;
    private List<ShouFeiBZBean> list;

    public ShouFeiBiaoZhunAdapter(Context context, List<ShouFeiBZBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ShouFeiBiaoZhunAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shoufeibiaozhun_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShouFeiBiaoZhunAdapter.ViewHolder holder, int position) {
        holder.tv_fanwei.setText(list.get(position).getTimename());
        holder.tv_jg.setText("¥"+list.get(position).getDuring_time());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fanwei;
        private TextView tv_jg;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_jg = (TextView) itemView.findViewById(R.id.tv_jg);
            tv_fanwei = (TextView) itemView.findViewById(R.id.tv_fanwei);
        }
    }
}
