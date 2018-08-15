package com.lx.hd.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.adapter.QueryListAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.OrderDetailsInfoEntity;
import com.lx.hd.bean.QueryListEntity;
import com.lx.hd.utils.DialogHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class AddressActivity extends BackCommonActivity implements View.OnClickListener {
    private ImageView img_add;
    private RecyclerView act_recyclerView;
    private QueryListAdapter adapter;
    private List<QueryListEntity> queryList;
    private int line;


    private boolean isLogin = false;

    private int province;
    private int city;
    private int county;


    @Override
    protected int getContentView() {
        return R.layout.activity_address;
    }



    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("地址管理");

        Intent intent = getIntent();
        line = intent.getIntExtra("line", 2);
        initView();

        queryAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryAddress();
    }

    private void initView() {
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        img_add = (ImageView) findViewById(R.id.img_add);
        img_add.setOnClickListener(this);

    }

    //加载查询地址列表
    private void queryAddress() {
        PileApi.instance.searchCustAddress()
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
                                showToast("暂无地址，请添加");
                            } else {
                                Gson gson = new Gson();
                                queryList = gson.fromJson(body, new TypeToken<List<QueryListEntity>>() {
                                }.getType());

                                update();
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

    private void update() {
        adapter = new QueryListAdapter(this, queryList, line);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        //默认地址
        adapter.setClickChildItem(new QueryListAdapter.ClickChildItem1() {
            @Override
            public void onClick(final int position) {

                HashMap<String, String> map = new HashMap<>();
                map.put("id", queryList.get(position).getId() + "");
                Gson gson = new Gson();
                String data = gson.toJson(map);
                PileApi.instance.updCustAddressDefault(data)
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
                                        showToast("设置默认地址失败");
                                    } else {

                                        Toast.makeText(AddressActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                        queryAddress();
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
        });
        //删除地址
        adapter.setClickChildItem2(new QueryListAdapter.ClickChildItem2() {
            @Override
            public void onClick(final int position) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddressActivity.this)
                        .setTitle("提示")
                        .setMessage("确定删除么")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                HashMap<String, String> map = new HashMap<>();
                                map.put("id", queryList.get(position).getId() + "");
                                Gson gson = new Gson();
                                String data = gson.toJson(map);
                                PileApi.instance.delCustAddress(data)
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

                                                        queryList.clear();
                                                        adapter.notifyDataSetChanged();
                                                        Toast.makeText(AddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                        queryAddress();
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
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder1.show();
            }
        });

        //更新地址
        adapter.setClickChildItem3(new QueryListAdapter.ClickChildItem3() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(AddressActivity.this, UpdateAddressActivity.class);
                intent.putExtra("id", queryList.get(position).getId() + "");
                intent.putExtra("name", queryList.get(position).getShcustname());
                intent.putExtra("phone", queryList.get(position).getShphone());
                intent.putExtra("gdphone", queryList.get(position).getTaxphone());
                intent.putExtra("province", province);
                intent.putExtra("city", city);
                intent.putExtra("county", county);
                intent.putExtra("address", queryList.get(position).getAddress());
                intent.putExtra("postCode", queryList.get(position).getPostcode());
                intent.putExtra("addressLabel", queryList.get(position).getAddresslabel());
                startActivity(intent);
            }
        });
        //选择当前地址
        adapter.setClickChildItem4(new QueryListAdapter.ClickChildItem4() {
            @Override
            public void onClick(int position, String name, String number, String address) {

                Intent intent=new Intent();
                intent.putExtra("name",name);
                intent.putExtra("number",number);
                intent.putExtra("address",address);
                setResult(9,intent);
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        province = data.getIntExtra("province", 1);
//        city = data.getIntExtra("city", 1);
//        county = data.getIntExtra("county", 1);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditAddressActivity.class);
//        startActivity(intent);
        startActivityForResult(intent,1);
    }
}
