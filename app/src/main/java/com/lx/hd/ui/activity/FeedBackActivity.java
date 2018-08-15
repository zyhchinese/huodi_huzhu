package com.lx.hd.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class FeedBackActivity extends BackCommonActivity {

    private EditText etAdvice;
    private TextView tvConfirm;
    @Override
    protected int getContentView() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("意见和建议");
        etAdvice=(EditText)findViewById(R.id.et_input_advice);
        tvConfirm=(TextView) findViewById(R.id.tv_confirm);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempContent=etAdvice.getText().toString().trim();
                if(tempContent.length()==0){
                    showToast("反馈内容不能为空");
                    return;
                }

                HashMap<String,String> map=new HashMap<String, String>();
                map.put("content",tempContent);
                Gson gson=new Gson();
                String data=gson.toJson(map);
                showWaitDialog("正在反馈...");
                PileApi.instance.addAdvice(data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull ResponseBody responseBody) {
                                try {
                                    hideWaitDialog();
                                    String body= responseBody.string();
                                    if(body.equals("\"true\"")){
                                        showToast("反馈成功");
                                        finish();
                                    }else {
                                        showToast("反馈失败，请稍后重试");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    onError(e);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                hideWaitDialog();
                                showToast("反馈失败，请稍后重试");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
