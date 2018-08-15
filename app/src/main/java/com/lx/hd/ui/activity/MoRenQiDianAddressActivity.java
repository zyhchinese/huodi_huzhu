package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.MoRenQiDianAddressAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.MoRenQiDianAddressEntity;
import com.lx.hd.bean.ProvinceEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MoRenQiDianAddressActivity extends BackCommonActivity {


    private RecyclerView act_recyclerView;
    private MoRenQiDianAddressEntity moRenQiDianAddressEntity;

    @Override
    protected int getContentView() {
        return R.layout.activity_mo_ren_qi_dian_address;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("常用发货起点地址");

        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);

        initShuJu();
    }

    private void initShuJu() {
        PileApi.instance.selectUserRelevantList()
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

                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                Gson gson=new Gson();
                                moRenQiDianAddressEntity = gson.fromJson(body, MoRenQiDianAddressEntity.class);

                                initRecyclerView();
                            }




                        } catch (IOException e) {
                            e.printStackTrace();
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

        MoRenQiDianAddressAdapter adapter =new MoRenQiDianAddressAdapter(this,moRenQiDianAddressEntity.getResponse().get(0).getSaddlist());
        LinearLayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickMoRen(new MoRenQiDianAddressAdapter.OnClickMoRen() {
            @Override
            public void onClick(int position, boolean isChecked) {
                initMoRen(position,isChecked);
            }
        });
        adapter.setOnClickDelete(new MoRenQiDianAddressAdapter.OnClickDelete() {
            @Override
            public void onClick(final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MoRenQiDianAddressActivity.this)
                        .setTitle("提示")
                        .setMessage("确定删除吗？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                initDelete(position);
                                dialog.dismiss();
                            }
                        });
                builder.show();

            }
        });
        adapter.setOnClickHang(new MoRenQiDianAddressAdapter.OnClickHang() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent();
                intent.putExtra("myresuly",moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(position).getSaddress());
                intent.putExtra("address","");
                intent.putExtra("y",moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(position).getSlongitude());
                intent.putExtra("x",moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(position).getSlatitude());
                intent.putExtra("dwsheng",moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(position).getSprovince());
                intent.putExtra("dwshi",moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(position).getScity());
                intent.putExtra("dwxian",moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(position).getScounty());
                intent.putExtra("morendizhi","");
                setResult(002,intent);
                finish();
            }
        });
    }

    private void initDelete(int position) {
        HashMap<String, String> map = new HashMap<>();
        map.put("relevant_type", "2");
        map.put("selectid", moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(position).getId()+"");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.deleteUserRelevant(data)
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
                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                showToast("删除成功");
                                initShuJu();
                            }else {
                                showToast("删除失败");
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

    private void initMoRen(int position, boolean isChecked) {
        HashMap<String, String> map = new HashMap<>();
        map.put("relevant_type", "2");
        map.put("selectid", moRenQiDianAddressEntity.getResponse().get(0).getSaddlist().get(position).getId()+"");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.setUserRelevantDef(data)
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
                            JSONObject jsonObject=new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")){
                                showToast("设置成功");
                                initShuJu();
                            }else {
                                showToast("设置失败");
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
