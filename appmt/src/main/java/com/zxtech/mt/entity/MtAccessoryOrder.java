package com.zxtech.mt.entity;

import java.io.Serializable;

/**
 * Created by Chw on 2016/7/25.
 */
public class MtAccessoryOrder implements Serializable{

    private static final long serialVersionUID = -8317176508730996129L;
    private String id;

    private String comp_id;

    private String emp_id;

    private String proj_id;

    private String rec_address;

    private String rec_phone;

    private String hope_rec_date;

    private String rec_person;

    private String total_sum;

    private String proj_name;

    private String create_timestamp;

    private String accessory_status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getProj_id() {
        return proj_id;
    }

    public void setProj_id(String proj_id) {
        this.proj_id = proj_id;
    }

    public String getRec_address() {
        return rec_address;
    }

    public void setRec_address(String rec_address) {
        this.rec_address = rec_address;
    }

    public String getRec_phone() {
        return rec_phone;
    }

    public void setRec_phone(String rec_phone) {
        this.rec_phone = rec_phone;
    }

    public String getHope_rec_date() {
        return hope_rec_date;
    }

    public void setHope_rec_date(String hope_rec_date) {
        this.hope_rec_date = hope_rec_date;
    }

    public String getRec_person() {
        return rec_person;
    }

    public void setRec_person(String rec_person) {
        this.rec_person = rec_person;
    }

    public String getTotal_sum() {
        return total_sum;
    }

    public void setTotal_sum(String total_sum) {
        this.total_sum = total_sum;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public String getCreate_timestamp() {
        return create_timestamp;
    }

    public void setCreate_timestamp(String create_timestamp) {
        this.create_timestamp = create_timestamp;
    }

    public String getAccessory_status() {
        return accessory_status;
    }

    public void setAccessory_status(String accessory_status) {
        this.accessory_status = accessory_status;
    }
}
