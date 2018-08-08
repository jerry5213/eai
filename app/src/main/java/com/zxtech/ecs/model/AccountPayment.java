package com.zxtech.ecs.model;

import com.zxtech.ecs.event.EventAccountPayment;

import java.io.Serializable;

/**
 * Created by syp523 on 2018/7/9.
 */

public class AccountPayment  implements Serializable{


    private static final long serialVersionUID = 6497339037072645883L;
    /**
     * SaleBranchName : null
     * UpdateUserName : 孙丽娜
     * InstanceNodeName : 
     * IsHasElevator : false
     * RestMoney : 596330
     * Guid : d1428119-f9c4-44d7-8e40-b6232d460641
     * OrderNumber : null
     * FileName : 2018062601
     * Status : 0
     * SerialNo : 2018-00023.1
     * SerialNoCopy : 2018-00023
     * ImportPlace : 总部
     * SaleBranchNo : null
     * PayDate : 2017-05-19T00:00:00
     * RemittanceUnit : 石家庄创世纪房地产开发有限公司
     * OriginalMoney : 605280.00
     * AllotMoney : 0
     * Remark : null
     * InvoiceUnit : null
     * InfoSupporter : 赵洋
     * PaymentRemark : null
     * ContractNo : null
     * ContractArchivesNo : null
     * ProjectName : null
     * PayProblem : null
     * PayDealStatus : null
     * UpdateStatus : null
     * UpdateReason : null
     * CreateUser : facc8df7-5ab2-4568-9e36-0608f7492978
     * CreateTime : 2018-06-26T14:44:19.803
     * UpdateUser : 5712083e-e279-42c0-a26e-6b0b9b9888b8
     * UpdateTime : 2018-06-26T14:54:42.243
     * SaleUserId : 00000000-0000-0000-0000-000000000000
     * CopyType : null
     * InstanceGuid : null
     * VersionNo : B
     * IsCurrentVersion : true
     * InvoiceAttribution : 暂不分配
     */

    private String SaleBranchName;
    private String UpdateUserName;
    private String InstanceNodeName;
    private boolean IsHasElevator;
    private String RestMoney;
    private String Guid;
    private String OrderNumber;
    private String FileName;
    private int Status;
    private String SerialNo;
    private String SerialNoCopy;
    private String ImportPlace;
    private String SaleBranchNo;
    private String PayDate;
    private String RemittanceUnit;
    private String OriginalMoney;
    private String AllotMoney;
    private String Remark;
    private String InvoiceUnit;
    private String InfoSupporter;
    private String PaymentRemark;
    private String ContractNo;
    private String ContractArchivesNo;
    private String ProjectName;
    private String PayProblem;
    private String PayDealStatus;
    private String UpdateStatus;
    private String UpdateReason;
    private String CreateUser;
    private String CreateTime;
    private String UpdateUser;
    private String UpdateTime;
    private String SaleUserId;
    private String CopyType;
    private String InstanceGuid;
    private String VersionNo;
    private boolean IsCurrentVersion;
    private String InvoiceAttribution;
    private boolean isExpand;
    private String EquiMoney;
    private String InstMoney;
    private String ContractAllotTotalMoney;
    private String ToReceiveMoney;

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String getSaleBranchName() {
        return SaleBranchName;
    }

    public void setSaleBranchName(String SaleBranchName) {
        this.SaleBranchName = SaleBranchName;
    }

    public String getUpdateUserName() {
        return UpdateUserName;
    }

    public void setUpdateUserName(String UpdateUserName) {
        this.UpdateUserName = UpdateUserName;
    }

    public String getInstanceNodeName() {
        return InstanceNodeName;
    }

    public void setInstanceNodeName(String InstanceNodeName) {
        this.InstanceNodeName = InstanceNodeName;
    }

    public boolean isIsHasElevator() {
        return IsHasElevator;
    }

    public void setIsHasElevator(boolean IsHasElevator) {
        this.IsHasElevator = IsHasElevator;
    }

    public String getRestMoney() {
        return RestMoney;
    }

