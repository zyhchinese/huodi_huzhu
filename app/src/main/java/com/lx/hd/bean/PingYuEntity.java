package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/6/21.
 */

public class PingYuEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"id":1,"content":"人很帅","usenumber":3},{"id":2,"content":"工作认真仔细","usenumber":1}]
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
         * id : 1
         * content : 人很帅
         * usenumber : 3
         */

        private int id;
        private String content;
        private int usenumber;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUsenumber() {
            return usenumber;
        }

        public void setUsenumber(int usenumber) {
            this.usenumber = usenumber;
        }
    }
}
