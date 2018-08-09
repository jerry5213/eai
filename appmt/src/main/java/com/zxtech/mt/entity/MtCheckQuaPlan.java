package com.zxtech.mt.entity;

import java.io.Serializable;

/**
 * Created by Chw on 2016/7/13.
 */
public class MtCheckQuaPlan implements Serializable{

    private static final long serialVersionUID = -8592009246941857744L;
    private String id;

    private String comp_id;

    private String elevator_id;

    private String proj_id;

    private String check_cmp_id;

    private String worker_name;

    private String status;

    private String score;

    private String remark;

    private String enable_flag;

    private String create_user;

    private String create_timestamp;

    private String last_update_user;

    private String last_update_timestamp;

    private String last_update_remark;

    private String elevator_code;

    private String proj_name;

    private String plan_date;

    private String real_date;

    private String category;


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

    public String getElevator_id() {
        return elevator_id;
    }

    public void setElevator_id(String elevator_id) {
        this.elevator_id = elevator_id;
    }

    public String getProj_id() {
        return proj_id;
    }

    public void setProj_id(String proj_id) {
        this.proj_id = proj_id;
    }

    public String getCheck_cmp_id() {
        return check_cmp_id;
    }

    public void setCheck_cmp_id(String check_cmp_id) {
        this.check_cmp_id = check_cmp_id;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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

    public String getElevator_code() {
        return elevator_code;
    }

    public void setElevator_code(String elevator_code) {
        this.elevator_code = elevator_code;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public String getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(String plan_date) {
        this.plan_date = plan_date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReal_date() {
        return real_date;
    }

    public void setReal_date(String real_date) {
        this.real_date = real_date;
    }
}
