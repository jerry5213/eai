package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/7/22.
 */
public class MtStation {

    private String id;

    private String stat_name;

    private String stat_code;

    private String comp_id;

    private String stat_phone;

    private String stat_fax;

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

    public String getStat_name() {
        return stat_name;
    }

    public void setStat_name(String stat_name) {
        this.stat_name = stat_name;
    }

    public String getStat_code() {
        return stat_code;
    }

    public void setStat_code(String stat_code) {
        this.stat_code = stat_code;
    }

    public String getStat_phone() {
        return stat_phone;
    }

    public void setStat_phone(String stat_phone) {
        this.stat_phone = stat_phone;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getStat_fax() {
        return stat_fax;
    }

    public void setStat_fax(String stat_fax) {
        this.stat_fax = stat_fax;
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
