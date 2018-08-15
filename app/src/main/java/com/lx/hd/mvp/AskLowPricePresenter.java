package com.lx.hd.mvp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.hd.api.PileApi;
import com.lx.hd.bean.CarType;
import com.lx.hd.bean.ChargeOrder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/9/21.
 */

public class AskLowPricePresenter implements AskLowPriceContract.Presenter{

    private AskLowPriceContract.View view;
    public AskLowPricePresenter(AskLowPriceContract.View view){
        this.view=view;
        view.setPresenter(this);
    }
    @Override
    public void getCarType() {
        PileApi.instance.getCarType()
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
                            Type type= new TypeToken<List<CarType>>() {
                            }.getType();
                            List<CarType> carTypes=gson.fromJson(body,type);
                            view.showCarType(carTypes);

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
    public void toConfirm(HashMap<String,String> map) {
        Gson gson=new Gson();
        String data=gson.toJson(map);
        PileApi.instance.addCarRecord(data)
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
                                view.showConfirmResult(true);
                            }else {
                                view.showConfirmResult(false);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            onError(e);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showConfirmResult(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
