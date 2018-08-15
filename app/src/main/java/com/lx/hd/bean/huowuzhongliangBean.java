package com.lx.hd.bean;

/**
 * Created by TB on 2018/3/7 0007.
 */
//id  		  		主键id
//        timename			显示的重量区间
//        during_time			金额

public class huowuzhongliangBean {
    private String id;
    private String timename;
    private String during_time;

    public huowuzhongliangBean() {
        super();
    }

    public huowuzhongliangBean(String id, String timename, String during_time) {
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
