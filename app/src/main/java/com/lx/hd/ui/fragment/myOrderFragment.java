package com.lx.hd.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.MyOrderAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.fragment.BaseFragment;
import com.lx.hd.bean.PageBean;
import com.lx.hd.bean.ProvinceEntity;
import com.lx.hd.bean.QueryListEntity;
import com.lx.hd.bean.myorderbean;
import com.lx.hd.bean.myorderbeanforlist;
import com.lx.hd.interf.onOrderListener;
import com.lx.hd.ui.activity.ConfirmOrderActivity;
import com.lx.hd.ui.activity.OrderDetailsActivity;
import com.lx.hd.ui.activity.OrderDetialActivity;
import com.lx.hd.widgets.LoadListView;
import com.lx.hd.widgets.myListView;

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

import static com.lx.hd.base.activity.BaseActivity.ACTION_PAY_FINISH_ALL;

/**
 * TB  订单fragment
 * 20171025
 */
public class myOrderFragment extends BaseFragment implements LoadListView.OnLoaderListener {
    LoadListView listview;
    int page1 = 1;
    List<myorderbean> listdata;
    private MyOrderAdapter adt;
    String orderstatus = "";//当前所在的类别
    boolean load = false;//是否在上拉加载
    private boolean isOk = false;
    private boolean isOk1 = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myorder;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        listview = findView(R.id.order_list);
        listview.setLoaderListener(this);

    }


    public void LoadDatas(int type) {

        orderstatus = "";
        listdata = new ArrayList<myorderbean>();
        page1 = 1;
        switch (type) {
            case 0:
                orderstatus = "";
                break;
            case 1:
                orderstatus = "0";
                break;
            case 2:
                orderstatus = "1";
                break;
            case 3:
                orderstatus = "2";
                break;
            case 4:
                orderstatus = "4";
                break;
            default:
                orderstatus = "";
                break;
        }
        getdata(1, 4, orderstatus);
    }

    private void updateInfo(List<myorderbean> myorderbean) {
        if (myorderbean != null) {
            if (adt == null || isOk) {
                isOk = false;
                listdata = myorderbean;
                adt = new MyOrderAdapter(listdata, getActivity());
                listview.setAdapter(adt);
//                setMyGridViewHeightBasedOnChildren(listview);
                listview.loadComplete();
                adt.setOnClickItemChild(new MyOrderAdapter.OnClickItemChild() {
                    @Override
                    public void onClick(int position) {
                        if (listdata != null && listdata.size() > 0) {
                            if (listdata.get(position).getOrderstatus() == 0) {
                                Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                                intent.putExtra("uuid", listdata.get(position).getUuid());
                                intent.putExtra("type", listdata.get(position).getType() + "");
                                startActivity(intent);
                            } else if (listdata.get(position).getOrderstatus() == 1 || listdata.get(position).getOrderstatus() == 4) {
                                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                                intent.putExtra("id", listdata.get(position).getId() + "");
                                intent.putExtra("type1", 0);
                                intent.putExtra("type", listdata.get(position).getType() + "");
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                                intent.putExtra("id", listdata.get(position).getId() + "");
                                intent.putExtra("type1", 1);
                                intent.putExtra("type", listdata.get(position).getType() + "");
                                startActivity(intent);

                            }

                        }
                    }
                });
                adt.setOnClickChild(new MyOrderAdapter.OnClickChild() {
                    @Override
                    public void onClick(final int position) {
//                        Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle("提示")
                                .setMessage("确定取消订单吗")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("orderno", adt.getActivityList().get(position).getOrderno());
                                        Gson gson = new Gson();
                                        String params = gson.toJson(map);
                                        PileApi.instance.canclePro(params)
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
//                                            Toast.makeText(mContext, body, Toast.LENGTH_SHORT).show();
                                                            if (body.indexOf("false") != -1 || body.length() < 6) {
                                                                showToast("取消订单失败，请重试");
                                                            } else {

                                                                isOk = true;
                                                                getdata(1, 4, orderstatus);
                                                                Toast.makeText(mContext, "取消成功", Toast.LENGTH_SHORT).show();
                                                                dialog.dismiss();


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
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();


                    }
                });

            } else {
                listdata.addAll(myorderbean);
                adt.setMarkerData(listdata);
                adt.notifyDataSetChanged();
                listview.loadComplete();
            }
        } else {
//            myorderbean.clear();
            adt.notifyDataSetChanged();
        }
    }

    private void getdata(int page, int rows, String orderstatus) {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderstatus", orderstatus);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getOrderList(params, page, rows)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageBean<myorderbean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PageBean<myorderbean> responseBody) {
                        try {
                            if (responseBody == null || responseBody.getRows().size() == 0) {
//                                //   if(body.equals("\"false\""))
//
                                page1 = page1 - 1;
                                listview.loadComplete();
                                listdata = responseBody.getRows();
                                if (listdata == null || listdata.size() == 0) {

                                    if (load) {
                                        hideWaitDialog();
                                        load = false;
                                        showToast("暂无更多数据");
                                        return;
                                    }
                                }
                                updateInfo(listdata);
                            } else {

                                List<myorderbean> fd = responseBody.getRows();

                                getOrderProList(fd, fd.size() - 1);

                            }
                            hideWaitDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        page1 = page1 - 1;
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page1 = page1 + 1;
                getdata(page1, 4, orderstatus);
                load = true;
            }
        }, 2000);

    }

    private void getOrderProList(final List<myorderbean> fd, final int no) {

        HashMap<String, String> map = new HashMap<>();
        String id = "";
        id = fd.get(no).getId() + "";
        map.put("orderid", id);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getOrderProList(params)
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
                            if (body.length() > 10 && !body.equals("false")) {
                                ArrayList<myorderbeanforlist> zlistdata = new Gson().fromJson(body, new TypeToken<ArrayList<myorderbeanforlist>>() {
                                }.getType());
                                fd.get(no).getItemlist().addAll(zlistdata);
                                int xb = no;
                                xb--;
                                if (xb >= 0) {
                                    getOrderProList(fd, xb);
                                } else {
                                    updateInfo(fd);
                                }

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        String a = e.toString();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        page1 = 1;
//        getdata(1, 4, orderstatus);
//    }


}
