package com.lx.hd.ui.activity;

import android.widget.ListView;

import com.lx.hd.R;
import com.lx.hd.base.activity.BackActivity;

public class CarHomeBackActivity extends BackActivity {
    private ListView listview;

    @Override
    protected int getContentView() {
        return R.layout.activity_pile_apply;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleIcon(R.mipmap.dczj_icon);
        setTitleText("电车之家");
//        listview = (ListView) findViewById(R.id.car_pp);
//        List<CarPP> temp = new ArrayList<CarPP>();
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        temp.add(new CarPP("1", "宝马"));
//        listview.setAdapter(new CarPPAdapter(this, temp));
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(CarHomeActivity.this, CarHome_XQActivity.class));
//            }
//        });
    }
}
