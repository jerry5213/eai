package com.zxtech.ecs.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by syp523 on 2017/11/16.
 */

public class DropDownVo {

    private String value;
    private String text;

    public DropDownVo(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public DropDownVo() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return text;
    }


}
