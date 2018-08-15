package com.lx.hd.bean;

/**
 * Created by 赵英辉 on 2018/5/18.
 */

public class QianYueJieSuanEntity {

    /**
     * type : 0
     * id : 357
     * orderno : SDD201805180001
     * linkname : GodMan
     * linkphone : 17663080550
     * sendtime : 2018-05-18 09:46:00
     * money : 20
     * ispay : 0
     * createtime : 2018-05-11 09:43:19
     * saddress : 山东省济南市历城区港沟街道济南综合保税区(港兴一路)
     * eaddress : 山东省济南市历城区港沟街道济南药谷济南综合保税区(港兴一路)
     */

    private String type;
    private int id;
    private String orderno;
    private String linkname;
    private String linkphone;
    private String sendtime;
    private double money;
    private int ispay;
    private String createtime;
    private String saddress;
    private String eaddress;
    private String uuid;
    private boolean line=false;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isLine() {
        return line;
    }

    public void setLine(boolean line) {
        this.line = line;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getLinkname() {
        return linkname;
    }

    public void setLinkname(String linkname) {
        this.linkname = linkname;
    }

    public String getLinkphone() {
        return linkphone;
    }

    public void setLinkphone(String linkphone) {
        this.linkphone = linkphone;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getIspay() {
        return ispay;
    }

    public void setIspay(int ispay) {
        this.ispay = ispay;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress;
    }

    public String getEaddress() {
        return eaddress;
    }

    public void setEaddress(String eaddress) {
        this.eaddress = eaddress;
    }
}
