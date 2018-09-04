package com.lx.hd.api;

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
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {


    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> postJson(@Url String url, @Field("params1") String parmas);

    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> postMap(@Url String url, @FieldMap Map<String, Object> maps);

    @POST()
    Observable<ResponseBody> postBody(@Url String url, @Body Object object);

    @GET()
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> maps);

    @GET()
    Observable<ResponseBody> get(@Url String url);

    @DELETE()
    Observable<ResponseBody> delete(@Url String url, @QueryMap Map<String, String> maps);

    @PUT()
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> maps);

    @POST()
    Observable<ResponseBody> putBody(@Url String url, @Body Object object);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFlie(@Url String fileUrl, @Part("description") RequestBody description, @Part("files") MultipartBody.Part file);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url String url, @PartMap() Map<String, RequestBody> maps);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url String url, @Part() List<MultipartBody.Part> parts);

    //  @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    @FormUrlEncoded
    @POST("mbtwz/index?action=getNews")
    Observable<PageBean<PrimaryNews>> getNewsList(@Field("page") int page, @Field("rows") String rows, @Field("params") String params);

//    @FormUrlEncoded
//    @POST("mbtwz/index?action=getActivity")
//    Observable<List<PrimaryBanner>> getBanner(@Field("params") String params);

    @FormUrlEncoded
    @POST("mbtwz/PcIndex?action=getTrends")
    Observable<List<PrimaryBanner>> getBanner(@Field("params") String params);

