/**
 * 领取优惠券列表的实体
 */
package com.lx.hd.bean;

/**
 * Auto-generated: 2017-10-30 9:28:12
 *
 * @author TB
 * @website http://www.bejson.com/java2pojo/
 */
public class CouponCollection {

    private int id;
    private String cheap_name;
    private String cheap_descript;
    private int cheap_ange;
    private int cheap_type;
    private float cheap_equal_money;
    private int cheap_coupon_count;
    private int isvalid;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCheap_name(String cheap_name) {
        this.cheap_name = cheap_name;
    }

    public String getCheap_name() {
        return cheap_name;
    }

    public void setCheap_descript(String cheap_descript) {
        this.cheap_descript = cheap_descript;
    }

    public String getCheap_descript() {
        return cheap_descript;
    }

    public void setCheap_ange(int cheap_ange) {
        this.cheap_ange = cheap_ange;
    }

    public int getCheap_ange() {
        return cheap_ange;
    }

    public void setCheap_type(int cheap_type) {
        this.cheap_type = cheap_type;
    }

    public int getCheap_type() {
        return cheap_type;
    }

    public void setCheap_equal_money(float cheap_equal_money) {
        this.cheap_equal_money = cheap_equal_money;
    }

    public float getCheap_equal_money() {
        return cheap_equal_money;
    }

    public void setCheap_coupon_count(int cheap_coupon_count) {
        this.cheap_coupon_count = cheap_coupon_count;
    }

    public int getCheap_coupon_count() {
        return cheap_coupon_count;
    }

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }

    public int getIsvalid() {
        return isvalid;
    }

}