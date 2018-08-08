package com.zxtech.gks.model.vo.PrProductDetail;

import android.text.TextUtils;

import com.zxtech.ecs.model.BidAttachment;
import com.zxtech.ecs.model.ProjectBid;
import com.zxtech.gks.model.vo.BaseVO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by syp521 on 2017/10/27.
 */

public class PrProductDetail extends BaseVO<PrProductDetail> implements Serializable {


    /**
     * AgentName :
     * Agreement : null
     * BidCost : null
     * BidPoundage : null
     * BranchName : 沈阳分公司
     * ContractParty :
     * CustomerName : 0529手机建
     * DepositType :
     * EqContractTypeName :
     * EquiObligatePriceForSale : 0
     * FloatRate : 0%
     * InContractTypeName :
     * InstOtherPriceForSale : 0
     * InstanceNodeRoleNo : JS-FZ-SP
     * IsFreeInsurance : 否
     * IsKQ : false
     * PayType :
     * PaymentType : null
     * PerformanceBondBidCost : null
     * PriceTableList : [{"CMIIRateInst":"0","EQSProductGuid":"f37e74ca-77c0-497f-8074-ddda8afc1b92","ElevatorProduct":"乘客电梯","ElevatorType":"STKJS1000(1150/1)//","FCCMII":"92,313","FCCMIIRate":"98.41","FCCMIIRateEqui":"98.41","FLCMII":"89,950","FLCMIIRate":"95.9","FLCMIIRateEqui":"95.9","FloatRateEqui":"0%","FloorAngle":"","ProductCount":"2","ProductNo":"2018-00036-01","RealPrice_Equi":"114,800","RealPrice_Inst":"0","TotalPrice":"229,600"},{"CMIIRateInst":"0","EQSProductGuid":"00000000-0000-0000-0000-000000000000","ElevatorProduct":"-","ElevatorType":"-","FCCMII":"184,626","FCCMIIRate":"98.41","FCCMIIRateEqui":"98.41","FLCMII":"179,899","FLCMIIRate":"95.9","FLCMIIRateEqui":"95.9","FloatRateEqui":"-","FloorAngle":"-","ProductCount":"2","ProductNo":"合计","RealPrice_Equi":"229,600","RealPrice_Inst":"0","TotalPrice":"229,600"}]
     * ProductList : 2台 STKJS1000,
     * ProjectAreaNameStr : null
     * ProjectName : 0602流程测试02
     * ProjectNo : 2018-00036
     * ProjectType : 普通
     * PromisesCount : null
     * PurchaseType : null
     * RecoveryTime : null
     * Remarks : null
     * ReturnCondition : null
     * SalesmanUserName : 15840046701
     * TotalProductCount : 2
     * WorkFlowNodeList : [{"CompleteTime":"/Date(1527906797207+0800)/","CreateTime":"/Date(1527906794823+0800)/","InstanceNodeName":"价审准备","SubmitDescription":"","SubmitResult":"通过","TransactUserName":"15840046701"},{"CompleteTime":null,"CreateTime":"/Date(1527906797323+0800)/","InstanceNodeName":"分总审批","SubmitDescription":null,"SubmitResult":"","TransactUserName":"分总"}]
     * permissionList : {"HiddenFunctionNo":"colCMIIRateInst,btnOpenFulChainDetail,colFCCMIIRateEqui,colFLCMIITopLast,colFCCMIITop,btnOpenCmiiFullChain,colFCCMII,colFCCMIIRate","DisabledFunctionNo":"divPrice, divPayment, divContractPrice, colFloatRateEqui, colFLCMIIRateEqui, colFLCMIIRate, colFLCMII, rowFloatRate, colFLCMIITop","VisibleFunctionNoList":"btnPRChildProduct, btnPRBidding, btnOpenPriceReviewInformation, divExaminationAndApproval, btnPRSaveApproval, btnPRProductNo, divApproval, btnWFSubmit, btnBackHome"}
     * submitOption : [{"Result":0,"ToNodeName":null},{"Result":2,"ToNodeName":null}]
     * transactDutyNo : JS-FZ-SP
     * chargeList : [{"TransactorUserName":null,"PriceReviewGuid":"00000000-0000-0000-0000-000000000000","TransactorGuid":"00000000-0000-0000-0000-000000000000","RoleNo":null,"TransactoResult":false,"AgentCommission":0,"AgentCommissionRate":0,"RealFloatingRate":0,"TransactorDate":"/Date(-62135596800000)/","ValidState":false}]
     */

