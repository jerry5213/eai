package com.zxtech.mt.entity;

/**
 * Created by syp523 on 2017/8/25.
 */

public class Icon {

    private String text;

    private String icon;

    private int color;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Icon() {
    }

    public Icon(String text, String icon, int color) {
        this.text = text;
        this.icon = icon;
        this.color = color;
    }
}
