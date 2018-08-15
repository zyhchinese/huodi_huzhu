package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/7/19.
 */

public class ErShouCheShouYeXiangQingEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"twoScarinfo":{"id":19,"title":"呵呵","car_type_id":3,"car_type_name":"厢式","car_brand_id":29,"car_brand_name":"111","model_number":"398","price":"18万","watch_car_add":"山东省济南市历城区港沟街道济南药谷济南综合保税区(港兴一路)","registered_year":"2014年2月1日","kilometre":"12万","contacts":"13222222222","contact_number":"13222222222","province_id":0,"province":"山东省","city_id":0,"city":"济南市","county_id":0,"county":"历城区","describe":"","create_user":119,"create_time":"2018-07-20 11:49:59","state":0,"retain1":0,"retain2":"","region":"山东省济南市历城区港沟街道济南药谷济南综合保税区(港兴一路)","uuid":"87dd8b91c9ef4981aee6","twoscarpic":"secondhandcarImages76237a689ba94541b26872438f3a8412.jpg"},"twoScarpics":["secondhandcarImages76237a689ba94541b26872438f3a8412.jpg","secondhandcarImages8f5ebb9493b847bf84759fb8115a0280.jpg","secondhandcarImages5918fabc9bd9471fb515915033e72276.jpg"]}]
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
         * twoScarinfo : {"id":19,"title":"呵呵","car_type_id":3,"car_type_name":"厢式","car_brand_id":29,"car_brand_name":"111","model_number":"398","price":"18万","watch_car_add":"山东省济南市历城区港沟街道济南药谷济南综合保税区(港兴一路)","registered_year":"2014年2月1日","kilometre":"12万","contacts":"13222222222","contact_number":"13222222222","province_id":0,"province":"山东省","city_id":0,"city":"济南市","county_id":0,"county":"历城区","describe":"","create_user":119,"create_time":"2018-07-20 11:49:59","state":0,"retain1":0,"retain2":"","region":"山东省济南市历城区港沟街道济南药谷济南综合保税区(港兴一路)","uuid":"87dd8b91c9ef4981aee6","twoscarpic":"secondhandcarImages76237a689ba94541b26872438f3a8412.jpg"}
         * twoScarpics : ["secondhandcarImages76237a689ba94541b26872438f3a8412.jpg","secondhandcarImages8f5ebb9493b847bf84759fb8115a0280.jpg","secondhandcarImages5918fabc9bd9471fb515915033e72276.jpg"]
         */

        private TwoScarinfoBean twoScarinfo;
        private List<String> twoScarpics;

        public TwoScarinfoBean getTwoScarinfo() {
            return twoScarinfo;
        }

        public void setTwoScarinfo(TwoScarinfoBean twoScarinfo) {
            this.twoScarinfo = twoScarinfo;
        }

        public List<String> getTwoScarpics() {
            return twoScarpics;
        }

        public void setTwoScarpics(List<String> twoScarpics) {
            this.twoScarpics = twoScarpics;
        }

        public static class TwoScarinfoBean {
            /**
             * id : 19
             * title : 呵呵
             * car_type_id : 3
             * car_type_name : 厢式
             * car_brand_id : 29
             * car_brand_name : 111
             * model_number : 398
             * price : 18万
             * watch_car_add : 山东省济南市历城区港沟街道济南药谷济南综合保税区(港兴一路)
             * registered_year : 2014年2月1日
             * kilometre : 12万
             * contacts : 13222222222
             * contact_number : 13222222222
             * province_id : 0
             * province : 山东省
             * city_id : 0
             * city : 济南市
             * county_id : 0
             * county : 历城区
             * describe :
             * create_user : 119
             * create_time : 2018-07-20 11:49:59
             * state : 0
             * retain1 : 0
             * retain2 :
             * region : 山东省济南市历城区港沟街道济南药谷济南综合保税区(港兴一路)
             * uuid : 87dd8b91c9ef4981aee6
             * twoscarpic : secondhandcarImages76237a689ba94541b26872438f3a8412.jpg
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
            private String twoscarpic;

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

            public String getTwoscarpic() {
                return twoscarpic;
            }

            public void setTwoscarpic(String twoscarpic) {
                this.twoscarpic = twoscarpic;
            }
        }
    }
}
