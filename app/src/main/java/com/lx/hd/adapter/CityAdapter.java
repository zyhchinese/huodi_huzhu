package com.lx.hd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.ProvinceEntity;
import com.lx.hd.ui.activity.CityActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private Context context;
    private List<ProvinceEntity> cityList;
    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public CityAdapter(Context context, List<ProvinceEntity> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    public interface OnClickItem {
        void onClick(String name);
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item_province, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CityAdapter.ViewHolder holder, final int position) {

        holder.btn_province.setText(cityList.get(position).getAreaname());
        holder.btn_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ProvinceEntity provinceEntity : cityList) {
                    provinceEntity.setOk(false);
                }
                onClickItem.onClick(holder.btn_province.getText().toString());
                cityList.get(position).setOk(true);
                notifyDataSetChanged();
            }
        });

        if (cityList.get(position).isOk()) {
            holder.btn_province.setBackgroundResource(R.drawable.bg_orovince_bg);
            holder.btn_province.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.btn_province.setBackgroundResource(R.drawable.bg_orovince_bg_f);
            holder.btn_province.setTextColor(Color.parseColor("#6b6b6b"));
        }
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn_province;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_province = (Button) itemView.findViewById(R.id.btn_province);
        }
    }
}
