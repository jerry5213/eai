package com.zxtech.gks.model.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by syp523 on 2017/12/6.
 */

public class FbParamBean<E> {

    private String sumDesignCycle ;
    private String sumProductValidateCycle ;
    @SerializedName("SpecialNonStandardList")
    private List<E> data ;

    public String getSumDesignCycle() {
        return sumDesignCycle;
    }

    public void setSumDesignCycle(String sumDesignCycle) {
        this.sumDesignCycle = sumDesignCycle;
    }

    public String getSumProductValidateCycle() {
        return sumProductValidateCycle;
    }

    public void setSumProductValidateCycle(String sumProductValidateCycle) {
        this.sumProductValidateCycle = sumProductValidateCycle;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}
