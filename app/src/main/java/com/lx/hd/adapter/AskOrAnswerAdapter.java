package com.lx.hd.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lx.hd.R;
import com.lx.hd.bean.PrimaryNews;

import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class AskOrAnswerAdapter extends BaseQuickAdapter<PrimaryNews, BaseViewHolder> {
    public AskOrAnswerAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PrimaryNews item) {
        helper.setText(R.id.text, item.getTitle());
     //   helper.setImageResource(R.id.icon, item.getImageResource());
        // 加载网络图片
     //   Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) viewHolder.getView(R.id.iv));
    }
}
