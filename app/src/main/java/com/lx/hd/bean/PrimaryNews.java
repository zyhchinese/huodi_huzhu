package com.lx.hd.bean;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/8/25.
 */
@Entity
public class PrimaryNews implements Serializable{

    private static final long serialVersionUID = -6463724400806267109L;
    private String  title;
    private String  content;
    private String  picname;
    private String  folder;
    private String  autoname;
    private String  note;
    private String  createtime;
    private String  publisher;
    private String  publishid;
    private int  id;
    private String  uuid;

    @Generated(hash = 1598930936)
    public PrimaryNews(String title, String content, String picname, String folder,
            String autoname, String note, String createtime, String publisher,
            String publishid, int id, String uuid) {
        this.title = title;
        this.content = content;
        this.picname = picname;
        this.folder = folder;
        this.autoname = autoname;
        this.note = note;
        this.createtime = createtime;
        this.publisher = publisher;
        this.publishid = publishid;
        this.id = id;
        this.uuid = uuid;
    }

    @Generated(hash = 74665347)
    public PrimaryNews() {
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
