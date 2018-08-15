package com.lx.hd.bean;

import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by TB on 2017/10/17.
 */

public class CarParts implements Serializable {
    private static final long serialVersionUID = -6463724400806267109L;


    private int id;

    private String autoname;

    private String folder;

    private String proname;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setAutoname(String autoname) {
        this.autoname = autoname;
    }

    public String getAutoname() {
        return this.autoname;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProname() {
        return this.proname;
    }
    public CarParts(int id, String autoname, String folder, String proname) {
        this.id = id;
        this.autoname = autoname;
        this.folder = folder;
        this.proname = proname;
    }
    @Generated(hash = 74665348)
    public CarParts() {
        super();
    }
}
