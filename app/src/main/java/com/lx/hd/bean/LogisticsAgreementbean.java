package com.lx.hd.bean;

import java.io.Serializable;

/**
 * 物流协议实体类
 */

public class LogisticsAgreementbean implements Serializable {
    private String id;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
