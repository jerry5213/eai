package com.zxtech.gks.model.vo.PrProductDetail;

import java.io.Serializable;

public class PriceTableListBean implements Serializable {

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
    private String EQSProductGuid;
    private String ReserveMoneyDetail = "查看";
    private String FbReview = "查看";

    public String getCMIIRateInst() {
        return CMIIRateInst;
    }

    public String getElevatorProduct() {
        return ElevatorProduct;
    }

    public String getElevatorType() {
        return ElevatorType;
    }

    public String getFCCMII() {
        return FCCMII;
    }

    public String getFCCMIIRate() {
        return FCCMIIRate;
    }

    public String getFCCMIIRateEqui() {
        return FCCMIIRateEqui;
    }

    public String getFLCMII() {
        return FLCMII;
    }

    public String getFLCMIIRate() {
        return FLCMIIRate;
    }

    public String getFLCMIIRateEqui() {
        return FLCMIIRateEqui;
    }

    public String getFloatRateEqui() {
        return FloatRateEqui;
    }

    public String getFloorAngle() {
        return FloorAngle;
    }

    public String getProductCount() {
        return ProductCount;
    }

    public String getProductNo() {
        return ProductNo;
    }

    public String getRealPrice_Equi() {
        return RealPrice_Equi;
    }

    public String getRealPrice_Inst() {
        return RealPrice_Inst;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public String getReserveMoneyDetail() {
        return ReserveMoneyDetail;
    }

    public String getFbReview() {
        return FbReview;
    }

    public String getEQSProductGuid() {
        return EQSProductGuid;
    }
}