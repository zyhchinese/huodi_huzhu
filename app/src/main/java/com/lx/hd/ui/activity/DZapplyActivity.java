package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.DZapplyAdapter;
import com.lx.hd.adapter.SpinnerAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.bean.DZapplyEntity;
import com.lx.hd.bean.ProvinceEntity;

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

public class DZapplyActivity extends BackActivity {
    private RecyclerView act_recyclerView;
    private Button btn_apply;
    private List<DZapplyEntity> dZapplyEntityList=new ArrayList<>();
    private DZapplyAdapter adapter;


    @Override
    protected int getContentView() {
        return R.layout.activity_dzapply;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("电桩申请");
        setTitleIcon(R.mipmap.img_chongdian);

        initView();

        //请求电桩列表
        initDZapplyHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDZapplyHttp();

    }

    private void initDZapplyHttp() {

        PileApi.instance.getMyApply1()
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
                                showToast("暂无电桩申请，请添加");
                            } else {
                                Gson gson = new Gson();
                                dZapplyEntityList = gson.fromJson(body, new TypeToken<List<DZapplyEntity>>() {
                                }.getType());

                                initRecyclerView();


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

    private void initRecyclerView() {
        adapter = new DZapplyAdapter(this, dZapplyEntityList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new DZapplyAdapter.OnClickItem() {
            @Override
            public void onClick(final int position) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(DZapplyActivity.this)
                        .setTitle("提示")
                        .setMessage("确定删除么")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                //删除申请电桩
                                HashMap<String, String> map = new HashMap<>();
                                map.put("id", dZapplyEntityList.get(position).getId() + "");
                                Gson gson = new Gson();
                                String data = gson.toJson(map);
                                PileApi.instance.deleteMyele(data)
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
                                                        showToast("删除失败，请重试");
                                                    } else {
                                                        dZapplyEntityList.clear();
                                                        adapter.notifyDataSetChanged();
                                                        initDZapplyHttp();
                                                        Toast.makeText(DZapplyActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
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
    }

    private void initView() {
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        btn_apply = (Button) findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DZapplyActivity.this, ApplyInfoActivity.class);
                startActivity(intent);
            }
        });

    }
}
