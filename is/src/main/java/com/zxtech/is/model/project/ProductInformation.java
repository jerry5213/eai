package com.zxtech.is.model.project;

import java.io.Serializable;

/**
 * Created by syp661 on 2018/4/23.
 */

public class ProductInformation implements Serializable {

    private String equnr;
    private String matnr;

    private String arktx;
    private String elevatorGuid;
    private String taskCheck;
    private String procInstId;
    private String peAssignee;
    private String feAssignee;
    private String teAssignee;
    private String peOrgRoleName;
    private String feOrgRoleName;
    private String teOrgRoleName;
    private String peUserName;
    private String feUserName;
    private String teUserName;
    private String elevatorProduct;
    private String taskId;
    private Boolean projectTeam;
    private Boolean s1;
    private Boolean s2;
    private Boolean s3;
    private int smtType;
    private String scaffold;
    private String installType;
    private String smtCreatTime;
    private String smtCreatName;
    private String smtinstallType;
    private String smtprocInstId;
    private boolean slTeam; //班组分配判断

    public String getEqunr() {
        return equnr;
    }

    public void setEqunr(String equnr) {
        this.equnr = equnr;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getArktx() {
        return arktx;
    }

    public void setArktx(String arktx) {
        this.arktx = arktx;
    }

    public String getElevatorGuid() {
        return elevatorGuid;
    }

    public void setElevatorGuid(String elevatorGuid) {
        this.elevatorGuid = elevatorGuid;
    }

    public String getTaskCheck() {
        return taskCheck;
    }

    public void setTaskCheck(String taskCheck) {
        this.taskCheck = taskCheck;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getPeAssignee() {
        return peAssignee;
    }

    public void setPeAssignee(String peAssignee) {
        this.peAssignee = peAssignee;
    }

    public String getFeAssignee() {
        return feAssignee;
    }

    public void setFeAssignee(String feAssignee) {
        this.feAssignee = feAssignee;
    }

    public String getPeOrgRoleName() {
        return peOrgRoleName;
    }

    public void setPeOrgRoleName(String peOrgRoleName) {
        this.peOrgRoleName = peOrgRoleName;
    }

    public String getFeOrgRoleName() {
        return feOrgRoleName;
    }

    public void setFeOrgRoleName(String feOrgRoleName) {
        this.feOrgRoleName = feOrgRoleName;
    }

    public String getPeUserName() {
        return peUserName;
    }

    public void setPeUserName(String peUserName) {
        this.peUserName = peUserName;
    }

    public String getFeUserName() {
        return feUserName;
    }

    public void setFeUserName(String feUserName) {
        this.feUserName = feUserName;
    }

    public String getElevatorProduct() {
        return elevatorProduct;
    }

    public void setElevatorProduct(String elevatorProduct) {
        this.elevatorProduct = elevatorProduct;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(Boolean projectTeam) {
        this.projectTeam = projectTeam;
    }

    public Boolean getS1() {
        return s1;
    }

    public void setS1(Boolean s1) {
        this.s1 = s1;
    }

    public Boolean getS2() {
        return s2;
    }

    public void setS2(Boolean s2) {
        this.s2 = s2;
    }

    public Boolean getS3() {
        return s3;
    }

    public void setS3(Boolean s3) {
        this.s3 = s3;
    }

    public int getSmtType() {
        return smtType;
    }

    public void setSmtType(int smtType) {
        this.smtType = smtType;
    }

    public String getScaffold() {
        return scaffold;
    }

    public void setScaffold(String scaffold) {
        this.scaffold = scaffold;
    }

    public String getInstallType() {
        return installType;
    }

    public void setInstallType(String installType) {
        this.installType = installType;
    }

    public String getSmtCreatTime() {
        return smtCreatTime;
    }

    public void setSmtCreatTime(String smtCreatTime) {
        this.smtCreatTime = smtCreatTime;
    }

    public String getSmtCreatName() {
        return smtCreatName;
    }

    public void setSmtCreatName(String smtCreatName) {
        this.smtCreatName = smtCreatName;
    }

    public String getSmtinstallType() {
        return smtinstallType;
    }

    public void setSmtinstallType(String smtinstallType) {
        this.smtinstallType = smtinstallType;
    }

    public String getSmtprocInstId() {
        return smtprocInstId;
    }

    public void setSmtprocInstId(String smtprocInstId) {
        this.smtprocInstId = smtprocInstId;
    }

    public boolean isSlTeam() {
        return slTeam;
    }

    public void setSlTeam(boolean slTeam) {
        this.slTeam = slTeam;
    }

    public String getTeUserName() {
        return teUserName;
    }

    public void setTeUserName(String teUserName) {
        this.teUserName = teUserName;
    }

    public String getTeAssignee() {
        return teAssignee;
    }

    public void setTeAssignee(String teAssignee) {
        this.teAssignee = teAssignee;
    }

    public String getTeOrgRoleName() {
        return teOrgRoleName;
    }

    public void setTeOrgRoleName(String teOrgRoleName) {
        this.teOrgRoleName = teOrgRoleName;
    }
}
