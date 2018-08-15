package com.lx.hd.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/31.
 */

public class VersionBean implements Serializable{
   private String app_name;
    private String app_url;
    private String app_version;
    private String project;
    private String app_necessary;
    private String app_desc;
    private String info;

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getApp_necessary() {
        return app_necessary;
    }

    public void setApp_necessary(String app_necessary) {
        this.app_necessary = app_necessary;
    }

    public String getApp_desc() {
        return app_desc;
    }

    public void setApp_desc(String app_desc) {
        this.app_desc = app_desc;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
