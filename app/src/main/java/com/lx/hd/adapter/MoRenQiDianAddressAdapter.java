package com.lx.hd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.bean.MoRenQiDianAddressEntity;
import com.lx.hd.ui.activity.MoRenQiDianAddressActivity;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/6/13.
 */

public class MoRenQiDianAddressAdapter extends RecyclerView.Adapter<MoRenQiDianAddressAdapter.ViewHolder>{

    private Context context;
    private List<MoRenQiDianAddressEntity.ResponseBean.SaddlistBean> list;
    private OnClickHang onClickHang;
    private OnClickMoRen onClickMoRen;
    private OnClickDelete onClickDelete;
    public MoRenQiDianAddressAdapter(Context context, List<MoRenQiDianAddressEntity.ResponseBean.SaddlistBean> list) {
        this.context=context;
        this.list=list;
    }

    public void setOnClickHang(OnClickHang onClickHang) {
        this.onClickHang = onClickHang;
    }

    public void setOnClickMoRen(OnClickMoRen onClickMoRen) {
        this.onClickMoRen = onClickMoRen;
    }

    public void setOnClickDelete(OnClickDelete onClickDelete) {
        this.onClickDelete = onClickDelete;
    }

    public interface OnClickHang{
        void onClick(int position);
    }
    public interface OnClickMoRen{
        void onClick(int position,boolean isChecked);
    }
    public interface OnClickDelete{
        void onClick(int position);
    }

    @Override
    public MoRenQiDianAddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.morendizhi_item, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickHang!=null){
                    onClickHang.onClick(viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoRenQiDianAddressAdapter.ViewHolder holder, final int position) {

        holder.tv_address.setText(list.get(position).getSaddress());
        if (list.get(position).getIsdefault()==1){
            holder.checkbox_moren.setChecked(true);
            holder.checkbox_moren.setButtonDrawable(R.mipmap.img_huodiaddresst);
        }else {
            holder.checkbox_moren.setChecked(false);
            holder.checkbox_moren.setButtonDrawable(R.mipmap.img_huodiaddressf);
        }


        holder.checkbox_moren.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onClickMoRen!=null){
                    onClickMoRen.onClick(position,isChecked);
                }
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickDelete!=null){
                    onClickDelete.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_address,tv_delete;
        private CheckBox checkbox_moren;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            tv_address= (TextView) itemView.findViewById(R.id.tv_address);
            tv_delete= (TextView) itemView.findViewById(R.id.tv_delete);
            checkbox_moren= (CheckBox) itemView.findViewById(R.id.checkbox_moren);
        }
    }
}
