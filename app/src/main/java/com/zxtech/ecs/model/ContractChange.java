package com.zxtech.ecs.model;

import android.text.TextUtils;

import com.zxtech.ecs.util.DateUtil;

/**
 * Created by syp523 on 2018/6/28.
 */

public class ContractChange {


    /**
     * ProjectNo : 2018-00067
     * ProjectName : 0602流程测试10
     * CustomerName : 恒大
     * SalesmanUserName : 15840046701
     * BranchName : 沈阳分公司
     * BranchNo : 沈阳分公司
     * ProjectType : PT
     * Guid : 064f51b1-bcd4-44b5-a2c1-1c7c2a985c8b
     * ProjectGuid_Before : 84f76561-679e-45cd-973c-776e2d930975
     * ProjectGuid_After : b8da4540-ee8c-4bd4-a303-00b9f415f6f8
     * ContractGuid : 4301d485-89a0-4748-b986-6ca2feeac3b4
     * PR_ProductGuid : bcd88e39-2c2d-4774-8df0-ab6980d2466d
     * TypeId : 3
     * InstanceGuid : 52a24b27-7758-4c70-8d58-679cd01b27ec
     * ContractNo : GKLN2018-00067
     * VersionNum : A
     * State : 0
     * OrderNum :
     * ReasonOfChange : 客户原因
     * ProjectStates :
     * CreateUserGuid : facc8df7-5ab2-4568-9e36-0608f7492978
     * CreateDate : 2018/6/28 8:53:01
     * ExpectReceiveDate : 2018-06-30
     * Remarks_1 :
     * Remarks_2 :
     * TaskName : ECS-合同变更
     * TaskDescription :
     * StartTime : 2018/6/28 8:53:01
     * OrderCompleteTime :
     * CompleteTime :
     * TaskRunState : wait
     * InstanceNodeId : 91a01009-3829-4b20-b6f7-16044af51481
     * InstanceNodeName : 变更申请
     * RunState : wait
     * TransactUserNo : 15840046701
     * TransactDeptNo :
     * TransactDutyNo : HB-XSY-BGSQ
     * SubmitUserNo :
     * SubmitDeptNo :
     * NextNodeTransactUserNo :
     * NextNodeTransactDeptNo :
     * IsDeleted : False
     * BusinessState :
     * IsModified : False
     * SubmitOption : 提交
     * TriggerMark : a6a1d3e1-6d50-4feb-bb08-7258a968153e
     * InstanceId : 1f192dbd-1fce-4386-bc32-73de03b9a2c2
     * SubmitDutyNo :
     * SubmitResult :
     * NextNodeTransactDutyNo :
     * NodeInstanceNodeType : TransactNode
     * NodeCreateTime : 2018/6/28 8:53:01
     * MAXLevel : True
     */

    private String ProjectNo;
    private String ProjectName;
    private String CustomerName;
    private String SalesmanUserName;
    private String BranchName;
    private String BranchNo;
    private String ProjectType;
    private String Guid;
    private String ProjectGuid_Before;
    private String ProjectGuid_After;
    private String ContractGuid;
    private String PR_ProductGuid;
    private String TypeId;
    private String InstanceGuid;
    private String ContractNo;
    private String VersionNum;
    private String State;
    private String OrderNum;
    private String ReasonOfChange;
    private String ProjectStates;
    private String CreateUserGuid;
    private String CreateDate;
    private String ExpectReceiveDate;
    private String Remarks_1;
    private String Remarks_2;
    private String TaskName;
    private String TaskDescription;
    private String StartTime;
    private String OrderCompleteTime;
    private String CompleteTime;
    private String TaskRunState;
    private String InstanceNodeId;
    private String InstanceNodeName;
    private String RunState;
    private String TransactUserNo;
    private String TransactDeptNo;
    private String TransactDutyNo;
    private String SubmitUserNo;
    private String SubmitDeptNo;
    private String NextNodeTransactUserNo;
    private String NextNodeTransactDeptNo;
    private String IsDeleted;
    private String BusinessState;
    private String IsModified;
    private String SubmitOption;
    private String TriggerMark;
    private String InstanceId;
    private String SubmitDutyNo;
    private String SubmitResult;
    private String NextNodeTransactDutyNo;
    private String NodeInstanceNodeType;
    private String NodeCreateTime;
    private String MAXLevel;
    private String CreateTime;
    private String ElevatorNo;
    private String PlannedTime;//标准交期,
    private String ExpectedTime;//期望交期,
    private String RealTime;//实际交期

