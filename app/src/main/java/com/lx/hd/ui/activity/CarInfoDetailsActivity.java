package com.lx.hd.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.CarInfoDetailsEntity;
import com.lx.hd.bean.CarInfoEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CarInfoDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back, img_tu;
    private TextView tv_title,tv_type,tv_name,tv_waiguan,tv_huoxiang_nei,tv_huoxiang_kongjian,
            tv_max_zhiliang,tv_zaizhongliang,tv_zhengche,tv_dianjixingshi,tv_edinggonglv,
            tv_max_gonglv,tv_dianchi_type,tv_dianchi_rongliang,tv_xuhang,tv_chongdian_time,
            tv_chongdian_type;
    private String id;
    private List<CarInfoDetailsEntity> infoDetailsEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info_details);
        id = getIntent().getStringExtra("id");

        initView();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_tu = (ImageView) findViewById(R.id.img_tu);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_waiguan = (TextView) findViewById(R.id.tv_waiguan);
        tv_huoxiang_nei = (TextView) findViewById(R.id.tv_huoxiang_nei);
        tv_huoxiang_kongjian = (TextView) findViewById(R.id.tv_huoxiang_kongjian);
        tv_max_zhiliang = (TextView) findViewById(R.id.tv_max_zhiliang);
        tv_zaizhongliang = (TextView) findViewById(R.id.tv_zaizhongliang);
        tv_zhengche = (TextView) findViewById(R.id.tv_zhengche);
        tv_dianjixingshi = (TextView) findViewById(R.id.tv_dianjixingshi);
        tv_edinggonglv = (TextView) findViewById(R.id.tv_edinggonglv);
        tv_max_gonglv = (TextView) findViewById(R.id.tv_max_gonglv);
        tv_dianchi_type = (TextView) findViewById(R.id.tv_dianchi_type);
        tv_dianchi_rongliang = (TextView) findViewById(R.id.tv_dianchi_rongliang);
        tv_xuhang = (TextView) findViewById(R.id.tv_xuhang);
        tv_chongdian_time = (TextView) findViewById(R.id.tv_chongdian_time);
        tv_chongdian_type = (TextView) findViewById(R.id.tv_chongdian_type);

        img_back.setOnClickListener(this);

        initData();

    }

    private void initData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectCarDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {
                            String body = responseBody.string();
                            System.out.println(body);

                            if (body.indexOf("false") != -1 || body.length() < 10) {
                                Toast.makeText(CarInfoDetailsActivity.this, "暂无车辆详情信息", Toast.LENGTH_SHORT).show();

                            } else {
                                Gson gson = new Gson();
                                infoDetailsEntityList = gson.fromJson(body, new TypeToken<List<CarInfoDetailsEntity>>() {
                                }.getType());

                                initfuzhi();

                            }
                        } catch (IOException e) {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void initfuzhi() {
        tv_title.setText(infoDetailsEntityList.get(0).getCar_name());
        Glide.with(this).load(Constant.BASE_URL + infoDetailsEntityList.get(0).getFolder() +
                infoDetailsEntityList.get(0).getAutoname()).into(img_tu);
        tv_type.setText(infoDetailsEntityList.get(0).getTypename()+"基本参数");
        tv_name.setText(infoDetailsEntityList.get(0).getCar_name());
        if (infoDetailsEntityList.get(0).getOutside_size().equals("")){
            tv_waiguan.setText("无");
        }else {
            tv_waiguan.setText(infoDetailsEntityList.get(0).getOutside_size());
        }

        if (infoDetailsEntityList.get(0).getInside_size().equals("")){
            tv_huoxiang_nei.setText("无");
        }else {
            tv_huoxiang_nei.setText(infoDetailsEntityList.get(0).getInside_size());
        }

        if (infoDetailsEntityList.get(0).getCar_space().equals("")){
            tv_huoxiang_kongjian.setText("无");
        }else {
            tv_huoxiang_kongjian.setText(infoDetailsEntityList.get(0).getCar_space());
        }

        if (infoDetailsEntityList.get(0).getMax_weight().equals("")){
            tv_max_zhiliang.setText("无");
        }else {
            tv_max_zhiliang.setText(infoDetailsEntityList.get(0).getMax_weight());
        }

        if (infoDetailsEntityList.get(0).getRated_weight().equals("")){
            tv_zaizhongliang.setText("无");
        }else {
            tv_zaizhongliang.setText(infoDetailsEntityList.get(0).getRated_weight());
        }

        if (infoDetailsEntityList.get(0).getCar_weight().equals("")){
            tv_zhengche.setText("无");
        }else {
            tv_zhengche.setText(infoDetailsEntityList.get(0).getCar_weight());
        }

        if (infoDetailsEntityList.get(0).getElectric_mac_type().equals("")){
            tv_dianjixingshi.setText("无");
        }else {
            tv_dianjixingshi.setText(infoDetailsEntityList.get(0).getElectric_mac_type());
        }

        if (infoDetailsEntityList.get(0).getRated_power().equals("")){
            tv_edinggonglv.setText("无");
        }else {
            tv_edinggonglv.setText(infoDetailsEntityList.get(0).getRated_power());
        }

        if (infoDetailsEntityList.get(0).getMax_power().equals("")){
            tv_max_gonglv.setText("无");
        }else {
            tv_max_gonglv.setText(infoDetailsEntityList.get(0).getMax_power());
        }

        if (infoDetailsEntityList.get(0).getBattery_type().equals("")){
            tv_dianchi_type.setText("无");
        }else {
            tv_dianchi_type.setText(infoDetailsEntityList.get(0).getBattery_type());
        }

        if (infoDetailsEntityList.get(0).getCar_electricity().equals("")){
            tv_dianchi_rongliang.setText("无");
        }else {
            tv_dianchi_rongliang.setText(infoDetailsEntityList.get(0).getCar_electricity());
        }

        if (String.valueOf(infoDetailsEntityList.get(0).getCar_extension_mileage()).equals("")){
            tv_xuhang.setText("无");
        }else {
            tv_xuhang.setText(infoDetailsEntityList.get(0).getCar_extension_mileage()+"");
        }

        if (infoDetailsEntityList.get(0).getCharge_time().equals("")){
            tv_chongdian_time.setText("无");
        }else {
            tv_chongdian_time.setText(infoDetailsEntityList.get(0).getCharge_time());
        }

        if (infoDetailsEntityList.get(0).getCharge_type().equals("")){
            tv_chongdian_type.setText("无");
        }else {
            tv_chongdian_type.setText(infoDetailsEntityList.get(0).getCharge_type());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
