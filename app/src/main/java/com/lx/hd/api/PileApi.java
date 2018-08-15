package com.lx.hd.api;

import com.google.gson.Gson;
import com.lx.hd.base.Constant;
import com.lx.hd.bean.CarParts;
import com.lx.hd.bean.ChargeOrder;
import com.lx.hd.bean.PageBean;
import com.lx.hd.bean.PrimaryBanner;
import com.lx.hd.bean.PrimaryNews;
import com.lx.hd.bean.ShopListType;
import com.lx.hd.bean.TiXianStory;
import com.lx.hd.bean.activebean;
import com.lx.hd.bean.carhometopBean;
import com.lx.hd.bean.myorderbean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PileApi {
    public static PileApi instance;
    private Gson gson = new Gson();
    private ApiService service;

    public PileApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        service = retrofit.create(ApiService.class);
    }

    public static PileApi getInstance(OkHttpClient okHttpClient) {
        if (instance == null)
            instance = new PileApi(okHttpClient);
        return instance;
    }

    //验证手机号是否为当前用户所有
    public Observable<ResponseBody> mCheckPhone(String params) {
        return service.mCheckPhone(params);
    }

    //获取验证码
    public Observable<ResponseBody> mGetAuthCode(String params) {
        return service.mGetAuthCode(params);
    }

    public Observable<ResponseBody> mRegister(String params) {
        return service.mRegister(params);
    }

    /**
     * 登录
     *
     * @param parmas
     * @return
     */
    public Observable<ResponseBody> mLogin(String parmas) {
        return service.mLogin(parmas);
    }

    public Observable<PageBean<PrimaryNews>> getNewsList(int page, String rows, String params) {
        return service.getNewsList(page, rows, params);
    }

    public Observable<List<PrimaryBanner>> getBanner(String params) {
        return service.getBanner(params);//货主端固定传0，司机端固定传1
    }

    public Observable<ResponseBody> getBannerDetial(String params) {
        return service.getBannerDetail(params);
    }

    public Observable<ResponseBody> getNewsDetial(String params) {
        return service.getNewsDetial(params);
    }

    public Observable<ResponseBody> changePwd(String params) {
        return service.changePwd(params);
    }

    public Observable<ResponseBody> mUpdate(String url) {
        return service.downloadFile(url);
    }

    public Observable<List<ChargeOrder>> getChargeOrdreList(String params) {
        return service.getChargeOrdreList(params);
    }

    public Observable<List<TiXianStory>> getTiXianStory() {
        return service.getTiXianStory();
    }

    public Observable<ResponseBody> updateAvatar(MultipartBody.Part params) {
        return service.updateAvatar(params);
    }

    public Observable<ResponseBody> getUserInfo() {
        return service.getUserInfo();
    }

    public Observable<ResponseBody> mLogout() {
        return service.mLogout();
    }

    //度数转换成金额（按电量充）
    public Observable<ResponseBody> duToMoney(String data) {
        return service.duToMoney(data);
    }

    public Observable<ResponseBody> getPayOrderNo() {
        return service.getPayOrderNo();
    }

    public Observable<ResponseBody> mCheckChargeState() {
        return service.mCheckChargeState();
    }

    public Observable<ResponseBody> mCheckOrderIsHave(String orderNo) {
        return service.mCheckOrderIsHave(orderNo);
    }

    public Observable<ResponseBody> mCheckOrderIsCharging(String orderNo) {
        return service.mCheckOrderIsCharging(orderNo);
    }

    public Observable<ResponseBody> getOrderNo() {
        return service.getOrderNo();
    }

    public Observable<ResponseBody> mUpdateApp() {
        return service.mUpdateApp();
    }

    public Observable<ResponseBody> addChargeOrder(String data) {
        return service.addChargeOrder(data);
    }

    public Observable<ResponseBody> bindEmail(String data) {
        return service.bindEmail(data);
    }

    public Observable<ResponseBody> bindQqWx(String data) {
        return service.bindQqWx(data);
    }

    public Observable<ResponseBody> addAdvice(String data) {
        return service.addAdvice(data);
    }

    public Observable<ResponseBody> ChargeFail(String data) {
        return service.ChargeFail(data);
    }

    public Observable<ResponseBody> mCheckLoginState() {
        return service.mCheckLoginState();
    }

    public Observable<ResponseBody> getAccountBalance() {
        return service.getAccountBalance();
    }

    public Observable<ResponseBody> mCheckChargePwd(String pwd) {
        return service.mCheckChargePwd(pwd);
    }

    public Observable<ResponseBody> addCarRecord(String data) {
        return service.addCarRecord(data);
    }

    public Observable<List<PrimaryBanner>> getCarDetailUp(String id) {
        return service.getCarDetailUp(id);
    }

    public Observable<ResponseBody> getCarDetailDown(String id) {
        return service.getCarDetailDown(id);
    }

    public Observable<ResponseBody> getCarType() {
        return service.getCarType();
    }

    //提交个人信息
    public Observable<ResponseBody> isOwnPhone(String paraments) {
        return service.isOwnPhone(paraments);
    }

    //提交个人信息
    public Observable<ResponseBody> UpdateCustomer(String paraments) {
        return service.UpdateCustomer(paraments);
    }

    //拉取积分列表
    public Observable<ResponseBody> getCustScoreList() {
        return service.getCustScoreList();
    }

    //拉取明细列表
    public Observable<ResponseBody> getCustRecharge() {
        return service.getCustRecharge();
    }

    //拉取提现信息列表
    public Observable<ResponseBody> getCashInfo() {
        return service.getCashInfo();
    }

    //提交提现
    public Observable<ResponseBody> addGetCashInfo(String paraments) {
        return service.addGetCashInfo(paraments);
    }

    //设置充电密码
    public Observable<ResponseBody> upzhifuPassword(String paraments) {
        return service.upzhifuPassword(paraments);
    }

    //发送邮件验证码
    public Observable<ResponseBody> sendEmailNum(String paraments) {
        return service.sendEmailNum();
    }

    //加载正在充电口的数据
    public Observable<ResponseBody> getZhengZaiChongDianOrder() {
        return service.getZhengZaiChongDianOrder();
    }


    //加载正在充电口的总数据
    public Observable<ResponseBody> getSumzongdianliangandjine(String paraments) {
        return service.getSumzongdianliangandjine(paraments);
    }

    //结束充电
    public Observable<ResponseBody> endChongDian(String paraments) {
        return service.endChongDian(paraments);
    }

    //加载电车之家列表数据  paraments=热门品牌  =1  下面=“”
    public Observable<ResponseBody> getCarHomeListData(String paraments) {
        return service.getCarHomeListData(paraments);
    }

    //5秒发送充电订单
    public Observable<ResponseBody> ziciFaSongDingdan(String paraments) {
        return service.ziciFaSongDingdan(paraments);
    }

    //加载电车之家商品详情列表数据 传上一页接收的id      用params
    public Observable<PageBean<carhometopBean>> getCarSeries(String paraments, int page, int rows) {
        return service.getCarSeries(paraments, page, rows);
    }

    //搜索品牌数据
    public Observable<ResponseBody> searchCarBrand(String paraments) {
        return service.searchCarBrand(paraments);
    }

    //加载确定订单列表
    public Observable<ResponseBody> getProductOrder_0(String paraments) {
        return service.getProductOrder_0(paraments);
    }


    //加载订单详情参数
    public Observable<ResponseBody> getOneOrder(String paraments) {
        return service.getOneOrder(paraments);
    }


    //加载订单详情列表
    public Observable<ResponseBody> getOrderProList(String paraments) {
        return service.getOrderProList(paraments);
    }

    //加载余额信息
    public Observable<ResponseBody> loadCustomerBalance() {
        return service.loadCustomerBalance();
    }

    //加载查询地址列表
    public Observable<ResponseBody> searchCustAddress() {
        return service.searchCustAddress();
    }

    //加载设置默认地址
    public Observable<ResponseBody> updCustAddressDefault(String data) {
        return service.updCustAddressDefault(data);
    }

    //加载删除地址
    public Observable<ResponseBody> delCustAddress(String data) {
        return service.delCustAddress(data);
    }


    //省份查询
    public Observable<ResponseBody> loadProvince() {
        return service.loadProvince();
    }


    //城市查询
    public Observable<ResponseBody> loadCity(String paraments) {
        return service.loadCity(paraments);
    }

    //县区查询
    public Observable<ResponseBody> loadCountry(String paraments) {
        return service.loadCountry(paraments);
    }

    //添加地址
    public Observable<ResponseBody> addCustAddress(String data) {
        return service.addCustAddress(data);
    }

    //更新地址
    public Observable<ResponseBody> updateCustAddress(String data) {
        return service.updateCustAddress(data);
    }

    //查询我的预约列表
    public Observable<ResponseBody> getMyApply() {
        return service.getMyApply();
    }


    //加载Banner详情列表
    public Observable<ResponseBody> getProductDeatilBanner(String paraments) {
        return service.getProductDeatilBanner(paraments);
    }


    //提交预约
    public Observable<ResponseBody> addApply(String data) {
        return service.addApply(data);
    }


    //加载活动列表      用params
    public Observable<PageBean<activebean>> getActiveList(int page, int rows,String params) {
        return service.getActiveList(page, rows,params);
    }

    //获取活动详情
    public Observable<ResponseBody> getActivityDetail(String params) {
        return service.getActivityDetail(params);
    }

    //获取商品主页列表
    public Observable<ResponseBody> getMallProduct(String type) {
        return service.getMallProduct(type);
    }

    // 获取购物车banna
    public Observable<List<PrimaryBanner>> getCarPartBanner() {
        return service.getCarPartBanner();
    }


    // 获取购物车商品类型
    public Observable<ArrayList<ShopListType>> getAllProductType() {
        return service.getAllProductType();
    }

    //获取商品详情底部信息
    public Observable<ResponseBody> getProductDeatilParam(String id) {
        return service.getProductDeatilParam(id);
    }

    //加载订单列表数据 传上一页接收的id      用params
    public Observable<PageBean<myorderbean>> getOrderList(String paraments, int page, int rows) {
        return service.getOrderList(page, rows, paraments);
    }

    // 获取商城主页banna
    public Observable<List<PrimaryBanner>> getIndexImageWx() {
        return service.getIndexImageWx();
    }

    //提交订单
    public Observable<ResponseBody> insertProOrder_0(String paraments) {
        return service.insertProOrder_0(paraments);
    }

    //添加购物车
    public Observable<ResponseBody> addShoppingCart(String paraments) {
        return service.addShoppingCart(paraments);
    }

    //获取购物车主页列表
    public Observable<ResponseBody> searchShoppingCart() {
        return service.searchShoppingCart();
    }

    //删除购物车商品
    public Observable<ResponseBody> deleteShoppingCart(String paraments) {
        return service.deleteShoppingCart(paraments);
    }

    //添加订单
    public Observable<ResponseBody> addMyOrder(String paraments) {
        return service.addMyOrder(paraments);
    }

    //加载选择地址
    public Observable<ResponseBody> searchDefaultAddress() {
        return service.searchDefaultAddress();
    }

    //确认收货
    public Observable<ResponseBody> updateOrder_4(String paraments) {
        return service.updateOrder_4(paraments);
    }

    //支付时更新地址
    public Observable<ResponseBody> updateorderaddress(String paraments) {
        return service.updateorderaddress(paraments);
    }

    //请求电桩列表
    public Observable<ResponseBody> getMyApply1() {
        return service.getMyApply1();
    }


    //删除申请电桩
    public Observable<ResponseBody> deleteMyele(String data) {
        return service.deleteMyele(data);
    }


    //添加电桩
    public Observable<ResponseBody> addMyEle(String data) {
        return service.addMyEle(data);
    }


    //积分余额
    public Observable<ResponseBody> getCustScores() {
        return service.getCustScores();
    }

    //余额支付
    public Observable<ResponseBody> orderYuEZhiFu(String paraments) {
        return service.orderYuEZhiFu(paraments);
    }

    //物流余额支付
    public Observable<ResponseBody> shipperBalancePay(String paraments) {
        return service.shipperBalancePay(paraments);
    }

    //积分支付
    public Observable<ResponseBody> orderScoresZhiFu(String paraments) {
        return service.orderScoresZhiFu(paraments);
    }

    //优惠券数据拉取
    public Observable<ResponseBody> getCoupon() {
        return service.getCoupon();
    }


    //我的车辆列表
    public Observable<ResponseBody> getMyCar() {
        return service.getMyCar();
    }


    //添加车辆
    public Observable<ResponseBody> addMyCar(String data) {
        return service.addMyCar(data);
    }

    //优惠券数量判断
    public Observable<ResponseBody> getCustTicketsMax(String paraments) {
        return service.getCustTicketsMax(paraments);
    }

    //优惠券领取
    public Observable<ResponseBody> addMyCoupon(String paraments) {
        return service.addMyCoupon(paraments);
    }

    //我的优惠券数据拉取
    public Observable<ResponseBody> getCustTickets() {
        return service.getCustTickets();
    }

    //判断车辆是否预约过
    public Observable<ResponseBody> isApply(String paraments) {
        return service.isApply(paraments);
    }


    //取消订单
    public Observable<ResponseBody> canclePro(String paraments) {
        return service.canclePro(paraments);
    }

    //删除我的车辆
    public Observable<ResponseBody> delMyCar(String data) {
        return service.delMyCar(data);
    }

    //车牌号是否存在
    public Observable<ResponseBody> searchmyCarToId(String data) {
        return service.searchmyCarToId(data);
    }

    //车辆租赁拉取时间框内容
    public Observable<ResponseBody> searchtime(String data) {
        return service.searchtime(data);
    }

    //车辆租赁拉取车型内容
    public Observable<ResponseBody> searchleasecarName(String data) {
        return service.searchleasecarName(data);
    }

    //提交租赁订单
    public Observable<ResponseBody> addLeaseOrder(String data) {
        return service.addLeaseOrder(data);
    }


    //车辆信息列表
    public Observable<ResponseBody> searchleasecar(String paraments) {
        return service.searchleasecar(paraments);
    }


    //加载租赁订单列表
    public Observable<ResponseBody> searchLeaseOrder(String paraments) {
        return service.searchLeaseOrder(paraments);
    }


    //加载租赁订单详情
    public Observable<ResponseBody> searchLeaseOrderDetail(String paraments) {
        return service.searchLeaseOrderDetail(paraments);
    }


    //加载车辆详细参数
    public Observable<ResponseBody> selectCarDetail(String paraments) {
        return service.selectCarDetail(paraments);
    }

    //取消租赁订单
    public Observable<ResponseBody> cancleLeaseOrder(String data) {
        return service.cancleLeaseOrder(data);
    }

    //物流发货加载车型及参数
    public Observable<ResponseBody> searchCarType(String data) {
        return service.searchCarType(data);
    }

    //额外服务查询 接口
    public Observable<ResponseBody> searchCarServices(String data) {
        return service.searchCarServices(data);
    }

    //提交物流发货订单
    public Observable<ResponseBody> addSendCarOrder(String paraments) {
        return service.addSendCarOrder(paraments);
    }

    //拉取物流协议
    public Observable<ResponseBody> searchCarDeal() {
        return service.searchCarDeal();
    }

    //物流发货单列表
    public Observable<ResponseBody> searchsendorder(String data) {
        return service.searchsendorder(data);
    }

    //司机接货单列表
    public Observable<ResponseBody> searchfindorder(String data) {
        return service.searchfindorder(data);
    }

    //加载物流订单详情
    public Observable<ResponseBody> searchorder(String data) {
        return service.searchorder(data);
    }

    //加载订单详情额外需求
    public Observable<ResponseBody> searchorderdetail(String data) {
        return service.searchorderdetail(data);
    }


    //取消物流订单
    public Observable<ResponseBody> cancelOrder(String data) {
        return service.cancelOrder(data);
    }


    //结束物流订单
    public Observable<ResponseBody> finishOrderfinishOrder(String data) {
        return service.finishOrderfinishOrder(data);
    }

    //货主评价接口
    public Observable<ResponseBody> evlateOrder(String data) {
        return service.evlateOrder(data);
    }


    //开始物流订单
    public Observable<ResponseBody> startOrder(String data) {
        return service.startOrder(data);
    }

    //上传司机认证基本信息
    public Observable<ResponseBody> insertDriverCertificationInfo(String data) {
        return service.insertDriverCertificationInfo(data);
    }

    public Observable<ResponseBody> insertDCUploadImg(MultipartBody.Part params) {
        return service.updateAvatar(params);
    }


    //检测司机是否认证
    public Observable<ResponseBody> checkDriverSPStatus() {
        return service.checkDriverSPStatus();
    }

    //请求司机综合评分
    public Observable<ResponseBody> selectDriverEvaluationTotal() {
        return service.selectDriverEvaluationTotal();
    }


    //加载司机抢单列表
    public Observable<ResponseBody> selectLogisticsOrderList(int page, int rows, String paraments) {
        return service.selectLogisticsOrderList(page, rows, paraments);
    }

    //加载抢单详情
    public Observable<ResponseBody> selectLogisticsOrderDetail(String paraments) {
        return service.selectLogisticsOrderDetail(paraments);
    }

    //加载抢单详情额外需求
    public Observable<ResponseBody> selectLogisticsOrderDetailServ(String paraments) {
        return service.selectLogisticsOrderDetailServ(paraments);
    }

    //抢单
    public Observable<ResponseBody> updateLogisticsOrderGrabASingle(String paraments) {
        return service.updateLogisticsOrderGrabASingle(paraments);
    }

    //加载司机评价列表
    public Observable<ResponseBody> selectDriverEvaluation() {
        return service.selectDriverEvaluation();
    }

    //检查司机是否收到钱
    public Observable<ResponseBody> checkBeViewed() {
        return service.checkBeViewed();
    }

    //删除物流订单
    public Observable<ResponseBody> deleteShipperSendOrder(String paraments) {
        return service.deleteShipperSendOrder(paraments);
    }

    //请求费率
    public Observable<ResponseBody> searchfee() {
        return service.searchfee();
    }

    //同城小件单 订单列表 查询接口
    public Observable<ResponseBody> searchSuyunorder() {
        return service.searchSuyunorder();
    }


    //同城搬家单 订单列表 查询接口
    public Observable<ResponseBody> searchBanjiaorder() {
        return service.searchBanjiaorder();
    }


    //快运单 订单列表 查询接口
    public Observable<ResponseBody> searchKuaiyunOrder() {
        return service.searchKuaiyunOrder();
    }


    //速运单 同城小件单 订单详情 查询接口
    public Observable<ResponseBody> searchSuyunOrderDetail(String data) {
        return service.searchSuyunOrderDetail(data);
    }


    //速运单 同城小件单 订单详情额外需求 查询接口
    public Observable<ResponseBody> selectSuyunOrderDetail(String params) {
        return service.selectSuyunOrderDetail(params);
    }


    //速运单 同城小件单 取消订单接口
    public Observable<ResponseBody> cancelSuyunOrder(String data) {
        return service.cancelSuyunOrder(data);
    }


    //速运单 同城小件单 开始订单接口
    public Observable<ResponseBody> startSuyunOrder(String data) {
        return service.startSuyunOrder(data);
    }


    //速运单 同城小件单 结束订单接口
    public Observable<ResponseBody> finishSuyunOrder(String data) {
        return service.finishSuyunOrder(data);
    }


    //快运单 订单详情 查询接口
    public Observable<ResponseBody> searchKuaiyunOrderDetail(String data) {
        return service.searchKuaiyunOrderDetail(data);
    }


    //快运单 取消订单接口
    public Observable<ResponseBody> cancelKuaiyunOrder(String data) {
        return service.cancelKuaiyunOrder(data);
    }

    //快运单 开始订单接口
    public Observable<ResponseBody> startKuaiyunOrder(String data) {
        return service.startKuaiyunOrder(data);
    }


    //快运单 结束订单接口
    public Observable<ResponseBody> finishKuaiyunOrder(String data) {
        return service.finishKuaiyunOrder(data);
    }


    //订单搜索 查询接口
    public Observable<ResponseBody> searchOrderByParam(String data) {
        return service.searchOrderByParam(data);
    }

    //开票列表
    public Observable<ResponseBody> searchInvoiceOrder(int data) {
        return service.searchInvoiceOrder(data);
    }


    //提交开票
    public Observable<ResponseBody> addInvoice(String data) {
        return service.addInvoice(data);
    }


    //更新开票订单状态
    public Observable<ResponseBody> updateOrderStatus(String data) {
        return service.updateOrderStatus(data);
    }

    //查询所有省接口
    public Observable<ResponseBody> selectAllArea1() {
        return service.selectAllArea1();
    }

    //更新开票订单状态
    public Observable<ResponseBody> selectAllArea2(String data) {
        return service.selectAllArea2(data);
    }

    //更新开票订单状态
    public Observable<ResponseBody> selectAllArea3(String data) {
        return service.selectAllArea3(data);
    }

    //快运 车型选择 查询接口
    public Observable<ResponseBody> searchKuaiyunCartype() {
        return service.searchKuaiyunCartype();
    }

    ////快运 货物类型 查询接口
    public Observable<ResponseBody> searchKuaiyunCargotype() {
        return service.searchKuaiyunCargotype();
    }

    //快运提交订单 接口
    public Observable<ResponseBody> addKuaiyunOrder(String data) {
        return service.addKuaiyunOrder(data);
    }

    //速运提交订单 接口
    public Observable<ResponseBody> addSuyunOrder(String data) {
        return service.addSuyunOrder(data);
    }

    //余额支付 检测支付密码
    public Observable<ResponseBody> checkZhiFuPassword() {
        return service.checkZhiFuPassword();
    }


    //开票历史
    public Observable<ResponseBody> searchInvoice() {
        return service.searchInvoice();
    }

    //货物重量区间，金额 查询接口
    public Observable<ResponseBody> searchWeightOfGoods(String params) {
        return service.searchWeightOfGoods(params);
    }

    //货主端 查询收费标准 接口
    public Observable<ResponseBody> searchFeescale() {
        return service.searchFeescale();
    }

    //删除物流订单
    public Observable<ResponseBody> deleteSuyunOrder(String paraments) {
        return service.deleteSuyunOrder(paraments);
    }

    //删除物流订单
    public Observable<ResponseBody> deleteKuaiyunOrder(String paraments) {
        return service.deleteKuaiyunOrder(paraments);
    }


    //查询定位点
    public Observable<ResponseBody> queryGps(String params) {
        return service.queryGps(params);
    }
    //加载快运订单详情司机列表
    public Observable<ResponseBody> searchKuaiyunOrderDrivers(String data) {
        return service.searchKuaiyunOrderDrivers(data);
    }


    //快运单余额支付
    public Observable<ResponseBody> shipperBalancePay_ky(String paraments) {
        return service.shipperBalancePay_ky(paraments);
    }


    //快运线下周结支付
    public Observable<ResponseBody> shipperBalancePay_kyunderline(String data) {
        return service.shipperBalancePay_kyunderline(data);
    }


    //检测是否周结
    public Observable<ResponseBody> checkRelease() {
        return service.checkRelease();
    }

    //小件单 ，搬家单线下周结支付
    public Observable<ResponseBody> shipperBalancePay_underline(String data) {
        return service.shipperBalancePay_underline(data);
    }


    //检测是否是签约用户
    public Observable<ResponseBody> checkUserSign() {
        return service.checkUserSign();
    }

    //签约用户查询未支付的周结列表
    public Observable<ResponseBody> selectSignOrders() {
        return service.selectSignOrders();
    }


    //周结余额支付
    public Observable<ResponseBody> selectSignOrdersToPay(String paraments) {
        return service.selectSignOrdersToPay(paraments);
    }

    //快运签约用户价格维护接口
    public Observable<ResponseBody> selectSignSetPrice(String params) {
        return service.selectSignSetPrice(params);
    }

    //周结拼接Uuid
    public Observable<ResponseBody> payBefGetUuid(String data) {
        return service.payBefGetUuid(data);
    }


    //快运单支付宝 微信支付 传司机id
    public Observable<ResponseBody> alWxPayBeforeSelDri(String data) {
        return service.alWxPayBeforeSelDri(data);
    }

    //查询地址、联系人相关信息列表接口
    public Observable<ResponseBody> selectUserRelevantList() {
        return service.selectUserRelevantList();
    }

    //设置常用默认地址
    public Observable<ResponseBody> setUserRelevantDef(String data) {
        return service.setUserRelevantDef(data);
    }

    //删除常用默认地址
    public Observable<ResponseBody> deleteUserRelevant(String data) {
        return service.deleteUserRelevant(data);
    }

    //查询地址、联系人相关信息默认一条接口
    public Observable<ResponseBody> selectUserRelevantDef() {
        return service.selectUserRelevantDef();
    }

    //评语接口
    public Observable<ResponseBody> selectEvaluationList(String params) {
        return service.selectEvaluationList(params);
    }

    //历史消息列表
    public Observable<ResponseBody> searchUserMsgList(String params) {
        return service.searchUserMsgList(params);
    }

    //历史单个消息
    public Observable<ResponseBody> searchUserMsgById(String params) {
        return service.searchUserMsgById(params);
    }

    //删除历史消息
    public Observable<ResponseBody> deleteUserMsg(String data) {
        return service.deleteUserMsg(data);
    }

    //常见消息列表
    public Observable<ResponseBody> selectCommProblems(String params) {
        return service.selectCommProblems(params);
    }

    //点击有用无用接口
    public Observable<ResponseBody> updateCommProblemsUse(String data) {
        return service.updateCommProblemsUse(data);
    }

    //上传货主认证信息
    public Observable<ResponseBody> insertOwnerCertification(String data) {
        return service.insertOwnerCertification(data);
    }


    //检测货主是否认证
    public Observable<ResponseBody> checkOwnerStatus() {
        return service.checkOwnerStatus();
    }

    //检测货主是否认证
    public Observable<ResponseBody> checkOwnerStatus1(String params) {
        return service.checkOwnerStatus1(params);
    }

    //商城首页列表
    public Observable<ResponseBody> selectMallIndex() {
        return service.selectMallIndex();
    }

    //商品分类查询
    public Observable<ResponseBody> selectMallProdsByParam(String params) {
        return service.selectMallProdsByParam(params);
    }

    //未读消息个数
    public Observable<ResponseBody> searchUserMsgNoRead(String params) {
        return service.searchUserMsgNoRead(params);
    }

    //找货 货滴快运 快运单
    public Observable<ResponseBody> selectKuaiyunOrderList(int page,int rows,String paraments) {
        return service.selectKuaiyunOrderList(page,rows,paraments);
    }

    //快运单抢单列表筛选
    public Observable<ResponseBody> selectKuaiyunOrderListByParam(int page,int rows,String paraments) {
        return service.selectKuaiyunOrderListByParam(page,rows,paraments);
    }

    //找货 快运 订单详情 查询接口
    public Observable<ResponseBody> selectKuaiyunOrderDetail(String paraments) {
        return service.selectKuaiyunOrderDetail(paraments);
    }

    //二手车首页信息
    public Observable<ResponseBody> selectSecondhandcarIndex(String paraments) {
        return service.selectSecondhandcarIndex(paraments);
    }

    //二手车首页列表信息
    public Observable<ResponseBody> searchSecondhandcarListByPage(int page,int rows,String paraments) {
        return service.searchSecondhandcarListByPage(page,rows,paraments);
    }

    //商品分类查询11
    public Observable<ResponseBody> selectMallProdsByParam_type(String params) {
        return service.selectMallProdsByParam_type(params);
    }

    //首页二手车详情
    public Observable<ResponseBody> searchSecondhandcarDet(String paraments) {
        return service.searchSecondhandcarDet(paraments);
    }

    //二手车品牌选择
    public Observable<ResponseBody> selectPubSechancarBrand() {
        return service.selectPubSechancarBrand();
    }

    //发布二手车上传基本信息
    public Observable<ResponseBody> addSelfSecondhandcar(String data) {
        return service.addSelfSecondhandcar(data);
    }

    //查询我的二手车列表
    public Observable<ResponseBody> searchSelfSecondhandcar() {
        return service.searchSelfSecondhandcar();
    }

    //删除我的二手车
    public Observable<ResponseBody> deleteSelfSecondhandcar(String data) {
        return service.deleteSelfSecondhandcar(data);
    }

    //积分签到信息
    public Observable<ResponseBody> selectSigninIndex() {
        return service.selectSigninIndex();
    }

    //积分签到
    public Observable<ResponseBody> addSignin() {
        return service.addSignin();
    }

    //其他月的签到信息
    public Observable<ResponseBody> selectSigninByMonth(String paraments) {
        return service.selectSigninByMonth(paraments);
    }

    //快运车长
    public Observable<ResponseBody> searchVehicleLength() {
        return service.searchVehicleLength();
    }
}
