package com.zxtech.gks.model.bean;

/**
 * Created by syp521 on 2018/2/23.
 */

public class ReserveMoneyDetail {

    /**
     * PriceName : MP项目设备管理费用
     * EQS_ProductGuid : 2cd5caaa-42c6-4093-ab31-15b4da0a1681
     * PriceTypeId : 1
     * PriceCode : 2230
     * PrimeCost : 1000
     * SalePrice : 1287
     * CreateUserGuid : ef776e25-b704-4504-a3a8-9f3d907f18b7
     * ModifiedUserGuid : ef776e25-b704-4504-a3a8-9f3d907f18b7
     * ModifiedDate : /Date(1518396629097)/
     * PriceTypeManageId : 7
     */

    private String PriceName;
    private String EQS_ProductGuid;
    private int PriceTypeId;
    private String PriceCode;
    private String PrimeCost;
    private String SalePrice;
    private String CreateUserGuid;
    private String ModifiedUserGuid;
    private String ModifiedDate;
    private int PriceTypeManageId;

    public String getPriceName() {
        return PriceName;
    }

    public void setPriceName(String PriceName) {
        this.PriceName = PriceName;
    }

    public String getEQS_ProductGuid() {
        return EQS_ProductGuid;
    }

    public void setEQS_ProductGuid(String EQS_ProductGuid) {
        this.EQS_ProductGuid = EQS_ProductGuid;
    }

    public int getPriceTypeId() {
        return PriceTypeId;
    }

    public void setPriceTypeId(int PriceTypeId) {
        this.PriceTypeId = PriceTypeId;
    }

    public String getPriceCode() {
        return PriceCode;
    }

    public void setPriceCode(String PriceCode) {
        this.PriceCode = PriceCode;
    }

    public String getPrimeCost() {
        return PrimeCost;
    }

    public void setPrimeCost(String PrimeCost) {
        this.PrimeCost = PrimeCost;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(String SalePrice) {
        this.SalePrice = SalePrice;
    }

    public String getCreateUserGuid() {
        return CreateUserGuid;
    }

    public void setCreateUserGuid(String CreateUserGuid) {
        this.CreateUserGuid = CreateUserGuid;
    }

    public String getModifiedUserGuid() {
        return ModifiedUserGuid;
    }

    public void setModifiedUserGuid(String ModifiedUserGuid) {
        this.ModifiedUserGuid = ModifiedUserGuid;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String ModifiedDate) {
        this.ModifiedDate = ModifiedDate;
    }

    public int getPriceTypeManageId() {
        return PriceTypeManageId;
    }

    public void setPriceTypeManageId(int PriceTypeManageId) {
        this.PriceTypeManageId = PriceTypeManageId;
    }
}
