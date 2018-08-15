package com.lx.hd.widgets.duoxuandialog;

/**
 * Created by Administrator on 2018/3/5.
 */

public class ShuJuEntity {
    private int id;
    private String name;
    private boolean type;

    public ShuJuEntity(int id, String name, boolean type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
