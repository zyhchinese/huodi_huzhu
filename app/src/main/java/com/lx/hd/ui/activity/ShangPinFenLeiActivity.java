package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.hd.R;
import com.lx.hd.adapter.ShangPinFenLeiViewPagerAdapter;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.ui.fragment.ShangPinFenLeiFragment;

import java.util.ArrayList;
import java.util.List;

public class ShangPinFenLeiActivity extends BackCommonActivity implements View.OnClickListener {
    private TabLayout tab_layout;
    private ViewPager viewpage;
    private TextView tv_shouye,tv_gouwuche,tv_wode;
    private EditText ed_content;
    private TextView tv_sousuo;
    private ImageView img_saoma;
    private String [] tab=new String[3];
    private List<ShangPinFenLeiFragment> list=new ArrayList<>();
    private String protypeid1,protypeid2,protypeid3,proname1,proname2,proname3,proname;
    private int currentItem=0;


    @Override
    protected int getContentView() {
        return R.layout.activity_shang_pin_fen_lei11;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tab_layout= (TabLayout) findViewById(R.id.tab_layout);
        viewpage= (ViewPager) findViewById(R.id.viewpage);
        tv_shouye= (TextView) findViewById(R.id.tv_shouye);
        tv_gouwuche= (TextView) findViewById(R.id.tv_gouwuche);
        tv_wode= (TextView) findViewById(R.id.tv_wode);
        ed_content= (EditText) findViewById(R.id.ed_content);
        tv_sousuo= (TextView) findViewById(R.id.tv_sousuo);
        img_saoma= (ImageView) findViewById(R.id.img_saoma);

        tv_shouye.setOnClickListener(this);
        tv_gouwuche.setOnClickListener(this);
        tv_wode.setOnClickListener(this);
        tv_sousuo.setOnClickListener(this);
        img_saoma.setOnClickListener(this);

        protypeid1 = getIntent().getStringExtra("protypeid1");
        protypeid2 = getIntent().getStringExtra("protypeid2");
        protypeid3 = getIntent().getStringExtra("protypeid3");
        proname1 = getIntent().getStringExtra("proname1");
        proname2 = getIntent().getStringExtra("proname2");
        proname3 = getIntent().getStringExtra("proname3");
        proname = getIntent().getStringExtra("proname");
        tab[0]=proname1;
        tab[1]=proname2;
        tab[2]=proname3;

        for (int i = 0; i < 3; i++) {
            ShangPinFenLeiFragment fenLeiFragment=new ShangPinFenLeiFragment();
            list.add(fenLeiFragment);

        }

        tab_layout.addTab(tab_layout.newTab().setText(proname1));
        tab_layout.addTab(tab_layout.newTab().setText(proname2));
        tab_layout.addTab(tab_layout.newTab().setText(proname3));

        ShangPinFenLeiViewPagerAdapter adapter=new ShangPinFenLeiViewPagerAdapter(getSupportFragmentManager(),list,tab);
        viewpage.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewpage);
//        currentItem = viewpage.getCurrentItem();
        if (proname.equals(tab[0])){
            viewpage.setCurrentItem(0);
            list.get(0).initShuJu("2","1",protypeid1,"");
        }else if (proname.equals(tab[1])){
            viewpage.setCurrentItem(1);
            list.get(1).initShuJu("2","1",protypeid2,"");
        }else if (proname.equals(tab[2])){
            viewpage.setCurrentItem(2);
            list.get(2).initShuJu("2","1",protypeid3,"");
        }


        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    list.get(position).initShuJu("2","1",protypeid1,"");
                }else if (position==1){
                    list.get(position).initShuJu("2","1",protypeid2,"");
                }else if (position==2){
                    list.get(position).initShuJu("2","1",protypeid3,"");
                }

//                currentItem = viewpage.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_shouye:
                Intent intent=new Intent(this,ShangChengActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_gouwuche:
                Intent intent1=new Intent(this,ShoppingCartActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.tv_wode:
                Intent intent2=new Intent(this,OrderCenterActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.tv_sousuo:
                if (!ed_content.getText().toString().trim().equals("")){
                    Intent intent3=new Intent(this,ShangPinFenLei11Activity.class);
                    intent3.putExtra("name",ed_content.getText().toString().trim());
                    intent3.putExtra("gengduoid","");
                    startActivity(intent3);
                }else {
                    showToast("请输入商品名称");
                }

                break;
            case R.id.img_saoma:
                finish();
                break;
        }
    }
}
