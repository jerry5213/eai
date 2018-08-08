package com.zxtech.ecs.model;

import java.util.List;

/**
 * Created by syp523 on 2018/7/26.
 */

public class BidDetail {


    /**
     * CustomerName : 不要动这条数据
     * ProjectName : 新流程报备测试2
     * ProjectNo : 2018-00788
     * ProjectType : 普通
     * AgentName : 代理商测试_01
     * EqContractTypeName : 代理
     * BranchName : 沈阳分公司
     * SalesmanUserName : 13390247563
     * ProductList : 1台 STKJS1000,
     * TotalProductCount : 1
     * InContractTypeName : 经销
     * ContractParty : 11%
     * ProjectAreaNameStr : null
     * IsFreeInsurance : 是
     * Agreement : 否
     * PromisesCount : 11
     * PaymentType : 设备付款:
     1、 合同签订后 0 天内，支付设备总价款 100 %作为定金。
     安装付款:
     1、 发货期前 0 天，支付安装总价款 100 %。
     * Remarks : null
     * FloatRate : -4.68%
     * IsKQ : null
     * WorkFlowNodeList : null
     * BidCost : null
     * DepositType : null
     * RecoveryTime : null
     * PerformanceBondBidCost : null
     * PayType : null
     * ReturnCondition : null
     * BidPoundage : null
     * EquiObligatePriceForSale : 0
     * InstOtherPriceForSale : 0
     * PurchaseType : 招投标
     * InstanceNodeRoleNo : null
     */

    private String CustomerName;
    private String ProjectName;
    private String ProjectNo;
    private String ProjectType;
    private String AgentName;
    private String EqContractTypeName;
    private String BranchName;
    private String SalesmanUserName;
    private String ProductList;
    private String TotalProductCount;
    private String InContractTypeName;
    private String ContractParty;
    private String ProjectAreaNameStr;
    private String ProjectAdd_Province;
    private String ProjectAdd_City;
    private String ProjectAdd_Area;
    private String ProjectAdd_Other;
    private String IsFreeInsurance;
    private String Agreement;
    private String PromisesCount;
    private String PaymentType;
    private Object Remarks;
    private String FloatRate;
    private Object IsKQ;
    private Object WorkFlowNodeList;
    private Object BidCost;
    private Object DepositType;
    private Object RecoveryTime;
    private Object PerformanceBondBidCost;
    private Object PayType;
    private Object ReturnCondition;
    private Object BidPoundage;
    private String EquiObligatePriceForSale;
    private String InstOtherPriceForSale;
    private String PurchaseType;
    private Object InstanceNodeRoleNo;
    private ProjectBid ProjectBid;
    private List<BidAttachment> DrawingFileList;

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

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
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

    public String getProjectType() {
        return ProjectType;
    }

