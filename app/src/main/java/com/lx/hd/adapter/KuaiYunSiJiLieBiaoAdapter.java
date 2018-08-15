package com.lx.hd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.KuaiYunSiJiLieBiaoEntity;
import com.lx.hd.ui.activity.LogisticsOrderDetailsActivity2s;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/5/10.
 */

public class KuaiYunSiJiLieBiaoAdapter extends RecyclerView.Adapter<KuaiYunSiJiLieBiaoAdapter.ViewHolder>{

    private Context context;
    private List<KuaiYunSiJiLieBiaoEntity> list;
    private XuZhongItem xuZhongItem;
    private OnClickCall onClickCall;
    private RequestOptions mOptions;
    public KuaiYunSiJiLieBiaoAdapter(Context context, List<KuaiYunSiJiLieBiaoEntity> list) {
        this.context=context;
        this.list=list;
    }
    public interface XuZhongItem{
        void onClick(int position);
    }

    public void setOnClickCall(OnClickCall onClickCall) {
        this.onClickCall = onClickCall;
    }

    public interface OnClickCall{
        void onClick(int position);
    }

    public void setXuZhongItem(XuZhongItem xuZhongItem) {
        this.xuZhongItem = xuZhongItem;
    }

    @Override
    public KuaiYunSiJiLieBiaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kuaiyunsijiliebiao_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final KuaiYunSiJiLieBiaoAdapter.ViewHolder holder, final int position) {
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.user_header_defult)
                .error(R.mipmap.user_header_defult)
                .fitCenter();
        Glide.with(context).load(Constant.BASE_URL+list.get(position).getDriver_folder()+"/"+list.get(position).getDriver_autoname()).apply(mOptions).into(holder.img_touxiang);
        holder.tv_name.setText(list.get(position).getDriver_name());
        holder.tv_phone.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG);
        holder.tv_phone.getPaint().setAntiAlias(true);
        holder.tv_phone.setTextColor(Color.BLUE);
        holder.tv_phone.setText(list.get(position).getDriver_phone());
        holder.tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCall!=null){
                    onClickCall.onClick(position);
                }
            }
        });
        holder.tv_xingji.setText(list.get(position).getDriver_star()+"星");
        holder.tv_jiaoyi.setText("交易"+list.get(position).getDriver_num()+"笔");
        holder.img_xuanze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (KuaiYunSiJiLieBiaoEntity entity:list){
                    entity.setType(false);
                }
                if (holder.img_xuanze.isChecked()){
                    holder.img_xuanze.setButtonDrawable(R.mipmap.img_huodixuanze1);
                    list.get(position).setType(true);
                }else {
                    holder.img_xuanze.setButtonDrawable(R.mipmap.img_huodixuanze);
                    list.get(position).setType(false);
                }
                notifyDataSetChanged();
                if (xuZhongItem!=null){
                    xuZhongItem.onClick(position);
                }


            }
        });
        if (list.get(position).isType()){
            holder.img_xuanze.setButtonDrawable(R.mipmap.img_huodixuanze1);
            holder.img_xuanze.setChecked(true);
        }else {
            holder.img_xuanze.setButtonDrawable(R.mipmap.img_huodixuanze);
            holder.img_xuanze.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox img_xuanze;
        private CircleImageView img_touxiang;
        private TextView tv_name,tv_phone,tv_xingji,tv_jiaoyi;
        public ViewHolder(View itemView) {
            super(itemView);
            img_xuanze= (CheckBox) itemView.findViewById(R.id.img_xuanze);
            img_touxiang= (CircleImageView) itemView.findViewById(R.id.img_touxiang);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
            tv_xingji= (TextView) itemView.findViewById(R.id.tv_xingji);
            tv_jiaoyi= (TextView) itemView.findViewById(R.id.tv_jiaoyi);


        }
    }
}
