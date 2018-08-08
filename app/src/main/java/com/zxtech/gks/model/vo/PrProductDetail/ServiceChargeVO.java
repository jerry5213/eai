package com.zxtech.gks.model.vo.PrProductDetail;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SYP521 on 2017/12/7.
 */

public class ServiceChargeVO {

    /**
     * PriceReviewGuid : 1c7e3211-4ee8-4b55-83e7-969efdf5b7f1
     * TransactorGuid : 3b0c068c-c67a-4ec9-bf64-0c99441fa94c
     * RoleNo : JS-FZ-SP
     * TransactoResult : false
     * AgentCommission :
     * AgentCommissionRate :
     * RealFloatingRate :
     * TransactorDate : /Date(1510223824713)/
     * ValidState : true
     * TransactorUserName :
     */

    private String PriceReviewGuid;
    private String TransactorGuid;
    private String RoleNo;
    private boolean TransactoResult;
    private String AgentCommission;
    private String AgentCommissionRate;
    private String RealFloatingRate;
    private String TransactorDate;
    private boolean ValidState;
    private String TransactorUserName;

    public String getPriceReviewGuid() {
        return PriceReviewGuid;
    }

    public void setPriceReviewGuid(String PriceReviewGuid) {
        this.PriceReviewGuid = PriceReviewGuid;
    }

    public String getTransactorGuid() {
        return TransactorGuid;
    }

    public void setTransactorGuid(String TransactorGuid) {
        this.TransactorGuid = TransactorGuid;
    }

    public String getRoleNo() {
        return RoleNo;
    }

    public void setRoleNo(String RoleNo) {
        this.RoleNo = RoleNo;
    }

    public boolean isTransactoResult() {
        return TransactoResult;
    }

    public void setTransactoResult(boolean TransactoResult) {
        this.TransactoResult = TransactoResult;
    }

    public String getAgentCommission() {
        return AgentCommission;
    }

    public void setAgentCommission(String AgentCommission) {
        this.AgentCommission = AgentCommission;
    }

    public String getAgentCommissionRate() {
        return AgentCommissionRate;
    }

    public void setAgentCommissionRate(String AgentCommissionRate) {
        this.AgentCommissionRate = AgentCommissionRate;
    }

    public String getRealFloatingRate() {
        return RealFloatingRate;
    }

    public void setRealFloatingRate(String RealFloatingRate) {
        this.RealFloatingRate = RealFloatingRate;
    }

    public String getTransactorDate() {

        try {
            if (TextUtils.isEmpty(TransactorDate)) {
                return "";
            }
            long timeInMills = Long.parseLong(TransactorDate.substring(6, 19));
            return new SimpleDateFormat("yyyy/MM/dd").format(new Date(timeInMills));
        } catch (Exception e) {
            e.printStackTrace();
            return TransactorDate;
        }
    }

    public void setTransactorDate(String TransactorDate) {
        this.TransactorDate = TransactorDate;
    }

    public boolean isValidState() {
        return ValidState;
    }

    public void setValidState(boolean ValidState) {
        this.ValidState = ValidState;
    }

    public String getTransactorUserName() {
        return TransactorUserName;
    }

    public void setTransactorUserName(String TransactorUserName) {
        this.TransactorUserName = TransactorUserName;
    }
}
