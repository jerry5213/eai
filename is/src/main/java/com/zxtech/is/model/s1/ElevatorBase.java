package com.zxtech.is.model.s1;

import com.zxtech.is.model.attach.Attach;

import java.io.Serializable;
import java.util.List;

/**
 * Created by syp600 on 2018/5/15.
 */

public class ElevatorBase implements Serializable {

    private boolean isCheck = false;
    private String elevatorguid;
    private String EQUNR;
    private String ARKTX;
    private String projectGuid;
    private String procinstid;
    private String taskId;
    private String addressGuid;
    private String projectAddProvince;
    private String pcMoney;
    private String fhMoney;
    private String projectAddProvinceName;
    private String projectAddCity;
    private String projectAddCityName;
    private String projectAddArea;
    private String projectAddAreaName;
    private String projectAddOther;
    private String instlAddressGuid;
    private String instlProjectAddProvince;
    private String instlProjectAddProvinceName;
    private String instlProjectAddCity;
    private String instlProjectAddCityName;
    private String instlProjectAddArea;
    private String instlProjectAddAreaName;
    private String instlProjectAddOther;

    //联系人
    private List<ElevatorContact> contactsList;
    //产品级附件
    private List<Attach> elevatorAttachList;
    //删除的附件
    private List<String> deleteFileList;

    public String getElevatorguid() {
        return elevatorguid;
    }

    public void setElevatorguid(String elevatorguid) {
        this.elevatorguid = elevatorguid;
    }

    public String getEQUNR() {
        return EQUNR;
    }

    public void setEQUNR(String EQUNR) {
        this.EQUNR = EQUNR;
    }

    public String getARKTX() {
        return ARKTX;
    }

    public void setARKTX(String ARKTX) {
        this.ARKTX = ARKTX;
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public void setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
    }

    public String getProcinstid() {
        return procinstid;
    }

    public void setProcinstid(String procinstid) {
        this.procinstid = procinstid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAddressGuid() {
        return addressGuid;
    }

    public void setAddressGuid(String addressGuid) {
        this.addressGuid = addressGuid;
    }

    public String getProjectAddProvince() {
        return projectAddProvince;
    }

    public void setProjectAddProvince(String projectAddProvince) {
        this.projectAddProvince = projectAddProvince;
    }

    public String getPcMoney() {
        return pcMoney;
    }

    public void setPcMoney(String pcMoney) {
        this.pcMoney = pcMoney;
    }

    public String getFhMoney() {
        return fhMoney;
    }

    public void setFhMoney(String fhMoney) {
        this.fhMoney = fhMoney;
    }

    public String getProjectAddProvinceName() {
        return projectAddProvinceName;
    }

    public void setProjectAddProvinceName(String projectAddProvinceName) {
        this.projectAddProvinceName = projectAddProvinceName;
    }

    public String getProjectAddCity() {
        return projectAddCity;
    }

    public void setProjectAddCity(String projectAddCity) {
        this.projectAddCity = projectAddCity;
    }

    public String getProjectAddCityName() {
        return projectAddCityName;
    }

    public void setProjectAddCityName(String projectAddCityName) {
        this.projectAddCityName = projectAddCityName;
    }

    public String getProjectAddArea() {
        return projectAddArea;
    }

    public void setProjectAddArea(String projectAddArea) {
        this.projectAddArea = projectAddArea;
    }

    public String getProjectAddAreaName() {
        return projectAddAreaName;
    }

    public void setProjectAddAreaName(String projectAddAreaName) {
        this.projectAddAreaName = projectAddAreaName;
    }

    public String getProjectAddOther() {
        return projectAddOther;
    }

    public void setProjectAddOther(String projectAddOther) {
        this.projectAddOther = projectAddOther;
    }

    public String getInstlAddressGuid() {
        return instlAddressGuid;
    }

    public void setInstlAddressGuid(String instlAddressGuid) {
        this.instlAddressGuid = instlAddressGuid;
    }

    public String getInstlProjectAddProvince() {
        return instlProjectAddProvince;
    }

    public void setInstlProjectAddProvince(String instlProjectAddProvince) {
        this.instlProjectAddProvince = instlProjectAddProvince;
    }

    public String getInstlProjectAddProvinceName() {
        return instlProjectAddProvinceName;
    }

    public void setInstlProjectAddProvinceName(String instlProjectAddProvinceName) {
        this.instlProjectAddProvinceName = instlProjectAddProvinceName;
    }

    public String getInstlProjectAddCity() {
        return instlProjectAddCity;
    }

    public void setInstlProjectAddCity(String instlProjectAddCity) {
        this.instlProjectAddCity = instlProjectAddCity;
    }

    public String getInstlProjectAddCityName() {
        return instlProjectAddCityName;
    }

    public void setInstlProjectAddCityName(String instlProjectAddCityName) {
        this.instlProjectAddCityName = instlProjectAddCityName;
    }

    public String getInstlProjectAddArea() {
        return instlProjectAddArea;
    }

    public void setInstlProjectAddArea(String instlProjectAddArea) {
        this.instlProjectAddArea = instlProjectAddArea;
    }

    public String getInstlProjectAddAreaName() {
        return instlProjectAddAreaName;
    }

    public void setInstlProjectAddAreaName(String instlProjectAddAreaName) {
        this.instlProjectAddAreaName = instlProjectAddAreaName;
    }

    public String getInstlProjectAddOther() {
        return instlProjectAddOther;
    }

    public void setInstlProjectAddOther(String instlProjectAddOther) {
        this.instlProjectAddOther = instlProjectAddOther;
    }

    public List<ElevatorContact> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<ElevatorContact> contactsList) {
        this.contactsList = contactsList;
    }

    public List<Attach> getElevatorAttachList() {
        return elevatorAttachList;
    }

    public void setElevatorAttachList(List<Attach> elevatorAttachList) {
        this.elevatorAttachList = elevatorAttachList;
    }

    public List<String> getDeleteFileList() {
        return deleteFileList;
    }

    public void setDeleteFileList(List<String> deleteFileList) {
        this.deleteFileList = deleteFileList;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
