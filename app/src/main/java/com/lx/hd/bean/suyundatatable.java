package com.lx.hd.bean;

import java.util.ArrayList;

/**
 * Created by TB
 * on 2018/3/7 0007.
 * 速运订单提交
 */
//参数：
//        consigneename				收货人姓名
//        consigneephone				收货人联系电话
//        floorhousenumber			楼层及门牌号
//        remarks						发货备注
//        pickuptime					取货时间
//        ordertotalprice				订单总价
//        weightofgoods				货物区间名称
//        startaddress				起点地址
//        startlongitude				起点经度
//        startlatitude				起点纬度
//        endaddress					目的地
//        endlongitude				目的地经度
//        endlatitude					目的地纬度
//        expectedarrivaltime			预计到达时间
//        table="huodi_suyun"	(固定)
//        huodi_suyun_detailsList : 		数组 如下:
//        table="huodi_suyun_details" 	(固定)
//        owner_service_id 		额外需求服务id
//        owner_service_price		服务价格（0为免费）
//
//        //**************增加参数************//
//        sprovinceid					出发地省id
//        scityid						出发地市id
//        scountyid 					出发地县区id
//        sprocitcou 					出发地中文
//        (省市区英文逗号分隔，例 山东省,济南市,高新区)
//        eprovinceid 				目的地省id
//        ecityid 					目的地市id
//        ecountyid 					目的地县区id
//        eprocitcou					目的地中文
//        (省市区英文逗号分隔，同出发地)
public class suyundatatable {

    private String consigneename;        //	收货人姓名
    private String consigneephone;            //收货人联系电话
    private String consignorphone;          //发货人联系电话
    private String floorhousenumber;    //	楼层及门牌号
    private String remarks;                    //发货备注
    private String pickuptime;        //	取货时间
    private String ordertotalprice;        //	订单总价
    private String weightofgoods;        //	货物区间名称
    private String startaddress;            //起点地址
    private String startlongitude;        //	起点经度
    private String startlatitude;            //起点纬度
    private String endaddress;            //目的地
    private String endlongitude;            //目的地经度
    private String endlatitude;            //	目的地纬度
    private String expectedarrivaltime;            //预计到达时间
    private String table = "huodi_suyun";//(固定)
    private ArrayList<AdditionalDemandProListBean>  huodi_suyun_detailsList;    //数组 如下:
    private String sprovinceid;                //出发地省id
    private String scityid;                //	出发地市id
    private String scountyid;        //	出发地县区id
    private String sprocitcou;            //	出发地中文
    //  (省市区英文逗号分隔，例 山东省,济南市,高新区)
    private String eprovinceid;            //目的地省id
    private String ecityid;        //	目的地市id
    private String ecountyid;            //目的地县区id
    private String eprocitcou;    //	目的地中文
    private String total_mileage;  //总里程

    public String getTotal_mileage() {
        return total_mileage;
    }

    public void setTotal_mileage(String total_mileage) {
        this.total_mileage = total_mileage;
    }

    // (省市区英文逗号分隔，同出发地)
    public suyundatatable() {
        super();
    }

    public String getConsignorphone() {
        return consignorphone;
    }

    public void setConsignorphone(String consignorphone) {
        this.consignorphone = consignorphone;
    }

    public String getConsigneename() {
        return consigneename;
    }

    public void setConsigneename(String consigneename) {
        this.consigneename = consigneename;
    }

    public String getConsigneephone() {
        return consigneephone;
    }

    public void setConsigneephone(String consigneephone) {
        this.consigneephone = consigneephone;
    }

    public String getFloorhousenumber() {
        return floorhousenumber;
    }

