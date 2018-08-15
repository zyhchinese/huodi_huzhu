package com.lx.hd.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.bean.MycarsEntity;
import com.lx.hd.interf.myCarListener;
import com.lx.hd.ui.activity.DZapplyActivity;
import com.lx.hd.ui.activity.MyCar1Activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/10/29.
 */

public class MycarsAdapter extends RecyclerView.Adapter<MycarsAdapter.ViewHolder> {


    private Context context;
    private List<MycarsEntity> mycarsEntityList;
    private myCarListener myraLlistener;
    public MycarsAdapter(Context context, List<MycarsEntity> mycarsEntityList) {
        myraLlistener= (myCarListener) context;
        this.context = context;
        this.mycarsEntityList = mycarsEntityList;
    }

    @Override
    public MycarsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.act_recyclerview_item6, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MycarsAdapter.ViewHolder holder, final int position) {

        holder.tv_car_name.setText("车牌号:" + mycarsEntityList.get(position).getCartype());
        holder.tv_shuliang.setText(mycarsEntityList.get(position).getMaxpeople());
        holder.sudu.setText(mycarsEntityList.get(position).getChesu());
        holder.xuhang1.setText(mycarsEntityList.get(position).getZongdianya());
        holder.tv_gonglv1.setText(mycarsEntityList.get(position).getZongdianliu());
        holder.tv_type1.setText(mycarsEntityList.get(position).getWendu());
        holder.tv_beizhu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new AlertDialog.Builder(context)
                        .setTitle("删除对话框")
                        .setMessage("确认删除吗？")
//相当于点击确认按钮
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //删除申请电桩
                                HashMap<String, String> map = new HashMap<>();
                                map.put("id",  mycarsEntityList.get(position).getId() + "");
                                Gson gson = new Gson();
                                String data = gson.toJson(map);
                                PileApi.instance.delMyCar(data)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<ResponseBody>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {

                                            }

                                            @Override
                                            public void onNext(@NonNull ResponseBody responseBody) {

                                                try {
                                                    String body = responseBody.string();
                                                    System.out.println(body);
                                                    if (body.indexOf("false") != -1 || body.length() < 6) {
                                                        Toast.makeText(context, "删除失败，请重试", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        myraLlistener.Ondelete(position);

                                                    }
                                                } catch (IOException e) {

                                                }
                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            }
                        })
//相当于点击取消按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
// TODO Auto-generated method stub
                            }
                        })
                        .create();
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mycarsEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_car_name, tv_shuliang, sudu, xuhang1, tv_gonglv1, tv_type1, tv_beizhu1;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_car_name = (TextView) itemView.findViewById(R.id.tv_car_name);
            tv_shuliang = (TextView) itemView.findViewById(R.id.tv_shuliang);
            sudu = (TextView) itemView.findViewById(R.id.sudu);
            xuhang1 = (TextView) itemView.findViewById(R.id.xuhang1);
            tv_gonglv1 = (TextView) itemView.findViewById(R.id.tv_gonglv1);
            tv_type1 = (TextView) itemView.findViewById(R.id.tv_type1);
            tv_beizhu1 = (TextView) itemView.findViewById(R.id.tv_beizhu1);
        }
    }
}
