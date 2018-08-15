package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/7/12.
 */

public class ErShouCheShouYeEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"toppic":[{"advicename":"","picname":"timg (5).jpg","folder":"advicepicImg/","autoname":"609aeba3c4c5410d9c363f25ebe84bfd.jpg","id":28,"type":4},{"advicename":"","picname":"timg (7).jpg","folder":"advicepicImg/","autoname":"c89962384ae74b46bb9d114a3a161f91.jpg","id":29,"type":4}],"provinceList":[{"areaid":1,"areaname":"浙江省","parentid":0},{"areaid":2,"areaname":"北京市","parentid":0},{"areaid":3,"areaname":"上海市","parentid":0},{"areaid":4,"areaname":"天津市","parentid":0},{"areaid":5,"areaname":"重庆市","parentid":0},{"areaid":6,"areaname":"河北省","parentid":0},{"areaid":7,"areaname":"山西省","parentid":0},{"areaid":8,"areaname":"内蒙古自治区","parentid":0},{"areaid":9,"areaname":"辽宁省","parentid":0},{"areaid":10,"areaname":"吉林省","parentid":0},{"areaid":11,"areaname":"黑龙江省","parentid":0},{"areaid":12,"areaname":"江苏省","parentid":0},{"areaid":13,"areaname":"安徽省","parentid":0},{"areaid":14,"areaname":"福建省","parentid":0},{"areaid":15,"areaname":"江西省","parentid":0},{"areaid":16,"areaname":"山东省","parentid":0},{"areaid":17,"areaname":"河南省","parentid":0},{"areaid":18,"areaname":"湖北省","parentid":0},{"areaid":19,"areaname":"湖南省","parentid":0},{"areaid":20,"areaname":"广东省","parentid":0},{"areaid":21,"areaname":"广西壮族自治区","parentid":0},{"areaid":22,"areaname":"海南省","parentid":0},{"areaid":23,"areaname":"四川省","parentid":0},{"areaid":24,"areaname":"贵州省","parentid":0},{"areaid":25,"areaname":"云南省","parentid":0},{"areaid":26,"areaname":"西藏自治区","parentid":0},{"areaid":27,"areaname":"陕西省","parentid":0},{"areaid":28,"areaname":"甘肃省","parentid":0},{"areaid":29,"areaname":"青海省","parentid":0},{"areaid":30,"areaname":"宁夏回族自治区","parentid":0},{"areaid":31,"areaname":"新疆维吾尔自治区","parentid":0},{"areaid":32,"areaname":"台湾省","parentid":0},{"areaid":33,"areaname":"香港特别行政区","parentid":0},{"areaid":34,"areaname":"澳门特别行政区","parentid":0}],"carbrandList":[{"brandname":"1","picname":"banner04.jpg","autoname":"963b2fa0f443454ca41abba42c3f6b22.jpg","folder":"carbrandImg/","note":"","ishot":0,"id":31,"uuid":"33506777-abff-4d62-a5a0-f6456f8d6e69"},{"brandname":"22","picname":"1127.png","autoname":"04a616c6be384cf88459f9cc85eed7ee.png","folder":"carbrandImg/","note":"","ishot":0,"id":30,"uuid":"44fdb929-4038-4690-837c-ee850d1ca4dd"},{"brandname":"111","picname":"timg.jpg","autoname":"151b6af8753a4ecbbcb5678c917d93d6.jpg","folder":"carbrandImg/","note":"","ishot":1,"id":29,"uuid":"66651884-1d6f-46d3-a814-4aa5e4bb3c8d"},{"brandname":"啊啊啊啊啊","picname":"1127.png","autoname":"ec1416762ee048f799a7e2a4a0a5e5e1.png","folder":"carbrandImg/","note":"","ishot":1,"id":28,"uuid":"dc314c5e-849f-4bc7-9723-4da2fb5ca1b2"},{"brandname":"本田","picname":"fw4.png","autoname":"04ff6c0a8a4b4ee191478d401a83c4b3.png","folder":"carbrandImg/","note":"","ishot":1,"id":27,"uuid":"7c4b3051-e0de-4d23-b457-460848ac3b8a"},{"brandname":"奥迪","picname":"标致.png","autoname":"6f13a513b7e346a38d6369f767b58313.png","folder":"carbrandImg/","note":"","ishot":1,"id":26,"uuid":"bad30883-7cdb-46f9-8cc2-a61a39b953d3"},{"brandname":"兰博基尼","picname":"标致.png","autoname":"554272990d49466ab99e19aaaea553da.png","folder":"carbrandImg/","note":"","ishot":1,"id":25,"uuid":"772f89db-9aeb-4634-b03c-b889c593c871"},{"brandname":"沃尔沃","picname":"标致.png","autoname":"dd07ca25bcf0446386779084b02f53c6.png","folder":"carbrandImg/","note":"","ishot":1,"id":24,"uuid":"6abc8c96-af45-4dd6-870d-e34c285fef73"},{"brandname":"捷豹","picname":"标致.png","autoname":"e9d10a6570b84f1d8d1be3682b1dccc8.png","folder":"carbrandImg/","note":"","ishot":1,"id":23,"uuid":"c9c4fe75-346b-4334-a87d-0f6066936bac"},{"brandname":"标志","picname":"标致.png","autoname":"a2a4b83934d64cb69d2c0b6bc0a67812.png","folder":"carbrandImg/","note":"","ishot":1,"id":22,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"保时捷","picname":"未标题-1.png","autoname":"aa1347876f914ee6ba592be5b437f899.png","folder":"carbrandImg/","note":"","ishot":1,"id":21,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"名爵","picname":"组-8.png","autoname":"71e66a65542b4fe2b4c6b6d07cc3d1a4.png","folder":"carbrandImg/","note":"","ishot":1,"id":20,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"GMC","picname":"未标题-3.png","autoname":"4b31aec27feb4fa78c15a58826bc8482.png","folder":"carbrandImg/","note":"","ishot":1,"id":19,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"宝马","picname":"未标题-4.png","autoname":"d0416ba3f3734d0ba255ec67671533b1.png","folder":"carbrandImg/","note":"","ishot":1,"id":18,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"别克","picname":"未标题-4.png","autoname":"46feb0d22c6b476b9d66cef553d174f5.png","folder":"carbrandImg/","note":"","ishot":1,"id":17,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"福特","picname":"未标题-6.png","autoname":"4783e30f3ad0494eb660af344a9e575d.png","folder":"carbrandImg/","note":"","ishot":1,"id":16,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"大众","picname":"未标题-8.png","autoname":"54c96f2cb08a4dcfbafb2adf1ca2d6a1.png","folder":"carbrandImg/","note":"","ishot":1,"id":15,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"一汽大众","picname":"未标题-8.png","autoname":"3274b701b9164a7ea43a34f793717802.png","folder":"carbrandImg/","note":"","ishot":1,"id":14,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"上海大众","picname":"未标题-8.png","autoname":"77155231a8414d9f8dd1d7dce1bbbaf6.png","folder":"carbrandImg/","note":"","ishot":1,"id":13,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"吉普","picname":"未标题-8.png","autoname":"e4c9b0be13da4548bdaf4a907b33390d.png","folder":"carbrandImg/","note":"","ishot":1,"id":12,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"悍马","picname":"未标题-8.png","autoname":"47e14cdaa5a14bcf893761020db7eb3d.png","folder":"carbrandImg/","note":"","ishot":1,"id":10,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"罗密欧","picname":"未标题-8.png","autoname":"c04feb087eb94d46af9db34a632b61aa.png","folder":"carbrandImg/","note":"","ishot":1,"id":9,"uuid":"0c23da70-699c-42d2-90dc-4af2e2bdf10b"},{"brandname":"玛莎拉蒂","picname":"未标题-8.png","autoname":"e479d91a20b94b068196c1875c99e1d3.png","folder":"carbrandImg/","note":"","ishot":1,"id":7,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"奥迪","picname":"未标题-8.png","autoname":"63cb380369d747cb9492e86026354e80.png","folder":"carbrandImg/","note":"","ishot":1,"id":2,"uuid":"9663f0e7-273c-4bd8-baf0-9111d00a1295"}],"cartypeList":[{"id":1,"cartypename":"平板"},{"id":2,"cartypename":"高栏"},{"id":3,"cartypename":"厢式"},{"id":4,"cartypename":"高低板"},{"id":5,"cartypename":"保温"},{"id":6,"cartypename":"冷藏"},{"id":7,"cartypename":"危险品"},{"id":8,"cartypename":"自卸"},{"id":9,"cartypename":"中卡"},{"id":10,"cartypename":"面包"},{"id":11,"cartypename":"棉被车"},{"id":12,"cartypename":"爬梯车"}],"secondhandcarList":[{"id":1,"title":"二手昂克赛拉","car_type_id":123,"car_type_name":"紧凑级家用","car_brand_id":123,"car_brand_name":"马自达","model_number":"1.5自然吸气","price":"13万","watch_car_add":"济南药谷9楼","registered_year":"2017-10-20","kilometre":"5000km","contacts":"张三丰","contact_number":"15165155555","province_id":0,"province":"山东省","city_id":0,"city":"济南市","county_id":0,"county":"历城区","describe":"这车我开了半年准备出手","create_user":99,"create_time":"2018-07-12 12:22:20","state":1,"retain1":0,"retain2":"","region":"山东省济南市历城区","uuid":"123123123","erscarpic":""},{"id":2,"title":"售二手飞度","car_type_id":123,"car_type_name":"小型家用车","car_brand_id":123,"car_brand_name":"本田","model_number":"1.5地球梦","price":"9万","watch_car_add":"历下区会展路","registered_year":"2016-11-06","kilometre":"20000km","contacts":"舒淇","contact_number":"15165155554","province_id":0,"province":"山东省","city_id":0,"city":"济南市","county_id":0,"county":"历城区","describe":"开了两年半准备出手","create_user":99,"create_time":"2018-07-12 12:22:20","state":1,"retain1":0,"retain2":"","region":"山东省济南市历城区","uuid":"123123124","erscarpic":""}]}]
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
        private List<ToppicBean> toppic;
        private List<ProvinceListBean> provinceList;
        private List<CarbrandListBean> carbrandList;
        private List<CartypeListBean> cartypeList;
        private List<SecondhandcarListBean> secondhandcarList;

        public List<ToppicBean> getToppic() {
            return toppic;
        }

        public void setToppic(List<ToppicBean> toppic) {
            this.toppic = toppic;
        }

        public List<ProvinceListBean> getProvinceList() {
            return provinceList;
        }

        public void setProvinceList(List<ProvinceListBean> provinceList) {
            this.provinceList = provinceList;
        }

        public List<CarbrandListBean> getCarbrandList() {
            return carbrandList;
        }

        public void setCarbrandList(List<CarbrandListBean> carbrandList) {
            this.carbrandList = carbrandList;
        }

        public List<CartypeListBean> getCartypeList() {
            return cartypeList;
        }

        public void setCartypeList(List<CartypeListBean> cartypeList) {
            this.cartypeList = cartypeList;
        }

        public List<SecondhandcarListBean> getSecondhandcarList() {
            return secondhandcarList;
        }

        public void setSecondhandcarList(List<SecondhandcarListBean> secondhandcarList) {
            this.secondhandcarList = secondhandcarList;
        }

        public static class ToppicBean {
            /**
             * advicename :
             * picname : timg (5).jpg
             * folder : advicepicImg/
             * autoname : 609aeba3c4c5410d9c363f25ebe84bfd.jpg
             * id : 28
             * type : 4
             */

            private String advicename;
            private String picname;
            private String folder;
            private String autoname;
            private int id;
            private int type;

            public String getAdvicename() {
                return advicename;
            }

            public void setAdvicename(String advicename) {
                this.advicename = advicename;
            }

            public String getPicname() {
                return picname;
            }

            public void setPicname(String picname) {
                this.picname = picname;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class ProvinceListBean {
            /**
             * areaid : 1
             * areaname : 浙江省
             * parentid : 0
             */

            private int areaid;
            private String areaname;
            private int parentid;

            public int getAreaid() {
                return areaid;
            }

            public void setAreaid(int areaid) {
                this.areaid = areaid;
            }

            public String getAreaname() {
                return areaname;
            }

            public void setAreaname(String areaname) {
                this.areaname = areaname;
            }

            public int getParentid() {
                return parentid;
            }

            public void setParentid(int parentid) {
                this.parentid = parentid;
            }
        }

        public static class CarbrandListBean {
            /**
             * brandname : 1
             * picname : banner04.jpg
             * autoname : 963b2fa0f443454ca41abba42c3f6b22.jpg
             * folder : carbrandImg/
             * note :
             * ishot : 0
             * id : 31
             * uuid : 33506777-abff-4d62-a5a0-f6456f8d6e69
             */

            private String brandname;
            private String picname;
            private String autoname;
            private String folder;
            private String note;
            private int ishot;
            private int id;
            private String uuid;
            private boolean isOk;

            public boolean isOk() {
                return isOk;
            }

            public void setOk(boolean ok) {
                isOk = ok;
            }

            public String getBrandname() {
                return brandname;
            }

            public void setBrandname(String brandname) {
                this.brandname = brandname;
            }

            public String getPicname() {
                return picname;
            }

            public void setPicname(String picname) {
                this.picname = picname;
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

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public int getIshot() {
                return ishot;
            }

            public void setIshot(int ishot) {
                this.ishot = ishot;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }

        public static class CartypeListBean {
            /**
             * id : 1
             * cartypename : 平板
             */

            private int id;
            private String cartypename;
            private boolean isOk;

            public boolean isOk() {
                return isOk;
            }

            public void setOk(boolean ok) {
                isOk = ok;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCartypename() {
                return cartypename;
            }

            public void setCartypename(String cartypename) {
                this.cartypename = cartypename;
            }
        }

        public static class SecondhandcarListBean {
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
