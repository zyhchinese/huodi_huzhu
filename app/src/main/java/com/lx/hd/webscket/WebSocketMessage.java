package com.lx.hd.webscket;

/**
 * Created by JiangJie on 2017/2/14.
 */

public class WebSocketMessage {

    private String type = "register";//注册session固定值
    private String data;//传uid

    public WebSocketMessage() {
    }

    public WebSocketMessage(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
