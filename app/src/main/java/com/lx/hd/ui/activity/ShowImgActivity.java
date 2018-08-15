package com.lx.hd.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.lx.hd.R;
import com.lx.hd.utils.StringUtils2;
import com.lx.hd.widgets.imagezoom.HackyViewPager;
import com.lx.hd.widgets.imagezoom.ImageViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * TB
 * 圈子跳查看图片，暂时屏蔽以备用，调用代码请查看QZ长按处理部分
 */
public class ShowImgActivity extends AppCompatActivity {
    ImageViewPagerAdapter adapter;
    HackyViewPager pager;
    private List<String> mList = new ArrayList<>();
    String mInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_showimg);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("info")) {
                mInfo = bundle.getString("info");
            }
        }

        if (StringUtils2.isEmpty(mInfo)) {
            finish();
        } else {
            pager = (HackyViewPager) findViewById(R.id.pager);
            mList.add(mInfo);
            adapter = new ImageViewPagerAdapter(getSupportFragmentManager(), mList);
            pager.setAdapter(adapter);
        }
    }
}
