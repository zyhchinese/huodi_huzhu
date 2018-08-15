package com.lx.hd.bean;

/**
 * Created by TB on 2017/10/23.
 */

public class GoodsColosBean {
    private String colosname;
    private boolean isok = false;
    private int type; //1=颜色  2=类别

    public GoodsColosBean() {
        super();
    }

    public GoodsColosBean(String colosname, boolean isok) {
        this.colosname = colosname;
        this.isok = isok;
    }

    public GoodsColosBean(String colosname, boolean isok, int type) {
        this.colosname = colosname;
        this.isok = isok;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getColosname() {
        return colosname;
    }

    public void setColosname(String colosname) {
        this.colosname = colosname;
    }

    public boolean isok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }
}
