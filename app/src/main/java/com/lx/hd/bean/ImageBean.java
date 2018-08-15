package com.lx.hd.bean;

import java.io.Serializable;

public class ImageBean implements Serializable{
    private long iid;
    private String image_name;
    private long image_size;
    private String image_url;
    private String image_width;
    private String image_height;
    private String image_type;

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public long getIid() {
        return iid;
    }

    public void setIid(long iid) {
        this.iid = iid;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public long getImage_size() {
        return image_size;
    }

    public void setImage_size(long image_size) {
        this.image_size = image_size;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_width() {
        return image_width;
    }

    public void setImage_width(String image_width) {
        this.image_width = image_width;
    }

    public String getImage_height() {
        return image_height;
    }

    public void setImage_height(String image_height) {
        this.image_height = image_height;
    }
}
