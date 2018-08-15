package com.lx.hd.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.hd.R;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/26.
 */

public class TabIndicatorAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private String[] tabNames = { "正在充电" };
    ArrayList<Fragment> fmList;

    private LayoutInflater inflater;

    private Context mContext;
    public TabIndicatorAdapter(FragmentManager arg0,Context mContext,ArrayList<Fragment> fmList) {
        super(arg0);
        this.mContext=mContext;
        this.fmList=fmList;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public View getViewForTab(int position, View convertView,
                              ViewGroup container) {

        if (convertView == null) {
            convertView = (TextView) inflater.inflate(
                    R.layout.layout_tabtext, container, false);
        }
        TextView textView = (TextView) convertView;

        textView.setText(tabNames[position]);

        return textView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        // 填充Fragment界面,需要与tab数量相等
        switch (position) {
            case 0:
                return fmList.get(position);

//            case 1:
//                return fmList.get(position);

            default:
                return fmList.get(0);

        }

    }

}