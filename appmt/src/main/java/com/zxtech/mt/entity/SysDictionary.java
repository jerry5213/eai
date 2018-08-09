package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/7/27.
 */
public class SysDictionary {

    private String id;

    private String dict_name;

    private String dict_code;

    private String dict_type;

    private String p_dict_id;

    private String enable_flag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDict_name() {
        return dict_name;
    }

    public void setDict_name(String dict_name) {
        this.dict_name = dict_name;
    }

    public String getDict_code() {
        return dict_code;
    }

    public void setDict_code(String dict_code) {
        this.dict_code = dict_code;
    }

    public String getDict_type() {
        return dict_type;
    }

    public void setDict_type(String dict_type) {
        this.dict_type = dict_type;
    }

    public String getP_dict_id() {
        return p_dict_id;
    }

    public void setP_dict_id(String p_dict_id) {
        this.p_dict_id = p_dict_id;
    }

    public String getEnable_flag() {
        return enable_flag;
    }

    public void setEnable_flag(String enable_flag) {
        this.enable_flag = enable_flag;
    }
}
