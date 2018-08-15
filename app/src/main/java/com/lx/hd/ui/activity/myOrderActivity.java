package com.lx.hd.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.adapter.CarPP_XQAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.ChannelItem;
import com.lx.hd.bean.PageBean;
import com.lx.hd.bean.carhometopBean;
import com.lx.hd.interf.onOrderListener;
import com.lx.hd.mvp.CarDetialActivity;
import com.lx.hd.ui.fragment.myOrderFragment;
import com.lx.hd.utils.DensityUtils;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.widgets.ColumnHorizontalScrollView;
import com.lx.hd.widgets.LoadListView;
import com.lx.hd.widgets.LoadListView.OnLoaderListener;

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
 * 订单
 */
public class myOrderActivity extends BackCommonActivity implements onOrderListener {
    private ViewPager mViewPager;
    private String id;
    private DataReceiver dataReceiver;
    /**
     * 用户选择的分类列表
     */
    private ArrayList<ChannelItem> userChannelItems = new ArrayList<ChannelItem>();
    /**
     * 当前选中的栏目
     */
    private int columnSelectIndex = 0;
    /**
     * 屏幕的宽度
     */
    private int mScreeWidth = 0;
    /**
     * Item宽度
     */
    private int mItemWidth = 0;
    /**
     * 自定义的HorizontalScrollView
     */
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private LinearLayout mRadioGroup_content;
    private View view;

    @Override
    protected int getContentView() {
        return R.layout.activity_myorder;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("商城订单");
        //   String id = getIntent().getStringExtra("id");
        id = getIntent().getStringExtra("id");

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("abc");
        dataReceiver=new DataReceiver();
        registerReceiver(dataReceiver,intentFilter);

        PileApi.instance.mCheckLoginState()
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
                            if (body.equals("\"true\"")) {
                                // getUserInfo();
                            } else {
                                DialogHelper.getConfirmDialog(myOrderActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(myOrderActivity.this, LoginActivity.class));
                                    }
                                }, null).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        mViewPager = (ViewPager) findViewById(R.id.viewpage);
        mScreeWidth = DensityUtils.getWindowsWidth(this);
        mItemWidth = mScreeWidth / 5; // 一个Item宽度为屏幕的3分之一
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) findViewById(R.id.mColumnHorizontal);
        mRadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup);
        initColumData();
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(new myviewpageadt(getSupportFragmentManager()));
        /**
         * ViewPager切换监听的方法
         */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
                selectTab(position);
                initColumData();
                myOrderFragment thisfr = (myOrderFragment) mFragmentMap.get(position);
                thisfr.LoadDatas(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




    private void initColumData() {
        // TODO Auto-generated method stub
        userChannelItems = new ArrayList<ChannelItem>();
        ChannelItem item = new ChannelItem("全部");
        ChannelItem item1 = new ChannelItem("待支付");
        ChannelItem item2 = new ChannelItem("待发货");
        ChannelItem item3 = new ChannelItem("待收货");
        ChannelItem item4 = new ChannelItem("已完成");
        userChannelItems.add(item);
        userChannelItems.add(item1);
        userChannelItems.add(item2);
        userChannelItems.add(item3);
        userChannelItems.add(item4);
        initTabColumn();

    }



    /**
     * 初始化TabColumn栏目项
     */
    private void initTabColumn() {
        // TODO Auto-generated method stub
        mRadioGroup_content.removeAllViews();
        int count = userChannelItems.size();
        mColumnHorizontalScrollView.setParam(this, mScreeWidth,
                mRadioGroup_content);
        for (int i = 0; i < count; i++) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    mItemWidth, ViewPager.LayoutParams.WRAP_CONTENT);
//            params.leftMargin = 1;
//            params.rightMargin = 1;
            view = View.inflate(this, R.layout.item_mytab, null);
            TextView tv = (TextView) view.findViewById(R.id.tab_title);
            View line = (View) view.findViewById(R.id.tab_line);
            tv.setId(i);
            tv.setText(userChannelItems.get(i).getName());
            tv.setTextColor(Color.parseColor("#c30e21"));
            tv.setTextSize(13f);
            if (columnSelectIndex == i) {
                tv.setSelected(true);
                line.setSelected(true);
//                tv.setTextColor(Color.parseColor("#c30e21"));
                line.setBackgroundColor(Color.parseColor("#c30e21"));
            }
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != arg0) {
                            localView.setSelected(false);
                        } else {
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);

                        }
                    }
                }
            });
            mRadioGroup_content.addView(view, i, params);
        }
    }


    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreeWidth / 2;
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
        }
        // 判断是否选中
        for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
            View checkView = mRadioGroup_content.getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
            } else {
                ischeck = false;
            }
            checkView.setSelected(ischeck);
        }
    }

    /**
     * ViewPager的适配器
     *
     * @author zh
     */
    private class myviewpageadt extends FragmentStatePagerAdapter {

        public myviewpageadt(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        // 每个条目返回的Fragment
        @Override
        public Fragment getItem(int position) {
            // TODO Auto-generated method stub
            return createFragment(position);
        }

        // 一共多少个条目
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 5;
        }

        // 返回每个条目的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return "标签" + position;
        }
    }

    //private static Map<Integer, Fragment> mFragmentMap = new HashMap<Integer, Fragment>();
    private  SparseArray<Fragment> mFragmentMap = new SparseArray<Fragment>();

    public Fragment createFragment(int position) {
        // TODO Auto-generated method stub
        Fragment fragment = null;
        fragment = mFragmentMap.get(position);//在集合中去除Fragment
        if (fragment == null) {//如果没在集合中取出来  需要重新创建
            if (position == 0) {
                fragment = new myOrderFragment();
                myOrderFragment a = (myOrderFragment) fragment;
                a.LoadDatas(0);

//                myOrderFragment allfragment= (myOrderFragment) fragment;
//                allfragment.LoadDatas(0);

            } else if (position == 1) {
                fragment = new myOrderFragment();

            } else if (position == 3) {
                fragment = new myOrderFragment();
            } else if (position == 4) {
                fragment = new myOrderFragment();
            } else {
                fragment = new myOrderFragment();
            }
            if (fragment != null) {
                mFragmentMap.put(position, fragment);//把创建好的Fragment存放到集合中缓存起来
            }
        }
        return fragment;
    }


    @Override
    public void loadover() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                hideWaitDialog();
            }
        });
    }
    public class DataReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            myOrderFragment thisfr = (myOrderFragment) mFragmentMap.get(3);
            thisfr.LoadDatas(3);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dataReceiver);

    }
}

