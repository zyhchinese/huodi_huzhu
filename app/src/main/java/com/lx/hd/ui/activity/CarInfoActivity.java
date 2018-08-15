package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.ProvinceEntity;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CarInfoActivity extends BackActivity implements View.OnClickListener {
    private ImageView img_icon;
    private EditText ed_shouhuoren, ed_phone_number, ed_address, ed_youbian, ed_address1, ed_youbian1, ed_beizhu;
    private Button btn_save;

    private boolean isOk;
    private int i;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_info;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("车辆信息");
        img_icon = (ImageView) findViewById(R.id.img_icon);
        img_icon.setVisibility(View.GONE);

        initView();
    }

    private void initView() {
        ed_shouhuoren = (EditText) findViewById(R.id.ed_shouhuoren);
        ed_phone_number = (EditText) findViewById(R.id.ed_phone_number);
        ed_address = (EditText) findViewById(R.id.ed_address);
        ed_youbian = (EditText) findViewById(R.id.ed_youbian);
        ed_address1 = (EditText) findViewById(R.id.ed_address1);
        ed_youbian1 = (EditText) findViewById(R.id.ed_youbian1);
        ed_beizhu = (EditText) findViewById(R.id.ed_beizhu);
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

                            if (s.toString().indexOf(" ") != -1) {
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

                                        i = 4;
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                        if (s.toString().indexOf(" ") != -1) {
                                            i = 3;

                                            isOk = false;
                                        } else {
                                            ed_youbian.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                    i = 4;
                                                }

                                                @Override
                                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                    i = 5;
                                                }

                                                @Override
                                                public void afterTextChanged(Editable s) {
                                                    if (s.toString().indexOf(" ") != -1) {
                                                        i = 4;

                                                        isOk = false;
                                                    } else {
                                                        isOk = true;
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
            }
        });

    }

    @Override
    public void onClick(View v) {

        if ("".equals(ed_shouhuoren.getText().toString())) {
            Toast.makeText(CarInfoActivity.this, "请输入车牌号", Toast.LENGTH_SHORT).show();
            return;
        } else if ("".equals(ed_phone_number.getText().toString())) {
            Toast.makeText(CarInfoActivity.this, "请输入车辆名称", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("cartype", ed_shouhuoren.getText().toString());
        map.put("maxpeople", ed_phone_number.getText().toString());
        map.put("maxspeed", ed_address.getText().toString());

        map.put("maxway", ed_youbian.getText().toString());
        map.put("power", ed_address1.getText().toString());
        map.put("engine", ed_youbian1.getText().toString());
        map.put("note", ed_beizhu.getText().toString());
        String s = ed_beizhu.getText().toString();
        System.out.println(s);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.addMyCar(data)
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
                            if(body.length()>3){
                                body=body.replace("\"","");

                            }
                            if ("false".equals(body)) {
                                showToast("获取信息失败，请重试");
                            } else if("true".equals(body)) {

                                finish();
                                Toast.makeText(CarInfoActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                sendPayLocalReceiver();


                            }else{
                                showToast(body);
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
