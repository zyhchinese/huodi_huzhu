package com.lx.hd.bean;

/**
 * Created by Administrator on 2017/10/23.
 */

public class QueryListEntity {


    private int custid;

    private int provinceid;

    private int cityid;

    private int areaid;

    private String address;

    private String note;

    private int id;

    private int type;

    private int isdefault;

    private String shcustname;

    private String shphone;

    private String taxphone;

    private String postcode;

    private String addresslabel;

    public void setCustid(int custid){
        this.custid = custid;
    }
    public int getCustid(){
        return this.custid;
    }
    public void setProvinceid(int provinceid){
        this.provinceid = provinceid;
    }
    public int getProvinceid(){
        return this.provinceid;
    }
    public void setCityid(int cityid){
        this.cityid = cityid;
    }
    public int getCityid(){
        return this.cityid;
    }
    public void setAreaid(int areaid){
        this.areaid = areaid;
    }
    public int getAreaid(){
        return this.areaid;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public void setIsdefault(int isdefault){
        this.isdefault = isdefault;
    }
    public int getIsdefault(){
        return this.isdefault;
    }
    public void setShcustname(String shcustname){
        this.shcustname = shcustname;
    }
    public String getShcustname(){
        return this.shcustname;
    }
    public void setShphone(String shphone){
        this.shphone = shphone;
    }
    public String getShphone(){
        return this.shphone;
    }
    public void setTaxphone(String taxphone){
        this.taxphone = taxphone;
    }
    public String getTaxphone(){
        return this.taxphone;
    }
    public void setPostcode(String postcode){
        this.postcode = postcode;
    }
    public String getPostcode(){
        return this.postcode;
    }
    public void setAddresslabel(String addresslabel){
        this.addresslabel = addresslabel;
    }
    public String getAddresslabel(){
        return this.addresslabel;
    }
}
