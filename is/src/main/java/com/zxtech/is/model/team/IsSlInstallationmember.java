package com.zxtech.is.model.team;

/**
 * Created by syp692 on 2018/4/21.
 */

public class IsSlInstallationmember {

    private String telephone; //电话

    private String idcard;//身份证

    private String idCardAddress;//身份证地址

    private String license;

    private String gkLicense;

    private String slGuid; // 安装供方guid 合作方

    private String branchGuid; //分支机构

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdCardAddress() {
        return idCardAddress;
    }

    public void setIdCardAddress(String idCardAddress) {
        this.idCardAddress = idCardAddress;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getGkLicense() {
        return gkLicense;
    }

    public void setGkLicense(String gkLicense) {
        this.gkLicense = gkLicense;
    }

    public String getSlGuid() {
        return slGuid;
    }

    public void setSlGuid(String slGuid) {
        this.slGuid = slGuid;
    }

    public String getBranchGuid() {
        return branchGuid;
    }

    public void setBranchGuid(String branchGuid) {
        this.branchGuid = branchGuid;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    private boolean isCheck = false;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    private String guid;

    private String name;

    private String unitName;

    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    private boolean leader;


}
