package com.lx.hd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lx.hd.R;
import com.lx.hd.bean.ProvinceEntity;
import com.lx.hd.ui.activity.ProvinceActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder>{

    private Context context;
    private List<ProvinceEntity> provinceEntityList;
    private OnClickItemChild onClickItemChild;

    public void setOnClickItemChild(OnClickItemChild onClickItemChild) {
        this.onClickItemChild = onClickItemChild;
    }

    public ProvinceAdapter(Context context, List<ProvinceEntity> provinceEntityList) {
        this.context=context;
        this.provinceEntityList=provinceEntityList;
    }
    public interface OnClickItemChild{
        void onClick(int id);
    }

    @Override
    public ProvinceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item_province, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProvinceAdapter.ViewHolder holder, final int position) {
        holder.btn_province.setText(provinceEntityList.get(position).getAreaname());
        holder.btn_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (ProvinceEntity provinceEntity:provinceEntityList){
                        provinceEntity.setOk(false);
                }
                provinceEntityList.get(position).setOk(true);
                onClickItemChild.onClick(provinceEntityList.get(position).getAreaid());



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
