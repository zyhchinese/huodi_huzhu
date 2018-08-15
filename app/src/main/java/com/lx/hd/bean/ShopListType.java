package com.lx.hd.bean;

/**
 * Created by TB on 2017/10/19.
 */

public class ShopListType {
    private String id;
    private String protypename;

    public ShopListType() {
        super();
    }

    public ShopListType(String id, String protypename) {
        this.id = id;
        this.protypename = protypename;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setProtypename(String protypename) {
        this.protypename = protypename;
    }

    public String getProtypename() {
        return this.protypename;
    }

}
