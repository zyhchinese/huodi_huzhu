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
import com.lx.hd.bean.HuoDKuaiYun_liebiao;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.lx.hd.R.id.img1;
import static com.lx.hd.R.id.img_avatar;

/**
 * Created by admin on 2018/3/12.（找货 快运  适配器）
 */

public class RecyclerViewKuaiYunAdapter extends RecyclerView.Adapter<RecyclerViewKuaiYunAdapter.ViewHolder>{

    private Context context;
    private List<HuoDKuaiYun_liebiao.RowsBean> list;
    private OnClickItem onClickItem;
    private OnClickItemHang onClickItemHang;
    private OnCallPhone onCallPhone;
    private RequestOptions mOptions;//请求
    private HuoDKuaiYun_liebiao.RowsBean huoDKuaiYun_liebiao = new HuoDKuaiYun_liebiao.RowsBean();

    public void setOnClickItemHang(OnClickItemHang onClickItemHang) {
        this.onClickItemHang = onClickItemHang;
    }

    public RecyclerViewKuaiYunAdapter(Context context, List<HuoDKuaiYun_liebiao.RowsBean> list) {
        this.context=context;
        this.list=list;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setOnCallPhone(OnCallPhone onCallPhone) {
        this.onCallPhone = onCallPhone;
    }

    public interface OnClickItem{
        void onClick(int position);
    }
    public interface OnClickItemHang{
        void onClick(int position);
    }
    public interface OnCallPhone{
        void onClick(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.kuaiyun_liebiao100, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemHang!=null){
                    onClickItemHang.onClick(viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //String userLogo = Constant.BASE_URL + user.getFolder() + "/" + user.getAutoname();
        //Glide.with(context)
               // .load(userLogo)
               // .apply(mOptions)
               // .into(img_avatar);
        //holder.tv_car_name.setText(list.get(position).getCar_name());
        //Glide.with(context).load(Constant.BASE_URL+list.get(position).getFolder()+list.get(position).getAutoname()).into(holder.img_car);

        mOptions = new RequestOptions()
                .placeholder(R.mipmap.touxiang)
                .error(R.mipmap.touxiang)
                .fitCenter();

        huoDKuaiYun_liebiao = list.get(position);
        String userLogo = Constant.BASE_URL + huoDKuaiYun_liebiao.getFolder() + "/" + huoDKuaiYun_liebiao.getAutoname();
        Glide.with(context)
                .load(userLogo)
                .apply(mOptions)
                .into(holder.mAvatar);

        holder.tv_fhr.setText(list.get(position).getContactname());
        if (list.get(position).getCreatetime().length()>10){

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date createTime = null;
            try {
                createTime = df.parse(list.get(position).getCreatetime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date date=new Date(System.currentTimeMillis());
            String format = df.format(date);
            Date nowDate = null;
            try {
                nowDate=df.parse(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            //如果需要向后计算日期 -改为+
            Date newDate2 = new Date(nowDate.getTime()-createTime.getTime());

            if (newDate2.getTime()<=(long) 30*60*1000){
                holder.fh_sj.setText("刚刚");
            }else {
                holder.fh_sj.setText(list.get(position).getCreatetime().substring(5,list.get(position).getCreatetime().length()-3));
            }



        }else {
            holder.fh_sj.setText(list.get(position).getCreatetime());
        }
        if (list.get(position).getScity().length()>1){
            holder.tv_cfd_shi.setText(list.get(position).getScity().substring(0,list.get(position).getScity().length()-1));
        }else {
            holder.tv_cfd_shi.setText(list.get(position).getScity());
        }
        if (list.get(position).getScounty().length()>1){
            holder.tv_cfd_qu.setText(list.get(position).getScounty().substring(0,list.get(position).getScounty().length()-1));
        }else {
            holder.tv_cfd_qu.setText(list.get(position).getScounty());
        }
        if (list.get(position).getEcity().length()>1){
            holder.tv_mdd_shi.setText(list.get(position).getEcity().substring(0,list.get(position).getEcity().length()-1));
        }else {
            holder.tv_mdd_shi.setText(list.get(position).getEcity());
        }
        if (list.get(position).getEcounty().length()>1){
            holder.tv_mdd_qu.setText(list.get(position).getEcounty().substring(0,list.get(position).getEcounty().length()-1));
        }else {
            holder.tv_mdd_qu.setText(list.get(position).getEcounty());
        }
        String yongcheleixing="";
        if (list.get(position).getUse_car_type().equals("0")){
            yongcheleixing="整车";
        }else if (list.get(position).getUse_car_type().equals("1")){
            yongcheleixing="零担";
        }else {
            yongcheleixing="";
        }
        if (yongcheleixing.equals("")){
            if (list.get(position).getLengthname().equals("")){
                holder.tv_huowuleixing.setText(list.get(position).getCargotypenames());
            }else {
                holder.tv_huowuleixing.setText(list.get(position).getLengthname().replace(" ","/")+"米 "+list.get(position).getCargotypenames());
            }

        }else {
            if (list.get(position).getLengthname().equals("")){
                holder.tv_huowuleixing.setText(yongcheleixing+" "+list.get(position).getCargotypenames());
            }else {
                holder.tv_huowuleixing.setText(yongcheleixing+" "+list.get(position).getLengthname().replace(" ","/")+"米 "+list.get(position).getCargotypenames());
            }

        }

        holder.tv_cheliangleixing.setText(list.get(position).getCartypenames().replace(" ","/"));
//        if (list.get(position).getWeight().equals("0")){
//            holder.tv_zhongliang.setText(list.get(position).getVolume() + "件");
//        }else if (list.get(position).getVolume().equals("0")){
//            holder.tv_zhongliang.setText(list.get(position).getWeight() + "kg");
//        }else {
//            holder.tv_zhongliang.setText(list.get(position).getWeight() + "kg/"+list.get(position).getVolume() + "件");
//        }

        if (list.get(position).getWeight().equals("0")){
            if (list.get(position).getVolume().equals("0")){
                holder.tv_zhongliang.setText(list.get(position).getTon_weight()+"吨");
            }else {
                holder.tv_zhongliang.setText(list.get(position).getTon_weight()+"吨/"+list.get(position).getVolume()+"件");
            }

        }else if (list.get(position).getVolume().equals("0")){
            if (list.get(position).getWeight().equals("0")){
                holder.tv_zhongliang.setText(list.get(position).getTon_weight()+"吨");
            }else {
                holder.tv_zhongliang.setText(list.get(position).getWeight()+"kg");
            }
        }else {
            holder.tv_zhongliang.setText(list.get(position).getWeight()+"kg/"+list.get(position).getVolume()+"件");
        }

//        holder.tv_cheliangleixing.setText(list.get(position).getCartypenames());
        DecimalFormat decimalFormat=new DecimalFormat("###0.00");
        String format = decimalFormat.format(Double.parseDouble(list.get(position).getSiji_money()));
        holder.tv_zongjia.setText("¥" +format );
//        holder.tv_zhuangcheshijian.setText(list.get(position).getShipmenttime());

        if (list.get(position).getCust_num()!=null){
            holder.tv_jiaoyiliang.setText("交易"+list.get(position).getCust_num()+"笔");
        }else {
            holder.tv_jiaoyiliang.setText("交易0笔");
        }
        if (list.get(position).getCust_star().equals("0")){
            holder.img1.setVisibility(View.GONE);
            holder.img2.setVisibility(View.GONE);
            holder.img3.setVisibility(View.GONE);
            holder.img4.setVisibility(View.GONE);
            holder.img5.setVisibility(View.GONE);
        }else if (list.get(position).getCust_star().equals("1")){
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.GONE);
            holder.img3.setVisibility(View.GONE);
            holder.img4.setVisibility(View.GONE);
            holder.img5.setVisibility(View.GONE);
        }else if (list.get(position).getCust_star().equals("2")){
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
            holder.img3.setVisibility(View.GONE);
            holder.img4.setVisibility(View.GONE);
            holder.img5.setVisibility(View.GONE);
        }else if (list.get(position).getCust_star().equals("3")){
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
            holder.img3.setVisibility(View.VISIBLE);
            holder.img4.setVisibility(View.GONE);
            holder.img5.setVisibility(View.GONE);
        }else if (list.get(position).getCust_star().equals("4")){
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
            holder.img3.setVisibility(View.VISIBLE);
            holder.img4.setVisibility(View.VISIBLE);
            holder.img5.setVisibility(View.GONE);
        }else if (list.get(position).getCust_star().equals("5")){
            holder.img1.setVisibility(View.VISIBLE);
            holder.img2.setVisibility(View.VISIBLE);
            holder.img3.setVisibility(View.VISIBLE);
            holder.img4.setVisibility(View.VISIBLE);
            holder.img5.setVisibility(View.VISIBLE);
        }


//        //查看
//        holder.tv_chakan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onClickItem != null) {
//                    onClickItem.onClick(holder.getAdapterPosition());
//                }
//            }
//        });
        holder.iv_iphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCallPhone != null) {
                    onCallPhone.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fhr,fh_sj,tv_cfd_shi,tv_cfd_qu,tv_mdd_shi,tv_mdd_qu,tv_huowuleixing,tv_cheliangleixing,tv_zhongliang,tv_zongjia,
                tv_zhuangcheshijian,tv_chakan;
        private TextView tv_jiaoyiliang;
        private ImageView iv_iphone;
        private CircleImageView mAvatar;
        private View view;
        private ImageView img1,img2,img3,img4,img5;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            mAvatar = (CircleImageView) itemView.findViewById(R.id.img_avatar);//头像
            tv_fhr= (TextView) itemView.findViewById(R.id.tv_fhr);//发货人的名字
            fh_sj= (TextView) itemView.findViewById(R.id.fh_sj1);//发货时间
            iv_iphone= (ImageView) itemView.findViewById(R.id.iv_iphone);//电话
            tv_cfd_shi= (TextView) itemView.findViewById(R.id.tv_cfd_shi);//出发地市
            tv_cfd_qu= (TextView) itemView.findViewById(R.id.tv_cfd_qu);//出发地区
            tv_mdd_shi= (TextView) itemView.findViewById(R.id.tv_mdd_shi);//目的地市
            tv_mdd_qu= (TextView) itemView.findViewById(R.id.tv_mdd_qu);//目的地区
            tv_huowuleixing= (TextView) itemView.findViewById(R.id.tv_huowuleixing);//货物类型
            tv_cheliangleixing= (TextView) itemView.findViewById(R.id.tv_cheliangleixing);//车辆类型
            tv_zhongliang= (TextView) itemView.findViewById(R.id.tv_zhongliang);//重量
            tv_zongjia= (TextView) itemView.findViewById(R.id.tv_zongjia);//总价
//            tv_zhuangcheshijian= (TextView) itemView.findViewById(R.id.tv_zhuangcheshijian);//装车时间
//            tv_chakan= (TextView) itemView.findViewById(R.id.tv_chakan);//查看详情

            tv_jiaoyiliang= (TextView) itemView.findViewById(R.id.tv_jiaoyiliang);
            img1= (ImageView) itemView.findViewById(R.id.img1);
            img2= (ImageView) itemView.findViewById(R.id.img2);
            img3= (ImageView) itemView.findViewById(R.id.img3);
            img4= (ImageView) itemView.findViewById(R.id.img4);
            img5= (ImageView) itemView.findViewById(R.id.img5);
        }
    }
}
