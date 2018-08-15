package com.lx.hd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.MyReservation;

import java.util.List;

import io.reactivex.Observer;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/10/26.
 */

public class MyReservationAdapter extends RecyclerView.Adapter<MyReservationAdapter.ViewHolder>{
    private Context context;
    private List<MyReservation> myReservationList;
    private ClickItemChild1 clickItemChild;

    public void setClickItemChild(ClickItemChild1 clickItemChild) {
        this.clickItemChild = clickItemChild;
    }

    public MyReservationAdapter(Context context, List<MyReservation> myReservationList) {
        this.context=context;
        this.myReservationList=myReservationList;
    }

    public interface ClickItemChild1{
        void onClick(int position);
    }

    @Override
    public MyReservationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item3, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyReservationAdapter.ViewHolder holder, int position) {
        if (myReservationList.get(position).getIsvalid()==0){
            holder.img_dian.setImageResource(R.mipmap.icon_banner_select);
            holder.tv_daishenhe.setText("待审核");
            holder.tv_daishenhe.setTextColor(Color.YELLOW);


        }else if (myReservationList.get(position).getIsvalid()==1){
            holder.img_dian.setImageResource(R.mipmap.icon_green);
            holder.tv_daishenhe.setText("审核通过");
            holder.tv_daishenhe.setTextColor(Color.GREEN);
        }else {
            holder.img_dian.setImageResource(R.mipmap.icon_red);
            holder.tv_daishenhe.setText("审核未通过");
            holder.tv_daishenhe.setTextColor(Color.RED);
        }
        holder.tv_car.setText(myReservationList.get(position).getProname());
        holder.tv_name.setText(myReservationList.get(position).getUnname());
        holder.tv_phone.setText(myReservationList.get(position).getUnphone());
        holder.tv_time.setText(myReservationList.get(position).getCreatetime());
        holder.tv_zhushi.setText(myReservationList.get(position).getNote());
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemChild.onClick(holder.getAdapterPosition());
            }
        });



    }

    @Override
    public int getItemCount() {
        return myReservationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_dian,img_delete;
        private TextView tv_car,tv_daishenhe,tv_name,tv_phone,tv_time,tv_zhushi;
        public ViewHolder(View itemView) {
            super(itemView);
            img_dian= (ImageView) itemView.findViewById(R.id.img_dian);
            img_delete= (ImageView) itemView.findViewById(R.id.img_delete);
            tv_car= (TextView) itemView.findViewById(R.id.tv_car);
            tv_daishenhe= (TextView) itemView.findViewById(R.id.tv_daishenhe);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_zhushi= (TextView) itemView.findViewById(R.id.tv_zhushi);
        }
    }
}