    public String getProcess() {
        if ("end".equals(TaskRunState)) {
            return "流程结束";
        } else {
            return InstanceNodeName;
        }
    }

    public String getBranchNo() {
        return BranchNo;
    }

    public void setBranchNo(String branchNo) {
        BranchNo = branchNo;
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

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
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

    public String getProjectType() {
        return ProjectType;
    }

    public void setProjectType(String ProjectType) {
        this.ProjectType = ProjectType;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getProjectGuid_Before() {
        return ProjectGuid_Before;
    }

    public void setProjectGuid_Before(String ProjectGuid_Before) {
        this.ProjectGuid_Before = ProjectGuid_Before;
    }

    public String getProjectGuid_After() {
        return ProjectGuid_After;
    }

    public void setProjectGuid_After(String ProjectGuid_After) {
        this.ProjectGuid_After = ProjectGuid_After;
    }

    public String getContractGuid() {
        return ContractGuid;
    }

    public void setContractGuid(String ContractGuid) {
        this.ContractGuid = ContractGuid;
    }

    public String getPR_ProductGuid() {
        return PR_ProductGuid;
    }

    public void setPR_ProductGuid(String PR_ProductGuid) {
        this.PR_ProductGuid = PR_ProductGuid;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String TypeId) {
        this.TypeId = TypeId;
    }

    public String getInstanceGuid() {
        return InstanceGuid;
    }

    public void setInstanceGuid(String InstanceGuid) {
        this.InstanceGuid = InstanceGuid;
    }

    public String getContractNo() {
        return ContractNo;
    }

    public void setContractNo(String ContractNo) {
        this.ContractNo = ContractNo;
    }

    public String getVersionNum() {
        return VersionNum;
    }

    public void setVersionNum(String VersionNum) {
        this.VersionNum = VersionNum;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(String OrderNum) {
        this.OrderNum = OrderNum;
    }

    public String getReasonOfChange() {
        return ReasonOfChange;
    }

    public void setReasonOfChange(String ReasonOfChange) {
        this.ReasonOfChange = ReasonOfChange;
    }

    public String getProjectStates() {
        return ProjectStates;
    }

    public void setProjectStates(String ProjectStates) {
        this.ProjectStates = ProjectStates;
    }

    public String getCreateUserGuid() {
        return CreateUserGuid;
    }

    public void setCreateUserGuid(String CreateUserGuid) {
        this.CreateUserGuid = CreateUserGuid;
    }

    public String getCreateDate() {
        return CreateDate != null ? CreateDate.substring(0, 10) : null;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public String getExpectReceiveDate() {
        return ExpectReceiveDate;
    }

    public void setExpectReceiveDate(String ExpectReceiveDate) {
        this.ExpectReceiveDate = ExpectReceiveDate;
    }

    public String getRemarks_1() {
        return Remarks_1;
    }

    public void setRemarks_1(String Remarks_1) {
        this.Remarks_1 = Remarks_1;
    }

    public String getRemarks_2() {
        return Remarks_2;
    }

    public void setRemarks_2(String Remarks_2) {
        this.Remarks_2 = Remarks_2;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String TaskName) {
        this.TaskName = TaskName;
    }

    public String getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(String TaskDescription) {
        this.TaskDescription = TaskDescription;
    }

    public String getStartTime() {
        return StartTime != null ? DateUtil.dateFormat(StartTime) : null;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getOrderCompleteTime() {
        return OrderCompleteTime;
    }

    public void setOrderCompleteTime(String OrderCompleteTime) {
        this.OrderCompleteTime = OrderCompleteTime;
    }

    public String getCompleteTime() {
        return CompleteTime;
    }

    public void setCompleteTime(String CompleteTime) {
        this.CompleteTime = CompleteTime;
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

    public String getTransactDeptNo() {
        return TransactDeptNo;
    }

    public void setTransactDeptNo(String TransactDeptNo) {
        this.TransactDeptNo = TransactDeptNo;
    }

    public String getTransactDutyNo() {
        return TransactDutyNo;
    }

    public void setTransactDutyNo(String TransactDutyNo) {
        this.TransactDutyNo = TransactDutyNo;
    }

    public String getSubmitUserNo() {
        return SubmitUserNo;
    }

    public void setSubmitUserNo(String SubmitUserNo) {
        this.SubmitUserNo = SubmitUserNo;
    }

    public String getSubmitDeptNo() {
        return SubmitDeptNo;
    }

    public void setSubmitDeptNo(String SubmitDeptNo) {
        this.SubmitDeptNo = SubmitDeptNo;
    }

    public String getNextNodeTransactUserNo() {
        return NextNodeTransactUserNo;
    }

    public void setNextNodeTransactUserNo(String NextNodeTransactUserNo) {
        this.NextNodeTransactUserNo = NextNodeTransactUserNo;
    }

    public String getNextNodeTransactDeptNo() {
        return NextNodeTransactDeptNo;
    }

    public void setNextNodeTransactDeptNo(String NextNodeTransactDeptNo) {
        this.NextNodeTransactDeptNo = NextNodeTransactDeptNo;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public String getBusinessState() {
        return BusinessState;
    }

    public void setBusinessState(String BusinessState) {
        this.BusinessState = BusinessState;
    }

    public String getIsModified() {
        return IsModified;
    }

    public void setIsModified(String IsModified) {
        this.IsModified = IsModified;
    }

    public String getSubmitOption() {
        return SubmitOption;
    }

    public void setSubmitOption(String SubmitOption) {
        this.SubmitOption = SubmitOption;
    }

    public String getTriggerMark() {
        return TriggerMark;
    }

    public void setTriggerMark(String TriggerMark) {
        this.TriggerMark = TriggerMark;
    }

    public String getInstanceId() {
        return InstanceId;
    }

    public void setInstanceId(String InstanceId) {
        this.InstanceId = InstanceId;
    }

    public String getSubmitDutyNo() {
        return SubmitDutyNo;
    }

    public void setSubmitDutyNo(String SubmitDutyNo) {
        this.SubmitDutyNo = SubmitDutyNo;
    }

    public String getSubmitResult() {
        return SubmitResult;
    }

    public void setSubmitResult(String SubmitResult) {
        this.SubmitResult = SubmitResult;
    }

    public String getNextNodeTransactDutyNo() {
        return NextNodeTransactDutyNo;
    }

    public void setNextNodeTransactDutyNo(String NextNodeTransactDutyNo) {
        this.NextNodeTransactDutyNo = NextNodeTransactDutyNo;
    }

    public String getNodeInstanceNodeType() {
        return NodeInstanceNodeType;
    }

    public void setNodeInstanceNodeType(String NodeInstanceNodeType) {
        this.NodeInstanceNodeType = NodeInstanceNodeType;
    }

    public String getNodeCreateTime() {
        return NodeCreateTime;
    }

    public void setNodeCreateTime(String NodeCreateTime) {
        this.NodeCreateTime = NodeCreateTime;
    }

    public String getMAXLevel() {
        return MAXLevel;
    }

    public void setMAXLevel(String MAXLevel) {
        this.MAXLevel = MAXLevel;
    }

    public String getCreateTime() {
        return CreateTime != null ? CreateTime.substring(0, 10) : null;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getElevatorNo() {
        return ElevatorNo;
    }

    public void setElevatorNo(String elevatorNo) {
        ElevatorNo = elevatorNo;
    }

    public String getPlannedTime() {

        return !TextUtils.isEmpty(PlannedTime) ? DateUtil.dateFormat(PlannedTime) : null;
    }

    public void setPlannedTime(String plannedTime) {
        PlannedTime = plannedTime;
    }

    public String getExpectedTime() {
        return !TextUtils.isEmpty(ExpectedTime)  ? DateUtil.dateFormat(ExpectedTime) : null;
    }

    public void setExpectedTime(String expectedTime) {
        ExpectedTime = expectedTime;
    }

    public String getRealTime() {
        return !TextUtils.isEmpty(RealTime) ? DateUtil.dateFormat(RealTime) : null;
    }

    public void setRealTime(String realTime) {
        RealTime = realTime;
    }
}
