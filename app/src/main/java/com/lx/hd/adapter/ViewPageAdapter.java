package com.lx.hd.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5 0005.
 */

public class ViewPageAdapter extends PagerAdapter {
    private List<ImageView> list_view;
    private List<String> titles;

    public ViewPageAdapter(List<ImageView> list_view, List<String> titles) {
        this.list_view = list_view;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return list_view.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        View v = list_view.get(position);
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(list_view.get(position));
        return list_view.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            if (titles.size() >= position) {
                return titles.get(position);
            }
        }
        return "暂无标题";
    }

}


