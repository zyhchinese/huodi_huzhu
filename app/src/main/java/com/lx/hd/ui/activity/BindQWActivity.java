package com.lx.hd.ui.activity;

import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BackCommonActivity;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BindQWActivity extends BackCommonActivity {

    private TextView tvConfirm;
    private EditText etQQ,etWX;
    private ImageView img_anquanzhongxin_banner;

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_qw_gaiban;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("绑定QQ微信");
        etQQ=(EditText)findViewById(R.id.et_qq);
        etWX=(EditText)findViewById(R.id.et_wx);
        tvConfirm=(TextView)findViewById(R.id.tv_confirm);
        img_anquanzhongxin_banner= (ImageView) findViewById(R.id.img_anquanzhongxin_banner);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempQQ,tempWX;
                tempQQ=etQQ.getText().toString().trim();
                tempWX=etWX.getText().toString().trim();

                if(tempQQ.length()==0&&tempWX.length()==0){
                    showToast("QQ和微信至少输入一个");
                    return;
                }
                if(tempQQ.length()!=0&&tempQQ.length()<5){
                    showToast("QQ号码长度不能小于5位");
                    return;
                }
                if(tempWX.length()!=0&&tempWX.length()<3){
                    showToast("微信号码长度不能小于3位");
                    return;
                }
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("qq",tempQQ);
                map.put("wechat",tempWX);
                Gson gson=new Gson();
                String data=gson.toJson(map);
                PileApi.instance.bindQqWx(data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull ResponseBody responseBody) {
                                try {
                                    String body=responseBody.string();
                                    if(body.equals("\"true\"")){
                                        showToast("绑定成功");
                                        finish();
                                    }else {
                                        showToast("绑定失败，请稍后重试");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                showToast("绑定失败，请稍后重试");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        initGaoDu();
    }

    private void initGaoDu() {
        WindowManager windowManager=getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        double i = display.getWidth() / 720.0;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img_anquanzhongxin_banner.getLayoutParams();
        layoutParams.height= (int) (360.0*i);
        img_anquanzhongxin_banner.setLayoutParams(layoutParams);

    }

    @Override
    protected void initData() {
        super.initData();
    }
}
