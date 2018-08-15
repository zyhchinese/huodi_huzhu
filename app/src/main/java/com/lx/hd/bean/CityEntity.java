package com.lx.hd.bean;

/**
 * Created by Administrator on 2017/10/24.
 */

public class CityEntity {



    private String tishiname;

    public CityEntity(String areaname) {
        this.areaname = areaname;
    }


    public String getTishiname() {
        return tishiname;
    }


    private int areaid;

    private String areaname;

    public void setAreaid(int areaid){
        this.areaid = areaid;
    }
    public int getAreaid(){
        return this.areaid;
    }
    public void setAreaname(String areaname){
        this.areaname = areaname;
    }
    public String getAreaname(){
        return this.areaname;
    }
}
