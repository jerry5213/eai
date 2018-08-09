package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/8/12.
 */
public class MtAccessoryOrderDetail {


    private String id;

    private String acc_order_id;

    private String elevator_id;

    private String acc_code;

    private String acc_name;

    private String acc_unit;

    private int acc_count;

    private float acc_real_price;

    private String acc_real_total;

    private String usage_type;

    private String usage_property;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcc_order_id() {
        return acc_order_id;
    }

    public void setAcc_order_id(String acc_order_id) {
        this.acc_order_id = acc_order_id;
    }

    public String getElevator_id() {
        return elevator_id;
    }

    public void setElevator_id(String elevator_id) {
        this.elevator_id = elevator_id;
    }

    public String getAcc_code() {
        return acc_code;
    }

    public void setAcc_code(String acc_code) {
        this.acc_code = acc_code;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public String getAcc_unit() {
        return acc_unit;
    }

    public void setAcc_unit(String acc_unit) {
        this.acc_unit = acc_unit;
    }

    public int getAcc_count() {
        return acc_count;
    }

    public void setAcc_count(int acc_count) {
        this.acc_count = acc_count;
    }

    public float getAcc_real_price() {
        return acc_real_price;
    }

    public void setAcc_real_price(float acc_real_price) {
        this.acc_real_price = acc_real_price;
    }

    public String getAcc_real_total() {
        return acc_real_total;
    }

    public void setAcc_real_total(String acc_real_total) {
        this.acc_real_total = acc_real_total;
    }

    public String getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(String usage_type) {
        this.usage_type = usage_type;
    }

    public String getUsage_property() {
        return usage_property;
    }

    public void setUsage_property(String usage_property) {
        this.usage_property = usage_property;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
