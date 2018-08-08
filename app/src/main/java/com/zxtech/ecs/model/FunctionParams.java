package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/1/22.
 */

public class FunctionParams {

    private String paramsName;

    private boolean standard;


    public String getParamsName() {
        return paramsName;
    }

    public void setParamsName(String paramsName) {
        this.paramsName = paramsName;
    }

    public boolean isStandard() {
        return standard;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }

    public FunctionParams() {
    }

    public FunctionParams(String paramsName, boolean standard) {
        this.paramsName = paramsName;
        this.standard = standard;
    }
}
