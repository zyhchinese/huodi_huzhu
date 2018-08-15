package com.lx.hd.bean;

/**
 * Created by Administrator on 2017/10/26.
 */

public class BannerDetailsEntity {
    private String folder;

    private String autoname;

    private String specification;

    private String note;

    public void setFolder(String folder){
        this.folder = folder;
    }
    public String getFolder(){
        return this.folder;
    }
    public void setAutoname(String autoname){
        this.autoname = autoname;
    }
    public String getAutoname(){
        return this.autoname;
    }
    public void setSpecification(String specification){
        this.specification = specification;
    }
    public String getSpecification(){
        return this.specification;
    }
    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }

}
