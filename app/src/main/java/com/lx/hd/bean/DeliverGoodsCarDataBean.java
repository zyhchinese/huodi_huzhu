package com.lx.hd.bean;

import java.util.List;

/**
 * Created by TB on 2018/1/8 0008.
 */

public class DeliverGoodsCarDataBean {
    private List<Integer> id;
    private String car_load;
    private String car_volume;
    private String car_size;
    private String car_type;
    private String createtime;
    private String autoname;
    private String picname;
    private String folder;
    private String uuid;
    private int car_id;
    private String cityname;
    private String proname;
    private double starting_price;
    private double mileage_price;
    private double starting_mileage;

    public DeliverGoodsCarDataBean() {
        super();
    }

    public DeliverGoodsCarDataBean(List<Integer> id, String car_load, String car_volume, String car_size, String car_type, String createtime, String autoname, String picname, String folder, String uuid, int car_id, String cityname, String proname, double starting_price, double mileage_price, double starting_mileage) {
        this.id = id;
        this.car_load = car_load;
        this.car_volume = car_volume;
        this.car_size = car_size;
        this.car_type = car_type;
        this.createtime = createtime;
        this.autoname = autoname;
        this.picname = picname;
        this.folder = folder;
        this.uuid = uuid;
        this.car_id = car_id;
        this.cityname = cityname;
        this.proname = proname;
        this.starting_price = starting_price;
        this.mileage_price = mileage_price;
        this.starting_mileage = starting_mileage;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public List<Integer> getId() {
        return id;
    }

    public void setCar_load(String car_load) {
        this.car_load = car_load;
    }

    public String getCar_load() {
        return car_load;
    }

    public void setCar_volume(String car_volume) {
        this.car_volume = car_volume;
    }

    public String getCar_volume() {
        return car_volume;
    }

    public void setCar_size(String car_size) {
        this.car_size = car_size;
    }

    public String getCar_size() {
        return car_size;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setAutoname(String autoname) {
        this.autoname = autoname;
    }

    public String getAutoname() {
        return autoname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public String getPicname() {
        return picname;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProname() {
        return proname;
    }

    public void setStarting_price(double starting_price) {
        this.starting_price = starting_price;
    }

    public double getStarting_price() {
        return starting_price;
    }

    public void setMileage_price(int mileage_price) {
        this.mileage_price = mileage_price;
    }

    public double getMileage_price() {
        return mileage_price;
    }

    public void setStarting_mileage(int starting_mileage) {
        this.starting_mileage = starting_mileage;
    }

    public double getStarting_mileage() {
        return starting_mileage;
    }
}
