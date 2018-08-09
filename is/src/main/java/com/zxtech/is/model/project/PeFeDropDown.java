package com.zxtech.is.model.project;

/**
 * Created by syp661 on 2018/4/23.
 * <p>
 * 项目团队分配 下拉框
 */

public class PeFeDropDown {

    private String id;
    private String text;
    private String flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return text;
    }
}
