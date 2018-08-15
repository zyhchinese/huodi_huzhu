package com.lx.hd.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.adapter.ShangPinFenLeiAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.CheckSiJiRenZhengEntity;
import com.lx.hd.bean.ShangPinFenLeiEntity;
import com.lx.hd.ui.activity.DriverCertificationStateActivity;
import com.lx.hd.ui.activity.GoodsDetialActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by 赵英辉 on 2018/7/3.
 */

public class ShangPinFenLeiFragment extends Fragment{
    private RecyclerView act_recyclerView;
    private ShangPinFenLeiEntity shangPinFenLeiEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangpinfenlei, container, false);
        act_recyclerView= (RecyclerView) view.findViewById(R.id.act_recyclerView);

        return view;

    }

    public void initShuJu(String type,String ordertype,String protypeid,String sousuo){
        HashMap hashMap=new HashMap();
        hashMap.put("type",type);
        hashMap.put("ordertype",ordertype);
        hashMap.put("prodtype",protypeid);
        hashMap.put("searchstr",sousuo);
        Gson gson=new Gson();
        String params = gson.toJson(hashMap);
        PileApi.instance.selectMallProdsByParam(params)
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
                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                Gson gson=new Gson();
                                shangPinFenLeiEntity = gson.fromJson(body, ShangPinFenLeiEntity.class);

                                initRecyclerView();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

    public void initShuJu1(String type,String ordertype,String protypeid,String sousuo){
        HashMap hashMap=new HashMap();
        hashMap.put("type",type);
        hashMap.put("ordertype",ordertype);
        hashMap.put("prodtype",protypeid);
        hashMap.put("searchstr",sousuo);
        Gson gson=new Gson();
        String params = gson.toJson(hashMap);
        PileApi.instance.selectMallProdsByParam_type(params)
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
                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                Gson gson=new Gson();
                                shangPinFenLeiEntity = gson.fromJson(body, ShangPinFenLeiEntity.class);

                                initRecyclerView();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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

    private void initRecyclerView() {
        ShangPinFenLeiAdapter adapter=new ShangPinFenLeiAdapter(getContext(),shangPinFenLeiEntity.getResponse());
        GridLayoutManager manager=new GridLayoutManager(getContext(),2);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new ShangPinFenLeiAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getContext(), GoodsDetialActivity.class);
                intent.putExtra("proid",shangPinFenLeiEntity.getResponse().get(position).getId()+"");
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
    }
}
