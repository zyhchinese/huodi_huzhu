package com.lx.hd.mvp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.api.PileApi;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.CarInfo;
import com.lx.hd.bean.CarType;
import com.lx.hd.bean.PrimaryBanner;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/9/20.
 */

public class DetailPresenter implements CarDetailContract.Presenter{
    List<String> list;
    private CarDetailContract.View mView;
    public DetailPresenter (CarDetailContract.View mView){
        this.mView=mView;
        this.mView.setPresenter(this);
    }
    @Override
    public void getBanner(String id) {
        HashMap<String,String> map=new HashMap<>();
        map.put("id",id);
        Gson gson=new Gson();
        String data=gson.toJson(map);
        PileApi.instance.getCarDetailUp(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PrimaryBanner>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<PrimaryBanner> primaryBanners) {

                        list = new ArrayList<>();
                        for (int i = 0; i < primaryBanners.size(); i++) {
                            String url = Constant.BASE_URL + primaryBanners.get(i).getFolder() + primaryBanners.get(i).getAutoname();
                            list.add(url);
                        }
                        mView.showBanner(list);
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
    public void getCarInfo(String id) {
        HashMap<String,String> map=new HashMap<>();
        map.put("id",id);
        Gson gson=new Gson();
        String data=gson.toJson(map);
        PileApi.instance.getCarDetailDown(data)
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
                            Gson gson=new Gson();
                            Type type= new TypeToken<List<CarInfo>>() {
                            }.getType();
                            List<CarInfo> carInfos=gson.fromJson(body,type);
                            mView.showCarInfo(carInfos);
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

    @Override
    public void getWebView() {

        HashMap<String,String> map=new HashMap<>();
        map.put("id","39");
        map.put("appstatus","0");
        Gson gson=new Gson();
        String params=gson.toJson(map);
        PileApi.instance.getNewsDetial(params)
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
                            String note="";
                            try {
                                JSONArray jsonArray=new JSONArray(body);
                                for (int i=0;i<jsonArray.length();i++){
                                    note=jsonArray.getJSONObject(i).getString("note");
                                }
                                mView.showWebView(note);
                             //   webView.loadDataWithBaseURL(Constant.BASE_URL,note,"text/html","utf-8",null);
                            } catch (JSONException e) {
                                e.printStackTrace();
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
}
