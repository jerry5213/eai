package com.zxtech.is.model.person;

/**
 * Created by syp692 on 2018/4/21.
 */

public class PersonMember {

    private String guid;
    private String name;
    private String attachguid;
    private String idcard;
    private String license;
    private String telephone;
    private String gkLicense;
    private String deptid;
    private String DeptName;
    private String unitname;
    private String unitguid;


    private String autographGuid;


    public String getAutographGuid() {
        return autographGuid;
    }

    public void setAutographGuid(String autographGuid) {
        this.autographGuid = autographGuid;
    }

    public String getGkLicense() {
        return gkLicense;
    }

    public void setGkLicense(String gkLicense) {
        this.gkLicense = gkLicense;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }


    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }


    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }


    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getUnitguid() {
        return unitguid;
    }

    public void setUnitguid(String unitguid) {
        this.unitguid = unitguid;
    }

    private int blackflag;

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


    public String getAttachguid() {
        return attachguid;
    }

    public void setAttachguid(String attachguid) {
        this.attachguid = attachguid;
    }


    public int getBlackflag() {
        return blackflag;
    }

    public void setBlackflag(int blackflag) {
        this.blackflag = blackflag;
    }


}
