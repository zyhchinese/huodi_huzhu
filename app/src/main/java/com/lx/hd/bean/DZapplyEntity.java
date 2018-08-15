package com.lx.hd.bean;

/**
 * Created by Administrator on 2017/10/28.
 */

public class DZapplyEntity {
    private int custid;

    private int provinceid;

    private int cityid;

    private int areaid;

    private String address;

    private int status;

    private String note;

    private String custphone;

    private String custname;

    private String reson;

    private int id;

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
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }
    public void setCustphone(String custphone){
        this.custphone = custphone;
    }
    public String getCustphone(){
        return this.custphone;
    }
    public void setCustname(String custname){
        this.custname = custname;
    }
    public String getCustname(){
        return this.custname;
    }
    public void setReson(String reson){
        this.reson = reson;
    }
    public String getReson(){
        return this.reson;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
}
