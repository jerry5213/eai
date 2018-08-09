package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/7/14.
 */
public class MtEmpAttendance {

    private String id;

    private String emp_id;

    private Integer xio_week;

    private String record_date;

    private String start_time;

    private String end_time;

    private String work_long;

    private String relax_long;

    private String remark;

    private String start_address;

    private String end_address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public Integer getXio_week() {
        return xio_week;
    }

    public void setXio_week(Integer xio_week) {
        this.xio_week = xio_week;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getWork_long() {
        return work_long;
    }

    public void setWork_long(String work_long) {
        this.work_long = work_long;
    }

    public String getRelax_long() {
        return relax_long;
    }

    public void setRelax_long(String relax_long) {
        this.relax_long = relax_long;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }
}
