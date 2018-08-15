package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/6/22.
 */

public class LiShiXiaoXiEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"id":105,"user_id":99,"title":"订单接单","content":"您的省际/城际订单【FP201806220001】有司机接单了，请查看！","create_time":"2018-06-22 14:59:02","type":0,"mark":1,"issee":0},{"id":104,"user_id":99,"title":"订单接单","content":"您的省际/城际订单【FP201806220002】有司机接单了，请查看！","create_time":"2018-06-22 14:58:57","type":0,"mark":1,"issee":0},{"id":103,"user_id":99,"title":"订单接单","content":"您的省际/城际订单【FP201806220003】有司机接单了，请查看！","create_time":"2018-06-22 14:58:37","type":0,"mark":1,"issee":0}]
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
         * id : 105
         * user_id : 99
         * title : 订单接单
         * content : 您的省际/城际订单【FP201806220001】有司机接单了，请查看！
         * create_time : 2018-06-22 14:59:02
         * type : 0
         * mark : 1
         * issee : 0
         */

        private int id;
        private int user_id;
        private String title;
        private String content;
        private String create_time;
        private int type;
        private int mark;
        private int issee;
        private boolean line=false;

        public boolean isLine() {
            return line;
        }

        public void setLine(boolean line) {
            this.line = line;
        }

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public int getIssee() {
            return issee;
        }

        public void setIssee(int issee) {
            this.issee = issee;
        }
    }
}
