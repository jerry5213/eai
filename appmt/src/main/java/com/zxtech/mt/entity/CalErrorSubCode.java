package com.zxtech.mt.entity;



/**
 * Created by Chw on 2016/7/7.
 */
public class CalErrorSubCode {
    private String id;
    private String main_id;
    private String fix_id;
    private String sub_type;
    private String sub_code;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain_id() {
        return main_id;
    }

    public void setMain_id(String main_id) {
        this.main_id = main_id;
    }

    public String getFix_id() {
        return fix_id;
    }

    public void setFix_id(String fix_id) {
        this.fix_id = fix_id;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
