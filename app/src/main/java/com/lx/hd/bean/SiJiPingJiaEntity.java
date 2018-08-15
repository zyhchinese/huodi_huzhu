package com.lx.hd.bean;

/**
 * Created by Administrator on 2018/1/19.
 */

public class SiJiPingJiaEntity {
    private int id;

    private int driver_fraction;

    private int custid;

    private String note;

    private int driverid;

    private String createtime;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setDriver_fraction(int driver_fraction){
        this.driver_fraction = driver_fraction;
    }
    public int getDriver_fraction(){
        return this.driver_fraction;
    }
    public void setCustid(int custid){
        this.custid = custid;
    }
    public int getCustid(){
        return this.custid;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }
    public void setDriverid(int driverid){
        this.driverid = driverid;
    }
    public int getDriverid(){
        return this.driverid;
    }
    public void setCreatetime(String createtime){
        this.createtime = createtime;
    }
    public String getCreatetime(){
        return this.createtime;
    }
}
