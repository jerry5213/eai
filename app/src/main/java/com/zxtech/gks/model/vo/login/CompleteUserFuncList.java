package com.zxtech.gks.model.vo.login;

/**
 * Created by SYP521 on 2017/12/4.
 */

public class CompleteUserFuncList {

    /**
     * FuncControlType : 0
     * FuncId : 1967cc79-9ec4-4f25-b80b-5d567ace2131
     * FuncName : ECS主界面
     * FuncNo : ECSUI
     * FuncTreeNo : 034
     * FuncType : 0
     * FuncUrl : null
     * ParentFuncTreeNo :
     * SameLevelSort : null
     */

    private int FuncControlType;
    private String FuncId;
    private String FuncName;
    private String FuncNo;
    private String FuncTreeNo;
    private int FuncType;
    private String FuncUrl;
    private String ParentFuncTreeNo;
    private String SameLevelSort;
    private boolean IsByPermissionsControl;

    public int getFuncControlType() {
        return FuncControlType;
    }

    public void setFuncControlType(int FuncControlType) {
        this.FuncControlType = FuncControlType;
    }

    public String getFuncId() {
        return FuncId;
    }

    public void setFuncId(String FuncId) {
        this.FuncId = FuncId;
    }

    public String getFuncName() {
        return FuncName;
    }

    public void setFuncName(String FuncName) {
        this.FuncName = FuncName;
    }

    public String getFuncNo() {
        return FuncNo;
    }

    public void setFuncNo(String FuncNo) {
        this.FuncNo = FuncNo;
    }

    public String getFuncTreeNo() {
        return FuncTreeNo;
    }

    public void setFuncTreeNo(String FuncTreeNo) {
        this.FuncTreeNo = FuncTreeNo;
    }

    public int getFuncType() {
        return FuncType;
    }

    public void setFuncType(int FuncType) {
        this.FuncType = FuncType;
    }

    public Object getFuncUrl() {
        return FuncUrl;
    }

    public void setFuncUrl(String FuncUrl) {
        this.FuncUrl = FuncUrl;
    }

    public String getParentFuncTreeNo() {
        return ParentFuncTreeNo;
    }

    public void setParentFuncTreeNo(String ParentFuncTreeNo) {
        this.ParentFuncTreeNo = ParentFuncTreeNo;
    }

    public Object getSameLevelSort() {
        return SameLevelSort;
    }

    public void setSameLevelSort(String SameLevelSort) {
        this.SameLevelSort = SameLevelSort;
    }

    public boolean isByPermissionsControl() {
        return IsByPermissionsControl;
    }

    public void setByPermissionsControl(boolean byPermissionsControl) {
        IsByPermissionsControl = byPermissionsControl;
    }
}
