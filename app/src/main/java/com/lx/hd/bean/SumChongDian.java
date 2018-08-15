package com.lx.hd.bean;

import java.io.Serializable;

/**
 * Created by TB on 2017/9/7.
 */

public class SumChongDian implements Serializable{
    private String time;
    private String totalcount;
    private String totalrealmoney;

    public SumChongDian(String time, String totalcount, String totalrealmoney) {
        this.time = time;
        this.totalcount = totalcount;
        this.totalrealmoney = totalrealmoney;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public String getTotalrealmoney() {
        return totalrealmoney;
    }

    public void setTotalrealmoney(String totalrealmoney) {
        this.totalrealmoney = totalrealmoney;
    }
}
