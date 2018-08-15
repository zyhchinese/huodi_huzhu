package com.lx.hd.mvp;

import com.lx.hd.bean.CarInfo;
import com.lx.hd.mvp.base.BasePresenter;
import com.lx.hd.mvp.base.BaseView;

import java.util.List;

@SuppressWarnings("unused")
interface CarDetailContract {

    interface View extends BaseView<Presenter> {
        void showBanner(List<String> banners);
        void showCarInfo(List<CarInfo> carInfos);
        void showWebView(String content);

    }

    interface Presenter extends BasePresenter {

        void getBanner(String id);

        void getCarInfo(String id);//获得详情

        void getWebView();
    }
}
