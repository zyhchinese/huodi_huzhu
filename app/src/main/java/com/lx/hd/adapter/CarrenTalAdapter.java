package com.lx.hd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.bean.CarrenTalViewbean;
import com.lx.hd.bean.CarrenTal_Cx;
import com.lx.hd.bean.Carrental_dz_y;
import com.lx.hd.bean.Lease_order_detailList;
import com.lx.hd.interf.CarrenTalListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 车辆租赁列表adapter
 * Created by TB on 2017/12/21.
 */

public class CarrenTalAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<Lease_order_detailList> mMarkerData = null;
    LayoutInflater inflater;
    private int spsl = 1;
    private List<CarrenTalViewbean> datas;
    private int type = 1;
    private String cs = "济南市";
    private String name;
    private CarrenTalListener Listener;
    private boolean flag = false;
    private ArrayList<CarrenTal_Cx> temp = null;

    public CarrenTalAdapter(Context context, List<Lease_order_detailList> markerItems, String cs, String name) {
        Listener = (CarrenTalListener) context;
        mContext = context;
        mMarkerData = markerItems;
        this.inflater = LayoutInflater.from(context);
        if (cs != null && !cs.equals("")) {
            this.cs = cs;
        }
        this.name = name;
        this.datas = new ArrayList<CarrenTalViewbean>();
        CarrenTalViewbean tempdata = new CarrenTalViewbean();
        tempdata.setCarcount(markerItems.get(0).getLease_count());
        tempdata.setType(markerItems.get(0).getOrder_type());
        tempdata.setCarname(name);
        this.datas.add(tempdata);
    }

    public void setMarkerData(List<Lease_order_detailList> markerItems) {
        mMarkerData = markerItems;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (null != mMarkerData) {
            count = mMarkerData.size();
        }
        return count;
    }

    public Lease_order_detailList getItem(int position) {
        Lease_order_detailList item = null;

        if (null != mMarkerData) {
            item = mMarkerData.get(position);
        }

        return item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_carrental, null);
            viewHolder.zj = (ImageView) convertView.findViewById(R.id.zj);
            viewHolder.js = (ImageView) convertView.findViewById(R.id.js);
            viewHolder.clxh = (RelativeLayout) convertView.findViewById(R.id.clxh);
            viewHolder.zcsc = (RelativeLayout) convertView.findViewById(R.id.zcsc);
            viewHolder.sl = (TextView) convertView.findViewById(R.id.sl);
            viewHolder.xz = (TextView) convertView.findViewById(R.id.xz);
            viewHolder.dz = (TextView) convertView.findViewById(R.id.dz);
            viewHolder.cz = (TextView) convertView.findViewById(R.id.cz);
            viewHolder.clxq = (TextView) convertView.findViewById(R.id.clxq);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.clzl_del = (ImageView) convertView.findViewById(R.id.clzl_del);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//
//        // set item values to the viewHolder:
//
        Lease_order_detailList CarPP = getItem(position);

        if (position == 0) {
            viewHolder.clzl_del.setVisibility(View.INVISIBLE);
            viewHolder.name.setText(name);
            if (flag) {
                if (name.equals("请选择")) {
                    viewHolder.xz.setText("请选择");
                }
                flag = false;
            }

        } else {
            viewHolder.clzl_del.setVisibility(View.VISIBLE);
        }
        type = 1;
        if (null != CarPP) {
            final ViewHolder finalViewHolder1 = viewHolder;
            viewHolder.name.setText(datas.get(position).getCarname());
            viewHolder.xz.setText(datas.get(position).getTime());
            viewHolder.zj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spsl = Integer.parseInt(finalViewHolder1.sl.getText().toString());
                    spsl++;
                    finalViewHolder1.sl.setText(spsl + "");
                    Lease_order_detailList bean = mMarkerData.get(position);
                    bean.setLease_count(spsl + "");
                    mMarkerData.set(position, bean);
                }
            });
            viewHolder.js.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spsl = Integer.parseInt(finalViewHolder1.sl.getText().toString());
                    if (spsl > 1) {
                        spsl--;
                        finalViewHolder1.sl.setText(spsl + "");
                        Lease_order_detailList bean = mMarkerData.get(position);
                        bean.setLease_count(spsl + "");
                        mMarkerData.set(position, bean);
                    }
                }
            });
            viewHolder.clxh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchleasecarName(finalViewHolder1.name, cs, position);
                    //showdialog(finalViewHolder1.name);

                }

            });
            viewHolder.dz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalViewHolder1.dz.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_bk_carrental_textview2));
                    finalViewHolder1.dz.setTextColor(Color.parseColor("#FFFFFF"));
                    finalViewHolder1.cz.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_bk_carrental_textview));
                    finalViewHolder1.cz.setTextColor(Color.parseColor("#555555"));
                    type = 0;
                    Lease_order_detailList bean = mMarkerData.get(position);
                    bean.setOrder_type(type + "");
                    mMarkerData.set(position, bean);
                }
            });
            viewHolder.cz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalViewHolder1.cz.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_bk_carrental_textview2));
                    finalViewHolder1.cz.setTextColor(Color.parseColor("#FFFFFF"));
                    finalViewHolder1.dz.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_bk_carrental_textview));
                    finalViewHolder1.dz.setTextColor(Color.parseColor("#555555"));
                    type = 1;
                    Lease_order_detailList bean = mMarkerData.get(position);
                    bean.setOrder_type(type + "");
                    mMarkerData.set(position, bean);
                }
            });
            viewHolder.clxq.setText("车辆需求（" + (position + 1) + "）");
            viewHolder.zcsc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getsearchtime(finalViewHolder1.xz, type, position);
                }
            });
            viewHolder.clzl_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Listener.delete(position);
                }
            });
        }

        return convertView;
    }

    public void showdialog(final TextView name, final ArrayList<Carrental_dz_y> temp, final ArrayList<CarrenTal_Cx> temp2, final int position) {
        final List<String> options1Items = new ArrayList<>();
        options1Items.clear();
        if (temp != null) {
            for (Carrental_dz_y bean : temp) {
                options1Items.add(bean.getTimename());
            }
        } else if (temp2 != null) {
            for (CarrenTal_Cx bean : temp2) {
                options1Items.add(bean.getCar_name());
            }
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                name.setText(options1Items.get(options1));
                if (temp != null) {
                    Lease_order_detailList bean = mMarkerData.get(position);
                    bean.setDuring_time(temp.get(options1).getDuring_time() + "");
                    mMarkerData.set(position, bean);
                    datas.get(position).setTime(options1Items.get(options1));
                } else if (temp2 != null) {
                    Lease_order_detailList bean = mMarkerData.get(position);
                    bean.setModel_id(temp2.get(options1).getId() + "");
                    mMarkerData.set(position, bean);
                    datas.get(position).setCarname(options1Items.get(options1));
                }
            }
        })
