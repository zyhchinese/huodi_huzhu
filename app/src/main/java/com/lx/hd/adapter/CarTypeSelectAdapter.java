package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.CarType;
import java.util.List;

public class CarTypeSelectAdapter extends RecyclerView.Adapter<CarTypeSelectAdapter.MyViewHolder>{
    List<CarType> carTypes;

    private Context mContext;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public CarTypeSelectAdapter(Context mContext,List<CarType> carTypes){
        this.mContext=mContext;
        this.carTypes=carTypes;
    }
    @Override
    public CarTypeSelectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        CarTypeSelectAdapter.MyViewHolder holder = new CarTypeSelectAdapter.MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_detial, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final CarTypeSelectAdapter.MyViewHolder holder, final int position)
    {

        holder.tv.setText(carTypes.get(position).getGroupname());
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return carTypes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv;
        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.name);
        }
    }
}

