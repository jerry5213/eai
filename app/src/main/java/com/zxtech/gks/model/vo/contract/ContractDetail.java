package com.zxtech.gks.model.vo.contract;

import java.util.List;

/**
 * Created by SYP521 on 2017/12/15.
 */

public class ContractDetail {

    private String AgentName;
    private String BranchName;
    private String ContractType;
    private boolean IsKQ;
    private String ProductList;
    private String ProjectName;
    private String ProjectNo;
    private String ProjectType;
    private String SalesmanUserName;
    private String TotalProductCount;
    private List<WorkFlowNode> WorkFlowNodeList;
    private List<FileData> fileList;
    private List<Config> configs;
    /**
     * PRProductId : ccc20adf-f63d-4105-a66d-8091a69e2b1e
     * ProjectGuid : a2bcea9b-ce7e-4440-9a81-0e47cd0f44d4
     * Agent : null
     * ContractGuid : 5e288a94-2f33-478f-a9cc-074da1de33b3
     * ElevatorCount : 1
     * ProductAndCount : STKJS10001 台、
     * EqContractType : null
     */

    private String PRProductId;
    private String ProjectGuid;
    private String Agent;
    private String ContractGuid;
    private String ElevatorCount;
    private String ProductAndCount;
    private String EqContractType;
    private String Remark;


    public String getAgentName() {
        return AgentName;
    }

    public String getBranchName() {
        return BranchName;
    }

    public String getContractType() {
        return ContractType;
    }

    public boolean isIsKQ() {
        return IsKQ;
    }

    public String getProductList() {
        return ProductList;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public String getProjectNo() {
        return ProjectNo;
    }

    public void setProjectNo(String ProjectNo) {
        this.ProjectNo = ProjectNo;
    }

    public String getProjectType() {
        return ProjectType;
    }

    public String getSalesmanUserName() {
        return SalesmanUserName;
    }

    public String getTotalProductCount() {
        return TotalProductCount;
    }

    public List<WorkFlowNode> getWorkFlowNodeList() {
        return WorkFlowNodeList;
    }

    public List<FileData> getFileList() {
        return fileList;
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public String getPRProductId() {
        return PRProductId;
    }

    public void setPRProductId(String PRProductId) {
        this.PRProductId = PRProductId;
    }

    public String getProjectGuid() {
        return ProjectGuid;
    }

    public void setProjectGuid(String ProjectGuid) {
        this.ProjectGuid = ProjectGuid;
    }

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String Agent) {
        this.Agent = Agent;
    }

    public String getContractGuid() {
        return ContractGuid;
    }

    public void setContractGuid(String ContractGuid) {
        this.ContractGuid = ContractGuid;
    }

    public String getElevatorCount() {
        return ElevatorCount;
    }

    public void setElevatorCount(String ElevatorCount) {
        this.ElevatorCount = ElevatorCount;
    }

    public String getProductAndCount() {
        return ProductAndCount;
    }

    public void setProductAndCount(String ProductAndCount) {
        this.ProductAndCount = ProductAndCount;
    }

    public String getEqContractType() {
        return EqContractType;
    }

    public void setEqContractType(String EqContractType) {
        this.EqContractType = EqContractType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
