package com.lx.hd.bean;

import java.util.ArrayList;

/**
 * Created by TB on 2018/3/5 0005.
 * 速运提交表单字段
 */
//速运提交订单 接口
//	接口：
//            /mbtwz/logisticssendwz?action=addSuyunOrder
//            参数类型：
//            data
//            参数：
//            consigneename				收货人姓名
//            consigneephone				收货人联系电话
//            floorhousenumber			楼层及门牌号
//            remarks						发货备注
//            pickuptime					取货时间
//            ordertotalprice				订单总价
//            weightofgoods				货物区间名称
//            startaddress				起点地址
//            startlongitude				起点经度
//            startlatitude				起点纬度
//            endaddress					目的地
//            endlongitude				目的地经度
//            endlatitude					目的地纬度
//            expectedarrivaltime			预计到达时间
//            table="huodi_suyun"	(固定)
//            huodi_suyun_detailsList : 		数组 如下:
//            table="huodi_suyun_details" 	(固定)
//            owner_service_id 		额外需求服务id
//            owner_service_price		服务价格（0为免费）
//
//            //**************增加参数************//
//            sprovinceid					出发地省id
//            scityid						出发地市id
//            scountyid 					出发地县区id
//            sprocitcou 					出发地中文
//            (省市区英文逗号分隔，例 山东省,济南市,高新区)
//            eprovinceid 				目的地省id
//            ecityid 					目的地市id
//            ecountyid 					目的地县区id
//            eprocitcou					目的地中文
//            (省市区英文逗号分隔，同出发地)
//            //**************增加参数************//
//            返回值：
//            nologin				未登录
//            soverstep			出发地超出配送范围
//            eoverstep			目的地超出配送范围
//            false  		  		提交失败
//            UUID				成功，订单的uuid

public class SuYunTableBean {
    private String consigneename;
    private String consigneephone;
    private String floorhousenumber;
    private String remarks;
    private String pickuptime;
    private String ordertotalprice;
    private String weightofgoods;
    private String startaddress;
    private String startlongitude;
    private String startlatitude;
    private String endaddress;
    private String endlongitude;
    private String endlatitude;
    private String expectedarrivaltime;
    private String table = "huodi_suyun";
    private ArrayList<AdditionalDemandProListBean> huodi_suyun_detailsList;
    private String sprovinceid;
    private String scityid;
    private String scountyid;
    private String sprocitcou;
    private String eprovinceid;
    private String eprocitcou;

    public SuYunTableBean() {
        super();
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

    public ArrayList<AdditionalDemandProListBean> getHuodi_suyun_detailsList() {
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

    public String getEprocitcou() {
        return eprocitcou;
    }

    public void setEprocitcou(String eprocitcou) {
        this.eprocitcou = eprocitcou;
    }

    public SuYunTableBean(String consigneename, String consigneephone, String floorhousenumber, String remarks, String pickuptime, String ordertotalprice, String weightofgoods, String startaddress, String startlongitude, String startlatitude, String endaddress, String endlongitude, String endlatitude, String expectedarrivaltime, String table, ArrayList<AdditionalDemandProListBean> huodi_suyun_detailsList, String sprovinceid, String scityid, String scountyid, String sprocitcou, String eprovinceid, String eprocitcou) {
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
        this.eprocitcou = eprocitcou;
    }
};
