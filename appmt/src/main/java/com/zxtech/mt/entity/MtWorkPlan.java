package com.zxtech.mt.entity;

import java.io.Serializable;

/**
 * Created by Chw on 2016/7/11.
 */
public class MtWorkPlan implements Serializable{


    private static final long serialVersionUID = 6518329288202465398L;
    private String id;

    private String elevator_id;

    private String line_id;

    private String emp_one_id;

    private String emp_two_id;

    private String work_type;

    private String status;

    private Integer xio_week;

    private String plan_date;

    private String real_date;

    private String sign_date;

    private String sign_pic;

    private String work_result;

    private String remark;

    private String enable_flag;

    private String create_user;

    private String create_timestamp;

    private String last_update_user;

    private String last_update_timestamp;

    private String last_update_remark;

    private String elevator_code;

    private String proj_name;

    private String proj_address;

    private String next_annual_date;

    private String work_type_name;

    private String other_name;

    private String plan_work_type;

    private String category;

    private String proj_id;

    private String order_pincipal;

    private Integer exists_jha;

    private String result_photograph;

    private String contract_code;

    private String elevator_brand;
    private String elevator_reg_number;

    private String mt_photo_url;

    private String elevator_type;
    private String elevator_load;
    private String elevator_speed;
    private int elevator_level;
    private int elevator_station;
    private int elevator_door;
    private String elevator_angle;
    private String elevator_high;
    private String elevator_step_width;
    private String elevator_relation_person;
    private String elevator_relation_phone;
    private String next_check_date;
    private String contract_type;
    private String elevator_effect_date;
    private String workers;
    private String item_version;
    private String elevator_category;

    private String property_sign_url;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getElevator_id() {
        return elevator_id;
    }

    public void setElevator_id(String elevator_id) {
        this.elevator_id = elevator_id;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getEmp_one_id() {
        return emp_one_id;
    }

    public void setEmp_one_id(String emp_one_id) {
        this.emp_one_id = emp_one_id;
    }

    public String getEmp_two_id() {
        return emp_two_id;
    }

    public void setEmp_two_id(String emp_two_id) {
        this.emp_two_id = emp_two_id;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getXio_week() {
        return xio_week;
    }

    public void setXio_week(Integer xio_week) {
        this.xio_week = xio_week;
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

    public String getSign_date() {
        return sign_date;
    }

    public void setSign_date(String sign_date) {
        this.sign_date = sign_date;
    }

    public String getSign_pic() {
        return sign_pic;
    }

    public void setSign_pic(String sign_pic) {
        this.sign_pic = sign_pic;
    }

    public String getWork_result() {
        return work_result;
    }

    public void setWork_result(String work_result) {
        this.work_result = work_result;
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

    public String getNext_annual_date() {
        return next_annual_date;
    }

    public void setNext_annual_date(String next_annual_date) {
        this.next_annual_date = next_annual_date;
    }

    public String getWork_type_name() {
        return work_type_name;
    }

    public void setWork_type_name(String work_type_name) {
        this.work_type_name = work_type_name;
    }

    public String getProj_address() {
        return proj_address;
    }

    public void setProj_address(String proj_address) {
        this.proj_address = proj_address;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getPlan_work_type() {
        return plan_work_type;
    }

    public void setPlan_work_type(String plan_work_type) {
        this.plan_work_type = plan_work_type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProj_id() {
        return proj_id;
    }

    public void setProj_id(String proj_id) {
        this.proj_id = proj_id;
    }

    public String getOrder_pincipal() {
        return order_pincipal;
    }

    public void setOrder_pincipal(String order_pincipal) {
        this.order_pincipal = order_pincipal;
    }

    public Integer getExists_jha() {
        return exists_jha;
    }

    public void setExists_jha(Integer exists_jha) {
        this.exists_jha = exists_jha;
    }

    public String getResult_photograph() {
        return result_photograph;
    }

    public void setResult_photograph(String result_photograph) {
        this.result_photograph = result_photograph;
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    public String getElevator_brand() {
        return elevator_brand;
    }

    public void setElevator_brand(String elevator_brand) {
        this.elevator_brand = elevator_brand;
    }

    public String getElevator_reg_number() {
        return elevator_reg_number;
    }

    public void setElevator_reg_number(String elevator_reg_number) {
        this.elevator_reg_number = elevator_reg_number;
    }

    public String getElevator_type() {
        return elevator_type;
    }

    public void setElevator_type(String elevator_type) {
        this.elevator_type = elevator_type;
    }

    public String getElevator_load() {
        return elevator_load;
    }

    public void setElevator_load(String elevator_load) {
        this.elevator_load = elevator_load;
    }

    public String getElevator_speed() {
        return elevator_speed;
    }

    public void setElevator_speed(String elevator_speed) {
        this.elevator_speed = elevator_speed;
    }

    public int getElevator_level() {
        return elevator_level;
    }

    public void setElevator_level(int elevator_level) {
        this.elevator_level = elevator_level;
    }

    public int getElevator_station() {
        return elevator_station;
    }

    public void setElevator_station(int elevator_station) {
        this.elevator_station = elevator_station;
    }

    public int getElevator_door() {
        return elevator_door;
    }

    public void setElevator_door(int elevator_door) {
        this.elevator_door = elevator_door;
    }

    public String getElevator_angle() {
        return elevator_angle;
    }

    public void setElevator_angle(String elevator_angle) {
        this.elevator_angle = elevator_angle;
    }

    public String getElevator_high() {
        return elevator_high;
    }

    public void setElevator_high(String elevator_high) {
        this.elevator_high = elevator_high;
    }

    public String getElevator_step_width() {
        return elevator_step_width;
    }

    public void setElevator_step_width(String elevator_step_width) {
        this.elevator_step_width = elevator_step_width;
    }

    public String getElevator_relation_person() {
        return elevator_relation_person;
    }

    public void setElevator_relation_person(String elevator_relation_person) {
        this.elevator_relation_person = elevator_relation_person;
    }

    public String getElevator_relation_phone() {
        return elevator_relation_phone;
    }

    public void setElevator_relation_phone(String elevator_relation_phone) {
        this.elevator_relation_phone = elevator_relation_phone;
    }

    public String getNext_check_date() {
        return next_check_date;
    }

    public void setNext_check_date(String next_check_date) {
        this.next_check_date = next_check_date;
    }

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public String getElevator_effect_date() {
        return elevator_effect_date;
    }

    public void setElevator_effect_date(String elevator_effect_date) {
        this.elevator_effect_date = elevator_effect_date;
    }

    public String getWorkers() {
        return workers;
    }

    public void setWorkers(String workers) {
        this.workers = workers;
    }

    public String getItem_version() {
        return item_version;
    }

    public void setItem_version(String item_version) {
        this.item_version = item_version;
    }

    public String getElevator_category() {
        return elevator_category;
    }

    public void setElevator_category(String elevator_category) {
        this.elevator_category = elevator_category;
    }

    public String getMt_photo_url() {
        return mt_photo_url;
    }

    public void setMt_photo_url(String mt_photo_url) {
        this.mt_photo_url = mt_photo_url;
    }

    public String getProperty_sign_url() {
        return property_sign_url;
    }

    public void setProperty_sign_url(String property_sign_url) {
        this.property_sign_url = property_sign_url;
    }
}
