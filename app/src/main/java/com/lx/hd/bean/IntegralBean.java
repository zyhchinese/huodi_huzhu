package com.lx.hd.bean;

/**
 * Created by TB on 2017/9/1.
 * 积分列表bean
 */

public class IntegralBean {
    private String score; //积分
    private String scores;//总积分
    private String reason;//原因
    private String createtime; //时间
    private int type; //0减1增

    public IntegralBean() {
    }

    public IntegralBean(String score, String scores, String reason, String createtime, int type) {
        this.score = score;
        this.scores = scores;
        this.reason = reason;
        this.createtime = createtime;
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
