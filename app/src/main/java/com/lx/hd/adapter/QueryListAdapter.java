package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.QueryListEntity;
import com.lx.hd.ui.activity.AddressActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class QueryListAdapter extends RecyclerView.Adapter<QueryListAdapter.ViewHolder>{

    private Context context;
    private List<QueryListEntity> queryList;
    private int line;
    private ClickChildItem1 clickChildItem;
    private ClickChildItem2 clickChildItem2;
    private ClickChildItem3 clickChildItem3;
    private ClickChildItem4 clickChildItem4;

    public void setClickChildItem4(ClickChildItem4 clickChildItem4) {
        this.clickChildItem4 = clickChildItem4;
    }

    public void setClickChildItem3(ClickChildItem3 clickChildItem3) {
        this.clickChildItem3 = clickChildItem3;
    }

    public void setClickChildItem2(ClickChildItem2 clickChildItem2) {
        this.clickChildItem2 = clickChildItem2;
    }

    public void setClickChildItem(ClickChildItem1 clickChildItem) {
        this.clickChildItem = clickChildItem;
    }

    public QueryListAdapter(Context context, List<QueryListEntity> queryList, int line) {
        this.context=context;
        this.queryList=queryList;
        this.line=line;
    }

    //默认地址
    public interface ClickChildItem1{
        void onClick(int position);
    }
    //删除地址
    public interface ClickChildItem2{
        void onClick(int position);
    }
    //更新地址
    public interface ClickChildItem3{
        void onClick(int position);
    }
    //选择当前地址
    public interface ClickChildItem4{
        void onClick(int position,String name,String number,String address);
    }


    @Override
    public QueryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item2, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QueryListAdapter.ViewHolder holder, final int position) {
        if (line==1){
            holder.rb1.setVisibility(View.VISIBLE);
            holder.tv_now_address.setVisibility(View.VISIBLE);
        }else {
            holder.rb1.setVisibility(View.GONE);
            holder.tv_now_address.setVisibility(View.GONE);
        }
        holder.tv_name.setText(queryList.get(position).getShcustname());
        holder.tv_address.setText(queryList.get(position).getAddress());
        holder.tv_phone.setText(queryList.get(position).getShphone());
        if (queryList.get(position).getIsdefault()==1){

            holder.checkbox1.setChecked(true);
            holder.rb1.setChecked(true);

        }else {
            holder.checkbox1.setChecked(false);
            holder.rb1.setChecked(false);
        }

        holder.checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickChildItem.onClick(holder.getAdapterPosition());
            }

        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickChildItem2.onClick(holder.getAdapterPosition());
            }
        });

        holder.img_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickChildItem3.onClick(holder.getAdapterPosition());
            }
        });
        holder.rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickChildItem4.onClick(holder.getAdapterPosition(),queryList.get(position).getShcustname(),queryList.get(position).getShphone(),queryList.get(position).getAddress());
            }
        });


    }

    @Override
    public int getItemCount() {
        return queryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_address,tv_phone,tv_now_address;
        private CheckBox checkbox1;
        private RadioButton rb1;
        private ImageView img_write,img_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_address= (TextView) itemView.findViewById(R.id.tv_address);
            tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
            checkbox1= (CheckBox) itemView.findViewById(R.id.checkbox1);
            img_write= (ImageView) itemView.findViewById(R.id.img_write);
            img_delete= (ImageView) itemView.findViewById(R.id.img_delete);
            rb1= (RadioButton) itemView.findViewById(R.id.rb1);
            tv_now_address= (TextView) itemView.findViewById(R.id.tv_now_address);
        }
    }
}
