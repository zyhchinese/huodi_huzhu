package com.lx.hd.bean;

/**
 * Created by TB on 2018/3/5 0005.
 * 车型bean
 */

public class cxbean {
    private String id;//主键id
    private String cartypename;//车型名称
    private boolean type;

    public cxbean() {
        super();
    }

    public cxbean(String id, String cartypename) {
        this.id = id;
        this.cartypename = cartypename;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCartypename() {
        return cartypename;
    }

    public void setCartypename(String cartypename) {
        this.cartypename = cartypename;
    }
}
