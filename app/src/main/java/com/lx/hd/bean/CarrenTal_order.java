package com.lx.hd.bean;


import java.util.List;

/**
 * Created by tb on 2017/12/27.
 */

public class CarrenTal_order {
    private String take_time; //预计取车时间
    private String table = "lease_order"; //lease_order
    private List<Lease_order_detailList> lease_order_detailList; //详情列表
    private String note = ""; //空
    private String link_name;//联系人姓名
    private String link_phone;//联系人手机号
    private String link_email;//联系人邮箱
    private String link_org_name;//企业名称

    public String getLink_org_name() {
        return link_org_name;
    }

    public void setLink_org_name(String link_org_name) {
        this.link_org_name = link_org_name;
    }

    public void setTake_time(String take_time) {
        this.take_time = take_time;
    }

    public String getTake_time() {
        return take_time;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public void setLease_order_detailList(List<Lease_order_detailList> lease_order_detailList) {
        this.lease_order_detailList = lease_order_detailList;
    }

    public List<Lease_order_detailList> getLease_order_detailList() {
        return lease_order_detailList;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public String getLink_name() {
        return link_name;
    }

    public void setLink_name(String link_name) {
        this.link_name = link_name;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public void setLink_email(String link_email) {
        this.link_email = link_email;
    }

    public String getLink_email() {
        return link_email;
    }

}
