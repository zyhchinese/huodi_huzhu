package com.lx.hd.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 */

public class CheckSiJiRenZhengEntity {


    /**
     * flag : 200
     * msg : 您的实名认证申请审核通过
     * response : [{"info":{"id":3,"custname":"ghh","custcall":"17606401455","custidcard":"123456789123456789","status":1,"submittime":{"date":26,"day":2,"hours":15,"minutes":44,"month":5,"nanos":0,"seconds":29,"time":1529999069000,"timezoneOffset":-480,"year":118},"note":"测试通过。"},"imgs":[{"id":382,"driver_img_type":1,"autoname":"a415b15a105543a883a87554d9032663.jpg","folder":"ownercertificationimages"},{"id":383,"driver_img_type":2,"autoname":"bdc69af629034a46b7ca8e486d31756c.jpg","folder":"ownercertificationimages"},{"id":384,"driver_img_type":3,"autoname":"8aec46959a384f96a6bc7e8d1109a865.jpg","folder":"ownercertificationimages"}]}]
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
         * info : {"id":3,"custname":"ghh","custcall":"17606401455","custidcard":"123456789123456789","status":1,"submittime":{"date":26,"day":2,"hours":15,"minutes":44,"month":5,"nanos":0,"seconds":29,"time":1529999069000,"timezoneOffset":-480,"year":118},"note":"测试通过。"}
         * imgs : [{"id":382,"driver_img_type":1,"autoname":"a415b15a105543a883a87554d9032663.jpg","folder":"ownercertificationimages"},{"id":383,"driver_img_type":2,"autoname":"bdc69af629034a46b7ca8e486d31756c.jpg","folder":"ownercertificationimages"},{"id":384,"driver_img_type":3,"autoname":"8aec46959a384f96a6bc7e8d1109a865.jpg","folder":"ownercertificationimages"}]
         */

        private InfoBean info;
        private List<ImgsBean> imgs;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public static class InfoBean {
            /**
             * id : 3
             * custname : ghh
             * custcall : 17606401455
             * custidcard : 123456789123456789
             * status : 1
             * submittime : {"date":26,"day":2,"hours":15,"minutes":44,"month":5,"nanos":0,"seconds":29,"time":1529999069000,"timezoneOffset":-480,"year":118}
             * note : 测试通过。
             */

            private int id;
            private String custname;
            private String custcall;
            private String custidcard;
            private int status;
            private SubmittimeBean submittime;
            private String note;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCustname() {
                return custname;
            }

            public void setCustname(String custname) {
                this.custname = custname;
            }

            public String getCustcall() {
                return custcall;
            }

            public void setCustcall(String custcall) {
                this.custcall = custcall;
            }

            public String getCustidcard() {
                return custidcard;
            }

            public void setCustidcard(String custidcard) {
                this.custidcard = custidcard;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public SubmittimeBean getSubmittime() {
                return submittime;
            }

            public void setSubmittime(SubmittimeBean submittime) {
                this.submittime = submittime;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public static class SubmittimeBean {
                /**
                 * date : 26
                 * day : 2
                 * hours : 15
                 * minutes : 44
                 * month : 5
                 * nanos : 0
                 * seconds : 29
                 * time : 1529999069000
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

        public static class ImgsBean {
            /**
             * id : 382
             * driver_img_type : 1
             * autoname : a415b15a105543a883a87554d9032663.jpg
             * folder : ownercertificationimages
             */

            private int id;
            private int driver_img_type;
            private String autoname;
            private String folder;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDriver_img_type() {
                return driver_img_type;
            }

            public void setDriver_img_type(int driver_img_type) {
                this.driver_img_type = driver_img_type;
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
}
