package com.lx.hd.mvp;

import com.lx.hd.bean.CarType;
import com.lx.hd.mvp.base.BasePresenter;
import com.lx.hd.mvp.base.BaseView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 */
@SuppressWarnings("unused")
public interface AskLowPriceContract {
    interface View extends BaseView<Presenter>{
        void showCarType(List<CarType> carTypes);
        void showConfirmResult(boolean b);
    }

    interface Presenter extends BasePresenter{
        void getCarType();
        void toConfirm(HashMap<String,String> map);
    }
}
