package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lx.hd.R;
import com.lx.hd.bean.LiShiXiaoXiEntity;
import com.lx.hd.ui.activity.LiShiXiaoXiActivity;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/6/22.
 */

public class LiShiXiaoXiAdapter extends RecyclerView.Adapter<LiShiXiaoXiAdapter.ViewHolder>{

    private Context context;
    private List<LiShiXiaoXiEntity.ResponseBean> list;
    private TextView tv_guanli;
    private RelativeLayout relative_quanxuan;
    private CheckBox checkbox_quanxuan;
    private TextView tv_delete;
    private OnClickItem onClickItem;
    private OnClickZhi onClickZhi;
    private boolean guanli=true;
    private int a=0;


    public LiShiXiaoXiAdapter(Context context, List<LiShiXiaoXiEntity.ResponseBean> list, TextView tv_guanli, RelativeLayout relative_quanxuan, CheckBox checkbox_quanxuan, TextView tv_delete) {
        this.context=context;
        this.list=list;
        this.tv_guanli=tv_guanli;
        this.relative_quanxuan=relative_quanxuan;
        this.checkbox_quanxuan=checkbox_quanxuan;
        this.tv_delete=tv_delete;
    }
    public void addData(List<LiShiXiaoXiEntity.ResponseBean> list){
        this.list=list;
        notifyDataSetChanged();
        checkbox_quanxuan.setChecked(false);
        checkbox_quanxuan.setButtonDrawable(R.mipmap.img_huodishanchuweixuan);
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setOnClickZhi(OnClickZhi onClickZhi) {
        this.onClickZhi = onClickZhi;
    }

    public interface OnClickItem{
        void onClick(int position);
    }
    public interface OnClickZhi{
        void onClick();
    }

    @Override
    public LiShiXiaoXiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lishixiaoxi_item, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guanli){
                    if (onClickItem!=null){
                        onClickItem.onClick(viewHolder.getAdapterPosition());
                    }
                }else {
                    if (onClickZhi!=null){
                        onClickZhi.onClick();
                    }
                }

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final LiShiXiaoXiAdapter.ViewHolder holder, final int position) {
        if (!guanli){
            holder.checkbox_danxuan.setVisibility(View.VISIBLE);
        }else {
            holder.checkbox_danxuan.setVisibility(View.GONE);
        }
        //通知消息
        if (list.get(position).getMark()==0){
            if (list.get(position).getIssee()==0){
                holder.img_icon.setImageResource(R.mipmap.img_huoditongzhiweidu2);
            }else {
                holder.img_icon.setImageResource(R.mipmap.img_huoditongzhiyidu);
            }
        //物流消息
        }else if (list.get(position).getMark()==1){
            if (list.get(position).getIssee()==0){
                holder.img_icon.setImageResource(R.mipmap.img_huodijiaoyiweidu);
            }else {
                holder.img_icon.setImageResource(R.mipmap.img_huodijiaoyiyidu);
            }
        //商城消息
        }else if (list.get(position).getMark()==2){
            if (list.get(position).getIssee()==0){
                holder.img_icon.setImageResource(R.mipmap.img_huoditongzhiweidu2);
            }else {
                holder.img_icon.setImageResource(R.mipmap.img_huoditongzhiyidu);
            }
        //互动消息
        }else if (list.get(position).getMark()==3){
            if (list.get(position).getIssee()==0){
                holder.img_icon.setImageResource(R.mipmap.img_huodihudongweidu2);
            }else {
                holder.img_icon.setImageResource(R.mipmap.img_huodihudongyidu);
            }
        }
        holder.tv_type.setText(list.get(position).getTitle());
        holder.tv_content.setText(list.get(position).getContent());
        holder.tv_time.setText(list.get(position).getCreate_time());
        if (list.get(position).getIssee()==0){
            holder.img_weidu.setVisibility(View.VISIBLE);
        }else {
            holder.img_weidu.setVisibility(View.GONE);
        }

        if (checkbox_quanxuan.isChecked()){
            holder.checkbox_danxuan.setChecked(true);
            holder.checkbox_danxuan.setButtonDrawable(R.mipmap.img_huodishanchuxuanze);
        }else {
            holder.checkbox_danxuan.setChecked(false);
            holder.checkbox_danxuan.setButtonDrawable(R.mipmap.img_huodishanchuweixuan);
        }


        tv_guanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guanli){
                    guanli=false;
                    tv_guanli.setText("完成");
//                    holder.checkbox_danxuan.setVisibility(View.VISIBLE);
                    relative_quanxuan.setVisibility(View.VISIBLE);
                }else {
                    guanli=true;
                    tv_guanli.setText("管理");
//                    holder.checkbox_danxuan.setVisibility(View.GONE);
                    relative_quanxuan.setVisibility(View.GONE);
                }
                notifyDataSetChanged();

            }
        });

        holder.checkbox_danxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkbox_danxuan.isChecked()){
                    list.get(position).setLine(true);
                    holder.checkbox_danxuan.setButtonDrawable(R.mipmap.img_huodishanchuxuanze);
                }else {
                    list.get(position).setLine(false);
                    holder.checkbox_danxuan.setButtonDrawable(R.mipmap.img_huodishanchuweixuan);
                    checkbox_quanxuan.setChecked(false);
                    checkbox_quanxuan.setButtonDrawable(R.mipmap.img_huodishanchuweixuan);
                }

                a=0;
                for (LiShiXiaoXiEntity.ResponseBean entity:list){
                    if (entity.isLine()){
                        a++;
                    }
                }
                if (a==list.size()){
                    checkbox_quanxuan.setChecked(true);
                    checkbox_quanxuan.setButtonDrawable(R.mipmap.img_huodishanchuxuanze);
                }else {
                    checkbox_quanxuan.setChecked(false);
                    checkbox_quanxuan.setButtonDrawable(R.mipmap.img_huodishanchuweixuan);
                }

            }
        });

