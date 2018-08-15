package com.lx.hd.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.CarPartsAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.fragment.BaseFragment;
import com.lx.hd.bean.CarParts;
import com.lx.hd.bean.PageBean;
import com.lx.hd.bean.PrimaryBanner;
import com.lx.hd.bean.PrimaryNews;
import com.lx.hd.bean.ShopListBean;
import com.lx.hd.bean.ShopListType;
import com.lx.hd.bean.walletBean;
import com.lx.hd.ui.activity.GoodsDetialActivity;
import com.lx.hd.utils.DividerGridItemDecoration;
import com.lx.hd.utils.GlideImageLoader;
import com.lx.hd.widgets.toprecyclerview.ItemOrderIn;
import com.lx.hd.widgets.toprecyclerview.ItemOrderTop;
import com.lx.hd.widgets.toprecyclerview.MyAdapter;
import com.lx.hd.widgets.toprecyclerview.OrderContent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class testFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopping;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

    }


}
