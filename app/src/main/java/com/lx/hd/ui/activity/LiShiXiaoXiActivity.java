package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.adapter.LiShiXiaoXiAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.LiShiXiaoXiEntity;
import com.lx.hd.bean.PingYuEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LiShiXiaoXiActivity extends BackCommonActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tv_guanli, tv_delete,tv_num;
    private RelativeLayout relative_quanxuan;
    private CheckBox checkbox_quanxuan;
    private RecyclerView act_recyclerView;
    private LiShiXiaoXiEntity liShiXiaoXiEntity;
    private String ids = "";
    private LiShiXiaoXiAdapter adapter;


    @Override
    protected int getContentView() {
        return R.layout.activity_li_shi_xiao_xi;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initShuJu();
    }

    private void initShuJu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "0");
        final Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchUserMsgList(params)
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
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                Gson gson = new Gson();
                                liShiXiaoXiEntity = gson.fromJson(body, LiShiXiaoXiEntity.class);
                                tv_num.setText("消息("+liShiXiaoXiEntity.getResponse().size()+")");
                                if (ids.equals("")){
                                    initRecyclerView();
                                }else {
                                    adapter.addData(liShiXiaoXiEntity.getResponse());
                                }



                            }


                        } catch (IOException e) {
                        } catch (JSONException e) {
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

    private void initRecyclerView() {
        adapter = new LiShiXiaoXiAdapter(this, liShiXiaoXiEntity.getResponse(), tv_guanli, relative_quanxuan, checkbox_quanxuan, tv_delete);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new LiShiXiaoXiAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(LiShiXiaoXiActivity.this, LiShiXiaoXiDetailActivity.class);
                intent.putExtra("selid", liShiXiaoXiEntity.getResponse().get(position).getId() + "");
                startActivity(intent);
            }
        });


    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        tv_guanli = (TextView) findViewById(R.id.tv_guanli);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_num = (TextView) findViewById(R.id.tv_num);
        relative_quanxuan = (RelativeLayout) findViewById(R.id.relative_quanxuan);
        checkbox_quanxuan = (CheckBox) findViewById(R.id.checkbox_quanxuan);
        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);


        img_back.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_delete:
                ids = "";
                for (LiShiXiaoXiEntity.ResponseBean entity : liShiXiaoXiEntity.getResponse()) {
                    if (entity.isLine()) {
                        ids = ids + entity.getId() + ",";
                    }
                }
                if (!ids.equals("")) {
                    ids = ids.substring(0, ids.length() - 1);

                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("确定删除吗？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    initDelete(ids);
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }

                break;
        }
    }

    private void initDelete(String ids) {
        HashMap<String, String> map = new HashMap<>();
        map.put("selectid", ids);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.deleteUserMsg(data)
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
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                showToast("删除成功");
                                initShuJu();
                            } else {
//                                showToast("删除失败");
                            }

                        } catch (IOException e) {

                        } catch (JSONException e) {
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
