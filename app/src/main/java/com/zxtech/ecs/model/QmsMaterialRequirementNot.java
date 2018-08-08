package com.zxtech.ecs.model;

import java.io.Serializable;

/**
 * Created by syp521 on 2018/3/29.
 * 非物料需求
 */

public class QmsMaterialRequirementNot implements Serializable{

    private String NM_FKYY;
    private String NM_YZD;
    private String NM_FSPL;

    public QmsMaterialRequirementNot(String NM_FKYY, String NM_YZD, String NM_FSPL) {
        this.NM_FKYY = NM_FKYY;
        this.NM_YZD = NM_YZD;
        this.NM_FSPL = NM_FSPL;
    }

    public String getNM_FKYY() {
        return NM_FKYY;
    }

    public void setNM_FKYY(String NM_FKYY) {
        this.NM_FKYY = NM_FKYY;
    }

    public String getNM_YZD() {
        return NM_YZD;
    }

    public void setNM_YZD(String NM_YZD) {
        this.NM_YZD = NM_YZD;
    }

    public String getNM_FSPL() {
        return NM_FSPL;
    }

    public void setNM_FSPL(String NM_FSPL) {
        this.NM_FSPL = NM_FSPL;
    }
}