    private String AgentName;
    private Object Agreement;
    private Object BidCost;
    private String BidPoundage;
    private String BranchName;
    private String ContractParty;
    private String CustomerName;
    private String DepositType;
    private String EqContractTypeName;
    private String EquiObligatePriceForSale;
    private String FloatRate;
    private String InContractTypeName;
    private String InstOtherPriceForSale;
    private String InstanceNodeRoleNo;
    private String IsFreeInsurance;
    private boolean IsKQ;
    private String PayType;
    private String PaymentType;
    private Object PerformanceBondBidCost;
    private String ProductList;
    private Object ProjectAreaNameStr;
    private String ProjectName;
    private String ProjectNo;
    private String ProjectType;
    private String PromisesCount;
    private String PurchaseType;
    private String RecoveryTime;
    private Object Remarks;
    private Object ReturnCondition;
    private String SalesmanUserName;
    private String TotalProductCount;
    private PermissionListBean permissionList;
    private String transactDutyNo;
    private List<PriceTableListBean> PriceTableList;
    private List<WorkFlowNodeListBean> WorkFlowNodeList;
    private List<SubmitOption> submitOption;
    private List<ServiceChargeVO> chargeList;
    private ProjectBid ProjectBid;
    private List<BidAttachment> DrawingFileList;

    public ProjectBid getProjectBid() {
        return ProjectBid;
    }