    public void setProjectType(String ProjectType) {
        this.ProjectType = ProjectType;
    }

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String AgentName) {
        this.AgentName = AgentName;
    }

    public String getEqContractTypeName() {
        return EqContractTypeName;
    }

    public void setEqContractTypeName(String EqContractTypeName) {
        this.EqContractTypeName = EqContractTypeName;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public String getSalesmanUserName() {
        return SalesmanUserName;
    }

    public void setSalesmanUserName(String SalesmanUserName) {
        this.SalesmanUserName = SalesmanUserName;
    }

    public String getProductList() {
        return ProductList;
    }

    public void setProductList(String ProductList) {
        this.ProductList = ProductList;
    }

    public String getTotalProductCount() {
        return TotalProductCount;
    }

    public void setTotalProductCount(String TotalProductCount) {
        this.TotalProductCount = TotalProductCount;
    }

    public String getInContractTypeName() {
        return InContractTypeName;
    }

    public void setInContractTypeName(String InContractTypeName) {
        this.InContractTypeName = InContractTypeName;
    }

    public String getContractParty() {
        return ContractParty;
    }

    public void setContractParty(String ContractParty) {
        this.ContractParty = ContractParty;
    }

    public String getProjectAreaNameStr() {
        return ProjectAreaNameStr;
    }

    public void setProjectAreaNameStr(String ProjectAreaNameStr) {
        this.ProjectAreaNameStr = ProjectAreaNameStr;
    }

    public String getIsFreeInsurance() {
        return IsFreeInsurance;
    }

    public void setIsFreeInsurance(String IsFreeInsurance) {
        this.IsFreeInsurance = IsFreeInsurance;
    }

    public String getAgreement() {
        return Agreement;
    }

    public void setAgreement(String Agreement) {
        this.Agreement = Agreement;
    }

    public String getPromisesCount() {
        return PromisesCount;
    }

    public void setPromisesCount(String PromisesCount) {
        this.PromisesCount = PromisesCount;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String PaymentType) {
        this.PaymentType = PaymentType;
    }

    public Object getRemarks() {
        return Remarks;
    }

    public void setRemarks(Object Remarks) {
        this.Remarks = Remarks;
    }

    public String getFloatRate() {
        return FloatRate;
    }

    public void setFloatRate(String FloatRate) {
        this.FloatRate = FloatRate;
    }

    public Object getIsKQ() {
        return IsKQ;
    }

    public void setIsKQ(Object IsKQ) {
        this.IsKQ = IsKQ;
    }

    public Object getWorkFlowNodeList() {
        return WorkFlowNodeList;
    }

    public void setWorkFlowNodeList(Object WorkFlowNodeList) {
        this.WorkFlowNodeList = WorkFlowNodeList;
    }

    public Object getBidCost() {
        return BidCost;
    }

    public void setBidCost(Object BidCost) {
        this.BidCost = BidCost;
    }

    public Object getDepositType() {
        return DepositType;
    }

    public void setDepositType(Object DepositType) {
        this.DepositType = DepositType;
    }

    public Object getRecoveryTime() {
        return RecoveryTime;
    }

    public void setRecoveryTime(Object RecoveryTime) {
        this.RecoveryTime = RecoveryTime;
    }

    public Object getPerformanceBondBidCost() {
        return PerformanceBondBidCost;
    }

    public void setPerformanceBondBidCost(Object PerformanceBondBidCost) {
        this.PerformanceBondBidCost = PerformanceBondBidCost;
    }

    public Object getPayType() {
        return PayType;
    }

    public void setPayType(Object PayType) {
        this.PayType = PayType;
    }

    public Object getReturnCondition() {
        return ReturnCondition;
    }

    public void setReturnCondition(Object ReturnCondition) {
        this.ReturnCondition = ReturnCondition;
    }

    public Object getBidPoundage() {
        return BidPoundage;
    }

    public void setBidPoundage(Object BidPoundage) {
        this.BidPoundage = BidPoundage;
    }

    public String getEquiObligatePriceForSale() {
        return EquiObligatePriceForSale;
    }

    public void setEquiObligatePriceForSale(String EquiObligatePriceForSale) {
        this.EquiObligatePriceForSale = EquiObligatePriceForSale;
    }

    public String getInstOtherPriceForSale() {
        return InstOtherPriceForSale;
    }

    public void setInstOtherPriceForSale(String InstOtherPriceForSale) {
        this.InstOtherPriceForSale = InstOtherPriceForSale;
    }

    public String getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(String PurchaseType) {
        this.PurchaseType = PurchaseType;
    }

    public Object getInstanceNodeRoleNo() {
        return InstanceNodeRoleNo;
    }

    public void setInstanceNodeRoleNo(Object InstanceNodeRoleNo) {
        this.InstanceNodeRoleNo = InstanceNodeRoleNo;
    }

    public com.zxtech.ecs.model.ProjectBid getProjectBid() {
        return ProjectBid;
    }

    public void setProjectBid(com.zxtech.ecs.model.ProjectBid projectBid) {
        ProjectBid = projectBid;
    }

    public List<BidAttachment> getDrawingFileList() {
        return DrawingFileList;
    }

    public void setDrawingFileList(List<BidAttachment> drawingFileList) {
        DrawingFileList = drawingFileList;
    }
}
