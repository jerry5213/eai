package com.zxtech.mt.entity;

import java.io.Serializable;

/**
 * Created by Chw on 2016/7/13.
 */
public class MtEmpAttendanceDetail implements Serializable{

    private static final long serialVersionUID = -2521637383796506432L;
    private String id;

    private String record_date;

    private String emp_id;

    private String proj_id;

    private String road_long;

    private String road_end;

    private String work_flag;

    private String road_start;

    private String work_code;

    private String work_content;

    private String work_long;

    private String relax_code;

    private String relax_content;

    private String relax_long;

    private String wor_start;

    private String wor_end;

    private String remark;

    private String advise_content;

    private String approval_status;

    private String proj_name;

    private String work_code_dict_name;

    private String relax_code_dict_name;

    private String elevator_id;

    private String elevator_code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
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

    public String getRoad_long() {
        return road_long;
    }

    public void setRoad_long(String road_long) {
        this.road_long = road_long;
    }

    public String getRoad_end() {
        return road_end;
    }

    public void setRoad_end(String road_end) {
        this.road_end = road_end;
    }

    public String getWork_flag() {
        return work_flag;
    }

    public void setWork_flag(String work_flag) {
        this.work_flag = work_flag;
    }

    public String getRoad_start() {
        return road_start;
    }

    public void setRoad_start(String road_start) {
        this.road_start = road_start;
    }

    public String getWork_code() {
        return work_code;
    }

    public void setWork_code(String work_code) {
        this.work_code = work_code;
    }

    public String getWork_content() {
        return work_content;
    }

    public void setWork_content(String work_content) {
        this.work_content = work_content;
    }

    public String getWork_long() {
        return work_long;
    }

    public void setWork_long(String work_long) {
        this.work_long = work_long;
    }

    public String getRelax_code() {
        return relax_code;
    }

    public void setRelax_code(String relax_code) {
        this.relax_code = relax_code;
    }

    public String getRelax_content() {
        return relax_content;
    }

    public void setRelax_content(String relax_content) {
        this.relax_content = relax_content;
    }

    public String getRelax_long() {
        return relax_long;
    }

    public void setRelax_long(String relax_long) {
        this.relax_long = relax_long;
    }

    public String getWor_start() {
        return wor_start;
    }

    public void setWor_start(String wor_start) {
        this.wor_start = wor_start;
    }

    public String getWor_end() {
        return wor_end;
    }

    public void setWor_end(String wor_end) {
        this.wor_end = wor_end;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAdvise_content() {
        return advise_content;
    }

    public void setAdvise_content(String advise_content) {
        this.advise_content = advise_content;
    }

    public String getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(String approval_status) {
        this.approval_status = approval_status;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public String getWork_code_dict_name() {
        return work_code_dict_name;
    }

    public void setWork_code_dict_name(String work_code_dict_name) {
        this.work_code_dict_name = work_code_dict_name;
    }

    public String getRelax_code_dict_name() {
        return relax_code_dict_name;
    }

    public void setRelax_code_dict_name(String relax_code_dict_name) {
        this.relax_code_dict_name = relax_code_dict_name;
    }

    public String getElevator_id() {
        return elevator_id;
    }

    public void setElevator_id(String elevator_id) {
        this.elevator_id = elevator_id;
    }

    public String getElevator_code() {
        return elevator_code;
    }

    public void setElevator_code(String elevator_code) {
        this.elevator_code = elevator_code;
    }
}
