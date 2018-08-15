package com.lx.hd.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.lx.hd.AppContext;
import com.lx.hd.bean.VersionBean;
import com.lx.hd.api.ApiService;
import com.lx.hd.utils.DialogHelper;
import com.lx.hd.utils.TDevice;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by haibin
 * on 2016/10/19.
 */
public class CheckUpdateManager {

    private ApiService service;
    private ProgressDialog mWaitDialog;
    private Context mContext;
    private boolean mIsShowDialog;
    private RequestPermissions mCaller;

    public CheckUpdateManager(Context context, boolean showWaitingDialog) {
        this.mContext = context;
        mIsShowDialog = showWaitingDialog;
        if (mIsShowDialog) {
            mWaitDialog = DialogHelper.getProgressDialog(mContext);
            mWaitDialog.setMessage("正在检查中...");
            mWaitDialog.setCancelable(true);
            mWaitDialog.setCanceledOnTouchOutside(true);
        }
    }


    public void checkUpdate() {
        if (mIsShowDialog) {
            mWaitDialog.show();
        }

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true); // 失败重发
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://118.190.47.231:8004/lxpub/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(builder.build())
                .build();
        service= retrofit.create(ApiService.class);
        Observable<ResponseBody> mUpdateApp=service.mUpdateApp();
        mUpdateApp .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String body=responseBody.string();
                            Gson gson=new Gson();
                            VersionBean version=gson.fromJson(body,VersionBean.class);
                            double curVersionCode = Double.parseDouble(TDevice.getVersionName(AppContext
                                    .getInstance().getPackageName()));

                            if (curVersionCode < Double.parseDouble(version.getApp_version())) {
                                mCaller.call(version);
                                UpdateActivity.show((Activity) mContext, version);

                            } else {

                                if (mIsShowDialog) {
                                    NoUpdateActivity.show((Activity) mContext, version);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (mIsShowDialog) {
                            DialogHelper.getMessageDialog(mContext, "网络异常，无法获取新版本信息").show();
                        }
                        if (mWaitDialog != null) {
                            mWaitDialog.dismiss();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mWaitDialog != null) {
                            mWaitDialog.dismiss();
                        }
                    }
                });

    }

    public void setCaller(RequestPermissions caller) {
        this.mCaller = caller;
    }

    public interface RequestPermissions {
        void call(VersionBean version);
    }
}
