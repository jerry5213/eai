package com.zxtech.mt.entity;

import java.io.Serializable;

/**
 * Created by Chw on 2016/7/14.
 */
public class BaseElevator implements Serializable{

    private static final long serialVersionUID = -675896254031000814L;
    private String id;

    private String proj_id;

    private String elevator_code;

    private String elevator_reg_number;

    private String elevator_qrcode;

    private String type_id;

    private String produce_date;

    private String elevator_load;

    private String elevator_speed;

    private String elevator_high;

    private Integer elevator_level;

    private Integer elevator_station;

    private Integer elevator_door;

    private String elevator_angle;

    private String install_date;

    private String elevator_position;

    private String next_maintain_date;

    private String next_check_date;

    private String next_annual_date;

    private String remark;

    private String enable_flag;

    private String create_user;

    private String create_timestamp;

    private String last_update_user;

    private String last_update_timestamp;

    private String last_update_remark;

    private String type_name;

    private String producer_name;

    private String contract_type_name;

    private String proj_name;

    private String mt_unit;

    private  String elevator_category;

    private String category_name;

    private String elevator_relation_person;

    private String elevator_relation_phone;

    private String elevator_brand;

    private String last_date;

    private String next_date;

    private String check_date;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProj_id() {
        return proj_id;
    }

    public void setProj_id(String proj_id) {
        this.proj_id = proj_id;
    }

    public String getElevator_code() {
        return elevator_code;
    }

    public void setElevator_code(String elevator_code) {
        this.elevator_code = elevator_code;
    }

    public String getElevator_reg_number() {
        return elevator_reg_number;
    }

    public void setElevator_reg_number(String elevator_reg_number) {
        this.elevator_reg_number = elevator_reg_number;
    }

    public String getElevator_qrcode() {
        return elevator_qrcode;
    }

    public void setElevator_qrcode(String elevator_qrcode) {
        this.elevator_qrcode = elevator_qrcode;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getProduce_date() {
        return produce_date;
    }

    public void setProduce_date(String produce_date) {
        this.produce_date = produce_date;
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

    public String getElevator_high() {
        return elevator_high;
    }

    public void setElevator_high(String elevator_high) {
        this.elevator_high = elevator_high;
    }

    public Integer getElevator_level() {
        return elevator_level;
    }

    public void setElevator_level(Integer elevator_level) {
        this.elevator_level = elevator_level;
    }

    public Integer getElevator_station() {
        return elevator_station;
    }

    public void setElevator_station(Integer elevator_station) {
        this.elevator_station = elevator_station;
    }

    public Integer getElevator_door() {
        return elevator_door;
    }

    public void setElevator_door(Integer elevator_door) {
        this.elevator_door = elevator_door;
    }

    public String getElevator_angle() {
        return elevator_angle;
    }

    public void setElevator_angle(String elevator_angle) {
        this.elevator_angle = elevator_angle;
    }

    public String getInstall_date() {
        return install_date;
    }

    public void setInstall_date(String install_date) {
        this.install_date = install_date;
    }

    public String getElevator_position() {
        return elevator_position;
    }

    public void setElevator_position(String elevator_position) {
        this.elevator_position = elevator_position;
    }

    public String getNext_maintain_date() {
        return next_maintain_date;
    }

    public void setNext_maintain_date(String next_maintain_date) {
        this.next_maintain_date = next_maintain_date;
    }

    public String getNext_check_date() {
        return next_check_date;
    }

    public void setNext_check_date(String next_check_date) {
        this.next_check_date = next_check_date;
    }

    public String getNext_annual_date() {
        return next_annual_date;
    }

    public void setNext_annual_date(String next_annual_date) {
        this.next_annual_date = next_annual_date;
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

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getProducer_name() {
        return producer_name;
    }

    public void setProducer_name(String producer_name) {
        this.producer_name = producer_name;
    }

    public String getContract_type_name() {
        return contract_type_name;
    }

    public void setContract_type_name(String contract_type_name) {
        this.contract_type_name = contract_type_name;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public String getMt_unit() {
        return mt_unit;
    }

    public void setMt_unit(String mt_unit) {
        this.mt_unit = mt_unit;
    }

    public String getElevator_category() {
        return elevator_category;
    }

    public void setElevator_category(String elevator_category) {
        this.elevator_category = elevator_category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
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

    public String getElevator_brand() {
        return elevator_brand;
    }

    public void setElevator_brand(String elevator_brand) {
        this.elevator_brand = elevator_brand;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public String getNext_date() {
        return next_date;
    }

    public void setNext_date(String next_date) {
        this.next_date = next_date;
    }

    public String getCheck_date() {
        return check_date;
    }

    public void setCheck_date(String check_date) {
        this.check_date = check_date;
    }
}
