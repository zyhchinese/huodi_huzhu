package com.lx.hd.bean;

/**
 * Created by TB on 2018/3/4 0004.
 * 省市县实体类
 */

public class selectAllAreaBean {
    private String areaid;
    private String areaname;
    private String parentid;

    public selectAllAreaBean() {
        super();
    }

    public selectAllAreaBean(String areaid, String areaname, String parentid) {
        this.areaid = areaid;
        this.areaname = areaname;
        this.parentid = parentid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}
