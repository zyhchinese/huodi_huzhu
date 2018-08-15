package com.lx.hd.bean;

/**
 * Created by Administrator on 2018/1/18.
 */

public class EWaiEntity1 {
    private double owner_service_price;

    private String service_name;

    private double service_price;

    public void setOwner_service_price(double owner_service_price){
        this.owner_service_price = owner_service_price;
    }
    public double getOwner_service_price(){
        return this.owner_service_price;
    }
    public void setService_name(String service_name){
        this.service_name = service_name;
    }
    public String getService_name(){
        return this.service_name;
    }
    public void setService_price(double service_price){
        this.service_price = service_price;
    }
    public double getService_price(){
        return this.service_price;
    }
}
