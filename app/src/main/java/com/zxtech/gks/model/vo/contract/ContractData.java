package com.zxtech.gks.model.vo.contract;

/**
 * Created by SYP521 on 2017/12/13.
 */

public class ContractData {


    /**
     * ProjectNo : GK2018-01148
     * ProjectName : 0409苹果手机测试
     * CustomerName : 0409苹果手机建
     * SalesmanUserName : BF销售员
     * BranchName : 沈阳分公司
     * ProjectType : PT
     * ContractGuid : 052647bb-13a3-4a81-ba57-e1d326e95ca3
     * ProjectGuid : da82ac6b-04a4-4e8f-9722-d0de0066cea5
     * PR_ProductGuid : 7cd9d667-cab3-43dc-8cd2-3c7df18bfdbb
     * InstanceGuid : 9808281c-6107-44f2-9a0d-574a4b192879
     * ContractNo : GKLN2018-01148
     * ContractVersion : A
     * ContractType : 1
     * ContractState : 0
     * Remark : null
     * IsImportSAP : null
     * IsChange : false
     * CreateUser : b52b98da-d113-4194-8cd2-e6e290f0c3c1
     * CreateTime : 2018-05-24T11:58:27.733
     * ModifyUser : null
     * ModifyTime : null
     * ContractIsDeleted : false
     * TaskName : ECS-合同评审
     * TaskDescription : null
     * StartTime : 2018-05-24T11:58:26.747
     * OrderCompleteTime : null
     * CompleteTime : null
     * TaskRunState : wait
     * InstanceNodeId : eebfd73f-aa9e-4627-865c-c5689bcddb90
     * InstanceNodeName : 启动
     * RunState : end
     * TransactUserNo : 28503915
     * TransactDeptNo :
     * TransactDutyNo :
     * SubmitUserNo : 28503915
     * SubmitDeptNo : null
     * NextNodeTransactUserNo : null
     * NextNodeTransactDeptNo : null
     * IsDeleted : false
     * BusinessState : null
     * IsModified : false
     * SubmitOption : 人工启动
     * TriggerMark : 00000000-0000-0000-0000-000000000000
     * InstanceId : f3e2c068-604a-46e7-93e5-a374e4c94ba4
     * SubmitDutyNo : null
     * SubmitResult : 人工启动
     * NextNodeTransactDutyNo : null
     * NodeInstanceNodeType : StartNode
     * NodeCreateTime : 2018-05-24T11:58:27.007
     * HighestVersion : true
     * ContractInstanceState : null
     */

    private String ProjectNo;
    private String ProjectName;
    private String CustomerName;
    private String SalesmanUserName;
    private String BranchNo;
    private String BranchName;
    private String ProjectType;
    private String ContractGuid;
    private String ProjectGuid;
    private String PR_ProductGuid;
    private String InstanceGuid;
    private String ContractNo;
    private String ContractVersion;
    private int ContractType;
    private String ContractState;
    private Object Remark;
    private Object IsImportSAP;
    private String IsChange;
    private String CreateUser;
    private String CreateTime;
    private Object ModifyUser;
    private Object ModifyTime;
    private String ContractIsDeleted;
    private String TaskName;
    private Object TaskDescription;
    private String StartTime;
    private Object OrderCompleteTime;
    private Object CompleteTime;
    private String TaskRunState;
    private String InstanceNodeId;
    private String InstanceNodeName;
    private String RunState;
    private String TransactUserNo;
    private String TransactDeptNo;
    private String TransactDutyNo;
    private String SubmitUserNo;
    private Object SubmitDeptNo;
    private Object NextNodeTransactUserNo;
    private Object NextNodeTransactDeptNo;
    private String IsDeleted;
    private Object BusinessState;
    private String IsModified;
    private String SubmitOption;
    private String TriggerMark;
    private String InstanceId;
    private Object SubmitDutyNo;
    private String SubmitResult;
    private Object NextNodeTransactDutyNo;
    private String NodeInstanceNodeType;
    private String NodeCreateTime;
    private String HighestVersion;
    private Object ContractInstanceState;
    private String Guid;
    private String ProjectAdd_Province;
    private String ProjectAdd_City;
    private String ProjectAdd_Area;
    private String ProjectAdd_Other;

    public String getProcess() {
        if (TaskRunState == null) {
            return "";
        } else if (TaskRunState.equals("wait")) {
            return InstanceNodeName;
        } else if (TaskRunState.equals("end")) {
            return "流程结束";
        }
        return "";
    }


