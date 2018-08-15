package com.lx.hd.bean;

import java.io.Serializable;

/**
 * 地图poi列表实体类
 * Created by tb on 2018/1/11.
 */

public class mappoibean implements Serializable {
    private String title;
    private String text;
    double lon;
    double lat;

    public mappoibean( double lon, double lat,String title, String text) {
        this.title = title;
        this.text = text;
        this.lon = lon;
        this.lat = lat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
