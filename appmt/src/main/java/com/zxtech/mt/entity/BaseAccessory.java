package com.zxtech.mt.entity;

import java.io.Serializable;

/**
 * Created by Chw on 2016/7/22.
 */
public class BaseAccessory implements Serializable{

    private String id;

    private String acc_name;

    private String acc_spec;

    private String acc_category;

    private String store_count;

    private String unit_name;

    private String pic_url_one;

    private String acc_code;

    private Integer acc_level;

    private String acc_id;

    private String child_flag;

    private String remark;
    private String sale_price;



    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public String getAcc_code() {
        return acc_code;
    }

    public void setAcc_code(String acc_code) {
        this.acc_code = acc_code;
    }

    public Integer getAcc_level() {
        return acc_level;
    }

    public void setAcc_level(Integer acc_level) {
        this.acc_level = acc_level;
    }

    public String getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(String acc_id) {
        this.acc_id = acc_id;
    }

    public String getChild_flag() {
        return child_flag;
    }

    public void setChild_flag(String child_flag) {
        this.child_flag = child_flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAcc_spec() {
        return acc_spec;
    }

    public void setAcc_spec(String acc_spec) {
        this.acc_spec = acc_spec;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcc_category() {
        return acc_category;
    }

    public void setAcc_category(String acc_category) {
        this.acc_category = acc_category;
    }

    public String getStore_count() {
        return store_count;
    }

    public void setStore_count(String store_count) {
        this.store_count = store_count;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getPic_url_one() {
        return pic_url_one;
    }

    public void setPic_url_one(String pic_url_one) {
        this.pic_url_one = pic_url_one;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }
}
