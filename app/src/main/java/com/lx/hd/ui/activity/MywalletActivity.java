package com.lx.hd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.activity.BackCommonActivity;
import com.lx.hd.base.activity.BaseActivity;
import com.lx.hd.base.fragment.BaseRecyclerAdapter;
import com.lx.hd.bean.CheckSiJiRenZhengEntity;
import com.lx.hd.bean.CheckSiJiRenZhengImgEntity;
import com.lx.hd.bean.User;
import com.lx.hd.utils.DialogHelper;

import org.json.JSONException;
import org.json.JSONObject;

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

/***
 * 我的钱包余额页面activity
 * @author tb
 * 2017/8/28
 */
public class MywalletActivity extends BackCommonActivity {
    private TextView tvUserName;
    private TextView tBalance;
    private TextView tvTickets;
    private TextView tvScores;
    private CardView pinion1;
    private CardView pinion2;
    private CardView pinion3;
    private CardView pinion4;
    private CardView pinion5;
    boolean isLogin = false;
    RequestOptions mOptions;
    CircleImageView circleImageView;
    private TextView tv_qiandao;

    private List<CheckSiJiRenZhengEntity> checkSiJiRenZhengEntityList;
    private List<CheckSiJiRenZhengImgEntity> checkSiJiRenZhengImgEntityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("我的钱包");
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.user_header_defult)
                .error(R.mipmap.user_header_defult)
                .fitCenter();
        circleImageView = (CircleImageView) findViewById(R.id.img_avatar);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tBalance = (TextView) findViewById((R.id.tBalance));
        tvTickets = (TextView) findViewById((R.id.tvTickets));
        tvScores = (TextView) findViewById(R.id.tvScores);
        pinion1 = (CardView) findViewById(R.id.pinion);
        pinion2 = (CardView) findViewById(R.id.pinion1);
        pinion3 = (CardView) findViewById(R.id.pinion2);
        pinion4 = (CardView) findViewById(R.id.pinion3);
        pinion5 = (CardView) findViewById(R.id.pinion4);
        tv_qiandao= (TextView) findViewById(R.id.tv_qiandao);
        pinion1.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent1 = new Intent(MywalletActivity.this, BalanceActivity.class);

                                           startActivity(intent1);
                                       }
                                   }
        );
        pinion2.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent1 = new Intent(MywalletActivity.this, CouponActivity.class);

                                           startActivity(intent1);
                                       }
                                   }
        );
        pinion3.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
//                                           Intent intent1 = new Intent(MywalletActivity.this, IntegralActivity.class);
//
//                                           startActivity(intent1);
                                           Intent intent1 = new Intent(MywalletActivity.this, QianDaoActivity.class);

                                           startActivity(intent1);
                                       }
                                   }
        );
        pinion4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent intent=new Intent(MywalletActivity.this,KaiJuFaPiaoActivity.class);
                                            startActivity(intent);
                                        }
                                   }
        );
        pinion5.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

