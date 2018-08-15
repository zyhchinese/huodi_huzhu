package com.lx.hd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.bean.User;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 余额界面
 */
public class BalanceActivity extends BackCommonActivity implements OnClickListener {
    private TextView CustRecharge, cz, tx;
    private TextView ye;
    private CircleImageView circleImageView;
    private RequestOptions mOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("余额");
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.user_header_defult)
                .error(R.mipmap.user_header_defult)
                .fitCenter();
        CustRecharge = (TextView) findViewById(R.id.CustRecharge);
        ye = (TextView) findViewById(R.id.ye);
        cz = (TextView) findViewById(R.id.cz);
        tx = (TextView) findViewById(R.id.tx);
        circleImageView= (CircleImageView) findViewById(R.id.img_avatar);
        CustRecharge.setOnClickListener(this);
        cz.setOnClickListener(this);
        ye.setOnClickListener(this);
        tx.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_balance;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.CustRecharge) {
            startActivity(new Intent(BalanceActivity.this, CustomerPositionActivity.class));
        } else if (v.getId() == R.id.tx) {
            startActivity(new Intent(BalanceActivity.this, WithDrawalActivity.class));
        } else if (v.getId() == R.id.cz) {
            startActivity(new Intent(BalanceActivity.this, PayActivity.class));
        }
    }

    private void updateUserInfo(User user) {
        if (user != null) {
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            ye.setText("¥" + decimalFormat.format(user.getBalance()));

            String userLogo= Constant.BASE_URL+user.getFolder()+"/"+user.getAutoname();
            Glide.with(BalanceActivity.this)
                    .load(userLogo)
                    .apply(mOptions)
                    .into(circleImageView);
        }
    }

    private void getUserInfo() {
        showWaitDialog("正在获取个人信息");
        PileApi.instance.getUserInfo()
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
                            if (body.length() < 10) {
                                //   if(body.equals("\"false\""))
                                showToast("获取个人信息失败，请登录后重试");
                            } else {
                                Gson gson = new Gson();
                                List<User> userList = gson.fromJson(body, new TypeToken<List<User>>() {
                                }.getType());
                                updateUserInfo(userList.get(0));

                            }
                            hideWaitDialog();
                        } catch (IOException e) {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
