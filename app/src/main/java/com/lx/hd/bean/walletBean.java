package com.lx.hd.bean;

/**
 * Created by TB on 2017/8/28.
 */

public class walletBean {
    private int type; //订单类型 0充 1减
    private String money;  //价格
    private String ordertype;
    private String createtime;//时间
    private String tradeno;//交易单号
    private String chargeno;//充值单号
    private int paytype;//充值类型 0支付宝 1微信 2银行卡提现 3充电扣减

    public walletBean(int type, String money, String createtime, String tradeno, String chargeno, int paytype) {
        this.type = type;
        this.money = money;
        this.createtime = createtime;
        this.tradeno = tradeno;
        this.chargeno = chargeno;
        this.paytype = paytype;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTradeno() {
        return tradeno;
    }

    public void setTradeno(String tradeno) {
        this.tradeno = tradeno;
    }

    public String getChargeno() {
        return chargeno;
    }

    public void setChargeno(String chargeno) {
        this.chargeno = chargeno;
    }

    public int getPaytype() {
        return paytype;
    }

    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }

}
