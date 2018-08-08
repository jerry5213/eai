package com.zxtech.ecs.model;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by syp523 on 2018/6/1.
 */

public class ContractReview {


    /**
     * branchName : 沈阳分公司
     * createTime : /Date(1527764019060)/
     * projectGuid : 947c2789-a0b5-449a-92aa-8fbb5c827304
     * contractNo : GKLN2018-01277
     * projectType : PT
     * contractGuid : 557b0b05-37ba-492e-bd9e-c58f38879fd9
     * projectNo : GK2018-01277
     * projectName : 项目流程测试1
     * customerName : 0529手机建
     * salesmanUserName : 15840046701
     * instanceNodeId : 6545d3ea-76ab-4d15-84df-6e518f9252a8
     */

    private String branchName;
    private String createTime;
    private String projectGuid;
    private String contractNo;
    private String projectType;
    private String contractGuid;
    private String projectNo;
    private String projectName;
    private String customerName;
    private String salesmanUserName;
    private String instanceNodeId;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCreateTime() {
        try {
            if (TextUtils.isEmpty(createTime)) {
                return "";
            }
            long timeInMills = Long.parseLong(createTime.substring(6, 19));
            return new SimpleDateFormat("yyyy/MM/dd").format(new Date(timeInMills));
        } catch (Exception e) {
            e.printStackTrace();
            return createTime;
        }
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getContractGuid() {
        return contractGuid;
    }

    public void setContractGuid(String contractGuid) {
        this.contractGuid = contractGuid;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSalesmanUserName() {
        return salesmanUserName;
    }

    public void setSalesmanUserName(String salesmanUserName) {
        this.salesmanUserName = salesmanUserName;
    }

    public String getInstanceNodeId() {
        return instanceNodeId;
    }

    public void setInstanceNodeId(String instanceNodeId) {
        this.instanceNodeId = instanceNodeId;
    }
}
