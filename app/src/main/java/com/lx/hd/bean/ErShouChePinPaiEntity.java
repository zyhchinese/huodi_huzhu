package com.lx.hd.bean;

import java.util.List;

/**
 * Created by 赵英辉 on 2018/7/19.
 */

public class ErShouChePinPaiEntity {

    /**
     * flag : 200
     * msg : 查询成功
     * response : [{"brandname":"1","picname":"banner04.jpg","autoname":"963b2fa0f443454ca41abba42c3f6b22.jpg","folder":"carbrandImg/","note":"","ishot":0,"id":31,"uuid":"33506777-abff-4d62-a5a0-f6456f8d6e69"},{"brandname":"22","picname":"1127.png","autoname":"04a616c6be384cf88459f9cc85eed7ee.png","folder":"carbrandImg/","note":"","ishot":0,"id":30,"uuid":"44fdb929-4038-4690-837c-ee850d1ca4dd"},{"brandname":"111","picname":"timg.jpg","autoname":"151b6af8753a4ecbbcb5678c917d93d6.jpg","folder":"carbrandImg/","note":"","ishot":1,"id":29,"uuid":"66651884-1d6f-46d3-a814-4aa5e4bb3c8d"},{"brandname":"长安","picname":"1127.png","autoname":"ec1416762ee048f799a7e2a4a0a5e5e1.png","folder":"carbrandImg/","note":"","ishot":1,"id":28,"uuid":"dc314c5e-849f-4bc7-9723-4da2fb5ca1b2"},{"brandname":"本田","picname":"fw4.png","autoname":"04ff6c0a8a4b4ee191478d401a83c4b3.png","folder":"carbrandImg/","note":"","ishot":1,"id":27,"uuid":"7c4b3051-e0de-4d23-b457-460848ac3b8a"},{"brandname":"奥迪","picname":"标致.png","autoname":"6f13a513b7e346a38d6369f767b58313.png","folder":"carbrandImg/","note":"","ishot":1,"id":26,"uuid":"bad30883-7cdb-46f9-8cc2-a61a39b953d3"},{"brandname":"兰博基尼","picname":"标致.png","autoname":"554272990d49466ab99e19aaaea553da.png","folder":"carbrandImg/","note":"","ishot":1,"id":25,"uuid":"772f89db-9aeb-4634-b03c-b889c593c871"},{"brandname":"沃尔沃","picname":"标致.png","autoname":"dd07ca25bcf0446386779084b02f53c6.png","folder":"carbrandImg/","note":"","ishot":1,"id":24,"uuid":"6abc8c96-af45-4dd6-870d-e34c285fef73"},{"brandname":"捷豹","picname":"标致.png","autoname":"e9d10a6570b84f1d8d1be3682b1dccc8.png","folder":"carbrandImg/","note":"","ishot":1,"id":23,"uuid":"c9c4fe75-346b-4334-a87d-0f6066936bac"},{"brandname":"标志","picname":"标致.png","autoname":"a2a4b83934d64cb69d2c0b6bc0a67812.png","folder":"carbrandImg/","note":"","ishot":1,"id":22,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"保时捷","picname":"未标题-1.png","autoname":"aa1347876f914ee6ba592be5b437f899.png","folder":"carbrandImg/","note":"","ishot":1,"id":21,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"名爵","picname":"组-8.png","autoname":"71e66a65542b4fe2b4c6b6d07cc3d1a4.png","folder":"carbrandImg/","note":"","ishot":1,"id":20,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"GMC","picname":"未标题-3.png","autoname":"4b31aec27feb4fa78c15a58826bc8482.png","folder":"carbrandImg/","note":"","ishot":1,"id":19,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"宝马","picname":"未标题-4.png","autoname":"d0416ba3f3734d0ba255ec67671533b1.png","folder":"carbrandImg/","note":"","ishot":1,"id":18,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"别克","picname":"未标题-4.png","autoname":"46feb0d22c6b476b9d66cef553d174f5.png","folder":"carbrandImg/","note":"","ishot":1,"id":17,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"福特","picname":"未标题-6.png","autoname":"4783e30f3ad0494eb660af344a9e575d.png","folder":"carbrandImg/","note":"","ishot":1,"id":16,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"大众","picname":"未标题-8.png","autoname":"54c96f2cb08a4dcfbafb2adf1ca2d6a1.png","folder":"carbrandImg/","note":"","ishot":1,"id":15,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"一汽大众","picname":"未标题-8.png","autoname":"3274b701b9164a7ea43a34f793717802.png","folder":"carbrandImg/","note":"","ishot":1,"id":14,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"上海大众","picname":"未标题-8.png","autoname":"77155231a8414d9f8dd1d7dce1bbbaf6.png","folder":"carbrandImg/","note":"","ishot":1,"id":13,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"吉普","picname":"未标题-8.png","autoname":"e4c9b0be13da4548bdaf4a907b33390d.png","folder":"carbrandImg/","note":"","ishot":1,"id":12,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"悍马","picname":"未标题-8.png","autoname":"47e14cdaa5a14bcf893761020db7eb3d.png","folder":"carbrandImg/","note":"","ishot":1,"id":10,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"罗密欧","picname":"未标题-8.png","autoname":"c04feb087eb94d46af9db34a632b61aa.png","folder":"carbrandImg/","note":"","ishot":1,"id":9,"uuid":"0c23da70-699c-42d2-90dc-4af2e2bdf10b"},{"brandname":"玛莎拉蒂","picname":"未标题-8.png","autoname":"e479d91a20b94b068196c1875c99e1d3.png","folder":"carbrandImg/","note":"","ishot":1,"id":7,"uuid":"7eecc2da-b429-4813-9e81-d3cfcf4d9d05"},{"brandname":"奥迪","picname":"未标题-8.png","autoname":"63cb380369d747cb9492e86026354e80.png","folder":"carbrandImg/","note":"","ishot":1,"id":2,"uuid":"9663f0e7-273c-4bd8-baf0-9111d00a1295"}]
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
        private boolean type;

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
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
}
