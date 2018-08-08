package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/6/26.
 */

public class InstallUnit {


    /**
     * UnitSAPCode :
     * PartnerNumber :
     * UnitName : --请选择--
     * Enabled :
     * CreateDate :
     * UnitNo :
     */

    private String UnitSAPCode;
    private String PartnerNumber;
    private String UnitName;
    private String Enabled;
    private String CreateDate;
    private String UnitNo;

    public String getUnitSAPCode() {
        return UnitSAPCode;
    }

    public void setUnitSAPCode(String UnitSAPCode) {
        this.UnitSAPCode = UnitSAPCode;
    }

    public String getPartnerNumber() {
        return PartnerNumber;
    }

    public void setPartnerNumber(String PartnerNumber) {
        this.PartnerNumber = PartnerNumber;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String UnitName) {
        this.UnitName = UnitName;
    }

    public String getEnabled() {
        return Enabled;
    }

    public void setEnabled(String Enabled) {
        this.Enabled = Enabled;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public String getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(String UnitNo) {
        this.UnitNo = UnitNo;
    }

    @Override
    public String toString() {
        return UnitName;
    }
}
