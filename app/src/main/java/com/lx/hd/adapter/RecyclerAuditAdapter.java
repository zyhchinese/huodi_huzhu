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
import com.lx.hd.bean.AuditOrderEntity;
import com.lx.hd.bean.AuditOrderEntity1;
import com.lx.hd.ui.fragment.AuditStatusFragment;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/12/19.
 */

public class RecyclerAuditAdapter extends RecyclerView.Adapter<RecyclerAuditAdapter.ViewHolder>{
    private Context context;
    private List<AuditOrderEntity> list;
    private List<AuditOrderEntity1> list1;
    private int type;
    private OnClickChild onClickChild;
    public RecyclerAuditAdapter(Context context, List<AuditOrderEntity> list, int type) {
        this.context=context;
        this.list=list;
        this.type=type;
    }
    public RecyclerAuditAdapter(Context context, List<AuditOrderEntity1> list, int type,int a) {
        this.context=context;
        this.list1=list;
        this.type=type;
    }

    public void setOnClickChild(OnClickChild onClickChild) {
        this.onClickChild = onClickChild;
    }

    public interface OnClickChild{
        void onClick(int position);
    }

    @Override
    public RecyclerAuditAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder;
        if (type==0){
            View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_audit1, parent, false);
            viewHolder=new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_audit, parent, false);
            viewHolder=new ViewHolder(view);
        }
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickChild!=null){
                    onClickChild.onClick(viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAuditAdapter.ViewHolder holder, int position) {
//        String substring = list.get(position).getCreatetime().substring(0, 10);

        //改版前
//        if (type==0){
//            holder.tv_dingdanhao.setText(list.get(position).getOrderno());
//            if (list.get(position).getCust_orderstatus()==-1){
//                holder.tv_zhuangtai.setText("已取消");
//            }else if (list.get(position).getCust_orderstatus()==0){
//                holder.tv_zhuangtai.setText("等待接货");
//            }else if (list.get(position).getCust_orderstatus()==1){
//                holder.tv_zhuangtai.setText("服务开始");
//            }else if (list.get(position).getCust_orderstatus()==2){
//                holder.tv_zhuangtai.setText("已完成");
//            }
//            holder.tv_name.setText(list.get(position).getWeightofgoods());
//            holder.tv_phone.setText("¥"+list.get(position).getOrdertotalprice());
//            holder.tv_number.setText(list.get(position).getCreatetime());
//        }else {
//            holder.tv_dingdanhao.setText(list1.get(position).getOwner_orderno());
//            if (list1.get(position).getCust_orderstatus()==-1){
//                holder.tv_zhuangtai.setText("已取消");
//            }else if (list1.get(position).getCust_orderstatus()==0){
//                holder.tv_zhuangtai.setText("等待接货");
//            }else if (list1.get(position).getCust_orderstatus()==1){
//                holder.tv_zhuangtai.setText("服务开始");
//            }else if (list1.get(position).getCust_orderstatus()==2){
//                holder.tv_zhuangtai.setText("已完成");
//            }
//            holder.tv_name.setText(list1.get(position).getCar_type());
//            holder.tv_phone.setText("¥"+list1.get(position).getOwner_totalprice());
//            holder.tv_number.setText(list1.get(position).getOwner_createtime());
//        }


        //改版后
        if (type==0){
            RequestOptions mOptions = new RequestOptions()
                    .placeholder(R.mipmap.touxiang)
                    .error(R.mipmap.touxiang)
                    .fitCenter();

            holder.tv_dingdanhao.setText(list.get(position).getOrderno());
            if (list.get(position).getCust_orderstatus()==-1){
                holder.tv_zhuangtai.setText("已取消");
            }else if (list.get(position).getCust_orderstatus()==0){
                holder.tv_zhuangtai.setText("等待接货");
            }else if (list.get(position).getCust_orderstatus()==1){
                holder.tv_zhuangtai.setText("服务开始");
            }else if (list.get(position).getCust_orderstatus()==2){
                holder.tv_zhuangtai.setText("已完成");
            }
            holder.tv_address.setText(list.get(position).getStartaddress());
            holder.tv_address1.setText(list.get(position).getEndaddress());
            holder.tv_quhuoshijian.setText(list.get(position).getPickuptime());
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            String format = decimalFormat.format(list.get(position).getOrdertotalprice());
            holder.tv_zongjia.setText("¥"+format);
            Glide.with(context).load(Constant.BASE_URL+list.get(position).getFolder()+"/"+list.get(position).getAutoname()).apply(mOptions).into(holder.img_avatar);
            holder.tv_fhr.setText(list.get(position).getConsigneename());
            holder.fh_sj.setText(list.get(position).getCreatetime());
        }else {
            RequestOptions mOptions = new RequestOptions()
                    .placeholder(R.mipmap.touxiang)
                    .error(R.mipmap.touxiang)
                    .fitCenter();

            holder.tv_dingdanhao.setText(list1.get(position).getOwner_orderno());
            if (list1.get(position).getCust_orderstatus()==-1){
                holder.tv_zhuangtai.setText("已取消");
            }else if (list1.get(position).getCust_orderstatus()==0){
                holder.tv_zhuangtai.setText("等待接货");
            }else if (list1.get(position).getCust_orderstatus()==1){
                holder.tv_zhuangtai.setText("服务开始");
            }else if (list1.get(position).getCust_orderstatus()==2){
                holder.tv_zhuangtai.setText("已完成");
            }
            holder.tv_address.setText(list1.get(position).getOwner_address());
            holder.tv_address1.setText(list1.get(position).getOwner_send_address());
            holder.tv_chexing.setText(list1.get(position).getCar_type());
            holder.tv_quhuoshijian.setText(list1.get(position).getOwner_sendtime());
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            String format = decimalFormat.format(list1.get(position).getOwner_totalprice());
            holder.tv_zongjia.setText("¥"+format);
            Glide.with(context).load(Constant.BASE_URL+list1.get(position).getFolder()+"/"+list1.get(position).getAutoname()).apply(mOptions).into(holder.img_avatar);
            holder.tv_fhr.setText(list1.get(position).getOwner_link_name());
            holder.fh_sj.setText(list1.get(position).getOwner_createtime());
        }

//        holder.tv_time.setText(substring);
//        holder.tv_money.setText((int)(list.get(position).getOrder_money())+"元");
//        if (type==0){
//            holder.img_zuo.setImageResource(R.mipmap.img_zuoshangjiao);
//        }else{
//            if (list.get(position).getOrderstatus()==1){
//                holder.img_zuo.setImageResource(R.mipmap.img_zuoshangjiao1);
//            }else {
//                holder.img_zuo.setImageResource(R.mipmap.img_zuoshangjiao_f1);
//            }
//
//        }

//        holder.tv_dingdanhao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onClickChild!=null){
//                    onClickChild.onClick(holder.getAdapterPosition());
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (type==0){
            return list.size();
        }else {
            return list1.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView tv_name,tv_dingdanhao,tv_phone,tv_number,tv_time,tv_money,
//                tv_zhuangtai;
//        private ImageView jiantou,img_zuo;
//        private View view;


        private TextView tv_name,tv_dingdanhao,tv_phone,tv_number,tv_time,tv_money,
                tv_zhuangtai;
        private TextView tv_address,tv_address1,tv_quhuoshijian,tv_zongjia,tv_fhr,fh_sj,tv_chexing;
        private CircleImageView img_avatar;
        private ImageView jiantou,img_zuo;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
//            view=itemView;
//            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
//            tv_dingdanhao= (TextView) itemView.findViewById(R.id.tv_dingdanhao);
//            tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
//            tv_number= (TextView) itemView.findViewById(R.id.tv_number);
//            tv_zhuangtai= (TextView) itemView.findViewById(R.id.tv_zhuangtai);
////            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
////            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
////            jiantou= (ImageView) itemView.findViewById(R.id.jiantou);
////            img_zuo= (ImageView) itemView.findViewById(R.id.img_zuo);

            view=itemView;
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_dingdanhao= (TextView) itemView.findViewById(R.id.tv_dingdanhao);
            tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
            tv_number= (TextView) itemView.findViewById(R.id.tv_number);
            tv_zhuangtai= (TextView) itemView.findViewById(R.id.tv_zhuangtai);
            tv_address= (TextView) itemView.findViewById(R.id.tv_address);
            tv_address1= (TextView) itemView.findViewById(R.id.tv_address1);
            tv_quhuoshijian= (TextView) itemView.findViewById(R.id.tv_quhuoshijian);
            tv_zongjia= (TextView) itemView.findViewById(R.id.tv_zongjia);
            tv_fhr= (TextView) itemView.findViewById(R.id.tv_fhr);
            fh_sj= (TextView) itemView.findViewById(R.id.fh_sj);
            tv_chexing= (TextView) itemView.findViewById(R.id.tv_chexing);
            img_avatar= (CircleImageView) itemView.findViewById(R.id.img_avatar);
        }
    }
}
