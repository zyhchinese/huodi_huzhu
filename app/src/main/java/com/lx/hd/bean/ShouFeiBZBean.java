package com.lx.hd.bean;

/**
 * Created by TB on 2018/3/8 0008.
 * 收费标准
 */

public class ShouFeiBZBean {
    private String id;    //键id
    private String timename;    //量范围
    private String during_time;    //价格

    public ShouFeiBZBean() {
        super();
    }

    public ShouFeiBZBean(String id, String timename, String during_time) {
        this.id = id;
        this.timename = timename;
        this.during_time = during_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimename() {
        return timename;
    }

    public void setTimename(String timename) {
        this.timename = timename;
    }

    public String getDuring_time() {
        return during_time;
    }

    public void setDuring_time(String during_time) {
        this.during_time = during_time;
    }
}
