package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.base.fragment.BaseRecyclerAdapter;
import com.lx.hd.bean.User;

/**
 * Created by Administrator on 2017/8/22.
 */

public class TestAdapter extends BaseRecyclerAdapter<User> {
    public TestAdapter(Context context) {
        super(context, BOTH_HEADER_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_primary, parent, false);
        return new CommentHolder(view);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, User item, int position) {

        if (holder instanceof CommentHolder) {
            CommentHolder commentHolder = (CommentHolder) holder;
            commentHolder.mName.setVisibility(View.GONE);

        }
    }

    private static class CommentHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mDate;


        CommentHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.title_tv);
            mDate = (TextView) itemView.findViewById(R.id.title_tv);

        }
    }
}
