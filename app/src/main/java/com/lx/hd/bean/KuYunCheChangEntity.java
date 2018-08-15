package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/7/27.
 */

public class KuYunCheChangEntity {


    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"id":3,"lengthname":2},{"id":1,"lengthname":2.2},{"id":4,"lengthname":3.33}]
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
         * id : 3
         * lengthname : 2
         */

        private int id;
        private String lengthname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLengthname() {
            return lengthname;
        }

        public void setLengthname(String lengthname) {
            this.lengthname = lengthname;
        }
    }
}
