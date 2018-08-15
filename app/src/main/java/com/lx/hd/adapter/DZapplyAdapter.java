package com.lx.hd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.DZapplyEntity;
import com.lx.hd.ui.activity.DZapplyActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/28.
 */

public class DZapplyAdapter extends RecyclerView.Adapter<DZapplyAdapter.ViewHolder>{

    private Context context;
    private List<DZapplyEntity> dZapplyEntityList;

    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public DZapplyAdapter(Context context, List<DZapplyEntity> dZapplyEntityList) {
        this.context=context;
        this.dZapplyEntityList=dZapplyEntityList;
    }

    public interface OnClickItem{
        void onClick(int position);
    }

    @Override
    public DZapplyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item5, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DZapplyAdapter.ViewHolder holder, int position) {
        holder.tv_car.setText(dZapplyEntityList.get(position).getCustname());
        if (dZapplyEntityList.get(position).getStatus()==0){
            holder.img_dian.setImageResource(R.mipmap.img_user_y);
            holder.tv_yue_name.setImageResource(R.mipmap.img_address_y);
            holder.tv_daishenhe.setText("待审核");
            holder.tv_daishenhe.setTextColor(Color.YELLOW);


        }else if (dZapplyEntityList.get(position).getStatus()==1){
            holder.img_dian.setImageResource(R.mipmap.img_user_g);
            holder.tv_yue_name.setImageResource(R.mipmap.img_address_g);
            holder.tv_daishenhe.setText("审核通过");
            holder.tv_daishenhe.setTextColor(Color.GREEN);

        }else {
            holder.img_dian.setImageResource(R.mipmap.img_user_r);
            holder.tv_yue_name.setImageResource(R.mipmap.img_address_r);
            holder.tv_daishenhe.setText("审核未通过");
            holder.tv_daishenhe.setTextColor(Color.RED);
        }
        holder.tv_name.setText(dZapplyEntityList.get(position).getAddress());
        holder.tv_phone.setText(dZapplyEntityList.get(position).getCustphone());
        holder.tv_zhushi.setText(dZapplyEntityList.get(position).getNote());
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClick(holder.getAdapterPosition());
            }
        });



    }

    @Override
    public int getItemCount() {
        return dZapplyEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_car,tv_daishenhe,tv_name,tv_phone,tv_zhushi;
        private ImageView tv_yue_name,img_dian,img_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_car= (TextView) itemView.findViewById(R.id.tv_car);
            tv_daishenhe= (TextView) itemView.findViewById(R.id.tv_daishenhe);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
            tv_zhushi= (TextView) itemView.findViewById(R.id.tv_zhushi);
            tv_yue_name= (ImageView) itemView.findViewById(R.id.tv_yue_name);
            img_dian= (ImageView) itemView.findViewById(R.id.img_dian);
            img_delete= (ImageView) itemView.findViewById(R.id.img_delete);
        }
    }
}
