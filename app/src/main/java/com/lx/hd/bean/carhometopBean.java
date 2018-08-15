package com.lx.hd.bean;

/**
 * Created by TB on 2017/9/23.
 */

public class carhometopBean {
    private String folder;
    private String brandname;
    private int id;
    private String uuid;
    private int brandid;
    private String picname;
    private String groupname;
    private String autoname;
    private double groupprice;

    public carhometopBean() {

    }

    public carhometopBean(String folder, String brandname, int id, String uuid, int brandid, String picname, String groupname, String autoname, double groupprice) {
        this.folder = folder;
        this.brandname = brandname;
        this.id = id;
        this.uuid = uuid;
        this.brandid = brandid;
        this.picname = picname;
        this.groupname = groupname;
        this.autoname = autoname;
        this.groupprice = groupprice;
    }



    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getBrandid() {
        return brandid;
    }

    public void setBrandid(int brandid) {
        this.brandid = brandid;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getAutoname() {
        return autoname;
    }

    public void setAutoname(String autoname) {
        this.autoname = autoname;
    }

    public double getGroupprice() {
        return groupprice;
    }

    public void setGroupprice(double groupprice) {
        this.groupprice = groupprice;
    }
}
