package com.lx.hd.ui.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.MainActivity;
import com.lx.hd.R;
import com.lx.hd.account.AccountHelper;
import com.lx.hd.account.LoginActivity;
import com.lx.hd.adapter.MainAdapter;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.base.fragment.BaseFragment;
import com.lx.hd.base.recyclerviewpager.RecyclerViewPager;
import com.lx.hd.bean.CheckSiJiRenZhengEntity;
import com.lx.hd.bean.CheckSiJiRenZhengImgEntity;
import com.lx.hd.bean.DaoMaster;
import com.lx.hd.bean.DaoSession;
import com.lx.hd.bean.LiShiXiaoXiEntity;
import com.lx.hd.bean.PageBean;
import com.lx.hd.bean.PrimaryBanner;
import com.lx.hd.bean.PrimaryBannerDao;
import com.lx.hd.bean.PrimaryNews;
import com.lx.hd.bean.PrimaryNewsDao;
import com.lx.hd.bean.QueryListEntity;
import com.lx.hd.bean.User;
import com.lx.hd.interf.LoadMoreCallBack;
import com.lx.hd.mvp.CarDetialActivity;
import com.lx.hd.test.TestActivity;
import com.lx.hd.ui.activity.AskLowPriceActivity;
import com.lx.hd.ui.activity.AuditStatusActivity;
import com.lx.hd.ui.activity.BalanceActivity;
import com.lx.hd.ui.activity.BannerActivity;
import com.lx.hd.ui.activity.CarAudit1Activity;
import com.lx.hd.ui.activity.CarHomeActivity;
import com.lx.hd.ui.activity.CarHomeBackActivity;
import com.lx.hd.ui.activity.CarLeaseActivity;
import com.lx.hd.ui.activity.CarrenTalActivity;
import com.lx.hd.ui.activity.ConfirmOrderActivity;
import com.lx.hd.ui.activity.CouponCollectionActivity;
import com.lx.hd.ui.activity.CustomerPositionActivity;
import com.lx.hd.ui.activity.DZapplyActivity;
import com.lx.hd.ui.activity.DeliverGoodsActivity;
import com.lx.hd.ui.activity.DriverCertificationActivity;
import com.lx.hd.ui.activity.DriverCertificationStateActivity;
import com.lx.hd.ui.activity.DriverFriendActivity;
import com.lx.hd.ui.activity.DriverZhaoHuoActivity;
import com.lx.hd.ui.activity.DriverZhaoHuoDetailsActivity;
import com.lx.hd.ui.activity.HuoDKuaiyunActivity;
import com.lx.hd.ui.activity.HuoDSuyunActivity;
import com.lx.hd.ui.activity.JiHuoOrderActivity;
import com.lx.hd.ui.activity.DeliverMapActivity;
import com.lx.hd.ui.activity.LiShiXiaoXiActivity;
import com.lx.hd.ui.activity.MywalletActivity;
import com.lx.hd.ui.activity.NewsDetialActivity;
import com.lx.hd.ui.activity.NewsListActivity;
import com.lx.hd.ui.activity.PileApplyActivity;
import com.lx.hd.ui.activity.ProvinceActivity;
import com.lx.hd.ui.activity.ShangChengActivity;
import com.lx.hd.ui.activity.ShopActivity;
import com.lx.hd.ui.activity.ShoppingActivity;
import com.lx.hd.ui.activity.ShoppingCartActivity;
import com.lx.hd.ui.activity.XianZaiActivity;
import com.lx.hd.utils.BroadCastManager;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.utils.GlideImageLoader;
import com.lx.hd.utils.TDevice;
import com.lx.hd.widgets.MyNestedScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrimaryFragment extends BaseFragment implements View.OnClickListener,
        LoadMoreCallBack {

    private RecyclerView recyclerView;
    LoadMoreCallBack loadMoreCallBack;
    MainAdapter activeAdapter;
    MyNestedScrollView scrollView;
    private Banner banner;
    private TextView tvLocation;
    private LinearLayout llyWallet, llyCarTenancy, llyPileApply, llyCarHome;
    private LocalReceiver mReceiver;
    List<String> list = new ArrayList<>();
    List<PrimaryBanner> primaryBanner;
    private int index = 0;
    RelativeLayout rlyTitle;
    public DaoSession daoSession;
    private int page = 1;
    PrimaryNewsDao primaryNewsDao;
    PrimaryBannerDao primaryBannerDao;
    private boolean isLogin;
    private RelativeLayout relative;
    private List<CheckSiJiRenZhengEntity> checkSiJiRenZhengEntityList;
    private List<CheckSiJiRenZhengImgEntity> checkSiJiRenZhengImgEntityList;
    private ImageView sys;
    private static final int RC_EXTERNAL_STORAGE = 0x04;//存储权限
    private static final int REQUEST_CODE_CAMERA = 0x03;//相机权限
    private static final int REQUEST_CODE_LOCTION = 0x02;//相机权限
    private RelativeLayout xinwendongtai;
    private ImageView img_suyun,img_kuaiyun,img_shangcheng,img_xiazai,img_zixun,img_qianbao;
    private TextView tv_weidu;

    @Override
    protected void onBindViewBefore(View root) {
        super.onBindViewBefore(root);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_primary;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        setupDatabase();
        if (!TDevice.hasInternet()) {
            showToast("网络连接错误");
        }
        double w = TDevice.getScreenWidth();
        double a = 428;
        double b = 720;
        double h = a / b * w;
        loadMoreCallBack = this;
        recyclerView = findView(R.id.recyclerView);
        sys = findView(R.id.sys);
        banner = findView(R.id.banner);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) banner.getLayoutParams(); // 取控件mGrid当前的布局参数
        WindowManager windowManager=getActivity().getWindowManager();
        Display display=windowManager.getDefaultDisplay();

        linearParams.height = (int) (display.getHeight()*0.25);// 当控件的高强制设成75象素
        banner.setLayoutParams(linearParams);

        img_suyun=findView(R.id.img_suyun);
        img_kuaiyun=findView(R.id.img_kuaiyun);
        img_shangcheng=findView(R.id.img_shangcheng);
        img_xiazai=findView(R.id.img_xiazai);
        img_zixun=findView(R.id.img_zixun);
        img_qianbao=findView(R.id.img_qianbao);
        tvLocation = findView(R.id.tv_location);
        scrollView = findView(R.id.scrollView);
        scrollView.setOnTouchListener(new TouchListenerImpl());
        llyWallet = findView(R.id.lly_mywallet);
        llyCarTenancy = findView(R.id.lly_car_tenancy);
        llyPileApply = findView(R.id.lly_pile_apply);
        llyCarHome = findView(R.id.lly_car_home);
        relative = findView(R.id.relative);
        xinwendongtai=findView(R.id.xinwendongtai);
        tv_weidu=findView(R.id.tv_weidu);
        xinwendongtai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),NewsListActivity.class));
            }
        });
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProvinceActivity.class);
                startActivity(intent);

            }
        });


