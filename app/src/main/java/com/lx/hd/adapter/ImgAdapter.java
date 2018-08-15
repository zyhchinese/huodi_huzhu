package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.BannerDetailsEntity;
import com.lx.hd.ui.activity.BannerDetailsActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder> {

    private Context context;
    private List<BannerDetailsEntity> detailsEntityList;

    public ImgAdapter(Context context, List<BannerDetailsEntity> detailsEntityList) {
        this.context = context;
        this.detailsEntityList = detailsEntityList;
    }

    @Override
    public ImgAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item4, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImgAdapter.ViewHolder holder, int position) {
        if (!detailsEntityList.get(position).getFolder().equals("")&&!detailsEntityList.get(position).getAutoname().equals("")) {
            Glide.with(context).load(Constant.BASE_URL + detailsEntityList.get(position).getFolder() + detailsEntityList.get(position).getAutoname()).into(holder.img1);
        }else {
            holder.img1.setImageResource(R.mipmap.icon_default);
        }

    }

    @Override
    public int getItemCount() {
        return detailsEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img1;

        public ViewHolder(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
        }
    }
}
