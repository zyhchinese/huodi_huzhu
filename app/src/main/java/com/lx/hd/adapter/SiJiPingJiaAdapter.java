package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.SiJiPingJiaEntity;
import com.lx.hd.ui.activity.SiJiPingJiaActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */

public class SiJiPingJiaAdapter extends RecyclerView.Adapter<SiJiPingJiaAdapter.ViewHolder>{

    private Context context;
    private List<SiJiPingJiaEntity> list;

    public SiJiPingJiaAdapter(Context context, List<SiJiPingJiaEntity> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public SiJiPingJiaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sijipingjia_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SiJiPingJiaAdapter.ViewHolder holder, int position) {
        holder.tv_time.setText(list.get(position).getCreatetime());
        if (list.get(position).getDriver_fraction()==0){
            holder.img1.setImageResource(R.mipmap.img_pingjia_false);
            holder.img2.setImageResource(R.mipmap.img_pingjia_false);
            holder.img3.setImageResource(R.mipmap.img_pingjia_false);
            holder.img4.setImageResource(R.mipmap.img_pingjia_false);
            holder.img5.setImageResource(R.mipmap.img_pingjia_false);
        }else if (list.get(position).getDriver_fraction()==1){
            holder.img1.setImageResource(R.mipmap.img_pingjia_true);
            holder.img2.setImageResource(R.mipmap.img_pingjia_false);
            holder.img3.setImageResource(R.mipmap.img_pingjia_false);
            holder.img4.setImageResource(R.mipmap.img_pingjia_false);
            holder.img5.setImageResource(R.mipmap.img_pingjia_false);
        }else if (list.get(position).getDriver_fraction()==2){
            holder.img1.setImageResource(R.mipmap.img_pingjia_true);
            holder.img2.setImageResource(R.mipmap.img_pingjia_true);
            holder.img3.setImageResource(R.mipmap.img_pingjia_false);
            holder.img4.setImageResource(R.mipmap.img_pingjia_false);
            holder.img5.setImageResource(R.mipmap.img_pingjia_false);
        }else if (list.get(position).getDriver_fraction()==3){
            holder.img1.setImageResource(R.mipmap.img_pingjia_true);
            holder.img2.setImageResource(R.mipmap.img_pingjia_true);
            holder.img3.setImageResource(R.mipmap.img_pingjia_true);
            holder.img4.setImageResource(R.mipmap.img_pingjia_false);
            holder.img5.setImageResource(R.mipmap.img_pingjia_false);
        }else if (list.get(position).getDriver_fraction()==4){
            holder.img1.setImageResource(R.mipmap.img_pingjia_true);
            holder.img2.setImageResource(R.mipmap.img_pingjia_true);
            holder.img3.setImageResource(R.mipmap.img_pingjia_true);
            holder.img4.setImageResource(R.mipmap.img_pingjia_true);
            holder.img5.setImageResource(R.mipmap.img_pingjia_false);
        }else if (list.get(position).getDriver_fraction()==5){
            holder.img1.setImageResource(R.mipmap.img_pingjia_true);
            holder.img2.setImageResource(R.mipmap.img_pingjia_true);
            holder.img3.setImageResource(R.mipmap.img_pingjia_true);
            holder.img4.setImageResource(R.mipmap.img_pingjia_true);
            holder.img5.setImageResource(R.mipmap.img_pingjia_true);
        }
        holder.tv_note.setText(list.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_time,tv_note;
        private ImageView img1,img2,img3,img4,img5;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
            tv_note= (TextView) itemView.findViewById(R.id.tv_note);
            img1= (ImageView) itemView.findViewById(R.id.img1);
            img2= (ImageView) itemView.findViewById(R.id.img2);
            img3= (ImageView) itemView.findViewById(R.id.img3);
            img4= (ImageView) itemView.findViewById(R.id.img4);
            img5= (ImageView) itemView.findViewById(R.id.img5);
        }
    }
}
