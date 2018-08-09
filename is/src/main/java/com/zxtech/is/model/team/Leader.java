package com.zxtech.is.model.team;

/**
 * 安装队长列表
 * Created by syp692 on 2018/4/21.
 */
//
public class Leader {


    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    private String Code;
    private String Value;

    @Override
    public String toString() {
        return Value;
    }


}
