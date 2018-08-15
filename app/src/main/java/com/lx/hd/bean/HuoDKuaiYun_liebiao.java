package com.lx.hd.bean;

import java.util.List;

/**
 * Created by admin on 2018/3/12.
 */

public class HuoDKuaiYun_liebiao {


    /**
     * total : 5
     * page_number : 1
     * rows : [{"id":102,"siji_money":1.6,"orderno":"FP201803190003","contactname":"zhangxiao","contactphone":"15069019010","shipmenttime":"2018-03-21 00:00:00","createtime":"2018-03-19 16:00:03","startaddress":"山东省济南市历城区港沟街道奥林逸城 ","endaddress":"山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷 ","settheprice":2,"cartypenames":"厢式 危险品","cargotypenames":"石材","weight":0,"volume":2,"sprovince":"山东省","scity":"济南市","scounty":"历城区","eprovince":"山东省","ecity":"济南市","ecounty":"历城区","autoname":"030839e19a1e4b44b5480c210f357c4b.png","folder":"customerImages"},{"id":101,"siji_money":80,"orderno":"FP201803190002","contactname":"whl_123","contactphone":"18615207831","shipmenttime":"2018-03-19 13:00:00","createtime":"2018-03-19 09:45:23","startaddress":"山东省济南市历城区港沟街道汉峪金融商务中心","endaddress":"北京市东城区东华门街道北京市人民政府","settheprice":100,"cartypenames":"平板","cargotypenames":"蔬菜","weight":1,"volume":0,"sprovince":"山东省","scity":"济南市","scounty":"历城区","eprovince":"北京市","ecity":"北京市","ecounty":"东城区","autoname":"","folder":""},{"id":98,"siji_money":80,"orderno":"FP201803170002","contactname":"文斯莫克","contactphone":"15165155672","shipmenttime":"2018-03-20 00:00:00","createtime":"2018-03-17 12:07:01","startaddress":"山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷 ","endaddress":"山东省济南市历下区舜贤街东城逸家1期 ","settheprice":100,"cartypenames":"冷藏 危险品","cargotypenames":"粮食","weight":123,"volume":5,"sprovince":"山东省","scity":"济南市","scounty":"历城区","eprovince":"山东省","ecity":"济南市","ecounty":"历城区","autoname":"","folder":""},{"id":97,"siji_money":4.8,"orderno":"FP201803170001","contactname":"zhangxiao","contactphone":"15069019010","shipmenttime":"2018-03-19 00:00:00","createtime":"2018-03-17 09:57:00","startaddress":"山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷 ","endaddress":"山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷 ","settheprice":6,"cartypenames":"厢式 危险品","cargotypenames":"水果","weight":4,"volume":0,"sprovince":"山东省","scity":"济南市","scounty":"历城区","eprovince":"山东省","ecity":"济南市","ecounty":"历城区","autoname":"030839e19a1e4b44b5480c210f357c4b.png","folder":"customerImages"},{"id":96,"siji_money":160,"orderno":"FP201803160002","contactname":"zhangxiao","contactphone":"15069019010","shipmenttime":"2018-03-18 00:00:00","createtime":"2018-03-16 17:54:48","startaddress":"山东省济南市历城区港沟街道舜华南路2666号汉峪金融商务中心 ","endaddress":"山东省济南市历城区港沟街道舜华南路汉峪金谷 ","settheprice":200,"cartypenames":"厢式 冷藏","cargotypenames":"煤炭","weight":0,"volume":11,"sprovince":"山东省","scity":"济南市","scounty":"历城区","eprovince":"山东省","ecity":"济南市","ecounty":"历城区","autoname":"030839e19a1e4b44b5480c210f357c4b.png","folder":"customerImages"}]
     */

    private String total;
    private String page_number;
    private List<RowsBean> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage_number() {
        return page_number;
    }

    public void setPage_number(String page_number) {
        this.page_number = page_number;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 102
         * siji_money : 1.6
         * orderno : FP201803190003
         * contactname : zhangxiao
         * contactphone : 15069019010
         * shipmenttime : 2018-03-21 00:00:00
         * createtime : 2018-03-19 16:00:03
         * startaddress : 山东省济南市历城区港沟街道奥林逸城
         * endaddress : 山东省济南市历城区港沟街道嘉塑美食公园汉峪金谷
         * settheprice : 2
         * cartypenames : 厢式 危险品
         * cargotypenames : 石材
         * weight : 0
         * volume : 2
         * sprovince : 山东省
         * scity : 济南市
         * scounty : 历城区
         * eprovince : 山东省
         * ecity : 济南市
         * ecounty : 历城区
         * autoname : 030839e19a1e4b44b5480c210f357c4b.png
         * folder : customerImages
         */

        private String id;
        private String siji_money;
        private String orderno;
        private String contactname;
        private String contactphone;
        private String shipmenttime;
        private String createtime;
        private String startaddress;
        private String endaddress;
        private String settheprice;
        private String cartypenames;
        private String cargotypenames;
        private String weight;
        private String volume;
        private String sprovince;
        private String scity;
        private String scounty;
        private String eprovince;
        private String ecity;
        private String ecounty;
        private String autoname;
        private String folder;
        private String cust_num;
        private String cust_star;
        private String beizhu;
        private String ton_weight;
        private String use_car_type;
        private String lengthname;

        public String getUse_car_type() {
            return use_car_type;
        }

        public void setUse_car_type(String use_car_type) {
            this.use_car_type = use_car_type;
        }

        public String getLengthname() {
            return lengthname;
        }

        public void setLengthname(String lengthname) {
            this.lengthname = lengthname;
        }

        public String getTon_weight() {
            return ton_weight;
        }

        public void setTon_weight(String ton_weight) {
            this.ton_weight = ton_weight;
        }

        public String getBeizhu() {
            return beizhu;
        }

        public void setBeizhu(String beizhu) {
            this.beizhu = beizhu;
        }

        public String getCust_num() {
            return cust_num;
        }

        public void setCust_num(String cust_num) {
            this.cust_num = cust_num;
        }

        public String getCust_star() {
            return cust_star;
        }

        public void setCust_star(String cust_star) {
            this.cust_star = cust_star;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSiji_money() {
            return siji_money;
        }

        public void setSiji_money(String siji_money) {
            this.siji_money = siji_money;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
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

        public String getShipmenttime() {
            return shipmenttime;
        }

        public void setShipmenttime(String shipmenttime) {
            this.shipmenttime = shipmenttime;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getStartaddress() {
            return startaddress;
        }

        public void setStartaddress(String startaddress) {
            this.startaddress = startaddress;
        }

        public String getEndaddress() {
            return endaddress;
        }

        public void setEndaddress(String endaddress) {
            this.endaddress = endaddress;
        }

        public String getSettheprice() {
            return settheprice;
        }

        public void setSettheprice(String settheprice) {
            this.settheprice = settheprice;
        }

        public String getCartypenames() {
            return cartypenames;
        }

        public void setCartypenames(String cartypenames) {
            this.cartypenames = cartypenames;
        }

        public String getCargotypenames() {
            return cargotypenames;
        }

        public void setCargotypenames(String cargotypenames) {
            this.cargotypenames = cargotypenames;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
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

        public String getAutoname() {
            return autoname;
        }

        public void setAutoname(String autoname) {
            this.autoname = autoname;
        }

        public String getFolder() {
            return folder;
        }

        public void setFolder(String folder) {
            this.folder = folder;
        }
    }
}
