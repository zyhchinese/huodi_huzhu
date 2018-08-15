package com.lx.hd.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.adapter.ChargingOrderAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.fragment.BaseRecyclerAdapter;
import com.lx.hd.base.fragment.BaseRecyclerViewFragment;
import com.lx.hd.bean.ChargeOrder;
import com.lx.hd.ui.activity.OrderDetialActivity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChargingOrderFragment extends BaseRecyclerViewFragment<ChargeOrder> {

    private String content;

    public String beginTimes="",finishTimes="";
    public static final String ACTION_UPDATE_DATA = "com.lx.hd.action.update.data";
    protected LocalBroadcastManager mManager;
    // fix: 直接点击找人Tab的时候，doSearch调用requestData方法, 异步网络过程中,
    // 生命周期流程调用requestData, 前一次调用使isRefreshing致为false，导致数据重复
    private boolean isRequesting = false;
    public static Fragment instantiate(String tempBeginTime,String tempFinishTime) {
        Bundle bundle = new Bundle();
        Fragment fragment = new ChargingOrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    protected boolean sendLocalReceiver() {
        if (mManager == null)
            mManager = LocalBroadcastManager.getInstance(getContext());
        if (mManager != null) {
            Intent intent = new Intent();
            intent.setAction(ACTION_UPDATE_DATA);
            return mManager.sendBroadcast(intent);
        }

        return false;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
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
        sendLocalReceiver();
        if (isRequesting) return;
        isRequesting = true;
        getData(beginTimes,finishTimes);
        beginTimes="";
        finishTimes="";

    }

    @Override
    protected void onRequestFinish() {
        super.onRequestFinish();
        isRequesting = false;
    }

    @Override
    public void onItemClick(int position, long itemId) {
        super.onItemClick(position, itemId);
        ChargeOrder chargeOrder = mAdapter.getItem(position);
        if (chargeOrder == null) return;
        OrderDetialActivity.show(getContext(),chargeOrder);

    }
    @Override
    protected boolean isNeedEmptyView() {
        return false;
    }

    @Override
    protected boolean isNeedCache() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mRecyclerView == null)
            return;
      //  mAdapter.clear();
        mRefreshLayout.setRefreshing(true);
        onRefreshing();
    }

    public void getData(String tempBeginTime,String tempFinishTime){
        HashMap<String,String> map=new HashMap<>();
        map.put("createtimeGE",tempBeginTime);
        map.put("createtimeLE",tempFinishTime);
        Gson gson=new Gson();
        String params=gson.toJson(map);
        PileApi.instance.getChargeOrdreList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mHandler);
    }
}
