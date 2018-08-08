package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/5/26.
 */

public class PriceApproval {


    /**
     * ProjectName : 恒大城市阳光广场1-5#楼
     * InstanceGuid : eeda9ca1-7604-4f03-80c4-ec867e85d9f8
     * ProjectNo : 2018-00008
     * CustomerName : 恒大
     * BranchName : 兰州分公司
     * Guid : 28547c29-fe5d-4af5-a70b-c9cfc675039c
     * SalesmanUserName : 侯孝刚
     * VersionNum : 2
     * CreateDate : 2018-04-16T17:41:15.303
     * ValidState : true
     * PriceReviewState : 0.0
     * TaskRunState : end
     * InstanceNodeId : 14cdf79a-7779-42e2-87c8-ffa9dc8cc83d
     * InstanceNodeName : 分总审批
     * RunState : end
     * TransactUserNo : 28503385
     * SubmitOption : 提交,驳回
     * SubmitResult : 提交
     * NodeCreateTime : 2018-04-16T17:41:18.22
     * ProjectGuid : 21e87cdd-51ec-4efe-ac0e-a02530747012
     * ProjectType : PT
     * PRVersionNum : null
     * WorkFlowNodeName : R-End-Y
     * SAPStatus : null
     * SAPCreateDate : null
     */

    private String ProjectName;
    private String InstanceGuid;
    private String ProjectNo;
    private String CustomerName;
    private String BranchName;
    private String Guid;
    private String SalesmanUserName;
    private String VersionNum;
    private String CreateDate;
    private boolean ValidState;
    private double PriceReviewState;
    private String TaskRunState;
    private String InstanceNodeId;
    private String InstanceNodeName;
    private String RunState;
    private String TransactUserNo;
    private String SubmitOption;
    private String SubmitResult;
    private String NodeCreateTime;
    private String ProjectGuid;
    private String ProjectType;
    private Object PRVersionNum;
    private String WorkFlowNodeName;
    private Object SAPStatus;
    private Object SAPCreateDate;

    public String getProcess(){
        if (TaskRunState == null) {
            return "";
        }else if (TaskRunState.equals("wait")) {
            return InstanceNodeName;
        }else if (TaskRunState.equals("end")){
            if (SubmitResult == null) {
                return "";
            }else if (SubmitResult.equals("驳回")){
                return "流程驳回";
            }else {
                return "流程通过";
            }
        }
        return "";
    }


    public boolean showDownload(){
        boolean isShow = false;
        if (TaskRunState != null && SubmitResult != null && TaskRunState.equals("end") && !SubmitResult.equals("驳回")) {
            isShow = true;
        }

        return isShow;
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

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getSalesmanUserName() {
        return SalesmanUserName;
    }

    public void setSalesmanUserName(String SalesmanUserName) {
        this.SalesmanUserName = SalesmanUserName;
    }

    public String getVersionNum() {
        return VersionNum;
    }

    public void setVersionNum(String VersionNum) {
        this.VersionNum = VersionNum;
    }

    public String getCreateDate() {
        if (CreateDate != null) {
            return CreateDate.substring(0, 10);
        }
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public boolean isValidState() {
        return ValidState;
    }

    public void setValidState(boolean ValidState) {
        this.ValidState = ValidState;
    }

    public double getPriceReviewState() {
        return PriceReviewState;
    }

    public void setPriceReviewState(double PriceReviewState) {
        this.PriceReviewState = PriceReviewState;
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
        return NodeCreateTime;
    }

    public void setNodeCreateTime(String NodeCreateTime) {
        this.NodeCreateTime = NodeCreateTime;
    }

    public String getProjectGuid() {
        return ProjectGuid;
    }

    public void setProjectGuid(String ProjectGuid) {
        this.ProjectGuid = ProjectGuid;
    }

    public String getProjectType() {
        return ProjectType;
    }

    public void setProjectType(String ProjectType) {
        this.ProjectType = ProjectType;
    }

    public Object getPRVersionNum() {
        return PRVersionNum;
    }

    public void setPRVersionNum(Object PRVersionNum) {
        this.PRVersionNum = PRVersionNum;
    }

    public String getWorkFlowNodeName() {
        return WorkFlowNodeName;
    }

    public void setWorkFlowNodeName(String WorkFlowNodeName) {
        this.WorkFlowNodeName = WorkFlowNodeName;
    }

    public Object getSAPStatus() {
        return SAPStatus;
    }

    public void setSAPStatus(Object SAPStatus) {
        this.SAPStatus = SAPStatus;
    }

    public Object getSAPCreateDate() {
        return SAPCreateDate;
    }

    public void setSAPCreateDate(Object SAPCreateDate) {
        this.SAPCreateDate = SAPCreateDate;
    }
}
