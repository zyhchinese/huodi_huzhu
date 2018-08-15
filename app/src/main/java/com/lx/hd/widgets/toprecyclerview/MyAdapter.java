package com.lx.hd.widgets.toprecyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * 作者：fly on 2016/8/24 0024 23:34
 * 邮箱：cugb_feiyang@163.com
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private static List<OrderContent> list;//这个地方改了就行了----ItemOrderIn
    private LayoutInflater mIflater;
//    private OnItemClickLitener onItemClickListener;
    private static List<ItemOrderIn> itemOrderIns;
//    OnClickListener onClickListener;

//    /**
//     * 添加项点击事件
//     *
//     * @param onItemClickListener the RecyclerView item click listener
//     */
//    public void setOnItemClickListener(OnItemClickLitener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    public MyAdapter(Context mContext, List<OrderContent> list, List<ItemOrderIn> itemOrderIns){
        this.mContext = mContext;
        this.itemOrderIns = itemOrderIns;
        this.list = list;
//        initListener();
    }

    int times = 0;

    /**
     * 初始化listener
     */
//    private void initListener() {
//        onClickListener = new OnClickListener() {
//            @Override
//            public void onClick(int position, long itemId, ItemOrderIn orderContent) {
//                if (onItemClickListener != null)
//                    onItemClickListener.onItemClick(position, itemId, orderContent);//here
//            }
//        };
//        onClickListener=new OnClickListener() {
//            @Override
//            public void onClick(int position, long itemId, ItemOrderIn orderContent) {
//                if (onItemClickListener != null)
//                    onItemClickListener.onItemClick(position, itemId, orderContent);//here
//            }
//        };
//        onClickListener = new OnClickListener() {
//            @Override
//            public void onClick(int position, long itemId) {
//                if (onItemClickListener != null)
//                    onItemClickListener.onItemClick(position, itemId);
//            }
//        };

//    }
//
//    public interface OnItemClickLitener {
//        void onItemClick(int position, long itemId, ItemOrderIn orderContent);//这个里面随便放参数，怎么连数据源都没有。。。。。。。。。。。。。。。。，我得找到数据源穿进去
//
//    }

//    /**
//     * 可以共用同一个listener，相对高效
//     */
//    public static abstract class OnClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
//            onClick(holder.getAdapterPosition(), holder.getItemId(), itemOrderIns.get(holder.getAdapterPosition()));//这里给他的数据
//
//        }

//        public abstract void onClick(int position, long itemId, ItemOrderIn orderContent);
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder =
                new MyViewHolder(list.get(viewType).getView(mContext, parent, mIflater));
        times++;
        Log.d("MyAdapter", "times:" + times);
        if (holder != null) {
            holder.itemView.setTag(holder);
//            holder.itemView.setOnClickListener(onClickListener);
        }

        return holder;
    }

    /**
     * 每一个位置的item都作为单独一项来设置
     * viewType 设置为position
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

//        OrderContent content = list.get(position);
//        if(content instanceof ItemOrderTop){
//            return 0;
//        }
//        if(content instanceof ItemOrderBottom){
//            return 1;
//        }
//        return 2;
        return position;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 更新数据
     *
     * @param orderContents
     */
    public void updateList(List<OrderContent> orderContents) {
        this.list = orderContents;
        this.notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clearList() {
        this.list.clear();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
