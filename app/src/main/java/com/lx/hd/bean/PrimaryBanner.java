package com.lx.hd.bean;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/8/25.
 */
@Entity
public class PrimaryBanner implements Serializable{

    private static final long serialVersionUID = -5661989043454524350L;
    private String activityname;
            private String createtime;
            private String publisher;
            private String publishid;
            private String picname;
            private String folder;
            private String autoname;
            private String note;
            private int id;
            private String uuid;

            @Generated(hash = 1915145216)
            public PrimaryBanner(String activityname, String createtime, String publisher,
                    String publishid, String picname, String folder, String autoname,
                    String note, int id, String uuid) {
                this.activityname = activityname;
                this.createtime = createtime;
                this.publisher = publisher;
                this.publishid = publishid;
                this.picname = picname;
                this.folder = folder;
                this.autoname = autoname;
                this.note = note;
                this.id = id;
                this.uuid = uuid;
            }

            @Generated(hash = 1648564820)
            public PrimaryBanner() {
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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
