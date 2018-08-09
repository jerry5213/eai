package com.zxtech.is.model.me;

/**
 * Created by syp523 on 2018/2/2.
 */

public class Setting {

    private String title;

    private int drawable;

    private Class intentClass;

    public Class getIntentClass() {
        return intentClass;
    }

    public void setIntentClass(Class intentClass) {
        this.intentClass = intentClass;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Setting() {
    }

    public Setting(String title) {
        this.title = title;
    }

    public Setting(String title, int drawable, Class intentClass) {
        this.title = title;
        this.drawable = drawable;
        this.intentClass = intentClass;
    }
}
