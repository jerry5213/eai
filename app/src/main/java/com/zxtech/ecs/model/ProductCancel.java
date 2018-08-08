package com.zxtech.ecs.model;

import android.text.TextUtils;

/**
 * Created by syp523 on 2018/7/30.
 */

public class ProductCancel {


    /**
     * Guid : f1eae060-ab27-40f4-b3ca-c8944de5fac1
     * contractChangeGuid : 3db4a00b-7353-49aa-89ad-9b87cc590d03
     * projectNo : 2018-00874
     * EQSProductGuid_Before : 9d9920b2-2b26-4a75-a9f8-7d2cc4544438
     * EQSProductGuid_After : a0770d77-f6e1-4b46-aa05-9576c096afe8
     * ElevatorProduct : 自动扶梯
     * CancelElevotorNo : E69
     * CancelCount : 1
     * ElevatorType : BF(1000/35)-
     * Load :
     * Speed :
     * CreateUser : 3adf49f8-bf12-4480-87ae-d68e5f12317c
     * CreateDateTime : 2018/7/27 14:52:37
     * UpdateUser : 3adf49f8-bf12-4480-87ae-d68e5f12317c
     * UpdateDateTime : 2018/7/27 14:53:31
     * isDelete : False
     * CancelStateId : 未排产
     * CancelStateValue : 未排产
     * PaymentId : 未付款
     * PaymentValue : 未付款
     * CancelReason : aa
     * SchedulingRemark :
     * IsScheduling :
     * MaterielRemark :
     * MoneyRemark :
     * CalculationRemark :
     * CancelMoneyDealRemark : bb
     * CCVersion : 2
     * EqsProductNo : 2018-00874-11
     * tb_CMS_ContractChange :
     */

    private String Guid;
    private String contractChangeGuid;
    private String projectNo;
    private String EQSProductGuid_Before;
    private String EQSProductGuid_After;
    private String ElevatorProduct;
    private String CancelElevotorNo;
    private String CancelCount;
    private String ElevatorType;
    private String Load;
    private String Speed;
    private String CreateUser;
    private String CreateDateTime;
    private String UpdateUser;
    private String UpdateDateTime;
    private String isDelete;
    private String CancelStateId;
    private String CancelStateValue;
    private String PaymentId;
    private String PaymentValue;
    private String CancelReason;
    private String SchedulingRemark;
    private String IsScheduling;
    private String MaterielRemark;
    private String MoneyRemark;
    private String CalculationRemark;
    private String CancelMoneyDealRemark;
    private String CCVersion;
    private String EqsProductNo;
    private String tb_CMS_ContractChange;

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getContractChangeGuid() {
        return contractChangeGuid;
    }

    public void setContractChangeGuid(String contractChangeGuid) {
        this.contractChangeGuid = contractChangeGuid;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getEQSProductGuid_Before() {
        return EQSProductGuid_Before;
    }

    public void setEQSProductGuid_Before(String EQSProductGuid_Before) {
        this.EQSProductGuid_Before = EQSProductGuid_Before;
    }

    public String getEQSProductGuid_After() {
        return EQSProductGuid_After;
    }

    public void setEQSProductGuid_After(String EQSProductGuid_After) {
        this.EQSProductGuid_After = EQSProductGuid_After;
    }

    public String getElevatorProduct() {
        return ElevatorProduct;
    }

    public void setElevatorProduct(String ElevatorProduct) {
        this.ElevatorProduct = ElevatorProduct;
    }

    public String getCancelElevotorNo() {
        return CancelElevotorNo;
    }

    public void setCancelElevotorNo(String CancelElevotorNo) {
        this.CancelElevotorNo = CancelElevotorNo;
    }

    public String getCancelCount() {
        return CancelCount;
    }

    public void setCancelCount(String CancelCount) {
        this.CancelCount = CancelCount;
    }

    public String getElevatorType() {
        return ElevatorType;
    }

    public void setElevatorType(String ElevatorType) {
        this.ElevatorType = ElevatorType;
    }

    public String getLoad() {
        return Load;
    }

    public void setLoad(String Load) {
        this.Load = Load;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String Speed) {
        this.Speed = Speed;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String CreateUser) {
        this.CreateUser = CreateUser;
    }

    public String getCreateDateTime() {
        return CreateDateTime;
    }

    public void setCreateDateTime(String CreateDateTime) {
        this.CreateDateTime = CreateDateTime;
    }

    public String getUpdateUser() {
        return UpdateUser;
    }

    public void setUpdateUser(String UpdateUser) {
        this.UpdateUser = UpdateUser;
    }

    public String getUpdateDateTime() {
        return UpdateDateTime;
    }

    public void setUpdateDateTime(String UpdateDateTime) {
        this.UpdateDateTime = UpdateDateTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getCancelStateId() {
        return CancelStateId;
    }

    public void setCancelStateId(String CancelStateId) {
        this.CancelStateId = CancelStateId;
    }

    public String getCancelStateValue() {
        return TextUtils.isEmpty(CancelStateValue)? "请选择": CancelStateValue;
    }

    public void setCancelStateValue(String CancelStateValue) {
        this.CancelStateValue = CancelStateValue;
    }

    public String getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(String PaymentId) {
        this.PaymentId = PaymentId;
    }

    public String getPaymentValue() {
        return TextUtils.isEmpty(PaymentValue)? "请选择": PaymentValue;
    }

    public void setPaymentValue(String PaymentValue) {
        this.PaymentValue = PaymentValue;
    }

    public String getCancelReason() {
        return CancelReason;
    }

    public void setCancelReason(String CancelReason) {
        this.CancelReason = CancelReason;
    }

    public String getSchedulingRemark() {
        return SchedulingRemark;
    }

    public void setSchedulingRemark(String SchedulingRemark) {
        this.SchedulingRemark = SchedulingRemark;
    }

    public String getIsScheduling() {
        return IsScheduling;
    }

    public void setIsScheduling(String IsScheduling) {
        this.IsScheduling = IsScheduling;
    }

    public String getMaterielRemark() {
        return MaterielRemark;
    }

    public void setMaterielRemark(String MaterielRemark) {
        this.MaterielRemark = MaterielRemark;
    }

    public String getMoneyRemark() {
        return MoneyRemark;
    }

    public void setMoneyRemark(String MoneyRemark) {
        this.MoneyRemark = MoneyRemark;
    }

    public String getCalculationRemark() {
        return CalculationRemark;
    }

    public void setCalculationRemark(String CalculationRemark) {
        this.CalculationRemark = CalculationRemark;
    }

    public String getCancelMoneyDealRemark() {
        return CancelMoneyDealRemark;
    }

    public void setCancelMoneyDealRemark(String CancelMoneyDealRemark) {
        this.CancelMoneyDealRemark = CancelMoneyDealRemark;
    }

    public String getCCVersion() {
        return CCVersion;
    }

    public void setCCVersion(String CCVersion) {
        this.CCVersion = CCVersion;
    }

    public String getEqsProductNo() {
        return EqsProductNo;
    }

    public void setEqsProductNo(String EqsProductNo) {
        this.EqsProductNo = EqsProductNo;
    }

    public String getTb_CMS_ContractChange() {
        return tb_CMS_ContractChange;
    }

    public void setTb_CMS_ContractChange(String tb_CMS_ContractChange) {
        this.tb_CMS_ContractChange = tb_CMS_ContractChange;
    }
}
