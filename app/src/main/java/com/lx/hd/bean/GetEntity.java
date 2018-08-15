package com.lx.hd.bean;

/**
 * Created by Administrator on 2017/10/24.
 */

public class GetEntity {
    private int id;
    private String name;

    public GetEntity(int id,String name){
        this.id=id;
        this.name=name;
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
}
