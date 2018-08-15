package com.lx.hd.base.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.bean.NewsBean;
import com.lx.hd.bean.PageBean;
import com.lx.hd.bean.PrimaryNews;
import com.lx.hd.refresh.EmptyLayout;
import com.lx.hd.refresh.RecyclerRefreshLayout;
import com.lx.hd.utils.CacheManager;
import com.lx.hd.utils.TDevice;
import com.lx.hd.utils.gson.AppOperator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 基本列表类，重写getLayoutId()自定义界面
 */
public abstract class BaseObjectRecyclerViewFragment<T> extends BaseFragment implements
        RecyclerRefreshLayout.SuperRefreshLayoutListener,
        BaseRecyclerAdapter.OnItemClickListener,
        View.OnClickListener,
        BaseGeneralRecyclerAdapter.Callback {
    private final String TAG = this.getClass().getSimpleName();
    protected BaseRecyclerAdapter<T> mAdapter;
    protected RecyclerView mRecyclerView;
    protected RecyclerRefreshLayout mRefreshLayout;
    protected boolean isRefreshing;
    protected Observer<PageBean<T>> mHandler;
    protected List<T> mBean;
    protected String CACHE_NAME = getClass().getName();
    protected EmptyLayout mErrorLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler_view;
    }

    @Override
    protected void initWidget(View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mRefreshLayout = (RecyclerRefreshLayout) root.findViewById(R.id.refreshLayout);
        mErrorLayout = (EmptyLayout) root.findViewById(R.id.error_layout);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initData() {
        mBean = new ArrayList<>();
        mAdapter = getRecyclerAdapter();
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mErrorLayout.setOnLayoutClickListener(this);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState && getActivity() != null
                        && getActivity().getCurrentFocus() != null) {
                    TDevice.hideSoftKeyboard(getActivity().getCurrentFocus());
                }
            }
        });
        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mHandler=new Observer<PageBean<T>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PageBean<T> tPageBean) {
                        try {

                            PageBean<T> resultBean = tPageBean;
                            if (resultBean != null) {
                                setListData(resultBean);
                                onRequestSuccess(200);
                            } else {
//                                if (resultBean.getCode() == ResultBean.RESULT_TOKEN_ERROR) {
//                                    SimplexToast.show(getActivity(), resultBean.getMessage());
//                                }
                                mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            onError( e);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onRequestError();
                    }

                    @Override
                    public void onComplete() {
                        onRequestFinish();

                    }
                };

        boolean isNeedEmptyView = isNeedEmptyView();
        if (isNeedEmptyView) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            mRefreshLayout.setVisibility(View.GONE);
            mBean = new ArrayList<>();

            List<T> items = isNeedCache()
                    ? (List<T>) CacheManager.readListJson(getActivity(), CACHE_NAME, getCacheClass())
                    : null;

            mBean=items;
            //if is the first loading
            if (items == null) {
                mBean=new ArrayList<T>();
                onRefreshing();
            } else {
                mAdapter.addAll(mBean);
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRoot.post(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(true);
                        onRefreshing();
                    }
                });
            }
        } else {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                    onRefreshing();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        onRefreshing();
    }


    @Override
    public void onItemClick(int position, long itemId) {

    }

    @Override
    public void onRefreshing() {
        isRefreshing = true;
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, true);
        requestData();
    }

    @Override
    public void onLoadMore() {
        mAdapter.setState(isRefreshing ? BaseRecyclerAdapter.STATE_HIDE : BaseRecyclerAdapter.STATE_LOADING, true);
        requestData();
    }

    protected void requestData() {
    }

    protected void onRequestSuccess(int code) {

    }

    protected void onRequestFinish() {
        onComplete();
    }

    protected void onRequestError() {
        onComplete();
        if (mAdapter.getItems().size() == 0) {
            if (isNeedEmptyView()) mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOAD_ERROR, true);
        }
    }

    protected void onComplete() {
        mRefreshLayout.onComplete();
        isRefreshing = false;
    }

    protected void setListData(PageBean<T> resultBean) {
      //  mBean.setNextPageToken(resultBean.getResult().getNextPageToken());
        if (isRefreshing) {
     //       AppConfig.getAppConfig(getActivity()).set("system_time", resultBean.getTime());
            mBean=resultBean.getRows();
            mAdapter.clear();
            mAdapter.addAll(mBean);
      //      mBean.setPrevPageToken(resultBean.getResult().getPrevPageToken());
            mRefreshLayout.setCanLoadMore(true);
            if (isNeedCache()) {
                CacheManager.saveToJson(getActivity(), CACHE_NAME, mBean);
            }
        } else {
            if(resultBean.getRows().size() == 8) {
                mAdapter.addAll(resultBean.getRows());
            }
        }

        if (resultBean == null
                || resultBean.getRows().size() < 8)
            mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
//        mAdapter.setState(resultBean.getResult().getItems() == null
//                || resultBean.getResult().getItems().size() < 20
//                ? BaseRecyclerAdapter.STATE_NO_MORE
//                : BaseRecyclerAdapter.STATE_LOADING, true);

        if (mAdapter.getItems().size() > 0) {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mErrorLayout.setErrorType(
                    isNeedEmptyView()
                            ? EmptyLayout.NODATA
                            : EmptyLayout.HIDE_LAYOUT);
        }
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected abstract BaseRecyclerAdapter<T> getRecyclerAdapter();

    protected abstract Type getType();

    /**
     * 获取缓存bean的class
     */
    protected Class<T> getCacheClass() {
        return null;
    }

    @Override
    public Date getSystemTime() {
        return new Date();
    }

    /**
     * 需要缓存
     *
     * @return isNeedCache
     */
    protected boolean isNeedCache() {
        return true;
    }

    /**
     * 需要空的View
     *
     * @return isNeedEmptyView
     */
    protected boolean isNeedEmptyView() {
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    private void log(String msg) {
       // if (false)
         //   TLog.i(TAG, msg);
    }
}
