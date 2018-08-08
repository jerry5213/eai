package com.zxtech.gks.model.bean;

/**
 * Created by hjw on 2016/5/14.
 */
public class City {

    /**
     * SeqId : 7863
     * ProvinceName : 四川省
     * ProvinceCode : 四川省
     * CityName : 雅安地区
     * CityCode : 雅安地区
     * AreaName : 雅安市
     * SaleBranchNo : GKSC
     * ContractBranchName : 四川分公司
     * ContractBranchSAPNo : GKSC
     * BelongToArea : 07
     * IsDeleted : false
     */

    private int SeqId;
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

    public int getSeqId() {
        return SeqId;
    }

    public void setSeqId(int SeqId) {
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
        return CityName;
    }
}