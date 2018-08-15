package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.SpinnerAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.ProvinceEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ApplyInfoActivity extends BackActivity implements View.OnClickListener {
    private ImageView img_icon;
    private EditText ed_shouhuoren, ed_phone_number, ed_address, ed_youbian;
    private Spinner spinner_city, spinner_province, spinner_county;
    private Button btn_save;

    private List<ProvinceEntity> provinceEntityList;
    private List<ProvinceEntity> cityList;
    private List<ProvinceEntity> countyList;
    private boolean isOk;
    private int i;


    private ProvinceEntity provinceEntity;
    private ProvinceEntity provinceEntity1;
    private ProvinceEntity provinceEntity2;


    @Override
    protected int getContentView() {
        return R.layout.activity_apply_info;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("申请信息");
        img_icon = (ImageView) findViewById(R.id.img_icon);
        img_icon.setVisibility(View.GONE);

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

                            if (body.indexOf("false") != -1 || body.length() < 3) {
                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                provinceEntityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                provinceEntityList.add(0, new ProvinceEntity("请选择省份"));

                                SpinnerAdapter adapter2 = new SpinnerAdapter(ApplyInfoActivity.this, provinceEntityList);
                                spinner_province.setAdapter(adapter2);


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
                                cityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                cityList.add(0, new ProvinceEntity("请选择城市"));

                                SpinnerAdapter adapter2 = new SpinnerAdapter(ApplyInfoActivity.this, cityList);
                                spinner_city.setAdapter(adapter2);

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
                                countyList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                countyList.add(0, new ProvinceEntity("请选择县区"));

                                SpinnerAdapter adapter2 = new SpinnerAdapter(ApplyInfoActivity.this, countyList);
                                spinner_county.setAdapter(adapter2);

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

    private void initSpinner() {
        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initCityHttp(provinceEntityList.get(position).getAreaid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("1111");

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

    private void initView() {

        ed_shouhuoren = (EditText) findViewById(R.id.ed_shouhuoren);
        ed_phone_number = (EditText) findViewById(R.id.ed_phone_number);
        ed_address = (EditText) findViewById(R.id.ed_address);
        ed_youbian = (EditText) findViewById(R.id.ed_youbian);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_province = (Spinner) findViewById(R.id.spinner_province);
        spinner_county = (Spinner) findViewById(R.id.spinner_county);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        initEdittextListener();


    }

    private void initEdittextListener() {
        i = 1;

        ed_shouhuoren.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                i = 1;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                i = 2;
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().indexOf(" ") != -1) {
                    i = 1;

                    isOk = false;
                } else {
                    ed_phone_number.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            i = 2;
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            i = 3;
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            boolean phone = phone(s.toString());

                            if (s.toString().indexOf(" ") != -1||!phone) {
                                i = 2;


                                isOk = false;
                            } else {
                                ed_address.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        i = 3;
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        i=4;
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                        if (s.toString().indexOf(" ") != -1) {

                                            i = 3;
                                            isOk = false;
                                        } else {
                                            i = 4;
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });


    }
    private boolean phone(String mobiles){
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @Override
    public void onClick(View v) {
        if (spinner_county.getSelectedItemPosition() != 0 && i > 3) {
            isOk = true;
        }

        if (!isOk) {
            if (i == 1) {
                Toast.makeText(ApplyInfoActivity.this, "请输入您的姓名", Toast.LENGTH_SHORT).show();
            } else if (i == 2) {
                Toast.makeText(ApplyInfoActivity.this, "请输入您的正确联系方式", Toast.LENGTH_SHORT).show();
            } else if (spinner_province.getSelectedItemPosition() == 0) {
                Toast.makeText(ApplyInfoActivity.this, "请选择省份", Toast.LENGTH_SHORT).show();
            } else if (spinner_city.getSelectedItemPosition() == 0) {
                Toast.makeText(ApplyInfoActivity.this, "请选择城市", Toast.LENGTH_SHORT).show();
            } else if (spinner_county.getSelectedItemPosition() == 0) {
                Toast.makeText(ApplyInfoActivity.this, "请选择县区", Toast.LENGTH_SHORT).show();
            } else if (i == 3) {
                Toast.makeText(ApplyInfoActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
            }

        } else {

            //请求
            HashMap<String, String> map = new HashMap<>();

            provinceEntity = (ProvinceEntity) spinner_province.getSelectedItem();
            map.put("provinceid", provinceEntity.getAreaid() + "");

            provinceEntity1 = (ProvinceEntity) spinner_city.getSelectedItem();
            map.put("cityid", provinceEntity1.getAreaid() + "");

            provinceEntity2 = (ProvinceEntity) spinner_county.getSelectedItem();
            map.put("areaid", provinceEntity2.getAreaid() + "");

            map.put("address", ed_address.getText().toString());
            map.put("status", "0");
            map.put("custname", ed_shouhuoren.getText().toString());
            map.put("custphone", ed_phone_number.getText().toString());
            map.put("reson", ed_youbian.getText().toString());
            Gson gson = new Gson();
            String data = gson.toJson(map);
            PileApi.instance.addMyEle(data)
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
                                    Toast.makeText(ApplyInfoActivity.this, "提交成功", Toast.LENGTH_SHORT).show();

                                    sendPayLocalReceiver();


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
}
