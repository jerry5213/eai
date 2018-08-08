package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/4/5.
 */

public class LayoutSelect {

    private String code;

    private String title;

    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LayoutSelect() {
    }

    public LayoutSelect(String code, String title, String value) {
        this.code = code;
        this.title = title;
        this.value = value;
    }


}
