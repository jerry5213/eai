package com.zxtech.is.model.s1;

import java.io.Serializable;

/**
 * Created by syp600 on 2018/4/24.
 */

public class S1Elevator extends ElevatorBase implements Serializable {

    private String planS1Guid;
    private String plan3cdate;
    private String moneys2date;
    private String moeny3cdate;
    private String wellholeitem;
    private String notifyitem;
    private String enterItem;
    private String yardItem;
    private String storehouseItem;
    private String constructionItem;
    private String installTemItem;

    public String getPlanS1Guid() {
        return planS1Guid;
    }

    public void setPlanS1Guid(String planS1Guid) {
        this.planS1Guid = planS1Guid;
    }

    public String getPlan3cdate() {
        return plan3cdate;
    }

    public void setPlan3cdate(String plan3cdate) {
        this.plan3cdate = plan3cdate;
    }

    public String getMoneys2date() {
        return moneys2date;
    }

    public void setMoneys2date(String moneys2date) {
        this.moneys2date = moneys2date;
    }

    public String getMoeny3cdate() {
        return moeny3cdate;
    }

    public void setMoeny3cdate(String moeny3cdate) {
        this.moeny3cdate = moeny3cdate;
    }

    public String getWellholeitem() {
        return wellholeitem;
    }

    public void setWellholeitem(String wellholeitem) {
        this.wellholeitem = wellholeitem;
    }

    public String getNotifyitem() {
        return notifyitem;
    }

    public void setNotifyitem(String notifyitem) {
        this.notifyitem = notifyitem;
    }

    public String getEnterItem() {
        return enterItem;
    }

    public void setEnterItem(String enterItem) {
        this.enterItem = enterItem;
    }

    public String getYardItem() {
        return yardItem;
    }

    public void setYardItem(String yardItem) {
        this.yardItem = yardItem;
    }

    public String getStorehouseItem() {
        return storehouseItem;
    }

    public void setStorehouseItem(String storehouseItem) {
        this.storehouseItem = storehouseItem;
    }

    public String getConstructionItem() {
        return constructionItem;
    }

    public void setConstructionItem(String constructionItem) {
        this.constructionItem = constructionItem;
    }

    public String getInstallTemItem() {
        return installTemItem;
    }

    public void setInstallTemItem(String installTemItem) {
        this.installTemItem = installTemItem;
    }
}
