package com.zxtech.gks.model.bean;

import com.zxtech.ecs.model.DropDown;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syp521 on 2018/7/4.
 */

public class GetProjectDetailDropDownList {

    private List<DropDown> ProjectStateList;
    private List<DropDown> ProjectStageList;
    private List<DropDown> MainEqType;
    private List<DropDown> HlAccountList;
    private List<DropDown> InfoSourceList;
    private List<DropDown> IsOnlyAgent;
    private List<DropDown> MainCompList;
    private List<DropDown> DevTypeList;
    private List<BelongArea> BelongAreaList;
    private List<DropDown> PurchaseTypeList;  //是否招投标

    public List<DropDown> getProjectStateList() {
        return ProjectStateList;
    }

    public void setProjectStateList(List<DropDown> projectStateList) {
        ProjectStateList = projectStateList;
    }

    public List<DropDown> getProjectStageList() {
        return ProjectStageList;
    }

    public void setProjectStageList(List<DropDown> projectStageList) {
        ProjectStageList = projectStageList;
    }

    public List<DropDown> getMainEqType() {
        return MainEqType;
    }

    public void setMainEqType(List<DropDown> mainEqType) {
        MainEqType = mainEqType;
    }

    public List<DropDown> getHlAccountList() {
        return HlAccountList;
    }

    public void setHlAccountList(List<DropDown> hlAccountList) {
        HlAccountList = hlAccountList;
    }

    public List<DropDown> getInfoSourceList() {
        return InfoSourceList;
    }

    public void setInfoSourceList(List<DropDown> infoSourceList) {
        InfoSourceList = infoSourceList;
    }

    public List<DropDown> getIsOnlyAgent() {
        return IsOnlyAgent;
    }

    public void setIsOnlyAgent(List<DropDown> isOnlyAgent) {
        IsOnlyAgent = isOnlyAgent;
    }

    public List<DropDown> getMainCompList() {
        return MainCompList;
    }

    public void setMainCompList(List<DropDown> mainCompList) {
        MainCompList = mainCompList;
    }

    public List<DropDown> getDevTypeList() {
        return DevTypeList;
    }

    public void setDevTypeList(List<DropDown> devTypeList) {
        DevTypeList = devTypeList;
    }

    public List<BelongArea> getBelongAreaList() {
        return BelongAreaList;
    }

    public void setBelongAreaList(List<BelongArea> belongAreaList) {
        BelongAreaList = belongAreaList;
    }

    public List<DropDown> getPurchaseTypeList() {
//        List<DropDown> fitterList = new ArrayList<>();
//        for (int i = 0; i < PurchaseTypeList.size(); i++) {
//            if ("招投标".equals(PurchaseTypeList.get(i).getText())){
//                continue;
//            }
//            fitterList.add(PurchaseTypeList.get(i));
//        }
        return PurchaseTypeList;
    }

    public void setPurchaseTypeList(List<DropDown> purchaseTypeList) {
        PurchaseTypeList = purchaseTypeList;
    }
}
