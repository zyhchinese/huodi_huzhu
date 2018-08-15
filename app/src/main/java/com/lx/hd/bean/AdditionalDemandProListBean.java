package com.lx.hd.bean;

/**
 * Created by tb on 2018/1/16 0016.
 */

public class AdditionalDemandProListBean {
    private String table = "huodi_suyun_details";
    private String owner_service_id;
    private String owner_service_price;

    public AdditionalDemandProListBean() {
        super();
    }

    public AdditionalDemandProListBean(String owner_service_id, String owner_service_price,String table) {

        this.owner_service_id = owner_service_id;
        this.owner_service_price = owner_service_price;
        this.table=table;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getOwner_service_id() {
        return owner_service_id;
    }

    public void setOwner_service_id(String owner_service_id) {
        this.owner_service_id = owner_service_id;
    }

    public String getOwner_service_price() {
        return owner_service_price;
    }

    public void setOwner_service_price(String owner_service_price) {
        this.owner_service_price = owner_service_price;
    }
}
