package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.LogisticsFahuoAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.WuLiuFaHuoEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class FahuoOrderActivity extends BackActivity implements View.OnClickListener {

    private ImageView img_search;
    private RecyclerView act_recyclerView;
    private List<WuLiuFaHuoEntity> wuLiuFaHuoEntityList;
    private String orderno="";
    private String createtime="";
    private String owner_link_name="";
    private String owner_link_phone="";

    @Override
    protected int getContentView() {
        return R.layout.activity_fahuo_order;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("物流订单");
        showSearchIcon(true);
        setTitleIcon(R.mipmap.img_wuliudingdan);
        Intent intent=getIntent();
        if (intent.getStringExtra("orderno")!=null){
            orderno = intent.getStringExtra("orderno");
        }
        if (intent.getStringExtra("createtime")!=null){
            createtime = intent.getStringExtra("createtime");
        }
        if (intent.getStringExtra("owner_link_name")!=null){
            owner_link_name = intent.getStringExtra("owner_link_name");
        }
        if (intent.getStringExtra("owner_link_phone")!=null){
            owner_link_phone = intent.getStringExtra("owner_link_phone");
        }


        img_search = (ImageView) findViewById(R.id.img_search);
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        img_search.setImageResource(R.mipmap.img_search1);
        img_search.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //请求物流发货单列表
        initwuliufahuo();
    }

    private void initwuliufahuo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderno", orderno);
        map.put("createtime", createtime);
        map.put("owner_link_name", owner_link_name);
        map.put("owner_link_phone", owner_link_phone);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.searchsendorder(data)
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

                            if (body.indexOf("false") != -1 || body.length() < 3) {
                                showToast("暂无订单数据");
                            } else {
                                Gson gson = new Gson();
                                wuLiuFaHuoEntityList = gson.fromJson(body, new TypeToken<List<WuLiuFaHuoEntity>>() {
                                }.getType());

                                //物流发货单列表
                                initRecyclerView();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("2222");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });
    }

    //物流发货单列表
    private void initRecyclerView() {
        LogisticsFahuoAdapter adapter = new LogisticsFahuoAdapter(this, wuLiuFaHuoEntityList,2);
        LinearLayoutManager manager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new LogisticsFahuoAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(FahuoOrderActivity.this, LogisticsOrderDetailsActivity.class);
                intent.putExtra("orderno",wuLiuFaHuoEntityList.get(position).getOwner_orderno());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search:
                Intent intent=new Intent(this,OrderSearchsActivity.class);
                intent.putExtra("type","2");
                startActivity(intent);
//                startActivity(new Intent(this, DriverCertificationActivity.class));
                break;
        }
    }
}
