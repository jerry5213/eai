package com.zxtech.mt.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp523 on 2017/8/2.
 */

public class MtWorkPlanAddtion {

    private String id;

    private String plan_id;

    private String addition_type;

    private String addition_description;

    private Integer addition_order;

    private String addition_name;

    private String addition_url;

    private String dict_name;

    private String remark;

    private ArrayList<String> images = null;



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

    public String getAddition_type() {
        return addition_type;
    }

    public void setAddition_type(String addition_type) {
        this.addition_type = addition_type;
    }

    public String getAddition_description() {
        return addition_description;
    }

    public void setAddition_description(String addition_description) {
        this.addition_description = addition_description;
    }

    public Integer getAddition_order() {
        return addition_order;
    }

    public void setAddition_order(Integer addition_order) {
        this.addition_order = addition_order;
    }

    public String getAddition_name() {
        return addition_name;
    }

    public void setAddition_name(String addition_name) {
        this.addition_name = addition_name;
    }

    public String getAddition_url() {
        return addition_url;
    }

    public void setAddition_url(String addition_url) {
        this.addition_url = addition_url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDict_name() {
        return dict_name;
    }

    public void setDict_name(String dict_name) {
        this.dict_name = dict_name;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}

