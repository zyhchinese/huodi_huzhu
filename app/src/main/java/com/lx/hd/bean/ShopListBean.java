package com.lx.hd.bean;

/**
 * Created by TB on 2017/10/19.
 */

public class ShopListBean {
    public ShopListBean() {
        super();
    }

    public ShopListBean(String proname, String productdes, double price, String protypename, int id, String folder, String autoname) {
        this.proname = proname;
        this.productdes = productdes;
        this.price = price;
        this.protypename = protypename;
        this.id = id;
        this.folder = folder;
        this.autoname = autoname;
    }

    private String proname;

    private String productdes;

    private double price;

    private String protypename;
    private String type;
    private int id;
    private int sptype;//是积分商品  还是商品
    private String folder;

    public int getSptype() {
        return sptype;
    }

    public void setSptype(int sptype) {
        this.sptype = sptype;
    }

    private String autoname;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProname() {
        return this.proname;
    }

    public void setProductdes(String productdes) {
        this.productdes = productdes;
    }

    public String getProductdes() {
        return this.productdes;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setProtypename(String protypename) {
        this.protypename = protypename;
    }

    public String getProtypename() {
        return this.protypename;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setAutoname(String autoname) {
        this.autoname = autoname;
    }

    public String getAutoname() {
        return this.autoname;
    }

}
