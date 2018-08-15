package com.lx.hd.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lx.hd.R;
import com.lx.hd.adapter.ViewPagerAuditAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/27.
 */

public class SuYunOrderFragment extends Fragment{

    private TabLayout tab_layout;
    private ViewPager view_pager;
    private List<AuditStatusFragment> list=new ArrayList<>();
    private String[] title=new String[]{"同城小件单","同城搬家单"};
    private int currentItem;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view!=null){
            ViewGroup viewGroup= (ViewGroup) view.getParent();
            if (viewGroup!=null){
                viewGroup.removeView(view);
            }
        }else {
            view = inflater.inflate(R.layout.fragment_suyun_order, container, false);
            tab_layout= (TabLayout) view.findViewById(R.id.tab_layout);
            view_pager= (ViewPager) view.findViewById(R.id.view_pager);
            list.clear();
            for (int i = 0; i < 2; i++) {
                AuditStatusFragment fragment=new AuditStatusFragment();
                fragment.type(i);
                list.add(fragment);
            }
            initView();
        }

        return view;

    }

    private void initView() {
        tab_layout.addTab(tab_layout.newTab().setText("同城小件单"));
        tab_layout.addTab(tab_layout.newTab().setText("同城搬家单"));
        ViewPagerAuditAdapter adapter=new ViewPagerAuditAdapter(getActivity().getSupportFragmentManager(),list,title);
        view_pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(view_pager);

        currentItem = view_pager.getCurrentItem();

//        list.get(0).initDingDanSearch();
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                list.get(position).initDingDanSearch();
                currentItem = view_pager.getCurrentItem();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
