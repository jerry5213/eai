package com.zxtech.gks.model.bean;

/**
 * Created by SYP521 on 2018/1/5.
 */

public class DxmZY {

    /**
     * UserName : 沈斌
     * UserNo : 28500140
     */

    private String UserName;
    private String UserNo;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserNo() {
        return UserNo;
    }

    public void setUserNo(String UserNo) {
        this.UserNo = UserNo;
    }

    @Override
    public String toString() {
        return UserName;
    }
}
