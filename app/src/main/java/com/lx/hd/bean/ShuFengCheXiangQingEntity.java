package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/8/23.
 */

public class ShuFengCheXiangQingEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"freerideDet":{"id":13,"tripno":"TRN201808230001","custid":119,"slongitude":"117.000923","slatitude":"36.675807","sprovince":"山东省","scity":"济南市","scounty":"天桥区","saddress":"山东省济南市天桥区","elongitude":"116.999206","elatitude":"36.60804","eprovince":"山东省","ecity":"济南市","ecounty":"市中区","eaddress":"山东省济南市市中区","waytocitys":"山东省济南市天桥区,山东省济南市历下区,山东省济南市市中区","distance":"8.54","totalvehicle":55,"availablevehicle":33,"departuretime":{"date":25,"day":6,"hours":14,"minutes":40,"month":7,"nanos":0,"seconds":0,"time":1535179200000,"timezoneOffset":-480,"year":118},"contactname":"欧阳锋","contactphone":"15165155672","reserved1":"","reserved2":"","reserved3":0,"reserved4":0,"createtime":{"date":23,"day":4,"hours":14,"minutes":43,"month":7,"nanos":0,"seconds":36,"time":1535006616000,"timezoneOffset":-480,"year":118},"state":1,"uuid":"e009fe04-b52a-4e00-a1dc-87b780e35280","staendcitys":"山东省&济南市&天桥区-山东省#济南市#天桥区,山东省&济南市&天桥区-山东省#济南市#历下区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&天桥区-山东省#济南市#历下区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&历下区-山东省#济南市#市中区,山东省&济南市&历下区-山东省#济南市#市中区,山东省&济南市&市中区-山东省#济南市#市中区,","waytocitysshow":"济南市天桥区,济南市历下区,济南市市中区","deptime":"2018-08-25 14:40:00","cretime":"2018-08-23 14:43:36"}}]
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
         * freerideDet : {"id":13,"tripno":"TRN201808230001","custid":119,"slongitude":"117.000923","slatitude":"36.675807","sprovince":"山东省","scity":"济南市","scounty":"天桥区","saddress":"山东省济南市天桥区","elongitude":"116.999206","elatitude":"36.60804","eprovince":"山东省","ecity":"济南市","ecounty":"市中区","eaddress":"山东省济南市市中区","waytocitys":"山东省济南市天桥区,山东省济南市历下区,山东省济南市市中区","distance":"8.54","totalvehicle":55,"availablevehicle":33,"departuretime":{"date":25,"day":6,"hours":14,"minutes":40,"month":7,"nanos":0,"seconds":0,"time":1535179200000,"timezoneOffset":-480,"year":118},"contactname":"欧阳锋","contactphone":"15165155672","reserved1":"","reserved2":"","reserved3":0,"reserved4":0,"createtime":{"date":23,"day":4,"hours":14,"minutes":43,"month":7,"nanos":0,"seconds":36,"time":1535006616000,"timezoneOffset":-480,"year":118},"state":1,"uuid":"e009fe04-b52a-4e00-a1dc-87b780e35280","staendcitys":"山东省&济南市&天桥区-山东省#济南市#天桥区,山东省&济南市&天桥区-山东省#济南市#历下区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&天桥区-山东省#济南市#历下区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&历下区-山东省#济南市#市中区,山东省&济南市&历下区-山东省#济南市#市中区,山东省&济南市&市中区-山东省#济南市#市中区,","waytocitysshow":"济南市天桥区,济南市历下区,济南市市中区","deptime":"2018-08-25 14:40:00","cretime":"2018-08-23 14:43:36"}
         */

        private FreerideDetBean freerideDet;

        public FreerideDetBean getFreerideDet() {
            return freerideDet;
        }

        public void setFreerideDet(FreerideDetBean freerideDet) {
            this.freerideDet = freerideDet;
        }

        public static class FreerideDetBean {
            /**
             * id : 13
             * tripno : TRN201808230001
             * custid : 119
             * slongitude : 117.000923
             * slatitude : 36.675807
             * sprovince : 山东省
             * scity : 济南市
             * scounty : 天桥区
             * saddress : 山东省济南市天桥区
             * elongitude : 116.999206
             * elatitude : 36.60804
             * eprovince : 山东省
             * ecity : 济南市
             * ecounty : 市中区
             * eaddress : 山东省济南市市中区
             * waytocitys : 山东省济南市天桥区,山东省济南市历下区,山东省济南市市中区
             * distance : 8.54
             * totalvehicle : 55
             * availablevehicle : 33
             * departuretime : {"date":25,"day":6,"hours":14,"minutes":40,"month":7,"nanos":0,"seconds":0,"time":1535179200000,"timezoneOffset":-480,"year":118}
             * contactname : 欧阳锋
             * contactphone : 15165155672
             * reserved1 :
             * reserved2 :
             * reserved3 : 0
             * reserved4 : 0
             * createtime : {"date":23,"day":4,"hours":14,"minutes":43,"month":7,"nanos":0,"seconds":36,"time":1535006616000,"timezoneOffset":-480,"year":118}
             * state : 1
             * uuid : e009fe04-b52a-4e00-a1dc-87b780e35280
             * staendcitys : 山东省&济南市&天桥区-山东省#济南市#天桥区,山东省&济南市&天桥区-山东省#济南市#历下区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&天桥区-山东省#济南市#历下区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&天桥区-山东省#济南市#市中区,山东省&济南市&历下区-山东省#济南市#市中区,山东省&济南市&历下区-山东省#济南市#市中区,山东省&济南市&市中区-山东省#济南市#市中区,
             * waytocitysshow : 济南市天桥区,济南市历下区,济南市市中区
             * deptime : 2018-08-25 14:40:00
             * cretime : 2018-08-23 14:43:36
             */

            private int id;
            private String tripno;
            private int custid;
            private String slongitude;
            private String slatitude;
            private String sprovince;
            private String scity;
            private String scounty;
            private String saddress;
            private String elongitude;
            private String elatitude;
            private String eprovince;
            private String ecity;
            private String ecounty;
            private String eaddress;
            private String waytocitys;
            private String distance;
            private String totalvehicle;
            private String availablevehicle;
            private DeparturetimeBean departuretime;
            private String contactname;
            private String contactphone;
            private String reserved1;
            private String reserved2;
            private String reserved3;
            private String reserved4;
            private CreatetimeBean createtime;
            private int state;
            private String uuid;
            private String staendcitys;
            private String waytocitysshow;
            private String deptime;
            private String cretime;

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

            public int getCustid() {
                return custid;
            }

            public void setCustid(int custid) {
                this.custid = custid;
            }

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

            public String getWaytocitys() {
                return waytocitys;
            }

            public void setWaytocitys(String waytocitys) {
                this.waytocitys = waytocitys;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
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

            public DeparturetimeBean getDeparturetime() {
                return departuretime;
            }

            public void setDeparturetime(DeparturetimeBean departuretime) {
                this.departuretime = departuretime;
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

            public String getReserved1() {
                return reserved1;
            }

            public void setReserved1(String reserved1) {
                this.reserved1 = reserved1;
            }

            public String getReserved2() {
                return reserved2;
            }

            public void setReserved2(String reserved2) {
                this.reserved2 = reserved2;
            }

            public String getReserved3() {
                return reserved3;
            }

            public void setReserved3(String reserved3) {
                this.reserved3 = reserved3;
            }

            public String getReserved4() {
                return reserved4;
            }

            public void setReserved4(String reserved4) {
                this.reserved4 = reserved4;
            }

            public CreatetimeBean getCreatetime() {
                return createtime;
            }

            public void setCreatetime(CreatetimeBean createtime) {
                this.createtime = createtime;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getStaendcitys() {
                return staendcitys;
            }

            public void setStaendcitys(String staendcitys) {
                this.staendcitys = staendcitys;
            }

            public String getWaytocitysshow() {
                return waytocitysshow;
            }

            public void setWaytocitysshow(String waytocitysshow) {
                this.waytocitysshow = waytocitysshow;
            }

            public String getDeptime() {
                return deptime;
            }

            public void setDeptime(String deptime) {
                this.deptime = deptime;
            }

            public String getCretime() {
                return cretime;
            }

            public void setCretime(String cretime) {
                this.cretime = cretime;
            }

            public static class DeparturetimeBean {
                /**
                 * date : 25
                 * day : 6
                 * hours : 14
                 * minutes : 40
                 * month : 7
                 * nanos : 0
                 * seconds : 0
                 * time : 1535179200000
                 * timezoneOffset : -480
                 * year : 118
                 */

                private int date;
                private int day;
                private int hours;
                private int minutes;
                private int month;
                private int nanos;
                private int seconds;
                private long time;
                private int timezoneOffset;
                private int year;

                public int getDate() {
                    return date;
                }

                public void setDate(int date) {
                    this.date = date;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }

                public int getHours() {
                    return hours;
                }

                public void setHours(int hours) {
                    this.hours = hours;
                }

                public int getMinutes() {
                    return minutes;
                }

                public void setMinutes(int minutes) {
                    this.minutes = minutes;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public int getNanos() {
                    return nanos;
                }

                public void setNanos(int nanos) {
                    this.nanos = nanos;
                }

                public int getSeconds() {
                    return seconds;
                }

                public void setSeconds(int seconds) {
                    this.seconds = seconds;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getTimezoneOffset() {
                    return timezoneOffset;
                }

                public void setTimezoneOffset(int timezoneOffset) {
                    this.timezoneOffset = timezoneOffset;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }
            }

            public static class CreatetimeBean {
                /**
                 * date : 23
                 * day : 4
                 * hours : 14
                 * minutes : 43
                 * month : 7
                 * nanos : 0
                 * seconds : 36
                 * time : 1535006616000
                 * timezoneOffset : -480
                 * year : 118
                 */

                private int date;
                private int day;
                private int hours;
                private int minutes;
                private int month;
                private int nanos;
                private int seconds;
                private long time;
                private int timezoneOffset;
                private int year;

                public int getDate() {
                    return date;
                }

                public void setDate(int date) {
                    this.date = date;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }

                public int getHours() {
                    return hours;
                }

                public void setHours(int hours) {
                    this.hours = hours;
                }

                public int getMinutes() {
                    return minutes;
                }

                public void setMinutes(int minutes) {
                    this.minutes = minutes;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public int getNanos() {
                    return nanos;
                }

                public void setNanos(int nanos) {
                    this.nanos = nanos;
                }

                public int getSeconds() {
                    return seconds;
                }

                public void setSeconds(int seconds) {
                    this.seconds = seconds;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getTimezoneOffset() {
                    return timezoneOffset;
                }

                public void setTimezoneOffset(int timezoneOffset) {
                    this.timezoneOffset = timezoneOffset;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }
            }
        }
    }
}
