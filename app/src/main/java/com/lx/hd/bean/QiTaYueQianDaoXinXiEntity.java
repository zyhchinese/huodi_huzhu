package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/7/26.
 */

public class QiTaYueQianDaoXinXiEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"id":11,"user_id":114,"sum_count":1,"show_count":1,"create_time":"2018-07-26 11:56:29"},{"id":9,"user_id":114,"sum_count":3,"show_count":3,"create_time":"2018-07-24 11:54:58"},{"id":8,"user_id":114,"sum_count":2,"show_count":2,"create_time":"2018-07-23 11:52:47"},{"id":7,"user_id":114,"sum_count":1,"show_count":1,"create_time":"2018-07-22 11:51:44"}]
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
         * id : 11
         * user_id : 114
         * sum_count : 1
         * show_count : 1
         * create_time : 2018-07-26 11:56:29
         */

        private int id;
        private int user_id;
        private int sum_count;
        private int show_count;
        private String create_time;

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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
