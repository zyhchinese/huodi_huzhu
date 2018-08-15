package com.lx.hd.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.NewsListAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.fragment.BaseObjectRecyclerViewFragment;
import com.lx.hd.base.fragment.BaseRecyclerAdapter;
import com.lx.hd.base.fragment.BaseRecyclerViewFragment;
import com.lx.hd.bean.PrimaryNews;
import com.lx.hd.ui.activity.BannerActivity;
import com.lx.hd.ui.activity.NewsDetialActivity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends BaseObjectRecyclerViewFragment<PrimaryNews> {
    View headView;
    View footerView;
    private String content;
    private int page=1;

    // fix: 直接点击找人Tab的时候，doSearch调用requestData方法, 异步网络过程中,
    // 生命周期流程调用requestData, 前一次调用使isRefreshing致为false，导致数据重复
    private boolean isRequesting = false;
    public static Fragment instantiate(Context context) {
        return new NewsListFragment();
    }
    @Override
    protected BaseRecyclerAdapter<PrimaryNews> getRecyclerAdapter() {
        return new NewsListAdapter(getActivity(),BaseRecyclerAdapter.BOTH_HEADER_TWOFOOTER);
    }

    @Override
    protected Type getType() {
        return   new TypeToken<List<PrimaryNews>>() {
        }.getType();
    }
    @Override
    public void onRefreshing() {
        super.onRefreshing();

    }
    @Override
    protected void requestData() {
        super.requestData();
        page = isRefreshing ? 1 : ++page;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appstatus", "0");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.getNewsList(page,"100",data)
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
    protected void initWidget(View root) {
        super.initWidget(root);
        headView =getActivity().getLayoutInflater().inflate(R.layout.layout_news_list_header, (ViewGroup) mRecyclerView.getParent(), false);
        footerView =getActivity().getLayoutInflater().inflate(R.layout.layout_news_list_footer, (ViewGroup) mRecyclerView.getParent(), false);

    }

    @Override
    public void initData() {
        super.initData();
        mAdapter.setHeaderView(headView);
        mAdapter.setFooterView(footerView);
    }

    @Override
    public void onItemClick(int position, long itemId) {
        super.onItemClick(position, itemId);
        PrimaryNews primaryNews = mAdapter.getItem(position);
        if (primaryNews == null) return;
       NewsDetialActivity.show(getContext(),primaryNews);

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
