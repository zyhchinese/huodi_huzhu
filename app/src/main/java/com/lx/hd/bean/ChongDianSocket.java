package com.lx.hd.bean;

/**
 * Created by TB on 2017/9/7.
 */

public class ChongDianSocket {
    private String biaoshi;
    private String endtime;
    private String realmoney;
    private String chongdiandushu;
    private String orderid;

    public ChongDianSocket(String biaoshi, String endtime, String realmoney, String chongdiandushu, String orderid) {
        this.biaoshi = biaoshi;
        this.endtime = endtime;
        this.realmoney = realmoney;
        this.chongdiandushu = chongdiandushu;
        this.orderid = orderid;
    }

    public String getBiaoshi() {
        return biaoshi;
    }

    public void setBiaoshi(String biaoshi) {
        this.biaoshi = biaoshi;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getRealmoney() {
        return realmoney;
    }

    public void setRealmoney(String realmoney) {
        this.realmoney = realmoney;
    }

    public String getChongdiandushu() {
        return chongdiandushu;
    }

    public void setChongdiandushu(String chongdiandushu) {
        this.chongdiandushu = chongdiandushu;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
