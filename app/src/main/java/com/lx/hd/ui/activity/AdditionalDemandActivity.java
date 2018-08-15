package com.lx.hd.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.AdditionalDemandListAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.bean.AdditionalDemandListBean;
import com.lx.hd.bean.AdditionalDemandProListBean;
import com.lx.hd.bean.AdditionalDemandProListBean;
import com.lx.hd.bean.DeliverGoodsCarDataBean;
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

/*
物流发货添加额外需求
 */
public class AdditionalDemandActivity extends BaseActivity {
    private AdditionalDemandListAdapter madapter;
    private myListView listview;
    private ImageView img_back;
    private TextView tv_title, submit;
    private String cs;
    private ArrayList<AdditionalDemandProListBean> updata;
    private String type;
    @Override
    protected int getContentView() {
        return R.layout.activity_additional_demand;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
//        setTitleIcon(R.mipmap.icon_car_home_samall);
//        setTitleText("车辆租赁");


        listview = (myListView) findViewById(R.id.listview);
        // tv_title = (TextView) findViewById(R.id.tv_title);
        submit = (TextView) findViewById(R.id.submit);
        //tv_title.setText("添加额外需求");
        img_back = (ImageView) findViewById(R.id.go_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        cs = intent.getStringExtra("cs");
        type = intent.getStringExtra("type");
        String data = intent.getStringExtra("data");
        //showToast(data);
        updata = new Gson().fromJson(data, new TypeToken<List<AdditionalDemandProListBean>>() {
        }.getType());
        searchCarServices();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                ArrayList<AdditionalDemandProListBean> datas = new ArrayList<AdditionalDemandProListBean>();
                String titles = "",table="";
                if(type.equals("0")) {
                    table="huodi_suyun_details";
                }else if(type.equals("1")){
                    table="logistics_owner_orderDetails";
                }
                for (AdditionalDemandListBean temp : madapter.getmMarkerData()) {

                    if (temp.isselect()) {
                        titles = titles + temp.getService_name() + " ";
                        datas.add(new AdditionalDemandProListBean(temp.getId() + "", temp.getService_price() + "",table));
                    }
                }

                intent.putExtra("datas", new Gson().toJson(datas));
                intent.putExtra("titles", titles);
                setResult(004, intent);
                finish();
            }
        });

    }

    private void setData(ArrayList<AdditionalDemandListBean> AdditionalDemandList) {
        if (updata.size() > 0) {
            for (int i = 0; i < AdditionalDemandList.size(); i++) {
                for (AdditionalDemandProListBean temp : updata) {
                    if (Integer.parseInt(temp.getOwner_service_id()) == AdditionalDemandList.get(i).getId()) {
                        AdditionalDemandList.get(i).setIsselect(true);
                    }
                }
            }

        }
        // showToast(new Gson().toJson(AdditionalDemandList));
        if (madapter == null) {
            madapter = new AdditionalDemandListAdapter(AdditionalDemandActivity.this, AdditionalDemandList);
        } else {
            madapter.getmMarkerData().clear();
            madapter.setMarkerData(AdditionalDemandList);
        }
        listview.setAdapter(madapter);
        madapter.notifyDataSetChanged();
    }

    private void searchCarServices() {
        showWaitDialog("正在刷新信息...");
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchCarServices(params)
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
                            if (body.length() < 10) {
                                //   if(body.equals("\"false\""))
                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                ArrayList<AdditionalDemandListBean> AdditionalDemandList = gson.fromJson(body, new TypeToken<List<AdditionalDemandListBean>>() {
                                }.getType());


                                setData(AdditionalDemandList);
                            }
                            hideWaitDialog();
                        } catch (IOException e) {
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
