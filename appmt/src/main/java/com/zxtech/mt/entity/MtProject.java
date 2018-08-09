package com.zxtech.mt.entity;

import java.io.Serializable;

/**
 * Created by Chw on 2016/7/22.
 */
public class MtProject implements Serializable {

    private String id;

    private String customer_id;

    private String proj_name;

    private String proj_address;

    private String proj_province;

    private String proj_city;

    private String cust_rel_id;

    private String emp_manager_id;

    private String emp_seller_id;

    private String remark;

    private String enable_flag;

    private String create_user;

    private String create_timestamp;

    private String last_update_user;

    private String last_update_timestamp;

    private String last_update_remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
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

    public String getProj_province() {
        return proj_province;
    }

    public void setProj_province(String proj_province) {
        this.proj_province = proj_province;
    }

    public String getProj_city() {
        return proj_city;
    }

    public void setProj_city(String proj_city) {
        this.proj_city = proj_city;
    }

    public String getCust_rel_id() {
        return cust_rel_id;
    }

    public void setCust_rel_id(String cust_rel_id) {
        this.cust_rel_id = cust_rel_id;
    }

    public String getEmp_manager_id() {
        return emp_manager_id;
    }

    public void setEmp_manager_id(String emp_manager_id) {
        this.emp_manager_id = emp_manager_id;
    }

    public String getEmp_seller_id() {
        return emp_seller_id;
    }

    public void setEmp_seller_id(String emp_seller_id) {
        this.emp_seller_id = emp_seller_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEnable_flag() {
        return enable_flag;
    }

    public void setEnable_flag(String enable_flag) {
        this.enable_flag = enable_flag;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getCreate_timestamp() {
        return create_timestamp;
    }

    public void setCreate_timestamp(String create_timestamp) {
        this.create_timestamp = create_timestamp;
    }

    public String getLast_update_user() {
        return last_update_user;
    }

    public void setLast_update_user(String last_update_user) {
        this.last_update_user = last_update_user;
    }

    public String getLast_update_timestamp() {
        return last_update_timestamp;
    }

    public void setLast_update_timestamp(String last_update_timestamp) {
        this.last_update_timestamp = last_update_timestamp;
    }

    public String getLast_update_remark() {
        return last_update_remark;
    }

    public void setLast_update_remark(String last_update_remark) {
        this.last_update_remark = last_update_remark;
    }
}
