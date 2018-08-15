package com.lx.hd.bean;

/**
 * Created by TB on 2018/3/5 0005.
 * 货物类型bean
 */

public class hwlxBean {
    private String id;
    private String cargotypename;

    public hwlxBean(String id, String cargotypename) {
        this.id = id;
        this.cargotypename = cargotypename;
    }

    public hwlxBean() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCargotypename() {
        return cargotypename;
    }

    public void setCargotypename(String cargotypename) {
        this.cargotypename = cargotypename;
    }
}
