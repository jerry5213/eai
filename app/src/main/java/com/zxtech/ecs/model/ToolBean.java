package com.zxtech.ecs.model;

/**
 * Created by syp523 on 2018/6/27.
 */

public class ToolBean {

    private String text;
    private int color;

    public ToolBean(String text, int color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
