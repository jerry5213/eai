package com.zxtech.ecs.model;

import java.util.List;

/**
 * Created by syp523 on 2018/5/28.
 */

public class ContractDeliveryPoints {


    private List<ContractListBean> ContractList;
    private List<DeliveryPointsListBean> DeliveryPointsList;

    public List<ContractListBean> getContractList() {
        return ContractList;
    }

    public void setContractList(List<ContractListBean> ContractList) {
        this.ContractList = ContractList;
    }

    public List<DeliveryPointsListBean> getDeliveryPointsList() {
        return DeliveryPointsList;
    }

    public void setDeliveryPointsList(List<DeliveryPointsListBean> DeliveryPointsList) {
        this.DeliveryPointsList = DeliveryPointsList;
    }

    public static class ContractListBean {
        /**
         * SeqId : 6149.0
         * ProvinceName : 辽宁省
         * ProvinceCode : 辽宁省
         * CityName : 沈阳市
         * CityCode : 沈阳市
         * AreaName : 和平区
         * SaleBranchNo : GKLN
         * ContractBranchName : 沈阳分公司
         * ContractBranchSAPNo : GKLN
         * BelongToArea : 05
         * IsDeleted : false
         */

        private double SeqId;
        private String ProvinceName;
        private String ProvinceCode;
        private String CityName;
        private String CityCode;
        private String AreaName;
        private String SaleBranchNo;
        private String ContractBranchName;
        private String ContractBranchSAPNo;
        private String BelongToArea;
        private boolean IsDeleted;

        public double getSeqId() {
            return SeqId;
        }

        public void setSeqId(double SeqId) {
            this.SeqId = SeqId;
        }

        public String getProvinceName() {
            return ProvinceName;
        }

        public void setProvinceName(String ProvinceName) {
            this.ProvinceName = ProvinceName;
        }

        public String getProvinceCode() {
            return ProvinceCode;
        }

        public void setProvinceCode(String ProvinceCode) {
            this.ProvinceCode = ProvinceCode;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }

        public String getCityCode() {
            return CityCode;
        }

        public void setCityCode(String CityCode) {
            this.CityCode = CityCode;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public String getSaleBranchNo() {
            return SaleBranchNo;
        }

        public void setSaleBranchNo(String SaleBranchNo) {
            this.SaleBranchNo = SaleBranchNo;
        }

        public String getContractBranchName() {
            return ContractBranchName;
        }

        public void setContractBranchName(String ContractBranchName) {
            this.ContractBranchName = ContractBranchName;
        }

        public String getContractBranchSAPNo() {
            return ContractBranchSAPNo;
        }

        public void setContractBranchSAPNo(String ContractBranchSAPNo) {
            this.ContractBranchSAPNo = ContractBranchSAPNo;
        }

        public String getBelongToArea() {
            return BelongToArea;
        }

        public void setBelongToArea(String BelongToArea) {
            this.BelongToArea = BelongToArea;
        }

        public boolean isIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(boolean IsDeleted) {
            this.IsDeleted = IsDeleted;
        }

        @Override
        public String toString() {
            return AreaName;
        }
    }

    public static class DeliveryPointsListBean {
        /**
         * ContractBranchName : 沈阳分公司
         * ContractBranchSAPNo : GKLN
         * DeliveryPpoints : 鞍山
         * DeliveryPpointsSAPCode : ANSHAN
         * Enabled : true
         */

        private String ContractBranchName;
        private String ContractBranchSAPNo;
        private String DeliveryPpoints;
        private String DeliveryPpointsSAPCode;
        private boolean Enabled;

        public String getContractBranchName() {
            return ContractBranchName;
        }

        public void setContractBranchName(String ContractBranchName) {
            this.ContractBranchName = ContractBranchName;
        }

        public String getContractBranchSAPNo() {
            return ContractBranchSAPNo;
        }

        public void setContractBranchSAPNo(String ContractBranchSAPNo) {
            this.ContractBranchSAPNo = ContractBranchSAPNo;
        }

        public String getDeliveryPpoints() {
            return DeliveryPpoints;
        }

        public void setDeliveryPpoints(String DeliveryPpoints) {
            this.DeliveryPpoints = DeliveryPpoints;
        }

        public String getDeliveryPpointsSAPCode() {
            return DeliveryPpointsSAPCode;
        }

        public void setDeliveryPpointsSAPCode(String DeliveryPpointsSAPCode) {
            this.DeliveryPpointsSAPCode = DeliveryPpointsSAPCode;
        }

        public boolean isEnabled() {
            return Enabled;
        }

        public void setEnabled(boolean Enabled) {
            this.Enabled = Enabled;
        }

        @Override
        public String toString() {
            return DeliveryPpoints;
        }
    }
}
