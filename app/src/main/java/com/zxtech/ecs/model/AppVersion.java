package com.zxtech.ecs.model;

import java.io.Serializable;

/**
 * Created by syp523 on 2018/3/13.
 */

public class AppVersion implements Serializable{


    private int version_code;
    private String version_name;
    private String update_content;

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getUpdate_content() {
        return update_content;
    }

    public void setUpdate_content(String update_content) {
        this.update_content = update_content;
    }
}
