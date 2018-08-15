package com.lx.hd.bean;

/**
 * Created by Administrator on 2018/1/11.
 */

public class CheckSiJiRenZhengImgEntity {

    private int id;

    private int driverid;

    private int driver_img_type;

    private String autoname;

    private String picname;

    private String folder;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setDriverid(int driverid){
        this.driverid = driverid;
    }
    public int getDriverid(){
        return this.driverid;
    }
    public void setDriver_img_type(int driver_img_type){
        this.driver_img_type = driver_img_type;
    }
    public int getDriver_img_type(){
        return this.driver_img_type;
    }
    public void setAutoname(String autoname){
        this.autoname = autoname;
    }
    public String getAutoname(){
        return this.autoname;
    }
    public void setPicname(String picname){
        this.picname = picname;
    }
    public String getPicname(){
        return this.picname;
    }
    public void setFolder(String folder){
        this.folder = folder;
    }
    public String getFolder(){
        return this.folder;
    }
}
