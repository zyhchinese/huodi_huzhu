package com.lx.hd.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;
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

public class LiShiXiaoXiDetailActivity extends BackCommonActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView tv_type,tv_time,tv_content;
    private LiShiXiaoXiEntity liShiXiaoXiEntity;
    private String selid;

    @Override
    protected int getContentView() {
        return R.layout.activity_li_shi_xiao_xi_detail;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        selid = getIntent().getStringExtra("selid");
        img_back= (ImageView) findViewById(R.id.img_back);
        tv_type= (TextView) findViewById(R.id.tv_type);
        tv_time= (TextView) findViewById(R.id.tv_time);
        tv_content= (TextView) findViewById(R.id.tv_content);

        img_back.setOnClickListener(this);

        initShuJu();
    }

    private void initShuJu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("selid", selid);
        final Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchUserMsgById(params)
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
                                liShiXiaoXiEntity = gson.fromJson(body, LiShiXiaoXiEntity.class);

                                initFuZhi();


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

    private void initFuZhi() {
        tv_type.setText(liShiXiaoXiEntity.getResponse().get(0).getTitle());
        tv_time.setText(liShiXiaoXiEntity.getResponse().get(0).getCreate_time());
        tv_content.setText(liShiXiaoXiEntity.getResponse().get(0).getContent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
