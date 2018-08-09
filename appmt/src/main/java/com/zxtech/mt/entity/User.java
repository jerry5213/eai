package com.zxtech.mt.entity;

/**
 * Created by syp523 on 2017/8/3.
 */

public class User {

    private String user_id;

    private String user_realname;

    private String tenant_code;

    private String emp_id;

    private String emp_name;

    private String comp_name;

    private String grp_id;

    private String grp_name;

    private String grp_code;

    private String token;

    private String emp_photo_url;

    private String emp_sign_url;

    private boolean is_update;

    private String apk_file_url;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_realname() {
        return user_realname;
    }

    public void setUser_realname(String user_realname) {
        this.user_realname = user_realname;
    }

    public String getTenant_code() {
        return tenant_code;
    }

    public void setTenant_code(String tenant_code) {
        this.tenant_code = tenant_code;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getGrp_id() {
        return grp_id;
    }

    public void setGrp_id(String grp_id) {
        this.grp_id = grp_id;
    }

    public String getGrp_name() {
        return grp_name;
    }

    public void setGrp_name(String grp_name) {
        this.grp_name = grp_name;
    }

    public String getGrp_code() {
        return grp_code;
    }

    public void setGrp_code(String grp_code) {
        this.grp_code = grp_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmp_photo_url() {
        return emp_photo_url;
    }

    public void setEmp_photo_url(String emp_photo_url) {
        this.emp_photo_url = emp_photo_url;
    }

    public String getEmp_sign_url() {
        return emp_sign_url;
    }

    public void setEmp_sign_url(String emp_sign_url) {
        this.emp_sign_url = emp_sign_url;
    }

    public boolean is_update() {
        return is_update;
    }

    public void setIs_update(boolean is_update) {
        this.is_update = is_update;
    }

    public String getApk_file_url() {
        return apk_file_url;
    }

    public void setApk_file_url(String apk_file_url) {
        this.apk_file_url = apk_file_url;
    }
}
