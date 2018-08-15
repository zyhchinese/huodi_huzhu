package com.lx.hd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lx.hd.ui.fragment.AuditStatusFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class ViewPagerAuditAdapter extends FragmentStatePagerAdapter{
    private List<AuditStatusFragment> list;
    private String[] title;
    public ViewPagerAuditAdapter(FragmentManager fm, List<AuditStatusFragment> list, String[] title) {
        super(fm);
        this.list=list;
        this.title=title;
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
        return title[position];

    }


}
