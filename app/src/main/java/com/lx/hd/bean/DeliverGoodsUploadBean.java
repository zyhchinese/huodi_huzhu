package com.lx.hd.bean;

import java.util.ArrayList;

/**
 * Created by TB on 2018/1/13 0013.
 * 物流发货表单参数
 */

public class DeliverGoodsUploadBean {
    private String table = "logistics_owner_order";
    private String owner_link_name = "";//姓名
    private String owner_link_phone = "";//手机号
    private String consignorphone = "";  //发货人电话
    private String door_number;//楼层门牌号
    private String owner_note;//备注
    private String owner_sendtime;//用车时间
    private String owner_totalprice;//价格
    private String owner_cartype_id;//车辆ID
    private String owner_starting_price;//起步价
    private String owner_mileage_price;//实际价格
    private String total_mileage;//总里程
    private String owner_address;//起点地址
    private String owner_send_address;//终点地址
    private String longitude;//起点坐标
    private String latitude;
    private String send_longitude;//终点坐标
    private String send_latitude;
    private ArrayList<AdditionalDemandProListBean> lease_order_detailList;//额外需求


    public String getConsignorphone() {
        return consignorphone;
    }

    public void setConsignorphone(String consignorphone) {
        this.consignorphone = consignorphone;
    }

    public ArrayList<AdditionalDemandProListBean> getLease_order_detailList() {
        return lease_order_detailList;
    }

    public void setLease_order_detailList(ArrayList<AdditionalDemandProListBean> lease_order_detailList) {
        this.lease_order_detailList = lease_order_detailList;
    }

    public String getOwner_send_address() {
        return owner_send_address;
    }

    public void setOwner_send_address(String owner_send_address) {
        this.owner_send_address = owner_send_address;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getOwner_link_name() {
        return owner_link_name;
    }

    public void setOwner_link_name(String owner_link_name) {
        this.owner_link_name = owner_link_name;
    }

    public String getOwner_link_phone() {
        return owner_link_phone;
    }

    public void setOwner_link_phone(String owner_link_phone) {
        this.owner_link_phone = owner_link_phone;
    }

    public String getDoor_number() {
        return door_number;
    }

    public void setDoor_number(String door_number) {
        this.door_number = door_number;
    }

    public String getOwner_note() {
        return owner_note;
    }

    public void setOwner_note(String owner_note) {
        this.owner_note = owner_note;
    }

    public String getOwner_sendtime() {
        return owner_sendtime;
    }

    public void setOwner_sendtime(String owner_sendtime) {
        this.owner_sendtime = owner_sendtime;
    }

    public String getOwner_totalprice() {
        return owner_totalprice;
    }

    public void setOwner_totalprice(String owner_totalprice) {
        this.owner_totalprice = owner_totalprice;
    }

    public String getOwner_cartype_id() {
        return owner_cartype_id;
    }

    public void setOwner_cartype_id(String owner_cartype_id) {
        this.owner_cartype_id = owner_cartype_id;
    }

    public String getOwner_starting_price() {
        return owner_starting_price;
    }

    public void setOwner_starting_price(String owner_starting_price) {
        this.owner_starting_price = owner_starting_price;
    }

    public String getOwner_mileage_price() {
        return owner_mileage_price;
    }

    public void setOwner_mileage_price(String owner_mileage_price) {
        this.owner_mileage_price = owner_mileage_price;
    }

    public String getTotal_mileage() {
        return total_mileage;
    }

    public void setTotal_mileage(String total_mileage) {
        this.total_mileage = total_mileage;
    }

    public String getOwner_address() {
        return owner_address;
    }

    public void setOwner_address(String owner_address) {
        this.owner_address = owner_address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSend_longitude() {
        return send_longitude;
    }

    public void setSend_longitude(String send_longitude) {
        this.send_longitude = send_longitude;
    }

    public String getSend_latitude() {
        return send_latitude;
    }

    public void setSend_latitude(String send_latitude) {
        this.send_latitude = send_latitude;
    }
}
