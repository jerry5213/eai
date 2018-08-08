package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/4/5.
 */

public class DesignApply {


    /**
     * Guid : b32d38d4-5c25-403d-b9f2-a9e26c43f529
     * EQS_ProductGuid : 0cf2e760-c2ce-4d63-9591-b2683857204c
     * DS_ProductNo : GK2018-00992-02A-001
     * strEqsProductNo : XJ2018-00992-02A
     * projectName : HYtest032101跨区
     * elevatorProduct : 乘客电梯
     * elevatorType : STKJS1000
     * elevatorNo : L2
     * isNonstandard : -
     * BranchName : null
     * createUserName : null
     * createDate : 2018-04-05 14:54:55
     * instanceNodeName : 效果图申请中
     */

    private String Guid;
    private String EQS_ProductGuid;
    private String DS_ProductNo;
    private String strEqsProductNo;
    private String projectName;
    private String elevatorProduct;
    private String elevatorType;
    private String elevatorNo;
    private String isNonstandard;
    private String BranchName;
    private String createUserName;
    private String createDate;
    private String instanceNodeName;
    private String TaskGuId;

    public boolean showDownload() {
        boolean isShow = false;
        if (instanceNodeName != null && instanceNodeName.equals("流程结束")) {
            isShow = true;
        }

        return isShow;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getEQS_ProductGuid() {
        return EQS_ProductGuid;
    }

    public void setEQS_ProductGuid(String EQS_ProductGuid) {
        this.EQS_ProductGuid = EQS_ProductGuid;
    }

    public String getDS_ProductNo() {
        return DS_ProductNo;
    }

    public void setDS_ProductNo(String DS_ProductNo) {
        this.DS_ProductNo = DS_ProductNo;
    }

    public String getStrEqsProductNo() {
        return strEqsProductNo;
    }

    public void setStrEqsProductNo(String strEqsProductNo) {
        this.strEqsProductNo = strEqsProductNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getElevatorProduct() {
        return elevatorProduct;
    }

    public void setElevatorProduct(String elevatorProduct) {
        this.elevatorProduct = elevatorProduct;
    }

    public String getElevatorType() {
        return elevatorType;
    }

    public void setElevatorType(String elevatorType) {
        this.elevatorType = elevatorType;
    }

    public String getElevatorNo() {
        return elevatorNo;
    }

    public void setElevatorNo(String elevatorNo) {
        this.elevatorNo = elevatorNo;
    }

    public String getIsNonstandard() {
        return isNonstandard;
    }

    public void setIsNonstandard(String isNonstandard) {
        this.isNonstandard = isNonstandard;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateDate() {
        if (createDate != null) {
            return createDate.substring(0, 10);
        }
        return "";
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getInstanceNodeName() {
        return instanceNodeName;
    }

    public void setInstanceNodeName(String instanceNodeName) {
        this.instanceNodeName = instanceNodeName;
    }

    public String getTaskGuId() {
        return TaskGuId;
    }

    public void setTaskGuId(String taskGuId) {
        TaskGuId = taskGuId;
    }
}
