package com.lx.hd.bean;

/**
 * Created by TB on 2017/12/27.
 */

public class Lease_order_detailList {
    private String table="lease_order_details";
    private String model_id;//车型ID
    private String order_type;//1长租0短租
    private String lease_count;//数量
    private String during_time;//时长
    public void setTable(String table) {
        this.table = table;
    }
    public String getTable() {
        return table;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }
    public String getModel_id() {
        return model_id;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
    public String getOrder_type() {
        return order_type;
    }

    public void setLease_count(String lease_count) {
        this.lease_count = lease_count;
    }
    public String getLease_count() {
        return lease_count;
    }

    public void setDuring_time(String during_time) {
        this.during_time = during_time;
    }
    public String getDuring_time() {
        return during_time;
    }

}
