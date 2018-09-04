package com.lx.hd.bean;

/**
 * Created by Administrator on 2018/5/10.
 */

public class KuaiYunSiJiLieBiaoEntity {

    /**
     * id : 8
     * orderno : FP201805100012
     * driver_custid : 107
     * createtime : 2018-05-10 13:48:53
     * driver_name : 测试
     * driver_phone : 17663080550
     * driver_star : 0
     * driver_num : 8
     * driver_folder :
     * driver_autoname :
     */

    private int id;
    private String orderno;
    private int driver_custid;
    private String createtime;
    private String driver_name;
    private String driver_phone;
    private int driver_star;
    private int driver_num;
    private String driver_folder;
    private String driver_autoname;
    private boolean type1;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isType1() {
        return type1;
    }

    public void setType1(boolean type1) {
        this.type1 = type1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public int getDriver_custid() {
        return driver_custid;
    }

    public void setDriver_custid(int driver_custid) {
        this.driver_custid = driver_custid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public int getDriver_star() {
        return driver_star;
    }

    public void setDriver_star(int driver_star) {
        this.driver_star = driver_star;
    }

    public int getDriver_num() {
        return driver_num;
    }

    public void setDriver_num(int driver_num) {
        this.driver_num = driver_num;
    }

    public String getDriver_folder() {
        return driver_folder;
    }

    public void setDriver_folder(String driver_folder) {
        this.driver_folder = driver_folder;
    }

    public String getDriver_autoname() {
        return driver_autoname;
    }

    public void setDriver_autoname(String driver_autoname) {
        this.driver_autoname = driver_autoname;
    }
}
