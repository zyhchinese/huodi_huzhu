package com.lx.hd.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.adapter.ChangJianWenTiAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.ChangJianWenTiEntity;
import com.lx.hd.bean.LiShiXiaoXiEntity;

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

import static com.lx.hd.base.Constant.REQUEST_CODE;

public class ChangJianWenTiActivity extends BackCommonActivity implements View.OnClickListener {
    private RecyclerView act_recyclerView;
    private TextView tv_call;
    private ChangJianWenTiEntity changJianWenTiEntity;


    @Override
    protected int getContentView() {
        return R.layout.activity_chang_jian_wen_ti;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("常见问题");
        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);
        tv_call= (TextView) findViewById(R.id.tv_call);
        tv_call.setOnClickListener(this);

        initShuJu();
    }

    private void initShuJu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "0");
        final Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.selectCommProblems(params)
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
                                changJianWenTiEntity = gson.fromJson(body, ChangJianWenTiEntity.class);

                                initRecyclerView();


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
        ChangJianWenTiAdapter adapter=new ChangJianWenTiAdapter(this,changJianWenTiEntity.getResponse());
        LinearLayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new ChangJianWenTiAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {

                Intent intent=new Intent(ChangJianWenTiActivity.this,ChangJianWenTiDetailActivity.class);
                intent.putExtra("title",changJianWenTiEntity.getResponse().get(position).getProblem());
                intent.putExtra("content",changJianWenTiEntity.getResponse().get(position).getDescribe());
                intent.putExtra("youyong",changJianWenTiEntity.getResponse().get(position).getUseful()+"");
                intent.putExtra("wuyong",changJianWenTiEntity.getResponse().get(position).getUseless()+"");
                intent.putExtra("id",changJianWenTiEntity.getResponse().get(position).getId()+"");
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_call:
                AlertDialog.Builder builder=new AlertDialog.Builder(ChangJianWenTiActivity.this)
                        .setTitle("提示")
                        .setMessage("拨打客服电话：0531-88807916")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                testCallPhone();
                            }
                        });
                builder.show();
                break;
        }
    }

    //打电话
    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            } else {
                callPhone("0531-88807916");
            }

        } else {
            callPhone("0531-88807916");
        }
    }
    private void callPhone(String phoneNum) {
        //直接拨号
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        //此处不判断就会报错
        if (ActivityCompat.checkSelfPermission(ChangJianWenTiActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            startActivity(intent);
        }
    }
    /**
     * 请求权限的回调方法
     *
     * @param requestCode  请求码
     * @param permissions  请求的权限
     * @param grantResults 权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(ChangJianWenTiActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-88807916");
        } else {
            Toast.makeText(ChangJianWenTiActivity.this, "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }
}
