package com.zxtech.ecs.model;

import java.io.Serializable;

/**
 * Created by syp521 on 2018/7/11.
 */

public class PayNode implements Serializable{


    /**
     * Guid : 59809129-2125-40ec-840e-b9b9d39af8a3
     * BillingNumber : 0750067180
     * OrderNumber : 0006294860
     * MilestoneNo : 000250710619
     * Item : 000135
     * ElevatorNo : L14
     * WBS : 1/6294860-135-298L
     * PayNode : 0-1
     * ContractMoney : 179000.00
     * ReceiveMoney : 8950.00
     * ReceivePercent : 5
     * IsReceived : null
     * Attribution : null
     * CreateTime : 2017-10-21T00:00:00
     * CreateUser : null
     * RealReceiveMoney : 179000.00
     * RealMoney : 0
     * RealMoneyTotal : 0
     * AccountGuid : 00000000-0000-0000-0000-000000000000
     * PayNodeName : 设备-定金
     * ElevatorNoLike : null
     */

    private String Guid;
    private String BillingNumber;
    private String OrderNumber;
    private String MilestoneNo;
    private String Item;
    private String ElevatorNo;
    private String WBS;
    private String PayNode;
    private String ContractMoney;
    private String ReceiveMoney;
    private String ReceivePercent;
    private Object IsReceived;
    private Object Attribution;
    private String CreateTime;
    private Object CreateUser;
    private String RealReceiveMoney;
    private String RealMoney;
    private String RealMoneyTotal;
    private String AccountGuid;
    private String PayNodeName;
    private Object ElevatorNoLike;

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getBillingNumber() {
        return BillingNumber;
    }

    public void setBillingNumber(String BillingNumber) {
        this.BillingNumber = BillingNumber;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public String getMilestoneNo() {
        return MilestoneNo;
    }

    public void setMilestoneNo(String MilestoneNo) {
        this.MilestoneNo = MilestoneNo;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String Item) {
        this.Item = Item;
    }

    public String getElevatorNo() {
        return ElevatorNo;
    }

    public void setElevatorNo(String ElevatorNo) {
        this.ElevatorNo = ElevatorNo;
    }

    public String getWBS() {
        return WBS;
    }

    public void setWBS(String WBS) {
        this.WBS = WBS;
    }

    public String getPayNode() {
        return PayNode;
    }

    public void setPayNode(String PayNode) {
        this.PayNode = PayNode;
    }

    public String getContractMoney() {
        return ContractMoney;
    }

    public void setContractMoney(String ContractMoney) {
        this.ContractMoney = ContractMoney;
    }

    public String getReceiveMoney() {
        return ReceiveMoney;
    }

    public void setReceiveMoney(String ReceiveMoney) {
        this.ReceiveMoney = ReceiveMoney;
    }

    public String getReceivePercent() {
        return ReceivePercent;
    }

    public void setReceivePercent(String ReceivePercent) {
        this.ReceivePercent = ReceivePercent;
    }

    public Object getIsReceived() {
        return IsReceived;
    }

    public void setIsReceived(Object IsReceived) {
        this.IsReceived = IsReceived;
    }

    public Object getAttribution() {
        return Attribution;
    }

    public void setAttribution(Object Attribution) {
        this.Attribution = Attribution;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public Object getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(Object CreateUser) {
        this.CreateUser = CreateUser;
    }

    public String getRealReceiveMoney() {
        return RealReceiveMoney;
    }

    public void setRealReceiveMoney(String RealReceiveMoney) {
        this.RealReceiveMoney = RealReceiveMoney;
    }

    public String getRealMoney() {
        return RealMoney;
    }

    public void setRealMoney(String RealMoney) {
        this.RealMoney = RealMoney;
    }

    public String getRealMoneyTotal() {
        return RealMoneyTotal;
    }

    public void setRealMoneyTotal(String RealMoneyTotal) {
        this.RealMoneyTotal = RealMoneyTotal;
    }

    public String getAccountGuid() {
        return AccountGuid;
    }

    public void setAccountGuid(String AccountGuid) {
        this.AccountGuid = AccountGuid;
    }

    public String getPayNodeName() {
        return PayNodeName;
    }

    public void setPayNodeName(String PayNodeName) {
        this.PayNodeName = PayNodeName;
    }

    public Object getElevatorNoLike() {
        return ElevatorNoLike;
    }

    public void setElevatorNoLike(Object ElevatorNoLike) {
        this.ElevatorNoLike = ElevatorNoLike;
    }
}