//        setOnClickZhi(new OnClickZhi() {
//            @Override
//            public void onClick() {
//                if (holder.checkbox_danxuan.isChecked()){
//                    list.get(position).setLine(true);
//                    holder.checkbox_danxuan.setButtonDrawable(R.mipmap.img_huodishanchuxuanze);
//                }else {
//                    list.get(position).setLine(false);
//                    holder.checkbox_danxuan.setButtonDrawable(R.mipmap.img_huodishanchuweixuan);
//                    checkbox_quanxuan.setChecked(false);
//                    checkbox_quanxuan.setButtonDrawable(R.mipmap.img_huodishanchuweixuan);
//                }
//
//                a=0;
//                for (LiShiXiaoXiEntity.ResponseBean entity:list){
//                    if (entity.isLine()){
//                        a++;
//                    }
//                }
//                if (a==list.size()){
//                    checkbox_quanxuan.setChecked(true);
//                    checkbox_quanxuan.setButtonDrawable(R.mipmap.img_huodishanchuxuanze);
//                }else {
//                    checkbox_quanxuan.setChecked(false);
//                    checkbox_quanxuan.setButtonDrawable(R.mipmap.img_huodishanchuweixuan);
//                }
//
//            }
//        });
        checkbox_quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_quanxuan.isChecked()){
                    checkbox_quanxuan.setButtonDrawable(R.mipmap.img_huodishanchuxuanze);
                    notifyDataSetChanged();
                    for (LiShiXiaoXiEntity.ResponseBean entity:list){
                        entity.setLine(true);
                    }
                }else {
                    checkbox_quanxuan.setButtonDrawable(R.mipmap.img_huodishanchuweixuan);
                    notifyDataSetChanged();
                    for (LiShiXiaoXiEntity.ResponseBean entity:list){
                        entity.setLine(false);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkbox_danxuan;
        private ImageView img_icon,img_weidu;
        private TextView tv_type,tv_content,tv_time;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            checkbox_danxuan= (CheckBox) itemView.findViewById(R.id.checkbox_danxuan);
            img_icon= (ImageView) itemView.findViewById(R.id.img_icon);
            img_weidu= (ImageView) itemView.findViewById(R.id.img_weidu);
            tv_type= (TextView) itemView.findViewById(R.id.tv_type);
            tv_content= (TextView) itemView.findViewById(R.id.tv_content);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
