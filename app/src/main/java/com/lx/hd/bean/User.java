package com.lx.hd.bean;

/**
 * Created by Administrator on 2017/8/18.
 */

public class User {

    private int id;
    // 本地缓存多余信息
    private String cookie;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    private String custname;

    private String custphone;

    private double balance;

    private double scores;

    private int tickets;

    private int isvalid;

    private String createtime;

    private String password;

    private String note;

    private String qq;

    private String email;

    private String uuid;

    private String autoname;

    private String picname;

    private String folder;

    private String birthday;

    private String occupation;

    private String addressdetail;

    private String incomelevel;

    private String vehicle_information;

    private int sex;

    private String paypassword;

    private String wechat;

    private int count;
    private int isdriver;

    public int getIsdriver() {
        return isdriver;
    }

    public void setIsdriver(int isdriver) {
        this.isdriver = isdriver;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setCustname(String custname){
        this.custname = custname;
    }
    public String getCustname(){
        return this.custname;
    }
    public void setCustphone(String custphone){
        this.custphone = custphone;
    }
    public String getCustphone(){
        return this.custphone;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    public double getBalance(){
        return this.balance;
    }
    public void setScores(double scores){
        this.scores = scores;
    }
    public double getScores(){
        return this.scores;
    }
    public void setTickets(int tickets){
        this.tickets = tickets;
    }
    public int getTickets(){
        return this.tickets;
    }
    public void setIsvalid(int isvalid){
        this.isvalid = isvalid;
    }
    public int getIsvalid(){
        return this.isvalid;
    }
    public void setCreatetime(String createtime){
        this.createtime = createtime;
    }
    public String getCreatetime(){
        return this.createtime;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }
    public void setQq(String qq){
        this.qq = qq;
    }
    public String getQq(){
        return this.qq;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setUuid(String uuid){
        this.uuid = uuid;
    }
    public String getUuid(){
        return this.uuid;
    }
    public void setAutoname(String autoname){
        this.autoname = autoname;
    }
    public String getAutoname(){
        return this.autoname;
    }
    public void setPicname(String picname){
        this.picname = picname;
    }
    public String getPicname(){
        return this.picname;
    }
    public void setFolder(String folder){
        this.folder = folder;
    }
    public String getFolder(){
        return this.folder;
    }
    public void setBirthday(String birthday){
        this.birthday = birthday;
    }
    public String getBirthday(){
        return this.birthday;
    }
    public void setOccupation(String occupation){
        this.occupation = occupation;
    }
    public String getOccupation(){
        return this.occupation;
    }
    public void setAddressdetail(String addressdetail){
        this.addressdetail = addressdetail;
    }
    public String getAddressdetail(){
        return this.addressdetail;
    }
    public void setIncomelevel(String incomelevel){
        this.incomelevel = incomelevel;
    }
    public String getIncomelevel(){
        return this.incomelevel;
    }
    public void setVehicle_information(String vehicle_information){
        this.vehicle_information = vehicle_information;
    }
    public String getVehicle_information(){
        return this.vehicle_information;
    }
    public void setSex(int sex){
        this.sex = sex;
    }
    public int getSex(){
        return this.sex;
    }
    public void setPaypassword(String paypassword){
        this.paypassword = paypassword;
    }
    public String getPaypassword(){
        return this.paypassword;
    }
    public void setWechat(String wechat){
        this.wechat = wechat;
    }
    public String getWechat(){
        return this.wechat;
    }
    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
}
