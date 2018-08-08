package com.zxtech.ecs.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp521 on 2018/4/9.
 * 获取项目信息下拉选项值
 */

public class ProjectDropDown {

    private List<DropDown> eqContractTypeList;
    private List<DropDown> buildingCharacterList;
    private List<DropDown> isFreeInsuranceList;
    private List<DropDown> purchaseTypeList;
    private List<DropDown> inContractTypeList;
    private List<DropDown> contractPartyList;
    private List<DropDown> bidTypeList;
    private List<DropDown> modeOfTransportList;
    private List<DropDown> isUnloadingHoistingCostList;
    private List<DropDown> isScaffoldCostList;
    private List<DropDown> isCheckCostList;
    private List<DropDown> informationSourcesList;
    private List<DropDown> isReProjectList;
    private List<DropDown> isReadBidList;
    private List<DropDown> keyCustomerList;
    private List<DropDown> isBidList;
    private List<DropDown> isChangePerformanceBondList;
    private List<DropDown> depositTypeList;
    private List<DropDown> payTypeList;
    private List<DropDown> isPerformanceBondList;
    private List<DropDown> letterOfIndemnityFormatList;

    public List<DropDown> getEqContractTypeList() {
        return eqContractTypeList;
    }

    public void setEqContractTypeList(List<DropDown> eqContractTypeList) {
        this.eqContractTypeList = eqContractTypeList;
    }

    public List<DropDown> getBuildingCharacterList() {
        return buildingCharacterList;
    }

    public void setBuildingCharacterList(List<DropDown> buildingCharacterList) {
        this.buildingCharacterList = buildingCharacterList;
    }

    public List<DropDown> getIsFreeInsuranceList() {
        return isFreeInsuranceList;
    }

    public void setIsFreeInsuranceList(List<DropDown> isFreeInsuranceList) {
        this.isFreeInsuranceList = isFreeInsuranceList;
    }

    public List<DropDown> getPurchaseTypeList() {
        return purchaseTypeList;
    }

    public void setPurchaseTypeList(List<DropDown> purchaseTypeList) {
        this.purchaseTypeList = purchaseTypeList;
    }

    public List<DropDown> getInContractTypeList() {
        return inContractTypeList;
    }

    public List<DropDown> getInContractTypeList(String eqContractType) {
        List<DropDown> fitterList = new ArrayList<>();
        if (TextUtils.isEmpty(eqContractType)) {
            return inContractTypeList;
        }else  {
            for (int i = 0; i < inContractTypeList.size(); i++) {
                if ("直销".equals(eqContractType) && "直销".equals(inContractTypeList.get(i).getValue())) {
                    fitterList.add(inContractTypeList.get(i));
                }else if (!"直销".equals(eqContractType) && !"直销".equals(inContractTypeList.get(i).getValue())){
                    fitterList.add(inContractTypeList.get(i));
                }

            }
            return fitterList;
        }
    }

    public void setInContractTypeList(List<DropDown> inContractTypeList) {
        this.inContractTypeList = inContractTypeList;
    }

    public List<DropDown> getContractPartyList() {
        return contractPartyList;
    }

    public void setContractPartyList(List<DropDown> contractPartyList) {
        this.contractPartyList = contractPartyList;
    }

    public List<DropDown> getBidTypeList() {
        return bidTypeList;
    }

    public void setBidTypeList(List<DropDown> bidTypeList) {
        this.bidTypeList = bidTypeList;
    }

    public List<DropDown> getModeOfTransportList() {
        return modeOfTransportList;
    }

    public void setModeOfTransportList(List<DropDown> modeOfTransportList) {
        this.modeOfTransportList = modeOfTransportList;
    }

    public List<DropDown> getIsUnloadingHoistingCostList() {
        return isUnloadingHoistingCostList;
    }

    public void setIsUnloadingHoistingCostList(List<DropDown> isUnloadingHoistingCostList) {
        this.isUnloadingHoistingCostList = isUnloadingHoistingCostList;
    }

    public List<DropDown> getIsScaffoldCostList() {
        return isScaffoldCostList;
    }

    public void setIsScaffoldCostList(List<DropDown> isScaffoldCostList) {
        this.isScaffoldCostList = isScaffoldCostList;
    }

    public List<DropDown> getIsCheckCostList() {
        return isCheckCostList;
    }

    public void setIsCheckCostList(List<DropDown> isCheckCostList) {
        this.isCheckCostList = isCheckCostList;
    }

    public List<DropDown> getInformationSourcesList() {
        return informationSourcesList;
    }

    public void setInformationSourcesList(List<DropDown> informationSourcesList) {
        this.informationSourcesList = informationSourcesList;
    }

    public List<DropDown> getIsReProjectList() {
        return isReProjectList;
    }

    public void setIsReProjectList(List<DropDown> isReProjectList) {
        this.isReProjectList = isReProjectList;
    }

    public List<DropDown> getIsReadBidList() {
        return isReadBidList;
    }

    public void setIsReadBidList(List<DropDown> isReadBidList) {
        this.isReadBidList = isReadBidList;
    }

    public List<DropDown> getKeyCustomerList() {
        return keyCustomerList;
    }

    public void setKeyCustomerList(List<DropDown> keyCustomerList) {
        this.keyCustomerList = keyCustomerList;
    }

    public List<DropDown> getIsBidList() {
        return isBidList;
    }

    public void setIsBidList(List<DropDown> isBidList) {
        this.isBidList = isBidList;
    }

    public List<DropDown> getIsChangePerformanceBondList() {
        return isChangePerformanceBondList;
    }

    public void setIsChangePerformanceBondList(List<DropDown> isChangePerformanceBondList) {
        this.isChangePerformanceBondList = isChangePerformanceBondList;
    }

    public List<DropDown> getDepositTypeList() {
        return depositTypeList;
    }

    public void setDepositTypeList(List<DropDown> depositTypeList) {
        this.depositTypeList = depositTypeList;
    }

    public List<DropDown> getPayTypeList() {
        return payTypeList;
    }

    public void setPayTypeList(List<DropDown> payTypeList) {
        this.payTypeList = payTypeList;
    }

    public List<DropDown> getIsPerformanceBondList() {
        return isPerformanceBondList;
    }

    public void setIsPerformanceBondList(List<DropDown> isPerformanceBondList) {
        this.isPerformanceBondList = isPerformanceBondList;
    }

    public List<DropDown> getLetterOfIndemnityFormatList() {
        return letterOfIndemnityFormatList;
    }

    public void setLetterOfIndemnityFormatList(List<DropDown> letterOfIndemnityFormatList) {
        this.letterOfIndemnityFormatList = letterOfIndemnityFormatList;
    }


}
