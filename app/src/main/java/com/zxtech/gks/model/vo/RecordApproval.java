package com.zxtech.gks.model.vo;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by syp523 on 2017/11/30.
 */

public class RecordApproval {


    @SerializedName("ProjectGuid")
    private String projectGuid;
    @SerializedName("ProjectNo")
    private String projectNo;
    @SerializedName("ProjectName")
    private String projectName;
    @SerializedName("ProjectAreaNameStr")
    private String projectAreaNameStr;
    @SerializedName("CreateTime")
    private String createTime;
    @SerializedName("SalesmanUserName")
    private String salesmanUserName;
    @SerializedName("CloseFlag")
    private boolean closeFlag;
    @SerializedName("IsSAPClose")
    private boolean isSAPClose;
    @SerializedName("BranchName")
    private String branchName;
    @SerializedName("CustomerName")
    private String customerName;
    @SerializedName("BuildingCharacter")
    private String buildingCharacter;
    @SerializedName("BuildingCharacterName")
    private String buildingCharacterName;
    @SerializedName("BranchSalesmanNo")
    private String branchSalesmanNo;
    @SerializedName("ProjectType")
    private String projectType;
    @SerializedName("DxmzyUserName")
    private String dxmzyUserName;
    @SerializedName("DxmzyUserNo")
    private String dxmzyUserNo;
    @SerializedName("DxmzySalesManager")
    private String dxmzySalesManager;
    @SerializedName("BranchSalesmanName")
    private String branchSalesmanName;
    @SerializedName("PartnerSalespersonName")
    private String partnerSalespersonName;
    @SerializedName("PartnerSalespersonPhone")
    private String partnerSalespersonPhone;
    @SerializedName("ProjectAdd_Other")
    private String projectAdd_Other;
    @SerializedName("ProjectAdd_Province")
    private String projectAdd_Province;
    @SerializedName("ProjectAdd_City")
    private String projectAdd_City;
    @SerializedName("ProjectAdd_Area")
    private String projectAdd_Area;
    @SerializedName("CustomerId")
    private String customerId;
    @SerializedName("CustomerContact")
    private String customerContact;
    @SerializedName("CustomerPhoneNum")
    private String customerPhoneNum;
    @SerializedName("CustomerAddrees")
    private String customerAddrees;
    @SerializedName("ProjectAreaName")
    private String projectAreaName;
    @SerializedName("InstanceGuid")
    private String instanceGuid;
    @SerializedName("PurchaseType")
    private String purchaseType;

    public String getDxmzyUserNo() {
        return dxmzyUserNo;
    }

    public void setDxmzyUserNo(String dxmzyUserNo) {
        this.dxmzyUserNo = dxmzyUserNo;
    }

    public String getBuildingCharacterName() {
        return buildingCharacterName;
    }

    public void setBuildingCharacterName(String buildingCharacterName) {
        this.buildingCharacterName = buildingCharacterName;
    }

    public String getInstanceGuid() {
        return instanceGuid;
    }

    public void setInstanceGuid(String instanceGuid) {
        this.instanceGuid = instanceGuid;
    }

    public void setProjectAreaName(String projectAreaName) {
        this.projectAreaName = projectAreaName;
    }

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerPhoneNum() {
        return customerPhoneNum;
    }

    public void setCustomerPhoneNum(String customerPhoneNum) {
        this.customerPhoneNum = customerPhoneNum;
    }

    public String getCustomerAddrees() {
        return customerAddrees;
    }

    public void setCustomerAddrees(String customerAddrees) {
        this.customerAddrees = customerAddrees;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectAreaNameStr() {
        return projectAreaNameStr;
    }

    public void setProjectAreaNameStr(String projectAreaNameStr) {
        this.projectAreaNameStr = projectAreaNameStr;
    }


    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isCloseFlag() {
        return closeFlag;
    }

    public void setCloseFlag(boolean closeFlag) {
        this.closeFlag = closeFlag;
    }

    public String getSalesmanUserName() {
        return salesmanUserName;
    }

    public void setSalesmanUserName(String salesmanUserName) {
        this.salesmanUserName = salesmanUserName;
    }

    public boolean isSAPClose() {
        return isSAPClose;
    }

    public void setSAPClose(boolean SAPClose) {
        isSAPClose = SAPClose;
    }

    public String getCreateTime() {
        try {
            if (TextUtils.isEmpty(createTime)) {
                return "";
            }
            long timeInMills = Long.parseLong(createTime.substring(6, 19));
            return new SimpleDateFormat("yyyy/MM/dd").format(new Date(timeInMills));
        } catch (Exception e) {
            e.printStackTrace();
            return createTime;
        }
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBuildingCharacter() {
        return buildingCharacter;
    }

    public void setBuildingCharacter(String buildingCharacter) {
        this.buildingCharacter = buildingCharacter;
    }

    public String getBranchSalesmanNo() {
        return branchSalesmanNo;
    }

    public void setBranchSalesmanNo(String branchSalesmanNo) {
        this.branchSalesmanNo = branchSalesmanNo;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getDxmzyUserName() {
        return dxmzyUserName;
    }

    public void setDxmzyUserName(String dxmzyUserName) {
        this.dxmzyUserName = dxmzyUserName;
    }

    public String getDxmzySalesManager() {
        return dxmzySalesManager;
    }

    public void setDxmzySalesManager(String dxmzySalesManager) {
        this.dxmzySalesManager = dxmzySalesManager;
    }

    public String getBranchSalesmanName() {
        return branchSalesmanName;
    }

    public void setBranchSalesmanName(String branchSalesmanName) {
        this.branchSalesmanName = branchSalesmanName;
    }

    public String getPartnerSalespersonName() {
        return partnerSalespersonName;
    }

    public void setPartnerSalespersonName(String partnerSalespersonName) {
        this.partnerSalespersonName = partnerSalespersonName;
    }

    public String getPartnerSalespersonPhone() {
        return partnerSalespersonPhone;
    }

    public void setPartnerSalespersonPhone(String partnerSalespersonPhone) {
        this.partnerSalespersonPhone = partnerSalespersonPhone;
    }

    public String getProjectAdd_Other() {
        return projectAdd_Other;
    }

    public void setProjectAdd_Other(String projectAdd_Other) {
        this.projectAdd_Other = projectAdd_Other;
    }

    public String getProjectAdd_Province() {
        return projectAdd_Province;
    }

    public void setProjectAdd_Province(String projectAdd_Province) {
        this.projectAdd_Province = projectAdd_Province;
    }

    public String getProjectAdd_City() {
        return projectAdd_City;
    }

    public void setProjectAdd_City(String projectAdd_City) {
        this.projectAdd_City = projectAdd_City;
    }

    public String getProjectAdd_Area() {
        return projectAdd_Area;
    }

    public void setProjectAdd_Area(String projectAdd_Area) {
        this.projectAdd_Area = projectAdd_Area;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }
}
