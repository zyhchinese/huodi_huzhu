package com.lx.hd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.lx.hd.R;
import com.lx.hd.adapter.ViewPagerAuditAdapter;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.ui.fragment.AuditStatusFragment;

import java.util.ArrayList;
import java.util.List;

public class AuditStatusActivity extends BackActivity implements View.OnClickListener {
    private TabLayout tab_layout;
    private ViewPager view_pager;
    private ImageView img_search;
    private List<AuditStatusFragment> list=new ArrayList<>();
    private String[] title=new String[]{"未审核","已审核"};
    private int currentItem;
    private boolean isOk=true;


    @Override
    protected int getContentView() {
        return R.layout.activity_audit_status;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("租赁订单");
        showSearchIcon(true);
        setTitleIcon(R.mipmap.lease_list_car);
        tab_layout= (TabLayout) findViewById(R.id.tab_layout);
        view_pager= (ViewPager) findViewById(R.id.view_pager);
        img_search= (ImageView) findViewById(R.id.img_search);
        img_search.setImageResource(R.mipmap.img_search1);
        img_search.setOnClickListener(this);

        for (int i = 0; i < 2; i++) {
            AuditStatusFragment fragment=new AuditStatusFragment();
            fragment.type(i);
            list.add(fragment);
        }

        initView();

    }

    private void initView() {
        tab_layout.setDrawingCacheBackgroundColor(Color.WHITE);

        tab_layout.addTab(tab_layout.newTab().setText("未审核"));
        tab_layout.addTab(tab_layout.newTab().setText("已审核"));
        ViewPagerAuditAdapter adapter=new ViewPagerAuditAdapter(getSupportFragmentManager(),list,title);
        view_pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(view_pager);

        currentItem = view_pager.getCurrentItem();

        list.get(0).initDingDanSearch();
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
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (isOk){
                    tab_layout.setSelectedTabIndicatorColor(Color.GREEN);
                    isOk=false;
                }else {
                    tab_layout.setSelectedTabIndicatorColor(Color.parseColor("#e87627"));
                    isOk=true;
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,OrderSearchActivity.class);
        intent.putExtra("orderstatus",currentItem+"");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==2){
            String orderno = data.getStringExtra("orderno");
            String createtimebefore = data.getStringExtra("createtimebefore");
            String createtimeafter = data.getStringExtra("createtimeafter");
            String link_name = data.getStringExtra("link_name");
            String link_phone = data.getStringExtra("link_phone");
            list.get(currentItem).initDingDanSearch();
        }
    }
}
