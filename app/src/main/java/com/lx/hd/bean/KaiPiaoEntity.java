package com.lx.hd.bean;

/**
 * Created by Administrator on 2018/3/3.
 */

public class KaiPiaoEntity {

    /**
     * id : 19
     * createtime : 2018-03-03 11:22:42
     * startaddress : 山东省济南市历城区港沟街道龙奥北路307号汉峪金融商务中心
     * endaddress : 山东省济南市历下区龙洞街道扁石山龙洞风景区
     * ordertotalprice : 10
     */

    private int id;
    private String createtime;
    private String startaddress;
    private String endaddress;
    private double ordertotalprice;
    private boolean type=false;

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStartaddress() {
        return startaddress;
    }

    public void setStartaddress(String startaddress) {
        this.startaddress = startaddress;
    }

    public String getEndaddress() {
        return endaddress;
    }

    public void setEndaddress(String endaddress) {
        this.endaddress = endaddress;
    }

    public double getOrdertotalprice() {
        return ordertotalprice;
    }

    public void setOrdertotalprice(double ordertotalprice) {
        this.ordertotalprice = ordertotalprice;
    }
}
