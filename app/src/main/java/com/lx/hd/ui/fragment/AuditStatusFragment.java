package com.lx.hd.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.RecyclerAuditAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.bean.AuditOrderEntity;
import com.lx.hd.bean.AuditOrderEntity1;
import com.lx.hd.ui.activity.AuditDetailsActivity;
import com.lx.hd.ui.activity.LogisticsOrderDetailsActivity;
import com.lx.hd.ui.activity.LogisticsOrderDetailsActivity1;
import com.lx.hd.ui.activity.LogisticsOrderDetailsActivity1s;
import com.lx.hd.ui.activity.LogisticsOrderDetailsActivitys;

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
 * Created by Administrator on 2017/12/19.
 */

public class AuditStatusFragment extends Fragment {
    private RecyclerView act_recyclerView;
    private int type=0;
    List<AuditOrderEntity> auditOrderEntityList;
    List<AuditOrderEntity1> auditOrderEntityList1;
    private RecyclerAuditAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audit_status, container, false);

        act_recyclerView = (RecyclerView) view.findViewById(R.id.act_recyclerView);

//        //加载租赁订单列表数据
//        initDingDanSearch();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        initDingDanSearch();
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
//                                    Toast.makeText(getContext(), "暂无订单", Toast.LENGTH_SHORT).show();
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
//                                    Toast.makeText(getContext(), "暂无订单", Toast.LENGTH_SHORT).show();
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
            adapter = new RecyclerAuditAdapter(getContext(), auditOrderEntityList, type);
        }else {
            adapter = new RecyclerAuditAdapter(getContext(), auditOrderEntityList1, type,0);
        }

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickChild(new RecyclerAuditAdapter.OnClickChild() {
            @Override
            public void onClick(int position) {

                if (type==0){
                    if (auditOrderEntityList.get(position).getPaytype()>4){
                        Intent intent=new Intent(getContext(), LogisticsOrderDetailsActivitys.class);
                        intent.putExtra("orderno",auditOrderEntityList.get(position).getOrderno());
                        intent.putExtra("line","1");
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent(getContext(), LogisticsOrderDetailsActivity.class);
                        intent.putExtra("orderno",auditOrderEntityList.get(position).getOrderno());
                        intent.putExtra("line","1");
                        startActivity(intent);
                    }

                }else {
                    if (auditOrderEntityList1.get(position).getPaytype()>4){
                        Intent intent=new Intent(getContext(), LogisticsOrderDetailsActivity1s.class);
                        intent.putExtra("orderno",auditOrderEntityList1.get(position).getOwner_orderno());
                        intent.putExtra("line","0");
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent(getContext(), LogisticsOrderDetailsActivity1.class);
                        intent.putExtra("orderno",auditOrderEntityList1.get(position).getOwner_orderno());
                        intent.putExtra("line","0");
                        startActivity(intent);
                    }

                }

//                Intent intent = new Intent(getContext(), AuditDetailsActivity.class);
//                intent.putExtra("type", type);
//                intent.putExtra("order_id", auditOrderEntityList.get(position).getId() + "");
//                intent.putExtra("orderno", auditOrderEntityList.get(position).getOrderno());
//                intent.putExtra("createtime", auditOrderEntityList.get(position).getCreatetime());
//                intent.putExtra("shijiquche_name", auditOrderEntityList.get(position).getShijiquche_name());
//                intent.putExtra("shijiquche_linkerphone", auditOrderEntityList.get(position).getShijiquche_linkerphone());
//                intent.putExtra("shijiquche_time", auditOrderEntityList.get(position).getShijiquche_time());
//                intent.putExtra("shijiquche_address", auditOrderEntityList.get(position).getShijiquche_address());
//                intent.putExtra("sumcarnum", auditOrderEntityList.get(position).getSumcarnum() + "");
//                intent.putExtra("take_time", auditOrderEntityList.get(position).getTake_time());
//                intent.putExtra("orderstatus", auditOrderEntityList.get(position).getOrderstatus());
//                intent.putExtra("note", auditOrderEntityList.get(position).getNote());
//                intent.putExtra("order_money", auditOrderEntityList.get(position).getOrder_money() + "");
//                intent.putExtra("link_name", auditOrderEntityList.get(position).getLink_name());
//                intent.putExtra("link_phone", auditOrderEntityList.get(position).getLink_phone());
//                startActivityForResult(intent, 2);
            }
        });


    }

    public void type(int type) {
        this.type = type;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3) {
            initDingDanSearch();
        }
    }
}