    public void setFloorhousenumber(String floorhousenumber) {
        this.floorhousenumber = floorhousenumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

    public String getOrdertotalprice() {
        return ordertotalprice;
    }

    public void setOrdertotalprice(String ordertotalprice) {
        this.ordertotalprice = ordertotalprice;
    }

    public String getWeightofgoods() {
        return weightofgoods;
    }

    public void setWeightofgoods(String weightofgoods) {
        this.weightofgoods = weightofgoods;
    }

    public String getStartaddress() {
        return startaddress;
    }

    public void setStartaddress(String startaddress) {
        this.startaddress = startaddress;
    }

    public String getStartlongitude() {
        return startlongitude;
    }

    public void setStartlongitude(String startlongitude) {
        this.startlongitude = startlongitude;
    }

    public String getStartlatitude() {
        return startlatitude;
    }

    public void setStartlatitude(String startlatitude) {
        this.startlatitude = startlatitude;
    }

    public String getEndaddress() {
        return endaddress;
    }

    public void setEndaddress(String endaddress) {
        this.endaddress = endaddress;
    }

    public String getEndlongitude() {
        return endlongitude;
    }

    public void setEndlongitude(String endlongitude) {
        this.endlongitude = endlongitude;
    }

    public String getEndlatitude() {
        return endlatitude;
    }

    public void setEndlatitude(String endlatitude) {
        this.endlatitude = endlatitude;
    }

    public String getExpectedarrivaltime() {
        return expectedarrivaltime;
    }

    public void setExpectedarrivaltime(String expectedarrivaltime) {
        this.expectedarrivaltime = expectedarrivaltime;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public ArrayList<AdditionalDemandProListBean>  getHuodi_suyun_detailsList() {
        return huodi_suyun_detailsList;
    }

    public void setHuodi_suyun_detailsList(ArrayList<AdditionalDemandProListBean> huodi_suyun_detailsList) {
        this.huodi_suyun_detailsList = huodi_suyun_detailsList;
    }

    public String getSprovinceid() {
        return sprovinceid;
    }

    public void setSprovinceid(String sprovinceid) {
        this.sprovinceid = sprovinceid;
    }

    public String getScityid() {
        return scityid;
    }

    public void setScityid(String scityid) {
        this.scityid = scityid;
    }

    public String getScountyid() {
        return scountyid;
    }

    public void setScountyid(String scountyid) {
        this.scountyid = scountyid;
    }

    public String getSprocitcou() {
        return sprocitcou;
    }

    public void setSprocitcou(String sprocitcou) {
        this.sprocitcou = sprocitcou;
    }

    public String getEprovinceid() {
        return eprovinceid;
    }

    public void setEprovinceid(String eprovinceid) {
        this.eprovinceid = eprovinceid;
    }

    public String getEcityid() {
        return ecityid;
    }

    public void setEcityid(String ecityid) {
        this.ecityid = ecityid;
    }

    public String getEcountyid() {
        return ecountyid;
    }

    public void setEcountyid(String ecountyid) {
        this.ecountyid = ecountyid;
    }

    public String getEprocitcou() {
        return eprocitcou;
    }

    public void setEprocitcou(String eprocitcou) {
        this.eprocitcou = eprocitcou;
    }

    public suyundatatable(String consigneename, String consigneephone, String floorhousenumber, String remarks, String pickuptime, String ordertotalprice, String weightofgoods, String startaddress, String startlongitude, String startlatitude, String endaddress, String endlongitude, String endlatitude, String expectedarrivaltime, String table, ArrayList<AdditionalDemandProListBean>  huodi_suyun_detailsList, String sprovinceid, String scityid, String scountyid, String sprocitcou, String eprovinceid, String ecityid, String ecountyid, String eprocitcou) {
        this.consigneename = consigneename;
        this.consigneephone = consigneephone;
        this.floorhousenumber = floorhousenumber;
        this.remarks = remarks;
        this.pickuptime = pickuptime;
        this.ordertotalprice = ordertotalprice;
        this.weightofgoods = weightofgoods;
        this.startaddress = startaddress;
        this.startlongitude = startlongitude;
        this.startlatitude = startlatitude;
        this.endaddress = endaddress;
        this.endlongitude = endlongitude;
        this.endlatitude = endlatitude;
        this.expectedarrivaltime = expectedarrivaltime;
        this.table = table;
        this.huodi_suyun_detailsList = huodi_suyun_detailsList;
        this.sprovinceid = sprovinceid;
        this.scityid = scityid;
        this.scountyid = scountyid;
        this.sprocitcou = sprocitcou;
        this.eprovinceid = eprovinceid;
        this.ecityid = ecityid;
        this.ecountyid = ecountyid;
        this.eprocitcou = eprocitcou;
    }
}
