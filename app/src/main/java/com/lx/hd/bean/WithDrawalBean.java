package com.lx.hd.bean;

/**
 * Created by TB on 2017/9/3 0003.
 */

public class WithDrawalBean {
    private String balance;//余额
    private String custname;//申请人
    private String bankcard;//卡号
    private String note;//备注
    private String isvalid;

    public WithDrawalBean(String balance, String custname, String bankcard, String note,String isvalid) {
        this.balance = balance;
        this.custname = custname;
        this.bankcard = bankcard;
        this.note = note;
        this.isvalid = isvalid;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
