package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/7/26.
 */

public class QianDaoXinXiEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"userScores":"2.00","setSigninDay":"30","signinList":[{"id":5,"user_id":119,"sum_count":1,"show_count":1,"create_time":{"date":26,"day":4,"hours":11,"minutes":39,"month":6,"nanos":0,"seconds":20,"time":1532576360000,"timezoneOffset":-480,"year":118},"todayflag":2},{"id":4,"user_id":119,"sum_count":1,"show_count":1,"create_time":{"date":26,"day":4,"hours":11,"minutes":38,"month":6,"nanos":0,"seconds":37,"time":1532576317000,"timezoneOffset":-480,"year":118},"todayflag":2}],"signinFlag":0,"continuitySignin":"0"}]
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
         * userScores : 2.00
         * setSigninDay : 30
         * signinList : [{"id":5,"user_id":119,"sum_count":1,"show_count":1,"create_time":{"date":26,"day":4,"hours":11,"minutes":39,"month":6,"nanos":0,"seconds":20,"time":1532576360000,"timezoneOffset":-480,"year":118},"todayflag":2},{"id":4,"user_id":119,"sum_count":1,"show_count":1,"create_time":{"date":26,"day":4,"hours":11,"minutes":38,"month":6,"nanos":0,"seconds":37,"time":1532576317000,"timezoneOffset":-480,"year":118},"todayflag":2}]
         * signinFlag : 0
         * continuitySignin : 0
         */

        private String userScores;
        private String setSigninDay;
        private int signinFlag;
        private String continuitySignin;
        private List<SigninListBean> signinList;

        public String getUserScores() {
            return userScores;
        }

        public void setUserScores(String userScores) {
            this.userScores = userScores;
        }

        public String getSetSigninDay() {
            return setSigninDay;
        }

        public void setSetSigninDay(String setSigninDay) {
            this.setSigninDay = setSigninDay;
        }

        public int getSigninFlag() {
            return signinFlag;
        }

        public void setSigninFlag(int signinFlag) {
            this.signinFlag = signinFlag;
        }

        public String getContinuitySignin() {
            return continuitySignin;
        }

        public void setContinuitySignin(String continuitySignin) {
            this.continuitySignin = continuitySignin;
        }

        public List<SigninListBean> getSigninList() {
            return signinList;
        }

        public void setSigninList(List<SigninListBean> signinList) {
            this.signinList = signinList;
        }

        public static class SigninListBean {
            /**
             * id : 5
             * user_id : 119
             * sum_count : 1
             * show_count : 1
             * create_time : {"date":26,"day":4,"hours":11,"minutes":39,"month":6,"nanos":0,"seconds":20,"time":1532576360000,"timezoneOffset":-480,"year":118}
             * todayflag : 2
             */

            private int id;
            private int user_id;
            private int sum_count;
            private int show_count;
            private CreateTimeBean create_time;
            private int todayflag;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getSum_count() {
                return sum_count;
            }

            public void setSum_count(int sum_count) {
                this.sum_count = sum_count;
            }

            public int getShow_count() {
                return show_count;
            }

            public void setShow_count(int show_count) {
                this.show_count = show_count;
            }

            public CreateTimeBean getCreate_time() {
                return create_time;
            }

            public void setCreate_time(CreateTimeBean create_time) {
                this.create_time = create_time;
            }

            public int getTodayflag() {
                return todayflag;
            }

            public void setTodayflag(int todayflag) {
                this.todayflag = todayflag;
            }

            public static class CreateTimeBean {
                /**
                 * date : 26
                 * day : 4
                 * hours : 11
                 * minutes : 39
                 * month : 6
                 * nanos : 0
                 * seconds : 20
                 * time : 1532576360000
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
