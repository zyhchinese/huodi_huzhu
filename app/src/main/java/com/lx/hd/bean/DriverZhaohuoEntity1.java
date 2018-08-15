package com.lx.hd.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class DriverZhaohuoEntity1 {
    private int total;

    private int page_number;

    private List<Rows> rows ;

    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setPage_number(int page_number){
        this.page_number = page_number;
    }
    public int getPage_number(){
        return this.page_number;
    }
    public void setRows(List<Rows> rows){
        this.rows = rows;
    }
    public List<Rows> getRows(){
        return this.rows;
    }

    public class Rows {
        private int id;

        private double siji_money;

        private String owner_orderno;

        private String owner_link_name;

        private String owner_link_phone;

        private String owner_sendtime;

        private String owner_address;

        private String owner_send_address;

        private double longitude;

        private double latitude;

        private double owner_totalprice;

        private String car_type;

        private String autoname;

        private String folder;
        private double licheng;

        public double getLicheng() {
            return licheng;
        }

        public void setLicheng(double licheng) {
            this.licheng = licheng;
        }

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setSiji_money(double siji_money){
            this.siji_money = siji_money;
        }
        public double getSiji_money(){
            return this.siji_money;
        }
        public void setOwner_orderno(String owner_orderno){
            this.owner_orderno = owner_orderno;
        }
        public String getOwner_orderno(){
            return this.owner_orderno;
        }
        public void setOwner_link_name(String owner_link_name){
            this.owner_link_name = owner_link_name;
        }
        public String getOwner_link_name(){
            return this.owner_link_name;
        }
        public void setOwner_link_phone(String owner_link_phone){
            this.owner_link_phone = owner_link_phone;
        }
        public String getOwner_link_phone(){
            return this.owner_link_phone;
        }
        public void setOwner_sendtime(String owner_sendtime){
            this.owner_sendtime = owner_sendtime;
        }
        public String getOwner_sendtime(){
            return this.owner_sendtime;
        }
        public void setOwner_address(String owner_address){
            this.owner_address = owner_address;
        }
        public String getOwner_address(){
            return this.owner_address;
        }
        public void setOwner_send_address(String owner_send_address){
            this.owner_send_address = owner_send_address;
        }
        public String getOwner_send_address(){
            return this.owner_send_address;
        }
        public void setLongitude(double longitude){
            this.longitude = longitude;
        }
        public double getLongitude(){
            return this.longitude;
        }
        public void setLatitude(double latitude){
            this.latitude = latitude;
        }
        public double getLatitude(){
            return this.latitude;
        }
        public void setOwner_totalprice(double owner_totalprice){
            this.owner_totalprice = owner_totalprice;
        }
        public double getOwner_totalprice(){
            return this.owner_totalprice;
        }
        public void setCar_type(String car_type){
            this.car_type = car_type;
        }
        public String getCar_type(){
            return this.car_type;
        }
        public void setAutoname(String autoname){
            this.autoname = autoname;
        }
        public String getAutoname(){
            return this.autoname;
        }
        public void setFolder(String folder){
            this.folder = folder;
        }
        public String getFolder(){
            return this.folder;
        }

    }
}
