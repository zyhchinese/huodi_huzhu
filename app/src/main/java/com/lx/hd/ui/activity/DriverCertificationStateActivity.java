package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.CheckSiJiRenZhengEntity;
import com.lx.hd.bean.CheckSiJiRenZhengImgEntity;

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

public class DriverCertificationStateActivity extends BackCommonActivity implements View.OnClickListener {

    private TextView tv_shili1,tv_shili2,tv_shili3,tv_shili4,tv_shili5,tv_shili6;
    private ImageView img1,img2,img3,img4,img5,img6;
    private Button btn_tijiao;
    private TextView ed_name,ed_chepaihao,ed_phone_number;
    private List<CheckSiJiRenZhengEntity> checkSiJiRenZhengEntityList;
    private List<CheckSiJiRenZhengImgEntity> checkSiJiRenZhengImgEntityList;
    private CheckSiJiRenZhengEntity checkSiJiRenZhengEntity;


    @Override
    protected int getContentView() {
        return R.layout.activity_driver_certification_state;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("货主认证");
//        checkSiJiRenZhengEntityList= (List<CheckSiJiRenZhengEntity>) getIntent().getSerializableExtra("shuju");
//        checkSiJiRenZhengImgEntityList= (List<CheckSiJiRenZhengImgEntity>) getIntent().getSerializableExtra("tupian");
        initView();

    }
    private void initPanduan() {
        HashMap hashMap=new HashMap();
        hashMap.put("seltype","2");
        Gson gson=new Gson();
        String params = gson.toJson(hashMap);
        PileApi.instance.checkOwnerStatus1(params)
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
//                            if (flag.equals("200")){
//                                //审核通过
//
//                            }else if (flag.equals("210")){
//                                //未认证
//
//                            }else if (flag.equals("220")){
//                                //正在审核中
//
//                            }else if (flag.equals("230")){
//                                //审核未通过
//
//                            }

                            if (flag.equals("200")||flag.equals("220")||flag.equals("230")){
                                Gson gson=new Gson();
                                checkSiJiRenZhengEntity = gson.fromJson(body, CheckSiJiRenZhengEntity.class);

                                ed_name.setText(checkSiJiRenZhengEntity.getResponse().get(0).getInfo().getCustname());
                                ed_chepaihao.setText(checkSiJiRenZhengEntity.getResponse().get(0).getInfo().getCustidcard());
                                ed_phone_number.setText(checkSiJiRenZhengEntity.getResponse().get(0).getInfo().getCustcall());
                                for (CheckSiJiRenZhengEntity.ResponseBean.ImgsBean entity:checkSiJiRenZhengEntity.getResponse().get(0).getImgs()){
                                    if (entity.getDriver_img_type()==1){
                                        Glide.with(DriverCertificationStateActivity.this).load(Constant.BASE_URL+entity.getFolder()+"/"+entity.getAutoname()).into(img1);
                                    }else if (entity.getDriver_img_type()==2){
                                        Glide.with(DriverCertificationStateActivity.this).load(Constant.BASE_URL+entity.getFolder()+"/"+entity.getAutoname()).into(img2);
                                    }else if (entity.getDriver_img_type()==3){
                                        Glide.with(DriverCertificationStateActivity.this).load(Constant.BASE_URL+entity.getFolder()+"/"+entity.getAutoname()).into(img3);
                                    }
                                }
                                if (checkSiJiRenZhengEntity.getResponse().get(0).getInfo().getStatus()==0){
                                    btn_tijiao.setText("审核中");
                                }else if (checkSiJiRenZhengEntity.getResponse().get(0).getInfo().getStatus()==2){
                                    btn_tijiao.setText("审核拒绝");
                                    initSuccess();
                                }else if (checkSiJiRenZhengEntity.getResponse().get(0).getInfo().getStatus()==1){
                                    btn_tijiao.setBackgroundColor(Color.parseColor("#c31023"));
                                    btn_tijiao.setText("已通过【重新提交】");
                                }

                            }


//                            String substring = body.substring(1, body.length() - 1);
//
//                            if (substring.equals("-1")) {
////                                startActivity(new Intent(DriverCertificationActivity.this, DriverCertificationActivity.class));
//                            } else {
//
//                                Gson gson = new Gson();
//                                checkSiJiRenZhengEntityList = gson.fromJson(body, new TypeToken<List<CheckSiJiRenZhengEntity>>() {
//                                }.getType());
//                                String imglist = checkSiJiRenZhengEntityList.get(0).getImglist();
//                                Gson gson1=new Gson();
//                                checkSiJiRenZhengImgEntityList = gson1.fromJson(imglist, new TypeToken<List<CheckSiJiRenZhengImgEntity>>() {
//                                }.getType());
//
//                                ed_name.setText(checkSiJiRenZhengEntityList.get(0).getDriver_name());
//                                ed_chepaihao.setText(checkSiJiRenZhengEntityList.get(0).getDriver_car_number());
//                                ed_phone_number.setText(checkSiJiRenZhengEntityList.get(0).getDriver_phone());
//                                for (CheckSiJiRenZhengImgEntity entity:checkSiJiRenZhengImgEntityList){
//                                    if (entity.getDriver_img_type()==1){
//                                        Glide.with(DriverCertificationStateActivity.this).load(Constant.BASE_URL+entity.getFolder()+"/"+entity.getAutoname()).into(img1);
//                                    }else if (entity.getDriver_img_type()==2){
//                                        Glide.with(DriverCertificationStateActivity.this).load(Constant.BASE_URL+entity.getFolder()+"/"+entity.getAutoname()).into(img2);
//                                    }else if (entity.getDriver_img_type()==0){
//                                        Glide.with(DriverCertificationStateActivity.this).load(Constant.BASE_URL+entity.getFolder()+"/"+entity.getAutoname()).into(img3);
//                                    }else if (entity.getDriver_img_type()==3){
//                                        Glide.with(DriverCertificationStateActivity.this).load(Constant.BASE_URL+entity.getFolder()+"/"+entity.getAutoname()).into(img4);
//                                    }else if (entity.getDriver_img_type()==4){
//                                        Glide.with(DriverCertificationStateActivity.this).load(Constant.BASE_URL+entity.getFolder()+"/"+entity.getAutoname()).into(img5);
//                                    }else if (entity.getDriver_img_type()==5){
//                                        Glide.with(DriverCertificationStateActivity.this).load(Constant.BASE_URL+entity.getFolder()+"/"+entity.getAutoname()).into(img6);
//                                    }
//                                }
//                                if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status()==0){
//                                    btn_tijiao.setText("审核中");
//                                }else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status()==2){
//                                    btn_tijiao.setText("审核拒绝");
//                                    initSuccess();
//                                }else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status()==1){
//                                    btn_tijiao.setBackgroundColor(Color.parseColor("#c31023"));
//                                    btn_tijiao.setText("已通过【重新提交】");
//                                }
//
////                                if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status()==0){
////                                    //审核中
////                                    Intent intent=new Intent(DriverCertificationActivity.this, DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
////                                    startActivity(intent);
////                                }else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status()==1){
////                                    //审核通过
////                                    startActivity(new Intent(DriverCertificationActivity.this, CarLeaseActivity.class));
////                                }else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status()==2){
////                                    //审核拒绝
////                                    Intent intent=new Intent(DriverCertificationActivity.this, DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
////                                    startActivity(intent);
////                                }
//                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
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
    private void initSuccess() {
        AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.setCancelable(false);
        builder.show();
        builder.getWindow().setContentView(R.layout.dialog_shibai);
        builder.getWindow().setBackgroundDrawableResource(R.drawable.bg_yuanjiao_touming1);
        TextView textView= (TextView) builder.getWindow().findViewById(R.id.tv1);
        textView.setText("未通过原因："+checkSiJiRenZhengEntity.getResponse().get(0).getInfo().getNote()+"");
        builder.getWindow().findViewById(R.id.btn_queding)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(DriverCertificationStateActivity.this,DriverCertificationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }

    private void initView() {
        tv_shili1= (TextView) findViewById(R.id.tv_shili2);
        tv_shili2= (TextView) findViewById(R.id.tv_shili1);
        tv_shili3= (TextView) findViewById(R.id.tv_shili3);
        tv_shili4= (TextView) findViewById(R.id.tv_shili4);
        tv_shili5= (TextView) findViewById(R.id.tv_shili5);
        tv_shili6= (TextView) findViewById(R.id.tv_shili6);
        img1= (ImageView) findViewById(R.id.img1);
        img2= (ImageView) findViewById(R.id.img2);
        img3= (ImageView) findViewById(R.id.img3);
        img4= (ImageView) findViewById(R.id.img4);
        img5= (ImageView) findViewById(R.id.img5);
        img6= (ImageView) findViewById(R.id.img6);
        btn_tijiao= (Button) findViewById(R.id.btn_tijiao);
        ed_name= (TextView) findViewById(R.id.ed_name);
        ed_chepaihao= (TextView) findViewById(R.id.ed_chepaihao);
        ed_phone_number= (TextView) findViewById(R.id.ed_phone_number);


        tv_shili1.setOnClickListener(this);
        tv_shili2.setOnClickListener(this);
        tv_shili3.setOnClickListener(this);
        tv_shili4.setOnClickListener(this);
        tv_shili5.setOnClickListener(this);
        tv_shili6.setOnClickListener(this);
        btn_tijiao.setOnClickListener(this);
        initPanduan();


    }
    private void initDialog(int id){

        AlertDialog dialog=new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_shilitupian);
        ImageView imageView= (ImageView) dialog.getWindow().findViewById(R.id.img_shili);
        imageView.setImageResource(id);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_shili1:
                initDialog(R.mipmap.img_shili1);
                break;
            case R.id.tv_shili2:
                initDialog(R.mipmap.img_shili2);
                break;
            case R.id.tv_shili3:
                initDialog(R.mipmap.img_shili3);
                break;
            case R.id.tv_shili4:
                initDialog(R.mipmap.img_shili4);
                break;
            case R.id.tv_shili5:
                initDialog(R.mipmap.img_shili5);
                break;
            case R.id.tv_shili6:
                initDialog(R.mipmap.img_shili6);
                break;
            case R.id.btn_tijiao:
                if (checkSiJiRenZhengEntity.getResponse().get(0).getInfo().getStatus()==1){
                    AlertDialog.Builder builder=new AlertDialog.Builder(DriverCertificationStateActivity.this)
                            .setTitle("提示")
                            .setMessage("您确定需要重新提交审核吗？")
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
                                    Intent intent=new Intent(DriverCertificationStateActivity.this,DriverCertificationActivity.class);
                                    startActivity(intent);
                                }
                            });
                    builder.show();
                }

                break;
        }
    }
}
