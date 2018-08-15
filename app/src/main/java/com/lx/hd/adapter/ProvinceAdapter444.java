package com.lx.hd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lx.hd.R;
import com.lx.hd.bean.ErShouCheShouYeEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ProvinceAdapter444 extends RecyclerView.Adapter<ProvinceAdapter444.ViewHolder>{

    private Context context;
    private List<ErShouCheShouYeEntity.ResponseBean.CartypeListBean> provinceEntityList;
    private OnClickItemChild onClickItemChild;

    public void setOnClickItemChild(OnClickItemChild onClickItemChild) {
        this.onClickItemChild = onClickItemChild;
    }

    public ProvinceAdapter444(Context context, List<ErShouCheShouYeEntity.ResponseBean.CartypeListBean> provinceEntityList) {
        this.context=context;
        this.provinceEntityList=provinceEntityList;
    }
    public interface OnClickItemChild{
        void onClick(int id, String name);
    }

    @Override
    public ProvinceAdapter444.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item_province, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProvinceAdapter444.ViewHolder holder, final int position) {
        holder.btn_province.setText(provinceEntityList.get(position).getCartypename());
        holder.btn_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (ErShouCheShouYeEntity.ResponseBean.CartypeListBean provinceEntity:provinceEntityList){
                        provinceEntity.setOk(false);
                }
                provinceEntityList.get(position).setOk(true);
                onClickItemChild.onClick(provinceEntityList.get(position).getId(),provinceEntityList.get(position).getCartypename());



                notifyDataSetChanged();




            }
        });

        if (provinceEntityList.get(position).isOk()){
            holder.btn_province.setBackgroundResource(R.drawable.bg_orovince_bg);
            holder.btn_province.setTextColor(Color.parseColor("#ffffff"));
        }else {
            holder.btn_province.setBackgroundResource(R.drawable.bg_orovince_bg_f);
            holder.btn_province.setTextColor(Color.parseColor("#6b6b6b"));
        }


    }

    @Override
    public int getItemCount() {
        return provinceEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn_province;
        public ViewHolder(View itemView) {
            super(itemView);
            btn_province= (Button) itemView.findViewById(R.id.btn_province);

        }
    }
}