    public void setRestMoney(String RestMoney) {
        this.RestMoney = RestMoney;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public int getStatus() {
        return Status;
    }

    public String getStatusText() {
        if (Status == 0) {
            return "正常";
        }else if (Status == 1){
            return "锁定";
        }else if (Status == 2) {
            return "待入账";
        }else if (Status == 3) {
            return "已入账";
        }
        return null;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String SerialNo) {
        this.SerialNo = SerialNo;
    }

    public String getSerialNoCopy() {
        return SerialNoCopy;
    }

    public void setSerialNoCopy(String SerialNoCopy) {
        this.SerialNoCopy = SerialNoCopy;
    }

    public String getImportPlace() {
        return ImportPlace;
    }

    public void setImportPlace(String ImportPlace) {
        this.ImportPlace = ImportPlace;
    }

    public String getSaleBranchNo() {
        return SaleBranchNo;
    }

    public void setSaleBranchNo(String SaleBranchNo) {
        this.SaleBranchNo = SaleBranchNo;
    }

    public String getPayDate() {
        return (PayDate != null && PayDate.length() > 9) ? PayDate.substring(0,10) : PayDate;
    }

    public void setPayDate(String PayDate) {
        this.PayDate = PayDate;
    }

    public String getRemittanceUnit() {
        return RemittanceUnit;
    }

    public void setRemittanceUnit(String RemittanceUnit) {
        this.RemittanceUnit = RemittanceUnit;
    }

    public String getOriginalMoney() {
        return OriginalMoney;
    }

    public void setOriginalMoney(String OriginalMoney) {
        this.OriginalMoney = OriginalMoney;
    }

    public String getAllotMoney() {
        return AllotMoney;
    }

    public void setAllotMoney(String AllotMoney) {
        this.AllotMoney = AllotMoney;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getInvoiceUnit() {
        return InvoiceUnit;
    }

    public void setInvoiceUnit(String InvoiceUnit) {
        this.InvoiceUnit = InvoiceUnit;
    }

    public String getInfoSupporter() {
        return InfoSupporter;
    }

    public void setInfoSupporter(String InfoSupporter) {
        this.InfoSupporter = InfoSupporter;
    }

    public String getPaymentRemark() {
        return PaymentRemark;
    }

    public void setPaymentRemark(String PaymentRemark) {
        this.PaymentRemark = PaymentRemark;
    }

    public String getContractNo() {
        return ContractNo;
    }

    public void setContractNo(String ContractNo) {
        this.ContractNo = ContractNo;
    }

    public String getContractArchivesNo() {
        return ContractArchivesNo;
    }

    public void setContractArchivesNo(String ContractArchivesNo) {
        this.ContractArchivesNo = ContractArchivesNo;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getPayProblem() {
        return PayProblem;
    }

    public void setPayProblem(String PayProblem) {
        this.PayProblem = PayProblem;
    }

    public String getPayDealStatus() {
        return PayDealStatus;
    }

    public void setPayDealStatus(String PayDealStatus) {
        this.PayDealStatus = PayDealStatus;
    }

    public String getUpdateStatus() {
        return UpdateStatus;
    }

    public void setUpdateStatus(String UpdateStatus) {
        this.UpdateStatus = UpdateStatus;
    }

    public String getUpdateReason() {
        return UpdateReason;
    }

    public void setUpdateReason(String UpdateReason) {
        this.UpdateReason = UpdateReason;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String CreateUser) {
        this.CreateUser = CreateUser;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUpdateUser() {
        return UpdateUser;
    }

    public void setUpdateUser(String UpdateUser) {
        this.UpdateUser = UpdateUser;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getSaleUserId() {
        return SaleUserId;
    }

    public void setSaleUserId(String SaleUserId) {
        this.SaleUserId = SaleUserId;
    }

    public String getCopyType() {
        return CopyType;
    }

    public void setCopyType(String CopyType) {
        this.CopyType = CopyType;
    }

    public String getInstanceGuid() {
        return InstanceGuid;
    }

    public void setInstanceGuid(String InstanceGuid) {
        this.InstanceGuid = InstanceGuid;
    }

    public String getVersionNo() {
        return VersionNo;
    }

    public void setVersionNo(String VersionNo) {
        this.VersionNo = VersionNo;
    }

    public boolean isIsCurrentVersion() {
        return IsCurrentVersion;
    }

    public void setIsCurrentVersion(boolean IsCurrentVersion) {
        this.IsCurrentVersion = IsCurrentVersion;
    }

    public String getInvoiceAttribution() {
        return InvoiceAttribution;
    }

    public void setInvoiceAttribution(String InvoiceAttribution) {
        this.InvoiceAttribution = InvoiceAttribution;
    }

    public String getEquiMoney() {
        return EquiMoney;
    }

    public void setEquiMoney(String equiMoney) {
        EquiMoney = equiMoney;
    }

    public String getInstMoney() {
        return InstMoney;
    }

    public void setInstMoney(String instMoney) {
        InstMoney = instMoney;
    }

    public String getContractAllotTotalMoney() {
        return ContractAllotTotalMoney;
    }

    public void setContractAllotTotalMoney(String contractAllotTotalMoney) {
        ContractAllotTotalMoney = contractAllotTotalMoney;
    }

    public String getToReceiveMoney() {
        return ToReceiveMoney;
    }

    public void setToReceiveMoney(String toReceiveMoney) {
        ToReceiveMoney = toReceiveMoney;
    }
}