    public boolean showDownload() {
        boolean isShow = false;
        if (TaskRunState != null && TaskRunState.equals("end")) {
            isShow = true;
        }

        return isShow;
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

    public String getContractGuid() {
        return ContractGuid;
    }

    public void setContractGuid(String ContractGuid) {
        this.ContractGuid = ContractGuid;
    }

    public String getProjectGuid() {
        return ProjectGuid;
    }

    public void setProjectGuid(String ProjectGuid) {
        this.ProjectGuid = ProjectGuid;
    }

    public String getPR_ProductGuid() {
        return PR_ProductGuid;
    }

    public void setPR_ProductGuid(String PR_ProductGuid) {
        this.PR_ProductGuid = PR_ProductGuid;
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

    public String getContractVersion() {
        return ContractVersion;
    }

    public void setContractVersion(String ContractVersion) {
        this.ContractVersion = ContractVersion;
    }

    public int getContractType() {
        return ContractType;
    }

    public void setContractType(int ContractType) {
        this.ContractType = ContractType;
    }

    public String getContractState() {
        return ContractState;
    }

    public void setContractState(String ContractState) {
        this.ContractState = ContractState;
    }

    public Object getRemark() {
        return Remark;
    }

    public void setRemark(Object Remark) {
        this.Remark = Remark;
    }

    public Object getIsImportSAP() {
        return IsImportSAP;
    }

    public void setIsImportSAP(Object IsImportSAP) {
        this.IsImportSAP = IsImportSAP;
    }

    public String getIsChange() {
        return IsChange;
    }

    public void setIsChange(String IsChange) {
        this.IsChange = IsChange;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String CreateUser) {
        this.CreateUser = CreateUser;
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

    public Object getModifyUser() {
        return ModifyUser;
    }

    public void setModifyUser(Object ModifyUser) {
        this.ModifyUser = ModifyUser;
    }

    public Object getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(Object ModifyTime) {
        this.ModifyTime = ModifyTime;
    }

    public String getContractIsDeleted() {
        return ContractIsDeleted;
    }

    public void setContractIsDeleted(String ContractIsDeleted) {
        this.ContractIsDeleted = ContractIsDeleted;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String TaskName) {
        this.TaskName = TaskName;
    }

    public Object getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(Object TaskDescription) {
        this.TaskDescription = TaskDescription;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public Object getOrderCompleteTime() {
        return OrderCompleteTime;
    }

    public void setOrderCompleteTime(Object OrderCompleteTime) {
        this.OrderCompleteTime = OrderCompleteTime;
    }

    public Object getCompleteTime() {
        return CompleteTime;
    }

    public void setCompleteTime(Object CompleteTime) {
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

    public Object getSubmitDeptNo() {
        return SubmitDeptNo;
    }

    public void setSubmitDeptNo(Object SubmitDeptNo) {
        this.SubmitDeptNo = SubmitDeptNo;
    }

    public Object getNextNodeTransactUserNo() {
        return NextNodeTransactUserNo;
    }

    public void setNextNodeTransactUserNo(Object NextNodeTransactUserNo) {
        this.NextNodeTransactUserNo = NextNodeTransactUserNo;
    }

    public Object getNextNodeTransactDeptNo() {
        return NextNodeTransactDeptNo;
    }

    public void setNextNodeTransactDeptNo(Object NextNodeTransactDeptNo) {
        this.NextNodeTransactDeptNo = NextNodeTransactDeptNo;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public Object getBusinessState() {
        return BusinessState;
    }

    public void setBusinessState(Object BusinessState) {
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

    public Object getSubmitDutyNo() {
        return SubmitDutyNo;
    }

    public void setSubmitDutyNo(Object SubmitDutyNo) {
        this.SubmitDutyNo = SubmitDutyNo;
    }

    public String getSubmitResult() {
        return SubmitResult;
    }

    public void setSubmitResult(String SubmitResult) {
        this.SubmitResult = SubmitResult;
    }

    public Object getNextNodeTransactDutyNo() {
        return NextNodeTransactDutyNo;
    }

    public void setNextNodeTransactDutyNo(Object NextNodeTransactDutyNo) {
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

    public String getHighestVersion() {
        return HighestVersion;
    }

    public void setHighestVersion(String HighestVersion) {
        this.HighestVersion = HighestVersion;
    }

    public Object getContractInstanceState() {
        return ContractInstanceState;
    }

    public void setContractInstanceState(Object ContractInstanceState) {
        this.ContractInstanceState = ContractInstanceState;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getBranchNo() {
        return BranchNo;
    }

    public void setBranchNo(String branchNo) {
        BranchNo = branchNo;
    }

    public String getProjectAdd_Province() {
        return ProjectAdd_Province;
    }

    public void setProjectAdd_Province(String projectAdd_Province) {
        ProjectAdd_Province = projectAdd_Province;
    }

    public String getProjectAdd_City() {
        return ProjectAdd_City;
    }

    public void setProjectAdd_City(String projectAdd_City) {
        ProjectAdd_City = projectAdd_City;
    }

    public String getProjectAdd_Area() {
        return ProjectAdd_Area;
    }

    public void setProjectAdd_Area(String projectAdd_Area) {
        ProjectAdd_Area = projectAdd_Area;
    }

    public String getProjectAdd_Other() {
        return ProjectAdd_Other;
    }

    public void setProjectAdd_Other(String projectAdd_Other) {
        ProjectAdd_Other = projectAdd_Other;
    }

    public String getAddress(){
        String ads = "";
        if (ProjectAdd_Province != null) {
            ads += ProjectAdd_Province;
        }
        if (ProjectAdd_City != null) {
            ads += ProjectAdd_City;
        }
        if (ProjectAdd_Area != null) {
            ads += ProjectAdd_Area;
        }
        if (ProjectAdd_Other != null) {
            ads += ProjectAdd_Other;
        }
        return ads;
    }
}
