package com.zxtech.ecs.model;

import java.io.Serializable;

/**
 * Created by syp523 on 2018/4/3.
 */

public class ProjectQuote implements Serializable{


    /**
     * UserName : null
     * InstanceNodeName : 分总审批
     * InstanceNodeId : b4dccc78-9395-4f0f-b0bc-064d1f608957
     * ProjectGuid : f754d1ba-9767-4431-b6fe-0346c34dc519
     * CreateTime : 2018-03-15T13:37:15
     * BranchNo : GKLZ
     * CustomerName : 手机建客户测试0119
     * ProjectNo : GK2018-00955
     * ProjectName : test006
     * InstanceGuid : b351c63e-dc8d-418c-92f4-9a14c6dbce83
     * IsConfirm : true
     * ProjectState : 0
     * TaskRunState : end
     * RunState : end
     * TransactUserNo : 28503385
     * SubmitResult : 提交
     * SalesmanUserNo : 28503757
     * SalesmanUserName : 侯孝刚
     * BranchName : 兰州分公司
     * ProjectAdd_Other : test006
     * NodeInstanceNodeType : TransactNode
     * ProjectType : PT
     * NodeCreateTime : 2018-03-15T13:38:07.673
     * IsAllConfirm : false
     * IsSAPClose : null
     * WorkFlowNodeName : P-Doing
     * IsKQ : false
     */

    private Object UserName;
    private String InstanceNodeName;
    private String InstanceNodeId;
    private String ProjectGuid;
    private String CreateTime;
    private String BranchNo;
    private String CustomerName;
    private String ProjectNo;
    private String ProjectName;
    private String InstanceGuid;
    private boolean IsConfirm;
    private int ProjectState;
    private String TaskRunState;
    private String RunState;
    private String TransactUserNo;
    private String SubmitResult;
    private String SalesmanUserNo;
    private String SalesmanUserName;
    private String BranchName;
    private String ProjectAdd_Other;
    private String NodeInstanceNodeType;
    private String ProjectType;
    private String NodeCreateTime;
    private boolean IsAllConfirm;
    private Object IsSAPClose;
    private String WorkFlowNodeName;
    private boolean IsKQ;

    public String getProcess() {
        if ("end".equals(TaskRunState)) {
            return "报备结束";
        } else if ("项目登记".equals(TaskRunState)) {
            return "新建";
        } else if ("驳回".equals(TaskRunState)) {
            return "被驳回";
        } else {
            if (InstanceNodeName == null) {
                return "价审未提交";
            }
            return InstanceNodeName;
        }
    }



    public Object getUserName() {
        return UserName;
    }

    public void setUserName(Object UserName) {
        this.UserName = UserName;
    }

    public String getInstanceNodeName() {
        return InstanceNodeName;
    }

    public void setInstanceNodeName(String InstanceNodeName) {
        this.InstanceNodeName = InstanceNodeName;
    }

    public String getInstanceNodeId() {
        return InstanceNodeId;
    }

    public void setInstanceNodeId(String InstanceNodeId) {
        this.InstanceNodeId = InstanceNodeId;
    }

    public String getProjectGuid() {
        return ProjectGuid;
    }

    public void setProjectGuid(String ProjectGuid) {
        this.ProjectGuid = ProjectGuid;
    }

    public String getCreateTime() {
        if (CreateTime != null) {
            return CreateTime.substring(0, 10);
        }
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getBranchNo() {
        return BranchNo;
    }

    public void setBranchNo(String BranchNo) {
        this.BranchNo = BranchNo;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
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

    public String getInstanceGuid() {
        return InstanceGuid;
    }

    public void setInstanceGuid(String InstanceGuid) {
        this.InstanceGuid = InstanceGuid;
    }

    public boolean isIsConfirm() {
        return IsConfirm;
    }

    public void setIsConfirm(boolean IsConfirm) {
        this.IsConfirm = IsConfirm;
    }

    public int getProjectState() {
        return ProjectState;
    }

    public void setProjectState(int ProjectState) {
        this.ProjectState = ProjectState;
    }

    public String getTaskRunState() {
        return TaskRunState;
    }

    public void setTaskRunState(String TaskRunState) {
        this.TaskRunState = TaskRunState;
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

    public String getSubmitResult() {
        return SubmitResult;
    }

    public void setSubmitResult(String SubmitResult) {
        this.SubmitResult = SubmitResult;
    }

    public String getSalesmanUserNo() {
        return SalesmanUserNo;
    }

    public void setSalesmanUserNo(String SalesmanUserNo) {
        this.SalesmanUserNo = SalesmanUserNo;
    }

    public String getSalesmanUserName() {
        return SalesmanUserName;
    }

    public void setSalesmanUserName(String SalesmanUserName) {
        this.SalesmanUserName = SalesmanUserName;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public String getProjectAdd_Other() {
        return ProjectAdd_Other;
    }

    public void setProjectAdd_Other(String ProjectAdd_Other) {
        this.ProjectAdd_Other = ProjectAdd_Other;
    }

    public String getNodeInstanceNodeType() {
        return NodeInstanceNodeType;
    }

    public void setNodeInstanceNodeType(String NodeInstanceNodeType) {
        this.NodeInstanceNodeType = NodeInstanceNodeType;
    }

    public String getProjectType() {
        return ProjectType;
    }

    public void setProjectType(String ProjectType) {
        this.ProjectType = ProjectType;
    }

    public String getNodeCreateTime() {
        return NodeCreateTime;
    }

    public void setNodeCreateTime(String NodeCreateTime) {
        this.NodeCreateTime = NodeCreateTime;
    }

    public boolean isIsAllConfirm() {
        return IsAllConfirm;
    }

    public void setIsAllConfirm(boolean IsAllConfirm) {
        this.IsAllConfirm = IsAllConfirm;
    }

    public Object getIsSAPClose() {
        return IsSAPClose;
    }

    public void setIsSAPClose(Object IsSAPClose) {
        this.IsSAPClose = IsSAPClose;
    }

    public String getWorkFlowNodeName() {
        return WorkFlowNodeName;
    }

    public void setWorkFlowNodeName(String WorkFlowNodeName) {
        this.WorkFlowNodeName = WorkFlowNodeName;
    }

    public boolean isIsKQ() {
        return IsKQ;
    }

    public void setIsKQ(boolean IsKQ) {
        this.IsKQ = IsKQ;
    }
}
