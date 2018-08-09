package com.zxtech.mt.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/11/2.
 */
public class MtJhaOrder {
    private String id;

    private String work_type;

    private String source_id;

    private String worker_id;

    private String elevator_code;

    private java.sql.Date jha_date;

    private String work_description;

    private String fpa_other;

    private String control_other;

    private String remark;

    private String enable_flag;

    private String create_user;

    private java.sql.Timestamp create_timestamp;

    private String last_update_user;

    private java.sql.Timestamp last_update_timestamp;

    private String last_update_remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getElevator_code() {
        return elevator_code;
    }

    public void setElevator_code(String elevator_code) {
        this.elevator_code = elevator_code;
    }

    public Date getJha_date() {
        return jha_date;
    }

    public void setJha_date(Date jha_date) {
        this.jha_date = jha_date;
    }

    public String getWork_description() {
        return work_description;
    }

    public void setWork_description(String work_description) {
        this.work_description = work_description;
    }

    public String getFpa_other() {
        return fpa_other;
    }

    public void setFpa_other(String fpa_other) {
        this.fpa_other = fpa_other;
    }

    public String getControl_other() {
        return control_other;
    }

    public void setControl_other(String control_other) {
        this.control_other = control_other;
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

    public Timestamp getCreate_timestamp() {
        return create_timestamp;
    }

    public void setCreate_timestamp(Timestamp create_timestamp) {
        this.create_timestamp = create_timestamp;
    }

    public String getLast_update_user() {
        return last_update_user;
    }

    public void setLast_update_user(String last_update_user) {
        this.last_update_user = last_update_user;
    }

    public Timestamp getLast_update_timestamp() {
        return last_update_timestamp;
    }

    public void setLast_update_timestamp(Timestamp last_update_timestamp) {
        this.last_update_timestamp = last_update_timestamp;
    }

    public String getLast_update_remark() {
        return last_update_remark;
    }

    public void setLast_update_remark(String last_update_remark) {
        this.last_update_remark = last_update_remark;
    }
}