//    @POST()
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    Observable<ResponseBody> postJson(@Url String url, @Body RequestBody jsonBody);
//
//    @POST()
//    Observable<ResponseBody> postBody(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @POST("mbtwz/index?action=getActivityDetail")
    Observable<ResponseBody> getBannerDetail(@Field("data") String params);

    @FormUrlEncoded
    @POST("mbtwz/index?action=getNewsDetail")
    Observable<ResponseBody> getNewsDetial(@Field("data") String params);

    @FormUrlEncoded
    @POST("mbtwz/register?action=upPassword")
    Observable<ResponseBody> changePwd(@Field("data") String params);

    @FormUrlEncoded
    @POST("mbtwz/register?action=isExitsPhone")
    Observable<ResponseBody> mCheckPhone(@Field("data") String data);

    @FormUrlEncoded
    @POST("mbtwz/register?action=getSMSCode")
    Observable<ResponseBody> mGetAuthCode(@Field("params") String params);

    @FormUrlEncoded
    @POST("mbtwz/register?action=addCoustomer")
    Observable<ResponseBody> mRegister(@Field("data") String params);

    @FormUrlEncoded
    @POST("mbtwz/mallLogin?action=checkMallLogin")
    Observable<ResponseBody> mLogin(@Field("data") String params);

    @FormUrlEncoded
    @POST("mbtwz/wxorder?action=getOrderList")
    Observable<List<ChargeOrder>> getChargeOrdreList(@Field("params") String params);

    @POST("mbtwz/wallet?action=getCashSearchRecord")
    Observable<List<TiXianStory>> getTiXianStory();

    @POST("mbtwz/personal?action=getPersonalInfo")
    Observable<ResponseBody> getUserInfo();

    @POST("mbtwz/mallLogin?action=exiteMallLogin")
    Observable<ResponseBody> mLogout();

    @POST("weixinzhifu?action=getChongzhiDanHao")
    Observable<ResponseBody> getPayOrderNo();

    @POST("mbtwz/mallLogin?action=isLogin")
    Observable<ResponseBody> mCheckLoginState();

    @POST("mbtwz/cdxt?action=loadCustomerBalance")
    Observable<ResponseBody> getAccountBalance();

    @FormUrlEncoded
    @POST("mbtwz/wxorder?action=dushutojine")
    Observable<ResponseBody> duToMoney(@Field("data") String data);

    @FormUrlEncoded
    @POST("mbtwz/wxorder?action=validateChongdianmima")
    Observable<ResponseBody> mCheckChargePwd(@Field("params") String pwd);

    @FormUrlEncoded
    @POST("mbtwz/CarHome?action=addCarRecord")
    Observable<ResponseBody> addCarRecord(@Field("data") String data);

    @FormUrlEncoded
    @POST("mbtwz/CarHome?action=getCarDetailUp")
    Observable<List<PrimaryBanner>> getCarDetailUp(@Field("params") String id);

    @FormUrlEncoded
    @POST("mbtwz/CarHome?action=getCarDetailDown")
    Observable<ResponseBody> getCarDetailDown(@Field("params") String id);

    @POST("mbtwz/CarHome?action=searchModels")
    Observable<ResponseBody> getCarType();

    @POST("mbtwz/wxorder?action=zhengzaichongdian")
    Observable<ResponseBody> mCheckChargeState();

    @POST("mbtwz/cdxt?action=getZuiJinOrderSBM")
    Observable<ResponseBody> getOrderNo();

    @POST("app/version?action=getVersionInfo&project=货滴货主")
    Observable<ResponseBody> mUpdateApp();

    @FormUrlEncoded
    @POST("mbtwz/cdxt?action=checkChongDianQianHao")
    Observable<ResponseBody> mCheckOrderIsHave(@Field("params") String orderNo);

    @FormUrlEncoded
    @POST("mbtwz/wxorder?action=addChongDianOrder")
    Observable<ResponseBody> addChargeOrder(@Field("data") String data);

    @FormUrlEncoded
    @POST("mbtwz/wxcustomer?action=addEmailNum")
    Observable<ResponseBody> bindEmail(@Field("data") String data);

    @FormUrlEncoded
    @POST("mbtwz/wxcustomer?action=addQqWechat")
    Observable<ResponseBody> bindQqWx(@Field("data") String data);

    @FormUrlEncoded
    @POST("mbtwz/wxcustomer?action=addAdvice ")
    Observable<ResponseBody> addAdvice(@Field("data") String data);


    @FormUrlEncoded
    @POST("mbtwz/wxorder?action=getManagePhone")
    Observable<ResponseBody> ChargeFail(@Field("params") String data);


    @FormUrlEncoded
    @POST("mbtwz/wxorder?action=checkZhengZaiChongDian")
    Observable<ResponseBody> mCheckOrderIsCharging(@Field("params") String orderNo);

    //判断是否是当前登录的手机号
    @FormUrlEncoded
    @POST("mbtwz/register?action=isOwnPhone")
    Observable<ResponseBody> isOwnPhone(@Field("data") String params);

    //提交个人信息
    @FormUrlEncoded
    @POST("mbtwz/wxcustomer?action=updatecustomer")
    Observable<ResponseBody> UpdateCustomer(@Field("data") String params);

    //拉取积分列表
    @POST("mbtwz/wallet?action=getCustScoreList")
    Observable<ResponseBody> getCustScoreList();

    //拉取明细列表
    @POST("mbtwz/wallet?action=getCustRecharge")
    Observable<ResponseBody> getCustRecharge();

    //拉取提现列表
    @POST("mbtwz/wallet?action=getCashInfo")
    Observable<ResponseBody> getCashInfo();

    //提交提现
    @FormUrlEncoded
    @POST("mbtwz/wallet?action=addGetCashInfo")
    Observable<ResponseBody> addGetCashInfo(@Field("data") String params);

    //设置支付密码
    @FormUrlEncoded
    @POST("mbtwz/register?action=upzhifuPassword")
    Observable<ResponseBody> upzhifuPassword(@Field("data") String params);

    //发送邮件验证码
    @POST("mbtwz/register?action=getSMSCodeEmail")
    Observable<ResponseBody> sendEmailNum();

    //加载正在充电口的数据
    @POST("mbtwz/wxorder?action=getZhengZaiChongDianOrder")
    Observable<ResponseBody> getZhengZaiChongDianOrder();

    //加载正在充电口的总数据
    @FormUrlEncoded
    @POST("mbtwz/wxorder?action=getSumzongdianliangandjine")
    Observable<ResponseBody> getSumzongdianliangandjine(@Field("data") String params);

    //结束充电
    @FormUrlEncoded
    @POST("mbtwz/wxorder?action=endChongDian")
    Observable<ResponseBody> endChongDian(@Field("data") String params);

    @Multipart
    @POST("mbtwz/wxcustomer?action=uploadCustomerImage")
    Observable<ResponseBody> updateAvatar(@Part MultipartBody.Part userLogo);

    //加载电车之家列表
    @FormUrlEncoded
    @POST("mbtwz/CarHome?action=getCarBrand")
    Observable<ResponseBody> getCarHomeListData(@Field("params") String params);

    //加载电车之家列表
    @FormUrlEncoded
    @POST("mbtwz/wxorder?action=ziciFaSongDingdan")
    Observable<ResponseBody> ziciFaSongDingdan(@Field("params") String params);

    //加载电车之家商品详情列表数据 传上一页接收的id      用params
    @FormUrlEncoded
    @POST("mbtwz/CarHome?action=getCarSeries")
    Observable<PageBean<carhometopBean>> getCarSeries(@Field("params") String params, @Field("page") int page, @Field("rows") int rows);

    //搜索品牌列表
    @FormUrlEncoded
    @POST("mbtwz/CarHome?action=searchCarBrand")
    Observable<ResponseBody> searchCarBrand(@Field("params") String params);


    //加载确定订单列表
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=getProductOrder_0")
    Observable<ResponseBody> getProductOrder_0(@Field("params") String params);


    //加载订单详情参数
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=getOneOrder")
    Observable<ResponseBody> getOneOrder(@Field("params") String params);


    //加载订单详情列表
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=getOrderProList")
    Observable<ResponseBody> getOrderProList(@Field("params") String params);


    //加载余额信息
    @POST("mbtwz/cdxt?action=loadCustomerBalance")
    Observable<ResponseBody> loadCustomerBalance();

    //加载查询地址列表
    @POST("mbtwz/address?action=searchCustAddress")
    Observable<ResponseBody> searchCustAddress();

    //加载设置默认地址
    @FormUrlEncoded
    @POST("mbtwz/address?action=updCustAddressDefault")
    Observable<ResponseBody> updCustAddressDefault(@Field("data") String data);

    //加载删除地址
    @FormUrlEncoded
    @POST("mbtwz/address?action=delCustAddress")
    Observable<ResponseBody> delCustAddress(@Field("data") String data);


    //查询省份
    @POST("mbtwz/address?action=loadProvince")
    Observable<ResponseBody> loadProvince();

    //查询城市
    @FormUrlEncoded
    @POST("mbtwz/address?action=loadCity")
    Observable<ResponseBody> loadCity(@Field("params") String params);


    //查询县区
    @FormUrlEncoded
    @POST("mbtwz/address?action=loadCountry")
    Observable<ResponseBody> loadCountry(@Field("params") String params);


    //添加地址
    @FormUrlEncoded
    @POST("mbtwz/address?action=addCustAddress")
    Observable<ResponseBody> addCustAddress(@Field("data") String data);


    //更新地址
    @FormUrlEncoded
    @POST("mbtwz/address?action=updateCustAddress")
    Observable<ResponseBody> updateCustAddress(@Field("data") String data);


    //查询我的预约列表
    @POST("mbtwz/scshop?action=getMyApply")
    Observable<ResponseBody> getMyApply();


    //提交预约
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=addApply")
    Observable<ResponseBody> addApply(@Field("data") String data);


    //加载活动列表      用params
    @FormUrlEncoded
    @POST("mbtwz/find?action=getActiveList")
    Observable<PageBean<activebean>> getActiveList(@Field("page") int page, @Field("rows") int rows,@Field("params") String params);

    //加载活动详情
    @FormUrlEncoded
    @POST("mbtwz/find?action=getActivityDetail")
    Observable<ResponseBody> getActivityDetail(@Field("data") String params);

    // 获取购物车主页列表
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=getAllProduct")
    Observable<ResponseBody> getMallProduct(@Field("params") String type);

    // 获取购物车banna
    @GET("mbtwz/scshop?action=getMallCarBanner")
    Observable<List<PrimaryBanner>> getCarPartBanner();

    //加载详情banna图
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=getProductDeatilBanner")
    Observable<ResponseBody> getProductDeatilBanner(@Field("params") String params);


    //加载购物车商品分类
    @POST("mbtwz/scshop?action=getAllProductType")
    Observable<ArrayList<ShopListType>> getAllProductType();

    // 商品详情底部信息传id
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=getProductDeatilParam")
    Observable<ResponseBody> getProductDeatilParam(@Field("params") String id);

    //加载订单列表数据 传上一页接收的id      用params
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=getOrderList")
    Observable<PageBean<myorderbean>> getOrderList(@Field("page") int page, @Field("rows") int rows, @Field("params") String paraments);


    // 获取购物车banna
    @GET("mbtwz/scshop?action=getIndexImageWx")
    Observable<List<PrimaryBanner>> getIndexImageWx();

    //提交订单
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=insertProOrder_0")
    Observable<ResponseBody> insertProOrder_0(@Field("data") String params);

    //提交订单
    @FormUrlEncoded
    @POST("mbtwz/shoppingcart?action=addShoppingCart")
    Observable<ResponseBody> addShoppingCart(@Field("data") String params);

    //获取购物车列表
    @POST("mbtwz/shoppingcart?action=searchShoppingCart")
    Observable<ResponseBody> searchShoppingCart();

    //删除订单
    @FormUrlEncoded
    @POST("mbtwz/shoppingcart?action=deleteShoppingCart")
    Observable<ResponseBody> deleteShoppingCart(@Field("data") String params);


    //加载选择地址
    @POST("mbtwz/address?action=searchDefaultAddress")
    Observable<ResponseBody> searchDefaultAddress();


    //确认收货
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=updateOrder_4")
    Observable<ResponseBody> updateOrder_4(@Field("params") String params);

    //提交订单
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=addMyOrder")
    Observable<ResponseBody> addMyOrder(@Field("data") String params);


    //请求电桩列表
    @POST("mbtwz/elecar?action=getMyApply")
    Observable<ResponseBody> getMyApply1();


    //删除申请电桩
    @FormUrlEncoded
    @POST("mbtwz/elecar?action=deleteMyele")
    Observable<ResponseBody> deleteMyele(@Field("data") String data);


    //添加电桩
    @FormUrlEncoded
    @POST("mbtwz/elecar?action=addMyEle")
    Observable<ResponseBody> addMyEle(@Field("data") String data);

    //提交订单
    @FormUrlEncoded
    @POST("mbtwz/address?action=updateorderaddress")
    Observable<ResponseBody> updateorderaddress(@Field("data") String params);


    //积分余额
    @POST("mbtwz/scshop?action=getCustScores")
    Observable<ResponseBody> getCustScores();

    //余额支付
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=orderYuEZhiFu")
    Observable<ResponseBody> orderYuEZhiFu(@Field("data") String data);

    //物流——余额支付
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=shipperBalancePay")
    Observable<ResponseBody> shipperBalancePay(@Field("data") String data);

    //余额支付
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=orderScoresZhiFu")
    Observable<ResponseBody> orderScoresZhiFu(@Field("data") String data);


    //我的车辆列表
    @POST("mbtwz/elecar?action=getMyCar")
    Observable<ResponseBody> getMyCar();


    //添加车辆
    @FormUrlEncoded
    @POST("mbtwz/elecar?action=addMyCar")
    Observable<ResponseBody> addMyCar(@Field("data") String data);

    //优惠券拉取
    @POST("mbtwz/WxCoupon?action=getCoupon")
    Observable<ResponseBody> getCoupon();

    //优惠券数量判断
    @FormUrlEncoded
    @POST("mbtwz/WxCoupon?action=getCustTicketsMax")
    Observable<ResponseBody> getCustTicketsMax(@Field("params") String params);

    //优惠券领取
    @FormUrlEncoded
    @POST("mbtwz/WxCoupon?action=addMyCoupon")
    Observable<ResponseBody> addMyCoupon(@Field("data") String params);

    //我的优惠券拉取
    @POST("mbtwz/WxCoupon?action=getCustTickets")
    Observable<ResponseBody> getCustTickets();


    //判断车辆是否预约过
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=isApply")
    Observable<ResponseBody> isApply(@Field("params") String params);


    //取消订单
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=canclePro")
    Observable<ResponseBody> canclePro(@Field("params") String params);

    //车牌号是否存在
    @FormUrlEncoded
    @POST("mbtwz/elecar?action=searchmyCarToId")
    Observable<ResponseBody> searchmyCarToId(@Field("data") String params);

    //删除我的车辆
    @FormUrlEncoded
    @POST("mbtwz/elecar?action=delMyCar")
    Observable<ResponseBody> delMyCar(@Field("data") String data);

    //车辆租赁拉取时间框内容
    @FormUrlEncoded
    @POST("mbtwz/leasecar?action=searchtime")
    Observable<ResponseBody> searchtime(@Field("params") String params);

    //车辆租赁拉取车型内容
    @FormUrlEncoded
    @POST("mbtwz/leasecar?action=searchleasecarName")
    Observable<ResponseBody> searchleasecarName(@Field("params") String params);

    //提交租赁订单
    @FormUrlEncoded
    @POST("mbtwz/leaseorderwz?action=addLeaseOrder")
    Observable<ResponseBody> addLeaseOrder(@Field("data") String params);


    //车辆信息列表
    @FormUrlEncoded
    @POST("mbtwz/leasecar?action=searchleasecar")
    Observable<ResponseBody> searchleasecar(@Field("params") String params);

    //加载租赁订单列表
    @FormUrlEncoded
    @POST("mbtwz/leaseorderwz?action=searchLeaseOrder")
    Observable<ResponseBody> searchLeaseOrder(@Field("params") String params);


    //加载租赁订单详情
    @FormUrlEncoded
    @POST("mbtwz/leaseorderwz?action=searchLeaseOrderDetail")
    Observable<ResponseBody> searchLeaseOrderDetail(@Field("params") String params);


    //加载车辆详细参数
    @FormUrlEncoded
    @POST("mbtwz/leasecar?action=selectCarDetail")
    Observable<ResponseBody> selectCarDetail(@Field("params") String params);


    //取消租赁订单
    @FormUrlEncoded
    @POST("mbtwz/leaseorderwz?action=cancleLeaseOrder")
    Observable<ResponseBody> cancleLeaseOrder(@Field("data") String data);

    @FormUrlEncoded
    //物流发货加载车型及参数
    @POST("mbtwz/logisticssendwz?action=searchCarType")
    Observable<ResponseBody> searchCarType(@Field("data") String data);

    //额外需求加载
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=searchCarServices")
    Observable<ResponseBody> searchCarServices(@Field("params") String data);

    @FormUrlEncoded
    //物流发货提交订单
    @POST("mbtwz/logisticssendwz?action=addSendCarOrder")
    Observable<ResponseBody> addSendCarOrder(@Field("data") String data);

    //拉取物流协议
    @POST("mbtwz/logisticssendwz?action=searchCarDeal")
    Observable<ResponseBody> searchCarDeal();

    //物流发货单列表
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=searchsendorder")
    Observable<ResponseBody> searchsendorder(@Field("data") String data);


    //司机接货单列表
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=searchfindorder")
    Observable<ResponseBody> searchfindorder(@Field("data") String data);


    //加载物流订单详情
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=searchorder")
    Observable<ResponseBody> searchorder(@Field("data") String data);


    //加载订单详情额外需求
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=searchorderdetail")
    Observable<ResponseBody> searchorderdetail(@Field("data") String data);


    //取消物流订单
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=cancelOrder")
    Observable<ResponseBody> cancelOrder(@Field("data") String data);


    //结束物流订单
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=finishOrder")
    Observable<ResponseBody> finishOrderfinishOrder(@Field("data") String data);


    //货主评价接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=evlateOrder_by_sel")
    Observable<ResponseBody> evlateOrder(@Field("data") String data);


    //开始物流订单
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=startOrder")
    Observable<ResponseBody> startOrder(@Field("data") String data);

    //上传司机认证基本信息
    @FormUrlEncoded
    @POST("mbtwz/drivercertification?action=insertDriverCertificationInfo")
    Observable<ResponseBody> insertDriverCertificationInfo(@Field("data") String data);

    @Multipart
    @POST("mbtwz/drivercertification?action=insertDCUploadImg")
    Observable<ResponseBody> insertDCUploadImg(@Part MultipartBody.Part userLogo);


    //检测司机是否认证
    @POST("mbtwz/drivercertification?action=checkDriverSPStatus")
    Observable<ResponseBody> checkDriverSPStatus();

    //请求司机综合评分
    @POST("mbtwz/find?action=selectDriverEvaluationTotal")
    Observable<ResponseBody> selectDriverEvaluationTotal();


    //加载司机抢单列表
    @FormUrlEncoded
    @POST("mbtwz/find?action=selectLogisticsOrderList")
    Observable<ResponseBody> selectLogisticsOrderList(@Field("page") int page, @Field("rows") int rows, @Field("params") String params);


    //加载抢单详情
    @FormUrlEncoded
    @POST("mbtwz/find?action=selectLogisticsOrderDetail")
    Observable<ResponseBody> selectLogisticsOrderDetail(@Field("params") String params);


    //加载抢单详情额外需求
    @FormUrlEncoded
    @POST("mbtwz/find?action=selectLogisticsOrderDetailServ")
    Observable<ResponseBody> selectLogisticsOrderDetailServ(@Field("params") String params);

    //抢单
    @FormUrlEncoded
    @POST("mbtwz/find?action=updateLogisticsOrderGrabASingle")
    Observable<ResponseBody> updateLogisticsOrderGrabASingle(@Field("params") String params);


    //加载司机评价列表
    @POST("mbtwz/find?action=selectDriverEvaluation")
    Observable<ResponseBody> selectDriverEvaluation();


    //检查司机是否收到钱
    @POST("mbtwz/wallet?action=checkBeViewed")
    Observable<ResponseBody> checkBeViewed();


    @FormUrlEncoded
    //删除物流发货订单
    @POST("mbtwz/logisticssendwz?action=deleteShipperSendOrder")
    Observable<ResponseBody> deleteShipperSendOrder(@Field("data") String data);

    //请求费率
    @POST("mbtwz/logisticssendwz?action=searchfee")
    Observable<ResponseBody> searchfee();


    //同城小件单 订单列表 查询接口
    @POST("mbtwz/logisticssendwz?action=searchSuyunorder")
    Observable<ResponseBody> searchSuyunorder();


    //同城搬家单 订单列表 查询接口
    @POST("mbtwz/logisticssendwz?action=searchBanjiaorder")
    Observable<ResponseBody> searchBanjiaorder();


    //快运单 订单列表 查询接口
    @POST("mbtwz/logisticssendwz?action=searchKuaiyunOrder")
    Observable<ResponseBody> searchKuaiyunOrder();


    //速运单 同城小件单 订单详情 查询接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=searchSuyunOrderDetail")
    Observable<ResponseBody> searchSuyunOrderDetail(@Field("data") String data);


    //速运单 同城小件单 订单详情额外需求 查询接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=selectSuyunOrderDetail")
    Observable<ResponseBody> selectSuyunOrderDetail(@Field("params") String params);


    //速运单 同城小件单 取消订单接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=cancelSuyunOrder")
    Observable<ResponseBody> cancelSuyunOrder(@Field("data") String data);


    //速运单 同城小件单 开始订单接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=startSuyunOrder")
    Observable<ResponseBody> startSuyunOrder(@Field("data") String data);


    //速运单 同城小件单 结束订单接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=finishSuyunOrder")
    Observable<ResponseBody> finishSuyunOrder(@Field("data") String data);


    //快运单 订单详情 查询接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=searchKuaiyunOrderDetail")
    Observable<ResponseBody> searchKuaiyunOrderDetail(@Field("data") String data);


    //快运单 取消订单接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=cancelKuaiyunOrder")
    Observable<ResponseBody> cancelKuaiyunOrder(@Field("data") String data);

    //快运单 开始订单接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=startKuaiyunOrder")
    Observable<ResponseBody> startKuaiyunOrder(@Field("data") String data);


    //快运单 结束订单接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=finishKuaiyunOrder")
    Observable<ResponseBody> finishKuaiyunOrder(@Field("data") String data);


    //订单搜索 查询接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=searchOrderByParam")
    Observable<ResponseBody> searchOrderByParam(@Field("data") String data);

    //开票列表
    @FormUrlEncoded
    @POST("invoice?action=searchInvoiceOrder")
    Observable<ResponseBody> searchInvoiceOrder(@Field("ordertype") int ordertype);

    //提交开票
    @FormUrlEncoded
    @POST("invoice?action=addInvoice")
    Observable<ResponseBody> addInvoice(@Field("data") String data);


    //更新开票订单状态
    @FormUrlEncoded
    @POST("invoice?action=updateOrderStatus")
    Observable<ResponseBody> updateOrderStatus(@Field("data") String data);

    //查询所有省接口
    @POST("mbtwz/logisticssendwz?action=selectAllArea1")
    Observable<ResponseBody> selectAllArea1();

    //查询选择省下面所有市接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=selectAllArea2")
    Observable<ResponseBody> selectAllArea2(@Field("params") String data);

    //查询选择市下面所有县区接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=selectAllArea3")
    Observable<ResponseBody> selectAllArea3(@Field("params") String data);

    //快运 车型选择 查询接口
    @POST("mbtwz/logisticssendwz?action=searchKuaiyunCartype")
    Observable<ResponseBody> searchKuaiyunCartype();

    //快运 货物类型 查询接口
    @POST("mbtwz/logisticssendwz?action=searchKuaiyunCargotype")
    Observable<ResponseBody> searchKuaiyunCargotype();

    //快运提交接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=addKuaiyunOrder_by_rele")
    Observable<ResponseBody> addKuaiyunOrder(@Field("data") String data);

    //速运提交接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=addSuyunOrder")
    Observable<ResponseBody> addSuyunOrder(@Field("data") String data);


    //余额支付 检测支付密码
    @POST("mbtwz/logisticssendwz?action=checkZhiFuPassword")
    Observable<ResponseBody> checkZhiFuPassword();


    //开票历史
    @POST("invoice?action=searchInvoice&flag=0")
    Observable<ResponseBody> searchInvoice();

    //货物重量区间，金额 查询接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=searchWeightOfGoods")
    Observable<ResponseBody> searchWeightOfGoods(@Field("params") String params);

   // 货主端 查询收费标准 接口
    @POST("mbtwz/logisticssendwz?action=searchFeescale")
    Observable<ResponseBody> searchFeescale();

    @FormUrlEncoded
    //删除物流发货订单
    @POST("mbtwz/logisticssendwz?action=deleteSuyunOrder")
    Observable<ResponseBody> deleteSuyunOrder(@Field("data") String data);

    @FormUrlEncoded
    //删除物流发货订单
    @POST("mbtwz/logisticssendwz?action=deleteKuaiyunOrder")
    Observable<ResponseBody> deleteKuaiyunOrder(@Field("data") String data);

    //查询定位点
    @FormUrlEncoded
    @POST("huodigps?action=queryGps")
    Observable<ResponseBody> queryGps(@Field("params") String params);


    //加载快运订单详情司机列表
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=searchKuaiyunOrderDrivers")
    Observable<ResponseBody> searchKuaiyunOrderDrivers(@Field("data") String data);


    //快运单余额支付
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=shipperBalancePay_ky")
    Observable<ResponseBody> shipperBalancePay_ky(@Field("data") String data);


    //快运线下周结支付
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=shipperBalancePay_kyunderline")
    Observable<ResponseBody> shipperBalancePay_kyunderline(@Field("data") String data);


    //检测是否周结
    @POST("mbtwz/logisticssendwz?action=checkRelease")
    Observable<ResponseBody> checkRelease();


    //小件单 ，搬家单线下周结支付
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=shipperBalancePay_underline")
    Observable<ResponseBody> shipperBalancePay_underline(@Field("data") String data);

    //检测是否是签约用户
    @POST("mbtwz/logisticssendwz?action=checkUserSign")
    Observable<ResponseBody> checkUserSign();


    //签约用户查询未支付的周结列表
    @POST("mbtwz/logisticssendwz?action=selectSignOrders")
    Observable<ResponseBody> selectSignOrders();


    //周结余额支付
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=selectSignOrdersToPay")
    Observable<ResponseBody> selectSignOrdersToPay(@Field("data") String data);


    //快运签约用户价格维护接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=selectSignSetPrice")
    Observable<ResponseBody> selectSignSetPrice(@Field("params") String params);


    //周结拼接Uuid
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=payBefGetUuid")
    Observable<ResponseBody> payBefGetUuid(@Field("data") String data);


    //快运单支付宝 微信支付 传司机id
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=alWxPayBeforeSelDri")
    Observable<ResponseBody> alWxPayBeforeSelDri(@Field("data") String data);


    //查询地址、联系人相关信息列表接口
    @POST("mbtwz/logisticssendwz?action=selectUserRelevantList")
    Observable<ResponseBody> selectUserRelevantList();

    //设置常用默认地址
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=setUserRelevantDef")
    Observable<ResponseBody> setUserRelevantDef(@Field("data") String data);


    //删除常用默认地址
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=deleteUserRelevant")
    Observable<ResponseBody> deleteUserRelevant(@Field("data") String data);

    //查询地址、联系人相关信息默认一条接口
    @POST("mbtwz/logisticssendwz?action=selectUserRelevantDef")
    Observable<ResponseBody> selectUserRelevantDef();


    //评语接口
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=selectEvaluationList")
    Observable<ResponseBody> selectEvaluationList(@Field("params") String params);

    //历史消息列表
    @FormUrlEncoded
    @POST("mbtwz/sendusermsg?action=searchUserMsgList")
    Observable<ResponseBody> searchUserMsgList(@Field("params") String params);

    //历史单个消息
    @FormUrlEncoded
    @POST("mbtwz/sendusermsg?action=searchUserMsgById")
    Observable<ResponseBody> searchUserMsgById(@Field("params") String params);


    //删除历史消息
    @FormUrlEncoded
    @POST("mbtwz/sendusermsg?action=deleteUserMsg")
    Observable<ResponseBody> deleteUserMsg(@Field("data") String data);

    //常见消息列表
    @FormUrlEncoded
    @POST("mbtwz/comprob?action=selectCommProblems")
    Observable<ResponseBody> selectCommProblems(@Field("params") String params);

    //点击有用无用接口
    @FormUrlEncoded
    @POST("mbtwz/comprob?action=updateCommProblemsUse")
    Observable<ResponseBody> updateCommProblemsUse(@Field("data") String data);

    //上传货主认证信息
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=insertOwnerCertification")
    Observable<ResponseBody> insertOwnerCertification(@Field("data") String data);

    //检测货主是否认证
    @POST("mbtwz/logisticssendwz?action=checkOwnerStatus")
    Observable<ResponseBody> checkOwnerStatus();

    //检测货主是否认证
    @FormUrlEncoded
    @POST("mbtwz/logisticssendwz?action=checkOwnerStatus")
    Observable<ResponseBody> checkOwnerStatus1(@Field("params") String params);


    //商城首页列表
    @POST("mbtwz/scshop?action=selectMallIndex")
    Observable<ResponseBody> selectMallIndex();


    //商品分类查询
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=selectMallProdsByParam")
    Observable<ResponseBody> selectMallProdsByParam(@Field("params") String params);

    //未读消息个数
    @FormUrlEncoded
    @POST("mbtwz/sendusermsg?action=searchUserMsgNoRead")
    Observable<ResponseBody> searchUserMsgNoRead(@Field("params") String params);


    //找货 货滴快运 快运单
    @FormUrlEncoded
    @POST("mbtwz/find?action=selectKuaiyunOrderList")
    Observable<ResponseBody> selectKuaiyunOrderList(@Field("page") int page, @Field("rows") int rows,@Field("params") String params);

    //快运单抢单列表筛选
    @FormUrlEncoded
    @POST("mbtwz/find?action=selectKuaiyunOrderListByParam")
    Observable<ResponseBody> selectKuaiyunOrderListByParam(@Field("page") int page, @Field("rows") int rows,@Field("params") String params);

    //找货 快运 订单详情 查询接口
    @FormUrlEncoded
    @POST("mbtwz/find?action=selectKuaiyunOrderDetail")
    Observable<ResponseBody> selectKuaiyunOrderDetail(@Field("params") String params);


    //二手车首页信息
    @FormUrlEncoded
    @POST("mbtwz/secondhandcar?action=selectSecondhandcarIndex")
    Observable<ResponseBody> selectSecondhandcarIndex(@Field("params") String params);

    //二手车首页列表信息
    @FormUrlEncoded
    @POST("mbtwz/secondhandcar?action=searchSecondhandcarListByPage")
    Observable<ResponseBody> searchSecondhandcarListByPage(@Field("page") int page, @Field("rows") int rows,@Field("params") String params);

    //商品分类查询11
    @FormUrlEncoded
    @POST("mbtwz/scshop?action=selectMallProdsByParam_type")
    Observable<ResponseBody> selectMallProdsByParam_type(@Field("params") String params);

    //首页二手车详情
    @FormUrlEncoded
    @POST("mbtwz/secondhandcar?action=searchSecondhandcarDet")
    Observable<ResponseBody> searchSecondhandcarDet(@Field("params") String params);


    //二手车品牌选择
    @POST("mbtwz/secondhandcar?action=selectPubSechancarBrand")
    Observable<ResponseBody> selectPubSechancarBrand();


    //发布二手车上传基本信息
    @FormUrlEncoded
    @POST("mbtwz/secondhandcar?action=addSelfSecondhandcar")
    Observable<ResponseBody> addSelfSecondhandcar(@Field("data") String data);


    //查询我的二手车列表
    @POST("mbtwz/secondhandcar?action=searchSelfSecondhandcar")
    Observable<ResponseBody> searchSelfSecondhandcar();

    //删除我的二手车
    @FormUrlEncoded
    @POST("mbtwz/secondhandcar?action=deleteSelfSecondhandcar")
    Observable<ResponseBody> deleteSelfSecondhandcar(@Field("data") String data);


    //积分签到信息
    @POST("mbtwz/signinintegral?action=selectSigninIndex")
    Observable<ResponseBody> selectSigninIndex();


    //积分签到
    @POST("mbtwz/signinintegral?action=addSignin")
    Observable<ResponseBody> addSignin();


    //其他月的签到信息
    @FormUrlEncoded
    @POST("mbtwz/signinintegral?action=selectSigninByMonth")
    Observable<ResponseBody> selectSigninByMonth(@Field("params") String params);

    //快运车长
    @POST("mbtwz/logisticssendwz?action=searchVehicleLength")
    Observable<ResponseBody> searchVehicleLength();


    //筛选顺风车列表
    @FormUrlEncoded
    @POST("mbtwz/freeride?action=searchFreeRideList")
    Observable<ResponseBody> searchFreeRideList(@Field("params") String params);


    //顺风车详情
    @FormUrlEncoded
    @POST("mbtwz/freeride?action=selectFreeRideDet")
    Observable<ResponseBody> selectFreeRideDet(@Field("data") String data);

    //选择顺风车行程后进行发快运单
    @FormUrlEncoded
    @POST("mbtwz/freeride?action=addKuaiyunOrderByFreeRide")
    Observable<ResponseBody> addKuaiyunOrderByFreeRide(@Field("data") String data);


    //快运单选择此行程
    @FormUrlEncoded
    @POST("mbtwz/freeride?action=joinFreeRideOne")
    Observable<ResponseBody> joinFreeRideOne(@Field("data") String data);

    //检测是否在此顺风车下发布过订单
    @FormUrlEncoded
    @POST("mbtwz/freeride?action=checkJoinFreeRideOne")
    Observable<ResponseBody> checkJoinFreeRideOne(@Field("data") String data);
}
