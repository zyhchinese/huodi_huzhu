/**
 * Copyright 2017 TB   订单信息
 */
package com.lx.hd.bean;

import java.util.ArrayList;

/**
 * Auto-generated: 2017-10-24 15:47:26
 */
public class myorderbean {

    private int areaid;
    private int cityid;
    private String createtime;
    private String custaddress;
    private int custid;
    private int type;
    private String custname;
    private String custphone;
    private int id;
    private int ispay;
    private String note;
    private String orderno;
    private int orderstatus;
    private int paymethod;
    private String payno;
    private int provinceid;
    private int totalcount;
    private double totalmoney;
    private String uuid;
    private double totaljifen;
    private ArrayList<myorderbeanforlist> itemlist=new ArrayList<myorderbeanforlist>();

    public double getTotaljifen() {
        return totaljifen;
    }

    public void setTotaljifen(double totaljifen) {
        this.totaljifen = totaljifen;
    }

    public ArrayList<myorderbeanforlist> getItemlist() {
        return itemlist;
    }

    public void setItemlist(ArrayList<myorderbeanforlist> itemlist) {
        this.itemlist = itemlist;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public int getAreaid() {
        return areaid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public int getCityid() {
        return cityid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCustaddress(String custaddress) {
        this.custaddress = custaddress;
    }

    public String getCustaddress() {
        return custaddress;
    }

    public void setCustid(int custid) {
        this.custid = custid;
    }

    public int getCustid() {
        return custid;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustphone(String custphone) {
        this.custphone = custphone;
    }

    public String getCustphone() {
        return custphone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIspay(int ispay) {
        this.ispay = ispay;
    }

    public int getIspay() {
        return ispay;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderstatus(int orderstatus) {
        this.orderstatus = orderstatus;
    }

    public int getOrderstatus() {
        return orderstatus;
    }

    public void setPaymethod(int paymethod) {
        this.paymethod = paymethod;
    }

    public int getPaymethod() {
        return paymethod;
    }

    public void setPayno(String payno) {
        this.payno = payno;
    }

    public String getPayno() {
        return payno;
    }

    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }

    public int getProvinceid() {
        return provinceid;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalmoney(double totalmoney) {
        this.totalmoney = totalmoney;
    }

    public double getTotalmoney() {
        return totalmoney;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

}