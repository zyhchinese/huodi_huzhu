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
import com.lx.hd.bean.ErShouCheShouYeLieBiaoEntity;
import com.lx.hd.bean.WoDeErShouCheLieBiaoEntity;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/7/13.
 */

public class ErShouCheShouYeLieBiaoAdapter111 extends RecyclerView.Adapter<ErShouCheShouYeLieBiaoAdapter111.ViewHolder>{

    private Context context;
    private List<WoDeErShouCheLieBiaoEntity.ResponseBean> response;
    private OnClickItem onClickItem;

    public ErShouCheShouYeLieBiaoAdapter111(Context context, List<WoDeErShouCheLieBiaoEntity.ResponseBean> response) {
        this.context=context;
        this.response=response;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem{
        void onClick(int position);
    }

    @Override
    public ErShouCheShouYeLieBiaoAdapter111.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ershoucheshouye_shangpin_item, parent, false);
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
    public void onBindViewHolder(ErShouCheShouYeLieBiaoAdapter111.ViewHolder holder, int position) {
        RequestOptions mOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.icon_default)
                .fitCenter();
        String erscarpic = response.get(position).getTwoscarpic();
        Glide.with(context).load(Constant.BASE_URL+erscarpic).apply(mOptions).into(holder.img_icon);
        holder.tv_name.setText(response.get(position).getTitle());
        holder.tv_price.setText(response.get(position).getPrice());
        holder.tv_info.setText(response.get(position).getRegion());
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_icon;
        private TextView tv_name,tv_price,tv_info;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            img_icon= (ImageView) itemView.findViewById(R.id.img_icon);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_price= (TextView) itemView.findViewById(R.id.tv_price);
            tv_info= (TextView) itemView.findViewById(R.id.tv_info);

        }
    }
}
