package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.KuaiYunEntity;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/2/27.
 */

public class KuaiYunAdapter extends RecyclerView.Adapter<KuaiYunAdapter.ViewHolder>{

    private Context context;
    private List<KuaiYunEntity> list;
    private OnClickItem onClickItem;
    public KuaiYunAdapter(Context context, List<KuaiYunEntity> list) {
        this.context=context;
        this.list=list;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem{
        void onClick(int position);
    }

    @Override
    public KuaiYunAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_kuaiyun, viewGroup, false);
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

    @Override
    public void onBindViewHolder(final KuaiYunAdapter.ViewHolder viewHolder, int i) {
        //改版前
//        viewHolder.tv_dingdanhao.setText(list.get(i).getOrderno());
//        if (list.get(i).getCust_orderstatus()==-1){
//            viewHolder.tv_zhuangtai.setText("已取消");
//        }else if (list.get(i).getCust_orderstatus()==0){
//            viewHolder.tv_zhuangtai.setText("等待接货");
//        }else if (list.get(i).getCust_orderstatus()==1){
//            viewHolder.tv_zhuangtai.setText("服务开始");
//        }else if (list.get(i).getCust_orderstatus()==2){
//            viewHolder.tv_zhuangtai.setText("已完成");
//        }
//        viewHolder.tv_car_name.setText(list.get(i).getCartypenames());
//        viewHolder.tv_huowu_type.setText(list.get(i).getCargotypenames());
//        if (list.get(i).getWeight()==0){
//            viewHolder.tv_zhongliang.setText(list.get(i).getVolume()+"件");
//        }else if (list.get(i).getVolume()==0){
//            viewHolder.tv_zhongliang.setText(list.get(i).getWeight()+"kg");
//        }else {
//            viewHolder.tv_zhongliang.setText(list.get(i).getWeight()+"kg/"+list.get(i).getVolume()+"件");
//        }
//        viewHolder.tv_money.setText("¥"+list.get(i).getSettheprice());
//        viewHolder.tv_time.setText(list.get(i).getCreatetime());

        //改版后
        RequestOptions mOptions = new RequestOptions()
                .placeholder(R.mipmap.touxiang)
                .error(R.mipmap.touxiang)
                .fitCenter();

        viewHolder.tv_dingdanhao.setText(list.get(i).getOrderno());
        if (list.get(i).getCust_orderstatus()==-1){
            viewHolder.tv_zhuangtai.setText("已取消");
        }else if (list.get(i).getCust_orderstatus()==0){
            viewHolder.tv_zhuangtai.setText("等待接货");
        }else if (list.get(i).getCust_orderstatus()==1){
            viewHolder.tv_zhuangtai.setText("服务开始");
        }else if (list.get(i).getCust_orderstatus()==2){
            viewHolder.tv_zhuangtai.setText("已完成");
        }

        viewHolder.tv_cfd_shi.setText(list.get(i).getScity()+list.get(i).getScounty());
        viewHolder.tv_mdd_shi.setText(list.get(i).getEcity()+list.get(i).getEcounty());
        viewHolder.tv_huowuleixing.setText(list.get(i).getCartypenames());
        viewHolder.tv_cheliangleixing.setText(list.get(i).getCargotypenames());
        if (list.get(i).getWeight()==0){
            viewHolder.tv_zhongliang.setText(list.get(i).getVolume()+"件");
        }else if (list.get(i).getVolume()==0){
            viewHolder.tv_zhongliang.setText(list.get(i).getWeight()+"kg");
        }else {
            viewHolder.tv_zhongliang.setText(list.get(i).getWeight()+"kg/"+list.get(i).getVolume()+"件");
        }

        DecimalFormat decimalFormat=new DecimalFormat("###0.00");
        String format = decimalFormat.format(list.get(i).getSettheprice());
        viewHolder.tv_zongjia.setText("¥"+format);
        viewHolder.tv_zhuangcheshijian.setText(list.get(i).getShipmenttime());
        Glide.with(context).load(Constant.BASE_URL+list.get(i).getFolder()+"/"+list.get(i).getAutoname()).apply(mOptions).into(viewHolder.img_avatar);
        viewHolder.tv_fhr.setText(list.get(i).getContactname());
        viewHolder.fh_sj.setText(list.get(i).getCreatetime());

//        viewHolder.tv_dingdanhao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onClickItem!=null){
//                    onClickItem.onClick(viewHolder.getAdapterPosition());
//                }
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView tv_dingdanhao,tv_zhuangtai,tv_car_name,tv_huowu_type,tv_zhongliang,
//                tv_money,tv_time;
//        private View view;

        private TextView tv_dingdanhao,tv_zhuangtai,tv_car_name,tv_huowu_type,tv_zhongliang,
                tv_money,tv_time;
        private TextView tv_cfd_shi,tv_mdd_shi,tv_huowuleixing,tv_cheliangleixing,tv_zongjia,
                tv_zhuangcheshijian,tv_fhr,fh_sj;
        private CircleImageView img_avatar;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
//            view=itemView;
//            tv_dingdanhao= (TextView) itemView.findViewById(R.id.tv_dingdanhao);
//            tv_zhuangtai= (TextView) itemView.findViewById(R.id.tv_zhuangtai);
//            tv_car_name= (TextView) itemView.findViewById(R.id.tv_car_name);
//            tv_huowu_type= (TextView) itemView.findViewById(R.id.tv_huowu_type);
//            tv_zhongliang= (TextView) itemView.findViewById(R.id.tv_zhongliang);
//            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
//            tv_time= (TextView) itemView.findViewById(R.id.tv_time);

            view=itemView;
            tv_dingdanhao= (TextView) itemView.findViewById(R.id.tv_dingdanhao);
            tv_zhuangtai= (TextView) itemView.findViewById(R.id.tv_zhuangtai);
            tv_car_name= (TextView) itemView.findViewById(R.id.tv_car_name);
//            tv_huowu_type= (TextView) itemView.findViewById(R.id.tv_huowu_type);
            tv_zhongliang= (TextView) itemView.findViewById(R.id.tv_zhongliang);
            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_cfd_shi= (TextView) itemView.findViewById(R.id.tv_cfd_shi);
            tv_mdd_shi= (TextView) itemView.findViewById(R.id.tv_mdd_shi);
            tv_huowuleixing= (TextView) itemView.findViewById(R.id.tv_huowuleixing);
            tv_cheliangleixing= (TextView) itemView.findViewById(R.id.tv_cheliangleixing);
            tv_zongjia= (TextView) itemView.findViewById(R.id.tv_zongjia);
            tv_zhuangcheshijian= (TextView) itemView.findViewById(R.id.tv_zhuangcheshijian);
            tv_fhr= (TextView) itemView.findViewById(R.id.tv_fhr);
            fh_sj= (TextView) itemView.findViewById(R.id.fh_sj);
            img_avatar= (CircleImageView) itemView.findViewById(R.id.img_avatar);
        }
    }
}
