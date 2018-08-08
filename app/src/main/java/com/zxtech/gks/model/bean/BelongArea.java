package com.zxtech.gks.model.bean;

/**
 * Created by syp521 on 2018/7/9.
 */

public class BelongArea {

    /**
     * BusinessDataXml : null
     * CreatedTime : 2017-06-01T17:39:17.213
     * CreatedUserName : 流程管理员
     * CreatedUserNo : sa
     * DeleteTime : null
     * DeptId : 09a51f6d-f8bd-40e1-9ee1-32be39824fed
     * DeptName : 北京分公司（内蒙）
     * DeptNo : GKIM
     * DeptRemark : null
     * DeptTreeNo : 044003002
     * DeptType : 1
     * IsBranchFactory : false
     * IsDeleted : false
     * ParentDeptId : 95974633-0728-4d33-a897-8a134b18d572
     * ParentDeptTreeNo : 044003
     */

    private Object BusinessDataXml;
    private String CreatedTime;
    private String CreatedUserName;
    private String CreatedUserNo;
    private Object DeleteTime;
    private String DeptId;
    private String DeptName;
    private String DeptNo;
    private Object DeptRemark;
    private String DeptTreeNo;
    private int DeptType;
    private boolean IsBranchFactory;
    private boolean IsDeleted;
    private String ParentDeptId;
    private String ParentDeptTreeNo;

    public Object getBusinessDataXml() {
        return BusinessDataXml;
    }

    public void setBusinessDataXml(Object BusinessDataXml) {
        this.BusinessDataXml = BusinessDataXml;
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

    public Object getDeleteTime() {
        return DeleteTime;
    }

    public void setDeleteTime(Object DeleteTime) {
        this.DeleteTime = DeleteTime;
    }

    public String getDeptId() {
        return DeptId;
    }

    public void setDeptId(String DeptId) {
        this.DeptId = DeptId;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String DeptName) {
        this.DeptName = DeptName;
    }

    public String getDeptNo() {
        return DeptNo;
    }

    public void setDeptNo(String DeptNo) {
        this.DeptNo = DeptNo;
    }

    public Object getDeptRemark() {
        return DeptRemark;
    }

    public void setDeptRemark(Object DeptRemark) {
        this.DeptRemark = DeptRemark;
    }

    public String getDeptTreeNo() {
        return DeptTreeNo;
    }

    public void setDeptTreeNo(String DeptTreeNo) {
        this.DeptTreeNo = DeptTreeNo;
    }

    public int getDeptType() {
        return DeptType;
    }

    public void setDeptType(int DeptType) {
        this.DeptType = DeptType;
    }

    public boolean isIsBranchFactory() {
        return IsBranchFactory;
    }

    public void setIsBranchFactory(boolean IsBranchFactory) {
        this.IsBranchFactory = IsBranchFactory;
    }

    public boolean isIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public String getParentDeptId() {
        return ParentDeptId;
    }

    public void setParentDeptId(String ParentDeptId) {
        this.ParentDeptId = ParentDeptId;
    }

    public String getParentDeptTreeNo() {
        return ParentDeptTreeNo;
    }

    public void setParentDeptTreeNo(String ParentDeptTreeNo) {
        this.ParentDeptTreeNo = ParentDeptTreeNo;
    }

    @Override
    public String toString() {
        return DeptName;
    }
}
