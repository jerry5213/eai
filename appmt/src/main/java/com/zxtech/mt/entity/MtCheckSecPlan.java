package com.zxtech.mt.entity;

import java.io.Serializable;

/**
 * Created by Chw on 2016/7/19.
 */
public class MtCheckSecPlan implements Serializable{


    private static final long serialVersionUID = -5816610273654962991L;
    private String id;

    private String comp_id;

    private String check_emp_id;

    private String check_status;

    private String check_province;

    private String check_city;

    private String site_name;

    private String manager_name;

    private String worker_name;

    private Integer worker_count;

    private String plan_date;

    private String real_date;

    private String work_type;

    private String check_object;

    private String product_type;

    private String check_type;

    private String org_name;

    private String contract_code;

    private String org_manager_name;

    private String org_worker_name;

    private Integer org_worker_count;

    private String notify_flag;

    private String check_result;

    private String remark;

    private String enable_flag;

    private String create_user;

    private String create_timestamp;

    private String last_update_user;

    private String last_update_timestamp;

    private String last_update_remark;

    private String comp_name;

    private String comp_address;

    private String score;

    private String check_type_name;

    private String check_object_name;

    private String product_type_name;

    private String work_sec_type;

    private String worker_id;



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

    public String getCheck_emp_id() {
        return check_emp_id;
    }

    public void setCheck_emp_id(String check_emp_id) {
        this.check_emp_id = check_emp_id;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public String getCheck_province() {
        return check_province;
    }

    public void setCheck_province(String check_province) {
        this.check_province = check_province;
    }

    public String getCheck_city() {
        return check_city;
    }

    public void setCheck_city(String check_city) {
        this.check_city = check_city;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public Integer getWorker_count() {
        return worker_count;
    }

    public void setWorker_count(Integer worker_count) {
        this.worker_count = worker_count;
    }

    public String getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(String plan_date) {
        this.plan_date = plan_date;
    }

    public String getReal_date() {
        return real_date;
    }

    public void setReal_date(String real_date) {
        this.real_date = real_date;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getCheck_object() {
        return check_object;
    }

    public void setCheck_object(String check_object) {
        this.check_object = check_object;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getCheck_type() {
        return check_type;
    }

    public void setCheck_type(String check_type) {
        this.check_type = check_type;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    public String getOrg_manager_name() {
        return org_manager_name;
    }

    public void setOrg_manager_name(String org_manager_name) {
        this.org_manager_name = org_manager_name;
    }

    public String getOrg_worker_name() {
        return org_worker_name;
    }

    public void setOrg_worker_name(String org_worker_name) {
        this.org_worker_name = org_worker_name;
    }

    public Integer getOrg_worker_count() {
        return org_worker_count;
    }

    public void setOrg_worker_count(Integer org_worker_count) {
        this.org_worker_count = org_worker_count;
    }

    public String getNotify_flag() {
        return notify_flag;
    }

    public void setNotify_flag(String notify_flag) {
        this.notify_flag = notify_flag;
    }

    public String getCheck_result() {
        return check_result;
    }

    public void setCheck_result(String check_result) {
        this.check_result = check_result;
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

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getComp_address() {
        return comp_address;
    }

    public void setComp_address(String comp_address) {
        this.comp_address = comp_address;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCheck_object_name() {
        return check_object_name;
    }

    public void setCheck_object_name(String check_object_name) {
        this.check_object_name = check_object_name;
    }

    public String getCheck_type_name() {
        return check_type_name;
    }

    public void setCheck_type_name(String check_type_name) {
        this.check_type_name = check_type_name;
    }

    public String getProduct_type_name() {
        return product_type_name;
    }

    public void setProduct_type_name(String product_type_name) {
        this.product_type_name = product_type_name;
    }

    public String getWork_sec_type() {
        return work_sec_type;
    }

    public void setWork_sec_type(String work_sec_type) {
        this.work_sec_type = work_sec_type;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }
}
