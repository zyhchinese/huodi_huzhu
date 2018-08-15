package com.lx.hd.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lx.hd.R;
import com.lx.hd.ui.activity.OrderSearchsActivity;

/**
 * Created by Administrator on 2018/2/27.
 */

public class OrderFragment extends Fragment implements View.OnClickListener {
    private Button btn_suyun,btn_kuaiyun;
    private FrameLayout frame;
    private ImageView img_dingdan_search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        btn_suyun= (Button) view.findViewById(R.id.btn_suyun);
        btn_kuaiyun= (Button) view.findViewById(R.id.btn_kuaiyun);
        frame= (FrameLayout) view.findViewById(R.id.frame);
        img_dingdan_search= (ImageView) view.findViewById(R.id.img_dingdan_search);
        btn_suyun.setOnClickListener(this);
        btn_kuaiyun.setOnClickListener(this);
        img_dingdan_search.setOnClickListener(this);

        initFragment();

        return view;

    }

    private void initFragment() {
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.frame,new SuYunOrderFragment());
        transaction.commit();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_suyun:
                btn_suyun.setBackgroundColor(Color.parseColor("#c31023"));
                btn_suyun.setTextColor(Color.parseColor("#ffffff"));
                btn_kuaiyun.setBackgroundResource(R.drawable.huodi_btn_bg);
                btn_kuaiyun.setTextColor(Color.parseColor("#000000"));
                initFragment();
                break;
            case R.id.btn_kuaiyun:
                btn_suyun.setBackgroundResource(R.drawable.huodi_btn_bg);
                btn_suyun.setTextColor(Color.parseColor("#000000"));
                btn_kuaiyun.setBackgroundColor(Color.parseColor("#c31023"));
                btn_kuaiyun.setTextColor(Color.parseColor("#ffffff"));
                FragmentManager fragmentManager=getChildFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.frame,new KuaiYunOrderFragment());
                transaction.commit();
                break;
            case R.id.img_dingdan_search:
                Intent intent=new Intent(getContext(), OrderSearchsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
