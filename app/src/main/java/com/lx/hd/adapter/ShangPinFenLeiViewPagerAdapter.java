package com.lx.hd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lx.hd.ui.fragment.ShangPinFenLeiFragment;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/7/3.
 */

public class ShangPinFenLeiViewPagerAdapter extends FragmentStatePagerAdapter{
    private String[] tab;
    private List<ShangPinFenLeiFragment> list;

    public ShangPinFenLeiViewPagerAdapter(FragmentManager fm, List<ShangPinFenLeiFragment> list, String[] tab) {
        super(fm);
        this.tab=tab;
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab[position];

    }
}
