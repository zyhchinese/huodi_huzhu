package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.CarInfoEntity;
import com.lx.hd.ui.activity.CarAudit1Activity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class RecyclerViewCarAdapter extends RecyclerView.Adapter<RecyclerViewCarAdapter.ViewHolder>{

    private Context context;
    private List<CarInfoEntity> list;
    private OnClickItem onClickItem;
    private OnClickItemHang onClickItemHang;

    public void setOnClickItemHang(OnClickItemHang onClickItemHang) {
        this.onClickItemHang = onClickItemHang;
    }

    public RecyclerViewCarAdapter(Context context, List<CarInfoEntity> list) {
        this.context=context;
        this.list=list;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem{
        void onClick(int position);
    }
    public interface OnClickItemHang{
        void onClick(int position);
    }

    @Override
    public RecyclerViewCarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_cars, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemHang!=null){
                    onClickItemHang.onClick(viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewCarAdapter.ViewHolder holder, int position) {
        holder.tv_car_name.setText(list.get(position).getCar_name());
        Glide.with(context).load(Constant.BASE_URL+list.get(position).getFolder()+list.get(position).getAutoname()).into(holder.img_car);
        holder.tv_type.setText(list.get(position).getTypename());
        holder.tv_xuhang.setText("续航："+list.get(position).getCar_extension_mileage()+"km");
        holder.tv_dianliang.setText("电量："+list.get(position).getCar_electricity());
        holder.tv_chicun.setText("尺寸："+list.get(position).getOutside_size());
        if (String.valueOf((int)(list.get(position).getMoon_price())).length()==4){
            double v = list.get(position).getMoon_price() / 1000;
            if ((int)(list.get(position).getMoon_price())%1000==0){
                holder.tv_price.setText("¥"+(int)(list.get(position).getMoon_price())/1000+"K");
            }else {
                holder.tv_price.setText("¥"+v+"K");
            }
        }else if (String.valueOf((int)(list.get(position).getMoon_price())).length()==5){
            double v = list.get(position).getMoon_price() / 10000;
            if ((int)(list.get(position).getMoon_price())%10000==0){
                holder.tv_price.setText("¥"+(int)(list.get(position).getMoon_price())/10000+"W");
            }else {
                holder.tv_price.setText("¥"+v+"W");
            }
        }else {
            holder.tv_price.setText("¥"+(int)(list.get(position).getMoon_price()));
        }

        if (String.valueOf((int)(list.get(position).getYear_price())).length()==4){
            double v = list.get(position).getYear_price() / 1000;
            if ((int)(list.get(position).getYear_price())%1000==0){
                holder.tv_price1.setText("¥"+(int)(list.get(position).getYear_price())/1000+"K");
            }else {
                holder.tv_price1.setText("¥"+v+"K");
            }

        }else if (String.valueOf((int)(list.get(position).getYear_price())).length()==5){
            double v = list.get(position).getYear_price() / 10000;
            if ((int)(list.get(position).getYear_price())%10000==0){
                holder.tv_price1.setText("¥"+(int)(list.get(position).getYear_price())/10000+"W");
            }else {
                holder.tv_price1.setText("¥"+v+"W");
            }
        }else {
            holder.tv_price1.setText("¥"+(int)(list.get(position).getYear_price()));
        }
//        holder.tv_price1.setText("¥"+(int)(list.get(position).getMoon_price()));
//        holder.tv_price1.setText("¥"+(int)(list.get(position).getYear_price()));
        holder.tv_xiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem!=null){
                    onClickItem.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_car_name,tv_xiangqing,tv_type,tv_xuhang,tv_dianliang,tv_chicun,tv_price,tv_price1;
        private ImageView img_car;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            tv_car_name= (TextView) itemView.findViewById(R.id.tv_car_name);
            tv_xiangqing= (TextView) itemView.findViewById(R.id.tv_xiangqing);
            img_car= (ImageView) itemView.findViewById(R.id.img_car);
            tv_type= (TextView) itemView.findViewById(R.id.tv_type);
            tv_xuhang= (TextView) itemView.findViewById(R.id.tv_xuhang);
            tv_dianliang= (TextView) itemView.findViewById(R.id.tv_dianliang);
            tv_chicun= (TextView) itemView.findViewById(R.id.tv_chicun);
            tv_price= (TextView) itemView.findViewById(R.id.tv_price);
            tv_price1= (TextView) itemView.findViewById(R.id.tv_price1);
        }
    }
}
