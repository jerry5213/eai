package com.zxtech.mt.entity;

import java.sql.Date;

/**
 * Created by Chw on 2016/7/20.
 */
public class MtCheckSecWrongItemRel {

    private String id;

    private String plan_id;

    private String item_id;

    private String fix_method;

    private String fix_worker_name;

    private Date fix_date;

    private String remark;

    private String grp_id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getFix_method() {
        return fix_method;
    }

    public void setFix_method(String fix_method) {
        this.fix_method = fix_method;
    }

    public String getFix_worker_name() {
        return fix_worker_name;
    }

    public void setFix_worker_name(String fix_worker_name) {
        this.fix_worker_name = fix_worker_name;
    }

    public Date getFix_date() {
        return fix_date;
    }

    public void setFix_date(Date fix_date) {
        this.fix_date = fix_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGrp_id() {
        return grp_id;
    }

    public void setGrp_id(String grp_id) {
        this.grp_id = grp_id;
    }
}