    public void setProjectBid(ProjectBid projectBid) {
        ProjectBid = projectBid;
    }

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String AgentName) {
        this.AgentName = AgentName;
    }

    public Object getAgreement() {
        return Agreement;
    }

    public void setAgreement(Object Agreement) {
        this.Agreement = Agreement;
    }

    public Object getBidCost() {
        return BidCost;
    }

    public void setBidCost(Object BidCost) {
        this.BidCost = BidCost;
    }

    public String getBidPoundage() {
        return BidPoundage;
    }

    public void setBidPoundage(String BidPoundage) {
        this.BidPoundage = BidPoundage;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public String getContractParty() {
        return ContractParty;
    }

    public void setContractParty(String ContractParty) {
        this.ContractParty = ContractParty;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getDepositType() {

        if (TextUtils.isEmpty(DepositType) || "--请选择--".equals(DepositType)) {
            return "";
        }
        return DepositType;
    }

    public void setDepositType(String DepositType) {
        this.DepositType = DepositType;
    }

    public String getEqContractTypeName() {
        return EqContractTypeName;
    }

    public void setEqContractTypeName(String EqContractTypeName) {
        this.EqContractTypeName = EqContractTypeName;
    }

    public String getEquiObligatePriceForSale() {
        return EquiObligatePriceForSale;
    }

    public void setEquiObligatePriceForSale(String EquiObligatePriceForSale) {
        this.EquiObligatePriceForSale = EquiObligatePriceForSale;
    }

    public String getFloatRate() {
        return FloatRate;
    }

    public void setFloatRate(String FloatRate) {
        this.FloatRate = FloatRate;
    }

    public String getInContractTypeName() {
        return InContractTypeName;
    }

    public void setInContractTypeName(String InContractTypeName) {
        this.InContractTypeName = InContractTypeName;
    }

    public String getInstOtherPriceForSale() {
        return InstOtherPriceForSale;
    }

    public void setInstOtherPriceForSale(String InstOtherPriceForSale) {
        this.InstOtherPriceForSale = InstOtherPriceForSale;
    }

    public String getInstanceNodeRoleNo() {
        return InstanceNodeRoleNo;
    }

    public void setInstanceNodeRoleNo(String InstanceNodeRoleNo) {
        this.InstanceNodeRoleNo = InstanceNodeRoleNo;
    }

    public String getIsFreeInsurance() {
        return IsFreeInsurance;
    }

    public void setIsFreeInsurance(String IsFreeInsurance) {
        this.IsFreeInsurance = IsFreeInsurance;
    }

    public boolean isIsKQ() {
        return IsKQ;
    }

    public void setIsKQ(boolean IsKQ) {
        this.IsKQ = IsKQ;
    }

    public String getPayType() {
        if (TextUtils.isEmpty(PayType) || "--请选择--".equals(PayType)) {
            return "";
        }
        return PayType;
    }

    public void setPayType(String PayType) {
        this.PayType = PayType;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String PaymentType) {
        this.PaymentType = PaymentType;
    }

    public Object getPerformanceBondBidCost() {
        return PerformanceBondBidCost;
    }

    public void setPerformanceBondBidCost(Object PerformanceBondBidCost) {
        this.PerformanceBondBidCost = PerformanceBondBidCost;
    }

    public String getProductList() {
        return ProductList;
    }

    public void setProductList(String ProductList) {
        this.ProductList = ProductList;
    }

    public Object getProjectAreaNameStr() {
        return ProjectAreaNameStr;
    }

    public void setProjectAreaNameStr(Object ProjectAreaNameStr) {
        this.ProjectAreaNameStr = ProjectAreaNameStr;
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

    public String getPromisesCount() {
        return PromisesCount;
    }

    public void setPromisesCount(String PromisesCount) {
        this.PromisesCount = PromisesCount;
    }

    public String getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(String PurchaseType) {
        this.PurchaseType = PurchaseType;
    }

    public String getRecoveryTime() {

        try {
            if (TextUtils.isEmpty(RecoveryTime)) {
                return "";
            }
            long timeInMills = Long.parseLong(RecoveryTime.substring(6, 19));
            return new SimpleDateFormat("yyyy/MM/dd").format(new Date(timeInMills));
        } catch (Exception e) {
            e.printStackTrace();
            return RecoveryTime;
        }
    }

    public void setRecoveryTime(String RecoveryTime) {
        this.RecoveryTime = RecoveryTime;
    }

    public Object getRemarks() {
        return Remarks;
    }

    public void setRemarks(Object Remarks) {
        this.Remarks = Remarks;
    }

    public Object getReturnCondition() {
        return ReturnCondition;
    }

    public void setReturnCondition(Object ReturnCondition) {
        this.ReturnCondition = ReturnCondition;
    }

    public String getSalesmanUserName() {
        return SalesmanUserName;
    }

    public void setSalesmanUserName(String SalesmanUserName) {
        this.SalesmanUserName = SalesmanUserName;
    }

    public String getTotalProductCount() {
        return TotalProductCount;
    }

    public void setTotalProductCount(String TotalProductCount) {
        this.TotalProductCount = TotalProductCount;
    }

    public PermissionListBean getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(PermissionListBean permissionList) {
        this.permissionList = permissionList;
    }

    public String getTransactDutyNo() {
        return transactDutyNo;
    }

    public void setTransactDutyNo(String transactDutyNo) {
        this.transactDutyNo = transactDutyNo;
    }

    public List<PriceTableListBean> getPriceTableList() {
        return PriceTableList;
    }

    public void setPriceTableList(List<PriceTableListBean> PriceTableList) {
        this.PriceTableList = PriceTableList;
    }

    public List<WorkFlowNodeListBean> getWorkFlowNodeList() {
        return WorkFlowNodeList;
    }

    public void setWorkFlowNodeList(List<WorkFlowNodeListBean> WorkFlowNodeList) {
        this.WorkFlowNodeList = WorkFlowNodeList;
    }


    public List<SubmitOption> getSubmitOption() {
        return submitOption;
    }

    public void setSubmitOption(List<SubmitOption> submitOption) {
        this.submitOption = submitOption;
    }

    public List<ServiceChargeVO> getChargeList() {
        return chargeList;
    }

    public void setChargeList(List<ServiceChargeVO> chargeList) {
        this.chargeList = chargeList;
    }
}
