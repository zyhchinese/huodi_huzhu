package com.lx.hd.bean;

/**
 * Created by Administrator on 2017/10/26.
 */

public class MyReservation {
    private int id;

    private String unorderno;

    private int custid;

    private String custname;

    private String unname;

    private String unphone;

    private String createtime;

    private int proid;

    private String proname;

    private String specification;

    private int type;

    private String note;

    private int isvalid;

    private String username;

    private int userid;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setUnorderno(String unorderno){
        this.unorderno = unorderno;
    }
    public String getUnorderno(){
        return this.unorderno;
    }
    public void setCustid(int custid){
        this.custid = custid;
    }
    public int getCustid(){
        return this.custid;
    }
    public void setCustname(String custname){
        this.custname = custname;
    }
    public String getCustname(){
        return this.custname;
    }
    public void setUnname(String unname){
        this.unname = unname;
    }
    public String getUnname(){
        return this.unname;
    }
    public void setUnphone(String unphone){
        this.unphone = unphone;
    }
    public String getUnphone(){
        return this.unphone;
    }
    public void setCreatetime(String createtime){
        this.createtime = createtime;
    }
    public String getCreatetime(){
        return this.createtime;
    }
    public void setProid(int proid){
        this.proid = proid;
    }
    public int getProid(){
        return this.proid;
    }
    public void setProname(String proname){
        this.proname = proname;
    }
    public String getProname(){
        return this.proname;
    }
    public void setSpecification(String specification){
        this.specification = specification;
    }
    public String getSpecification(){
        return this.specification;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }
    public void setIsvalid(int isvalid){
        this.isvalid = isvalid;
    }
    public int getIsvalid(){
        return this.isvalid;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUserid(int userid){
        this.userid = userid;
    }
    public int getUserid(){
        return this.userid;
    }

}
