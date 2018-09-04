package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.ShuFengCheLieBiaoEntity;
import com.lx.hd.ui.activity.ShuFengCheLieBiaoActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 赵英辉 on 2018/8/23.
 */

public class ShuFengCheLieBiaoAdapter extends RecyclerView.Adapter<ShuFengCheLieBiaoAdapter.ViewHolder>{

    private Context context;
    private List<ShuFengCheLieBiaoEntity.ResponseBean> response;
    private OnClickCall onClickCall;
    private OnClickItem onClickItem;
    private OnClickXuanZe onClickXuanZe;

    public ShuFengCheLieBiaoAdapter(Context context, List<ShuFengCheLieBiaoEntity.ResponseBean> response) {
        this.context=context;
        this.response=response;
    }

    public void setOnClickCall(OnClickCall onClickCall) {
        this.onClickCall = onClickCall;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setOnClickXuanZe(OnClickXuanZe onClickXuanZe) {
        this.onClickXuanZe = onClickXuanZe;
    }

    @Override
    public ShuFengCheLieBiaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shufengcheliebiao_item, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem!=null){
                    onClickItem.onClick(viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    public interface OnClickCall{
        void onClick(int position);
    }

    public interface OnClickItem{
        void onClick(int position);
    }

    public interface OnClickXuanZe{
        void onClick(int position);
    }

    @Override
    public void onBindViewHolder(ShuFengCheLieBiaoAdapter.ViewHolder holder, final int position) {

        RequestOptions options=new RequestOptions()
                .placeholder(R.mipmap.user_header_defult)
                .error(R.mipmap.user_header_defult)
                .fitCenter();
        holder.tv_chufa.setText(response.get(position).getScity());
        holder.tv_mudi.setText(response.get(position).getEcity());
        holder.tv_tujing.setText("一"+response.get(position).getWaytocitysshow().replace(",","一")+"一");
        holder.tv_chechang.setText("车长:"+response.get(position).getTotalvehicle()+"米/余:"+response.get(position).getAvailablevehicle()+"米");
        holder.tv_chufatime.setText("出发时间："+response.get(position).getDeparturetime()+"");
        Glide.with(context).load(Constant.BASE_URL+response.get(position).getFolder()+response.get(position).getAutoname()).apply(options).into(holder.img_avatar);
        holder.tv_fhr.setText(response.get(position).getContactname());

        if (response.get(position).getDriver_star().equals("0")){
            holder.img1.setVisibility(View.GONE);
            holder.img2.setVisibility(View.GONE);
            holder.img3.setVisibility(View.GONE);
            holder.img4.setVisibility(View.GONE);
            holder.img5.setVisibility(View.GONE);
        }else if (response.get(position).getDriver_star().equals("1")){
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.GONE);
            holder.img3.setVisibility(View.GONE);
            holder.img4.setVisibility(View.GONE);
            holder.img5.setVisibility(View.GONE);
        }else if (response.get(position).getDriver_star().equals("2")){
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
            holder.img3.setVisibility(View.GONE);
            holder.img4.setVisibility(View.GONE);
            holder.img5.setVisibility(View.GONE);
        }else if (response.get(position).getDriver_star().equals("3")){
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
            holder.img3.setVisibility(View.VISIBLE);
            holder.img4.setVisibility(View.GONE);
            holder.img5.setVisibility(View.GONE);
        }else if (response.get(position).getDriver_star().equals("4")){
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
            holder.img3.setVisibility(View.VISIBLE);
            holder.img4.setVisibility(View.VISIBLE);
            holder.img5.setVisibility(View.GONE);
        }else if (response.get(position).getDriver_star().equals("5")){
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
            holder.img3.setVisibility(View.VISIBLE);
            holder.img4.setVisibility(View.VISIBLE);
            holder.img5.setVisibility(View.VISIBLE);
        }

        if (response.get(position).getDriver_num()!=null){
            holder.tv_jiaoyiliang.setText("交易"+response.get(position).getDriver_num()+"笔");
        }else {
            holder.tv_jiaoyiliang.setText("交易0笔");
        }
        holder.iv_iphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCall!=null){
                    onClickCall.onClick(position);
                }
            }
        });

        holder.tv_xianzaiyongche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickXuanZe!=null){
                    onClickXuanZe.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_chufa,tv_tujing,tv_mudi,tv_chechang,tv_chufatime,tv_fhr,tv_jiaoyiliang,tv_xianzaiyongche;
        private CircleImageView img_avatar;
        private ImageView img1,img2,img3,img4,img5,iv_iphone;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            tv_chufa= (TextView) itemView.findViewById(R.id.tv_chufa);
            tv_tujing= (TextView) itemView.findViewById(R.id.tv_tujing);
            tv_mudi= (TextView) itemView.findViewById(R.id.tv_mudi);
            tv_chechang= (TextView) itemView.findViewById(R.id.tv_chechang);
            tv_chufatime= (TextView) itemView.findViewById(R.id.tv_chufatime);
            tv_fhr= (TextView) itemView.findViewById(R.id.tv_fhr);
            tv_jiaoyiliang= (TextView) itemView.findViewById(R.id.tv_jiaoyiliang);
            tv_xianzaiyongche= (TextView) itemView.findViewById(R.id.tv_xianzaiyongche);
            img_avatar= (CircleImageView) itemView.findViewById(R.id.img_avatar);
            img1= (ImageView) itemView.findViewById(R.id.img1);
            img2= (ImageView) itemView.findViewById(R.id.img2);
            img3= (ImageView) itemView.findViewById(R.id.img3);
            img4= (ImageView) itemView.findViewById(R.id.img4);
            img5= (ImageView) itemView.findViewById(R.id.img5);
            iv_iphone= (ImageView) itemView.findViewById(R.id.iv_iphone);
        }
    }
}
