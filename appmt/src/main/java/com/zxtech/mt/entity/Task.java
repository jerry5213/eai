package com.zxtech.mt.entity;

/**
 * Created by Chw on 2016/6/26.
 */
public class Task {

    private int id;

    private String projectName;

    private String phone;

    private String address;

    private int isTrap;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsTrap() {
        return isTrap;
    }

    public void setIsTrap(int isTrap) {
        this.isTrap = isTrap;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
