package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.adapter.RecyclerAuditDetailsAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackActivity;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.AuditDetailsEntity;
import com.lx.hd.bean.AuditOrderEntity;

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

public class AuditDetailsActivity extends BackCommonActivity implements View.OnClickListener {
    private RecyclerView act_recyclerView;
    private Button btn_quxiao;
    private TextView tv_status, tv_tiche_info, tv_dingdanhao, tv_name, tv_phone, tv_number, tv_time,
            tv_qutime, tv_name1, tv_phone1, tv_time1, tv_address,tv_beizhu,tv_beizhu1,tv_money;
    private ImageView img_zuo;
    private RelativeLayout relative,relative1234;
    private int type;
    private String order_id;
    private String orderno;
    private String createtime;
    private String shijiquche_name;
    private String shijiquche_linkerphone;
    private String shijiquche_time;
    private String shijiquche_address;
    private String sumcarnum;
    private String take_time;
    private int orderstatus;
    private String note;
    private String order_money;
    private String link_name;
    private String link_phone;
    private String substring1;
    private List<AuditDetailsEntity> detailsEntityList;


    @Override
    protected int getContentView() {
        return R.layout.activity_audit_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("订单详情");
        type = getIntent().getIntExtra("type", 0);
        orderstatus = getIntent().getIntExtra("orderstatus", 1);
        order_id = getIntent().getStringExtra("order_id");
        orderno = getIntent().getStringExtra("orderno");
        createtime = getIntent().getStringExtra("createtime");
        shijiquche_name = getIntent().getStringExtra("shijiquche_name");
        shijiquche_linkerphone = getIntent().getStringExtra("shijiquche_linkerphone");
        shijiquche_time = getIntent().getStringExtra("shijiquche_time");
        shijiquche_address = getIntent().getStringExtra("shijiquche_address");
        sumcarnum = getIntent().getStringExtra("sumcarnum");
        take_time = getIntent().getStringExtra("take_time");
        note = getIntent().getStringExtra("note");
        order_money = getIntent().getStringExtra("order_money");
        link_name = getIntent().getStringExtra("link_name");
        link_phone = getIntent().getStringExtra("link_phone");
        initView();

    }

    private void initView() {

        act_recyclerView = (RecyclerView) findViewById(R.id.act_recyclerView);
        btn_quxiao = (Button) findViewById(R.id.btn_quxiao);
        tv_status = (TextView) findViewById(R.id.tv_status);
        img_zuo = (ImageView) findViewById(R.id.img_zuo);
        relative = (RelativeLayout) findViewById(R.id.relative123);
        relative1234 = (RelativeLayout) findViewById(R.id.relative1234);
        tv_tiche_info = (TextView) findViewById(R.id.tv_tiche_info);
        tv_dingdanhao = (TextView) findViewById(R.id.tv_dingdanhao);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_qutime = (TextView) findViewById(R.id.tv_qutime);
        tv_name1 = (TextView) findViewById(R.id.tv_name1);
        tv_phone1 = (TextView) findViewById(R.id.tv_phone1);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_beizhu= (TextView) findViewById(R.id.tv_beizhu);
        tv_beizhu1= (TextView) findViewById(R.id.tv_beizhu1);
        tv_money= (TextView) findViewById(R.id.tv_money);
        btn_quxiao.setOnClickListener(this);

//        String substring = createtime.substring(0, 10);
//        if (shijiquche_time.length()!=0){
//            substring1 = shijiquche_time.substring(0, 10);
//        }


//        tv_dingdanhao.setText(orderno);
//        tv_name.setText(link_name);
//        tv_phone.setText(link_phone);
//        tv_number.setText(sumcarnum+"辆");
//        tv_time.setText(substring);
//        tv_qutime.setText(take_time);
//        tv_name1.setText(shijiquche_name);
//        tv_phone1.setText(shijiquche_linkerphone);
//        tv_time1.setText(substring1);
//        tv_address.setText(shijiquche_address);
//        tv_money.setText(order_money+"元");
        if (type == 0) {
            tv_tiche_info.setVisibility(View.GONE);
            relative.setVisibility(View.GONE);
            btn_quxiao.setVisibility(View.VISIBLE);
            tv_beizhu.setVisibility(View.GONE);
            relative1234.setVisibility(View.GONE);
            img_zuo.setImageResource(R.mipmap.img_zuoshangjiao_false);
            tv_status.setText("未审核");
            tv_status.setTextColor(Color.parseColor("#FFE86305"));
        } else {
            if (orderstatus==1){
                tv_tiche_info.setVisibility(View.VISIBLE);
                relative.setVisibility(View.VISIBLE);
                btn_quxiao.setVisibility(View.GONE);
//                tv_beizhu.setVisibility(View.VISIBLE);
//                relative1234.setVisibility(View.VISIBLE);
                img_zuo.setImageResource(R.mipmap.img_zuoshangjiao_true);
                tv_status.setText("审核通过");
                tv_status.setTextColor(Color.parseColor("#81c26a"));
//                tv_beizhu1.setText(note);
            }else {
                tv_tiche_info.setVisibility(View.GONE);
                relative.setVisibility(View.GONE);
                btn_quxiao.setVisibility(View.GONE);
//                tv_beizhu.setVisibility(View.VISIBLE);
//                relative1234.setVisibility(View.VISIBLE);
                img_zuo.setImageResource(R.mipmap.img_zuoshangjiao_false1);
                tv_status.setText("审核拒绝");
                tv_status.setTextColor(Color.parseColor("#888888"));
//                tv_beizhu1.setText(note);
            }

        }
        //加载订单详情列表数据
        initOrderDetails();


    }