//                        .setSubmitText("确定")//确定按钮文字
//                        .setCancelText("取消")//取消按钮文字
//                        .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
//                        .setTitleSize(20)//标题文字大小
//                        .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#333333"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#333333"))//取消按钮文字颜色
//                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
//                        .setTextColorCenter(Color.BLUE)//设置选中项的颜色
                .setTextColorCenter(Color.parseColor("#000000"))//设置选中项的颜色
                .setDividerColor(Color.parseColor("#ffffff"))
                .setTitleBgColor(Color.parseColor("#ffffff"))
//                        .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                        .setLinkage(false)//设置是否联动，默认true
//                        .setLabels("省", "市", "区")//设置选择的三级单位
//                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .setCyclic(false, false, false)//循环与否
//                        .setSelectOptions(1, 1, 1)  //设置默认选中项
//                        .setOutSideCancelable(false)//点击外部dismiss default true
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();
    }

    public void getsearchtime(final View v, final int type, final int position) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type + "");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchtime(data)
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
                            ArrayList<Carrental_dz_y> temp = new Gson().fromJson(body, new TypeToken<ArrayList<Carrental_dz_y>>() {
                            }.getType());
                            if (temp.size() == 0) {
                                Toast.makeText(mContext, "该城市暂无车辆", Toast.LENGTH_LONG).show();
                                return;
                            }
                            showdialog((TextView) v, temp, null, position);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void searchleasecarName(final View v, final String cityname, final int position) {
        HashMap<String, String> map = new HashMap<>();
        map.put("cityname", cityname);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchleasecarName(data)
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
                            // Toast.makeText(mContext,body,Toast.LENGTH_LONG).show();
                            temp = new Gson().fromJson(body, new TypeToken<ArrayList<CarrenTal_Cx>>() {
                            }.getType());
                            if (temp.size() == 0) {
                                Toast.makeText(mContext, "该城市暂无车辆", Toast.LENGTH_LONG).show();
                                return;
                            }
                            showdialog((TextView) v, null, temp, position);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updatecs(String cs) {
        if (cs != null && !cs.equals("")) {
            this.cs = cs;
            name = cs;
        }
    }

    public void additem() {
        Lease_order_detailList bean = new Lease_order_detailList();
        bean.setLease_count(1 + "");
        bean.setOrder_type(1 + "");
        mMarkerData.add(bean);
        CarrenTalViewbean tempdata = new CarrenTalViewbean();
        tempdata.setCarcount(bean.getLease_count());
        tempdata.setType(bean.getOrder_type());
        datas.add(tempdata);
    }

    public void delete(int i) {
        mMarkerData.remove(i);
    }

    public void deleteall() {
        datas.clear();
        mMarkerData.clear();
        name = "请选择";
        flag = true;
    }

    public List<Lease_order_detailList> getmMarkerData() {
        return mMarkerData;
    }

    private static class ViewHolder {
        ImageView zj, js, clzl_del;
        TextView sl, name, dz, cz, clxq, xz;
        RelativeLayout clxh, zcsc;
    }

}
