package com.zxtech.gks.model.vo;

/**
 * Created by SYP521 on 2017/11/1.
 */

public class GridMenu {

    private String id;
    private String name;
    private int drawableId;
    private int num;

    public GridMenu(String id, String name, int drawableId, int num) {
        this.id = id;
        this.name = name;
        this.drawableId = drawableId;
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
