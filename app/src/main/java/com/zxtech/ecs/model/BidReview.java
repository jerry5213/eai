package com.zxtech.ecs.model;

import java.io.Serializable;

/**
 * Created by syp523 on 2018/7/24.
 */

public class BidReview implements Serializable{


    /**
     * ProjectName : 招投标测试1
     * ProjectNo : 2018-00905
     * CustomerName : 不要动这条数据
     * BranchName : 沈阳分公司
     * PurchaseType : 招投标
     * BiddingReviewState : 1
     * ProjectCreateTime : 2018-07-19T08:51:03
     * IsBidding : null
     * IsKQ : false
     * WorkFlowNodeName : R-End-Y
     * SalesmanUserName : 13390247563
     * TaskRunState : end
     * InstanceNodeId : c7a16e7d-4b7b-4925-b431-10281299ffa4
     * InstanceNodeName : 投标评审
     * RunState : end
     * TransactUserNo : 28504272
     * SubmitOption : 提交,驳回
     * SubmitResult : 提交
     * NodeCreateTime : 2018-07-20T11:48:39.24
     * ProjectType : PT
     * CreateDate : 2018-07-20T08:51:06.61
     * CreateUser : 3adf49f8-bf12-4480-87ae-d68e5f12317c
     * Guid : b9d69bbd-5784-4241-978c-160c9aaeee46
     * IsCurrentVersion : true
     * ProjectGuid : fcf05cff-995e-41d8-acea-7c4e401d4394
     * InstanceGuid : dd41192b-ebfc-40b8-9890-4806ebbb5c96
     * VersionNum : 1
     * UpdateUser : 3adf49f8-bf12-4480-87ae-d68e5f12317c
     * UpdateDate : 2018-07-20T08:51:06.61
     */

    private String ProjectName;
    private String ProjectNo;
    private String CustomerName;
    private String BranchName;
    private String PurchaseType;
    private String BiddingReviewState;
    private String ProjectCreateTime;
    private String IsBidding;
    private boolean IsKQ;
    private String WorkFlowNodeName;
    private String SalesmanUserName;
    private String TaskRunState;
    private String InstanceNodeId;
    private String InstanceNodeName;
    private String RunState;
    private String TransactUserNo;
    private String SubmitOption;
    private String SubmitResult;
    private String NodeCreateTime;
    private String ProjectType;
    private String CreateDate;
    private String CreateUser;
    private String Guid;
    private boolean IsCurrentVersion;
    private String ProjectGuid;
    private String InstanceGuid;
    private String VersionNum;
    private String UpdateUser;
    private String UpdateDate;
    private int CommitType;
    private String BiddingReviewGuid;

    public String getBiddingReviewGuid() {
        return BiddingReviewGuid;
    }

    public void setBiddingReviewGuid(String biddingReviewGuid) {
        BiddingReviewGuid = biddingReviewGuid;
    }

    public int getCommitType() {
        return CommitType;
    }

    public void setCommitType(int commitType) {
        CommitType = commitType;
    }

    public String getProcess() {
        if ("end".equals(TaskRunState)) {
            return "流程结束";
        } else {
            return InstanceNodeName;
        }
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getProjectNo() {
        return ProjectNo;
    }

    public void setProjectNo(String ProjectNo) {
        this.ProjectNo = ProjectNo;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public String getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(String PurchaseType) {
        this.PurchaseType = PurchaseType;
    }

    public String getBiddingReviewState() {
        return BiddingReviewState;
    }

    public void setBiddingReviewState(String BiddingReviewState) {
        this.BiddingReviewState = BiddingReviewState;
    }

    public String getProjectCreateTime() {
        return ProjectCreateTime;
    }

    public void setProjectCreateTime(String ProjectCreateTime) {
        this.ProjectCreateTime = ProjectCreateTime;
    }

    public String getIsBidding() {
        return IsBidding;
    }

    public String getIsBiddingText() {
        if (IsBidding == null) {
            return IsBidding;
        }else if (IsBidding.equals("true")) {
            return "中标";
        }else{
            return "未中标";
        }
    }

    public void setIsBidding(String IsBidding) {
        this.IsBidding = IsBidding;
    }

    public boolean isIsKQ() {
        return IsKQ;
    }

    public void setIsKQ(boolean IsKQ) {
        this.IsKQ = IsKQ;
    }

    public String getWorkFlowNodeName() {
        return WorkFlowNodeName;
    }

    public void setWorkFlowNodeName(String WorkFlowNodeName) {
        this.WorkFlowNodeName = WorkFlowNodeName;
    }

    public String getSalesmanUserName() {
        return SalesmanUserName;
    }

    public void setSalesmanUserName(String SalesmanUserName) {
        this.SalesmanUserName = SalesmanUserName;
    }

    public String getTaskRunState() {
        return TaskRunState;
    }

    public void setTaskRunState(String TaskRunState) {
        this.TaskRunState = TaskRunState;
    }

    public String getInstanceNodeId() {
        return InstanceNodeId;
    }

    public void setInstanceNodeId(String InstanceNodeId) {
        this.InstanceNodeId = InstanceNodeId;
    }

    public String getInstanceNodeName() {
        return InstanceNodeName;
    }

    public void setInstanceNodeName(String InstanceNodeName) {
        this.InstanceNodeName = InstanceNodeName;
    }

    public String getRunState() {
        return RunState;
    }

    public void setRunState(String RunState) {
        this.RunState = RunState;
    }

    public String getTransactUserNo() {
        return TransactUserNo;
    }

    public void setTransactUserNo(String TransactUserNo) {
        this.TransactUserNo = TransactUserNo;
    }

    public String getSubmitOption() {
        return SubmitOption;
    }

    public void setSubmitOption(String SubmitOption) {
        this.SubmitOption = SubmitOption;
    }

    public String getSubmitResult() {
        return SubmitResult;
    }

    public void setSubmitResult(String SubmitResult) {
        this.SubmitResult = SubmitResult;
    }

    public String getNodeCreateTime() {
        return NodeCreateTime != null ? NodeCreateTime.substring(0,10) : null;
    }

    public void setNodeCreateTime(String NodeCreateTime) {
        this.NodeCreateTime = NodeCreateTime;
    }

    public String getProjectType() {
        return ProjectType;
    }

    public void setProjectType(String ProjectType) {
        this.ProjectType = ProjectType;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String CreateUser) {
        this.CreateUser = CreateUser;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public boolean isIsCurrentVersion() {
        return IsCurrentVersion;
    }

    public void setIsCurrentVersion(boolean IsCurrentVersion) {
        this.IsCurrentVersion = IsCurrentVersion;
    }

    public String getProjectGuid() {
        return ProjectGuid;
    }

    public void setProjectGuid(String ProjectGuid) {
        this.ProjectGuid = ProjectGuid;
    }

    public String getInstanceGuid() {
        return InstanceGuid;
    }

    public void setInstanceGuid(String InstanceGuid) {
        this.InstanceGuid = InstanceGuid;
    }

    public String getVersionNum() {
        return VersionNum;
    }

    public void setVersionNum(String VersionNum) {
        this.VersionNum = VersionNum;
    }

    public String getUpdateUser() {
        return UpdateUser;
    }

    public void setUpdateUser(String UpdateUser) {
        this.UpdateUser = UpdateUser;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String UpdateDate) {
        this.UpdateDate = UpdateDate;
    }
}
