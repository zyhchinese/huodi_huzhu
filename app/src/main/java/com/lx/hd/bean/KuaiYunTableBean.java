package com.lx.hd.bean;

/**
 * Created by tb
 * on 2018/3/5 0005.
 * 快运提交订单表单
 */

public class KuaiYunTableBean {
    //    快运提交订单 接口
//    接口：
//            /mbtwz/logisticssendwz?action=addKuaiyunOrder
//    参数类型：
//    data
//    参数：
//    contactname					联系人
//    contactphone				联系人电话
//    startaddress				起点地址
//    startlongitude				起点经度
//    startlatitude				起点纬度
//    endaddress					终点地址
//    endlongitude				终点经度
//    endlatitude					终点纬度
//    shipmenttime				装车时间
//    cartypenames				车型名称（多个英文逗号分隔）
//    cargotypenames				货物类型
//    weight 						重量 （没有则传0）
//    volume 						体积 （没有则传0）
//    remarks						备注
//    settheprice					设定金额
//    table="huodi_kuaiyun"	(固定)
//    返回值：
//    nologin						未登录
//		false  		  				提交失败
//    UUID						订单的uuid
    private String contactname;//		联系人
    private String contactphone;//	联系人电话
    private String consignorphone;   //发货人联系电话
    private String startaddress;//	起点地址
    private String startlongitude;//		起点经度
    private String startlatitude;//	起点纬度
    private String endaddress;//		终点地址
    private String endlongitude;//	终点经度
    private String endlatitude;//		终点纬度
    private String shipmenttime;//	装车时间
    private String cartypenames;//	车型名称（多个英文逗号分隔）
    private String cargotypenames;//		货物类型
    private String weight;//		重量 （没有则传0）
    private String volume;//		体积 （没有则传0）
    private String remarks;//		备注
    private String settheprice;//		设定金额
    private String table = "huodi_kuaiyun";//(固定)
    private String sprovince;                //出发地省id
    private String scity;                //	出发地市id
    private String scounty;        //	出发地县区id
    private String eprovince;            //目的地省id
    private String ecity;        //	目的地市id
    private String ecounty;            //目的地县区id
    private String total_mileage;    //总里程
    private String relevant_type;    //默认信息
    private String use_car_type;       //用车类型， 0：整车，1：零担，2：不限
    private String lengthname;      //车长，单位米
    private String ton_weight;        //吨单位的重量，可为0，单位吨


    public String getUse_car_type() {
        return use_car_type;
    }

    public void setUse_car_type(String use_car_type) {
        this.use_car_type = use_car_type;
    }

    public String getLengthname() {
        return lengthname;
    }

    public void setLengthname(String lengthname) {
        this.lengthname = lengthname;
    }

    public String getTon_weight() {
        return ton_weight;
    }

    public void setTon_weight(String ton_weight) {
        this.ton_weight = ton_weight;
    }

    public String getRelevant_type() {
        return relevant_type;
    }

    public void setRelevant_type(String relevant_type) {
        this.relevant_type = relevant_type;
    }

    public String getTotal_mileage() {
        return total_mileage;
    }

    public void setTotal_mileage(String total_mileage) {
        this.total_mileage = total_mileage;
    }

    public String getConsignorphone() {
        return consignorphone;
    }

    public void setConsignorphone(String consignorphone) {
        this.consignorphone = consignorphone;
    }

    public String getSprovince() {
        return sprovince;
    }

    public void setSprovince(String sprovince) {
        this.sprovince = sprovince;
    }

    public String getScity() {
        return scity;
    }

    public void setScity(String scity) {
        this.scity = scity;
    }

    public String getScounty() {
        return scounty;
    }

    public void setScounty(String scounty) {
        this.scounty = scounty;
    }

    public String getEprovince() {
        return eprovince;
    }

    public void setEprovince(String eprovince) {
        this.eprovince = eprovince;
    }

    public String getEcity() {
        return ecity;
    }

    public void setEcity(String ecity) {
        this.ecity = ecity;
    }

    public String getEcounty() {
        return ecounty;
    }

    public void setEcounty(String ecounty) {
        this.ecounty = ecounty;
    }

    public KuaiYunTableBean() {
        super();
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
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

    public String getShipmenttime() {
        return shipmenttime;
    }

    public void setShipmenttime(String shipmenttime) {
        this.shipmenttime = shipmenttime;
    }

    public String getCartypenames() {
        return cartypenames;
    }

    public void setCartypenames(String cartypenames) {
        this.cartypenames = cartypenames;
    }

    public String getCargotypenames() {
        return cargotypenames;
    }

    public void setCargotypenames(String cargotypenames) {
        this.cargotypenames = cargotypenames;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSettheprice() {
        return settheprice;
    }

    public void setSettheprice(String settheprice) {
        this.settheprice = settheprice;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


}
