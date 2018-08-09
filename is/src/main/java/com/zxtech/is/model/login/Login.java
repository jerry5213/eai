package com.zxtech.is.model.login;

import java.util.List;
import java.util.Map;

public class Login {

    private String userId;

    private String userNo;

    private String username;

    private String roleIds;

    //	headquarters  总部   hqFlag 1是0否;
    private String hqFlag;

    //	projectFlag 1项目团队内 0项目团队外;
    private String projectFlag;

    //  校验权限（不可见0/可见1/可用2）的传参容器
    private List<String> funcControlList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getHqFlag() {
        return hqFlag;
    }

    public void setHqFlag(String hqFlag) {
        this.hqFlag = hqFlag;
    }

    public String getProjectFlag() {
        return projectFlag;
    }

    public void setProjectFlag(String projectFlag) {
        this.projectFlag = projectFlag;
    }

    public List<String> getFuncControlList() {
        return funcControlList;
    }

    public void setFuncControlList(List<String> funcControlList) {
        this.funcControlList = funcControlList;
    }
}
