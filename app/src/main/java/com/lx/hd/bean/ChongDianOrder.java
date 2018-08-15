package com.lx.hd.bean;

/**
 * Created by TB on 2017/9/6.
 */

public class ChongDianOrder {
    private int orderid;
    private String orderno;
    private String createtime;
    private String electricsbm;
    private String electricno;
    private String count;
    private String realmoney;

    public ChongDianOrder(int orderid, String orderno, String createtime, String electricsbm, String electricno, String count, String realmoney) {
        this.orderid = orderid;
        this.orderno = orderno;
        this.createtime = createtime;
        this.electricsbm = electricsbm;
        this.electricno = electricno;
        this.count = count;
        this.realmoney = realmoney;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getElectricsbm() {
        return electricsbm;
    }

    public void setElectricsbm(String electricsbm) {
        this.electricsbm = electricsbm;
    }

    public String getElectricno() {
        return electricno;
    }

    public void setElectricno(String electricno) {
        this.electricno = electricno;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRealmoney() {
        return realmoney;
    }

    public void setRealmoney(String realmoney) {
        this.realmoney = realmoney;
    }
}
