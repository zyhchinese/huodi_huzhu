package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.ShangChengEntity;
import com.lx.hd.ui.activity.ShangChengActivity;

/**
 * Created by 赵英辉 on 2018/7/2.
 */

public class ShangChengLieBiaoAdapter extends RecyclerView.Adapter<ShangChengLieBiaoAdapter.ViewHolder> {

    private Context context;
    private ShangChengEntity.ResponseBean responseBean;
    private int line;
    private OnClickItem onClickItem;

    public ShangChengLieBiaoAdapter(Context context, ShangChengEntity.ResponseBean responseBean,int line) {
        this.context=context;
        this.responseBean=responseBean;
        this.line=line;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem{
        void onClick(int position);
    }

    @Override
    public ShangChengLieBiaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shangchengshouye_shangpin_item, parent, false);
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
    public void onBindViewHolder(ShangChengLieBiaoAdapter.ViewHolder holder, int position) {
        RequestOptions mOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.icon_default)
                .fitCenter();

        if (line==1){
            String folder = responseBean.getProdlistth().get(position).getFolder();
            String autoname = responseBean.getProdlistth().get(position).getAutoname();
            Glide.with(context).load(Constant.BASE_URL+responseBean.getProdlistth().get(position).getProdpic()).apply(mOptions).into(holder.img_icon);
            holder.tv_name.setText(responseBean.getProdlistth().get(position).getProname());
            holder.tv_price.setText("¥："+responseBean.getProdlistth().get(position).getPrice()+"  /  积："+responseBean.getProdlistth().get(position).getPrice_jf());
//            holder.tv_jifen_price.setText(""+responseBean.getProdlistth().get(position).getPrice_jf()+"积分");
        }else if (line==2){
            String folder = responseBean.getProdlistrm().get(position).getFolder();
            String autoname = responseBean.getProdlistrm().get(position).getAutoname();
            Glide.with(context).load(Constant.BASE_URL+responseBean.getProdlistrm().get(position).getProdpic()).apply(mOptions).into(holder.img_icon);
            holder.tv_name.setText(responseBean.getProdlistrm().get(position).getProname());
            holder.tv_price.setText("¥："+responseBean.getProdlistrm().get(position).getPrice()+"  /  积："+responseBean.getProdlistrm().get(position).getPrice_jf());
//            holder.tv_jifen_price.setText(""+responseBean.getProdlistrm().get(position).getPrice_jf()+"积分");
        }else {
            String folder = responseBean.getProdlistcnxh().get(position).getFolder();
            String autoname = responseBean.getProdlistcnxh().get(position).getAutoname();
            Glide.with(context).load(Constant.BASE_URL+responseBean.getProdlistcnxh().get(position).getProdpic()).apply(mOptions).into(holder.img_icon);
            holder.tv_name.setText(responseBean.getProdlistcnxh().get(position).getProname());
            holder.tv_price.setText("¥："+responseBean.getProdlistcnxh().get(position).getPrice()+"  /  积："+responseBean.getProdlistcnxh().get(position).getPrice_jf());
//            holder.tv_jifen_price.setText(""+responseBean.getProdlistcnxh().get(position).getPrice_jf()+"积分");
        }

    }

    @Override
    public int getItemCount() {
        if (line==1){
            if (responseBean.getProdlistth().size()>8){
                return 8;
            }else {
                return responseBean.getProdlistth().size();
            }

        }else if (line==2){
            if (responseBean.getProdlistrm().size()>8){
                return 8;
            }else {
                return responseBean.getProdlistrm().size();
            }
        } else {
            if (responseBean.getProdlistcnxh().size()>8){
                return 8;
            }else {
                return responseBean.getProdlistcnxh().size();
            }
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_icon;
        private TextView tv_name,tv_price,tv_jifen_price;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            img_icon= (ImageView) itemView.findViewById(R.id.img_icon);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_price= (TextView) itemView.findViewById(R.id.tv_price);
            tv_jifen_price= (TextView) itemView.findViewById(R.id.tv_jifen_price);
        }
    }
}
