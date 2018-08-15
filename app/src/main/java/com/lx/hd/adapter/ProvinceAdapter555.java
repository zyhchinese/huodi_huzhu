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
import com.lx.hd.bean.cxbean11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ProvinceAdapter555 extends RecyclerView.Adapter<ProvinceAdapter555.ViewHolder>{

    private Context context;
    private ArrayList<cxbean11> provinceEntityList;
    private OnClickItemChild onClickItemChild;

    public void setOnClickItemChild(OnClickItemChild onClickItemChild) {
        this.onClickItemChild = onClickItemChild;
    }

    public ProvinceAdapter555(Context context, ArrayList<cxbean11> provinceEntityList) {
        this.context=context;
        this.provinceEntityList=provinceEntityList;
    }
    public interface OnClickItemChild{
        void onClick(int id, String name);
    }

    @Override
    public ProvinceAdapter555.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item_province, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProvinceAdapter555.ViewHolder holder, final int position) {
        holder.btn_province.setText(provinceEntityList.get(position).getCartypename());
        holder.btn_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (cxbean11 provinceEntity:provinceEntityList){
                        provinceEntity.setType(false);
                }
                provinceEntityList.get(position).setType(true);
                onClickItemChild.onClick(Integer.parseInt(provinceEntityList.get(position).getId()),provinceEntityList.get(position).getCartypename());



                notifyDataSetChanged();




            }
        });

        if (provinceEntityList.get(position).isType()){
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
