package com.zxtech.gks.model.vo;

import java.io.Serializable;

/**
 * Created by SYP521 on 2017/11/7.
 */

public class ProjectInfo implements Serializable {

    private String project_name;
    private String project_attr;
    private String agent_name;
    private String promise_Count;
    private String product_list;
    private String project_no;
    private String project_type;
    private String gk_user;
    private String sale_branch;
    private String is_kq;

    public String getPromise_Count() {
        return promise_Count;
    }

    public void setPromise_Count(String promise_Count) {
        this.promise_Count = promise_Count;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_attr() {
        return project_attr;
    }

    public void setProject_attr(String project_attr) {
        this.project_attr = project_attr;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getProduct_list() {
        return product_list;
    }

    public void setProduct_list(String product_list) {
        this.product_list = product_list;
    }

    public String getProject_no() {
        return project_no;
    }

    public void setProject_no(String project_no) {
        this.project_no = project_no;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getGk_user() {
        return gk_user;
    }

    public void setGk_user(String gk_user) {
        this.gk_user = gk_user;
    }

    public String getSale_branch() {
        return sale_branch;
    }

    public void setSale_branch(String sale_branch) {
        this.sale_branch = sale_branch;
    }

    public String getIs_kq() {
        return is_kq;
    }

    public void setIs_kq(String is_kq) {
        this.is_kq = is_kq;
    }
}
