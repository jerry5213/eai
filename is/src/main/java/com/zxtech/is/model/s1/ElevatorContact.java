package com.zxtech.is.model.s1;

import java.io.Serializable;

/**
 * Created by syp600 on 2018/4/24.
 */

public class ElevatorContact implements Serializable {

    private boolean isCheck = false;
    private String guid;
    private String elevatorGuid;
    private String procDefKey;
    private String procinstid;
    private String name;
    private String telephone;
    private String email;
    private String post;

    private String elevatorguids;

    private String procdefkeys;

    private String procInstIds;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getElevatorGuid() {
        return elevatorGuid;
    }

    public void setElevatorGuid(String elevatorGuid) {
        this.elevatorGuid = elevatorGuid;
    }

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public String getProcinstid() {
        return procinstid;
    }

    public void setProcinstid(String procinstid) {
        this.procinstid = procinstid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getElevatorguids() {
        return elevatorguids;
    }

    public void setElevatorguids(String elevatorguids) {
        this.elevatorguids = elevatorguids;
    }

    public String getProcdefkeys() {
        return procdefkeys;
    }

    public void setProcdefkeys(String procdefkeys) {
        this.procdefkeys = procdefkeys;
    }

    public String getProcInstIds() {
        return procInstIds;
    }

    public void setProcInstIds(String procInstIds) {
        this.procInstIds = procInstIds;
    }
}
