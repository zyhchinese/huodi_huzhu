package com.lx.hd.base.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lx.hd.R;

public abstract class BackActivity extends BaseActivity {
    protected RelativeLayout mTitleBar;
    protected ImageView imgBack,imgIcon,imgSearch;
    protected TextView tvTitle;
    @Override
    protected void initWindow() {
        super.initWindow();
        mTitleBar = (RelativeLayout) findViewById(R.id.rly_title_root);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgIcon = (ImageView) findViewById(R.id.img_icon);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        if (mTitleBar != null) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void setTitleText(String titleText){
        if(tvTitle!=null){
            tvTitle.setText(titleText);
        }else {
            throw new IllegalStateException("如果有标题，请在布局中通过include标签添加后再操作");
        }
    }
    public void showSearchIcon(boolean b){
        if(imgSearch!=null ){
            if(b==true){
                imgSearch.setVisibility(View.VISIBLE);
            }else {
                imgSearch.setVisibility(View.GONE);
            }
        }else {
            throw new IllegalStateException("如果有标题，请在布局中通过include标签添加后再操作");
        }
    }
    public void setTitleIcon(int ids){
        if(imgIcon!=null){
            if(ids==-1){
                imgIcon .setVisibility(View.GONE);
                return;
            }
            imgIcon.setImageResource(ids);
        }else {
             throw new IllegalStateException("如果有标题，请在布局中通过include标签添加后再操作");
        }
    }
}
