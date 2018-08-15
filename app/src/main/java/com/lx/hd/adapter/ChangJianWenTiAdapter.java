package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.ChangJianWenTiEntity;
import com.lx.hd.ui.activity.ChangJianWenTiActivity;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/6/25.
 */

public class ChangJianWenTiAdapter extends RecyclerView.Adapter<ChangJianWenTiAdapter.ViewHolder>{
    private Context context;
    private List<ChangJianWenTiEntity.ResponseBean> list;
    private OnClickItem onClickItem;
    public ChangJianWenTiAdapter(Context context, List<ChangJianWenTiEntity.ResponseBean> list) {
        this.context=context;
        this.list=list;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem{
        void onClick(int position);
    }

    @Override
    public ChangJianWenTiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.changjianwenti_item, parent, false);
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
    public void onBindViewHolder(ChangJianWenTiAdapter.ViewHolder holder, int position) {

        holder.tv_wenti.setText(position+1+".  "+list.get(position).getProblem());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_wenti;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            tv_wenti= (TextView) itemView.findViewById(R.id.tv_wenti);
        }
    }
}
