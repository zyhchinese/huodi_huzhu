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
import com.lx.hd.bean.HuoDiSearch;
import com.lx.hd.bean.HuoDiSearch1;
import com.lx.hd.bean.HuoDiSearch2;
import com.lx.hd.ui.activity.SearchResultActivity;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/3/2.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{

    private Context context;
    private List<HuoDiSearch> huoDiSearchList;
    private List<HuoDiSearch1> huoDiSearchList1;
    private List<HuoDiSearch2> huoDiSearchList2;
    private String type;
    private OnClickItem onClickItem;

    public OnClickItem getOnClickItem() {
        return onClickItem;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public SearchResultAdapter(Context context, List<HuoDiSearch> huoDiSearchList, List<HuoDiSearch1> huoDiSearchList1, List<HuoDiSearch2> huoDiSearchList2, String type) {
        this.context=context;
        this.huoDiSearchList=huoDiSearchList;
        this.huoDiSearchList1=huoDiSearchList1;
        this.huoDiSearchList2=huoDiSearchList2;
        this.type=type;
    }

    public interface OnClickItem{
        void onClick(int position);
    }

    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder viewHolder=null;
        View view=null;
        if (type.equals("0")){
            view= LayoutInflater.from(context).inflate(R.layout.act_recyclerview_audit1,viewGroup,false);
            viewHolder=new ViewHolder(view);
        }else if (type.equals("1")){
            view= LayoutInflater.from(context).inflate(R.layout.act_recyclerview_audit,viewGroup,false);
            viewHolder=new ViewHolder(view);
        }else {
            view= LayoutInflater.from(context).inflate(R.layout.act_recyclerview_kuaiyun,viewGroup,false);
            viewHolder=new ViewHolder(view);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem!=null){
                    onClickItem.onClick(finalViewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchResultAdapter.ViewHolder viewHolder, int i) {

        if (type.equals("0")) {
//            viewHolder.tv_dingdanhao.setText(huoDiSearchList.get(i).getOrderno());
//            if (huoDiSearchList.get(i).getCust_orderstatus() == -1) {
//                viewHolder.tv_zhuangtai.setText("已取消");
//            } else if (huoDiSearchList.get(i).getCust_orderstatus() == 0) {
//                viewHolder.tv_zhuangtai.setText("等待接货");
//            } else if (huoDiSearchList.get(i).getCust_orderstatus() == 1) {
//                viewHolder.tv_zhuangtai.setText("服务开始");
//            } else if (huoDiSearchList.get(i).getCust_orderstatus() == 2) {
//                viewHolder.tv_zhuangtai.setText("已完成");
//            }
//            viewHolder.tv_name.setText(huoDiSearchList.get(i).getWeightofgoods());
//            viewHolder.tv_phone.setText("¥" + huoDiSearchList.get(i).getOrdertotalprice());
//            viewHolder.tv_number.setText(huoDiSearchList.get(i).getCreatetime());

            RequestOptions mOptions = new RequestOptions()
                    .placeholder(R.mipmap.touxiang)
                    .error(R.mipmap.touxiang)
                    .fitCenter();

            viewHolder.tv_dingdanhao.setText(huoDiSearchList.get(i).getOrderno());
            if (huoDiSearchList.get(i).getCust_orderstatus()==-1){
                viewHolder.tv_zhuangtai.setText("已取消");
            }else if (huoDiSearchList.get(i).getCust_orderstatus()==0){
                viewHolder.tv_zhuangtai.setText("等待接货");
            }else if (huoDiSearchList.get(i).getCust_orderstatus()==1){
                viewHolder.tv_zhuangtai.setText("服务开始");
            }else if (huoDiSearchList.get(i).getCust_orderstatus()==2){
                viewHolder.tv_zhuangtai.setText("已完成");
            }
            viewHolder.tv_address.setText(huoDiSearchList.get(i).getStartaddress());
            viewHolder.tv_address1.setText(huoDiSearchList.get(i).getEndaddress());
            viewHolder.tv_quhuoshijian.setText(huoDiSearchList.get(i).getPickuptime());
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            String format = decimalFormat.format(huoDiSearchList.get(i).getOrdertotalprice());
            viewHolder.tv_zongjia.setText("¥"+format);
            Glide.with(context).load(Constant.BASE_URL+huoDiSearchList.get(i).getFolder()+"/"+huoDiSearchList.get(i).getAutoname()).apply(mOptions).into(viewHolder.img_avatar);
            viewHolder.tv_fhr.setText(huoDiSearchList.get(i).getConsigneename());
            viewHolder.fh_sj.setText(huoDiSearchList.get(i).getCreatetime());
        }else if (type.equals("1")){
//            viewHolder.tv_dingdanhao.setText(huoDiSearchList1.get(i).getOwner_orderno());
//            if (huoDiSearchList1.get(i).getCust_orderstatus()==-1){
//                viewHolder.tv_zhuangtai.setText("已取消");
//            }else if (huoDiSearchList1.get(i).getCust_orderstatus()==0){
//                viewHolder.tv_zhuangtai.setText("等待接货");
//            }else if (huoDiSearchList1.get(i).getCust_orderstatus()==1){
//                viewHolder.tv_zhuangtai.setText("服务开始");
//            }else if (huoDiSearchList1.get(i).getCust_orderstatus()==2){
//                viewHolder.tv_zhuangtai.setText("已完成");
//            }
//            viewHolder.tv_name.setText(huoDiSearchList1.get(i).getCar_type());
//            viewHolder.tv_phone.setText("¥"+huoDiSearchList1.get(i).getOwner_totalprice());
//            viewHolder.tv_number.setText(huoDiSearchList1.get(i).getOwner_createtime());

            RequestOptions mOptions = new RequestOptions()
                    .placeholder(R.mipmap.touxiang)
                    .error(R.mipmap.touxiang)
                    .fitCenter();

            viewHolder.tv_dingdanhao.setText(huoDiSearchList1.get(i).getOwner_orderno());
            if (huoDiSearchList1.get(i).getCust_orderstatus()==-1){
                viewHolder.tv_zhuangtai.setText("已取消");
            }else if (huoDiSearchList1.get(i).getCust_orderstatus()==0){
                viewHolder.tv_zhuangtai.setText("等待接货");
            }else if (huoDiSearchList1.get(i).getCust_orderstatus()==1){
                viewHolder.tv_zhuangtai.setText("服务开始");
            }else if (huoDiSearchList1.get(i).getCust_orderstatus()==2){
                viewHolder.tv_zhuangtai.setText("已完成");
            }
            viewHolder.tv_address.setText(huoDiSearchList1.get(i).getOwner_address());
            viewHolder.tv_address1.setText(huoDiSearchList1.get(i).getOwner_send_address());
            viewHolder.tv_chexing.setText(huoDiSearchList1.get(i).getCar_type());
            viewHolder.tv_quhuoshijian.setText(huoDiSearchList1.get(i).getOwner_sendtime());
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            String format = decimalFormat.format(huoDiSearchList1.get(i).getOwner_totalprice());
            viewHolder.tv_zongjia.setText("¥"+format);
            Glide.with(context).load(Constant.BASE_URL+huoDiSearchList1.get(i).getFolder()+"/"+huoDiSearchList1.get(i).getAutoname()).apply(mOptions).into(viewHolder.img_avatar);
            viewHolder.tv_fhr.setText(huoDiSearchList1.get(i).getOwner_link_name());
            viewHolder.fh_sj.setText(huoDiSearchList1.get(i).getOwner_createtime());


        }else {
//            viewHolder.tv_dingdanhao.setText(huoDiSearchList2.get(i).getOrderno());
//            if (huoDiSearchList2.get(i).getCust_orderstatus()==-1){
//                viewHolder.tv_zhuangtai.setText("已取消");
//            }else if (huoDiSearchList2.get(i).getCust_orderstatus()==0){
//                viewHolder.tv_zhuangtai.setText("等待接货");
//            }else if (huoDiSearchList2.get(i).getCust_orderstatus()==1){
//                viewHolder.tv_zhuangtai.setText("服务开始");
//            }else if (huoDiSearchList2.get(i).getCust_orderstatus()==2){
//                viewHolder.tv_zhuangtai.setText("已完成");
//            }
//            viewHolder.tv_car_name.setText(huoDiSearchList2.get(i).getCartypenames());
////            viewHolder.tv_huowu_type.setText(huoDiSearchList2.get(i).getCargotypenames());
//            if (huoDiSearchList2.get(i).getWeight()==0){
//                viewHolder.tv_zhongliang.setText(huoDiSearchList2.get(i).getVolume()+"");
//            }else if (huoDiSearchList2.get(i).getVolume()==0){
//                viewHolder.tv_zhongliang.setText(huoDiSearchList2.get(i).getWeight()+"");
//            }else {
//                viewHolder.tv_zhongliang.setText(huoDiSearchList2.get(i).getWeight()+"/"+huoDiSearchList2.get(i).getVolume());
//            }
//            viewHolder.tv_money.setText("¥"+huoDiSearchList2.get(i).getSettheprice());
//            viewHolder.tv_time.setText(huoDiSearchList2.get(i).getCreatetime());

            RequestOptions mOptions = new RequestOptions()
                    .placeholder(R.mipmap.touxiang)
                    .error(R.mipmap.touxiang)
                    .fitCenter();

            viewHolder.tv_dingdanhao.setText(huoDiSearchList2.get(i).getOrderno());
            if (huoDiSearchList2.get(i).getCust_orderstatus()==-1){
                viewHolder.tv_zhuangtai.setText("已取消");
            }else if (huoDiSearchList2.get(i).getCust_orderstatus()==0){
                viewHolder.tv_zhuangtai.setText("等待接货");
            }else if (huoDiSearchList2.get(i).getCust_orderstatus()==1){
                viewHolder.tv_zhuangtai.setText("服务开始");
            }else if (huoDiSearchList2.get(i).getCust_orderstatus()==2){
                viewHolder.tv_zhuangtai.setText("已完成");
            }

            viewHolder.tv_cfd_shi.setText(huoDiSearchList2.get(i).getScity()+huoDiSearchList2.get(i).getScounty());
            viewHolder.tv_mdd_shi.setText(huoDiSearchList2.get(i).getEcity()+huoDiSearchList2.get(i).getEcounty());
            viewHolder.tv_huowuleixing.setText(huoDiSearchList2.get(i).getCartypenames());
            viewHolder.tv_cheliangleixing.setText(huoDiSearchList2.get(i).getCargotypenames());
            if (huoDiSearchList2.get(i).getWeight()==0){
                viewHolder.tv_zhongliang.setText(huoDiSearchList2.get(i).getVolume()+"件");
            }else if (huoDiSearchList2.get(i).getVolume()==0){
                viewHolder.tv_zhongliang.setText(huoDiSearchList2.get(i).getWeight()+"kg");
            }else {
                viewHolder.tv_zhongliang.setText(huoDiSearchList2.get(i).getWeight()+"kg/"+huoDiSearchList2.get(i).getVolume()+"件");
            }

            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            String format = decimalFormat.format(huoDiSearchList2.get(i).getSettheprice());
            viewHolder.tv_zongjia.setText("¥"+format);
            viewHolder.tv_zhuangcheshijian.setText(huoDiSearchList2.get(i).getShipmenttime());
            Glide.with(context).load(Constant.BASE_URL+huoDiSearchList2.get(i).getFolder()+"/"+huoDiSearchList2.get(i).getAutoname()).apply(mOptions).into(viewHolder.img_avatar);
            viewHolder.tv_fhr.setText(huoDiSearchList2.get(i).getContactname());
            viewHolder.fh_sj.setText(huoDiSearchList2.get(i).getCreatetime());


        }
    }

    @Override
    public int getItemCount() {
        if (type.equals("0")){
            return huoDiSearchList.size();
        }else if (type.equals("1")){
            return huoDiSearchList1.size();
        }else {
            return huoDiSearchList2.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView tv_name,tv_dingdanhao,tv_phone,tv_number,
//                tv_zhuangtai,tv_car_name,tv_huowu_type,tv_zhongliang,tv_money,tv_time;
//        private View view;

        private TextView tv_name,tv_dingdanhao,tv_phone,tv_number,
                tv_zhuangtai,tv_car_name,tv_huowu_type,tv_zhongliang,tv_money,tv_time,
                tv_address,tv_address1,tv_quhuoshijian,tv_zongjia,tv_fhr,fh_sj,tv_chexing,
                tv_cfd_shi,tv_mdd_shi,tv_huowuleixing,tv_cheliangleixing,tv_zhuangcheshijian;
        private CircleImageView img_avatar;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            if (type.equals("0")||type.equals("1")){
//                tv_name= (TextView) itemView.findViewById(R.id.tv_name);
//                tv_dingdanhao= (TextView) itemView.findViewById(R.id.tv_dingdanhao);
//                tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
//                tv_number= (TextView) itemView.findViewById(R.id.tv_number);
//                tv_zhuangtai= (TextView) itemView.findViewById(R.id.tv_zhuangtai);

//                view=itemView;
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
            }else {
//                tv_dingdanhao= (TextView) itemView.findViewById(R.id.tv_dingdanhao);
//                tv_zhuangtai= (TextView) itemView.findViewById(R.id.tv_zhuangtai);
//                tv_car_name= (TextView) itemView.findViewById(R.id.tv_car_name);
////                tv_huowu_type= (TextView) itemView.findViewById(R.id.tv_huowu_type);
//                tv_zhongliang= (TextView) itemView.findViewById(R.id.tv_zhongliang);
//                tv_money= (TextView) itemView.findViewById(R.id.tv_money);
//                tv_time= (TextView) itemView.findViewById(R.id.tv_time);

//                view=itemView;
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
}
