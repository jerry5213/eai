package com.zxtech.ecs.model;

/**
 * Created by syp600 on 2018/7/6.
 */

public class ProjectTrack {

    /**
     * data:[{guid=b674ad0c-b02c-4b25-bdfa-2d3e301bef72, projectGuid=7bbe5fe4-01c2-481f-aafa-11fbf6b39c8a, deptGuid=9e0cf186-7f51-418d-9930-c02f42cefd71, deptName=沈阳分公司, salesmanUserName=7bbe5fe4-01c2-481f-aafa-11fbf6b39c8a, salesmanUserId=7bbe5fe4-01c2-481f-aafa-11fbf6b39c8a, destination=卖电梯, customerName=老李, visitReason=卖电梯2, visitDate=2018-07-04}]
     */
    private String guid;
    private String projectGuid;
    private String deptGuid;
    private String salesmanUserId;
    private String destination;
    private String customerName;
    private String visitReason;
    private String visitDate;
    private String createDate;
    private String createUser;
    private String updateDate;
    private String delFlag;
    private String deptName;
    private String salesmanUserName;
    private String updateUser;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getDeptGuid() {
        return deptGuid;
    }

    public void setDeptGuid(String deptGuid) {
        this.deptGuid = deptGuid;
    }

    public String getSalesmanUserId() {
        return salesmanUserId;
    }

    public void setSalesmanUserId(String salesmanUserId) {
        this.salesmanUserId = salesmanUserId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSalesmanUserName() {
        return salesmanUserName;
    }

    public void setSalesmanUserName(String salesmanUserName) {
        this.salesmanUserName = salesmanUserName;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
