package com.zxtech.ecs.model;

import java.io.Serializable;

/**
 * Created by syp521 on 2018/3/29.
 * 物料需求
 */

public class QmsMaterialRequirement implements Serializable{

    private String M_JH;
    private String M_MC;
    private String M_WTDM;
    private String M_WTDMValue;
    private String M_SL;
    private String M_DW;
    private String M_DWValue;

    public QmsMaterialRequirement(String m_JH, String m_MC, String m_WTDM,String M_WTDMValue, String m_SL, String m_DW,String M_DWValue) {
        M_JH = m_JH;
        M_MC = m_MC;
        M_WTDM = m_WTDM;
        this.M_WTDMValue = M_WTDMValue;
        M_SL = m_SL;
        M_DW = m_DW;
        this.M_DWValue = M_DWValue;
    }

    public String getM_WTDMValue() {
        return M_WTDMValue;
    }

    public void setM_WTDMValue(String m_WTDMValue) {
        M_WTDMValue = m_WTDMValue;
    }

    public String getM_JH() {
        return M_JH;
    }

    public void setM_JH(String m_JH) {
        M_JH = m_JH;
    }

    public String getM_MC() {
        return M_MC;
    }

    public void setM_MC(String m_MC) {
        M_MC = m_MC;
    }

    public String getM_WTDM() {
        return M_WTDM;
    }

    public void setM_WTDM(String m_WTDM) {
        M_WTDM = m_WTDM;
    }

    public String getM_SL() {
        return M_SL;
    }

    public void setM_SL(String m_SL) {
        M_SL = m_SL;
    }

    public String getM_DW() {
        return M_DW;
    }

    public void setM_DW(String m_DW) {
        M_DW = m_DW;
    }

    public String getM_DWValue() {
        return M_DWValue;
    }

    public void setM_DWValue(String m_DWValue) {
        M_DWValue = m_DWValue;
    }
}
