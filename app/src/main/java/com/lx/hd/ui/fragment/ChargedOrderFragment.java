package com.lx.hd.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.adapter.ChargingOrderAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.fragment.BaseRecyclerAdapter;
import com.lx.hd.base.fragment.BaseRecyclerViewFragment;
import com.lx.hd.bean.ChargeOrder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChargedOrderFragment extends BaseRecyclerViewFragment<ChargeOrder> {

    private String content;

    // fix: 直接点击找人Tab的时候，doSearch调用requestData方法, 异步网络过程中,
    // 生命周期流程调用requestData, 前一次调用使isRefreshing致为false，导致数据重复
    private boolean isRequesting = false;
    public static Fragment instantiate(Context context) {
        return new ChargedOrderFragment();
    }
    @Override
    protected BaseRecyclerAdapter<ChargeOrder> getRecyclerAdapter() {
        return new ChargingOrderAdapter(getActivity(),BaseRecyclerAdapter.ONLY_FOOTER);
    }

    @Override
    protected Type getType() {
        return   new TypeToken<List<ChargeOrder>>() {
        }.getType();
    }
    @Override
    public void onRefreshing() {
        super.onRefreshing();

    }
    @Override
    protected void requestData() {
        super.requestData();
        HashMap<String,String> map=new HashMap<>();
        map.put("createtimeGE","");
        map.put("createtimeLE","");
        Gson gson=new Gson();
        String params=gson.toJson(map);
        PileApi.instance.getChargeOrdreList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mHandler);
    }

    @Override
    protected void onRequestFinish() {
        super.onRequestFinish();
        isRequesting = false;
    }

    @Override
    public void onItemClick(int position, long itemId) {
        super.onItemClick(position, itemId);


    }
    @Override
    protected boolean isNeedEmptyView() {
        return false;
    }

    @Override
    protected boolean isNeedCache() {
        return false;
    }


}