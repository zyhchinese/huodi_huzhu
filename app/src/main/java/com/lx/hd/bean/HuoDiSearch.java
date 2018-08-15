package com.lx.hd.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/2.
 */

public class HuoDiSearch implements Serializable{

    /**
     * id : 9
     * orderno : SDD201802270003
     * owner_custid : 62
     * driver_custid : 55
     * pickuptime : 2018-02-28 00:10:00
     * createtime : 2018-02-27 13:51:55
     * startlongitude : 117
     * startlatitude : 37
     * startaddress : 山东省济南市历下区龙洞街道济南市人大常委会济南龙奥大厦(龙奥北路)
     * endlongitude : 117
     * endlatitude : 37
     * endaddress : 山东省济南市历下区龙洞街道经十路济南奥林匹克体育中心
     * weightofgoods : 50-80
     * expectedarrivaltime : 2018-03-01 02:50:00
     * consigneename : 哦了
     * consigneephone : 17663080550
     * floorhousenumber : 你是
     * remarks : 在线的人越来越少
     * ordertotalprice : 50
     * ispay : 1
     * paymethod : 2
     * pay_no : SDD201802270003
     * pay_time : 2018-02-27 13:52:01
     * pingtai_money : 10
     * siji_money : 40
     * siji_singletime :
     * cancelmoney : 1900-01-03 12:00:00
     * uuid : 72b043a56e734acdbfb2
     * cust_orderstatus : -1
     * driver_orderstatus : -1
     * money_status : 1
     * isinvoice : 0
     * isevaluate : 0
     */

    private int id;
    private String orderno;
    private int owner_custid;
    private int driver_custid;
    private String pickuptime;
    private String createtime;
    private double startlongitude;
    private double startlatitude;
    private String startaddress;
    private double endlongitude;
    private double endlatitude;
    private String endaddress;
    private String weightofgoods;
    private String expectedarrivaltime;
    private String consigneename;
    private String consigneephone;
    private String floorhousenumber;
    private String remarks;
    private double ordertotalprice;
    private int ispay;
    private int paymethod;
    private String pay_no;
    private String pay_time;
    private double pingtai_money;
    private double siji_money;
    private String siji_singletime;
    private String cancelmoney;
    private String uuid;
    private int cust_orderstatus;
    private int driver_orderstatus;
    private int money_status;
    private int isinvoice;
    private String isevaluate;
    private String autoname;
    private String folder;

    public String getAutoname() {
        return autoname;
    }

    public void setAutoname(String autoname) {
        this.autoname = autoname;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public int getOwner_custid() {
        return owner_custid;
    }

    public void setOwner_custid(int owner_custid) {
        this.owner_custid = owner_custid;
    }

    public int getDriver_custid() {
        return driver_custid;
    }

    public void setDriver_custid(int driver_custid) {
        this.driver_custid = driver_custid;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public double getStartlongitude() {
        return startlongitude;
    }

    public void setStartlongitude(double startlongitude) {
        this.startlongitude = startlongitude;
    }

    public double getStartlatitude() {
        return startlatitude;
    }

    public void setStartlatitude(double startlatitude) {
        this.startlatitude = startlatitude;
    }

    public String getStartaddress() {
        return startaddress;
    }

    public void setStartaddress(String startaddress) {
        this.startaddress = startaddress;
    }

    public double getEndlongitude() {
        return endlongitude;
    }

    public void setEndlongitude(double endlongitude) {
        this.endlongitude = endlongitude;
    }

    public double getEndlatitude() {
        return endlatitude;
    }

    public void setEndlatitude(double endlatitude) {
        this.endlatitude = endlatitude;
    }

    public String getEndaddress() {
        return endaddress;
    }

    public void setEndaddress(String endaddress) {
        this.endaddress = endaddress;
    }

    public String getWeightofgoods() {
        return weightofgoods;
    }

    public void setWeightofgoods(String weightofgoods) {
        this.weightofgoods = weightofgoods;
    }

    public String getExpectedarrivaltime() {
        return expectedarrivaltime;
    }

    public void setExpectedarrivaltime(String expectedarrivaltime) {
        this.expectedarrivaltime = expectedarrivaltime;
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

    public double getOrdertotalprice() {
        return ordertotalprice;
    }

    public void setOrdertotalprice(double ordertotalprice) {
        this.ordertotalprice = ordertotalprice;
    }

    public int getIspay() {
        return ispay;
    }

    public void setIspay(int ispay) {
        this.ispay = ispay;
    }

    public int getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(int paymethod) {
        this.paymethod = paymethod;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public double getPingtai_money() {
        return pingtai_money;
    }

    public void setPingtai_money(double pingtai_money) {
        this.pingtai_money = pingtai_money;
    }

    public double getSiji_money() {
        return siji_money;
    }

    public void setSiji_money(double siji_money) {
        this.siji_money = siji_money;
    }

    public String getSiji_singletime() {
        return siji_singletime;
    }

    public void setSiji_singletime(String siji_singletime) {
        this.siji_singletime = siji_singletime;
    }

    public String getCancelmoney() {
        return cancelmoney;
    }

    public void setCancelmoney(String cancelmoney) {
        this.cancelmoney = cancelmoney;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCust_orderstatus() {
        return cust_orderstatus;
    }

    public void setCust_orderstatus(int cust_orderstatus) {
        this.cust_orderstatus = cust_orderstatus;
    }

    public int getDriver_orderstatus() {
        return driver_orderstatus;
    }

    public void setDriver_orderstatus(int driver_orderstatus) {
        this.driver_orderstatus = driver_orderstatus;
    }

    public int getMoney_status() {
        return money_status;
    }

    public void setMoney_status(int money_status) {
        this.money_status = money_status;
    }

    public int getIsinvoice() {
        return isinvoice;
    }

    public void setIsinvoice(int isinvoice) {
        this.isinvoice = isinvoice;
    }

    public String getIsevaluate() {
        return isevaluate;
    }

    public void setIsevaluate(String isevaluate) {
        this.isevaluate = isevaluate;
    }
}
