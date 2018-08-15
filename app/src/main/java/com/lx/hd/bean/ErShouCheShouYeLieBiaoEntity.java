package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/7/13.
 */

public class ErShouCheShouYeLieBiaoEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : {"total":2,"page_number":1,"rows":[{"id":1,"title":"二手昂克赛拉","car_type_id":123,"car_type_name":"紧凑级家用","car_brand_id":123,"car_brand_name":"马自达","model_number":"1.5自然吸气","price":"13万","watch_car_add":"济南药谷9楼","registered_year":"2017-10-20","kilometre":"5000km","contacts":"张三丰","contact_number":"15165155555","province_id":0,"province":"山东省","city_id":0,"city":"济南市","county_id":0,"county":"历城区","describe":"这车我开了半年准备出手","create_user":99,"create_time":"2018-07-12 12:22:20","state":1,"retain1":0,"retain2":"","region":"山东省济南市历城区","uuid":"123123123","erscarpic":""},{"id":2,"title":"售二手飞度","car_type_id":123,"car_type_name":"小型家用车","car_brand_id":123,"car_brand_name":"本田","model_number":"1.5地球梦","price":"9万","watch_car_add":"历下区会展路","registered_year":"2016-11-06","kilometre":"20000km","contacts":"舒淇","contact_number":"15165155554","province_id":0,"province":"山东省","city_id":0,"city":"济南市","county_id":0,"county":"历城区","describe":"开了两年半准备出手","create_user":99,"create_time":"2018-07-12 12:22:20","state":1,"retain1":0,"retain2":"","region":"山东省济南市历城区","uuid":"123123124","erscarpic":""}]}
     */

    private int flag;
    private String msg;
    private ResponseBean response;

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

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * total : 2
         * page_number : 1
         * rows : [{"id":1,"title":"二手昂克赛拉","car_type_id":123,"car_type_name":"紧凑级家用","car_brand_id":123,"car_brand_name":"马自达","model_number":"1.5自然吸气","price":"13万","watch_car_add":"济南药谷9楼","registered_year":"2017-10-20","kilometre":"5000km","contacts":"张三丰","contact_number":"15165155555","province_id":0,"province":"山东省","city_id":0,"city":"济南市","county_id":0,"county":"历城区","describe":"这车我开了半年准备出手","create_user":99,"create_time":"2018-07-12 12:22:20","state":1,"retain1":0,"retain2":"","region":"山东省济南市历城区","uuid":"123123123","erscarpic":""},{"id":2,"title":"售二手飞度","car_type_id":123,"car_type_name":"小型家用车","car_brand_id":123,"car_brand_name":"本田","model_number":"1.5地球梦","price":"9万","watch_car_add":"历下区会展路","registered_year":"2016-11-06","kilometre":"20000km","contacts":"舒淇","contact_number":"15165155554","province_id":0,"province":"山东省","city_id":0,"city":"济南市","county_id":0,"county":"历城区","describe":"开了两年半准备出手","create_user":99,"create_time":"2018-07-12 12:22:20","state":1,"retain1":0,"retain2":"","region":"山东省济南市历城区","uuid":"123123124","erscarpic":""}]
         */

        private int total;
        private int page_number;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPage_number() {
            return page_number;
        }

        public void setPage_number(int page_number) {
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
             * id : 1
             * title : 二手昂克赛拉
             * car_type_id : 123
             * car_type_name : 紧凑级家用
             * car_brand_id : 123
             * car_brand_name : 马自达
             * model_number : 1.5自然吸气
             * price : 13万
             * watch_car_add : 济南药谷9楼
             * registered_year : 2017-10-20
             * kilometre : 5000km
             * contacts : 张三丰
             * contact_number : 15165155555
             * province_id : 0
             * province : 山东省
             * city_id : 0
             * city : 济南市
             * county_id : 0
             * county : 历城区
             * describe : 这车我开了半年准备出手
             * create_user : 99
             * create_time : 2018-07-12 12:22:20
             * state : 1
             * retain1 : 0
             * retain2 :
             * region : 山东省济南市历城区
             * uuid : 123123123
             * erscarpic :
             */

            private int id;
            private String title;
            private int car_type_id;
            private String car_type_name;
            private int car_brand_id;
            private String car_brand_name;
            private String model_number;
            private String price;
            private String watch_car_add;
            private String registered_year;
            private String kilometre;
            private String contacts;
            private String contact_number;
            private int province_id;
            private String province;
            private int city_id;
            private String city;
            private int county_id;
            private String county;
            private String describe;
            private int create_user;
            private String create_time;
            private int state;
            private int retain1;
            private String retain2;
            private String region;
            private String uuid;
            private String erscarpic;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getCar_type_id() {
                return car_type_id;
            }

            public void setCar_type_id(int car_type_id) {
                this.car_type_id = car_type_id;
            }

            public String getCar_type_name() {
                return car_type_name;
            }

            public void setCar_type_name(String car_type_name) {
                this.car_type_name = car_type_name;
            }

            public int getCar_brand_id() {
                return car_brand_id;
            }

            public void setCar_brand_id(int car_brand_id) {
                this.car_brand_id = car_brand_id;
            }

            public String getCar_brand_name() {
                return car_brand_name;
            }

            public void setCar_brand_name(String car_brand_name) {
                this.car_brand_name = car_brand_name;
            }

            public String getModel_number() {
                return model_number;
            }

            public void setModel_number(String model_number) {
                this.model_number = model_number;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getWatch_car_add() {
                return watch_car_add;
            }

            public void setWatch_car_add(String watch_car_add) {
                this.watch_car_add = watch_car_add;
            }

            public String getRegistered_year() {
                return registered_year;
            }

            public void setRegistered_year(String registered_year) {
                this.registered_year = registered_year;
            }

            public String getKilometre() {
                return kilometre;
            }

            public void setKilometre(String kilometre) {
                this.kilometre = kilometre;
            }

            public String getContacts() {
                return contacts;
            }

            public void setContacts(String contacts) {
                this.contacts = contacts;
            }

            public String getContact_number() {
                return contact_number;
            }

            public void setContact_number(String contact_number) {
                this.contact_number = contact_number;
            }

            public int getProvince_id() {
                return province_id;
            }

            public void setProvince_id(int province_id) {
                this.province_id = province_id;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getCounty_id() {
                return county_id;
            }

            public void setCounty_id(int county_id) {
                this.county_id = county_id;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public int getCreate_user() {
                return create_user;
            }

            public void setCreate_user(int create_user) {
                this.create_user = create_user;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getRetain1() {
                return retain1;
            }

            public void setRetain1(int retain1) {
                this.retain1 = retain1;
            }

            public String getRetain2() {
                return retain2;
            }

            public void setRetain2(String retain2) {
                this.retain2 = retain2;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getErscarpic() {
                return erscarpic;
            }

            public void setErscarpic(String erscarpic) {
                this.erscarpic = erscarpic;
            }
        }
    }
}
