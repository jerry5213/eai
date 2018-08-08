package com.zxtech.gks.model.vo.login;

import java.io.Serializable;

/**
 * Created by SYP521 on 2017/12/4.
 */

public class UserInfo implements Serializable {
    private static UserInfo userInfo;

    private void UserInfo(){

    }

    public static UserInfo getUser(){
        return userInfo;
    }


    private Object ADUserNo;
    private String CreatedTime;
    private String CreatedUserName;
    private String CreatedUserNo;
    private String DeleteTime;
    private boolean IsAD;
    private boolean IsDeleted;
    private boolean LoginChangePassword;
    private Object MultiLanguage;
    private Object UserAddress;
    private int UserAge;
    private String UserBirthday;
    private String UserEmail;
    private String UserEndDate;
    private String UserId;
    private String UserMobile;
    private String UserName;
    private String UserNo;
    private String UserPassword;
    private Object UserPostcode;
    private String UserRemark;
    private Object UserSex;
    private String UserStartDate;
    private String UserState;
    private Object UserTel;
    private String UserType;
    private String headImgUrl;

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Object getADUserNo() {
        return ADUserNo;
    }

    public void setADUserNo(Object ADUserNo) {
        this.ADUserNo = ADUserNo;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public String getCreatedUserName() {
        return CreatedUserName;
    }

    public void setCreatedUserName(String CreatedUserName) {
        this.CreatedUserName = CreatedUserName;
    }

    public String getCreatedUserNo() {
        return CreatedUserNo;
    }

    public void setCreatedUserNo(String CreatedUserNo) {
        this.CreatedUserNo = CreatedUserNo;
    }

    public String getDeleteTime() {
        return DeleteTime;
    }

    public void setDeleteTime(String DeleteTime) {
        this.DeleteTime = DeleteTime;
    }

    public boolean isIsAD() {
        return IsAD;
    }

    public void setIsAD(boolean IsAD) {
        this.IsAD = IsAD;
    }

    public boolean isIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public boolean isLoginChangePassword() {
        return LoginChangePassword;
    }

    public void setLoginChangePassword(boolean LoginChangePassword) {
        this.LoginChangePassword = LoginChangePassword;
    }

    public Object getMultiLanguage() {
        return MultiLanguage;
    }

    public void setMultiLanguage(Object MultiLanguage) {
        this.MultiLanguage = MultiLanguage;
    }

    public Object getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(Object UserAddress) {
        this.UserAddress = UserAddress;
    }

    public int getUserAge() {
        return UserAge;
    }

    public void setUserAge(int UserAge) {
        this.UserAge = UserAge;
    }

    public String getUserBirthday() {
        return UserBirthday;
    }

    public void setUserBirthday(String UserBirthday) {
        this.UserBirthday = UserBirthday;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String UserEmail) {
        this.UserEmail = UserEmail;
    }

    public String getUserEndDate() {
        return UserEndDate;
    }

    public void setUserEndDate(String UserEndDate) {
        this.UserEndDate = UserEndDate;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String UserMobile) {
        this.UserMobile = UserMobile;
    }

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

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String UserPassword) {
        this.UserPassword = UserPassword;
    }

    public Object getUserPostcode() {
        return UserPostcode;
    }

    public void setUserPostcode(Object UserPostcode) {
        this.UserPostcode = UserPostcode;
    }

    public String getUserRemark() {
        return UserRemark;
    }

    public void setUserRemark(String UserRemark) {
        this.UserRemark = UserRemark;
    }

    public Object getUserSex() {
        return UserSex;
    }

    public void setUserSex(Object UserSex) {
        this.UserSex = UserSex;
    }

    public String getUserStartDate() {
        return UserStartDate;
    }

    public void setUserStartDate(String UserStartDate) {
        this.UserStartDate = UserStartDate;
    }

    public String getUserState() {
        return UserState;
    }

    public void setUserState(String UserState) {
        this.UserState = UserState;
    }

    public Object getUserTel() {
        return UserTel;
    }

    public void setUserTel(Object UserTel) {
        this.UserTel = UserTel;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String UserType) {
        this.UserType = UserType;
    }

}
