package com.lx.hd.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public class NewsBean {
    private String total;
    private String page_number;
    private List<PrimaryNews> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage_number() {
        return page_number;
    }

    public void setPage_number(String page_number) {
        this.page_number = page_number;
    }

    public List<PrimaryNews> getRows() {
        return rows;
    }

    public void setRows(List<PrimaryNews> rows) {
        this.rows = rows;
    }
}
