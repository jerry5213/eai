package com.zxtech.ecs.model;

import java.util.List;

/**
 * Created by syp521 on 2018/4/6.
 */

public class ProjectProductInfo {

    private ProjectInfo returnProject;
    private OtherInfo projectInfo;
    private String agentName;
    private String payTypeParam;
    private ProjectBid projectBid;
    private List<BidAttachment> DrawingFileList;

    public ProjectInfo getReturnProject() {
        return returnProject;
    }

    public void setReturnProject(ProjectInfo returnProject) {
        this.returnProject = returnProject;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public OtherInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(OtherInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    public String getPayTypeParam() {
        return payTypeParam;
    }

    public void setPayTypeParam(String payTypeParam) {
        this.payTypeParam = payTypeParam;
    }

    public ProjectBid getProjectBid() {
        return projectBid;
    }

    public void setProjectBid(ProjectBid projectBid) {
        this.projectBid = projectBid;
    }

    public List<BidAttachment> getDrawingFileList() {
        return DrawingFileList;
    }

    public void setDrawingFileList(List<BidAttachment> drawingFileList) {
        DrawingFileList = drawingFileList;
    }

    public static class OtherInfo {

        /**
         * Guid : 400aa529-0f1e-4203-914e-4f69ce425949
         * CompetitorInfo : null
         * Remarks : null
         * IsBid : null
         * BidCost : null
         * IsChangePerformanceBond : null
         * RecoveryTime : null
         * DepositType : null
         * Payee : null
         * CutOffTime : null
         * IsPerformanceBond : null
         * PerformanceBondBidCost : null
         * PayType : null
         * AfterPayCost : null
         * LetterOfIndemnityFormat : null
         * LetterOfIndemnityTerm : null
         * ReturnCondition : null
         * PaymentType : null
         * BreachRemarks : null
         * ProjectGuid : fd013891-c9cf-4848-b42c-19df4da3368e
         * PBPayee : null
         * PBRemarks : null
         * tb_Project : null
         */

        private String Guid;
        private String CompetitorInfo;
        private String Remarks;
        private String IsBid;
        private String BidCost;
        private String IsChangePerformanceBond;
        private String RecoveryTime;
        private String DepositType;
        private String Payee;
        private String CutOffTime;
        private String IsPerformanceBond;
        private String PerformanceBondBidCost;
        private String PayType;
        private String AfterPayCost;
        private String LetterOfIndemnityFormat;
        private String LetterOfIndemnityTerm;
        private String LetterOfIndemnityTermNotMap;
        private String ReturnCondition;
        private String PaymentType;
        private String BreachRemarks;
        private String ProjectGuid;
        private String PBPayee;
        private String PBRemarks;
        private String tb_Project;

        public String getGuid() {
            return Guid;
        }

        public void setGuid(String Guid) {
            this.Guid = Guid;
        }

        public String getCompetitorInfo() {
            return CompetitorInfo;
        }

        public void setCompetitorInfo(String CompetitorInfo) {
            this.CompetitorInfo = CompetitorInfo;
        }

        public String getRemarks() {
            return Remarks;
        }

        public void setRemarks(String Remarks) {
            this.Remarks = Remarks;
        }

        public String getIsBid() {
            return IsBid;
        }

        public void setIsBid(String IsBid) {
            this.IsBid = IsBid;
        }

        public String getBidCost() {
            return BidCost;
        }

        public void setBidCost(String BidCost) {
            this.BidCost = BidCost;
        }

        public String getIsChangePerformanceBond() {
            return IsChangePerformanceBond;
        }

        public void setIsChangePerformanceBond(String IsChangePerformanceBond) {
            this.IsChangePerformanceBond = IsChangePerformanceBond;
        }

        public String getRecoveryTime() {
            return RecoveryTime;
        }

        public void setRecoveryTime(String RecoveryTime) {
            this.RecoveryTime = RecoveryTime;
        }

        public String getDepositType() {
            return DepositType;
        }

        public void setDepositType(String DepositType) {
            this.DepositType = DepositType;
        }

        public String getPayee() {
            return Payee;
        }

        public void setPayee(String Payee) {
            this.Payee = Payee;
        }

        public String getCutOffTime() {
            return CutOffTime;
        }

        public void setCutOffTime(String CutOffTime) {
            this.CutOffTime = CutOffTime;
        }

        public String getIsPerformanceBond() {
            return IsPerformanceBond;
        }

        public void setIsPerformanceBond(String IsPerformanceBond) {
            this.IsPerformanceBond = IsPerformanceBond;
        }

        public String getPerformanceBondBidCost() {
            return PerformanceBondBidCost;
        }

        public void setPerformanceBondBidCost(String PerformanceBondBidCost) {
            this.PerformanceBondBidCost = PerformanceBondBidCost;
        }

        public String getPayType() {
            return PayType;
        }

        public String getPayTypeText() {
            if ("0".equals(PayType)) {
                return "请选择";
            }else if ("1".equals(PayType)) {
                return "保函";
            }else if ("2".equals(PayType)){
                return "现金";
            }
            return PayType;
        }

        public void setPayType(String PayType) {
            this.PayType = PayType;
        }

        public String getAfterPayCost() {
            return AfterPayCost;
        }

        public void setAfterPayCost(String AfterPayCost) {
            this.AfterPayCost = AfterPayCost;
        }

        public String getLetterOfIndemnityFormat() {
            return LetterOfIndemnityFormat;
        }

        public String getLetterOfIndemnityFormatText() {
            if ("0".equals(LetterOfIndemnityFormat)) {
                return "请选择";
            }else if ("1".equals(LetterOfIndemnityFormat)) {
                return "标准";
            }else if ("2".equals(LetterOfIndemnityFormat)) {
                return "客户";
            }
            return LetterOfIndemnityFormat;
        }

        public void setLetterOfIndemnityFormat(String LetterOfIndemnityFormat) {
            this.LetterOfIndemnityFormat = LetterOfIndemnityFormat;
        }

        public String getLetterOfIndemnityTerm() {
            return LetterOfIndemnityTerm != null ? LetterOfIndemnityTerm.substring(0,10) : null;
        }

        public void setLetterOfIndemnityTerm(String LetterOfIndemnityTerm) {
            this.LetterOfIndemnityTerm = LetterOfIndemnityTerm;
        }

        public String getReturnCondition() {
            return ReturnCondition;
        }

        public void setReturnCondition(String ReturnCondition) {
            this.ReturnCondition = ReturnCondition;
        }

        public String getPaymentType() {
            return PaymentType;
        }

        public void setPaymentType(String PaymentType) {
            this.PaymentType = PaymentType;
        }

        public String getBreachRemarks() {
            return BreachRemarks;
        }

        public void setBreachRemarks(String BreachRemarks) {
            this.BreachRemarks = BreachRemarks;
        }

        public String getProjectGuid() {
            return ProjectGuid;
        }

        public void setProjectGuid(String ProjectGuid) {
            this.ProjectGuid = ProjectGuid;
        }

        public String getPBPayee() {
            return PBPayee;
        }

        public void setPBPayee(String PBPayee) {
            this.PBPayee = PBPayee;
        }

        public String getPBRemarks() {
            return PBRemarks;
        }

        public void setPBRemarks(String PBRemarks) {
            this.PBRemarks = PBRemarks;
        }

        public String getTb_Project() {
            return tb_Project;
        }

        public void setTb_Project(String tb_Project) {
            this.tb_Project = tb_Project;
        }

        public String getLetterOfIndemnityTermNotMap() {
            return LetterOfIndemnityTermNotMap;
        }

        public void setLetterOfIndemnityTermNotMap(String letterOfIndemnityTermNotMap) {
            LetterOfIndemnityTermNotMap = letterOfIndemnityTermNotMap;
        }
    }
}
