package com.lx.hd.bean;

/**
 * Created by Administrator on 2017/10/20.
 */

public class ShoppingEntity {
    private int id;

    private int totalcount;

    private double totalmoney;

    private String orderno;

    private String createtime;

    private String productdes;

    private String proname;

    private String specification;

    private double price;

    private int count;

    private double money;

    private String color;

    private int proid;

    private String folder;

    private String autoname;

    private double totaljifen;

    private double price_jf;

    public double getPrice_jf() {
        return price_jf;
    }

    public void setPrice_jf(double price_jf) {
        this.price_jf = price_jf;
    }

    public double getTotaljifen() {
        return totaljifen;
    }

    public void setTotaljifen(double totaljifen) {
        this.totaljifen = totaljifen;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setTotalcount(int totalcount){
        this.totalcount = totalcount;
    }
    public int getTotalcount(){
        return this.totalcount;
    }
    public void setTotalmoney(int totalmoney){
        this.totalmoney = totalmoney;
    }
    public double getTotalmoney(){
        return this.totalmoney;
    }
    public void setOrderno(String orderno){
        this.orderno = orderno;
    }
    public String getOrderno(){
        return this.orderno;
    }
    public void setCreatetime(String createtime){
        this.createtime = createtime;
    }
    public String getCreatetime(){
        return this.createtime;
    }
    public void setProductdes(String productdes){
        this.productdes = productdes;
    }
    public String getProductdes(){
        return this.productdes;
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
    public void setPrice(int price){
        this.price = price;
    }
    public double getPrice(){
        return this.price;
    }
    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setMoney(int money){
        this.money = money;
    }
    public double getMoney(){
        return this.money;
    }
    public void setColor(String color){
        this.color = color;
    }
    public String getColor(){
        return this.color;
    }
    public void setProid(int proid){
        this.proid = proid;
    }
    public int getProid(){
        return this.proid;
    }
    public void setFolder(String folder){
        this.folder = folder;
    }
    public String getFolder(){
        return this.folder;
    }
    public void setAutoname(String autoname){
        this.autoname = autoname;
    }
    public String getAutoname(){
        return this.autoname;
    }
}
