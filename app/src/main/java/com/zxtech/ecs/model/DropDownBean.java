package com.zxtech.ecs.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by syp523 on 2017/11/16.
 */

public class DropDownBean {
    @SerializedName("value")
    private String key;
    @SerializedName("text")
    private String value;
    @SerializedName("imagePath")
    private String ImagePath;

    public DropDownBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public DropDownBean() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }


}
