package com.zxtech.ecs.model;

import java.io.Serializable;

/**
 * Created by syp521 on 2018/4/6.
 */

public class ProductInfo implements Serializable{

    private static final long serialVersionUID = -4634464520797917349L;
    private String ProductNo;
    private String ElevatorProduct;
    private String ElevatorType;
    private String TypeId;
    private int ElevatorCount;
    private String Angle;
    private String DeliveryDate; //交期
    private String CutStringElevatorNo; //梯号
    private String VersionNum;
    private String NonState;
    private String CreateDateStr;
    private String InstanceNodeName;
    private String EQS_Guid;
    private String IsConfirmVersion;
    private String ProgrammeGuid;
    private String IsMR;
    private String ElevatorLoad;
    private String Speed;
    private String RungsWidth;
    private String RealPrice_Equi;
    private String GuaranteeDate;
    private String FreeInsuranceDate;
    private String EQSState;
    private String EQS_ProductNo;


    public String getEQS_ProductNo() {
        return EQS_ProductNo;
    }

    public void setEQS_ProductNo(String EQS_ProductNo) {
        this.EQS_ProductNo = EQS_ProductNo;
    }

    public String getRealPrice_Equi() {
        return RealPrice_Equi;
    }

    public void setRealPrice_Equi(String realPrice_Equi) {
        RealPrice_Equi = realPrice_Equi;
    }

    public String getEQS_Guid() {
        return EQS_Guid;
    }

    public void setEQS_Guid(String EQS_Guid) {
        this.EQS_Guid = EQS_Guid;
    }

    public String getProductNo() {
        return ProductNo;
    }

    public void setProductNo(String productNo) {
        ProductNo = productNo;
    }

    public String getElevatorProduct() {
        return ElevatorProduct;
    }

    public void setElevatorProduct(String elevatorProduct) {
        ElevatorProduct = elevatorProduct;
    }

    public int getElevatorCount() {
        return ElevatorCount;
    }

    public void setElevatorCount(int elevatorCount) {
        ElevatorCount = elevatorCount;
    }

    public String getAngle() {
        return Angle;
    }

    public void setAngle(String angle) {
        Angle = angle;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getCutStringElevatorNo() {
        return CutStringElevatorNo;
    }

    public void setCutStringElevatorNo(String cutStringElevatorNo) {
        CutStringElevatorNo = cutStringElevatorNo;
    }

    public String getVersionNum() {
        return VersionNum;
    }

    public void setVersionNum(String versionNum) {
        VersionNum = versionNum;
    }

    public String getNonState() {
        return NonState;
    }

    public void setNonState(String nonState) {
        NonState = nonState;
    }

    public String getCreateDateStr() {
        return CreateDateStr != null ? CreateDateStr.substring(0,10) : null;
    }

    public void setCreateDateStr(String createDateStr) {
        CreateDateStr = createDateStr;
    }

    public String getInstanceNodeName() {
        return InstanceNodeName;
    }

    public void setInstanceNodeName(String instanceNodeName) {
        InstanceNodeName = instanceNodeName;
    }

    public boolean getIsConfirmVersion() {
        return (IsConfirmVersion != null && IsConfirmVersion.equals("True"));
    }

    public void setIsConfirmVersion(String isConfirmVersion) {
        IsConfirmVersion = isConfirmVersion;
    }

    public String getElevatorType() {
        return ElevatorType;
    }

    public void setElevatorType(String elevatorType) {
        ElevatorType = elevatorType;
    }

    public String getProgrammeGuid() {
        return ProgrammeGuid;
    }

    public void setProgrammeGuid(String programmeGuid) {
        ProgrammeGuid = programmeGuid;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

    public String getIsMR() {
        return IsMR;
    }

    public boolean isMr() {
        if (IsMR != null && IsMR.equals("YES")) {
            return true;
        } else {
            return false;
        }
    }

    public void setIsMR(String isMR) {
        IsMR = isMR;
    }

    public String getElevatorLoad() {
        return ElevatorLoad;
    }

    public void setElevatorLoad(String elevatorLoad) {
        ElevatorLoad = elevatorLoad;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getRungsWidth() {
        return RungsWidth;
    }

    public void setRungsWidth(String rungsWidth) {
        RungsWidth = rungsWidth;
    }

    public String getGuaranteeDate() {
        return GuaranteeDate;
    }

    public void setGuaranteeDate(String guaranteeDate) {
        GuaranteeDate = guaranteeDate;
    }

    public String getFreeInsuranceDate() {
        return FreeInsuranceDate;
    }

    public void setFreeInsuranceDate(String freeInsuranceDate) {
        FreeInsuranceDate = freeInsuranceDate;
    }

    public String getEQSState() {
        return EQSState;
    }

    public void setEQSState(String EQSState) {
        this.EQSState = EQSState;
    }
}
