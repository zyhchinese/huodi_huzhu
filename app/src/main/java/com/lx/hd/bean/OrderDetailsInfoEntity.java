package com.lx.hd.bean;

/**
 * Created by Administrator on 2017/10/21.
 */

public class OrderDetailsInfoEntity {
    private int id;

    private String orderno;

    private int custid;

    private String custname;

    private String custphone;

    private String custaddress;

    private double totalmoney;

    private int totalcount;

    private String createtime;

    private int orderstatus;

    private int ispay;

    private int paymethod;

    private String payno;

    private String note;

    private String uuid;

    private int provinceid;

    private int cityid;

    private int areaid;

    private String shcustname;

    private String shphone;

    private int type;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setOrderno(String orderno){
        this.orderno = orderno;
    }
    public String getOrderno(){
        return this.orderno;
    }
    public void setCustid(int custid){
        this.custid = custid;
    }
    public int getCustid(){
        return this.custid;
    }
    public void setCustname(String custname){
        this.custname = custname;
    }
    public String getCustname(){
        return this.custname;
    }
    public void setCustphone(String custphone){
        this.custphone = custphone;
    }
    public String getCustphone(){
        return this.custphone;
    }
    public void setCustaddress(String custaddress){
        this.custaddress = custaddress;
    }
    public String getCustaddress(){
        return this.custaddress;
    }
    public void setTotalmoney(double totalmoney){
        this.totalmoney = totalmoney;
    }
    public double getTotalmoney(){
        return this.totalmoney;
    }
    public void setTotalcount(int totalcount){
        this.totalcount = totalcount;
    }
    public int getTotalcount(){
        return this.totalcount;
    }
    public void setCreatetime(String createtime){
        this.createtime = createtime;
    }
    public String getCreatetime(){
        return this.createtime;
    }
    public void setOrderstatus(int orderstatus){
        this.orderstatus = orderstatus;
    }
    public int getOrderstatus(){
        return this.orderstatus;
    }
    public void setIspay(int ispay){
        this.ispay = ispay;
    }
    public int getIspay(){
        return this.ispay;
    }
    public void setPaymethod(int paymethod){
        this.paymethod = paymethod;
    }
    public int getPaymethod(){
        return this.paymethod;
    }
    public void setPayno(String payno){
        this.payno = payno;
    }
    public String getPayno(){
        return this.payno;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }
    public void setUuid(String uuid){
        this.uuid = uuid;
    }
    public String getUuid(){
        return this.uuid;
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
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }

}
