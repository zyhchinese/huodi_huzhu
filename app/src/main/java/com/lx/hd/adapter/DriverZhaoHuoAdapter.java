package com.lx.hd.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.bumptech.glide.Glide;
import com.lx.hd.R;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.DriverZhaohuoEntity;
import com.lx.hd.bean.DriverZhaohuoEntity1;
import com.lx.hd.bean.JuLiEntity;
import com.lx.hd.ui.activity.DriverZhaoHuoActivity;
import com.lx.hd.ui.activity.DriverZhaoHuoDetailsActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/1/16.
 */

public class DriverZhaoHuoAdapter extends RecyclerView.Adapter<DriverZhaoHuoAdapter.ViewHolder> implements RouteSearch.OnRouteSearchListener {
    private Context context;
    private List<DriverZhaohuoEntity1.Rows> list;
    private OnClickItem onClickItem;
    private OnClickItem1 onClickItem1;
    private FuZhi fuZhi;
    private double latitude;
    private double longitude;
    DecimalFormat decimalFormat = new DecimalFormat("###0.00");
    private final int ROUTE_TYPE_DRIVE = 0;
    private RouteSearch mRouteSearch;
    private boolean isok=true;

    private DriverZhaoHuoAdapter.ViewHolder holder;



    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setOnClickItem1(OnClickItem1 onClickItem1) {
        this.onClickItem1 = onClickItem1;
    }

    public void setFuZhi(FuZhi fuZhi) {
        this.fuZhi = fuZhi;
    }

    public DriverZhaoHuoAdapter(Context context, List<DriverZhaohuoEntity1.Rows> list, double latitude, double longitude) {
        this.context = context;
        this.list = list;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public interface OnClickItem {
        void onClick(int position);
    }

    public interface OnClickItem1 {
        void onClick(int position);
    }

    public interface FuZhi {
        void onClick(List<DriverZhaohuoEntity> list);
    }


    @Override
    public DriverZhaoHuoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.driver_zhaohuo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
//        if (isok){
//            isok=false;
//            mRouteSearch = new RouteSearch(context);
//            mRouteSearch.setRouteSearchListener(this);
//        }

//        System.out.println("onCreateViewHolder+路线信息拉取失败+");
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final DriverZhaoHuoAdapter.ViewHolder holder, int position) {
        this.holder = holder;
        Glide.with(context).load(Constant.BASE_URL + list.get(position).getFolder() + "/" + list.get(position).getAutoname()).into(holder.img_tu);
        holder.tv_name.setText(list.get(position).getOwner_link_name());
        String substring = list.get(position).getOwner_sendtime().substring(0, list.get(position).getOwner_sendtime().length() - 3);

        holder.tv_time.setText(substring);
        holder.tv_address.setText(list.get(position).getOwner_address());
        holder.tv_address1.setText(list.get(position).getOwner_send_address());
        holder.tv_car_type.setText(list.get(position).getCar_type());
        String format = decimalFormat.format(list.get(position).getSiji_money());
        holder.tv_money.setText("¥" + format);
        holder.tv_juli.setText("起点距您："+list.get(position).getLicheng()+"公里");
        holder.btn_xiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem != null) {
                    onClickItem.onClick(holder.getAdapterPosition());
                }
            }
        });
        holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem1 != null) {
                    onClickItem1.onClick(holder.getAdapterPosition());
                }
            }
        });
//        System.out.println("onBindViewHolder+路线信息拉取失败+");

//        LatLonPoint start = new LatLonPoint(latitude, longitude);
//        LatLonPoint end = new LatLonPoint(list.get(position).getLatitude(), list.get(position).getLongitude());
//        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT, start, end);

    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode, LatLonPoint startpoint, LatLonPoint endporint) {

        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startpoint, endporint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
//            showWaitDialog("正在加载...").setCancelable(false);
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_address;
        private TextView tv_address1;
        private TextView tv_car_type;
        private TextView tv_money;
        private TextView tv_juli;
        private Button btn_xiangqing;
        private CircleImageView img_tu;
        private ImageView img_call;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_address1 = (TextView) itemView.findViewById(R.id.tv_address1);
            tv_car_type = (TextView) itemView.findViewById(R.id.tv_car_type);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_juli = (TextView) itemView.findViewById(R.id.tv_juli);
            btn_xiangqing = (Button) itemView.findViewById(R.id.btn_xiangqing);
            img_tu = (CircleImageView) itemView.findViewById(R.id.img_tu);
            img_call = (ImageView) itemView.findViewById(R.id.img_call);
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        if (i == 1000) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    final DrivePath drivePath = driveRouteResult.getPaths()
                            .get(0);
                    double dis = drivePath.getDistance();
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                    BigDecimal b = new BigDecimal(dis / 1000);


                    double licheng = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

//                    holder.tv_juli.setText("起点距您：" + licheng + "公里");
//                    list.get(holder.getAdapterPosition()).setLicheng(licheng);
//                    notifyItemChanged(holder.getAdapterPosition());


                    System.out.println(licheng + "++++++++++++++++路线信息拉取失败");

                }
            }
        } else {
            System.out.println("路线信息拉取失败");
//            showToast("路线信息拉取失败");
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
