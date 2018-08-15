package com.lx.hd.bean;

import java.io.Serializable;
import java.util.List;
public class PageBean<T> implements Serializable {
    private List<T> rows;
    private String total;
    private int page_number;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPage_number() {
        return page_number;
    }

    public void setPage_number(int page_number) {
        this.page_number = page_number;
    }
}