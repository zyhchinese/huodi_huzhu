package com.lx.hd.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.utils.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class KaiPiaoActivity1Activity extends BackCommonActivity implements View.OnClickListener {

    private String money;
    private LinearLayout linear1,linear2,linear3;
    private ImageView img_qiyetaitou,img_feiqiyetaitou;
    private TextView tv_money;
    private EditText ed_fapiaotaitou,ed_shuihao,ed_address,ed_phone1,ed_kaihuhang,ed_beizhu,
            ed_dianziyouxiang;
    private Button btn_tijiao;
    private boolean isOk=true;
    private String id;
    private String ordertype;

    @Override
    protected int getContentView() {
        return R.layout.activity_kai_piao_activity1;

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("按行程开票");
        money = getIntent().getStringExtra("money");
        id = getIntent().getStringExtra("id");
        ordertype = getIntent().getStringExtra("ordertype");

        linear1= (LinearLayout) findViewById(R.id.linear1);
        linear2= (LinearLayout) findViewById(R.id.linear2);
        linear3= (LinearLayout) findViewById(R.id.linear3);
        img_qiyetaitou= (ImageView) findViewById(R.id.img_qiyetaitou);
        img_feiqiyetaitou= (ImageView) findViewById(R.id.img_feiqiyetaitou);
        tv_money= (TextView) findViewById(R.id.tv_money);
        ed_fapiaotaitou= (EditText) findViewById(R.id.ed_fapiaotaitou);
        ed_shuihao= (EditText) findViewById(R.id.ed_shuihao);
        ed_address= (EditText) findViewById(R.id.ed_address);
        ed_phone1= (EditText) findViewById(R.id.ed_phone1);
        ed_kaihuhang= (EditText) findViewById(R.id.ed_kaihuhang);
        ed_beizhu= (EditText) findViewById(R.id.ed_beizhu);
        ed_dianziyouxiang= (EditText) findViewById(R.id.ed_dianziyouxiang);
        btn_tijiao= (Button) findViewById(R.id.btn_tijiao);

        img_qiyetaitou.setOnClickListener(this);
        img_feiqiyetaitou.setOnClickListener(this);
        linear1.setOnClickListener(this);
        linear2.setOnClickListener(this);
        btn_tijiao.setOnClickListener(this);
        tv_money.setText(money);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_qiyetaitou:
                isOk=true;
                img_qiyetaitou.setImageResource(R.mipmap.img_huodi_xuanzhong);
                img_feiqiyetaitou.setImageResource(R.mipmap.img_huodi_weixuanzhong);
                linear3.setVisibility(View.VISIBLE);
                break;
            case R.id.img_feiqiyetaitou:
                isOk=false;
                img_feiqiyetaitou.setImageResource(R.mipmap.img_huodi_xuanzhong);
                img_qiyetaitou.setImageResource(R.mipmap.img_huodi_weixuanzhong);
                linear3.setVisibility(View.GONE);
                break;
            case R.id.linear2:
                isOk=true;
                img_qiyetaitou.setImageResource(R.mipmap.img_huodi_xuanzhong);
                img_feiqiyetaitou.setImageResource(R.mipmap.img_huodi_weixuanzhong);
                linear3.setVisibility(View.VISIBLE);
                break;
            case R.id.linear1:
                isOk=false;
                img_feiqiyetaitou.setImageResource(R.mipmap.img_huodi_xuanzhong);
                img_qiyetaitou.setImageResource(R.mipmap.img_huodi_weixuanzhong);
                linear3.setVisibility(View.GONE);
                break;
            case R.id.btn_tijiao:
                if (ed_fapiaotaitou.getText().toString().trim().length()==0){
                    Toast.makeText(this, "发票抬头不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isOk){
                    if (ed_shuihao.getText().toString().trim().length()==0){
                        Toast.makeText(this, "税号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ed_address.getText().toString().trim().length()==0){
                        Toast.makeText(this, "地址不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ed_phone1.getText().toString().trim().length()==0){
                        Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!phone(ed_phone1.getText().toString().trim())){
                        Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ed_kaihuhang.getText().toString().trim().length()==0){
                        Toast.makeText(this, "开户行及账号不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (ed_dianziyouxiang.getText().toString().length() > 0) {
                    if (!StringUtils.emailFormat(ed_dianziyouxiang.getText().toString())) {
                        showToast("请填写正确的邮箱");
                        return;
                    }
                }else {
                    showToast("请填写邮箱");
                    return;
                }

                initTijiao();


                
                break;
        }
    }
    private boolean phone(String mobiles){
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private void initTijiao() {
        HashMap<String, String> map = new HashMap<>();
        if (isOk){
            map.put("invoicetype", "0");
        }else {
            map.put("invoicetype", "1");
        }

        map.put("invoicetaitou", ed_fapiaotaitou.getText().toString().trim());
        map.put("invoiceshuihao", ed_shuihao.getText().toString().trim());
        map.put("invoicecontent", "运输服务费");
        map.put("invoicemoney", money);
        map.put("invoiceaddress", ed_address.getText().toString().trim());
        map.put("invoicephone", ed_phone1.getText().toString().trim());
        map.put("bankaccount", ed_kaihuhang.getText().toString().trim());
        map.put("email", ed_dianziyouxiang.getText().toString().trim());
        map.put("note", ed_beizhu.getText().toString().trim());
        map.put("typeorder", ordertype);
        map.put("orderids", id);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.addInvoice(data)
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
                            if (body.indexOf("true")!=-1){
                                initGengxin();
                            }else {
                                Toast.makeText(KaiPiaoActivity1Activity.this, "提交失败1", Toast.LENGTH_SHORT).show();
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

    private void initGengxin() {

        HashMap<String, String> map = new HashMap<>();
        map.put("typeorder", ordertype);
        map.put("orderids", id);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.updateOrderStatus(data)
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
                            if (body.indexOf("true")!=-1){
                                sendPayLocalReceiver(KaiPiaoActivity.class.getName());
//                                Intent intent=new Intent(KaiPiaoActivity1Activity.this,KaiPiaoLiShiActivity.class);
//                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(KaiPiaoActivity1Activity.this, "提交失败2", Toast.LENGTH_SHORT).show();
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
    protected boolean sendPayLocalReceiver(String className) {
        if (mManager != null) {
            Intent intent = new Intent();
            intent.putExtra("className", className);
            intent.setAction(ACTION_PAY_FINISH_ALL);
            return mManager.sendBroadcast(intent);
        }

        return false;
    }
}
