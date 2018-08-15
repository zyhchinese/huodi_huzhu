package com.lx.hd.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ChargeOrder implements Serializable{
     private String  orderno;
     private String  custid;
     private String  electricsbm;
     private String  orderstatus;
     private String  createtime;
     private String  count;
     private String  totalmoney;
     private String  realmoney;
     private String  note;
     private String  uuid;
     private long  id;
     private String  maxcount;
     private String  electricname;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getElectricsbm() {
        return electricsbm;
    }

    public void setElectricsbm(String electricsbm) {
        this.electricsbm = electricsbm;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(String totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getRealmoney() {
        return realmoney;
    }

    public void setRealmoney(String realmoney) {
        this.realmoney = realmoney;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMaxcount() {
        return maxcount;
    }

    public void setMaxcount(String maxcount) {
        this.maxcount = maxcount;
    }

    public String getElectricname() {
        return electricname;
    }

    public void setElectricname(String electricname) {
        this.electricname = electricname;
    }
}
