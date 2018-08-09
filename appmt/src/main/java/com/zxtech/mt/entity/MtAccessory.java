package com.zxtech.mt.entity;

import java.io.Serializable;

/**
 * Created by Chw on 2016/7/24.
 */
public class MtAccessory implements Serializable {

    private static final long serialVersionUID = 7606212173856495241L;
    private String id;

    private String acc_name;

    private String acc_code;

    private String elevator_code;

    private String device_code;

    private String company_stock;

    private String company_stock_hq;

    private String stock_type;

    private float sale_price;

    private float sale_lower_price;

    private String status;

    private String user_id;

    private float acc_real_price;

    private String discount;

    private String proj_id;

    private String proj_name;

    private String proj_address;

    private String emp_name;

    private String emp_phone_one;

    private String picture_url;

    private Integer acc_count = 1;

    private float acc_real_total;

    private String acc_type;

    private int selected;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public String getAcc_code() {
        return acc_code;
    }

    public void setAcc_code(String acc_code) {
        this.acc_code = acc_code;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getCompany_stock() {
        return company_stock;
    }

    public void setCompany_stock(String company_stock) {
        this.company_stock = company_stock;
    }

    public String getCompany_stock_hq() {
        return company_stock_hq;
    }

    public void setCompany_stock_hq(String company_stock_hq) {
        this.company_stock_hq = company_stock_hq;
    }

    public String getStock_type() {
        return stock_type;
    }

    public void setStock_type(String stock_type) {
        this.stock_type = stock_type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getProj_id() {
        return proj_id;
    }

    public void setProj_id(String proj_id) {
        this.proj_id = proj_id;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public String getProj_address() {
        return proj_address;
    }

    public void setProj_address(String proj_address) {
        this.proj_address = proj_address;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_phone_one() {
        return emp_phone_one;
    }

    public void setEmp_phone_one(String emp_phone_one) {
        this.emp_phone_one = emp_phone_one;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public float getSale_lower_price() {
        return sale_lower_price;
    }

    public void setSale_lower_price(float sale_lower_price) {
        this.sale_lower_price = sale_lower_price;
    }

    public float getAcc_real_price() {
        return acc_real_price;
    }

    public void setAcc_real_price(float acc_real_price) {
        this.acc_real_price = acc_real_price;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public Integer getAcc_count() {
        return acc_count;
    }

    public void setAcc_count(Integer acc_count) {
        this.acc_count = acc_count;
    }

    public float getAcc_real_total() {
        return acc_real_total;
    }

    public void setAcc_real_total(float acc_real_total) {
        this.acc_real_total = acc_real_total;
    }

    public String getAcc_type() {
        return acc_type;
    }

    public void setAcc_type(String acc_type) {
        this.acc_type = acc_type;
    }

    public String getElevator_code() {
        return elevator_code;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
