package com.lx.hd.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.adapter.RecyclerViewCarAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CarInfoEntity;
import com.lx.hd.bean.OrderDetailsInfoEntity;
import com.lx.hd.utils.DialogHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CarAudit1Activity extends BackActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tv_city;
    private EditText ed_car_name;
    private LinearLayout linear12;
    private RecyclerView act_recyclerView;
    private List<CarInfoEntity> listCarInfo;
    private RecyclerViewCarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_car_audit1);
        //初始化控件
        initView();
        if (Constant.city.equals("")){
            //加载列表数据
            initCarInfo();
        }

    }

    private void initView() {
        img_back= (ImageView) findViewById(R.id.img_back);
        tv_city= (TextView) findViewById(R.id.tv_city);
        ed_car_name= (EditText) findViewById(R.id.ed_car_name);
        linear12= (LinearLayout) findViewById(R.id.linear12);
        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);

        img_back.setOnClickListener(this);
        linear12.setOnClickListener(this);
//        ed_car_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                //加载列表数据
//                initCarInfo();
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(ed_car_name.getWindowToken(), 0) ;
//                return false;
//            }
//        });
        ed_car_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                //加载列表数据
                initCarInfo();
            }
        });




    }

    private void initCarInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("cityname", tv_city.getText().toString().trim());
        map.put("car_name", ed_car_name.getText().toString().trim());
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchleasecar(params)
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
                                Toast.makeText(CarAudit1Activity.this, "暂无车辆信息", Toast.LENGTH_SHORT).show();
                                if (listCarInfo!=null){
                                    listCarInfo.clear();
                                    adapter.notifyDataSetChanged();
                                }

                            } else {
                                Gson gson = new Gson();
                                listCarInfo=gson.fromJson(body,new TypeToken<List<CarInfoEntity>>(){}.getType());

                                //加载列表
                                initRecyclerView();
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

    private void initRecyclerView() {
        adapter=new RecyclerViewCarAdapter(this,listCarInfo);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new RecyclerViewCarAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(CarAudit1Activity.this,CarInfoDetailsActivity.class);
                intent.putExtra("id",listCarInfo.get(position).getId()+"");
                startActivity(intent);
            }
        });
        adapter.setOnClickItemHang(new RecyclerViewCarAdapter.OnClickItemHang() {
            @Override
            public void onClick(int position) {

                initLogin(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.linear12:

                Intent intent=new Intent(this, ProvinceActivity.class);
                startActivity(intent);
                break;
        }
    }

    class LocalsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //收到广播后的处理

            String location = intent.getStringExtra("location");
            if (Constant.city.equals("")){
                tv_city.setText(location);

                //加载列表数据
                initCarInfo();
            }
//            if (Constant.city.equals("")&&location.equals("")){
//                //加载列表数据
//                initCarInfo();
//            }


        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!Constant.city.equals("")){
            tv_city.setText(Constant.city);

            //加载列表数据
            initCarInfo();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_car_audit1;
    }

    //判断登录
    private void initLogin(final int position) {
        PileApi.instance.mCheckLoginState()
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
                            if (body.equals("\"true\"")) {
                                Intent intent=new Intent(CarAudit1Activity.this,CarrenTalActivity.class);
                                intent.putExtra("id",listCarInfo.get(position).getId()+"");
                                intent.putExtra("name",listCarInfo.get(position).getCar_name());
                                startActivity(intent);
                            } else {

                                DialogHelper.getConfirmDialog(CarAudit1Activity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(CarAudit1Activity.this, LoginActivity.class));
                                    }
                                }, null).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
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
