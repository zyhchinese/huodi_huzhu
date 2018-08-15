package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.base.fragment.BaseRecyclerAdapter;
import com.lx.hd.bean.ChargeOrder;
import com.lx.hd.bean.TiXianStory;


public class TiXIanStoryAdapter extends BaseRecyclerAdapter<TiXianStory> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack {

    public TiXIanStoryAdapter(Context context, int mode) {
        super(context, mode);

        setOnLoadingHeaderCallBack(this);
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
        return new NewsViewHolder(mInflater.inflate(R.layout.item_tixian_story, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final TiXianStory item, int position) {
        final NewsViewHolder vh = (NewsViewHolder) holder;
        if(item!=null){
            vh.tvApplyMoney.setText(item.getMoney());
            vh.tvApplyTime.setText(item.getCreatetime());
            if(item.getNote().length()==0||item.getNote()==null){

            }else {
                vh.tvApplyReson.setText(item.getNote());
            }

            if(item.getIsvalid().equals("0")){
                vh.tvShenHeState.setText("待审核");
                vh.tvShenHeState.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }else if(item.getIsvalid().equals("1")){
                vh.tvShenHeState.setText("审核通过");
                vh.tvShenHeState.setTextColor(mContext.getResources().getColor(R.color.account_pressed_true));
            }else if(item.getIsvalid().equals("2")){
                vh.tvShenHeState.setText("审核拒绝");
                vh.tvShenHeState.setTextColor(mContext.getResources().getColor(R.color.swiperefresh_color3));
            }else {
                vh.tvShenHeState.setText("审核状态未知");
            }

        }
    }
    private static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvApplyMoney,tvApplyTime,tvApplyReson,tvShenHeState;
        public NewsViewHolder(View itemView) {
            super(itemView);
            tvApplyMoney=(TextView)itemView.findViewById(R.id.tv_apply_money);
            tvApplyTime=(TextView)itemView.findViewById(R.id.tv_apply_time);
            tvApplyReson=(TextView)itemView.findViewById(R.id.tv_apply_reson);
            tvShenHeState=(TextView)itemView.findViewById(R.id.tv_shenhe_state);
        }
    }
}
