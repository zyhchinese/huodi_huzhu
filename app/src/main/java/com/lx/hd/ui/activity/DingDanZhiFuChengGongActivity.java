package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.KuaiYunAdapter;
import com.lx.hd.adapter.RecyclerAuditAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.AuditOrderEntity;
import com.lx.hd.bean.AuditOrderEntity1;
import com.lx.hd.bean.KuaiYunEntity;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
//TYPE=0速运  =1搬家   =2块运
public class DingDanZhiFuChengGongActivity extends BackCommonActivity {

    private RecyclerView act_recyclerView;
    private int type=0;
    List<AuditOrderEntity> auditOrderEntityList;
    List<AuditOrderEntity1> auditOrderEntityList1;
    private List<KuaiYunEntity> auditOrderEntityList2;
    private RecyclerAuditAdapter adapter;
    private KuaiYunAdapter adapter1;

    @Override
    protected int getContentView() {
        return R.layout.activity_ding_dan_zhi_fu_cheng_gong;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        type = getIntent().getIntExtra("type", 0);
        if (type==0){
            setTitleText("同城小件单");
        }else if (type==1){
            setTitleText("同城搬家单");
        }else {
            setTitleText("快运单");
        }

        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);

        if (this.type ==0|| this.type ==1){
            initDingDanSearch();
        }else {
            initKuaiyunshuju();
        }

    }

    public void initDingDanSearch() {
        if (type==0){
            PileApi.instance.searchSuyunorder()
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
//                            System.out.println(body);


                                if (body.indexOf("false") != -1 || body.length() < 10) {
                                    if (auditOrderEntityList != null) {
                                        auditOrderEntityList.clear();
                                        adapter.notifyDataSetChanged();
                                    }
                                    Toast.makeText(DingDanZhiFuChengGongActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
                                } else {
                                    Gson gson = new Gson();
                                    auditOrderEntityList = gson.fromJson(body, new TypeToken<List<AuditOrderEntity>>() {
                                    }.getType());
                                    //加载列表
                                    initRecyclerView();

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
        }else {
            PileApi.instance.searchBanjiaorder()
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
//                            System.out.println(body);


                                if (body.indexOf("false") != -1 || body.length() < 10) {
                                    if (auditOrderEntityList1 != null) {
                                        auditOrderEntityList1.clear();
                                        adapter.notifyDataSetChanged();
                                    }
                                    Toast.makeText(DingDanZhiFuChengGongActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
                                } else {
                                    Gson gson = new Gson();
                                    auditOrderEntityList1 = gson.fromJson(body, new TypeToken<List<AuditOrderEntity1>>() {
                                    }.getType());
                                    //加载列表
                                    initRecyclerView();

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

    }

    private void initRecyclerView() {

        if (type==0){
            adapter = new RecyclerAuditAdapter(this, auditOrderEntityList, type);
        }else {
            adapter = new RecyclerAuditAdapter(this, auditOrderEntityList1, type,0);
        }

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickChild(new RecyclerAuditAdapter.OnClickChild() {
            @Override
            public void onClick(int position) {

                if (type==0){
                    Intent intent=new Intent(DingDanZhiFuChengGongActivity.this, LogisticsOrderDetailsActivity.class);
                    intent.putExtra("orderno",auditOrderEntityList.get(position).getOrderno());
                    intent.putExtra("line","1");
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(DingDanZhiFuChengGongActivity.this, LogisticsOrderDetailsActivity1.class);
                    intent.putExtra("orderno",auditOrderEntityList1.get(position).getOwner_orderno());
                    intent.putExtra("line","0");
                    startActivity(intent);
                }
            }
        });


    }

    private void initKuaiyunshuju() {
        PileApi.instance.searchKuaiyunOrder()
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
//                            System.out.println(body);


                            if (body.indexOf("false") != -1 || body.length() < 10) {
                                if (auditOrderEntityList2 != null) {
                                    auditOrderEntityList2.clear();
                                    adapter1.notifyDataSetChanged();
                                }
                                Toast.makeText(DingDanZhiFuChengGongActivity.this, "暂无订单", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                auditOrderEntityList2 = gson.fromJson(body, new TypeToken<List<KuaiYunEntity>>() {
                                }.getType());
                                //加载列表
                                initView();

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

    private void initView() {

        adapter1=new KuaiYunAdapter(this,auditOrderEntityList2);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter1);
        adapter1.setOnClickItem(new KuaiYunAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(DingDanZhiFuChengGongActivity.this, LogisticsOrderDetailsActivity2.class);
                intent.putExtra("orderno",auditOrderEntityList2.get(position).getOrderno());
                intent.putExtra("line","2");
                startActivity(intent);
            }
        });

    }

}
