package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.base.fragment.BaseRecyclerAdapter;
import com.lx.hd.bean.PrimaryNews;


public class NewsListAdapter extends BaseRecyclerAdapter<PrimaryNews> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack,
        BaseRecyclerAdapter.OnLoadingFooterCallBack{
    RequestOptions mOptions;
    public NewsListAdapter(Context context, int mode) {
        super(context, mode);
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.icon_default)
                .fitCenter();
        setOnLoadingHeaderCallBack(this);
        setOnLoadingFooterCallBack(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new HeaderViewHolder(mHeaderView);
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new NewsViewHolder(mInflater.inflate(R.layout.item_main_recycler1, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final PrimaryNews item, int position) {
        final NewsViewHolder vh = (NewsViewHolder) holder;
        if(position==1){
            vh.viewLine.setVisibility(View.GONE);
        }else {
            vh.viewLine.setVisibility(View.VISIBLE);
        }
        if(item!=null){
            Glide.with(mContext)
                    .load(Constant.BASE_URL+item.getFolder()+item.getAutoname())
                    .apply(mOptions)
                    .into(vh.imgIcon);
            vh.tvTitle.setText(item.getTitle());
            vh.tvContent.setText(item.getContent());
            vh.tvCreateTime.setText(item.getCreatetime());

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterHolder(ViewGroup parent) {
        return new FooterViewHolder(mFooterView);
    }

    @Override
    public void onBindFooterHolder(RecyclerView.ViewHolder holder, int position) {

    }

    private static class NewsViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgIcon;
        public TextView tvTitle, tvContent, tvCreateTime;
        public View viewLine;
        public NewsViewHolder(View itemView) {
            super(itemView);
            imgIcon=(ImageView)itemView.findViewById(R.id.img_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_conent);
            tvCreateTime = (TextView) itemView.findViewById(R.id.tv_time);
            viewLine =itemView.findViewById(R.id.view_lines);
        }
    }
}
