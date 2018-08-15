package com.lx.hd.bean;

import java.io.Serializable;

/**
 * 活动实体类
 * Created by tb on 2017/10/11.
 */

public class activebean implements Serializable {
    private static final long serialVersionUID = -6463724400806267109L;
    private String folder;
    private String autoname;
    private String note;
    private String id;
    private String uuid;
    private String publisher;
    private String activityname;
    private String createtime;
    private String publishid;
    private String picname;
    private String content;

    public activebean(String folder, String autoname, String note, String id, String uuid, String publisher, String activityname, String createtime, String publishid, String picname, String content) {
        this.folder = folder;
        this.autoname = autoname;
        this.note = note;
        this.id = id;
        this.uuid = uuid;
        this.publisher = publisher;
        this.activityname = activityname;
        this.createtime = createtime;
        this.publishid = publishid;
        this.picname = picname;
        this.content = content;
    }

    public activebean() {
        super();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getAutoname() {
        return autoname;
    }

    public void setAutoname(String autoname) {
        this.autoname = autoname;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getPublishid() {
        return publishid;
    }

    public void setPublishid(String publishid) {
        this.publishid = publishid;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }
}
