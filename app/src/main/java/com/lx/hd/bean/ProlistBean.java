package com.lx.hd.bean;

/**
 * Created by TB on 2017/10/27.
 */

public class ProlistBean {
    private String table = "mall_pro_orderdetail";
    private float money;
    private String proname;
    private int proid;
    private String specification;
    private String color;
    private int count;
    private float price;
    private float price_jf;

    public ProlistBean(float money, String proname, int proid, String specification, String color, int count, float price,float price_jf) {
        this.money = money;
        this.proname = proname;
        this.proid = proid;
        this.specification = specification;
        this.color = color;
        this.count = count;
        this.price = price;
        this.price_jf=price_jf;
    }

    public float getPrice_jf() {
        return price_jf;
    }

    public void setPrice_jf(float price_jf) {
        this.price_jf = price_jf;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
