package com.lx.hd.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.SpinnerAdapter;
import com.lx.hd.adapter.SpinnerAdapter1;
import com.lx.hd.adapter.SpinnerAdapter2;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CityEntity;
import com.lx.hd.bean.GetEntity;
import com.lx.hd.bean.ProvinceEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class UpdateAddressActivity extends BackCommonActivity implements View.OnClickListener {
    private Spinner spinner_city, spinner_province, spinner_county;
    private List<CityEntity> provinceEntityList;
    private List<CityEntity> cityList;
    private List<CityEntity> countyList;
    private EditText ed_shouhuoren, ed_phone_number, ed_guding, ed_address, ed_youbian, ed_biaoqian;
    private Button btn_save;

    private String id;
    private String name;
    private String phone;
    private String gdphone;
    private int province;
    private int city;
    private int county;
    private String address;
    private String postCode;
    private String addressLabel;




    @Override
    protected int getContentView() {
        return R.layout.activity_update_address;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("编辑地址");


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        gdphone = intent.getStringExtra("gdphone");
        province = intent.getIntExtra("province",1);
        city = intent.getIntExtra("city",1);
        county = intent.getIntExtra("county",1);
        address = intent.getStringExtra("address");
        postCode = intent.getStringExtra("postCode");
        addressLabel = intent.getStringExtra("addressLabel");



        initView();

        initSpinner();

        //请求省份
        PileApi.instance.loadProvince()
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

                            if (body.indexOf("false") != -1 || body.length() < 2) {
                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                provinceEntityList = gson.fromJson(body, new TypeToken<List<CityEntity>>() {
                                }.getType());

//                                provinceEntityList.add(0, new CityEntity("请选择省份"));


                                SpinnerAdapter1 adapter2 = new SpinnerAdapter1(UpdateAddressActivity.this, provinceEntityList);
                                spinner_province.setAdapter(adapter2);

//                                spinner_province.setSelection(province);





                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("2222");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });
    }

    //城市查询
    private void initCityHttp(int areaid) {

        HashMap<String, String> map = new HashMap<>();
        map.put("provinceid", areaid + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.loadCity(params)
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
                            if (body.indexOf("false") != -1 || body.length() < 2) {
                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                cityList = gson.fromJson(body, new TypeToken<List<CityEntity>>() {
                                }.getType());


//                                cityList.add(0, new CityEntity("请选择城市"));

                                SpinnerAdapter1 adapter2 = new SpinnerAdapter1(UpdateAddressActivity.this, cityList);
                                spinner_city.setAdapter(adapter2);
//                                spinner_city.setSelection(city);


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

    private void initView() {
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_province = (Spinner) findViewById(R.id.spinner_province);
        spinner_county = (Spinner) findViewById(R.id.spinner_county);
        ed_shouhuoren = (EditText) findViewById(R.id.ed_shouhuoren);
        ed_phone_number = (EditText) findViewById(R.id.ed_phone_number);
        ed_guding = (EditText) findViewById(R.id.ed_guding);
        ed_address = (EditText) findViewById(R.id.ed_address);
        ed_youbian = (EditText) findViewById(R.id.ed_youbian);
        ed_biaoqian = (EditText) findViewById(R.id.ed_biaoqian);

        btn_save = (Button) findViewById(R.id.btn_save);

        btn_save.setOnClickListener(this);


        ed_shouhuoren.setText(name);
        ed_phone_number.setText(phone);
        ed_guding.setText(gdphone);
        ed_address.setText(address);
        ed_youbian.setText(postCode);
        ed_biaoqian.setText(addressLabel);





    }

    private void initSpinner() {

        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initCityHttp(provinceEntityList.get(position).getAreaid());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initCountyHttp(cityList.get(position).getAreaid());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    //县区查询
    private void initCountyHttp(int areaid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("cityid", areaid + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.loadCountry(params)
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
                            if (body.indexOf("false") != -1 || body.length() < 2) {
                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                countyList = gson.fromJson(body, new TypeToken<List<CityEntity>>() {
                                }.getType());

//                                countyList.add(0, new CityEntity("请选择县区"));


                                SpinnerAdapter1 adapter2 = new SpinnerAdapter1(UpdateAddressActivity.this, countyList);
                                spinner_county.setAdapter(adapter2);
//                                spinner_county.setSelection(county);

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


    @Override
    public void onClick(View v) {

        //请求更新地址
        HashMap<String, String> map = new HashMap<>();

        CityEntity cityEntity = (CityEntity) spinner_province.getSelectedItem();
        map.put("provinceid", cityEntity.getAreaid() + "");

        CityEntity cityEntity1 = (CityEntity) spinner_city.getSelectedItem();
        map.put("cityid", cityEntity1.getAreaid() + "");

        CityEntity cityEntity2 = (CityEntity) spinner_county.getSelectedItem();
        map.put("areaid", cityEntity2.getAreaid() + "");

        map.put("address", ed_address.getText().toString());
        map.put("id", id);
        map.put("shcustname", ed_shouhuoren.getText().toString());
        map.put("shphone", ed_phone_number.getText().toString());
        map.put("taxphone", ed_guding.getText().toString());
        map.put("postcode", ed_youbian.getText().toString());
        map.put("addresslabel", ed_biaoqian.getText().toString());
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.updateCustAddress(data)
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
                            if (body.indexOf("false") != -1 || body.length() < 6) {
                                showToast("获取信息失败，请重试");
                            } else {
                                finish();
                                Toast.makeText(UpdateAddressActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
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
}
