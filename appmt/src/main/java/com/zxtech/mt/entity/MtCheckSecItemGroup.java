package com.zxtech.mt.entity;


import java.io.Serializable;

/**
 * Created by Chw on 2016/7/19.
 */
public class MtCheckSecItemGroup implements Serializable{


    private static final long serialVersionUID = -3277739476706441952L;
    private String id;
    private String grp_code;
    private String grp_name;
    private Integer grp_level;
    private String grp_type;
    private String remark;
    //子项中是否有错误
    private int clickWrong;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrp_code() {
        return grp_code;
    }

    public void setGrp_code(String grp_code) {
        this.grp_code = grp_code;
    }

    public String getGrp_name() {
        return grp_name;
    }

    public void setGrp_name(String grp_name) {
        this.grp_name = grp_name;
    }

    public Integer getGrp_level() {
        return grp_level;
    }

    public void setGrp_level(Integer grp_level) {
        this.grp_level = grp_level;
    }

    public String getGrp_type() {
        return grp_type;
    }

    public void setGrp_type(String grp_type) {
        this.grp_type = grp_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getClickWrong() {
        return clickWrong;
    }

    public void setClickWrong(int clickWrong) {
        this.clickWrong = clickWrong;
    }
}
