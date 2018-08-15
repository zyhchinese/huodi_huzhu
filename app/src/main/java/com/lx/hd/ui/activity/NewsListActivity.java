package com.lx.hd.ui.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lx.hd.R;
import com.lx.hd.adapter.NewsListAdapter;
import com.lx.hd.adapter.TabIndicatorAdapter;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.fragment.BaseRecyclerAdapter;
import com.lx.hd.ui.fragment.NewsListFragment;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

public class NewsListActivity extends BackCommonActivity {
    private FixedIndicatorView indicator;
    private IndicatorViewPager indicatorViewPager;
    private ArrayList<Fragment> fmList;
    private ViewPager viewPager;
    @Override
    protected int getContentView() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("最新动态");
        viewPager =(ViewPager)findViewById(R.id.guide_viewPager);
        indicator =(FixedIndicatorView)findViewById(R.id.guide_indicator);
        fmList=new ArrayList<>();
        fmList.add(new NewsListFragment());
        //   fmList.add(new ChargedOrderFragment());
        // 将viewPager和indicator使用
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        ViewPagerSetting();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    /**
     * 滑动界面tab标题的配置
     *
     */
    private void ViewPagerSetting() {

        // 指示条
        //  indicator.setScrollBar(new ColorBar(getContext(), R.color.colorAccent, 5));
        // 未选中字体大小
        float unSelectSize = 20;
        // 选中字体大小
        float selectSize = unSelectSize * 1.0f;
        // 未选中的颜色
        int unSelectColor = Color.parseColor("#bbbbbb");
        // 选中时的颜色
        int selectColor = ContextCompat.getColor(this,R.color.main_red);
        // 设置tab字体的变化
        indicator.setOnTransitionListener(new OnTransitionTextListener(
                selectSize, unSelectSize, selectColor, unSelectColor));
        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);

        // 设置适配器
        indicatorViewPager.setAdapter(new TabIndicatorAdapter(getSupportFragmentManager(),this,fmList));

        indicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                if(currentItem==1){
                    //   mLocation.setVisibility(View.VISIBLE);
                }else {
                    //     mLocation.setVisibility(View.INVISIBLE);
                }
                viewPager.setCurrentItem(currentItem);

            }
        });
    }
}

