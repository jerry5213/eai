package com.zxtech.ecs.model;

public class SystemCodeListBean {
    /**
     * Code : 1
     * Value : 01-人机界面系统
     */
    private String Code;
    private String Value;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    @Override
    public String toString() {
        return Value;
    }
}