//        banner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                if (!TDevice.hasInternet()) {
//                    showToast("网络连接错误");
//                    return;
//                }
//                if (primaryBanner != null) {
//                    PrimaryBanner primaryBanners = primaryBanner.get(position);
//                    if (primaryBanners == null) return;
//                    BannerActivity.show(getContext(), primaryBanners);
//
//                }
//            }
//        });
        sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                requestCamral();
                startActivity(new Intent(getContext(), LiShiXiaoXiActivity.class));
            }
        });
        llyWallet.setOnClickListener(this);
        llyCarTenancy.setOnClickListener(this);
        llyPileApply.setOnClickListener(this);
        llyCarHome.setOnClickListener(this);

        img_suyun.setOnClickListener(this);
        img_kuaiyun.setOnClickListener(this);
        img_shangcheng.setOnClickListener(this);
        img_xiazai.setOnClickListener(this);
        img_zixun.setOnClickListener(this);
        img_qianbao.setOnClickListener(this);
        //接收广播
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("location");
            mReceiver = new LocalReceiver();
            BroadCastManager.getInstance().registerReceiver(getActivity(),
                    mReceiver, filter);//注册广播接收者
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterPermissionGranted(REQUEST_CODE_CAMERA)
    public void requestCamral() {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            Intent intent = new Intent(getActivity(), com.lx.hd.ui.activity.ScannerActivity.class);
            startActivityForResult(intent, Constant.REQUEST_CODE_CAMERA);
        } else {
            EasyPermissions.requestPermissions(this, "相机权限未授权,是否去授权", REQUEST_CODE_CAMERA, Manifest.permission.CAMERA);
        }
    }

    private void initShouQian() {
        PileApi.instance.checkBeViewed()
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

                            if (body.indexOf("true") != -1) {
                                final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                                dialog.show();
                                dialog.getWindow().setContentView(R.layout.dialog_sijishouqian);
                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_bg_yuanjiao);

                                WindowManager windowManager = getActivity().getWindowManager();
                                Display display = windowManager.getDefaultDisplay();
                                WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
                                p.width = (int) (display.getWidth() * 0.6);
                                dialog.getWindow().setAttributes(p);

                                TextView textView = (TextView) dialog.getWindow().findViewById(R.id.tv_chakan);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getContext(), CustomerPositionActivity.class);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        } catch (IOException e) {
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

    @Override
    protected void initData() {
        super.initData();
        loadBanner();

        loadNews();
    }


    private void setupDatabase() {

//创建数据库mbt.db"


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "mbt.db", null);

//获取可写数据库

        SQLiteDatabase db = helper.getWritableDatabase();

//获取数据库对象

        DaoMaster daoMaster = new DaoMaster(db);

//获取Dao对象管理者

        daoSession = daoMaster.newSession();
        primaryNewsDao = daoSession.getPrimaryNewsDao();
        primaryBannerDao = daoSession.getPrimaryBannerDao();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lly_mywallet:
                if (!TDevice.hasInternet()) {
                    showToast("网络链接错误");
                    return;
                }
                showWaitDialog("正在获取登录状态...");
                PileApi.instance.mCheckLoginState()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {

                                try {
                                    hideWaitDialog();
                                    String body = responseBody.string();
                                    if (body.equals("\"true\"")) {
                                        startActivity(new Intent(getContext(), MywalletActivity.class));
                                    } else {
                                        DialogHelper.getConfirmDialog(getContext(), "温馨提示", "当前用户未登录，是否去登录", "去登录", "取消", true, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(getContext(), LoginActivity.class));
                                            }
                                        }, null).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    onError(e);
                                }

                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                hideWaitDialog();
                                showToast("登录状态获取失败，无法获取钱包信息");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case R.id.lly_car_tenancy:
                startActivity(new Intent(getContext(), CarAudit1Activity.class));

                break;
            case R.id.lly_pile_apply:
                if (!TDevice.hasInternet()) {
                    showToast("网络链接错误");
                    return;
                }
                showWaitDialog("正在获取登录状态...");
                PileApi.instance.mCheckLoginState()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {

                                try {
                                    hideWaitDialog();
                                    String body = responseBody.string();
                                    if (body.equals("\"true\"")) {
                                        //   Intent intent1 = new Intent(getContext(), DeliverGoodsActivity.class);
                                        //  intent1.putExtra("crty", tvLocation.getText().toString());
                                        Intent intent1 = new Intent(getContext(), HuoDSuyunActivity.class);
                                        intent1.putExtra("cs", tvLocation.getText().toString());
                                        startActivity(intent1);
                                    } else {
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
                                            requestLogin(name,password,1);
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    onError(e);
                                }

                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                hideWaitDialog();
                                showToast("登录状态获取失败");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

//                startActivity(new Intent(getContext(),DZapplyActivity.class));


                break;
            case R.id.lly_car_home:

                initLogin();
//                startActivity(new Intent(getContext(),CarHomeActivity.class));
                break;

            case R.id.img_suyun:
                if (!TDevice.hasInternet()) {
                    showToast("网络链接错误");
                    return;
                }
                showWaitDialog("正在获取登录状态...");
                PileApi.instance.mCheckLoginState()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {

                                try {
                                    hideWaitDialog();
                                    String body = responseBody.string();
                                    if (body.equals("\"true\"")) {
                                        //   Intent intent1 = new Intent(getContext(), DeliverGoodsActivity.class);
                                        //  intent1.putExtra("crty", tvLocation.getText().toString());
                                        Intent intent1 = new Intent(getContext(), HuoDSuyunActivity.class);
                                        intent1.putExtra("cs", tvLocation.getText().toString());
                                        startActivity(intent1);
                                    } else {
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
                                            requestLogin(name,password,1);
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    onError(e);
                                }

                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                hideWaitDialog();
                                showToast("登录状态获取失败");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case R.id.img_kuaiyun:
                initLogin();
                break;
            case R.id.img_shangcheng:
                startActivity(new Intent(getContext(), ShangChengActivity.class));
                break;
            case R.id.img_xiazai:
                startActivity(new Intent(getContext(), XianZaiActivity.class));
                break;
            case R.id.img_zixun:
                startActivity(new Intent(getContext(),DriverFriendActivity.class));
                break;
            case R.id.img_qianbao:
                if (!TDevice.hasInternet()) {
                    showToast("网络链接错误");
                    return;
                }
                showWaitDialog("正在获取登录状态...");
                PileApi.instance.mCheckLoginState()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.annotations.NonNull ResponseBody responseBody) {

                                try {
                                    hideWaitDialog();
                                    String body = responseBody.string();
                                    if (body.equals("\"true\"")) {
                                        startActivity(new Intent(getContext(), MywalletActivity.class));
                                    } else {
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
                                            requestLogin(name,password,3);
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    onError(e);
                                }

                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                hideWaitDialog();
                                showToast("登录状态获取失败，无法获取钱包信息");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
        }
    }

    private void requestLogin(String tempUsername, String tempPwd, final int i) {
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
                                        if (i==1){
                                            Intent intent1 = new Intent(getContext(), HuoDSuyunActivity.class);
                                            intent1.putExtra("cs", tvLocation.getText().toString());
                                            startActivity(intent1);
                                        }else if (i==2){
                                            Intent intent1 = new Intent(getContext(), HuoDKuaiyunActivity.class);
                                            intent1.putExtra("cs", tvLocation.getText().toString());
                                            startActivity(intent1);
                                        }else if (i==3){
                                            startActivity(new Intent(getContext(), MywalletActivity.class));
                                        }

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

    //判断登录
    private void initLogin() {
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
//                                initPanduan();
                                Intent intent1 = new Intent(getContext(), HuoDKuaiyunActivity.class);
                                intent1.putExtra("cs", tvLocation.getText().toString());
                                startActivity(intent1);
                            } else {

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
                                    requestLogin(name,password,2);
                                }
                            }
                        } catch (IOException e) {
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

//    private void initPanduan() {
//        PileApi.instance.checkDriverSPStatus()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull ResponseBody responseBody) {
//                        try {
//                            String body = responseBody.string();
//                            System.out.println(body);
//                            String substring = body.substring(1, body.length() - 1);
//
//                            if (body.indexOf("司机已被停用") != -1) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
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
//                                startActivity(new Intent(getContext(), DriverCertificationActivity.class));
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
//                                    Intent intent = new Intent(getActivity(), DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
//                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 1) {
//                                    //审核通过
//                                    Intent intent = new Intent(getActivity(), DriverZhaoHuoActivity.class);
//                                    intent.putExtra("city", tvLocation.getText());
//                                    startActivity(intent);
//                                } else if (checkSiJiRenZhengEntityList.get(0).getDriver_info_status() == 2) {
//                                    //审核拒绝
//                                    Intent intent = new Intent(getActivity(), DriverCertificationStateActivity.class);
////                                    intent.putExtra("shuju", (Serializable) checkSiJiRenZhengEntityList);
////                                    intent.putExtra("tupian", (Serializable) checkSiJiRenZhengImgEntityList);
//                                    startActivity(intent);
//                                }
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        System.out.println("2222");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        System.out.println();
//                    }
//                });
//    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //收到广播后的处理

            String location = intent.getStringExtra("location");
            if (Constant.city.equals("")) {
                tvLocation.setText(location);
            }


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BroadCastManager.getInstance().unregisterReceiver(getActivity(), mReceiver);//注销广播接收者
        // getContext().unregisterReceiver(mReceiver);
    }

    private class TouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int scrollY = view.getScrollY();
            int height = view.getHeight();
            int scrollViewMeasuredHeight = scrollView.getChildAt(0).getMeasuredHeight();

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_MOVE:
                    index++;
                    if (activeAdapter != null) {
                        activeAdapter.toLoad();
                    }

                    if (scrollY == 0) {
                        System.out.println("滑动到了顶端 view.getScrollY()=" + scrollY);
                    }

                    //    if((scrollY+height)>=scrollViewMeasuredHeight){
                    if (scrollView.getScrollY() - scrollY < 2 && scrollView.getScrollY() >= scrollY) {

                        System.out.println("滑动到了底部 scrollY=" + scrollY);
                        System.out.println("滑动到了底部 height=" + height);
                        System.out.println("滑动到了底部 scrollViewMeasuredHeight=" + scrollViewMeasuredHeight);
                    }
                    break;

                default:
                    break;
            }

            if (motionEvent.getAction() == MotionEvent.ACTION_UP && index > 0) {
                index = 0;
                page = ++page;
                if (scrollViewMeasuredHeight <= scrollY + height) {
                    if (activeAdapter != null) {
                        activeAdapter.isLoadMore(true, 1);
                    }
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("appstatus", "0");
                    Gson gson = new Gson();
                    String data = gson.toJson(map);
                    PileApi.instance.getNewsList(page, "8", data)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<PageBean<PrimaryNews>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull final PageBean<PrimaryNews> activesBeanPageBean) {

                                    if (activeAdapter != null) {
                                        activeAdapter.addAll(activesBeanPageBean.getRows());
                                        activeAdapter.isLoadMore(false, activesBeanPageBean.getRows().size());
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    if (activeAdapter != null) {
                                        activeAdapter.loadFail();
                                    }
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }
            return false;
        }

    }

    @Override
    public void loadData(List<PrimaryNews> primaryNewsList) {

    }


    private void loadBanner() {
        if (!TDevice.hasInternet()) {
            if (primaryBannerDao != null) {
                List<PrimaryBanner> stuList = primaryBannerDao.queryBuilder().list();
                for (int i = 0; i < stuList.size(); i++) {
                    String url = Constant.BASE_URL + stuList.get(i).getFolder() + stuList.get(i).getAutoname();
                    list.add(url);
                }

                //设置banner样式
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                banner.setImages(list);
                //设置banner动画效果
                banner.setBannerAnimation(Transformer.Default);
                //设置自动轮播，默认为true
                banner.isAutoPlay(true);
                //设置轮播时间
                banner.setDelayTime(3000);
                //设置指示器位置（当banner模式中有指示器时）
                banner.setIndicatorGravity(BannerConfig.CENTER);
                //banner设置方法全部调用完毕时最后调用
                banner.start();

            }
            return;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appstatus", "0");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.getBanner(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PrimaryBanner>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        try {
                            if (primaryBannerDao != null) {
                                primaryBannerDao.deleteAll();
                            }

                        } catch (Exception e) {
                        }

                    }

                    @Override
                    public void onNext(@NonNull List<PrimaryBanner> primaryBanners) {

                        list = new ArrayList<>();
                        primaryBanner = primaryBanners;
                        for (int i = 0; i < primaryBanners.size(); i++) {
                            String url = Constant.BASE_URL + primaryBanners.get(i).getFolder() + primaryBanners.get(i).getAutoname();
                            list.add(url);
                        }

                        //设置banner样式
                        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                        //设置图片加载器
                        banner.setImageLoader(new GlideImageLoader());
                        //设置图片集合
                        banner.setImages(list);
                        //设置banner动画效果
                        banner.setBannerAnimation(Transformer.Default);
                        //设置自动轮播，默认为true
                        banner.isAutoPlay(true);
                        //设置轮播时间
                        banner.setDelayTime(3000);
                        //设置指示器位置（当banner模式中有指示器时）
                        banner.setIndicatorGravity(BannerConfig.CENTER);
                        //banner设置方法全部调用完毕时最后调用
                        banner.start();


                        List<PrimaryBanner> stuList;
                        stuList = primaryBanners;
                        try {
                            if (primaryBannerDao != null) {
                                primaryBannerDao.insertInTx(stuList);
                            }

                        } catch (Exception e) {
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

    private void loadNews() {
//        if (!TDevice.hasInternet()) {
//            if (primaryNewsDao != null) {
//                List<PrimaryNews> stuList = primaryNewsDao.queryBuilder().list();
//                final PageBean<PrimaryNews> activesBeanPageBean = new PageBean<>();
//                activesBeanPageBean.setRows(stuList);
//                GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
//                linearLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
//                activeAdapter = new MainAdapter(getActivity(), activesBeanPageBean);
//                recyclerView.setLayoutManager(linearLayoutManager);
//                recyclerView.setAdapter(activeAdapter);
//                PagerSnapHelper snapHelper = new PagerSnapHelper();
//                snapHelper.attachToRecyclerView(recyclerView);
//                activeAdapter.setOnItemClickListener(new MainAdapter.OnItemClickLitener() {
//                    @Override
//                    public void onItemClick(int position, long itemId) {
//                        showToast("网络连接错误");
//                    }
//                });
//
//            }
//            return;
//        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appstatus", "0");
        Gson gson = new Gson();
        String data = gson.toJson(map);
        PileApi.instance.getNewsList(1, "100", data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageBean<PrimaryNews>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        try {
                            if (primaryNewsDao != null) {
                                primaryNewsDao.deleteAll();
                            }

                        } catch (Exception e) {
                        }

                    }

                    @Override
                    public void onNext(@NonNull final PageBean<PrimaryNews> activesBeanPageBean) {

                        List<PrimaryNews> stuList;
                        stuList = activesBeanPageBean.getRows();
                        try {
                            if (primaryNewsDao != null) {
                                primaryNewsDao.insertInTx(stuList);
                            }

                        } catch (Exception e) {
                        }


                        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2) {
                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        };
                        // showToast(activesBeanPageBean.getRows().size() + "");

                        activeAdapter = new MainAdapter(getActivity(), activesBeanPageBean);
                        linearLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
                        recyclerView.setLayoutManager(linearLayoutManager);
                        PagerSnapHelper snapHelper = new PagerSnapHelper();
                        snapHelper.attachToRecyclerView(recyclerView);
                        recyclerView.setAdapter(activeAdapter);
                        activeAdapter.setOnItemClickListener(new MainAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(int position, long itemId) {
                                if (!TDevice.hasInternet()) {
                                    showToast("网络连接错误");
                                    return;
                                }
//                                //去除头和尾
//                                if (position == 0) {
//
//                                    startActivity(new Intent(getContext(), NewsListActivity.class));
//                                } else if (position == activesBeanPageBean.getRows().size()) {
//
//                                } else {

                                PrimaryNews primaryNews = activesBeanPageBean.getRows().get(position);
                                if (primaryNews == null) return;
                                NewsDetialActivity.show(getContext(), primaryNews);
//                                }

                            }
                        });

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void iflogin() {

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

                                isLogin = true;

                                //跳转
                            } else {
                                isLogin = false;
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


    @Override
    public void onStart() {
        super.onStart();
        iflogin();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!Constant.city.equals("")) {
            tvLocation.setText(Constant.city);
        }

        Constant.HUOYUAN_SHI=tvLocation.getText().toString();

        initShouQian();

        initXiaoXiGeShu();
    }

    private void initXiaoXiGeShu() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "0");
        final Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.searchUserMsgNoRead(params)
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
                            JSONObject jsonObject = new JSONObject(body);
                            String flag = jsonObject.getString("flag");
                            if (flag.equals("200")) {
                                JSONArray response = jsonObject.getJSONArray("response");
                                JSONObject jsonObject1 = response.getJSONObject(0);
                                String noread = jsonObject1.getString("noread");
                                if (Integer.parseInt(noread)>0){
                                    tv_weidu.setVisibility(View.VISIBLE);
                                    tv_weidu.setText(noread);
                                }else {
                                    tv_weidu.setVisibility(View.GONE);
                                }


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
}
