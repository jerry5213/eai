package com.zxtech.gks.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by syp521 on 2017/10/27.
 */

public class PrProductDetail implements Serializable {

    private String AgentName;
    private String Agreement;
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
    private List<PriceTableListBean> PriceTableList;
    private List<WorkFlowNodeListBean> WorkFlowNodeList;

    public String getPurchaseType() {
        return PurchaseType;
    }

    public String getBidPoundage() {
        return BidPoundage;
    }

    public void setBidPoundage(String bidPoundage) {
        BidPoundage = bidPoundage;
    }

    public String getProjectNo() {
        return ProjectNo;
    }

    public void setProjectNo(String projectNo) {
        ProjectNo = projectNo;
    }

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String AgentName) {
        this.AgentName = AgentName;
    }

    public String getAgreement() {
        return Agreement;
    }

    public void setAgreement(String Agreement) {
        this.Agreement = Agreement;
    }

    public Object getBidCost() {
        return BidCost;
    }

    public void setBidCost(Object BidCost) {
        this.BidCost = BidCost;
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

    public static class PriceTableListBean implements Serializable {
        /**
         * CMIIRateInst : 0
         * ElevatorProduct : 乘客电梯
         * ElevatorType : GPN60K
         * FCCMII : 173,447
         * FCCMIIRate : 70.24
         * FCCMIIRateEqui : 108.21
         * FLCMII : 160,283
         * FLCMIIRate : 64.91
         * FLCMIIRateEqui : 64.91
         * FloatRateEqui : 0%
         * FloorAngle : 15/15/-
         * ProductCount : 1
         * ProductNo : XJ2017-04559-01
         * RealPrice_Equi : 299,030
         * RealPrice_Inst : 0
         * TotalPrice : 299,030
         */

        private String CMIIRateInst;
        private String ElevatorProduct;
        private String ElevatorType;
        private String FCCMII;
        private String FCCMIIRate;
        private String FCCMIIRateEqui;
        private String FLCMII;
        private String FLCMIIRate;
        private String FLCMIIRateEqui;
        private String FloatRateEqui;
        private String FloorAngle;
        private String ProductCount;
        private String ProductNo;
        private String RealPrice_Equi;
        private String RealPrice_Inst;
        private String TotalPrice;

        public String getCMIIRateInst() {
            return CMIIRateInst;
        }

        public void setCMIIRateInst(String CMIIRateInst) {
            this.CMIIRateInst = CMIIRateInst;
        }

        public String getElevatorProduct() {
            return ElevatorProduct;
        }

        public void setElevatorProduct(String ElevatorProduct) {
            this.ElevatorProduct = ElevatorProduct;
        }

        public String getElevatorType() {
            return ElevatorType;
        }

        public void setElevatorType(String ElevatorType) {
            this.ElevatorType = ElevatorType;
        }

        public String getFCCMII() {
            return FCCMII;
        }

        public void setFCCMII(String FCCMII) {
            this.FCCMII = FCCMII;
        }

        public String getFCCMIIRate() {
            return FCCMIIRate;
        }

        public void setFCCMIIRate(String FCCMIIRate) {
            this.FCCMIIRate = FCCMIIRate;
        }

        public String getFCCMIIRateEqui() {
            return FCCMIIRateEqui;
        }

        public void setFCCMIIRateEqui(String FCCMIIRateEqui) {
            this.FCCMIIRateEqui = FCCMIIRateEqui;
        }

        public String getFLCMII() {
            return FLCMII;
        }

        public void setFLCMII(String FLCMII) {
            this.FLCMII = FLCMII;
        }

        public String getFLCMIIRate() {
            return FLCMIIRate;
        }

        public void setFLCMIIRate(String FLCMIIRate) {
            this.FLCMIIRate = FLCMIIRate;
        }

        public String getFLCMIIRateEqui() {
            return FLCMIIRateEqui;
        }

        public void setFLCMIIRateEqui(String FLCMIIRateEqui) {
            this.FLCMIIRateEqui = FLCMIIRateEqui;
        }

        public String getFloatRateEqui() {
            return FloatRateEqui;
        }

        public void setFloatRateEqui(String FloatRateEqui) {
            this.FloatRateEqui = FloatRateEqui;
        }

        public String getFloorAngle() {
            return FloorAngle;
        }

        public void setFloorAngle(String FloorAngle) {
            this.FloorAngle = FloorAngle;
        }

        public String getProductCount() {
            return ProductCount;
        }

        public void setProductCount(String ProductCount) {
            this.ProductCount = ProductCount;
        }

        public String getProductNo() {
            return ProductNo;
        }

        public void setProductNo(String ProductNo) {
            this.ProductNo = ProductNo;
        }

        public String getRealPrice_Equi() {
            return RealPrice_Equi;
        }

        public void setRealPrice_Equi(String RealPrice_Equi) {
            this.RealPrice_Equi = RealPrice_Equi;
        }

        public String getRealPrice_Inst() {
            return RealPrice_Inst;
        }

        public void setRealPrice_Inst(String RealPrice_Inst) {
            this.RealPrice_Inst = RealPrice_Inst;
        }

        public String getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(String TotalPrice) {
            this.TotalPrice = TotalPrice;
        }
    }

    public static class WorkFlowNodeListBean {
        /**
         * CompleteTime : /Date(1508309078807+0800)/
         * CreateTime : /Date(1508309075993+0800)/
         * InstanceNodeName : 价审准备
         * SubmitDescription :
         * SubmitResult : 通过
         * TransactUserName : 上海鹏阳电梯有限公司
         */

        private String CompleteTime;
        private String CreateTime;
        private String InstanceNodeName;
        private String SubmitDescription;
        private String SubmitResult;
        private String TransactUserName;

        public String getCompleteTime() {

            try {
                long timeInMills = Long.parseLong(CompleteTime.substring(6, 19));
                return new SimpleDateFormat("yyyy/MM/dd").format(new Date(timeInMills));
            } catch (Exception e) {
                e.printStackTrace();
                return CompleteTime;
            }
        }

        public void setCompleteTime(String CompleteTime) {
            this.CompleteTime = CompleteTime;
        }

        public String getCreateTime() {

            try {
                long timeInMills = Long.parseLong(CreateTime.substring(6, 19));
                return new SimpleDateFormat("yyyy/MM/dd").format(new Date(timeInMills));
            } catch (Exception e) {
                e.printStackTrace();
                return CreateTime;
            }
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getInstanceNodeName() {
            return InstanceNodeName;
        }

        public void setInstanceNodeName(String InstanceNodeName) {
            this.InstanceNodeName = InstanceNodeName;
        }

        public String getSubmitDescription() {
            return SubmitDescription;
        }

        public void setSubmitDescription(String SubmitDescription) {
            this.SubmitDescription = SubmitDescription;
        }

        public String getSubmitResult() {
            return SubmitResult;
        }

        public void setSubmitResult(String SubmitResult) {
            this.SubmitResult = SubmitResult;
        }

        public String getTransactUserName() {
            return TransactUserName;
        }

        public void setTransactUserName(String TransactUserName) {
            this.TransactUserName = TransactUserName;
        }
    }
}
