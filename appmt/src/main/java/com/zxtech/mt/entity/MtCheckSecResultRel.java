package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/7/20.
 */
public class MtCheckSecResultRel {

    private String id;

    private String plan_id;

    private String grp_id;

    private String include_flag;

    private String grp_result;


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

    public String getGrp_id() {
        return grp_id;
    }

    public void setGrp_id(String grp_id) {
        this.grp_id = grp_id;
    }


    public String getInclude_flag() {
        return include_flag;
    }

    public void setInclude_flag(String include_flag) {
        this.include_flag = include_flag;
    }

    public String getGrp_result() {
        return grp_result;
    }

    public void setGrp_result(String grp_result) {
        this.grp_result = grp_result;
    }
}
