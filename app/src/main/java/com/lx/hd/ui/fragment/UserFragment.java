package com.lx.hd.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.R;
import com.lx.hd.account.AccountHelper;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.fragment.BaseFragment;
import com.lx.hd.bean.User;
import com.lx.hd.bean.VersionBean;
import com.lx.hd.nav.NavFragment;
import com.lx.hd.ui.activity.AboutActivity;
import com.lx.hd.ui.activity.ActiveListActivity;
import com.lx.hd.ui.activity.BalanceActivity;
import com.lx.hd.ui.activity.CarrenTal2Activity;
import com.lx.hd.ui.activity.ChangJianWenTiActivity;
import com.lx.hd.ui.activity.ChargeOrderActivity;
import com.lx.hd.ui.activity.CouponActivity;
import com.lx.hd.ui.activity.CustomerPositionActivity;
import com.lx.hd.ui.activity.DriverCertificationActivity;
import com.lx.hd.ui.activity.DriverCertificationStateActivity;
import com.lx.hd.ui.activity.ErShouCheShouYeActivity;
import com.lx.hd.ui.activity.FeedBackActivity;
import com.lx.hd.ui.activity.KaiJuFaPiaoActivity;
import com.lx.hd.ui.activity.MywalletActivity;
import com.lx.hd.ui.activity.PersonalInformationActivity;
import com.lx.hd.ui.activity.QianDaoActivity;
import com.lx.hd.ui.activity.SecurityActivity;
import com.lx.hd.ui.activity.SetupActivity;
import com.lx.hd.ui.activity.ShangChengActivity;
import com.lx.hd.ui.activity.ShopActivity;
import com.lx.hd.ui.activity.XianZaiActivity;
import com.lx.hd.ui.activity.YongHuZhiNanActivity;
import com.lx.hd.update.CheckUpdateManager;
import com.lx.hd.utils.DialogHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.lx.hd.base.Constant.REQUEST_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener,
        EasyPermissions.PermissionCallbacks,CheckUpdateManager.RequestPermissions {
    RequestOptions mOptions;
    boolean isLogin = false;
    private CircleImageView mAvatar;
    private TextView tvUserName, icon_wdqb, icon_sz;
    private LinearLayout icon_wdzd, icon_sc,linear_yue,linear_youhuiquan,linear_fapiaoyubaoxiao,
            linear_dingdan,linear_shimingrenzheng,linear_ershoucheshichang,linear_zuixinhuodong,img_call,
            linear_guanyuwomen,linear_banbengengxin,linear_anquanzhongxin,linear_yingyongxiazai,
            linear_yijianyujianyi,linear_yonghuzhinan,linear_changjianwenti,linear_tuichudenglu;
//    private ImageView img_call;
    private TextView tv_qiandao,tv_renzheng;
    private NavFragment mNavBar;
    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限

    @Override
    protected void onBindViewBefore(View root) {
        super.onBindViewBefore(root);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_hd11;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mOptions = new RequestOptions()
                .placeholder(R.mipmap.user_header_defult)
                .error(R.mipmap.user_header_defult)
                .fitCenter();
        mAvatar = findView(R.id.img_avatar);
        icon_wdqb = findView(R.id.icon_wdqb);
        icon_wdzd = findView(R.id.icon_wdzd);
        icon_sc = findView(R.id.icon_sc);
        icon_sz = findView(R.id.icon_sz);
        tvUserName = findView(R.id.tv_user_name);
        img_call=findView(R.id.img_call);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        mNavBar = ((NavFragment) manager.findFragmentById(R.id.fag_nav));

        tv_qiandao=findView(R.id.tv_qiandao);
        linear_yue=findView(R.id.linear_yue);
        linear_youhuiquan=findView(R.id.linear_youhuiquan);
        linear_fapiaoyubaoxiao=findView(R.id.linear_fapiaoyubaoxiao);
        linear_dingdan=findView(R.id.linear_dingdan);
        linear_shimingrenzheng=findView(R.id.linear_shimingrenzheng);
        linear_ershoucheshichang=findView(R.id.linear_ershoucheshichang);
        linear_zuixinhuodong=findView(R.id.linear_zuixinhuodong);
        linear_guanyuwomen=findView(R.id.linear_guanyuwomen);
        linear_banbengengxin=findView(R.id.linear_banbengengxin);
        linear_anquanzhongxin=findView(R.id.linear_anquanzhongxin);
        linear_yingyongxiazai=findView(R.id.linear_yingyongxiazai);
        linear_yijianyujianyi=findView(R.id.linear_yijianyujianyi);
        linear_yonghuzhinan=findView(R.id.linear_yonghuzhinan);
        linear_changjianwenti=findView(R.id.linear_changjianwenti);
        linear_tuichudenglu=findView(R.id.linear_tuichudenglu);
        tv_renzheng=findView(R.id.tv_renzheng);

        mAvatar.setOnClickListener(this);
        icon_wdqb.setOnClickListener(this);
        icon_wdzd.setOnClickListener(this);
        icon_sc.setOnClickListener(this);
        icon_sz.setOnClickListener(this);
        img_call.setOnClickListener(this);
        tv_qiandao.setOnClickListener(this);
        linear_yue.setOnClickListener(this);
        linear_youhuiquan.setOnClickListener(this);
        linear_fapiaoyubaoxiao.setOnClickListener(this);
        linear_dingdan.setOnClickListener(this);
        linear_shimingrenzheng.setOnClickListener(this);
        linear_ershoucheshichang.setOnClickListener(this);
        linear_zuixinhuodong.setOnClickListener(this);
        linear_guanyuwomen.setOnClickListener(this);
        linear_banbengengxin.setOnClickListener(this);
        linear_anquanzhongxin.setOnClickListener(this);
        linear_yingyongxiazai.setOnClickListener(this);
        linear_yijianyujianyi.setOnClickListener(this);
        linear_yonghuzhinan.setOnClickListener(this);
        linear_changjianwenti.setOnClickListener(this);
        linear_tuichudenglu.setOnClickListener(this);


    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {

        if (isLogin) {
            switch (v.getId()) {
                case R.id.img_avatar:
                    startActivity(new Intent(getContext(), PersonalInformationActivity.class));
                    break;
                case R.id.icon_wdqb:
                    startActivity(new Intent(getContext(), MywalletActivity.class));
                    break;
                case R.id.icon_wdzd:
                    startActivity(new Intent(getContext(), CustomerPositionActivity.class));
                    break;
                case R.id.icon_sc:
//                    startActivity(new Intent(getContext(), ShopActivity.class));
                    startActivity(new Intent(getContext(), ShangChengActivity.class));
                    break;
                case R.id.icon_sz:
                    startActivity(new Intent(getContext(), SetupActivity.class));
                    break;
                case R.id.img_call:
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext())
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
                case R.id.tv_qiandao:
                    startActivity(new Intent(getContext(), QianDaoActivity.class));
                    break;
                case R.id.linear_yue:
                    startActivity(new Intent(getContext(), BalanceActivity.class));
                    break;
                case R.id.linear_youhuiquan:
                    startActivity(new Intent(getContext(), CouponActivity.class));
                    break;
                case R.id.linear_fapiaoyubaoxiao:
                    startActivity(new Intent(getContext(), KaiJuFaPiaoActivity.class));
                    break;
                case R.id.linear_dingdan:
                    mNavBar.select1(2);
                    break;
                case R.id.linear_shimingrenzheng:
                    initPanduan();
                    break;
                case R.id.linear_ershoucheshichang:
                    startActivity(new Intent(getContext(), ErShouCheShouYeActivity.class));
                    break;
                case R.id.linear_zuixinhuodong:
                    startActivity(new Intent(getContext(), ActiveListActivity.class));
                    break;
                case R.id.linear_guanyuwomen:
                    startActivity(new Intent(getContext(), AboutActivity.class));
                    break;
                case R.id.linear_banbengengxin:
                    requestExternalStorage();
                    break;
                case R.id.linear_anquanzhongxin:
                    startActivity(new Intent(getContext(), SecurityActivity.class));
                    break;
                case R.id.linear_yingyongxiazai:
                    startActivity(new Intent(getContext(), XianZaiActivity.class));
                    break;
                case R.id.linear_yijianyujianyi:
                    startActivity(new Intent(getContext(), FeedBackActivity.class));
                    break;
                case R.id.linear_yonghuzhinan:
                    startActivity(new Intent(getContext(),YongHuZhiNanActivity.class));
                    break;
                case R.id.linear_changjianwenti:
                    startActivity(new Intent(getContext(),ChangJianWenTiActivity.class));
                    break;
                case R.id.linear_tuichudenglu:
                    new AlertDialog.Builder(getContext()).setTitle("确认退出登录吗？")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击“确认”后的操作
                                    showWaitDialog("正在注销...请稍候");
                                    PileApi.instance.mLogout()
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
                                                        if (body.indexOf("true") != -1) {
                                                            hideWaitDialog();

                                                            JPushInterface.stopPush(getContext());
                                                            JPushInterface.setAlias(getContext(),"",null);

                                                            SharedPreferences sharedPreferences=getContext().getSharedPreferences("userpassword", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor1=sharedPreferences.edit();
                                                            editor1.putString("name","");
                                                            editor1.putString("password","");
                                                            editor1.apply();

                                                            startActivity(new Intent(getContext(), LoginActivity.class));

                                                        } else {
                                                            showToast("注销失败");
                                                            hideWaitDialog();
                                                        }
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }

                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {
                                                    showToast("网络状况不好，请重试");
                                                    hideWaitDialog();

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });

                                }
                            })
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击“返回”后的操作,这里不设置没有任何操作
                                }
                            }).show();
                    break;

            }
        } else {
            DialogHelper.getConfirmDialog(getContext(), "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }, null).show();
        }

    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            CheckUpdateManager manager = new CheckUpdateManager(getContext(), true);
            manager.setCaller(this);
            manager.checkUpdate();
        } else {
            EasyPermissions.requestPermissions(this, "存储权限未授权,是否去授权", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
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
                                Intent intent = new Intent(getContext(), DriverCertificationStateActivity.class);
                                startActivity(intent);
                            }else if (flag.equals("210")){
                                //未认证
                                Intent intent = new Intent(getContext(), DriverCertificationActivity.class);
                                startActivity(intent);
                            }else if (flag.equals("220")){
                                //正在审核中
                                Intent intent = new Intent(getContext(), DriverCertificationStateActivity.class);
                                startActivity(intent);
                            }else if (flag.equals("230")){
                                //审核未通过
                                Intent intent = new Intent(getContext(), DriverCertificationStateActivity.class);
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

    private void initPanduan1() {
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
//                                Intent intent = new Intent(getContext(), DriverCertificationStateActivity.class);
//                                startActivity(intent);
                                tv_renzheng.setTextColor(Color.parseColor("#ffe307"));
                                tv_renzheng.setText("已认证");
                                Drawable drawable=getResources().getDrawable(R.mipmap.img_huodiyirenzheng111);
                                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                                tv_renzheng.setCompoundDrawables(drawable,null,null,null);
                            }else if (flag.equals("210")){
                                //未认证
//                                Intent intent = new Intent(getContext(), DriverCertificationActivity.class);
//                                startActivity(intent);
                                tv_renzheng.setTextColor(Color.parseColor("#ffffff"));
                                tv_renzheng.setText("未认证");
                                Drawable drawable=getResources().getDrawable(R.mipmap.img_huodiweirenzheng111);
                                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                                tv_renzheng.setCompoundDrawables(drawable,null,null,null);
                            }else if (flag.equals("220")){
                                //正在审核中
//                                Intent intent = new Intent(getContext(), DriverCertificationStateActivity.class);
//                                startActivity(intent);
                                tv_renzheng.setTextColor(Color.parseColor("#ffffff"));
                                tv_renzheng.setText("未认证");
                                Drawable drawable=getResources().getDrawable(R.mipmap.img_huodiweirenzheng111);
                                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                                tv_renzheng.setCompoundDrawables(drawable,null,null,null);
                            }else if (flag.equals("230")){
                                //审核未通过
//                                Intent intent = new Intent(getContext(), DriverCertificationStateActivity.class);
//                                startActivity(intent);
                                tv_renzheng.setTextColor(Color.parseColor("#ffffff"));
                                tv_renzheng.setText("未认证");
                                Drawable drawable=getResources().getDrawable(R.mipmap.img_huodiweirenzheng111);
                                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                                tv_renzheng.setCompoundDrawables(drawable,null,null,null);
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

    //打电话
    private void testCallPhone() {

        if (Build.VERSION.SDK_INT >= 23) {

            //判断有没有拨打电话权限
            if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                //请求拨打电话权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

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
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

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
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//            //ToastUtils.show(context, "授权成功");
//            callPhone("0531-88807916");
//        } else {
//            Toast.makeText(getContext(), "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
//        }
    }

    private void updateUserInfo(User user) {
        if (user != null) {
            tvUserName.setText(user.getCustname());
            String userLogo = Constant.BASE_URL + user.getFolder() + "/" + user.getAutoname();
            Glide.with(getContext())
                    .load(userLogo)
                    .apply(mOptions)
                    .into(mAvatar);
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
                                initPanduan1();
                                isLogin = true;
                            } else {
                                //tvUserName.setText("麦巴特用户");
                                //Glide.with(getContext())
                                // .load("")
                                //  .apply(mOptions)
                                //  .into(mAvatar);
                                isLogin = false;
                                SharedPreferences sharedPreferences=getContext().getSharedPreferences("userpassword", Context.MODE_PRIVATE);
                                String name = sharedPreferences.getString("name", "");
                                String password = sharedPreferences.getString("password", "");
                                if (name.equals("")||password.equals("")){
                                    DialogHelper.getConfirmDialog(getContext(), "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(getContext(), LoginActivity.class));
                                        }
                                    }, null).show();
                                }else {
                                    requestLogin(name,password);
                                }
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

    private void requestLogin(String tempUsername, String tempPwd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("account", tempUsername);
        map.put("password", tempPwd);
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.mLogin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        //  if (AccountHelper.login(user, headers)) {
                        try {
                            String body = responseBody.string();
                            if (body.length() < 10) {
                                if (body.equals("\"false\"")){
//                                    showToast("用户名或密码错误");
                                }

                            } else {
                                Gson gson = new Gson();
                                try {
                                    List<User> userList = gson.fromJson(body, new TypeToken<List<User>>() {
                                    }.getType());
                                    if (AccountHelper.login(userList.get(0))) {
                                        isLogin=true;
                                        getUserInfo();
                                        initPanduan1();
//                                        MyJPushMessageReceiver myJPushMessageReceiver=new MyJPushMessageReceiver();
//                                        JPushMessage jPushMessage=new JPushMessage();
//                                        jPushMessage.setAlias(userList.get(0).getCustphone());
//                                        myJPushMessageReceiver.onAliasOperatorResult(LoginActivity.this,jPushMessage);

//                                        JPushInterface.init(LoginActivity.this);
//                                        if (JPushInterface.isPushStopped(LoginActivity.this)){
//                                            JPushInterface.resumePush(LoginActivity.this);
//                                        }
//                                        JPushInterface.setAlias(LoginActivity.this,userList.get(0).getCustphone(),null);


                                    } else {
                                        showToast("账号异常");
                                    }
                                } catch (Exception e) {
                                    showToast("用户信息获取异常，请重新登录");
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


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        for (String perm : perms) {
            if (perm.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                DialogHelper.getConfirmDialog(getContext(), "温馨提示", "需要开启对您手机的存储权限才能下载安装，是否现在开启", "去开启", "取消", true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                    }
                }, null).show();

            } else {
                //  Setting.updateLocationPermission(getApplicationContext(), false);
            }

        }

        if (requestCode == REQUEST_CODE && PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            //ToastUtils.show(context, "授权成功");
            callPhone("0531-88807916");
        } else {
            Toast.makeText(getContext(), "您拒绝了电话申请权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void call(VersionBean version) {

    }
}
