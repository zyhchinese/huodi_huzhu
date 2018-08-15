package com.lx.hd.base.fragment;

import android.content.Context;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huanghaibin_dev
 * on 2016/8/18.
 */

public abstract class BaseGeneralRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {
    protected Callback mCallBack;
    private List<T> mPreItems;

    public BaseGeneralRecyclerAdapter(Callback callback, int mode) {
        super(callback.getContext(), mode);
        mCallBack = callback;
        setState(STATE_LOADING, true);
    }


    @SuppressWarnings("UnusedReturnValue")
    public int addItems(List<T> items) {
        int filterOut = 0;
        if (items != null && !items.isEmpty()) {
            List<T> date = new ArrayList<>();
            if (mPreItems != null) {
                for (T d : items) {
                    if (!mPreItems.contains(d)) {
                        date.add(d);
                    } else {
                        filterOut++;
                    }
                }
            } else {
                date = items;
            }
            mPreItems = items;
            addAll(date);
        }
        return filterOut;
    }

    public void clearPreItems() {
        mPreItems = null;
    }

    @SuppressWarnings("unused")
    public interface Callback {
        RequestManager getImgLoader();

        Context getContext();

        Date getSystemTime();
    }
}
