package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/6/25.
 */

public class ChangJianWenTiEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"id":5,"type":0,"problem":"忘记登录密码","describe":"尊敬的用户，您好，现在货滴不支持密码登录，为了用户信息安全，只支持手机号+验证码登录。","retain1":0,"retain2":"","create_time":"2018-06-22 16:58:42","useful":0,"useless":0}]
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
         * id : 5
         * type : 0
         * problem : 忘记登录密码
         * describe : 尊敬的用户，您好，现在货滴不支持密码登录，为了用户信息安全，只支持手机号+验证码登录。
         * retain1 : 0
         * retain2 :
         * create_time : 2018-06-22 16:58:42
         * useful : 0
         * useless : 0
         */

        private int id;
        private int type;
        private String problem;
        private String describe;
        private int retain1;
        private String retain2;
        private String create_time;
        private int useful;
        private int useless;

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

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getUseful() {
            return useful;
        }

        public void setUseful(int useful) {
            this.useful = useful;
        }

        public int getUseless() {
            return useless;
        }

        public void setUseless(int useless) {
            this.useless = useless;
        }
    }
}
