package com.lx.hd.account;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.activity.BaseActivity;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class
ForgetPwdTwoActivity extends BaseActivity {

    private EditText etCheckPwd,etPwd;
    private TextView tvCongirm;
    private String mUserName="",mPwd="",mCheckPwd="",mPhone="";
    @Override
    protected int getContentView() {
        return R.layout.activity_forget_pwd_two;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mPhone=getIntent().getStringExtra("phone");
        etCheckPwd=(EditText)findViewById(R.id.et_check_pwd);
        etPwd=(EditText)findViewById(R.id.et_new_pwd);
        tvCongirm=(TextView)findViewById(R.id.tv_confirm);
        tvCongirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePwd();
            }
        });
    }

    private void changePwd(){
        String tempCheckPwd=etCheckPwd.getText().toString().trim();
        String tempPwd=etPwd.getText().toString().trim();
        if(tempPwd.length()<6){
            showToast("密码格式不正确：\n由数字、字母、下划线组成的6—15位密码");
            return;
        }
        if(tempPwd.length()>15){
            showToast("密码格式不正确：\n由数字、字母、下划线组成的6—15位密码");
            return;
        }
        if(!tempCheckPwd.equals(tempPwd)){
            showToast("两次密码输入不一致");
            return;
        }


        HashMap<String,String> map=new HashMap<>();
        map.put("phone",mPhone);
        map.put("password",tempPwd);
        Gson gson=new Gson();
        String params=gson.toJson(map);
        showWaitDialog("正在修改密码...");
        PileApi.instance.changePwd(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        hideWaitDialog();
                        try {
                            String body=responseBody.string();
                            if(body.equals("\"true\"")){
                                showToast("密码修改成功");
                                if( ForgetPwdOneActivity.instance!=null){
                                    ForgetPwdOneActivity.instance.finish();
                                }
                                finish();
                              //  startActivity(new Intent(ForgetPwdTwoActivity.this,LoginActivity.class));
                            }else {
                                showToast("密码修改失败");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToast("密码修改失败,请稍后重试");
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
