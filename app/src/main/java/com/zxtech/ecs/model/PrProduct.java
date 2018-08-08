package com.zxtech.ecs.model;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by syp521 on 2017/10/26.
 */

public class PrProduct {

    private String instanceNodeId;
    private String instanceNodeName;
    private int priceReviewState;
    private String salesmanUserName;
    private String workFlowNodeName;
    private String getWorkFlowNodeNameStr;
    private String projectNo;
    private String projectName;
    private String customerName;
    private String submitResult;
    private String transactUserNo;
    private String branchName;
    private String createDate;
    private String createTime;
    private String guid;
    private String instanceGuid;
    private String prversionNum;
    private String projectGuid;
    private String projectType;
    private String runState;
    private String submitOption;
    private String taskRunState;
    private boolean validState;
    private String versionNum;
    private String projectInstanceGuid;
    private String orderNumber;
    private String priceReviewGuid;

    public String getPriceReviewGuid() {
        return priceReviewGuid;
    }

    public void setPriceReviewGuid(String priceReviewGuid) {
        this.priceReviewGuid = priceReviewGuid;
    }

    public String getProjectInstanceGuid() {
        return projectInstanceGuid;
    }

    public String getInstanceNodeId() {
        return instanceNodeId;
    }

    public void setInstanceNodeId(String instanceNodeId) {
        this.instanceNodeId = instanceNodeId;
    }

    public String getGetWorkFlowNodeNameStr() {
        return getWorkFlowNodeNameStr;
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

    public String getInstanceNodeName() {
        return instanceNodeName;
    }

    public void setInstanceNodeName(String instanceNodeName) {
        this.instanceNodeName = instanceNodeName;
    }

    public int getPriceReviewState() {
        return priceReviewState;
    }

    public void setPriceReviewState(int priceReviewState) {
        this.priceReviewState = priceReviewState;
    }

    public String getSalesmanUserName() {
        return salesmanUserName;
    }

    public void setSalesmanUserName(String salesmanUserName) {
        this.salesmanUserName = salesmanUserName;
    }

    public String getWorkFlowNodeName() {
        return workFlowNodeName;
    }

    public void setWorkFlowNodeName(String workFlowNodeName) {
        this.workFlowNodeName = workFlowNodeName;
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

    public Object getSubmitResult() {
        return submitResult;
    }

    public void setSubmitResult(String submitResult) {
        this.submitResult = submitResult;
    }

    public String getTransactUserNo() {
        return transactUserNo;
    }

    public void setTransactUserNo(String transactUserNo) {
        this.transactUserNo = transactUserNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCreateDate() {

        try {
            if (TextUtils.isEmpty(createDate)) {
                return "";
            }
            long timeInMills = Long.parseLong(createDate.substring(6, 19));
            return new SimpleDateFormat("yyyy/MM/dd").format(new Date(timeInMills));
        } catch (Exception e) {
            e.printStackTrace();
            return createDate;
        }
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getInstanceGuid() {
        return instanceGuid;
    }

    public void setInstanceGuid(String instanceGuid) {
        this.instanceGuid = instanceGuid;
    }

    public Object getPrversionNum() {
        return prversionNum;
    }

    public void setPrversionNum(String prversionNum) {
        this.prversionNum = prversionNum;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getRunState() {
        return runState;
    }

    public void setRunState(String runState) {
        this.runState = runState;
    }

    public String getSubmitOption() {
        return submitOption;
    }

    public void setSubmitOption(String submitOption) {
        this.submitOption = submitOption;
    }

    public String getTaskRunState() {
        return taskRunState;
    }

    public void setTaskRunState(String taskRunState) {
        this.taskRunState = taskRunState;
    }

    public boolean isValidState() {
        return validState;
    }

    public void setValidState(boolean validState) {
        this.validState = validState;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
