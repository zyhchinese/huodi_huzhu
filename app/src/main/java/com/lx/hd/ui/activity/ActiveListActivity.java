package com.lx.hd.ui.activity;
/**
 * 活动列表activity  支持下拉加载
 */

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.adapter.ActiveListAdapter;
import com.lx.hd.adapter.CarPP_XQAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.PageBean;
import com.lx.hd.bean.activebean;
import com.lx.hd.bean.carhometopBean;
import com.lx.hd.mvp.CarDetialActivity;
import com.lx.hd.widgets.LoadListView;
import com.lx.hd.widgets.LoadListView.OnLoaderListener;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActiveListActivity extends BackCommonActivity implements OnLoaderListener {
    private LoadListView listview;
    private ActiveListAdapter adt;
    //    private ImageView shoushuo;
    //    private EditText et_input_pwd;
    List<activebean> listdata;
    int page1 = 1;
    LinearLayout linearLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_active_list;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("行业资讯");
        listview = (LoadListView) findViewById(R.id.car_pp);
        listview.setLoaderListener(this);
//        shoushuo = (ImageView) findViewById(R.id.shoushuo);
//        et_input_pwd = (EditText) findViewById(R.id.et_input_pwd);
        linearLayout = (LinearLayout) findViewById(R.id.lly_empty_view);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivedetailsActivity.show(ActiveListActivity.this, adt.getItem(position));
            }
        });
//        shoushuo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showWaitDialog("正在刷新信息...");
//                if (listdata != null) {
//                    listdata.clear();
//                }
//                page1 = 1;
//                getActiveList(et_input_pwd.getText().toString(), page1, 8);
//            }
//        });
        showWaitDialog("正在刷新信息...");
        getActiveList("", 1, 8);
    }

    private void updateInfo(List<activebean> CarPP) {
        if (CarPP != null) {
            if (adt == null) {
                listdata = CarPP;
                adt = new ActiveListAdapter(ActiveListActivity.this, listdata);
                listview.setAdapter(adt);
                listview.loadComplete();
            } else {
                listdata.addAll(CarPP);
                adt.setMarkerData(listdata);
                adt.notifyDataSetChanged();
                listview.loadComplete();
            }
        }
    }

    private void getActiveList(String groupname, int page, int rows) {
        HashMap<String, String> map = new HashMap<>();
        map.put("appstatus", "0");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.getActiveList(page, rows,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageBean<activebean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PageBean<activebean> responseBody) {

                        try {
                            if (responseBody == null || responseBody.getRows().size() == 0) {
                                //   if(body.equals("\"false\""))
                                responseBody.getRows();
                                page1 = page1 - 1;
                                listview.loadComplete();
                                if (listdata == null) {
                                    linearLayout.setVisibility(View.VISIBLE);
                                    hideWaitDialog();
                                }
                                if (listdata.size() == 0) {
                                    linearLayout.setVisibility(View.VISIBLE);
                                } else {
                                    showToast("暂无更多数据");
                                }
                            } else {
                                linearLayout.setVisibility(View.GONE);
                                List<activebean> fd = responseBody.getRows();
                                updateInfo(fd);
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
                getActiveList("", page1, 8);
            }
        }, 2000);


    }
}