//                                           initjiance();
                                           Intent intent=new Intent(MywalletActivity.this,QianYueJieSuanActivity.class);
                                           startActivity(intent);
                                       }
                                   }
        );
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initPanduan();
            }
        });
        tv_qiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MywalletActivity.this,QianDaoActivity.class);
                startActivity(intent);
            }
        });
        getUserInfo();

        initjiance();

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mywallet;
    }

    @Override
    protected void initData() {
        super.initData();
        // 可以进行定位、检测更新等操作
    }

    private void updateUserInfo(User user) {
        if (user != null) {
            tvUserName.setText(user.getCustname());
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            tBalance.setText(decimalFormat.format(user.getBalance()) + "元");
            tvTickets.setText(user.getCount() + "张");
            tvScores.setText(decimalFormat.format(user.getScores()) + "积分");
            String userLogo= Constant.BASE_URL+user.getFolder()+"/"+user.getAutoname();
            Glide.with(MywalletActivity.this)
                    .load(userLogo)
                    .apply(mOptions)
                    .into(circleImageView);
        }
    }

    private void initjiance() {
        PileApi.instance.checkUserSign()
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
                            String msg = jsonObject.getString("msg");
                            if (flag.equals("100")){
                                pinion5.setVisibility(View.GONE);
                                showToast("请检查您的登录状态");
                            }else if (flag.equals("210")){
                                pinion5.setVisibility(View.GONE);
//                                AlertDialog.Builder builder=new AlertDialog.Builder(MywalletActivity.this)
//                                        .setTitle("提示")
//                                        .setMessage(msg)
//                                        .setCancelable(false)
//                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//                                            }
//                                        });
//                                builder.show();
                            }else if (flag.equals("200")){
                                pinion5.setVisibility(View.VISIBLE);
//                                Intent intent=new Intent(MywalletActivity.this,QianYueJieSuanActivity.class);
//                                startActivity(intent);
                            }else {
                                pinion5.setVisibility(View.GONE);
//                                showToast(msg);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(MywalletActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
                            System.out.println(body);
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

    @Override
    public void onResume() {
        super.onResume();
        PileApi.instance.mCheckLoginState()
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
                            if (body.equals("\"true\"")) {
                                getUserInfo();
                                isLogin = true;
                            } else {
                                isLogin = false;
                                DialogHelper.getConfirmDialog(MywalletActivity.this, "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(MywalletActivity.this, LoginActivity.class));
                                    }
                                }, null).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLogin = false;
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initPanduan() {
        PileApi.instance.checkOwnerStatus()
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
                                //审核通过
                                Intent intent = new Intent(MywalletActivity.this, DriverCertificationStateActivity.class);
                                startActivity(intent);
                            }else if (flag.equals("210")){
                                //未认证
                                Intent intent = new Intent(MywalletActivity.this, DriverCertificationActivity.class);
                                startActivity(intent);
                            }else if (flag.equals("220")){
                                //正在审核中
                                Intent intent = new Intent(MywalletActivity.this, DriverCertificationStateActivity.class);
                                startActivity(intent);
                            }else if (flag.equals("230")){
                                //审核未通过
                                Intent intent = new Intent(MywalletActivity.this, DriverCertificationStateActivity.class);
                                startActivity(intent);
                            }


//                            String substring = body.substring(1, body.length() - 1);
//
//                            if (body.indexOf("司机已被停用") != -1) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(MywalletActivity.this)
//                                        .setTitle("提示")
//                                        .setMessage(body)
//                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//                                            }
//                                        });
//                                builder.show();
//                                return;
//                            }
//                            if (substring.equals("-1")) {
//                                startActivity(new Intent(MywalletActivity.this, DriverCertificationActivity.class));
//                            } else {
//
//                                Gson gson = new Gson();
//                                checkSiJiRenZhengEntityList = gson.fromJson(body, new TypeToken<List<CheckSiJiRenZhengEntity>>() {
//                                }.getType());
//                                String imglist = checkSiJiRenZhengEntityList.get(0).getImglist();
//                                Gson gson1 = new Gson();
//                                checkSiJiRenZhengImgEntityList = gson1.fromJson(imglist, new TypeToken<List<CheckSiJiRenZhengImgEntity>>() {
//                                }.getType());
//                                if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 0) {
//                                    //审核中
//                                    Intent intent = new Intent(MywalletActivity.this, DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
//                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 1) {
//                                    //审核通过
////                                    if (type==1){
////                                        Intent intent1 = new Intent(getContext(), HuoDSuyun1Activity.class);
////                                        intent1.putExtra("cs", tvLocation.getText().toString());
////                                        startActivity(intent1);
////                                    }else if (type==2){
////                                        Intent intent1 = new Intent(getContext(), HuoDKuaiyun1Activity.class);
////                                        intent1.putExtra("cs", tvLocation.getText().toString());
////                                        startActivity(intent1);
////                                    }
//
//                                    Intent intent = new Intent(MywalletActivity.this, DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
//                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 2) {
//                                    //审核拒绝
//                                    Intent intent = new Intent(MywalletActivity.this, DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
//                                }
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
}