    private void initOrderDetails() {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", order_id);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchLeaseOrderDetail(params)
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
                                Toast.makeText(AuditDetailsActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                detailsEntityList = gson.fromJson(body, new TypeToken<List<AuditDetailsEntity>>() {
                                }.getType());

                                //加载列表
                                initRecyclerView();
                                tv_dingdanhao.setText(detailsEntityList.get(0).getOrderno());
                                tv_name.setText(detailsEntityList.get(0).getLink_name());
                                tv_phone.setText(detailsEntityList.get(0).getLink_phone());
                                tv_number.setText(detailsEntityList.get(0).getNeed_count()+"辆");
                                tv_time.setText(detailsEntityList.get(0).getCreatetime());
                                tv_qutime.setText(detailsEntityList.get(0).getTake_time());
                                tv_name1.setText(detailsEntityList.get(0).getShijiquche_name());
                                tv_phone1.setText(detailsEntityList.get(0).getShijiquche_linkerphone());
                                tv_time1.setText(detailsEntityList.get(0).getShijiquche_time());
                                tv_address.setText(detailsEntityList.get(0).getShijiquche_address());
                                tv_money.setText((int) (detailsEntityList.get(0).getOrder_money())+"元");

                                if (detailsEntityList.get(0).getNote().equals("")){
                                    tv_beizhu.setVisibility(View.GONE);
                                    relative1234.setVisibility(View.GONE);
                                }else {
                                    tv_beizhu.setVisibility(View.VISIBLE);
                                    relative1234.setVisibility(View.VISIBLE);
                                    tv_beizhu1.setText(detailsEntityList.get(0).getNote());
                                }

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
        RecyclerAuditDetailsAdapter adapter=new RecyclerAuditDetailsAdapter(this,detailsEntityList,detailsEntityList.get(0).getShijiquche_name());
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定取消订单吗")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", order_id);
                        Gson gson = new Gson();
                        String data = gson.toJson(map);
                        PileApi.instance.cancleLeaseOrder(data)
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
                                                showToast("订单取消失败");
                                                dialog.dismiss();
                                            } else {
                                                dialog.dismiss();
                                                setResult(3);
                                                finish();
                                            }
                                        } catch (IOException e) {
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                });
        dialog.show();

    }
}
