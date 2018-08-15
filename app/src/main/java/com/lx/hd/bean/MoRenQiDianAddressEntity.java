package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/6/13.
 */

public class MoRenQiDianAddressEntity {


    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"custinfo":[{"id":61,"name":"送给","call":"17606401400","isdefault":0}],"saddlist":[{"id":62,"slongitude":"117.22173392772675","slatitude":"36.669316052970146","saddress":"山东省济南市历城区港沟街道济南药谷济南综合保税区(港兴一路)","sprovince":"山东省","scity":"济南市","scounty":"历城区","isdefault":0}],"eaddlist":[{"id":63,"elongitude":"117.20937967300415","elatitude":"36.66130386704049","eaddress":"山东省济南市历城区港沟街道G2京沪高速济南综合保税区(港兴一路)","eprovince":"山东省","ecity":"济南市","ecounty":"历城区","isdefault":0}]}]
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
        private List<CustinfoBean> custinfo;
        private List<SaddlistBean> saddlist;
        private List<EaddlistBean> eaddlist;

        public List<CustinfoBean> getCustinfo() {
            return custinfo;
        }

        public void setCustinfo(List<CustinfoBean> custinfo) {
            this.custinfo = custinfo;
        }

        public List<SaddlistBean> getSaddlist() {
            return saddlist;
        }

        public void setSaddlist(List<SaddlistBean> saddlist) {
            this.saddlist = saddlist;
        }

        public List<EaddlistBean> getEaddlist() {
            return eaddlist;
        }

        public void setEaddlist(List<EaddlistBean> eaddlist) {
            this.eaddlist = eaddlist;
        }

        public static class CustinfoBean {
            /**
             * id : 61
             * name : 送给
             * call : 17606401400
             * isdefault : 0
             */

            private int id;
            private String name;
            private String call;
            private int isdefault;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCall() {
                return call;
            }

            public void setCall(String call) {
                this.call = call;
            }

            public int getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(int isdefault) {
                this.isdefault = isdefault;
            }
        }

        public static class SaddlistBean {
            /**
             * id : 62
             * slongitude : 117.22173392772675
             * slatitude : 36.669316052970146
             * saddress : 山东省济南市历城区港沟街道济南药谷济南综合保税区(港兴一路)
             * sprovince : 山东省
             * scity : 济南市
             * scounty : 历城区
             * isdefault : 0
             */

            private int id;
            private String slongitude;
            private String slatitude;
            private String saddress;
            private String sprovince;
            private String scity;
            private String scounty;
            private int isdefault;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getSaddress() {
                return saddress;
            }

            public void setSaddress(String saddress) {
                this.saddress = saddress;
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

            public int getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(int isdefault) {
                this.isdefault = isdefault;
            }
        }

        public static class EaddlistBean {
            /**
             * id : 63
             * elongitude : 117.20937967300415
             * elatitude : 36.66130386704049
             * eaddress : 山东省济南市历城区港沟街道G2京沪高速济南综合保税区(港兴一路)
             * eprovince : 山东省
             * ecity : 济南市
             * ecounty : 历城区
             * isdefault : 0
             */

            private int id;
            private String elongitude;
            private String elatitude;
            private String eaddress;
            private String eprovince;
            private String ecity;
            private String ecounty;
            private int isdefault;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getEaddress() {
                return eaddress;
            }

            public void setEaddress(String eaddress) {
                this.eaddress = eaddress;
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

            public int getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(int isdefault) {
                this.isdefault = isdefault;
            }
        }
    }
}
