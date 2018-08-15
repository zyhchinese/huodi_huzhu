package com.lx.hd.bean;

/**
 * Created by TB on 2017/10/26.
 */

public class GoodsBean {
    private int id;
    private boolean isok = false;

    private String proname;
    private int proid;
    private String prono;

    private String specification;

    private double price;
    private int type;
    private String unit;

    private int protypeid;

    private String protypename;

    private int isvalid;

    private String note;

    private String uuid;

    private String autoname;

    private String folder;

    private String picname;

    private String productdes;

    private String color;
    private int count;
    private String fitcar;
    private double price_jf;

    public double getPrice_jf() {
        return price_jf;
    }

    public void setPrice_jf(double price_jf) {
        this.price_jf = price_jf;
    }

    public boolean isok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public GoodsBean() {
        super();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public GoodsBean(int id, String proname, String prono, String specification, double price, String unit, int protypeid, String protypename, int isvalid, String note, String uuid, String autoname, String folder, String picname, String productdes, String color, String fitcar) {
        this.id = id;
        this.proname = proname;
        this.prono = prono;
        this.specification = specification;
        this.price = price;
        this.unit = unit;
        this.protypeid = protypeid;
        this.protypename = protypename;
        this.isvalid = isvalid;
        this.note = note;
        this.uuid = uuid;
        this.autoname = autoname;
        this.folder = folder;
        this.picname = picname;
        this.productdes = productdes;
        this.color = color;
        this.fitcar = fitcar;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProname() {
        return this.proname;
    }

    public void setProno(String prono) {
        this.prono = prono;
    }

    public String getProno() {
        return this.prono;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSpecification() {
        return this.specification;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setProtypeid(int protypeid) {
        this.protypeid = protypeid;
    }

    public int getProtypeid() {
        return this.protypeid;
    }

    public void setProtypename(String protypename) {
        this.protypename = protypename;
    }

    public String getProtypename() {
        return this.protypename;
    }

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }

    public int getIsvalid() {
        return this.isvalid;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setAutoname(String autoname) {
        this.autoname = autoname;
    }

    public String getAutoname() {
        return this.autoname;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public String getPicname() {
        return this.picname;
    }

    public void setProductdes(String productdes) {
        this.productdes = productdes;
    }

    public String getProductdes() {
        return this.productdes;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public void setFitcar(String fitcar) {
        this.fitcar = fitcar;
    }

    public String getFitcar() {
        return this.fitcar;
    }

}
