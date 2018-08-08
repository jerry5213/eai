package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/7/10.
 */

public class AccountPaymentProject {


    /**
     * OrderNumber : 0006294860
     * ContractNo : GKLN2018-00220
     * ProjectNo : 2018-00220
     * ProjectName : 首创国际城二期6号楼
     * SalesmanUserId : facc8df7-5ab2-4568-9e36-0608f7492978
     * CreateDate : 2018-06-26T09:52:14.103
     */

    private String OrderNumber;
    private String ContractNo;
    private String ProjectNo;
    private String ProjectName;
    private String SalesmanUserId;
    private String CreateDate;

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public String getContractNo() {
        return ContractNo;
    }

    public void setContractNo(String ContractNo) {
        this.ContractNo = ContractNo;
    }

    public String getProjectNo() {
        return ProjectNo;
    }

    public void setProjectNo(String ProjectNo) {
        this.ProjectNo = ProjectNo;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getSalesmanUserId() {
        return SalesmanUserId;
    }

    public void setSalesmanUserId(String SalesmanUserId) {
        this.SalesmanUserId = SalesmanUserId;
    }

    public String getCreateDate() {
        return CreateDate != null ? CreateDate.substring(0, 10) : null;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }
}
