package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/6/25.
 */

public class AgentOrg {


    /**
     * ExtensionData :
     * BusinessDataXml :
     * CreatedTime : 0001/1/1 0:00:00
     * CreatedUserName :
     * CreatedUserNo :
     * DeleteTime :
     * DeptId : 00000000-0000-0000-0000-000000000000
     * DeptName : --请选择--
     * DeptNo :
     * DeptRemark :
     * DeptTreeNo :
     * DeptType :
     * IsBranchFactory : False
     * IsDeleted : False
     * ParentDeptId :
     * ParentDeptTreeNo :
     */

    private String ExtensionData;
    private String BusinessDataXml;
    private String CreatedTime;
    private String CreatedUserName;
    private String CreatedUserNo;
    private String DeleteTime;
    private String DeptId;
    private String DeptName;
    private String DeptNo;
    private String DeptRemark;
    private String DeptTreeNo;
    private String DeptType;
    private String IsBranchFactory;
    private String IsDeleted;
    private String ParentDeptId;
    private String ParentDeptTreeNo;

    public String getExtensionData() {
        return ExtensionData;
    }

    public void setExtensionData(String ExtensionData) {
        this.ExtensionData = ExtensionData;
    }

    public String getBusinessDataXml() {
        return BusinessDataXml;
    }

    public void setBusinessDataXml(String BusinessDataXml) {
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

    public String getDeleteTime() {
        return DeleteTime;
    }

    public void setDeleteTime(String DeleteTime) {
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

    public String getDeptRemark() {
        return DeptRemark;
    }

    public void setDeptRemark(String DeptRemark) {
        this.DeptRemark = DeptRemark;
    }

    public String getDeptTreeNo() {
        return DeptTreeNo;
    }

    public void setDeptTreeNo(String DeptTreeNo) {
        this.DeptTreeNo = DeptTreeNo;
    }

    public String getDeptType() {
        return DeptType;
    }

    public void setDeptType(String DeptType) {
        this.DeptType = DeptType;
    }

    public String getIsBranchFactory() {
        return IsBranchFactory;
    }

    public void setIsBranchFactory(String IsBranchFactory) {
        this.IsBranchFactory = IsBranchFactory;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String IsDeleted) {
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
