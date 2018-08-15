package com.lx.hd.bean;

/**
 * Created by TB on 2017/12/27.
 */

public class AdditionalDemandListBean {

    private int id;
    private String service_name;
    private double service_price;
    private int isvalid;
    private boolean isselect=false;

    public void setService_price(double service_price) {
        this.service_price = service_price;
    }

    public boolean isselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_name() {
        return service_name;
    }



    public Double getService_price() {
        return service_price;
    }

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }

    public Object getIsvalid() {
        return isvalid;
    }

}
