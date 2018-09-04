package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/8/23.
 */

public class ShuFengCheLieBiaoEntity{

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"id":10,"tripno":"TRIP201808230001","sprovince":"山东省","scity":"济南市","scounty":"历城区","saddress":"山东省济南市历城区港沟街道经十路599号九英里颢苑","eprovince":"山东省","ecity":"济南市","ecounty":"历城区","eaddress":"山东省济南市历城区孙村街道浪潮产业园","waytocitysshow":"济南市历城区,济南市历城区","totalvehicle":6,"availablevehicle":1,"departuretime":"2018-08-24 00:00:00","createtime":"2018-08-23 14:34:26","contactname":"奥迪","contactphone":"17663080550","folder":"customerImages","autoname":"df1942ac4d8c406bac5c6c820bbc137b.jpg","driver_num":5,"driver_star":5},{"id":14,"tripno":"TRN201808230002","sprovince":"山东省","scity":"济南市","scounty":"天桥区","saddress":"山东省济南市天桥区","eprovince":"山东省","ecity":"济南市","ecounty":"历下区","eaddress":"山东省济南市历下区","waytocitysshow":"济南市天桥区,济南市槐荫区","totalvehicle":100,"availablevehicle":20,"departuretime":"2018-08-27 14:45:00","createtime":"2018-08-23 14:45:26","contactname":"欧阳锋","contactphone":"15165155672","folder":"","autoname":"","driver_num":0,"driver_star":0},{"id":19,"tripno":"TRN201808230007","sprovince":"山东省","scity":"济南市","scounty":"历城区","saddress":"山东省济南市历城区","eprovince":"山东省","ecity":"济南市","ecounty":"历城区","eaddress":"山东省济南市历城区","waytocitysshow":"济南市历城区,济南市历城区,济南市历城区","totalvehicle":22222,"availablevehicle":555,"departuretime":"2022-02-26 17:00:00","createtime":"2018-08-23 17:00:20","contactname":"xjdj","contactphone":"17606401755","folder":"","autoname":"","driver_num":0,"driver_star":0}]
     */

    private int flag;
    private String msg;
    private List<ResponseBean> response;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * id : 10
         * tripno : TRIP201808230001
         * sprovince : 山东省
         * scity : 济南市
         * scounty : 历城区
         * saddress : 山东省济南市历城区港沟街道经十路599号九英里颢苑
         * eprovince : 山东省
         * ecity : 济南市
         * ecounty : 历城区
         * eaddress : 山东省济南市历城区孙村街道浪潮产业园
         * waytocitysshow : 济南市历城区,济南市历城区
         * totalvehicle : 6
         * availablevehicle : 1
         * departuretime : 2018-08-24 00:00:00
         * createtime : 2018-08-23 14:34:26
         * contactname : 奥迪
         * contactphone : 17663080550
         * folder : customerImages
         * autoname : df1942ac4d8c406bac5c6c820bbc137b.jpg
         * driver_num : 5
         * driver_star : 5
         */

        private int id;
        private String tripno;
        private String sprovince;
        private String scity;
        private String scounty;
        private String saddress;
        private String eprovince;
        private String ecity;
        private String ecounty;
        private String eaddress;
        private String waytocitysshow;
        private String totalvehicle;
        private String availablevehicle;
        private String departuretime;
        private String createtime;
        private String contactname;
        private String contactphone;
        private String folder;
        private String autoname;
        private String driver_num;
        private String driver_star;
        private String slongitude;
        private String slatitude;
        private String elongitude;
        private String elatitude;
        private String distance;

        public String getSlongitude() {
            return slongitude;
        }

        public void setSlongitude(String slongitude) {
            this.slongitude = slongitude;
        }

        public String getSlatitude() {
            return slatitude;
        }

        public void setSlatitude(String slatitude) {
            this.slatitude = slatitude;
        }

        public String getElongitude() {
            return elongitude;
        }

        public void setElongitude(String elongitude) {
            this.elongitude = elongitude;
        }

        public String getElatitude() {
            return elatitude;
        }

        public void setElatitude(String elatitude) {
            this.elatitude = elatitude;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTripno() {
            return tripno;
        }

        public void setTripno(String tripno) {
            this.tripno = tripno;
        }

        public String getSprovince() {
            return sprovince;
        }

        public void setSprovince(String sprovince) {
            this.sprovince = sprovince;
        }

        public String getScity() {
            return scity;
        }

        public void setScity(String scity) {
            this.scity = scity;
        }

        public String getScounty() {
            return scounty;
        }

        public void setScounty(String scounty) {
            this.scounty = scounty;
        }

        public String getSaddress() {
            return saddress;
        }

        public void setSaddress(String saddress) {
            this.saddress = saddress;
        }

        public String getEprovince() {
            return eprovince;
        }

        public void setEprovince(String eprovince) {
            this.eprovince = eprovince;
        }

        public String getEcity() {
            return ecity;
        }

        public void setEcity(String ecity) {
            this.ecity = ecity;
        }

        public String getEcounty() {
            return ecounty;
        }

        public void setEcounty(String ecounty) {
            this.ecounty = ecounty;
        }

        public String getEaddress() {
            return eaddress;
        }

        public void setEaddress(String eaddress) {
            this.eaddress = eaddress;
        }

        public String getWaytocitysshow() {
            return waytocitysshow;
        }

        public void setWaytocitysshow(String waytocitysshow) {
            this.waytocitysshow = waytocitysshow;
        }

        public String getTotalvehicle() {
            return totalvehicle;
        }

        public void setTotalvehicle(String totalvehicle) {
            this.totalvehicle = totalvehicle;
        }

        public String getAvailablevehicle() {
            return availablevehicle;
        }

        public void setAvailablevehicle(String availablevehicle) {
            this.availablevehicle = availablevehicle;
        }

        public String getDeparturetime() {
            return departuretime;
        }

        public void setDeparturetime(String departuretime) {
            this.departuretime = departuretime;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getContactname() {
            return contactname;
        }

        public void setContactname(String contactname) {
            this.contactname = contactname;
        }

        public String getContactphone() {
            return contactphone;
        }

        public void setContactphone(String contactphone) {
            this.contactphone = contactphone;
        }

        public String getFolder() {
            return folder;
        }

        public void setFolder(String folder) {
            this.folder = folder;
        }

        public String getAutoname() {
            return autoname;
        }

        public void setAutoname(String autoname) {
            this.autoname = autoname;
        }

        public String getDriver_num() {
            return driver_num;
        }

        public void setDriver_num(String driver_num) {
            this.driver_num = driver_num;
        }

        public String getDriver_star() {
            return driver_star;
        }

        public void setDriver_star(String driver_star) {
            this.driver_star = driver_star;
        }
    }
}
