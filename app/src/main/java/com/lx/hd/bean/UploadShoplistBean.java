package com.lx.hd.bean;

import java.util.ArrayList;

/**
 * Created by TB on 2017/10/27.
 */

public class UploadShoplistBean {
    private String table = "mall_pro_orderdetail";
    private float totalmoney;
    private float totaljifen;
    private int totalcount;
    private int type;
    private ArrayList<ProlistBean> proList;

    public UploadShoplistBean() {
        super();
    }

    public UploadShoplistBean(float totalmoney, int totalcount, int type,  ArrayList<ProlistBean>  proList) {
        this.totalmoney = totalmoney;
        this.totalcount = totalcount;
        this.type = type;
        this.proList = proList;
    }

    public float getTotaljifen() {
        return totaljifen;
    }

    public void setTotaljifen(float totaljifen) {
        this.totaljifen = totaljifen;
    }

    public float getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(float totalmoney) {
        this.totalmoney = totalmoney;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public  ArrayList<ProlistBean>  getProList() {
        return proList;
    }

    public void setProList( ArrayList<ProlistBean>  proList) {
        this.proList = proList;
    }
}
